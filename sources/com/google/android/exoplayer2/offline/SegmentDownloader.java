package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.util.Pair;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.upstream.cache.CacheUtil.ProgressListener;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    private static final int BUFFER_SIZE_BYTES = 131072;
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final CacheDataSource dataSource;
    private final AtomicBoolean isCanceled = new AtomicBoolean();
    private final DataSpec manifestDataSpec;
    private final CacheDataSource offlineDataSource;
    private final PriorityTaskManager priorityTaskManager;
    private final ArrayList<StreamKey> streamKeys;

    private static final class ProgressNotifier implements ProgressListener {
        private long bytesDownloaded;
        private final long contentLength;
        private final Downloader.ProgressListener progressListener;
        private int segmentsDownloaded;
        private final int totalSegments;

        public ProgressNotifier(Downloader.ProgressListener progressListener2, long j, int i, long j2, int i2) {
            this.progressListener = progressListener2;
            this.contentLength = j;
            this.totalSegments = i;
            this.bytesDownloaded = j2;
            this.segmentsDownloaded = i2;
        }

        public void onProgress(long j, long j2, long j3) {
            this.bytesDownloaded += j3;
            this.progressListener.onProgress(this.contentLength, this.bytesDownloaded, getPercentDownloaded());
        }

        public void onSegmentDownloaded() {
            this.segmentsDownloaded++;
            this.progressListener.onProgress(this.contentLength, this.bytesDownloaded, getPercentDownloaded());
        }

        private float getPercentDownloaded() {
            long j = this.contentLength;
            if (j != -1 && j != 0) {
                return (((float) this.bytesDownloaded) * 100.0f) / ((float) j);
            }
            int i = this.totalSegments;
            if (i != 0) {
                return (((float) this.segmentsDownloaded) * 100.0f) / ((float) i);
            }
            return -1.0f;
        }
    }

    protected static class Segment implements Comparable<Segment> {
        public final DataSpec dataSpec;
        public final long startTimeUs;

        public Segment(long j, DataSpec dataSpec2) {
            this.startTimeUs = j;
            this.dataSpec = dataSpec2;
        }

        public int compareTo(Segment segment) {
            return Util.compareLong(this.startTimeUs, segment.startTimeUs);
        }
    }

    /* access modifiers changed from: protected */
    public abstract M getManifest(DataSource dataSource2, DataSpec dataSpec) throws IOException;

    /* access modifiers changed from: protected */
    public abstract List<Segment> getSegments(DataSource dataSource2, M m, boolean z) throws InterruptedException, IOException;

    public SegmentDownloader(Uri uri, List<StreamKey> list, DownloaderConstructorHelper downloaderConstructorHelper) {
        this.manifestDataSpec = getCompressibleDataSpec(uri);
        this.streamKeys = new ArrayList<>(list);
        this.cache = downloaderConstructorHelper.getCache();
        this.dataSource = downloaderConstructorHelper.createCacheDataSource();
        this.offlineDataSource = downloaderConstructorHelper.createOfflineCacheDataSource();
        this.cacheKeyFactory = downloaderConstructorHelper.getCacheKeyFactory();
        this.priorityTaskManager = downloaderConstructorHelper.getPriorityTaskManager();
    }

    public final void download(Downloader.ProgressListener progressListener) throws IOException, InterruptedException {
        this.priorityTaskManager.add(-1000);
        try {
            FilterableManifest manifest = getManifest(this.dataSource, this.manifestDataSpec);
            if (!this.streamKeys.isEmpty()) {
                manifest = (FilterableManifest) manifest.copy(this.streamKeys);
            }
            List segments = getSegments(this.dataSource, manifest, false);
            int size = segments.size();
            long j = 0;
            long j2 = 0;
            int i = 0;
            for (int size2 = segments.size() - 1; size2 >= 0; size2--) {
                Pair cached = CacheUtil.getCached(((Segment) segments.get(size2)).dataSpec, this.cache, this.cacheKeyFactory);
                long longValue = ((Long) cached.first).longValue();
                long longValue2 = ((Long) cached.second).longValue();
                j2 += longValue2;
                if (longValue != -1) {
                    if (longValue == longValue2) {
                        i++;
                        segments.remove(size2);
                    }
                    if (j != -1) {
                        j += longValue;
                    }
                } else {
                    j = -1;
                }
            }
            Collections.sort(segments);
            ProgressNotifier progressNotifier = null;
            if (progressListener != null) {
                progressNotifier = new ProgressNotifier(progressListener, j, size, j2, i);
            }
            byte[] bArr = new byte[131072];
            for (int i2 = 0; i2 < segments.size(); i2++) {
                CacheUtil.cache(((Segment) segments.get(i2)).dataSpec, this.cache, this.cacheKeyFactory, this.dataSource, bArr, this.priorityTaskManager, -1000, progressNotifier, this.isCanceled, true);
                if (progressNotifier != null) {
                    progressNotifier.onSegmentDownloaded();
                }
            }
        } finally {
            this.priorityTaskManager.remove(-1000);
        }
    }

    public void cancel() {
        this.isCanceled.set(true);
    }

    public final void remove() throws InterruptedException {
        try {
            List segments = getSegments(this.offlineDataSource, getManifest(this.offlineDataSource, this.manifestDataSpec), true);
            for (int i = 0; i < segments.size(); i++) {
                removeDataSpec(((Segment) segments.get(i)).dataSpec);
            }
        } catch (IOException unused) {
        } catch (Throwable th) {
            removeDataSpec(this.manifestDataSpec);
            throw th;
        }
        removeDataSpec(this.manifestDataSpec);
    }

    private void removeDataSpec(DataSpec dataSpec) {
        CacheUtil.remove(dataSpec, this.cache, this.cacheKeyFactory);
    }

    protected static DataSpec getCompressibleDataSpec(Uri uri) {
        DataSpec dataSpec = new DataSpec(uri, 0, -1, null, 1);
        return dataSpec;
    }
}
