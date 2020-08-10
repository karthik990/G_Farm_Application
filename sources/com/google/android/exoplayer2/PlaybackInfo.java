package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;

final class PlaybackInfo {
    private static final MediaPeriodId DUMMY_MEDIA_PERIOD_ID = new MediaPeriodId(new Object());
    public volatile long bufferedPositionUs;
    public final long contentPositionUs;
    public final boolean isLoading;
    public final MediaPeriodId loadingMediaPeriodId;
    public final MediaPeriodId periodId;
    public final ExoPlaybackException playbackError;
    public final int playbackState;
    public volatile long positionUs;
    public final long startPositionUs;
    public final Timeline timeline;
    public volatile long totalBufferedDurationUs;
    public final TrackGroupArray trackGroups;
    public final TrackSelectorResult trackSelectorResult;

    public static PlaybackInfo createDummy(long j, TrackSelectorResult trackSelectorResult2) {
        TrackSelectorResult trackSelectorResult3 = trackSelectorResult2;
        PlaybackInfo playbackInfo = new PlaybackInfo(Timeline.EMPTY, DUMMY_MEDIA_PERIOD_ID, j, C1996C.TIME_UNSET, 1, null, false, TrackGroupArray.EMPTY, trackSelectorResult3, DUMMY_MEDIA_PERIOD_ID, j, 0, j);
        return playbackInfo;
    }

    public PlaybackInfo(Timeline timeline2, MediaPeriodId mediaPeriodId, long j, long j2, int i, ExoPlaybackException exoPlaybackException, boolean z, TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult2, MediaPeriodId mediaPeriodId2, long j3, long j4, long j5) {
        this.timeline = timeline2;
        this.periodId = mediaPeriodId;
        this.startPositionUs = j;
        this.contentPositionUs = j2;
        this.playbackState = i;
        this.playbackError = exoPlaybackException;
        this.isLoading = z;
        this.trackGroups = trackGroupArray;
        this.trackSelectorResult = trackSelectorResult2;
        this.loadingMediaPeriodId = mediaPeriodId2;
        this.bufferedPositionUs = j3;
        this.totalBufferedDurationUs = j4;
        this.positionUs = j5;
    }

    public MediaPeriodId getDummyFirstMediaPeriodId(boolean z, Window window, Period period) {
        if (this.timeline.isEmpty()) {
            return DUMMY_MEDIA_PERIOD_ID;
        }
        int firstWindowIndex = this.timeline.getFirstWindowIndex(z);
        int i = this.timeline.getWindow(firstWindowIndex, window).firstPeriodIndex;
        int indexOfPeriod = this.timeline.getIndexOfPeriod(this.periodId.periodUid);
        long j = -1;
        if (indexOfPeriod != -1 && firstWindowIndex == this.timeline.getPeriod(indexOfPeriod, period).windowIndex) {
            j = this.periodId.windowSequenceNumber;
        }
        return new MediaPeriodId(this.timeline.getUidOfPeriod(i), j);
    }

    public PlaybackInfo copyWithNewPosition(MediaPeriodId mediaPeriodId, long j, long j2, long j3) {
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, mediaPeriodId, j, mediaPeriodId.isAd() ? j2 : -9223372036854775807L, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, j3, j);
        return playbackInfo;
    }

    public PlaybackInfo copyWithTimeline(Timeline timeline2) {
        Timeline timeline3 = timeline2;
        PlaybackInfo playbackInfo = new PlaybackInfo(timeline2, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }

    public PlaybackInfo copyWithPlaybackState(int i) {
        int i2 = i;
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, this.periodId, this.startPositionUs, this.contentPositionUs, i2, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }

    public PlaybackInfo copyWithPlaybackError(ExoPlaybackException exoPlaybackException) {
        ExoPlaybackException exoPlaybackException2 = exoPlaybackException;
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, exoPlaybackException2, this.isLoading, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }

    public PlaybackInfo copyWithIsLoading(boolean z) {
        boolean z2 = z;
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.playbackError, z2, this.trackGroups, this.trackSelectorResult, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }

    public PlaybackInfo copyWithTrackInfo(TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult2) {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        TrackSelectorResult trackSelectorResult3 = trackSelectorResult2;
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.playbackError, this.isLoading, trackGroupArray2, trackSelectorResult3, this.loadingMediaPeriodId, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }

    public PlaybackInfo copyWithLoadingMediaPeriodId(MediaPeriodId mediaPeriodId) {
        MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        PlaybackInfo playbackInfo = new PlaybackInfo(this.timeline, this.periodId, this.startPositionUs, this.contentPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, mediaPeriodId2, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs);
        return playbackInfo;
    }
}
