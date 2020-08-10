package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import com.google.android.exoplayer2.PlayerMessage.Target;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;

public interface ExoPlayer extends Player {

    public static final class Builder {
        private AnalyticsCollector analyticsCollector;
        private BandwidthMeter bandwidthMeter;
        private boolean buildCalled;
        private Clock clock;
        private LoadControl loadControl;
        private Looper looper;
        private final Renderer[] renderers;
        private TrackSelector trackSelector;
        private boolean useLazyPreparation;

        public Builder(Context context, Renderer... rendererArr) {
            this(rendererArr, new DefaultTrackSelector(context), new DefaultLoadControl(), DefaultBandwidthMeter.getSingletonInstance(context), Util.getLooper(), new AnalyticsCollector(Clock.DEFAULT), true, Clock.DEFAULT);
        }

        public Builder(Renderer[] rendererArr, TrackSelector trackSelector2, LoadControl loadControl2, BandwidthMeter bandwidthMeter2, Looper looper2, AnalyticsCollector analyticsCollector2, boolean z, Clock clock2) {
            Assertions.checkArgument(rendererArr.length > 0);
            this.renderers = rendererArr;
            this.trackSelector = trackSelector2;
            this.loadControl = loadControl2;
            this.bandwidthMeter = bandwidthMeter2;
            this.looper = looper2;
            this.analyticsCollector = analyticsCollector2;
            this.useLazyPreparation = z;
            this.clock = clock2;
        }

        public Builder setTrackSelector(TrackSelector trackSelector2) {
            Assertions.checkState(!this.buildCalled);
            this.trackSelector = trackSelector2;
            return this;
        }

        public Builder setLoadControl(LoadControl loadControl2) {
            Assertions.checkState(!this.buildCalled);
            this.loadControl = loadControl2;
            return this;
        }

        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter2) {
            Assertions.checkState(!this.buildCalled);
            this.bandwidthMeter = bandwidthMeter2;
            return this;
        }

        public Builder setLooper(Looper looper2) {
            Assertions.checkState(!this.buildCalled);
            this.looper = looper2;
            return this;
        }

        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector2) {
            Assertions.checkState(!this.buildCalled);
            this.analyticsCollector = analyticsCollector2;
            return this;
        }

        public Builder setUseLazyPreparation(boolean z) {
            Assertions.checkState(!this.buildCalled);
            this.useLazyPreparation = z;
            return this;
        }

        public Builder setClock(Clock clock2) {
            Assertions.checkState(!this.buildCalled);
            this.clock = clock2;
            return this;
        }

        public ExoPlayer build() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            ExoPlayerImpl exoPlayerImpl = new ExoPlayerImpl(this.renderers, this.trackSelector, this.loadControl, this.bandwidthMeter, this.clock, this.looper);
            return exoPlayerImpl;
        }
    }

    PlayerMessage createMessage(Target target);

    Looper getPlaybackLooper();

    SeekParameters getSeekParameters();

    void prepare(MediaSource mediaSource);

    void prepare(MediaSource mediaSource, boolean z, boolean z2);

    void retry();

    void setForegroundMode(boolean z);

    void setSeekParameters(SeekParameters seekParameters);
}
