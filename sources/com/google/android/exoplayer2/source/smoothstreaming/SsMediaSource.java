package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager.CC;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest.StreamElement;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsUtil;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.Callback;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower.Dummy;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SsMediaSource extends BaseMediaSource implements Callback<ParsingLoadable<SsManifest>> {
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    private static final int MINIMUM_MANIFEST_REFRESH_PERIOD_MS = 5000;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private final com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionManager<?> drmSessionManager;
    private final long livePresentationDelayMs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private DataSource manifestDataSource;
    private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
    private final EventDispatcher manifestEventDispatcher;
    private long manifestLoadStartTimestamp;
    private Loader manifestLoader;
    private LoaderErrorThrower manifestLoaderErrorThrower;
    private final Parser<? extends SsManifest> manifestParser;
    private Handler manifestRefreshHandler;
    private final Uri manifestUri;
    private final ArrayList<SsMediaPeriod> mediaPeriods;
    private TransferListener mediaTransferListener;
    private final boolean sideloadedManifest;
    private final Object tag;

    public static final class Factory implements MediaSourceFactory {
        private final com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManager<?> drmSessionManager;
        private boolean isCreateCalled;
        private long livePresentationDelayMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
        private Parser<? extends SsManifest> manifestParser;
        private List<StreamKey> streamKeys;
        private Object tag;

        public int[] getSupportedTypes() {
            return new int[]{1};
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(new com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource.Factory(factory), factory);
        }

        public Factory(com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory, com.google.android.exoplayer2.upstream.DataSource.Factory factory2) {
            this.chunkSourceFactory = (com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory) Assertions.checkNotNull(factory);
            this.manifestDataSourceFactory = factory2;
            this.drmSessionManager = CC.getDummyDrmSessionManager();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.livePresentationDelayMs = 30000;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
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

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            return setLoadErrorHandlingPolicy(new DefaultLoadErrorHandlingPolicy(i));
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
            return this;
        }

        public Factory setLivePresentationDelayMs(long j) {
            Assertions.checkState(!this.isCreateCalled);
            this.livePresentationDelayMs = j;
            return this;
        }

        public Factory setManifestParser(Parser<? extends SsManifest> parser) {
            Assertions.checkState(!this.isCreateCalled);
            this.manifestParser = (Parser) Assertions.checkNotNull(parser);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory2);
            return this;
        }

        public SsMediaSource createMediaSource(SsManifest ssManifest) {
            Assertions.checkArgument(!ssManifest.isLive);
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null && !list.isEmpty()) {
                ssManifest = ssManifest.copy(this.streamKeys);
            }
            SsMediaSource ssMediaSource = new SsMediaSource(ssManifest, null, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.tag);
            return ssMediaSource;
        }

        @Deprecated
        public SsMediaSource createMediaSource(SsManifest ssManifest, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            SsMediaSource createMediaSource = createMediaSource(ssManifest);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }

        @Deprecated
        public SsMediaSource createMediaSource(Uri uri, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            SsMediaSource createMediaSource = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }

        public SsMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            if (this.manifestParser == null) {
                this.manifestParser = new SsManifestParser();
            }
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.manifestParser = new FilteringManifestParser(this.manifestParser, list);
            }
            SsMediaSource ssMediaSource = new SsMediaSource(null, (Uri) Assertions.checkNotNull(uri), this.manifestDataSourceFactory, this.manifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.tag);
            return ssMediaSource;
        }

        public Factory setStreamKeys(List<StreamKey> list) {
            Assertions.checkState(!this.isCreateCalled);
            this.streamKeys = list;
            return this;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.smoothstreaming");
    }

    @Deprecated
    public SsMediaSource(SsManifest ssManifest, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(ssManifest, factory, 3, handler, mediaSourceEventListener);
    }

    @Deprecated
    public SsMediaSource(SsManifest ssManifest, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory, int i, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler2 = handler;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(ssManifest, null, null, null, factory, new DefaultCompositeSequenceableLoaderFactory(), CC.getDummyDrmSessionManager(), new DefaultLoadErrorHandlingPolicy(i), 30000, null);
        if (handler2 == null || mediaSourceEventListener2 == null) {
            return;
        }
        addEventListener(handler2, mediaSourceEventListener2);
    }

    @Deprecated
    public SsMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory2, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, factory2, 3, 30000, handler, mediaSourceEventListener);
    }

    @Deprecated
    public SsMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory2, int i, long j, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, new SsManifestParser(), factory2, i, j, handler, mediaSourceEventListener);
    }

    @Deprecated
    public SsMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends SsManifest> parser, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory2, int i, long j, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler2 = handler;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(null, uri, factory, parser, factory2, new DefaultCompositeSequenceableLoaderFactory(), CC.getDummyDrmSessionManager(), new DefaultLoadErrorHandlingPolicy(i), j, null);
        if (handler2 == null || mediaSourceEventListener2 == null) {
            return;
        }
        addEventListener(handler2, mediaSourceEventListener2);
    }

    private SsMediaSource(SsManifest ssManifest, Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends SsManifest> parser, com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, long j, Object obj) {
        Uri uri2;
        boolean z = false;
        Assertions.checkState(ssManifest == null || !ssManifest.isLive);
        this.manifest = ssManifest;
        if (uri == null) {
            uri2 = null;
        } else {
            uri2 = SsUtil.fixManifestUri(uri);
        }
        this.manifestUri = uri2;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.drmSessionManager = drmSessionManager2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.livePresentationDelayMs = j;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.tag = obj;
        if (ssManifest != null) {
            z = true;
        }
        this.sideloadedManifest = z;
        this.mediaPeriods = new ArrayList<>();
    }

    public Object getTag() {
        return this.tag;
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.prepare();
        if (this.sideloadedManifest) {
            this.manifestLoaderErrorThrower = new Dummy();
            processManifest();
            return;
        }
        this.manifestDataSource = this.manifestDataSourceFactory.createDataSource();
        this.manifestLoader = new Loader("Loader:Manifest");
        this.manifestLoaderErrorThrower = this.manifestLoader;
        this.manifestRefreshHandler = new Handler();
        startLoadingManifest();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        SsMediaPeriod ssMediaPeriod = new SsMediaPeriod(this.manifest, this.chunkSourceFactory, this.mediaTransferListener, this.compositeSequenceableLoaderFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, createEventDispatcher(mediaPeriodId), this.manifestLoaderErrorThrower, allocator);
        this.mediaPeriods.add(ssMediaPeriod);
        return ssMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((SsMediaPeriod) mediaPeriod).release();
        this.mediaPeriods.remove(mediaPeriod);
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestDataSource = null;
        this.manifestLoadStartTimestamp = 0;
        Loader loader = this.manifestLoader;
        if (loader != null) {
            loader.release();
            this.manifestLoader = null;
        }
        Handler handler = this.manifestRefreshHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.manifestRefreshHandler = null;
        }
        this.drmSessionManager.release();
    }

    public void onLoadCompleted(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2) {
        ParsingLoadable<SsManifest> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
        this.manifest = (SsManifest) parsingLoadable.getResult();
        this.manifestLoadStartTimestamp = j - j2;
        processManifest();
        scheduleManifestRefresh();
    }

    public void onLoadCanceled(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2, boolean z) {
        ParsingLoadable<SsManifest> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCanceled(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
    }

    public LoadErrorAction onLoadError(ParsingLoadable<SsManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        LoadErrorAction loadErrorAction;
        ParsingLoadable<SsManifest> parsingLoadable2 = parsingLoadable;
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(4, j2, iOException, i);
        if (retryDelayMsFor == C1996C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        this.manifestEventDispatcher.loadError(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded(), iOException, !loadErrorAction.isRetry());
        return loadErrorAction;
    }

    private void processManifest() {
        StreamElement[] streamElementArr;
        SinglePeriodTimeline singlePeriodTimeline;
        for (int i = 0; i < this.mediaPeriods.size(); i++) {
            ((SsMediaPeriod) this.mediaPeriods.get(i)).updateManifest(this.manifest);
        }
        long j = Long.MIN_VALUE;
        long j2 = Long.MAX_VALUE;
        for (StreamElement streamElement : this.manifest.streamElements) {
            if (streamElement.chunkCount > 0) {
                long min = Math.min(j2, streamElement.getStartTimeUs(0));
                j = Math.max(j, streamElement.getStartTimeUs(streamElement.chunkCount - 1) + streamElement.getChunkDurationUs(streamElement.chunkCount - 1));
                j2 = min;
            }
        }
        if (j2 == Long.MAX_VALUE) {
            singlePeriodTimeline = new SinglePeriodTimeline(this.manifest.isLive ? -9223372036854775807L : 0, 0, 0, 0, true, this.manifest.isLive, this.manifest.isLive, this.manifest, this.tag);
        } else if (this.manifest.isLive) {
            if (this.manifest.dvrWindowLengthUs != C1996C.TIME_UNSET && this.manifest.dvrWindowLengthUs > 0) {
                j2 = Math.max(j2, j - this.manifest.dvrWindowLengthUs);
            }
            long j3 = j2;
            long j4 = j - j3;
            long msToUs = j4 - C1996C.msToUs(this.livePresentationDelayMs);
            if (msToUs < MIN_LIVE_DEFAULT_START_POSITION_US) {
                msToUs = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j4 / 2);
            }
            singlePeriodTimeline = new SinglePeriodTimeline(C1996C.TIME_UNSET, j4, j3, msToUs, true, true, true, this.manifest, this.tag);
        } else {
            long j5 = this.manifest.durationUs != C1996C.TIME_UNSET ? this.manifest.durationUs : j - j2;
            singlePeriodTimeline = new SinglePeriodTimeline(j2 + j5, j5, j2, 0, true, false, false, this.manifest, this.tag);
        }
        refreshSourceInfo(singlePeriodTimeline);
    }

    private void scheduleManifestRefresh() {
        if (this.manifest.isLive) {
            this.manifestRefreshHandler.postDelayed(new Runnable() {
                public final void run() {
                    SsMediaSource.this.startLoadingManifest();
                }
            }, Math.max(0, (this.manifestLoadStartTimestamp + DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) - SystemClock.elapsedRealtime()));
        }
    }

    /* access modifiers changed from: private */
    public void startLoadingManifest() {
        if (!this.manifestLoader.hasFatalError()) {
            ParsingLoadable parsingLoadable = new ParsingLoadable(this.manifestDataSource, this.manifestUri, 4, this.manifestParser);
            this.manifestEventDispatcher.loadStarted(parsingLoadable.dataSpec, parsingLoadable.type, this.manifestLoader.startLoading(parsingLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable.type)));
        }
    }
}
