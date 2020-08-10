package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.TeeDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata.CC;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class CacheDataSource implements DataSource {
    public static final int CACHE_IGNORED_REASON_ERROR = 0;
    public static final int CACHE_IGNORED_REASON_UNSET_LENGTH = 1;
    private static final int CACHE_NOT_IGNORED = -1;
    public static final int FLAG_BLOCK_ON_CACHE = 1;
    public static final int FLAG_IGNORE_CACHE_FOR_UNSET_LENGTH_REQUESTS = 4;
    public static final int FLAG_IGNORE_CACHE_ON_ERROR = 2;
    private static final long MIN_READ_BEFORE_CHECKING_CACHE = 102400;
    private Uri actualUri;
    private final boolean blockOnCache;
    private long bytesRemaining;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final DataSource cacheReadDataSource;
    private final DataSource cacheWriteDataSource;
    private long checkCachePosition;
    private DataSource currentDataSource;
    private boolean currentDataSpecLengthUnset;
    private CacheSpan currentHoleSpan;
    private boolean currentRequestIgnoresCache;
    private final EventListener eventListener;
    private int flags;
    private byte[] httpBody;
    private int httpMethod;
    private final boolean ignoreCacheForUnsetLengthRequests;
    private final boolean ignoreCacheOnError;
    private String key;
    private long readPosition;
    private boolean seenCacheError;
    private long totalCachedBytesRead;
    private final DataSource upstreamDataSource;
    private Uri uri;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheIgnoredReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public interface EventListener {
        void onCacheIgnored(int i);

        void onCachedBytesRead(long j, long j2);
    }

    public CacheDataSource(Cache cache2, DataSource dataSource) {
        this(cache2, dataSource, 0);
    }

    public CacheDataSource(Cache cache2, DataSource dataSource, int i) {
        this(cache2, dataSource, new FileDataSource(), new CacheDataSink(cache2, CacheDataSink.DEFAULT_FRAGMENT_SIZE), i, null);
    }

    public CacheDataSource(Cache cache2, DataSource dataSource, DataSource dataSource2, DataSink dataSink, int i, EventListener eventListener2) {
        this(cache2, dataSource, dataSource2, dataSink, i, eventListener2, null);
    }

    public CacheDataSource(Cache cache2, DataSource dataSource, DataSource dataSource2, DataSink dataSink, int i, EventListener eventListener2, CacheKeyFactory cacheKeyFactory2) {
        this.cache = cache2;
        this.cacheReadDataSource = dataSource2;
        if (cacheKeyFactory2 == null) {
            cacheKeyFactory2 = CacheUtil.DEFAULT_CACHE_KEY_FACTORY;
        }
        this.cacheKeyFactory = cacheKeyFactory2;
        boolean z = false;
        this.blockOnCache = (i & 1) != 0;
        this.ignoreCacheOnError = (i & 2) != 0;
        if ((i & 4) != 0) {
            z = true;
        }
        this.ignoreCacheForUnsetLengthRequests = z;
        this.upstreamDataSource = dataSource;
        if (dataSink != null) {
            this.cacheWriteDataSource = new TeeDataSource(dataSource, dataSink);
        } else {
            this.cacheWriteDataSource = null;
        }
        this.eventListener = eventListener2;
    }

    public void addTransferListener(TransferListener transferListener) {
        this.cacheReadDataSource.addTransferListener(transferListener);
        this.upstreamDataSource.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        try {
            this.key = this.cacheKeyFactory.buildCacheKey(dataSpec);
            this.uri = dataSpec.uri;
            this.actualUri = getRedirectedUriOrDefault(this.cache, this.key, this.uri);
            this.httpMethod = dataSpec.httpMethod;
            this.httpBody = dataSpec.httpBody;
            this.flags = dataSpec.flags;
            this.readPosition = dataSpec.position;
            int shouldIgnoreCacheForRequest = shouldIgnoreCacheForRequest(dataSpec);
            this.currentRequestIgnoresCache = shouldIgnoreCacheForRequest != -1;
            if (this.currentRequestIgnoresCache) {
                notifyCacheIgnored(shouldIgnoreCacheForRequest);
            }
            if (dataSpec.length == -1) {
                if (!this.currentRequestIgnoresCache) {
                    this.bytesRemaining = CC.getContentLength(this.cache.getContentMetadata(this.key));
                    if (this.bytesRemaining != -1) {
                        this.bytesRemaining -= dataSpec.position;
                        if (this.bytesRemaining <= 0) {
                            throw new DataSourceException(0);
                        }
                    }
                    openNextSource(false);
                    return this.bytesRemaining;
                }
            }
            this.bytesRemaining = dataSpec.length;
            openNextSource(false);
            return this.bytesRemaining;
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        try {
            if (this.readPosition >= this.checkCachePosition) {
                openNextSource(true);
            }
            int read = this.currentDataSource.read(bArr, i, i2);
            if (read != -1) {
                if (isReadingFromCache()) {
                    this.totalCachedBytesRead += (long) read;
                }
                long j = (long) read;
                this.readPosition += j;
                if (this.bytesRemaining != -1) {
                    this.bytesRemaining -= j;
                }
            } else if (this.currentDataSpecLengthUnset) {
                setNoBytesRemainingAndMaybeStoreLength();
            } else {
                if (this.bytesRemaining <= 0) {
                    if (this.bytesRemaining == -1) {
                    }
                }
                closeCurrentSource();
                openNextSource(false);
                return read(bArr, i, i2);
            }
            return read;
        } catch (IOException e) {
            if (!this.currentDataSpecLengthUnset || !CacheUtil.isCausedByPositionOutOfRange(e)) {
                handleBeforeThrow(e);
                throw e;
            }
            setNoBytesRemainingAndMaybeStoreLength();
            return -1;
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    public Uri getUri() {
        return this.actualUri;
    }

    public Map<String, List<String>> getResponseHeaders() {
        if (isReadingFromUpstream()) {
            return this.upstreamDataSource.getResponseHeaders();
        }
        return Collections.emptyMap();
    }

    public void close() throws IOException {
        this.uri = null;
        this.actualUri = null;
        this.httpMethod = 1;
        this.httpBody = null;
        notifyBytesRead();
        try {
            closeCurrentSource();
        } catch (Throwable th) {
            handleBeforeThrow(th);
            throw th;
        }
    }

    private void openNextSource(boolean z) throws IOException {
        CacheSpan cacheSpan;
        DataSource dataSource;
        CacheSpan cacheSpan2;
        DataSpec dataSpec;
        long j;
        DataSpec dataSpec2;
        Uri uri2 = null;
        if (this.currentRequestIgnoresCache) {
            cacheSpan = null;
        } else if (this.blockOnCache) {
            try {
                cacheSpan = this.cache.startReadWrite(this.key, this.readPosition);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
        } else {
            cacheSpan = this.cache.startReadWriteNonBlocking(this.key, this.readPosition);
        }
        if (cacheSpan == null) {
            DataSource dataSource2 = this.upstreamDataSource;
            Uri uri3 = this.uri;
            int i = this.httpMethod;
            byte[] bArr = this.httpBody;
            long j2 = this.readPosition;
            DataSpec dataSpec3 = new DataSpec(uri3, i, bArr, j2, j2, this.bytesRemaining, this.key, this.flags);
            dataSource = dataSource2;
            cacheSpan2 = cacheSpan;
            dataSpec = dataSpec3;
        } else {
            if (cacheSpan.isCached) {
                Uri fromFile = Uri.fromFile(cacheSpan.file);
                long j3 = this.readPosition - cacheSpan.position;
                long j4 = cacheSpan.length - j3;
                long j5 = this.bytesRemaining;
                if (j5 != -1) {
                    j4 = Math.min(j4, j5);
                }
                dataSpec2 = new DataSpec(fromFile, this.readPosition, j3, j4, this.key, this.flags);
                dataSource = this.cacheReadDataSource;
            } else {
                if (cacheSpan.isOpenEnded()) {
                    j = this.bytesRemaining;
                } else {
                    j = cacheSpan.length;
                    long j6 = this.bytesRemaining;
                    if (j6 != -1) {
                        j = Math.min(j, j6);
                    }
                }
                long j7 = j;
                Uri uri4 = this.uri;
                int i2 = this.httpMethod;
                byte[] bArr2 = this.httpBody;
                long j8 = this.readPosition;
                dataSpec2 = new DataSpec(uri4, i2, bArr2, j8, j8, j7, this.key, this.flags);
                dataSource = this.cacheWriteDataSource;
                if (dataSource == null) {
                    dataSource = this.upstreamDataSource;
                    this.cache.releaseHoleSpan(cacheSpan);
                    dataSpec = dataSpec2;
                    cacheSpan2 = null;
                }
            }
            DataSpec dataSpec4 = dataSpec2;
            cacheSpan2 = cacheSpan;
            dataSpec = dataSpec4;
        }
        this.checkCachePosition = (this.currentRequestIgnoresCache || dataSource != this.upstreamDataSource) ? Long.MAX_VALUE : this.readPosition + MIN_READ_BEFORE_CHECKING_CACHE;
        if (z) {
            Assertions.checkState(isBypassingCache());
            if (dataSource != this.upstreamDataSource) {
                try {
                    closeCurrentSource();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (cacheSpan2.isHoleSpan()) {
                        this.cache.releaseHoleSpan(cacheSpan2);
                    }
                    throw th2;
                }
            } else {
                return;
            }
        }
        if (cacheSpan2 != null && cacheSpan2.isHoleSpan()) {
            this.currentHoleSpan = cacheSpan2;
        }
        this.currentDataSource = dataSource;
        this.currentDataSpecLengthUnset = dataSpec.length == -1;
        long open = dataSource.open(dataSpec);
        ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
        if (this.currentDataSpecLengthUnset && open != -1) {
            this.bytesRemaining = open;
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.readPosition + this.bytesRemaining);
        }
        if (isReadingFromUpstream()) {
            this.actualUri = this.currentDataSource.getUri();
            if (!this.uri.equals(this.actualUri)) {
                uri2 = this.actualUri;
            }
            ContentMetadataMutations.setRedirectedUri(contentMetadataMutations, uri2);
        }
        if (isWritingToCache()) {
            this.cache.applyContentMetadataMutations(this.key, contentMetadataMutations);
        }
    }

    private void setNoBytesRemainingAndMaybeStoreLength() throws IOException {
        this.bytesRemaining = 0;
        if (isWritingToCache()) {
            ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
            ContentMetadataMutations.setContentLength(contentMetadataMutations, this.readPosition);
            this.cache.applyContentMetadataMutations(this.key, contentMetadataMutations);
        }
    }

    private static Uri getRedirectedUriOrDefault(Cache cache2, String str, Uri uri2) {
        Uri redirectedUri = CC.getRedirectedUri(cache2.getContentMetadata(str));
        return redirectedUri != null ? redirectedUri : uri2;
    }

    private boolean isReadingFromUpstream() {
        return !isReadingFromCache();
    }

    private boolean isBypassingCache() {
        return this.currentDataSource == this.upstreamDataSource;
    }

    private boolean isReadingFromCache() {
        return this.currentDataSource == this.cacheReadDataSource;
    }

    private boolean isWritingToCache() {
        return this.currentDataSource == this.cacheWriteDataSource;
    }

    private void closeCurrentSource() throws IOException {
        DataSource dataSource = this.currentDataSource;
        if (dataSource != null) {
            try {
                dataSource.close();
            } finally {
                this.currentDataSource = null;
                this.currentDataSpecLengthUnset = false;
                CacheSpan cacheSpan = this.currentHoleSpan;
                if (cacheSpan != null) {
                    this.cache.releaseHoleSpan(cacheSpan);
                    this.currentHoleSpan = null;
                }
            }
        }
    }

    private void handleBeforeThrow(Throwable th) {
        if (isReadingFromCache() || (th instanceof CacheException)) {
            this.seenCacheError = true;
        }
    }

    private int shouldIgnoreCacheForRequest(DataSpec dataSpec) {
        if (!this.ignoreCacheOnError || !this.seenCacheError) {
            return (!this.ignoreCacheForUnsetLengthRequests || dataSpec.length != -1) ? -1 : 1;
        }
        return 0;
    }

    private void notifyCacheIgnored(int i) {
        EventListener eventListener2 = this.eventListener;
        if (eventListener2 != null) {
            eventListener2.onCacheIgnored(i);
        }
    }

    private void notifyBytesRead() {
        EventListener eventListener2 = this.eventListener;
        if (eventListener2 != null && this.totalCachedBytesRead > 0) {
            eventListener2.onCachedBytesRead(this.cache.getCacheSpace(), this.totalCachedBytesRead);
            this.totalCachedBytesRead = 0;
        }
    }
}
