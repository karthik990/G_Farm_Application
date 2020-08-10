package com.google.android.exoplayer2.analytics;

import android.view.Surface;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public class AnalyticsCollector implements EventListener, MetadataOutput, AudioRendererEventListener, VideoRendererEventListener, MediaSourceEventListener, BandwidthMeter.EventListener, DefaultDrmSessionEventListener, VideoListener, AudioListener {
    private final Clock clock;
    private final CopyOnWriteArraySet<AnalyticsListener> listeners = new CopyOnWriteArraySet<>();
    private final MediaPeriodQueueTracker mediaPeriodQueueTracker = new MediaPeriodQueueTracker();
    private Player player;
    private final Window window = new Window();

    private static final class MediaPeriodInfo {
        public final MediaPeriodId mediaPeriodId;
        public final Timeline timeline;
        public final int windowIndex;

        public MediaPeriodInfo(MediaPeriodId mediaPeriodId2, Timeline timeline2, int i) {
            this.mediaPeriodId = mediaPeriodId2;
            this.timeline = timeline2;
            this.windowIndex = i;
        }
    }

    private static final class MediaPeriodQueueTracker {
        private boolean isSeeking;
        private MediaPeriodInfo lastPlayingMediaPeriod;
        private MediaPeriodInfo lastReportedPlayingMediaPeriod;
        private final HashMap<MediaPeriodId, MediaPeriodInfo> mediaPeriodIdToInfo = new HashMap<>();
        /* access modifiers changed from: private */
        public final ArrayList<MediaPeriodInfo> mediaPeriodInfoQueue = new ArrayList<>();
        private final Period period = new Period();
        private MediaPeriodInfo readingMediaPeriod;
        private Timeline timeline = Timeline.EMPTY;

        public MediaPeriodInfo getPlayingMediaPeriod() {
            if (this.mediaPeriodInfoQueue.isEmpty() || this.timeline.isEmpty() || this.isSeeking) {
                return null;
            }
            return (MediaPeriodInfo) this.mediaPeriodInfoQueue.get(0);
        }

        public MediaPeriodInfo getLastReportedPlayingMediaPeriod() {
            return this.lastReportedPlayingMediaPeriod;
        }

        public MediaPeriodInfo getReadingMediaPeriod() {
            return this.readingMediaPeriod;
        }

        public MediaPeriodInfo getLoadingMediaPeriod() {
            if (this.mediaPeriodInfoQueue.isEmpty()) {
                return null;
            }
            ArrayList<MediaPeriodInfo> arrayList = this.mediaPeriodInfoQueue;
            return (MediaPeriodInfo) arrayList.get(arrayList.size() - 1);
        }

        public MediaPeriodInfo getMediaPeriodInfo(MediaPeriodId mediaPeriodId) {
            return (MediaPeriodInfo) this.mediaPeriodIdToInfo.get(mediaPeriodId);
        }

        public boolean isSeeking() {
            return this.isSeeking;
        }

        public MediaPeriodInfo tryResolveWindowIndex(int i) {
            MediaPeriodInfo mediaPeriodInfo = null;
            for (int i2 = 0; i2 < this.mediaPeriodInfoQueue.size(); i2++) {
                MediaPeriodInfo mediaPeriodInfo2 = (MediaPeriodInfo) this.mediaPeriodInfoQueue.get(i2);
                int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodInfo2.mediaPeriodId.periodUid);
                if (indexOfPeriod != -1 && this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
                    if (mediaPeriodInfo != null) {
                        return null;
                    }
                    mediaPeriodInfo = mediaPeriodInfo2;
                }
            }
            return mediaPeriodInfo;
        }

        public void onPositionDiscontinuity(int i) {
            this.lastReportedPlayingMediaPeriod = this.lastPlayingMediaPeriod;
        }

        public void onTimelineChanged(Timeline timeline2) {
            for (int i = 0; i < this.mediaPeriodInfoQueue.size(); i++) {
                MediaPeriodInfo updateMediaPeriodInfoToNewTimeline = updateMediaPeriodInfoToNewTimeline((MediaPeriodInfo) this.mediaPeriodInfoQueue.get(i), timeline2);
                this.mediaPeriodInfoQueue.set(i, updateMediaPeriodInfoToNewTimeline);
                this.mediaPeriodIdToInfo.put(updateMediaPeriodInfoToNewTimeline.mediaPeriodId, updateMediaPeriodInfoToNewTimeline);
            }
            MediaPeriodInfo mediaPeriodInfo = this.readingMediaPeriod;
            if (mediaPeriodInfo != null) {
                this.readingMediaPeriod = updateMediaPeriodInfoToNewTimeline(mediaPeriodInfo, timeline2);
            }
            this.timeline = timeline2;
            this.lastReportedPlayingMediaPeriod = this.lastPlayingMediaPeriod;
        }

        public void onSeekStarted() {
            this.isSeeking = true;
        }

        public void onSeekProcessed() {
            this.isSeeking = false;
            this.lastReportedPlayingMediaPeriod = this.lastPlayingMediaPeriod;
        }

        public void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
            MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(mediaPeriodId, this.timeline.getIndexOfPeriod(mediaPeriodId.periodUid) != -1 ? this.timeline : Timeline.EMPTY, i);
            this.mediaPeriodInfoQueue.add(mediaPeriodInfo);
            this.mediaPeriodIdToInfo.put(mediaPeriodId, mediaPeriodInfo);
            this.lastPlayingMediaPeriod = (MediaPeriodInfo) this.mediaPeriodInfoQueue.get(0);
            if (this.mediaPeriodInfoQueue.size() == 1 && !this.timeline.isEmpty()) {
                this.lastReportedPlayingMediaPeriod = this.lastPlayingMediaPeriod;
            }
        }

        public boolean onMediaPeriodReleased(MediaPeriodId mediaPeriodId) {
            MediaPeriodInfo mediaPeriodInfo = (MediaPeriodInfo) this.mediaPeriodIdToInfo.remove(mediaPeriodId);
            if (mediaPeriodInfo == null) {
                return false;
            }
            this.mediaPeriodInfoQueue.remove(mediaPeriodInfo);
            MediaPeriodInfo mediaPeriodInfo2 = this.readingMediaPeriod;
            if (mediaPeriodInfo2 != null && mediaPeriodId.equals(mediaPeriodInfo2.mediaPeriodId)) {
                this.readingMediaPeriod = this.mediaPeriodInfoQueue.isEmpty() ? null : (MediaPeriodInfo) this.mediaPeriodInfoQueue.get(0);
            }
            if (!this.mediaPeriodInfoQueue.isEmpty()) {
                this.lastPlayingMediaPeriod = (MediaPeriodInfo) this.mediaPeriodInfoQueue.get(0);
            }
            return true;
        }

        public void onReadingStarted(MediaPeriodId mediaPeriodId) {
            this.readingMediaPeriod = (MediaPeriodInfo) this.mediaPeriodIdToInfo.get(mediaPeriodId);
        }

        private MediaPeriodInfo updateMediaPeriodInfoToNewTimeline(MediaPeriodInfo mediaPeriodInfo, Timeline timeline2) {
            int indexOfPeriod = timeline2.getIndexOfPeriod(mediaPeriodInfo.mediaPeriodId.periodUid);
            if (indexOfPeriod == -1) {
                return mediaPeriodInfo;
            }
            return new MediaPeriodInfo(mediaPeriodInfo.mediaPeriodId, timeline2, timeline2.getPeriod(indexOfPeriod, this.period).windowIndex);
        }
    }

    public final void onRenderedFirstFrame() {
    }

    @Deprecated
    public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
        CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    public AnalyticsCollector(Clock clock2) {
        this.clock = (Clock) Assertions.checkNotNull(clock2);
    }

    public void addListener(AnalyticsListener analyticsListener) {
        this.listeners.add(analyticsListener);
    }

    public void removeListener(AnalyticsListener analyticsListener) {
        this.listeners.remove(analyticsListener);
    }

    public void setPlayer(Player player2) {
        Assertions.checkState(this.player == null || this.mediaPeriodQueueTracker.mediaPeriodInfoQueue.isEmpty());
        this.player = (Player) Assertions.checkNotNull(player2);
    }

    public final void notifySeekStarted() {
        if (!this.mediaPeriodQueueTracker.isSeeking()) {
            EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
            this.mediaPeriodQueueTracker.onSeekStarted();
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((AnalyticsListener) it.next()).onSeekStarted(generatePlayingMediaPeriodEventTime);
            }
        }
    }

    public final void resetForNewMediaSource() {
        for (MediaPeriodInfo mediaPeriodInfo : new ArrayList(this.mediaPeriodQueueTracker.mediaPeriodInfoQueue)) {
            onMediaPeriodReleased(mediaPeriodInfo.windowIndex, mediaPeriodInfo.mediaPeriodId);
        }
    }

    public final void onMetadata(Metadata metadata) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onMetadata(generatePlayingMediaPeriodEventTime, metadata);
        }
    }

    public final void onAudioEnabled(DecoderCounters decoderCounters) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderEnabled(generatePlayingMediaPeriodEventTime, 1, decoderCounters);
        }
    }

    public final void onAudioDecoderInitialized(String str, long j, long j2) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderInitialized(generateReadingMediaPeriodEventTime, 1, str, j2);
        }
    }

    public final void onAudioInputFormatChanged(Format format) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderInputFormatChanged(generateReadingMediaPeriodEventTime, 1, format);
        }
    }

    public final void onAudioSinkUnderrun(int i, long j, long j2) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onAudioUnderrun(generateReadingMediaPeriodEventTime, i, j, j2);
        }
    }

    public final void onAudioDisabled(DecoderCounters decoderCounters) {
        EventTime generateLastReportedPlayingMediaPeriodEventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderDisabled(generateLastReportedPlayingMediaPeriodEventTime, 1, decoderCounters);
        }
    }

    public final void onAudioSessionId(int i) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onAudioSessionId(generateReadingMediaPeriodEventTime, i);
        }
    }

    public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onAudioAttributesChanged(generateReadingMediaPeriodEventTime, audioAttributes);
        }
    }

    public void onVolumeChanged(float f) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onVolumeChanged(generateReadingMediaPeriodEventTime, f);
        }
    }

    public final void onVideoEnabled(DecoderCounters decoderCounters) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderEnabled(generatePlayingMediaPeriodEventTime, 2, decoderCounters);
        }
    }

    public final void onVideoDecoderInitialized(String str, long j, long j2) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderInitialized(generateReadingMediaPeriodEventTime, 2, str, j2);
        }
    }

    public final void onVideoInputFormatChanged(Format format) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderInputFormatChanged(generateReadingMediaPeriodEventTime, 2, format);
        }
    }

    public final void onDroppedFrames(int i, long j) {
        EventTime generateLastReportedPlayingMediaPeriodEventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDroppedVideoFrames(generateLastReportedPlayingMediaPeriodEventTime, i, j);
        }
    }

    public final void onVideoDisabled(DecoderCounters decoderCounters) {
        EventTime generateLastReportedPlayingMediaPeriodEventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDecoderDisabled(generateLastReportedPlayingMediaPeriodEventTime, 2, decoderCounters);
        }
    }

    public final void onRenderedFirstFrame(Surface surface) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onRenderedFirstFrame(generateReadingMediaPeriodEventTime, surface);
        }
    }

    public final void onVideoSizeChanged(int i, int i2, int i3, float f) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onVideoSizeChanged(generateReadingMediaPeriodEventTime, i, i2, i3, f);
        }
    }

    public void onSurfaceSizeChanged(int i, int i2) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onSurfaceSizeChanged(generateReadingMediaPeriodEventTime, i, i2);
        }
    }

    public final void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onMediaPeriodCreated(i, mediaPeriodId);
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onMediaPeriodCreated(generateMediaPeriodEventTime);
        }
    }

    public final void onMediaPeriodReleased(int i, MediaPeriodId mediaPeriodId) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        if (this.mediaPeriodQueueTracker.onMediaPeriodReleased(mediaPeriodId)) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((AnalyticsListener) it.next()).onMediaPeriodReleased(generateMediaPeriodEventTime);
            }
        }
    }

    public final void onLoadStarted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onLoadStarted(generateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadCompleted(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onLoadCompleted(generateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadCanceled(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onLoadCanceled(generateMediaPeriodEventTime, loadEventInfo, mediaLoadData);
        }
    }

    public final void onLoadError(int i, MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onLoadError(generateMediaPeriodEventTime, loadEventInfo, mediaLoadData, iOException, z);
        }
    }

    public final void onReadingStarted(int i, MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onReadingStarted(mediaPeriodId);
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onReadingStarted(generateMediaPeriodEventTime);
        }
    }

    public final void onUpstreamDiscarded(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onUpstreamDiscarded(generateMediaPeriodEventTime, mediaLoadData);
        }
    }

    public final void onDownstreamFormatChanged(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        EventTime generateMediaPeriodEventTime = generateMediaPeriodEventTime(i, mediaPeriodId);
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDownstreamFormatChanged(generateMediaPeriodEventTime, mediaLoadData);
        }
    }

    public final void onTimelineChanged(Timeline timeline, int i) {
        this.mediaPeriodQueueTracker.onTimelineChanged(timeline);
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onTimelineChanged(generatePlayingMediaPeriodEventTime, i);
        }
    }

    public final void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onTracksChanged(generatePlayingMediaPeriodEventTime, trackGroupArray, trackSelectionArray);
        }
    }

    public final void onLoadingChanged(boolean z) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onLoadingChanged(generatePlayingMediaPeriodEventTime, z);
        }
    }

    public final void onPlayerStateChanged(boolean z, int i) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onPlayerStateChanged(generatePlayingMediaPeriodEventTime, z, i);
        }
    }

    public void onPlaybackSuppressionReasonChanged(int i) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onPlaybackSuppressionReasonChanged(generatePlayingMediaPeriodEventTime, i);
        }
    }

    public void onIsPlayingChanged(boolean z) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onIsPlayingChanged(generatePlayingMediaPeriodEventTime, z);
        }
    }

    public final void onRepeatModeChanged(int i) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onRepeatModeChanged(generatePlayingMediaPeriodEventTime, i);
        }
    }

    public final void onShuffleModeEnabledChanged(boolean z) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onShuffleModeChanged(generatePlayingMediaPeriodEventTime, z);
        }
    }

    public final void onPlayerError(ExoPlaybackException exoPlaybackException) {
        EventTime generateLastReportedPlayingMediaPeriodEventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onPlayerError(generateLastReportedPlayingMediaPeriodEventTime, exoPlaybackException);
        }
    }

    public final void onPositionDiscontinuity(int i) {
        this.mediaPeriodQueueTracker.onPositionDiscontinuity(i);
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onPositionDiscontinuity(generatePlayingMediaPeriodEventTime, i);
        }
    }

    public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onPlaybackParametersChanged(generatePlayingMediaPeriodEventTime, playbackParameters);
        }
    }

    public final void onSeekProcessed() {
        if (this.mediaPeriodQueueTracker.isSeeking()) {
            this.mediaPeriodQueueTracker.onSeekProcessed();
            EventTime generatePlayingMediaPeriodEventTime = generatePlayingMediaPeriodEventTime();
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((AnalyticsListener) it.next()).onSeekProcessed(generatePlayingMediaPeriodEventTime);
            }
        }
    }

    public final void onBandwidthSample(int i, long j, long j2) {
        EventTime generateLoadingMediaPeriodEventTime = generateLoadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onBandwidthEstimate(generateLoadingMediaPeriodEventTime, i, j, j2);
        }
    }

    public final void onDrmSessionAcquired() {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmSessionAcquired(generateReadingMediaPeriodEventTime);
        }
    }

    public final void onDrmKeysLoaded() {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmKeysLoaded(generateReadingMediaPeriodEventTime);
        }
    }

    public final void onDrmSessionManagerError(Exception exc) {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmSessionManagerError(generateReadingMediaPeriodEventTime, exc);
        }
    }

    public final void onDrmKeysRestored() {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmKeysRestored(generateReadingMediaPeriodEventTime);
        }
    }

    public final void onDrmKeysRemoved() {
        EventTime generateReadingMediaPeriodEventTime = generateReadingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmKeysRemoved(generateReadingMediaPeriodEventTime);
        }
    }

    public final void onDrmSessionReleased() {
        EventTime generateLastReportedPlayingMediaPeriodEventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((AnalyticsListener) it.next()).onDrmSessionReleased(generateLastReportedPlayingMediaPeriodEventTime);
        }
    }

    /* access modifiers changed from: protected */
    public Set<AnalyticsListener> getListeners() {
        return Collections.unmodifiableSet(this.listeners);
    }

    /* access modifiers changed from: protected */
    @RequiresNonNull({"player"})
    public EventTime generateEventTime(Timeline timeline, int i, MediaPeriodId mediaPeriodId) {
        if (timeline.isEmpty()) {
            mediaPeriodId = null;
        }
        MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        long elapsedRealtime = this.clock.elapsedRealtime();
        boolean z = true;
        boolean z2 = timeline == this.player.getCurrentTimeline() && i == this.player.getCurrentWindowIndex();
        long j = 0;
        if (mediaPeriodId2 != null && mediaPeriodId2.isAd()) {
            if (!(z2 && this.player.getCurrentAdGroupIndex() == mediaPeriodId2.adGroupIndex && this.player.getCurrentAdIndexInAdGroup() == mediaPeriodId2.adIndexInAdGroup)) {
                z = false;
            }
            if (z) {
                j = this.player.getCurrentPosition();
            }
        } else if (z2) {
            j = this.player.getContentPosition();
        } else if (!timeline.isEmpty()) {
            j = timeline.getWindow(i, this.window).getDefaultPositionMs();
        }
        EventTime eventTime = new EventTime(elapsedRealtime, timeline, i, mediaPeriodId2, j, this.player.getCurrentPosition(), this.player.getTotalBufferedDuration());
        return eventTime;
    }

    private EventTime generateEventTime(MediaPeriodInfo mediaPeriodInfo) {
        Assertions.checkNotNull(this.player);
        if (mediaPeriodInfo == null) {
            int currentWindowIndex = this.player.getCurrentWindowIndex();
            MediaPeriodInfo tryResolveWindowIndex = this.mediaPeriodQueueTracker.tryResolveWindowIndex(currentWindowIndex);
            if (tryResolveWindowIndex == null) {
                Timeline currentTimeline = this.player.getCurrentTimeline();
                if (!(currentWindowIndex < currentTimeline.getWindowCount())) {
                    currentTimeline = Timeline.EMPTY;
                }
                return generateEventTime(currentTimeline, currentWindowIndex, null);
            }
            mediaPeriodInfo = tryResolveWindowIndex;
        }
        return generateEventTime(mediaPeriodInfo.timeline, mediaPeriodInfo.windowIndex, mediaPeriodInfo.mediaPeriodId);
    }

    private EventTime generateLastReportedPlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLastReportedPlayingMediaPeriod());
    }

    private EventTime generatePlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getPlayingMediaPeriod());
    }

    private EventTime generateReadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getReadingMediaPeriod());
    }

    private EventTime generateLoadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLoadingMediaPeriod());
    }

    private EventTime generateMediaPeriodEventTime(int i, MediaPeriodId mediaPeriodId) {
        EventTime eventTime;
        Assertions.checkNotNull(this.player);
        if (mediaPeriodId != null) {
            MediaPeriodInfo mediaPeriodInfo = this.mediaPeriodQueueTracker.getMediaPeriodInfo(mediaPeriodId);
            if (mediaPeriodInfo != null) {
                eventTime = generateEventTime(mediaPeriodInfo);
            } else {
                eventTime = generateEventTime(Timeline.EMPTY, i, mediaPeriodId);
            }
            return eventTime;
        }
        Timeline currentTimeline = this.player.getCurrentTimeline();
        if (!(i < currentTimeline.getWindowCount())) {
            currentTimeline = Timeline.EMPTY;
        }
        return generateEventTime(currentTimeline, i, null);
    }
}
