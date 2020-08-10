package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;

@Deprecated
public final class ExoPlayerFactory {
    private ExoPlayerFactory() {
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i) {
        return newSimpleInstance(context, (RenderersFactory) new DefaultRenderersFactory(context).setExtensionRendererMode(i), trackSelector, loadControl, drmSessionManager);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int i, long j) {
        return newSimpleInstance(context, (RenderersFactory) new DefaultRenderersFactory(context).setExtensionRendererMode(i).setAllowedVideoJoiningTimeMs(j), trackSelector, loadControl, drmSessionManager);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context) {
        return newSimpleInstance(context, new DefaultTrackSelector(context));
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector) {
        return newSimpleInstance(context, (RenderersFactory) new DefaultRenderersFactory(context), trackSelector);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector) {
        return newSimpleInstance(context, renderersFactory, trackSelector, (LoadControl) new DefaultLoadControl());
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl) {
        return newSimpleInstance(context, (RenderersFactory) new DefaultRenderersFactory(context), trackSelector, loadControl);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, (RenderersFactory) new DefaultRenderersFactory(context), trackSelector, loadControl, drmSessionManager);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, renderersFactory, trackSelector, (LoadControl) new DefaultLoadControl(), drmSessionManager);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, null, Util.getLooper());
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, Util.getLooper());
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, new AnalyticsCollector(Clock.DEFAULT), Util.getLooper());
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, AnalyticsCollector analyticsCollector) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, analyticsCollector, Util.getLooper());
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Looper looper) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, new AnalyticsCollector(Clock.DEFAULT), looper);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, AnalyticsCollector analyticsCollector, Looper looper) {
        return newSimpleInstance(context, renderersFactory, trackSelector, loadControl, drmSessionManager, DefaultBandwidthMeter.getSingletonInstance(context), analyticsCollector, looper);
    }

    @Deprecated
    public static SimpleExoPlayer newSimpleInstance(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector, Looper looper) {
        SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer(context, renderersFactory, trackSelector, loadControl, drmSessionManager, bandwidthMeter, analyticsCollector, Clock.DEFAULT, looper);
        return simpleExoPlayer;
    }

    @Deprecated
    public static ExoPlayer newInstance(Context context, Renderer[] rendererArr, TrackSelector trackSelector) {
        return newInstance(context, rendererArr, trackSelector, new DefaultLoadControl());
    }

    @Deprecated
    public static ExoPlayer newInstance(Context context, Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl) {
        return newInstance(context, rendererArr, trackSelector, loadControl, Util.getLooper());
    }

    @Deprecated
    public static ExoPlayer newInstance(Context context, Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl, Looper looper) {
        return newInstance(context, rendererArr, trackSelector, loadControl, DefaultBandwidthMeter.getSingletonInstance(context), looper);
    }

    @Deprecated
    public static ExoPlayer newInstance(Context context, Renderer[] rendererArr, TrackSelector trackSelector, LoadControl loadControl, BandwidthMeter bandwidthMeter, Looper looper) {
        ExoPlayerImpl exoPlayerImpl = new ExoPlayerImpl(rendererArr, trackSelector, loadControl, bandwidthMeter, Clock.DEFAULT, looper);
        return exoPlayerImpl;
    }
}
