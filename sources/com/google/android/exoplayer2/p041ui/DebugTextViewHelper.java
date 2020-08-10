package com.google.android.exoplayer2.p041ui;

import android.os.Looper;
import android.widget.TextView;
import androidx.core.p012os.EnvironmentCompat;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.CC;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.mobiroller.constants.Constants;
import java.util.Locale;

/* renamed from: com.google.android.exoplayer2.ui.DebugTextViewHelper */
public class DebugTextViewHelper implements EventListener, Runnable {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private final SimpleExoPlayer player;
    private boolean started;
    private final TextView textView;

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

    public /* synthetic */ void onRepeatModeChanged(int i) {
        CC.$default$onRepeatModeChanged(this, i);
    }

    public /* synthetic */ void onSeekProcessed() {
        CC.$default$onSeekProcessed(this);
    }

    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
        CC.$default$onShuffleModeEnabledChanged(this, z);
    }

    public /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
        CC.$default$onTimelineChanged(this, timeline, i);
    }

    @Deprecated
    public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
        CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
    }

    public DebugTextViewHelper(SimpleExoPlayer simpleExoPlayer, TextView textView2) {
        Assertions.checkArgument(simpleExoPlayer.getApplicationLooper() == Looper.getMainLooper());
        this.player = simpleExoPlayer;
        this.textView = textView2;
    }

    public final void start() {
        if (!this.started) {
            this.started = true;
            this.player.addListener(this);
            updateAndPost();
        }
    }

    public final void stop() {
        if (this.started) {
            this.started = false;
            this.player.removeListener(this);
            this.textView.removeCallbacks(this);
        }
    }

    public final void onPlayerStateChanged(boolean z, int i) {
        updateAndPost();
    }

    public final void onPositionDiscontinuity(int i) {
        updateAndPost();
    }

    public final void run() {
        updateAndPost();
    }

    /* access modifiers changed from: protected */
    public final void updateAndPost() {
        this.textView.setText(getDebugString());
        this.textView.removeCallbacks(this);
        this.textView.postDelayed(this, 1000);
    }

    /* access modifiers changed from: protected */
    public String getDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPlayerStateString());
        sb.append(getVideoString());
        sb.append(getAudioString());
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getPlayerStateString() {
        int playbackState = this.player.getPlaybackState();
        String str = playbackState != 1 ? playbackState != 2 ? playbackState != 3 ? playbackState != 4 ? EnvironmentCompat.MEDIA_UNKNOWN : "ended" : "ready" : "buffering" : "idle";
        return String.format("playWhenReady:%s playbackState:%s window:%s", new Object[]{Boolean.valueOf(this.player.getPlayWhenReady()), str, Integer.valueOf(this.player.getCurrentWindowIndex())});
    }

    /* access modifiers changed from: protected */
    public String getVideoString() {
        Format videoFormat = this.player.getVideoFormat();
        DecoderCounters videoDecoderCounters = this.player.getVideoDecoderCounters();
        if (videoFormat == null || videoDecoderCounters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.NEW_LINE);
        sb.append(videoFormat.sampleMimeType);
        sb.append("(id:");
        sb.append(videoFormat.f1474id);
        sb.append(" r:");
        sb.append(videoFormat.width);
        sb.append("x");
        sb.append(videoFormat.height);
        sb.append(getPixelAspectRatioString(videoFormat.pixelWidthHeightRatio));
        sb.append(getDecoderCountersBufferCountString(videoDecoderCounters));
        sb.append(")");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getAudioString() {
        Format audioFormat = this.player.getAudioFormat();
        DecoderCounters audioDecoderCounters = this.player.getAudioDecoderCounters();
        if (audioFormat == null || audioDecoderCounters == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.NEW_LINE);
        sb.append(audioFormat.sampleMimeType);
        sb.append("(id:");
        sb.append(audioFormat.f1474id);
        sb.append(" hz:");
        sb.append(audioFormat.sampleRate);
        sb.append(" ch:");
        sb.append(audioFormat.channelCount);
        sb.append(getDecoderCountersBufferCountString(audioDecoderCounters));
        sb.append(")");
        return sb.toString();
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters decoderCounters) {
        if (decoderCounters == null) {
            return "";
        }
        decoderCounters.ensureUpdated();
        StringBuilder sb = new StringBuilder();
        sb.append(" sib:");
        sb.append(decoderCounters.skippedInputBufferCount);
        sb.append(" sb:");
        sb.append(decoderCounters.skippedOutputBufferCount);
        sb.append(" rb:");
        sb.append(decoderCounters.renderedOutputBufferCount);
        sb.append(" db:");
        sb.append(decoderCounters.droppedBufferCount);
        sb.append(" mcdb:");
        sb.append(decoderCounters.maxConsecutiveDroppedBufferCount);
        sb.append(" dk:");
        sb.append(decoderCounters.droppedToKeyframeCount);
        return sb.toString();
    }

    private static String getPixelAspectRatioString(float f) {
        if (f == -1.0f || f == 1.0f) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" par:");
        sb.append(String.format(Locale.US, "%.02f", new Object[]{Float.valueOf(f)}));
        return sb.toString();
    }
}
