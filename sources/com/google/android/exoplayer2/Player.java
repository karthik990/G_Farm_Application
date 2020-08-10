package com.google.android.exoplayer2;

import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Player {
    public static final int DISCONTINUITY_REASON_AD_INSERTION = 3;
    public static final int DISCONTINUITY_REASON_INTERNAL = 4;
    public static final int DISCONTINUITY_REASON_PERIOD_TRANSITION = 0;
    public static final int DISCONTINUITY_REASON_SEEK = 1;
    public static final int DISCONTINUITY_REASON_SEEK_ADJUSTMENT = 2;
    public static final int PLAYBACK_SUPPRESSION_REASON_NONE = 0;
    public static final int PLAYBACK_SUPPRESSION_REASON_TRANSIENT_AUDIO_FOCUS_LOSS = 1;
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_OFF = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int STATE_BUFFERING = 2;
    public static final int STATE_ENDED = 4;
    public static final int STATE_IDLE = 1;
    public static final int STATE_READY = 3;
    public static final int TIMELINE_CHANGE_REASON_DYNAMIC = 2;
    public static final int TIMELINE_CHANGE_REASON_PREPARED = 0;
    public static final int TIMELINE_CHANGE_REASON_RESET = 1;

    public interface AudioComponent {
        void addAudioListener(AudioListener audioListener);

        void clearAuxEffectInfo();

        AudioAttributes getAudioAttributes();

        int getAudioSessionId();

        float getVolume();

        void removeAudioListener(AudioListener audioListener);

        @Deprecated
        void setAudioAttributes(AudioAttributes audioAttributes);

        void setAudioAttributes(AudioAttributes audioAttributes, boolean z);

        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        void setVolume(float f);
    }

    @Deprecated
    public static abstract class DefaultEventListener implements EventListener {
        public /* synthetic */ void onIsPlayingChanged(boolean z) {
            CC.$default$onIsPlayingChanged(this, z);
        }

        public /* synthetic */ void onLoadingChanged(boolean z) {
            CC.$default$onLoadingChanged(this, z);
        }

        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            CC.$default$onPlaybackParametersChanged(this, playbackParameters);
        }

        public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            CC.$default$onPlayerError(this, exoPlaybackException);
        }

        public /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
            CC.$default$onPlayerStateChanged(this, z, i);
        }

        public /* synthetic */ void onPositionDiscontinuity(int i) {
            CC.$default$onPositionDiscontinuity(this, i);
        }

        public /* synthetic */ void onRepeatModeChanged(int i) {
            CC.$default$onRepeatModeChanged(this, i);
        }

        public /* synthetic */ void onSeekProcessed() {
            CC.$default$onSeekProcessed(this);
        }

        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            CC.$default$onShuffleModeEnabledChanged(this, z);
        }

        @Deprecated
        public void onTimelineChanged(Timeline timeline, Object obj) {
        }

        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
        }

        public void onTimelineChanged(Timeline timeline, int i) {
            onTimelineChanged(timeline, timeline.getWindowCount() == 1 ? timeline.getWindow(0, new Window()).manifest : null, i);
        }

        public void onTimelineChanged(Timeline timeline, Object obj, int i) {
            onTimelineChanged(timeline, obj);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DiscontinuityReason {
    }

    public interface EventListener {

        /* renamed from: com.google.android.exoplayer2.Player$EventListener$-CC reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onIsPlayingChanged(EventListener eventListener, boolean z) {
            }

            public static void $default$onLoadingChanged(EventListener eventListener, boolean z) {
            }

            public static void $default$onPlaybackParametersChanged(EventListener eventListener, PlaybackParameters playbackParameters) {
            }

            public static void $default$onPlaybackSuppressionReasonChanged(EventListener eventListener, int i) {
            }

            public static void $default$onPlayerError(EventListener eventListener, ExoPlaybackException exoPlaybackException) {
            }

            public static void $default$onPlayerStateChanged(EventListener eventListener, boolean z, int i) {
            }

            public static void $default$onPositionDiscontinuity(EventListener eventListener, int i) {
            }

            public static void $default$onRepeatModeChanged(EventListener eventListener, int i) {
            }

            public static void $default$onSeekProcessed(EventListener eventListener) {
            }

            public static void $default$onShuffleModeEnabledChanged(EventListener eventListener, boolean z) {
            }

            @Deprecated
            public static void $default$onTimelineChanged(EventListener eventListener, Timeline timeline, Object obj, int i) {
            }

            public static void $default$onTracksChanged(EventListener eventListener, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            }

            public static void $default$onTimelineChanged(EventListener _this, Timeline timeline, int i) {
                _this.onTimelineChanged(timeline, timeline.getWindowCount() == 1 ? timeline.getWindow(0, new Window()).manifest : null, i);
            }
        }

        void onIsPlayingChanged(boolean z);

        void onLoadingChanged(boolean z);

        void onPlaybackParametersChanged(PlaybackParameters playbackParameters);

        void onPlaybackSuppressionReasonChanged(int i);

        void onPlayerError(ExoPlaybackException exoPlaybackException);

        void onPlayerStateChanged(boolean z, int i);

        void onPositionDiscontinuity(int i);

        void onRepeatModeChanged(int i);

        void onSeekProcessed();

        void onShuffleModeEnabledChanged(boolean z);

        void onTimelineChanged(Timeline timeline, int i);

        @Deprecated
        void onTimelineChanged(Timeline timeline, Object obj, int i);

        void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray);
    }

    public interface MetadataComponent {
        void addMetadataOutput(MetadataOutput metadataOutput);

        void removeMetadataOutput(MetadataOutput metadataOutput);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackSuppressionReason {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public interface TextComponent {
        void addTextOutput(TextOutput textOutput);

        void removeTextOutput(TextOutput textOutput);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimelineChangeReason {
    }

    public interface VideoComponent {
        void addVideoListener(VideoListener videoListener);

        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        void clearVideoDecoderOutputBufferRenderer();

        void clearVideoDecoderOutputBufferRenderer(VideoDecoderOutputBufferRenderer videoDecoderOutputBufferRenderer);

        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void clearVideoSurface();

        void clearVideoSurface(Surface surface);

        void clearVideoSurfaceHolder(SurfaceHolder surfaceHolder);

        void clearVideoSurfaceView(SurfaceView surfaceView);

        void clearVideoTextureView(TextureView textureView);

        int getVideoScalingMode();

        void removeVideoListener(VideoListener videoListener);

        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        void setVideoDecoderOutputBufferRenderer(VideoDecoderOutputBufferRenderer videoDecoderOutputBufferRenderer);

        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        void setVideoScalingMode(int i);

        void setVideoSurface(Surface surface);

        void setVideoSurfaceHolder(SurfaceHolder surfaceHolder);

        void setVideoSurfaceView(SurfaceView surfaceView);

        void setVideoTextureView(TextureView textureView);
    }

    void addListener(EventListener eventListener);

    Looper getApplicationLooper();

    AudioComponent getAudioComponent();

    int getBufferedPercentage();

    long getBufferedPosition();

    long getContentBufferedPosition();

    long getContentDuration();

    long getContentPosition();

    int getCurrentAdGroupIndex();

    int getCurrentAdIndexInAdGroup();

    Object getCurrentManifest();

    int getCurrentPeriodIndex();

    long getCurrentPosition();

    Object getCurrentTag();

    Timeline getCurrentTimeline();

    TrackGroupArray getCurrentTrackGroups();

    TrackSelectionArray getCurrentTrackSelections();

    int getCurrentWindowIndex();

    long getDuration();

    MetadataComponent getMetadataComponent();

    int getNextWindowIndex();

    boolean getPlayWhenReady();

    ExoPlaybackException getPlaybackError();

    PlaybackParameters getPlaybackParameters();

    int getPlaybackState();

    int getPlaybackSuppressionReason();

    int getPreviousWindowIndex();

    int getRendererCount();

    int getRendererType(int i);

    int getRepeatMode();

    boolean getShuffleModeEnabled();

    TextComponent getTextComponent();

    long getTotalBufferedDuration();

    VideoComponent getVideoComponent();

    boolean hasNext();

    boolean hasPrevious();

    boolean isCurrentWindowDynamic();

    boolean isCurrentWindowLive();

    boolean isCurrentWindowSeekable();

    boolean isLoading();

    boolean isPlaying();

    boolean isPlayingAd();

    void next();

    void previous();

    void release();

    void removeListener(EventListener eventListener);

    void seekTo(int i, long j);

    void seekTo(long j);

    void seekToDefaultPosition();

    void seekToDefaultPosition(int i);

    void setPlayWhenReady(boolean z);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setRepeatMode(int i);

    void setShuffleModeEnabled(boolean z);

    void stop();

    void stop(boolean z);
}
