package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager.CC;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

public final class HlsMediaSource extends BaseMediaSource implements PrimaryPlaylistListener {
    private final boolean allowChunklessPreparation;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionManager<?> drmSessionManager;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Uri manifestUri;
    private TransferListener mediaTransferListener;
    private final int metadataType;
    private final HlsPlaylistTracker playlistTracker;
    private final Object tag;
    private final boolean useSessionKeys;

    public static final class Factory implements MediaSourceFactory {
        private boolean allowChunklessPreparation;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManager<?> drmSessionManager;
        private HlsExtractorFactory extractorFactory;
        private final HlsDataSourceFactory hlsDataSourceFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private int metadataType;
        private HlsPlaylistParserFactory playlistParserFactory;
        private com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory playlistTrackerFactory;
        private List<StreamKey> streamKeys;
        private Object tag;
        private boolean useSessionKeys;

        public int[] getSupportedTypes() {
            return new int[]{2};
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this((HlsDataSourceFactory) new DefaultHlsDataSourceFactory(factory));
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory2) {
            this.hlsDataSourceFactory = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory2);
            this.playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            this.playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            this.extractorFactory = HlsExtractorFactory.DEFAULT;
            this.drmSessionManager = CC.getDummyDrmSessionManager();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            this.metadataType = 1;
        }

        public Factory setTag(Object obj) {
            Assertions.checkState(!this.isCreateCalled);
            this.tag = obj;
            return this;
        }

        public Factory setExtractorFactory(HlsExtractorFactory hlsExtractorFactory) {
            Assertions.checkState(!this.isCreateCalled);
            this.extractorFactory = (HlsExtractorFactory) Assertions.checkNotNull(hlsExtractorFactory);
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

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy(i);
            return this;
        }

        public Factory setPlaylistParserFactory(HlsPlaylistParserFactory hlsPlaylistParserFactory) {
            Assertions.checkState(!this.isCreateCalled);
            this.playlistParserFactory = (HlsPlaylistParserFactory) Assertions.checkNotNull(hlsPlaylistParserFactory);
            return this;
        }

        public Factory setPlaylistTrackerFactory(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory factory) {
            Assertions.checkState(!this.isCreateCalled);
            this.playlistTrackerFactory = (com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory) Assertions.checkNotNull(factory);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory2);
            return this;
        }

        public Factory setAllowChunklessPreparation(boolean z) {
            Assertions.checkState(!this.isCreateCalled);
            this.allowChunklessPreparation = z;
            return this;
        }

        public Factory setMetadataType(int i) {
            Assertions.checkState(!this.isCreateCalled);
            this.metadataType = i;
            return this;
        }

        public Factory setUseSessionKeys(boolean z) {
            this.useSessionKeys = z;
            return this;
        }

        @Deprecated
        public HlsMediaSource createMediaSource(Uri uri, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            HlsMediaSource createMediaSource = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }

        public HlsMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.playlistParserFactory = new FilteringHlsPlaylistParserFactory(this.playlistParserFactory, list);
            }
            HlsDataSourceFactory hlsDataSourceFactory2 = this.hlsDataSourceFactory;
            HlsExtractorFactory hlsExtractorFactory = this.extractorFactory;
            CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2 = this.compositeSequenceableLoaderFactory;
            DrmSessionManager<?> drmSessionManager2 = this.drmSessionManager;
            LoadErrorHandlingPolicy loadErrorHandlingPolicy2 = this.loadErrorHandlingPolicy;
            HlsMediaSource hlsMediaSource = new HlsMediaSource(uri, hlsDataSourceFactory2, hlsExtractorFactory, compositeSequenceableLoaderFactory2, drmSessionManager2, loadErrorHandlingPolicy2, this.playlistTrackerFactory.createTracker(hlsDataSourceFactory2, loadErrorHandlingPolicy2, this.playlistParserFactory), this.allowChunklessPreparation, this.metadataType, this.useSessionKeys, this.tag);
            return hlsMediaSource;
        }

        public Factory setStreamKeys(List<StreamKey> list) {
            Assertions.checkState(!this.isCreateCalled);
            this.streamKeys = list;
            return this;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    private HlsMediaSource(Uri uri, HlsDataSourceFactory hlsDataSourceFactory, HlsExtractorFactory hlsExtractorFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, HlsPlaylistTracker hlsPlaylistTracker, boolean z, int i, boolean z2, Object obj) {
        this.manifestUri = uri;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.extractorFactory = hlsExtractorFactory;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.drmSessionManager = drmSessionManager2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.playlistTracker = hlsPlaylistTracker;
        this.allowChunklessPreparation = z;
        this.metadataType = i;
        this.useSessionKeys = z2;
        this.tag = obj;
    }

    public Object getTag() {
        return this.tag;
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.prepare();
        this.playlistTracker.start(this.manifestUri, createEventDispatcher(null), this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        HlsMediaPeriod hlsMediaPeriod = new HlsMediaPeriod(this.extractorFactory, this.playlistTracker, this.dataSourceFactory, this.mediaTransferListener, this.drmSessionManager, this.loadErrorHandlingPolicy, createEventDispatcher(mediaPeriodId), allocator, this.compositeSequenceableLoaderFactory, this.allowChunklessPreparation, this.metadataType, this.useSessionKeys);
        return hlsMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
        this.playlistTracker.stop();
        this.drmSessionManager.release();
    }

    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist hlsMediaPlaylist) {
        SinglePeriodTimeline singlePeriodTimeline;
        long j;
        HlsMediaPlaylist hlsMediaPlaylist2 = hlsMediaPlaylist;
        long usToMs = hlsMediaPlaylist2.hasProgramDateTime ? C1996C.usToMs(hlsMediaPlaylist2.startTimeUs) : -9223372036854775807L;
        long j2 = (hlsMediaPlaylist2.playlistType == 2 || hlsMediaPlaylist2.playlistType == 1) ? usToMs : -9223372036854775807L;
        long j3 = hlsMediaPlaylist2.startOffsetUs;
        HlsManifest hlsManifest = new HlsManifest((HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist()), hlsMediaPlaylist2);
        if (this.playlistTracker.isLive()) {
            long initialStartTimeUs = hlsMediaPlaylist2.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
            long j4 = hlsMediaPlaylist2.hasEndTag ? initialStartTimeUs + hlsMediaPlaylist2.durationUs : -9223372036854775807L;
            List<Segment> list = hlsMediaPlaylist2.segments;
            if (j3 != C1996C.TIME_UNSET) {
                j = j3;
            } else if (!list.isEmpty()) {
                int max = Math.max(0, list.size() - 3);
                long j5 = hlsMediaPlaylist2.durationUs - (hlsMediaPlaylist2.targetDurationUs * 2);
                while (max > 0 && ((Segment) list.get(max)).relativeStartTimeUs > j5) {
                    max--;
                }
                j = ((Segment) list.get(max)).relativeStartTimeUs;
            } else {
                j = 0;
            }
            SinglePeriodTimeline singlePeriodTimeline2 = new SinglePeriodTimeline(j2, usToMs, j4, hlsMediaPlaylist2.durationUs, initialStartTimeUs, j, true, !hlsMediaPlaylist2.hasEndTag, true, hlsManifest, this.tag);
            singlePeriodTimeline = singlePeriodTimeline2;
        } else {
            singlePeriodTimeline = new SinglePeriodTimeline(j2, usToMs, hlsMediaPlaylist2.durationUs, hlsMediaPlaylist2.durationUs, 0, j3 == C1996C.TIME_UNSET ? 0 : j3, true, false, false, hlsManifest, this.tag);
        }
        refreshSourceInfo(singlePeriodTimeline);
    }
}
