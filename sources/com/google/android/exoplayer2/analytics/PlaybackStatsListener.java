package com.google.android.exoplayer2.analytics;

import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.analytics.AnalyticsListener.CC;
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime;
import com.google.android.exoplayer2.analytics.PlaybackSessionManager.Listener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlaybackStatsListener implements AnalyticsListener, Listener {
    private String activeAdPlayback;
    private String activeContentPlayback;
    private final Callback callback;
    private PlaybackStats finishedPlaybackStats = PlaybackStats.EMPTY;
    private boolean isSuppressed;
    private final boolean keepHistory;
    private final Period period = new Period();
    private boolean playWhenReady = false;
    private float playbackSpeed = 1.0f;
    private int playbackState = 1;
    private final Map<String, PlaybackStatsTracker> playbackStatsTrackers = new HashMap();
    private final PlaybackSessionManager sessionManager = new DefaultPlaybackSessionManager();
    private final Map<String, EventTime> sessionStartEventTimes = new HashMap();

    public interface Callback {
        void onPlaybackStatsReady(EventTime eventTime, PlaybackStats playbackStats);
    }

    private static final class PlaybackStatsTracker {
        private long audioFormatBitrateTimeProduct;
        private final List<Pair<EventTime, Format>> audioFormatHistory;
        private long audioFormatTimeMs;
        private long audioUnderruns;
        private long bandwidthBytes;
        private long bandwidthTimeMs;
        private Format currentAudioFormat;
        private float currentPlaybackSpeed;
        private int currentPlaybackState;
        private long currentPlaybackStateStartTimeMs;
        private Format currentVideoFormat;
        private long droppedFrames;
        private int fatalErrorCount;
        private final List<Pair<EventTime, Exception>> fatalErrorHistory;
        private long firstReportedTimeMs;
        private boolean hasBeenReady;
        private boolean hasEnded;
        private boolean hasFatalError;
        private long initialAudioFormatBitrate;
        private long initialVideoFormatBitrate;
        private int initialVideoFormatHeight;
        private final boolean isAd;
        private boolean isFinished;
        private boolean isForeground;
        private boolean isInterruptedByAd;
        private boolean isJoinTimeInvalid;
        private boolean isSeeking;
        private boolean isSuppressed;
        private final boolean keepHistory;
        private long lastAudioFormatStartTimeMs;
        private long lastRebufferStartTimeMs;
        private long lastVideoFormatStartTimeMs;
        private long maxRebufferTimeMs;
        private final List<long[]> mediaTimeHistory;
        private int nonFatalErrorCount;
        private final List<Pair<EventTime, Exception>> nonFatalErrorHistory;
        private int pauseBufferCount;
        private int pauseCount;
        private boolean playWhenReady;
        private final long[] playbackStateDurationsMs = new long[16];
        private final List<Pair<EventTime, Integer>> playbackStateHistory;
        private int playerPlaybackState;
        private int rebufferCount;
        private int seekCount;
        private boolean startedLoading;
        private long videoFormatBitrateTimeMs;
        private long videoFormatBitrateTimeProduct;
        private long videoFormatHeightTimeMs;
        private long videoFormatHeightTimeProduct;
        private final List<Pair<EventTime, Format>> videoFormatHistory;

        private static boolean isInvalidJoinTransition(int i, int i2) {
            boolean z = false;
            if (i != 1 && i != 2 && i != 14) {
                return false;
            }
            if (!(i2 == 1 || i2 == 2 || i2 == 14 || i2 == 3 || i2 == 4 || i2 == 9 || i2 == 11)) {
                z = true;
            }
            return z;
        }

        private static boolean isPausedState(int i) {
            return i == 4 || i == 7;
        }

        private static boolean isReadyState(int i) {
            return i == 3 || i == 4 || i == 9;
        }

        private static boolean isRebufferingState(int i) {
            return i == 6 || i == 7 || i == 10;
        }

        public PlaybackStatsTracker(boolean z, EventTime eventTime) {
            this.keepHistory = z;
            this.playbackStateHistory = z ? new ArrayList<>() : Collections.emptyList();
            this.mediaTimeHistory = z ? new ArrayList<>() : Collections.emptyList();
            this.videoFormatHistory = z ? new ArrayList<>() : Collections.emptyList();
            this.audioFormatHistory = z ? new ArrayList<>() : Collections.emptyList();
            this.fatalErrorHistory = z ? new ArrayList<>() : Collections.emptyList();
            this.nonFatalErrorHistory = z ? new ArrayList<>() : Collections.emptyList();
            boolean z2 = false;
            this.currentPlaybackState = 0;
            this.currentPlaybackStateStartTimeMs = eventTime.realtimeMs;
            this.playerPlaybackState = 1;
            this.firstReportedTimeMs = C1996C.TIME_UNSET;
            this.maxRebufferTimeMs = C1996C.TIME_UNSET;
            if (eventTime.mediaPeriodId != null && eventTime.mediaPeriodId.isAd()) {
                z2 = true;
            }
            this.isAd = z2;
            this.initialAudioFormatBitrate = -1;
            this.initialVideoFormatBitrate = -1;
            this.initialVideoFormatHeight = -1;
            this.currentPlaybackSpeed = 1.0f;
        }

        public void onPlayerStateChanged(EventTime eventTime, boolean z, int i, boolean z2) {
            this.playWhenReady = z;
            this.playerPlaybackState = i;
            if (i != 1) {
                this.hasFatalError = false;
            }
            if (i == 1 || i == 4) {
                this.isInterruptedByAd = false;
            }
            maybeUpdatePlaybackState(eventTime, z2);
        }

        public void onIsSuppressedChanged(EventTime eventTime, boolean z, boolean z2) {
            this.isSuppressed = z;
            maybeUpdatePlaybackState(eventTime, z2);
        }

        public void onPositionDiscontinuity(EventTime eventTime) {
            this.isInterruptedByAd = false;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onSeekStarted(EventTime eventTime) {
            this.isSeeking = true;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onSeekProcessed(EventTime eventTime) {
            this.isSeeking = false;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onFatalError(EventTime eventTime, Exception exc) {
            this.fatalErrorCount++;
            if (this.keepHistory) {
                this.fatalErrorHistory.add(Pair.create(eventTime, exc));
            }
            this.hasFatalError = true;
            this.isInterruptedByAd = false;
            this.isSeeking = false;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onLoadStarted(EventTime eventTime) {
            this.startedLoading = true;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onForeground(EventTime eventTime) {
            this.isForeground = true;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onInterruptedByAd(EventTime eventTime) {
            this.isInterruptedByAd = true;
            this.isSeeking = false;
            maybeUpdatePlaybackState(eventTime, true);
        }

        public void onFinished(EventTime eventTime) {
            this.isFinished = true;
            maybeUpdatePlaybackState(eventTime, false);
        }

        public void onTracksChanged(EventTime eventTime, TrackSelectionArray trackSelectionArray) {
            TrackSelection[] all;
            boolean z = false;
            boolean z2 = false;
            for (TrackSelection trackSelection : trackSelectionArray.getAll()) {
                if (trackSelection != null && trackSelection.length() > 0) {
                    int trackType = MimeTypes.getTrackType(trackSelection.getFormat(0).sampleMimeType);
                    if (trackType == 2) {
                        z = true;
                    } else if (trackType == 1) {
                        z2 = true;
                    }
                }
            }
            if (!z) {
                maybeUpdateVideoFormat(eventTime, null);
            }
            if (!z2) {
                maybeUpdateAudioFormat(eventTime, null);
            }
        }

        public void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData) {
            if (mediaLoadData.trackType == 2 || mediaLoadData.trackType == 0) {
                maybeUpdateVideoFormat(eventTime, mediaLoadData.trackFormat);
            } else if (mediaLoadData.trackType == 1) {
                maybeUpdateAudioFormat(eventTime, mediaLoadData.trackFormat);
            }
        }

        public void onVideoSizeChanged(EventTime eventTime, int i, int i2) {
            Format format = this.currentVideoFormat;
            if (format != null && format.height == -1) {
                maybeUpdateVideoFormat(eventTime, this.currentVideoFormat.copyWithVideoSize(i, i2));
            }
        }

        public void onPlaybackSpeedChanged(EventTime eventTime, float f) {
            maybeUpdateMediaTimeHistory(eventTime.realtimeMs, eventTime.eventPlaybackPositionMs);
            maybeRecordVideoFormatTime(eventTime.realtimeMs);
            maybeRecordAudioFormatTime(eventTime.realtimeMs);
            this.currentPlaybackSpeed = f;
        }

        public void onAudioUnderrun() {
            this.audioUnderruns++;
        }

        public void onDroppedVideoFrames(int i) {
            this.droppedFrames += (long) i;
        }

        public void onBandwidthData(long j, long j2) {
            this.bandwidthTimeMs += j;
            this.bandwidthBytes += j2;
        }

        public void onNonFatalError(EventTime eventTime, Exception exc) {
            this.nonFatalErrorCount++;
            if (this.keepHistory) {
                this.nonFatalErrorHistory.add(Pair.create(eventTime, exc));
            }
        }

        public PlaybackStats build(boolean z) {
            List<long[]> list;
            long[] jArr;
            long j;
            long[] jArr2 = this.playbackStateDurationsMs;
            List<long[]> list2 = this.mediaTimeHistory;
            if (!z) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long[] copyOf = Arrays.copyOf(this.playbackStateDurationsMs, 16);
                long max = Math.max(0, elapsedRealtime - this.currentPlaybackStateStartTimeMs);
                int i = this.currentPlaybackState;
                copyOf[i] = copyOf[i] + max;
                maybeUpdateMaxRebufferTimeMs(elapsedRealtime);
                maybeRecordVideoFormatTime(elapsedRealtime);
                maybeRecordAudioFormatTime(elapsedRealtime);
                ArrayList arrayList = new ArrayList(this.mediaTimeHistory);
                if (this.keepHistory && this.currentPlaybackState == 3) {
                    arrayList.add(guessMediaTimeBasedOnElapsedRealtime(elapsedRealtime));
                }
                jArr = copyOf;
                list = arrayList;
            } else {
                jArr = jArr2;
                list = list2;
            }
            int i2 = (this.isJoinTimeInvalid || !this.hasBeenReady) ? 1 : 0;
            if (i2 != 0) {
                j = C1996C.TIME_UNSET;
            } else {
                j = jArr[2];
            }
            long j2 = j;
            int i3 = jArr[1] > 0 ? 1 : 0;
            List arrayList2 = z ? this.videoFormatHistory : new ArrayList(this.videoFormatHistory);
            List arrayList3 = z ? this.audioFormatHistory : new ArrayList(this.audioFormatHistory);
            List arrayList4 = z ? this.playbackStateHistory : new ArrayList(this.playbackStateHistory);
            long j3 = this.firstReportedTimeMs;
            boolean z2 = this.isForeground;
            boolean z3 = !this.hasBeenReady;
            boolean z4 = this.hasEnded;
            int i4 = i2 ^ 1;
            int i5 = this.pauseCount;
            int i6 = this.pauseBufferCount;
            int i7 = this.seekCount;
            int i8 = this.rebufferCount;
            int i9 = i6;
            long j4 = this.maxRebufferTimeMs;
            boolean z5 = this.isAd;
            long[] jArr3 = jArr;
            int i10 = i7;
            boolean z6 = z5;
            PlaybackStats playbackStats = new PlaybackStats(1, jArr3, arrayList4, list, j3, z2 ? 1 : 0, z3 ? 1 : 0, z4 ? 1 : 0, i3, j2, i4, i5, i9, i10, i8, j4, z6 ? 1 : 0, arrayList2, arrayList3, this.videoFormatHeightTimeMs, this.videoFormatHeightTimeProduct, this.videoFormatBitrateTimeMs, this.videoFormatBitrateTimeProduct, this.audioFormatTimeMs, this.audioFormatBitrateTimeProduct, this.initialVideoFormatHeight == -1 ? 0 : 1, this.initialVideoFormatBitrate == -1 ? 0 : 1, this.initialVideoFormatHeight, this.initialVideoFormatBitrate, this.initialAudioFormatBitrate == -1 ? 0 : 1, this.initialAudioFormatBitrate, this.bandwidthTimeMs, this.bandwidthBytes, this.droppedFrames, this.audioUnderruns, this.fatalErrorCount > 0 ? 1 : 0, this.fatalErrorCount, this.nonFatalErrorCount, this.fatalErrorHistory, this.nonFatalErrorHistory);
            return playbackStats;
        }

        private void maybeUpdatePlaybackState(EventTime eventTime, boolean z) {
            int resolveNewPlaybackState = resolveNewPlaybackState();
            if (resolveNewPlaybackState != this.currentPlaybackState) {
                boolean z2 = false;
                Assertions.checkArgument(eventTime.realtimeMs >= this.currentPlaybackStateStartTimeMs);
                long j = eventTime.realtimeMs - this.currentPlaybackStateStartTimeMs;
                long[] jArr = this.playbackStateDurationsMs;
                int i = this.currentPlaybackState;
                jArr[i] = jArr[i] + j;
                long j2 = this.firstReportedTimeMs;
                long j3 = C1996C.TIME_UNSET;
                if (j2 == C1996C.TIME_UNSET) {
                    this.firstReportedTimeMs = eventTime.realtimeMs;
                }
                this.isJoinTimeInvalid |= isInvalidJoinTransition(this.currentPlaybackState, resolveNewPlaybackState);
                this.hasBeenReady |= isReadyState(resolveNewPlaybackState);
                boolean z3 = this.hasEnded;
                if (resolveNewPlaybackState == 11) {
                    z2 = true;
                }
                this.hasEnded = z3 | z2;
                if (!isPausedState(this.currentPlaybackState) && isPausedState(resolveNewPlaybackState)) {
                    this.pauseCount++;
                }
                if (resolveNewPlaybackState == 5) {
                    this.seekCount++;
                }
                if (!isRebufferingState(this.currentPlaybackState) && isRebufferingState(resolveNewPlaybackState)) {
                    this.rebufferCount++;
                    this.lastRebufferStartTimeMs = eventTime.realtimeMs;
                }
                if (isRebufferingState(this.currentPlaybackState) && this.currentPlaybackState != 7 && resolveNewPlaybackState == 7) {
                    this.pauseBufferCount++;
                }
                long j4 = eventTime.realtimeMs;
                if (z) {
                    j3 = eventTime.eventPlaybackPositionMs;
                }
                maybeUpdateMediaTimeHistory(j4, j3);
                maybeUpdateMaxRebufferTimeMs(eventTime.realtimeMs);
                maybeRecordVideoFormatTime(eventTime.realtimeMs);
                maybeRecordAudioFormatTime(eventTime.realtimeMs);
                this.currentPlaybackState = resolveNewPlaybackState;
                this.currentPlaybackStateStartTimeMs = eventTime.realtimeMs;
                if (this.keepHistory) {
                    this.playbackStateHistory.add(Pair.create(eventTime, Integer.valueOf(this.currentPlaybackState)));
                }
            }
        }

        private int resolveNewPlaybackState() {
            int i = 11;
            if (this.isFinished) {
                if (this.currentPlaybackState != 11) {
                    i = 15;
                }
                return i;
            } else if (this.isSeeking) {
                return 5;
            } else {
                if (this.hasFatalError) {
                    return 13;
                }
                if (!this.isForeground) {
                    return this.startedLoading ? 1 : 0;
                }
                if (this.isInterruptedByAd) {
                    return 14;
                }
                int i2 = this.playerPlaybackState;
                if (i2 == 4) {
                    return 11;
                }
                if (i2 == 2) {
                    int i3 = this.currentPlaybackState;
                    if (i3 == 0 || i3 == 1 || i3 == 2 || i3 == 14) {
                        return 2;
                    }
                    if (i3 == 5 || i3 == 8) {
                        return 8;
                    }
                    if (!this.playWhenReady) {
                        return 7;
                    }
                    return this.isSuppressed ? 10 : 6;
                }
                int i4 = 3;
                if (i2 == 3) {
                    if (!this.playWhenReady) {
                        return 4;
                    }
                    if (this.isSuppressed) {
                        i4 = 9;
                    }
                    return i4;
                } else if (i2 != 1 || this.currentPlaybackState == 0) {
                    return this.currentPlaybackState;
                } else {
                    return 12;
                }
            }
        }

        private void maybeUpdateMaxRebufferTimeMs(long j) {
            if (isRebufferingState(this.currentPlaybackState)) {
                long j2 = j - this.lastRebufferStartTimeMs;
                long j3 = this.maxRebufferTimeMs;
                if (j3 == C1996C.TIME_UNSET || j2 > j3) {
                    this.maxRebufferTimeMs = j2;
                }
            }
        }

        private void maybeUpdateMediaTimeHistory(long j, long j2) {
            long[] jArr;
            if (this.currentPlaybackState != 3) {
                if (j2 != C1996C.TIME_UNSET) {
                    if (!this.mediaTimeHistory.isEmpty()) {
                        List<long[]> list = this.mediaTimeHistory;
                        long j3 = ((long[]) list.get(list.size() - 1))[1];
                        if (j3 != j2) {
                            this.mediaTimeHistory.add(new long[]{j, j3});
                        }
                    }
                } else {
                    return;
                }
            }
            List<long[]> list2 = this.mediaTimeHistory;
            if (j2 == C1996C.TIME_UNSET) {
                jArr = guessMediaTimeBasedOnElapsedRealtime(j);
            } else {
                jArr = new long[]{j, j2};
            }
            list2.add(jArr);
        }

        private long[] guessMediaTimeBasedOnElapsedRealtime(long j) {
            List<long[]> list = this.mediaTimeHistory;
            long[] jArr = (long[]) list.get(list.size() - 1);
            return new long[]{j, jArr[1] + ((long) (((float) (j - jArr[0])) * this.currentPlaybackSpeed))};
        }

        private void maybeUpdateVideoFormat(EventTime eventTime, Format format) {
            if (!Util.areEqual(this.currentVideoFormat, format)) {
                maybeRecordVideoFormatTime(eventTime.realtimeMs);
                if (format != null) {
                    if (this.initialVideoFormatHeight == -1 && format.height != -1) {
                        this.initialVideoFormatHeight = format.height;
                    }
                    if (this.initialVideoFormatBitrate == -1 && format.bitrate != -1) {
                        this.initialVideoFormatBitrate = (long) format.bitrate;
                    }
                }
                this.currentVideoFormat = format;
                if (this.keepHistory) {
                    this.videoFormatHistory.add(Pair.create(eventTime, this.currentVideoFormat));
                }
            }
        }

        private void maybeUpdateAudioFormat(EventTime eventTime, Format format) {
            if (!Util.areEqual(this.currentAudioFormat, format)) {
                maybeRecordAudioFormatTime(eventTime.realtimeMs);
                if (!(format == null || this.initialAudioFormatBitrate != -1 || format.bitrate == -1)) {
                    this.initialAudioFormatBitrate = (long) format.bitrate;
                }
                this.currentAudioFormat = format;
                if (this.keepHistory) {
                    this.audioFormatHistory.add(Pair.create(eventTime, this.currentAudioFormat));
                }
            }
        }

        private void maybeRecordVideoFormatTime(long j) {
            if (this.currentPlaybackState == 3) {
                Format format = this.currentVideoFormat;
                if (format != null) {
                    long j2 = (long) (((float) (j - this.lastVideoFormatStartTimeMs)) * this.currentPlaybackSpeed);
                    if (format.height != -1) {
                        this.videoFormatHeightTimeMs += j2;
                        this.videoFormatHeightTimeProduct += ((long) this.currentVideoFormat.height) * j2;
                    }
                    if (this.currentVideoFormat.bitrate != -1) {
                        this.videoFormatBitrateTimeMs += j2;
                        this.videoFormatBitrateTimeProduct += j2 * ((long) this.currentVideoFormat.bitrate);
                    }
                }
            }
            this.lastVideoFormatStartTimeMs = j;
        }

        private void maybeRecordAudioFormatTime(long j) {
            if (this.currentPlaybackState == 3) {
                Format format = this.currentAudioFormat;
                if (!(format == null || format.bitrate == -1)) {
                    long j2 = (long) (((float) (j - this.lastAudioFormatStartTimeMs)) * this.currentPlaybackSpeed);
                    this.audioFormatTimeMs += j2;
                    this.audioFormatBitrateTimeProduct += j2 * ((long) this.currentAudioFormat.bitrate);
                }
            }
            this.lastAudioFormatStartTimeMs = j;
        }
    }

    public /* synthetic */ void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {
        CC.$default$onAudioAttributesChanged(this, eventTime, audioAttributes);
    }

    public /* synthetic */ void onAudioSessionId(EventTime eventTime, int i) {
        CC.$default$onAudioSessionId(this, eventTime, i);
    }

    public /* synthetic */ void onDecoderDisabled(EventTime eventTime, int i, DecoderCounters decoderCounters) {
        CC.$default$onDecoderDisabled(this, eventTime, i, decoderCounters);
    }

    public /* synthetic */ void onDecoderEnabled(EventTime eventTime, int i, DecoderCounters decoderCounters) {
        CC.$default$onDecoderEnabled(this, eventTime, i, decoderCounters);
    }

    public /* synthetic */ void onDecoderInitialized(EventTime eventTime, int i, String str, long j) {
        CC.$default$onDecoderInitialized(this, eventTime, i, str, j);
    }

    public /* synthetic */ void onDecoderInputFormatChanged(EventTime eventTime, int i, Format format) {
        CC.$default$onDecoderInputFormatChanged(this, eventTime, i, format);
    }

    public /* synthetic */ void onDrmKeysLoaded(EventTime eventTime) {
        CC.$default$onDrmKeysLoaded(this, eventTime);
    }

    public /* synthetic */ void onDrmKeysRemoved(EventTime eventTime) {
        CC.$default$onDrmKeysRemoved(this, eventTime);
    }

    public /* synthetic */ void onDrmKeysRestored(EventTime eventTime) {
        CC.$default$onDrmKeysRestored(this, eventTime);
    }

    public /* synthetic */ void onDrmSessionAcquired(EventTime eventTime) {
        CC.$default$onDrmSessionAcquired(this, eventTime);
    }

    public /* synthetic */ void onDrmSessionReleased(EventTime eventTime) {
        CC.$default$onDrmSessionReleased(this, eventTime);
    }

    public /* synthetic */ void onIsPlayingChanged(EventTime eventTime, boolean z) {
        CC.$default$onIsPlayingChanged(this, eventTime, z);
    }

    public /* synthetic */ void onLoadCanceled(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        CC.$default$onLoadCanceled(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public /* synthetic */ void onLoadCompleted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        CC.$default$onLoadCompleted(this, eventTime, loadEventInfo, mediaLoadData);
    }

    public /* synthetic */ void onLoadingChanged(EventTime eventTime, boolean z) {
        CC.$default$onLoadingChanged(this, eventTime, z);
    }

    public /* synthetic */ void onMediaPeriodCreated(EventTime eventTime) {
        CC.$default$onMediaPeriodCreated(this, eventTime);
    }

    public /* synthetic */ void onMediaPeriodReleased(EventTime eventTime) {
        CC.$default$onMediaPeriodReleased(this, eventTime);
    }

    public /* synthetic */ void onMetadata(EventTime eventTime, Metadata metadata) {
        CC.$default$onMetadata(this, eventTime, metadata);
    }

    public /* synthetic */ void onReadingStarted(EventTime eventTime) {
        CC.$default$onReadingStarted(this, eventTime);
    }

    public /* synthetic */ void onRenderedFirstFrame(EventTime eventTime, Surface surface) {
        CC.$default$onRenderedFirstFrame(this, eventTime, surface);
    }

    public /* synthetic */ void onRepeatModeChanged(EventTime eventTime, int i) {
        CC.$default$onRepeatModeChanged(this, eventTime, i);
    }

    public /* synthetic */ void onShuffleModeChanged(EventTime eventTime, boolean z) {
        CC.$default$onShuffleModeChanged(this, eventTime, z);
    }

    public /* synthetic */ void onSurfaceSizeChanged(EventTime eventTime, int i, int i2) {
        CC.$default$onSurfaceSizeChanged(this, eventTime, i, i2);
    }

    public /* synthetic */ void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData) {
        CC.$default$onUpstreamDiscarded(this, eventTime, mediaLoadData);
    }

    public /* synthetic */ void onVolumeChanged(EventTime eventTime, float f) {
        CC.$default$onVolumeChanged(this, eventTime, f);
    }

    public PlaybackStatsListener(boolean z, Callback callback2) {
        this.callback = callback2;
        this.keepHistory = z;
        this.sessionManager.setListener(this);
    }

    public PlaybackStats getCombinedPlaybackStats() {
        int i = 1;
        PlaybackStats[] playbackStatsArr = new PlaybackStats[(this.playbackStatsTrackers.size() + 1)];
        playbackStatsArr[0] = this.finishedPlaybackStats;
        for (PlaybackStatsTracker build : this.playbackStatsTrackers.values()) {
            int i2 = i + 1;
            playbackStatsArr[i] = build.build(false);
            i = i2;
        }
        return PlaybackStats.merge(playbackStatsArr);
    }

    public PlaybackStats getPlaybackStats() {
        PlaybackStatsTracker playbackStatsTracker;
        String str = this.activeAdPlayback;
        if (str != null) {
            playbackStatsTracker = (PlaybackStatsTracker) this.playbackStatsTrackers.get(str);
        } else {
            String str2 = this.activeContentPlayback;
            playbackStatsTracker = str2 != null ? (PlaybackStatsTracker) this.playbackStatsTrackers.get(str2) : null;
        }
        if (playbackStatsTracker == null) {
            return null;
        }
        return playbackStatsTracker.build(false);
    }

    public void finishAllSessions() {
        HashMap hashMap = new HashMap(this.playbackStatsTrackers);
        EventTime eventTime = new EventTime(SystemClock.elapsedRealtime(), Timeline.EMPTY, 0, null, 0, 0, 0);
        for (String onSessionFinished : hashMap.keySet()) {
            onSessionFinished(eventTime, onSessionFinished, false);
        }
    }

    public void onSessionCreated(EventTime eventTime, String str) {
        PlaybackStatsTracker playbackStatsTracker = new PlaybackStatsTracker(this.keepHistory, eventTime);
        playbackStatsTracker.onPlayerStateChanged(eventTime, this.playWhenReady, this.playbackState, true);
        playbackStatsTracker.onIsSuppressedChanged(eventTime, this.isSuppressed, true);
        playbackStatsTracker.onPlaybackSpeedChanged(eventTime, this.playbackSpeed);
        this.playbackStatsTrackers.put(str, playbackStatsTracker);
        this.sessionStartEventTimes.put(str, eventTime);
    }

    public void onSessionActive(EventTime eventTime, String str) {
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(str))).onForeground(eventTime);
        if (eventTime.mediaPeriodId == null || !eventTime.mediaPeriodId.isAd()) {
            this.activeContentPlayback = str;
        } else {
            this.activeAdPlayback = str;
        }
    }

    public void onAdPlaybackStarted(EventTime eventTime, String str, String str2) {
        EventTime eventTime2 = eventTime;
        Assertions.checkState(((MediaPeriodId) Assertions.checkNotNull(eventTime2.mediaPeriodId)).isAd());
        EventTime eventTime3 = r4;
        EventTime eventTime4 = new EventTime(eventTime2.realtimeMs, eventTime2.timeline, eventTime2.windowIndex, new MediaPeriodId(eventTime2.mediaPeriodId.periodUid, eventTime2.mediaPeriodId.windowSequenceNumber, eventTime2.mediaPeriodId.adGroupIndex), C1996C.usToMs(eventTime2.timeline.getPeriodByUid(eventTime2.mediaPeriodId.periodUid, this.period).getAdGroupTimeUs(eventTime2.mediaPeriodId.adGroupIndex)), eventTime2.currentPlaybackPositionMs, eventTime2.totalBufferedDurationMs);
        ((PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.get(str))).onInterruptedByAd(eventTime3);
    }

    public void onSessionFinished(EventTime eventTime, String str, boolean z) {
        if (str.equals(this.activeAdPlayback)) {
            this.activeAdPlayback = null;
        } else if (str.equals(this.activeContentPlayback)) {
            this.activeContentPlayback = null;
        }
        PlaybackStatsTracker playbackStatsTracker = (PlaybackStatsTracker) Assertions.checkNotNull(this.playbackStatsTrackers.remove(str));
        EventTime eventTime2 = (EventTime) Assertions.checkNotNull(this.sessionStartEventTimes.remove(str));
        if (z) {
            playbackStatsTracker.onPlayerStateChanged(eventTime, true, 4, false);
        }
        playbackStatsTracker.onFinished(eventTime);
        PlaybackStats build = playbackStatsTracker.build(true);
        this.finishedPlaybackStats = PlaybackStats.merge(this.finishedPlaybackStats, build);
        Callback callback2 = this.callback;
        if (callback2 != null) {
            callback2.onPlaybackStatsReady(eventTime2, build);
        }
    }

    public void onPlayerStateChanged(EventTime eventTime, boolean z, int i) {
        this.playWhenReady = z;
        this.playbackState = i;
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onPlayerStateChanged(eventTime, z, i, this.sessionManager.belongsToSession(eventTime, str));
        }
    }

    public void onPlaybackSuppressionReasonChanged(EventTime eventTime, int i) {
        this.isSuppressed = i != 0;
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onIsSuppressedChanged(eventTime, this.isSuppressed, this.sessionManager.belongsToSession(eventTime, str));
        }
    }

    public void onTimelineChanged(EventTime eventTime, int i) {
        this.sessionManager.handleTimelineUpdate(eventTime);
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onPositionDiscontinuity(eventTime);
            }
        }
    }

    public void onPositionDiscontinuity(EventTime eventTime, int i) {
        this.sessionManager.handlePositionDiscontinuity(eventTime, i);
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onPositionDiscontinuity(eventTime);
            }
        }
    }

    public void onSeekStarted(EventTime eventTime) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onSeekStarted(eventTime);
            }
        }
    }

    public void onSeekProcessed(EventTime eventTime) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onSeekProcessed(eventTime);
            }
        }
    }

    public void onPlayerError(EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onFatalError(eventTime, exoPlaybackException);
            }
        }
    }

    public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
        this.playbackSpeed = playbackParameters.speed;
        this.sessionManager.updateSessions(eventTime);
        for (PlaybackStatsTracker onPlaybackSpeedChanged : this.playbackStatsTrackers.values()) {
            onPlaybackSpeedChanged.onPlaybackSpeedChanged(eventTime, this.playbackSpeed);
        }
    }

    public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onTracksChanged(eventTime, trackSelectionArray);
            }
        }
    }

    public void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onLoadStarted(eventTime);
            }
        }
    }

    public void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onDownstreamFormatChanged(eventTime, mediaLoadData);
            }
        }
    }

    public void onVideoSizeChanged(EventTime eventTime, int i, int i2, int i3, float f) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onVideoSizeChanged(eventTime, i, i2);
            }
        }
    }

    public void onBandwidthEstimate(EventTime eventTime, int i, long j, long j2) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onBandwidthData((long) i, j);
            }
        }
    }

    public void onAudioUnderrun(EventTime eventTime, int i, long j, long j2) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onAudioUnderrun();
            }
        }
    }

    public void onDroppedVideoFrames(EventTime eventTime, int i, long j) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onDroppedVideoFrames(i);
            }
        }
    }

    public void onLoadError(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onNonFatalError(eventTime, iOException);
            }
        }
    }

    public void onDrmSessionManagerError(EventTime eventTime, Exception exc) {
        this.sessionManager.updateSessions(eventTime);
        for (String str : this.playbackStatsTrackers.keySet()) {
            if (this.sessionManager.belongsToSession(eventTime, str)) {
                ((PlaybackStatsTracker) this.playbackStatsTrackers.get(str)).onNonFatalError(eventTime, exc);
            }
        }
    }
}
