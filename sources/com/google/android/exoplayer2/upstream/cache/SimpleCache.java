package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import com.google.android.exoplayer2.upstream.cache.Cache.Listener;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata.CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public final class SimpleCache implements Cache {
    private static final int SUBDIRECTORY_COUNT = 10;
    private static final String TAG = "SimpleCache";
    private static final String UID_FILE_SUFFIX = ".uid";
    private static final HashSet<File> lockedCacheDirs = new HashSet<>();
    private final File cacheDir;
    private final CachedContentIndex contentIndex;
    /* access modifiers changed from: private */
    public final CacheEvictor evictor;
    private final CacheFileMetadataIndex fileIndex;
    private CacheException initializationException;
    private final HashMap<String, ArrayList<Listener>> listeners;
    private final Random random;
    private boolean released;
    private long totalSpace;
    private final boolean touchCacheSpans;
    private long uid;

    public static synchronized boolean isCacheFolderLocked(File file) {
        boolean contains;
        synchronized (SimpleCache.class) {
            contains = lockedCacheDirs.contains(file.getAbsoluteFile());
        }
        return contains;
    }

    public static void delete(File file, DatabaseProvider databaseProvider) {
        String str = "Failed to delete file metadata: ";
        String str2 = TAG;
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                file.delete();
                return;
            }
            if (databaseProvider != null) {
                long loadUid = loadUid(listFiles);
                if (loadUid != -1) {
                    try {
                        CacheFileMetadataIndex.delete(databaseProvider, loadUid);
                    } catch (DatabaseIOException unused) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(loadUid);
                        Log.m1396w(str2, sb.toString());
                    }
                    try {
                        CachedContentIndex.delete(databaseProvider, loadUid);
                    } catch (DatabaseIOException unused2) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(loadUid);
                        Log.m1396w(str2, sb2.toString());
                    }
                }
            }
            Util.recursiveDelete(file);
        }
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor) {
        this(file, cacheEvictor, (byte[]) null, false);
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr) {
        this(file, cacheEvictor, bArr, bArr != null);
    }

    @Deprecated
    public SimpleCache(File file, CacheEvictor cacheEvictor, byte[] bArr, boolean z) {
        this(file, cacheEvictor, null, bArr, z, true);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, DatabaseProvider databaseProvider) {
        this(file, cacheEvictor, databaseProvider, null, false, false);
    }

    public SimpleCache(File file, CacheEvictor cacheEvictor, DatabaseProvider databaseProvider, byte[] bArr, boolean z, boolean z2) {
        CachedContentIndex cachedContentIndex = new CachedContentIndex(databaseProvider, file, bArr, z, z2);
        this(file, cacheEvictor, cachedContentIndex, (databaseProvider == null || z2) ? null : new CacheFileMetadataIndex(databaseProvider));
    }

    SimpleCache(File file, CacheEvictor cacheEvictor, CachedContentIndex cachedContentIndex, CacheFileMetadataIndex cacheFileMetadataIndex) {
        if (lockFolder(file)) {
            this.cacheDir = file;
            this.evictor = cacheEvictor;
            this.contentIndex = cachedContentIndex;
            this.fileIndex = cacheFileMetadataIndex;
            this.listeners = new HashMap<>();
            this.random = new Random();
            this.touchCacheSpans = cacheEvictor.requiresCacheSpanTouches();
            this.uid = -1;
            final ConditionVariable conditionVariable = new ConditionVariable();
            new Thread("SimpleCache.initialize()") {
                public void run() {
                    synchronized (SimpleCache.this) {
                        conditionVariable.open();
                        SimpleCache.this.initialize();
                        SimpleCache.this.evictor.onCacheInitialized();
                    }
                }
            }.start();
            conditionVariable.block();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Another SimpleCache instance uses the folder: ");
        sb.append(file);
        throw new IllegalStateException(sb.toString());
    }

    public synchronized void checkInitialization() throws CacheException {
        if (this.initializationException != null) {
            throw this.initializationException;
        }
    }

    public synchronized long getUid() {
        return this.uid;
    }

    public synchronized void release() {
        if (!this.released) {
            this.listeners.clear();
            removeStaleSpans();
            try {
                this.contentIndex.store();
                unlockFolder(this.cacheDir);
            } catch (IOException e) {
                try {
                    Log.m1393e(TAG, "Storing index file failed", e);
                    unlockFolder(this.cacheDir);
                } catch (Throwable th) {
                    unlockFolder(this.cacheDir);
                    this.released = true;
                    throw th;
                }
            }
            this.released = true;
            return;
        }
        return;
    }

    public synchronized NavigableSet<CacheSpan> addListener(String str, Listener listener) {
        Assertions.checkState(!this.released);
        ArrayList arrayList = (ArrayList) this.listeners.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.listeners.put(str, arrayList);
        }
        arrayList.add(listener);
        return getCachedSpans(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeListener(java.lang.String r2, com.google.android.exoplayer2.upstream.cache.Cache.Listener r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.released     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r0 = r1.listeners     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x0021 }
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            r0.remove(r3)     // Catch:{ all -> 0x0021 }
            boolean r3 = r0.isEmpty()     // Catch:{ all -> 0x0021 }
            if (r3 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r3 = r1.listeners     // Catch:{ all -> 0x0021 }
            r3.remove(r2)     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r1)
            return
        L_0x0021:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.removeListener(java.lang.String, com.google.android.exoplayer2.upstream.cache.Cache$Listener):void");
    }

    public synchronized NavigableSet<CacheSpan> getCachedSpans(String str) {
        TreeSet treeSet;
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(str);
        if (cachedContent != null) {
            if (!cachedContent.isEmpty()) {
                treeSet = new TreeSet(cachedContent.getSpans());
            }
        }
        treeSet = new TreeSet();
        return treeSet;
    }

    public synchronized Set<String> getKeys() {
        Assertions.checkState(!this.released);
        return new HashSet(this.contentIndex.getKeys());
    }

    public synchronized long getCacheSpace() {
        Assertions.checkState(!this.released);
        return this.totalSpace;
    }

    public synchronized CacheSpan startReadWrite(String str, long j) throws InterruptedException, CacheException {
        CacheSpan startReadWriteNonBlocking;
        Assertions.checkState(!this.released);
        checkInitialization();
        while (true) {
            startReadWriteNonBlocking = startReadWriteNonBlocking(str, j);
            if (startReadWriteNonBlocking == null) {
                wait();
            }
        }
        return startReadWriteNonBlocking;
    }

    public synchronized CacheSpan startReadWriteNonBlocking(String str, long j) throws CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        SimpleCacheSpan span = getSpan(str, j);
        if (span.isCached) {
            return touchSpan(str, span);
        }
        CachedContent orAdd = this.contentIndex.getOrAdd(str);
        if (orAdd.isLocked()) {
            return null;
        }
        orAdd.setLocked(true);
        return span;
    }

    public synchronized File startFile(String str, long j, long j2) throws CacheException {
        CachedContent cachedContent;
        File file;
        Assertions.checkState(!this.released);
        checkInitialization();
        cachedContent = this.contentIndex.get(str);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
            removeStaleSpans();
        }
        this.evictor.onStartFile(this, str, j, j2);
        file = new File(this.cacheDir, Integer.toString(this.random.nextInt(10)));
        if (!file.exists()) {
            file.mkdir();
        }
        return SimpleCacheSpan.getCacheFile(file, cachedContent.f1529id, j, System.currentTimeMillis());
    }

    public synchronized void commitFile(File file, long j) throws CacheException {
        boolean z = true;
        Assertions.checkState(!this.released);
        if (file.exists()) {
            if (j == 0) {
                file.delete();
                return;
            }
            SimpleCacheSpan simpleCacheSpan = (SimpleCacheSpan) Assertions.checkNotNull(SimpleCacheSpan.createCacheEntry(file, j, this.contentIndex));
            CachedContent cachedContent = (CachedContent) Assertions.checkNotNull(this.contentIndex.get(simpleCacheSpan.key));
            Assertions.checkState(cachedContent.isLocked());
            long contentLength = CC.getContentLength(cachedContent.getMetadata());
            if (contentLength != -1) {
                if (simpleCacheSpan.position + simpleCacheSpan.length > contentLength) {
                    z = false;
                }
                Assertions.checkState(z);
            }
            if (this.fileIndex != null) {
                try {
                    this.fileIndex.set(file.getName(), simpleCacheSpan.length, simpleCacheSpan.lastTouchTimestamp);
                } catch (IOException e) {
                    throw new CacheException((Throwable) e);
                } catch (IOException e2) {
                    throw new CacheException((Throwable) e2);
                }
            }
            addSpan(simpleCacheSpan);
            this.contentIndex.store();
            notifyAll();
        }
    }

    public synchronized void releaseHoleSpan(CacheSpan cacheSpan) {
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(cacheSpan.key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        cachedContent.setLocked(false);
        this.contentIndex.maybeRemove(cachedContent.key);
        notifyAll();
    }

    public synchronized void removeSpan(CacheSpan cacheSpan) {
        Assertions.checkState(!this.released);
        removeSpanInternal(cacheSpan);
    }

    public synchronized boolean isCached(String str, long j, long j2) {
        boolean z;
        z = true;
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(str);
        if (cachedContent == null || cachedContent.getCachedBytesLength(j, j2) < j2) {
            z = false;
        }
        return z;
    }

    public synchronized long getCachedLength(String str, long j, long j2) {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        cachedContent = this.contentIndex.get(str);
        return cachedContent != null ? cachedContent.getCachedBytesLength(j, j2) : -j2;
    }

    public synchronized void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) throws CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        this.contentIndex.applyContentMetadataMutations(str, contentMetadataMutations);
        try {
            this.contentIndex.store();
        } catch (IOException e) {
            throw new CacheException((Throwable) e);
        }
    }

    public synchronized ContentMetadata getContentMetadata(String str) {
        Assertions.checkState(!this.released);
        return this.contentIndex.getContentMetadata(str);
    }

    /* access modifiers changed from: private */
    public void initialize() {
        boolean exists = this.cacheDir.exists();
        String str = TAG;
        if (exists || this.cacheDir.mkdirs()) {
            File[] listFiles = this.cacheDir.listFiles();
            if (listFiles == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to list cache directory files: ");
                sb.append(this.cacheDir);
                String sb2 = sb.toString();
                Log.m1392e(str, sb2);
                this.initializationException = new CacheException(sb2);
                return;
            }
            this.uid = loadUid(listFiles);
            if (this.uid == -1) {
                try {
                    this.uid = createUid(this.cacheDir);
                } catch (IOException e) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Failed to create cache UID: ");
                    sb3.append(this.cacheDir);
                    String sb4 = sb3.toString();
                    Log.m1393e(str, sb4, e);
                    this.initializationException = new CacheException(sb4, e);
                    return;
                }
            }
            try {
                this.contentIndex.initialize(this.uid);
                if (this.fileIndex != null) {
                    this.fileIndex.initialize(this.uid);
                    Map all = this.fileIndex.getAll();
                    loadDirectory(this.cacheDir, true, listFiles, all);
                    this.fileIndex.removeAll(all.keySet());
                } else {
                    loadDirectory(this.cacheDir, true, listFiles, null);
                }
                this.contentIndex.removeEmpty();
                try {
                    this.contentIndex.store();
                } catch (IOException e2) {
                    Log.m1393e(str, "Storing index file failed", e2);
                }
            } catch (IOException e3) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Failed to initialize cache indices: ");
                sb5.append(this.cacheDir);
                String sb6 = sb5.toString();
                Log.m1393e(str, sb6, e3);
                this.initializationException = new CacheException(sb6, e3);
            }
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Failed to create cache directory: ");
            sb7.append(this.cacheDir);
            String sb8 = sb7.toString();
            Log.m1392e(str, sb8);
            this.initializationException = new CacheException(sb8);
        }
    }

    private void loadDirectory(File file, boolean z, File[] fileArr, Map<String, CacheFileMetadata> map) {
        if (fileArr == null || fileArr.length == 0) {
            if (!z) {
                file.delete();
            }
            return;
        }
        for (File file2 : fileArr) {
            String name = file2.getName();
            if (z && name.indexOf(46) == -1) {
                loadDirectory(file2, false, file2.listFiles(), map);
            } else if (!z || (!CachedContentIndex.isIndexFile(name) && !name.endsWith(UID_FILE_SUFFIX))) {
                long j = -1;
                long j2 = C1996C.TIME_UNSET;
                CacheFileMetadata cacheFileMetadata = map != null ? (CacheFileMetadata) map.remove(name) : null;
                if (cacheFileMetadata != null) {
                    j = cacheFileMetadata.length;
                    j2 = cacheFileMetadata.lastTouchTimestamp;
                }
                SimpleCacheSpan createCacheEntry = SimpleCacheSpan.createCacheEntry(file2, j, j2, this.contentIndex);
                if (createCacheEntry != null) {
                    addSpan(createCacheEntry);
                } else {
                    file2.delete();
                }
            }
        }
    }

    private SimpleCacheSpan touchSpan(String str, SimpleCacheSpan simpleCacheSpan) {
        if (!this.touchCacheSpans) {
            return simpleCacheSpan;
        }
        String name = ((File) Assertions.checkNotNull(simpleCacheSpan.file)).getName();
        long j = simpleCacheSpan.length;
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        CacheFileMetadataIndex cacheFileMetadataIndex = this.fileIndex;
        if (cacheFileMetadataIndex != null) {
            try {
                cacheFileMetadataIndex.set(name, j, currentTimeMillis);
            } catch (IOException unused) {
                Log.m1396w(TAG, "Failed to update index with new touch timestamp.");
            }
        } else {
            z = true;
        }
        SimpleCacheSpan lastTouchTimestamp = this.contentIndex.get(str).setLastTouchTimestamp(simpleCacheSpan, currentTimeMillis, z);
        notifySpanTouched(simpleCacheSpan, lastTouchTimestamp);
        return lastTouchTimestamp;
    }

    private SimpleCacheSpan getSpan(String str, long j) {
        SimpleCacheSpan span;
        CachedContent cachedContent = this.contentIndex.get(str);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(str, j);
        }
        while (true) {
            span = cachedContent.getSpan(j);
            if (!span.isCached || span.file.length() == span.length) {
                return span;
            }
            removeStaleSpans();
        }
        return span;
    }

    private void addSpan(SimpleCacheSpan simpleCacheSpan) {
        this.contentIndex.getOrAdd(simpleCacheSpan.key).addSpan(simpleCacheSpan);
        this.totalSpace += simpleCacheSpan.length;
        notifySpanAdded(simpleCacheSpan);
    }

    private void removeSpanInternal(CacheSpan cacheSpan) {
        CachedContent cachedContent = this.contentIndex.get(cacheSpan.key);
        if (cachedContent != null && cachedContent.removeSpan(cacheSpan)) {
            this.totalSpace -= cacheSpan.length;
            if (this.fileIndex != null) {
                String name = cacheSpan.file.getName();
                try {
                    this.fileIndex.remove(name);
                } catch (IOException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to remove file index entry for: ");
                    sb.append(name);
                    Log.m1396w(TAG, sb.toString());
                }
            }
            this.contentIndex.maybeRemove(cachedContent.key);
            notifySpanRemoved(cacheSpan);
        }
    }

    private void removeStaleSpans() {
        ArrayList arrayList = new ArrayList();
        for (CachedContent spans : this.contentIndex.getAll()) {
            Iterator it = spans.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan cacheSpan = (CacheSpan) it.next();
                if (cacheSpan.file.length() != cacheSpan.length) {
                    arrayList.add(cacheSpan);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            removeSpanInternal((CacheSpan) arrayList.get(i));
        }
    }

    private void notifySpanRemoved(CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(cacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanRemoved(this, cacheSpan);
            }
        }
        this.evictor.onSpanRemoved(this, cacheSpan);
    }

    private void notifySpanAdded(SimpleCacheSpan simpleCacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanAdded(this, simpleCacheSpan);
            }
        }
        this.evictor.onSpanAdded(this, simpleCacheSpan);
    }

    private void notifySpanTouched(SimpleCacheSpan simpleCacheSpan, CacheSpan cacheSpan) {
        ArrayList arrayList = (ArrayList) this.listeners.get(simpleCacheSpan.key);
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((Listener) arrayList.get(size)).onSpanTouched(this, simpleCacheSpan, cacheSpan);
            }
        }
        this.evictor.onSpanTouched(this, simpleCacheSpan, cacheSpan);
    }

    private static long loadUid(File[] fileArr) {
        int length = fileArr.length;
        int i = 0;
        while (i < length) {
            File file = fileArr[i];
            String name = file.getName();
            if (name.endsWith(UID_FILE_SUFFIX)) {
                try {
                    return parseUid(name);
                } catch (NumberFormatException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Malformed UID file: ");
                    sb.append(file);
                    Log.m1392e(TAG, sb.toString());
                    file.delete();
                }
            } else {
                i++;
            }
        }
        return -1;
    }

    private static long createUid(File file) throws IOException {
        long j;
        long nextLong = new SecureRandom().nextLong();
        if (nextLong == Long.MIN_VALUE) {
            j = 0;
        } else {
            j = Math.abs(nextLong);
        }
        String l = Long.toString(j, 16);
        StringBuilder sb = new StringBuilder();
        sb.append(l);
        sb.append(UID_FILE_SUFFIX);
        File file2 = new File(file, sb.toString());
        if (file2.createNewFile()) {
            return j;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Failed to create UID file: ");
        sb2.append(file2);
        throw new IOException(sb2.toString());
    }

    private static long parseUid(String str) {
        return Long.parseLong(str.substring(0, str.indexOf(46)), 16);
    }

    private static synchronized boolean lockFolder(File file) {
        boolean add;
        synchronized (SimpleCache.class) {
            add = lockedCacheDirs.add(file.getAbsoluteFile());
        }
        return add;
    }

    private static synchronized void unlockFolder(File file) {
        synchronized (SimpleCache.class) {
            lockedCacheDirs.remove(file.getAbsoluteFile());
        }
    }
}
