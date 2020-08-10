package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener.CC;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

@Deprecated
public final class ExtractorMediaSource extends CompositeMediaSource<Void> {
    @Deprecated
    public static final int DEFAULT_LOADING_CHECK_INTERVAL_BYTES = 1048576;
    private final ProgressiveMediaSource progressiveMediaSource;

    @Deprecated
    public interface EventListener {
        void onLoadError(IOException iOException);
    }

    @Deprecated
    private static final class EventListenerWrapper implements MediaSourceEventListener {
        private final EventListener eventListener;

        public /* synthetic */ void onDownstreamFormatChanged(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            CC.$default$onDownstreamFormatChanged(this, i, mediaPeriodId, mediaLoadData);
        }

        public /* synthetic */ void onLoadCanceled(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            CC.$default$onLoadCanceled(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public /* synthetic */ void onLoadCompleted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            CC.$default$onLoadCompleted(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public /* synthetic */ void onLoadStarted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            CC.$default$onLoadStarted(this, i, mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        public /* synthetic */ void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
            CC.$default$onMediaPeriodCreated(this, i, mediaPeriodId);
        }

        public /* synthetic */ void onMediaPeriodReleased(int i, MediaPeriodId mediaPeriodId) {
            CC.$default$onMediaPeriodReleased(this, i, mediaPeriodId);
        }

        public /* synthetic */ void onReadingStarted(int i, MediaPeriodId mediaPeriodId) {
            CC.$default$onReadingStarted(this, i, mediaPeriodId);
        }

        public /* synthetic */ void onUpstreamDiscarded(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            CC.$default$onUpstreamDiscarded(this, i, mediaPeriodId, mediaLoadData);
        }

        public EventListenerWrapper(EventListener eventListener2) {
            this.eventListener = (EventListener) Assertions.checkNotNull(eventListener2);
        }

        public void onLoadError(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
            this.eventListener.onLoadError(iOException);
        }
    }

    @Deprecated
    public static final class Factory implements MediaSourceFactory {
        private int continueLoadingCheckIntervalBytes = 1048576;
        private String customCacheKey;
        private final com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory;
        private ExtractorsFactory extractorsFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        private Object tag;

        public int[] getSupportedTypes() {
            return new int[]{3};
        }

        public /* synthetic */ MediaSourceFactory setStreamKeys(List<StreamKey> list) {
            return MediaSourceFactory.CC.$default$setStreamKeys(this, list);
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this.dataSourceFactory = factory;
        }

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

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            return setLoadErrorHandlingPolicy(new DefaultLoadErrorHandlingPolicy(i));
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

        public ExtractorMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            if (this.extractorsFactory == null) {
                this.extractorsFactory = new DefaultExtractorsFactory();
            }
            ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource(uri, this.dataSourceFactory, this.extractorsFactory, this.loadErrorHandlingPolicy, this.customCacheKey, this.continueLoadingCheckIntervalBytes, this.tag);
            return extractorMediaSource;
        }

        @Deprecated
        public ExtractorMediaSource createMediaSource(Uri uri, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            ExtractorMediaSource createMediaSource = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }
    }

    @Deprecated
    public ExtractorMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory, Handler handler, EventListener eventListener) {
        this(uri, factory, extractorsFactory, handler, eventListener, null);
    }

    @Deprecated
    public ExtractorMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory, Handler handler, EventListener eventListener, String str) {
        this(uri, factory, extractorsFactory, handler, eventListener, str, 1048576);
    }

    @Deprecated
    public ExtractorMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory, Handler handler, EventListener eventListener, String str, int i) {
        this(uri, factory, extractorsFactory, (LoadErrorHandlingPolicy) new DefaultLoadErrorHandlingPolicy(), str, i, (Object) null);
        if (eventListener != null && handler != null) {
            addEventListener(handler, new EventListenerWrapper(eventListener));
        }
    }

    private ExtractorMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, ExtractorsFactory extractorsFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, String str, int i, Object obj) {
        ProgressiveMediaSource progressiveMediaSource2 = new ProgressiveMediaSource(uri, factory, extractorsFactory, DrmSessionManager.CC.getDummyDrmSessionManager(), loadErrorHandlingPolicy, str, i, obj);
        this.progressiveMediaSource = progressiveMediaSource2;
    }

    public Object getTag() {
        return this.progressiveMediaSource.getTag();
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        prepareChildSource(null, this.progressiveMediaSource);
    }

    /* access modifiers changed from: protected */
    public void onChildSourceInfoRefreshed(Void voidR, MediaSource mediaSource, Timeline timeline) {
        refreshSourceInfo(timeline);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        return this.progressiveMediaSource.createPeriod(mediaPeriodId, allocator, j);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        this.progressiveMediaSource.releasePeriod(mediaPeriod);
    }
}
