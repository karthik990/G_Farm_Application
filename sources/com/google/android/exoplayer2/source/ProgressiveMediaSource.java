package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceFactory.CC;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

public final class ProgressiveMediaSource extends BaseMediaSource implements Listener {
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final int continueLoadingCheckIntervalBytes;
    private final String customCacheKey;
    private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
    private final DrmSessionManager<?> drmSessionManager;
    private final ExtractorsFactory extractorsFactory;
    private final LoadErrorHandlingPolicy loadableLoadErrorHandlingPolicy;
    private final Object tag;
    private long timelineDurationUs = C1996C.TIME_UNSET;
    private boolean timelineIsLive;
    private boolean timelineIsSeekable;
    private TransferListener transferListener;
    private final Uri uri;

    public static final class Factory implements MediaSourceFactory {
        private int continueLoadingCheckIntervalBytes;
        private String customCacheKey;
        private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        private DrmSessionManager<?> drmSessionManager;
        private ExtractorsFactory extractorsFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private Object tag;

        public int[] getSupportedTypes() {
            return new int[]{3};
        }

        public /* synthetic */ MediaSourceFactory setStreamKeys(List<StreamKey> list) {
            return CC.$default$setStreamKeys(this, list);
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(factory, new DefaultExtractorsFactory());
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory2) {
            this.dataSourceFactory = factory;
            this.extractorsFactory = extractorsFactory2;
            this.drmSessionManager = DrmSessionManager.CC.getDummyDrmSessionManager();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.continueLoadingCheckIntervalBytes = 1048576;
        }

        @Deprecated
        public Factory setExtractorsFactory(ExtractorsFactory extractorsFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.extractorsFactory = extractorsFactory2;
            return this;
        }

        public Factory setCustomCacheKey(String str) {
            Assertions.checkState(!this.isCreateCalled);
            this.customCacheKey = str;
            return this;
        }

        public Factory setTag(Object obj) {
            Assertions.checkState(!this.isCreateCalled);
            this.tag = obj;
            return this;
        }

        public Factory setDrmSessionManager(DrmSessionManager<?> drmSessionManager2) {
            Assertions.checkState(!this.isCreateCalled);
            this.drmSessionManager = drmSessionManager2;
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
            return this;
        }

        public Factory setContinueLoadingCheckIntervalBytes(int i) {
            Assertions.checkState(!this.isCreateCalled);
            this.continueLoadingCheckIntervalBytes = i;
            return this;
        }

        public ProgressiveMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            ProgressiveMediaSource progressiveMediaSource = new ProgressiveMediaSource(uri, this.dataSourceFactory, this.extractorsFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, this.customCacheKey, this.continueLoadingCheckIntervalBytes, this.tag);
            return progressiveMediaSource;
        }
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
    }

    ProgressiveMediaSource(Uri uri2, com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory2, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, String str, int i, Object obj) {
        this.uri = uri2;
        this.dataSourceFactory = factory;
        this.extractorsFactory = extractorsFactory2;
        this.drmSessionManager = drmSessionManager2;
        this.loadableLoadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.customCacheKey = str;
        this.continueLoadingCheckIntervalBytes = i;
        this.tag = obj;
    }

    public Object getTag() {
        return this.tag;
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener2) {
        this.transferListener = transferListener2;
        this.drmSessionManager.prepare();
        notifySourceInfoRefreshed(this.timelineDurationUs, this.timelineIsSeekable, this.timelineIsLive);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        DataSource createDataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener2 = this.transferListener;
        if (transferListener2 != null) {
            createDataSource.addTransferListener(transferListener2);
        }
        ProgressiveMediaPeriod progressiveMediaPeriod = new ProgressiveMediaPeriod(this.uri, createDataSource, this.extractorsFactory.createExtractors(), this.drmSessionManager, this.loadableLoadErrorHandlingPolicy, createEventDispatcher(mediaPeriodId), this, allocator, this.customCacheKey, this.continueLoadingCheckIntervalBytes);
        return progressiveMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((ProgressiveMediaPeriod) mediaPeriod).release();
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
        this.drmSessionManager.release();
    }

    public void onSourceInfoRefreshed(long j, boolean z, boolean z2) {
        if (j == C1996C.TIME_UNSET) {
            j = this.timelineDurationUs;
        }
        if (this.timelineDurationUs != j || this.timelineIsSeekable != z || this.timelineIsLive != z2) {
            notifySourceInfoRefreshed(j, z, z2);
        }
    }

    private void notifySourceInfoRefreshed(long j, boolean z, boolean z2) {
        this.timelineDurationUs = j;
        this.timelineIsSeekable = z;
        this.timelineIsLive = z2;
        SinglePeriodTimeline singlePeriodTimeline = new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.timelineIsLive, null, this.tag);
        refreshSourceInfo(singlePeriodTimeline);
    }
}
