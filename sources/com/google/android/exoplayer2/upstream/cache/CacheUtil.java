package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import android.util.Pair;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata.CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CacheUtil {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    public static final CacheKeyFactory DEFAULT_CACHE_KEY_FACTORY = $$Lambda$CacheUtil$uQzD0N2Max0h6DuMDYcCbN2peIo.INSTANCE;

    public interface ProgressListener {
        void onProgress(long j, long j2, long j3);
    }

    private static final class ProgressNotifier {
        private long bytesCached;
        private final ProgressListener listener;
        private long requestLength;

        public ProgressNotifier(ProgressListener progressListener) {
            this.listener = progressListener;
        }

        public void init(long j, long j2) {
            this.requestLength = j;
            this.bytesCached = j2;
            this.listener.onProgress(j, j2, 0);
        }

        public void onRequestLengthResolved(long j) {
            if (this.requestLength == -1 && j != -1) {
                this.requestLength = j;
                this.listener.onProgress(j, this.bytesCached, 0);
            }
        }

        public void onBytesCached(long j) {
            this.bytesCached += j;
            this.listener.onProgress(this.requestLength, this.bytesCached, j);
        }
    }

    static /* synthetic */ String lambda$static$0(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : generateKey(dataSpec.uri);
    }

    public static String generateKey(Uri uri) {
        return uri.toString();
    }

    public static Pair<Long, Long> getCached(DataSpec dataSpec, Cache cache, CacheKeyFactory cacheKeyFactory) {
        DataSpec dataSpec2 = dataSpec;
        String buildCacheKey = buildCacheKey(dataSpec2, cacheKeyFactory);
        long j = dataSpec2.absoluteStreamPosition;
        long requestLength = getRequestLength(dataSpec2, cache, buildCacheKey);
        long j2 = j;
        long j3 = requestLength;
        long j4 = 0;
        while (j3 != 0) {
            long cachedLength = cache.getCachedLength(buildCacheKey, j2, j3 != -1 ? j3 : Long.MAX_VALUE);
            if (cachedLength <= 0) {
                cachedLength = -cachedLength;
                if (cachedLength == Long.MAX_VALUE) {
                    break;
                }
            } else {
                j4 += cachedLength;
            }
            j2 += cachedLength;
            if (j3 == -1) {
                cachedLength = 0;
            }
            j3 -= cachedLength;
        }
        return Pair.create(Long.valueOf(requestLength), Long.valueOf(j4));
    }

    public static void cache(DataSpec dataSpec, Cache cache, CacheKeyFactory cacheKeyFactory, DataSource dataSource, ProgressListener progressListener, AtomicBoolean atomicBoolean) throws IOException, InterruptedException {
        cache(dataSpec, cache, cacheKeyFactory, new CacheDataSource(cache, dataSource), new byte[131072], null, 0, progressListener, atomicBoolean, false);
    }

    public static void cache(DataSpec dataSpec, Cache cache, CacheKeyFactory cacheKeyFactory, CacheDataSource cacheDataSource, byte[] bArr, PriorityTaskManager priorityTaskManager, int i, ProgressListener progressListener, AtomicBoolean atomicBoolean, boolean z) throws IOException, InterruptedException {
        ProgressNotifier progressNotifier;
        long j;
        long j2;
        DataSpec dataSpec2 = dataSpec;
        ProgressListener progressListener2 = progressListener;
        Assertions.checkNotNull(cacheDataSource);
        Assertions.checkNotNull(bArr);
        String buildCacheKey = buildCacheKey(dataSpec2, cacheKeyFactory);
        if (progressListener2 != null) {
            progressNotifier = new ProgressNotifier(progressListener2);
            Pair cached = getCached(dataSpec, cache, cacheKeyFactory);
            progressNotifier.init(((Long) cached.first).longValue(), ((Long) cached.second).longValue());
            j = ((Long) cached.first).longValue();
            Cache cache2 = cache;
        } else {
            j = getRequestLength(dataSpec2, cache, buildCacheKey);
            progressNotifier = null;
        }
        ProgressNotifier progressNotifier2 = progressNotifier;
        long j3 = dataSpec2.absoluteStreamPosition;
        boolean z2 = j == -1;
        long j4 = j;
        long j5 = j3;
        while (j4 != 0) {
            throwExceptionIfInterruptedOrCancelled(atomicBoolean);
            long cachedLength = cache.getCachedLength(buildCacheKey, j5, z2 ? Long.MAX_VALUE : j4);
            if (cachedLength > 0) {
                j2 = cachedLength;
            } else {
                long j6 = -cachedLength;
                long j7 = j6 == Long.MAX_VALUE ? -1 : j6;
                j2 = j6;
                if (readAndDiscard(dataSpec, j5, j7, cacheDataSource, bArr, priorityTaskManager, i, progressNotifier2, j7 == j4, atomicBoolean) < j2) {
                    if (z && !z2) {
                        throw new EOFException();
                    }
                    return;
                }
            }
            j5 += j2;
            if (!z2) {
                j4 -= j2;
            }
        }
    }

    private static long getRequestLength(DataSpec dataSpec, Cache cache, String str) {
        long j = -1;
        if (dataSpec.length != -1) {
            return dataSpec.length;
        }
        long contentLength = CC.getContentLength(cache.getContentMetadata(str));
        if (contentLength != -1) {
            j = contentLength - dataSpec.absoluteStreamPosition;
        }
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0040, code lost:
        r14 = r7;
        r0 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long readAndDiscard(com.google.android.exoplayer2.upstream.DataSpec r16, long r17, long r19, com.google.android.exoplayer2.upstream.DataSource r21, byte[] r22, com.google.android.exoplayer2.util.PriorityTaskManager r23, int r24, com.google.android.exoplayer2.upstream.cache.CacheUtil.ProgressNotifier r25, boolean r26, java.util.concurrent.atomic.AtomicBoolean r27) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r1 = r16
            r2 = r21
            r3 = r22
            r4 = r25
            long r5 = r1.absoluteStreamPosition
            long r5 = r17 - r5
            r7 = -1
            int r0 = (r19 > r7 ? 1 : (r19 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x0015
            long r9 = r5 + r19
            goto L_0x0016
        L_0x0015:
            r9 = r7
        L_0x0016:
            r11 = r5
        L_0x0017:
            if (r23 == 0) goto L_0x001c
            r23.proceed(r24)
        L_0x001c:
            throwExceptionIfInterruptedOrCancelled(r27)
            r13 = 0
            int r0 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x0040
            long r14 = r9 - r11
            com.google.android.exoplayer2.upstream.DataSpec r0 = r1.subrange(r11, r14)     // Catch:{ IOException -> 0x0032 }
            long r14 = r2.open(r0)     // Catch:{ IOException -> 0x0032 }
            r0 = 1
            goto L_0x0042
        L_0x0030:
            r0 = move-exception
            goto L_0x0085
        L_0x0032:
            r0 = move-exception
            if (r26 == 0) goto L_0x003f
            boolean r14 = isCausedByPositionOutOfRange(r0)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            if (r14 == 0) goto L_0x003f
            com.google.android.exoplayer2.util.Util.closeQuietly(r21)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            goto L_0x0040
        L_0x003f:
            throw r0     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
        L_0x0040:
            r14 = r7
            r0 = 0
        L_0x0042:
            if (r0 != 0) goto L_0x004c
            com.google.android.exoplayer2.upstream.DataSpec r0 = r1.subrange(r11, r7)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            long r14 = r2.open(r0)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
        L_0x004c:
            if (r26 == 0) goto L_0x0058
            if (r4 == 0) goto L_0x0058
            int r0 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x0058
            long r14 = r14 + r11
            r4.onRequestLengthResolved(r14)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
        L_0x0058:
            int r0 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r0 == 0) goto L_0x008f
            throwExceptionIfInterruptedOrCancelled(r27)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            int r0 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x006d
            int r0 = r3.length     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            long r14 = (long) r0     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            long r7 = r9 - r11
            long r7 = java.lang.Math.min(r14, r7)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            int r0 = (int) r7     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            goto L_0x006e
        L_0x006d:
            int r0 = r3.length     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
        L_0x006e:
            int r0 = r2.read(r3, r13, r0)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            r7 = -1
            if (r0 != r7) goto L_0x007b
            if (r4 == 0) goto L_0x008f
            r4.onRequestLengthResolved(r11)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            goto L_0x008f
        L_0x007b:
            long r7 = (long) r0     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
            long r11 = r11 + r7
            if (r4 == 0) goto L_0x0082
            r4.onBytesCached(r7)     // Catch:{ PriorityTooLowException -> 0x0089, all -> 0x0030 }
        L_0x0082:
            r7 = -1
            goto L_0x0058
        L_0x0085:
            com.google.android.exoplayer2.util.Util.closeQuietly(r21)
            throw r0
        L_0x0089:
            com.google.android.exoplayer2.util.Util.closeQuietly(r21)
            r7 = -1
            goto L_0x0017
        L_0x008f:
            long r11 = r11 - r5
            com.google.android.exoplayer2.util.Util.closeQuietly(r21)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheUtil.readAndDiscard(com.google.android.exoplayer2.upstream.DataSpec, long, long, com.google.android.exoplayer2.upstream.DataSource, byte[], com.google.android.exoplayer2.util.PriorityTaskManager, int, com.google.android.exoplayer2.upstream.cache.CacheUtil$ProgressNotifier, boolean, java.util.concurrent.atomic.AtomicBoolean):long");
    }

    public static void remove(DataSpec dataSpec, Cache cache, CacheKeyFactory cacheKeyFactory) {
        remove(cache, buildCacheKey(dataSpec, cacheKeyFactory));
    }

    public static void remove(Cache cache, String str) {
        for (CacheSpan removeSpan : cache.getCachedSpans(str)) {
            try {
                cache.removeSpan(removeSpan);
            } catch (CacheException unused) {
            }
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.io.IOException, code=java.lang.Throwable, for r1v0, types: [java.lang.Throwable, java.io.IOException] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean isCausedByPositionOutOfRange(java.lang.Throwable r1) {
        /*
        L_0x0000:
            if (r1 == 0) goto L_0x0014
            boolean r0 = r1 instanceof com.google.android.exoplayer2.upstream.DataSourceException
            if (r0 == 0) goto L_0x000f
            r0 = r1
            com.google.android.exoplayer2.upstream.DataSourceException r0 = (com.google.android.exoplayer2.upstream.DataSourceException) r0
            int r0 = r0.reason
            if (r0 != 0) goto L_0x000f
            r1 = 1
            return r1
        L_0x000f:
            java.lang.Throwable r1 = r1.getCause()
            goto L_0x0000
        L_0x0014:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheUtil.isCausedByPositionOutOfRange(java.io.IOException):boolean");
    }

    private static String buildCacheKey(DataSpec dataSpec, CacheKeyFactory cacheKeyFactory) {
        if (cacheKeyFactory == null) {
            cacheKeyFactory = DEFAULT_CACHE_KEY_FACTORY;
        }
        return cacheKeyFactory.buildCacheKey(dataSpec);
    }

    private static void throwExceptionIfInterruptedOrCancelled(AtomicBoolean atomicBoolean) throws InterruptedException {
        if (Thread.interrupted() || (atomicBoolean != null && atomicBoolean.get())) {
            throw new InterruptedException();
        }
    }

    private CacheUtil() {
    }
}
