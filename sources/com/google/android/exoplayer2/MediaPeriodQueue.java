package com.google.android.exoplayer2;

import android.util.Pair;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;

final class MediaPeriodQueue {
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private int length;
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    private final Period period = new Period();
    private MediaPeriodHolder playing;
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private Timeline timeline = Timeline.EMPTY;
    private final Window window = new Window();

    private boolean areDurationsCompatible(long j, long j2) {
        return j == C1996C.TIME_UNSET || j == j2;
    }

    public void setTimeline(Timeline timeline2) {
        this.timeline = timeline2;
    }

    public boolean updateRepeatMode(int i) {
        this.repeatMode = i;
        return updateForPlaybackModeChange();
    }

    public boolean updateShuffleModeEnabled(boolean z) {
        this.shuffleModeEnabled = z;
        return updateForPlaybackModeChange();
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder != null && mediaPeriodHolder.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long j) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.reevaluateBuffer(j);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null || (!mediaPeriodHolder.info.isFinal && this.loading.isFullyBuffered() && this.loading.info.durationUs != C1996C.TIME_UNSET && this.length < 100);
    }

    public MediaPeriodInfo getNextMediaPeriodInfo(long j, PlaybackInfo playbackInfo) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(mediaPeriodHolder, j);
    }

    public MediaPeriodHolder enqueueNextMediaPeriodHolder(RendererCapabilities[] rendererCapabilitiesArr, TrackSelector trackSelector, Allocator allocator, MediaSource mediaSource, MediaPeriodInfo mediaPeriodInfo, TrackSelectorResult trackSelectorResult) {
        MediaPeriodInfo mediaPeriodInfo2 = mediaPeriodInfo;
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        long j = mediaPeriodHolder == null ? (!mediaPeriodInfo2.f1475id.isAd() || mediaPeriodInfo2.contentPositionUs == C1996C.TIME_UNSET) ? 0 : mediaPeriodInfo2.contentPositionUs : (mediaPeriodHolder.getRendererOffset() + this.loading.info.durationUs) - mediaPeriodInfo2.startPositionUs;
        MediaPeriodHolder mediaPeriodHolder2 = new MediaPeriodHolder(rendererCapabilitiesArr, j, trackSelector, allocator, mediaSource, mediaPeriodInfo, trackSelectorResult);
        MediaPeriodHolder mediaPeriodHolder3 = this.loading;
        if (mediaPeriodHolder3 != null) {
            mediaPeriodHolder3.setNext(mediaPeriodHolder2);
        } else {
            this.playing = mediaPeriodHolder2;
            this.reading = mediaPeriodHolder2;
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder2;
        this.length++;
        return mediaPeriodHolder2;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.reading;
        Assertions.checkState((mediaPeriodHolder == null || mediaPeriodHolder.getNext() == null) ? false : true);
        this.reading = this.reading.getNext();
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return null;
        }
        if (mediaPeriodHolder == this.reading) {
            this.reading = mediaPeriodHolder.getNext();
        }
        this.playing.release();
        this.length--;
        if (this.length == 0) {
            this.loading = null;
            this.oldFrontPeriodUid = this.playing.uid;
            this.oldFrontPeriodWindowSequenceNumber = this.playing.info.f1475id.windowSequenceNumber;
        }
        this.playing = this.playing.getNext();
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        boolean z = false;
        Assertions.checkState(mediaPeriodHolder != null);
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.getNext() != null) {
            mediaPeriodHolder = mediaPeriodHolder.getNext();
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                z = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.setNext(null);
        return z;
    }

    public void clear(boolean z) {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder != null) {
            this.oldFrontPeriodUid = z ? mediaPeriodHolder.uid : null;
            this.oldFrontPeriodWindowSequenceNumber = mediaPeriodHolder.info.f1475id.windowSequenceNumber;
            removeAfter(mediaPeriodHolder);
            mediaPeriodHolder.release();
        } else if (!z) {
            this.oldFrontPeriodUid = null;
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
    }

    public boolean updateQueuedPeriods(long j, long j2) {
        MediaPeriodInfo mediaPeriodInfo;
        long j3;
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        MediaPeriodHolder mediaPeriodHolder2 = null;
        while (true) {
            MediaPeriodHolder mediaPeriodHolder3 = mediaPeriodHolder2;
            mediaPeriodHolder2 = mediaPeriodHolder;
            MediaPeriodHolder mediaPeriodHolder4 = mediaPeriodHolder3;
            boolean z = true;
            if (mediaPeriodHolder2 == null) {
                return true;
            }
            MediaPeriodInfo mediaPeriodInfo2 = mediaPeriodHolder2.info;
            if (mediaPeriodHolder4 == null) {
                mediaPeriodInfo = getUpdatedMediaPeriodInfo(mediaPeriodInfo2);
            } else {
                MediaPeriodInfo followingMediaPeriodInfo = getFollowingMediaPeriodInfo(mediaPeriodHolder4, j);
                if (followingMediaPeriodInfo == null) {
                    return !removeAfter(mediaPeriodHolder4);
                }
                if (!canKeepMediaPeriodHolder(mediaPeriodInfo2, followingMediaPeriodInfo)) {
                    return !removeAfter(mediaPeriodHolder4);
                }
                mediaPeriodInfo = followingMediaPeriodInfo;
            }
            mediaPeriodHolder2.info = mediaPeriodInfo.copyWithContentPositionUs(mediaPeriodInfo2.contentPositionUs);
            if (!areDurationsCompatible(mediaPeriodInfo2.durationUs, mediaPeriodInfo.durationUs)) {
                if (mediaPeriodInfo.durationUs == C1996C.TIME_UNSET) {
                    j3 = Long.MAX_VALUE;
                } else {
                    j3 = mediaPeriodHolder2.toRendererTime(mediaPeriodInfo.durationUs);
                }
                boolean z2 = mediaPeriodHolder2 == this.reading && (j2 == Long.MIN_VALUE || j2 >= j3);
                if (removeAfter(mediaPeriodHolder2) || z2) {
                    z = false;
                }
                return z;
            }
            mediaPeriodHolder = mediaPeriodHolder2.getNext();
        }
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo) {
        MediaPeriodId mediaPeriodId = mediaPeriodInfo.f1475id;
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        this.timeline.getPeriodByUid(mediaPeriodInfo.f1475id.periodUid, this.period);
        long durationUs = mediaPeriodId.isAd() ? this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) : (mediaPeriodInfo.endPositionUs == C1996C.TIME_UNSET || mediaPeriodInfo.endPositionUs == Long.MIN_VALUE) ? this.period.getDurationUs() : mediaPeriodInfo.endPositionUs;
        MediaPeriodInfo mediaPeriodInfo2 = new MediaPeriodInfo(mediaPeriodId, mediaPeriodInfo.startPositionUs, mediaPeriodInfo.contentPositionUs, mediaPeriodInfo.endPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
        return mediaPeriodInfo2;
    }

    public MediaPeriodId resolveMediaPeriodIdForAds(Object obj, long j) {
        return resolveMediaPeriodIdForAds(obj, j, resolvePeriodIndexToWindowSequenceNumber(obj));
    }

    private MediaPeriodId resolveMediaPeriodIdForAds(Object obj, long j, long j2) {
        this.timeline.getPeriodByUid(obj, this.period);
        int adGroupIndexForPositionUs = this.period.getAdGroupIndexForPositionUs(j);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaPeriodId(obj, j2, this.period.getAdGroupIndexAfterPositionUs(j));
        }
        MediaPeriodId mediaPeriodId = new MediaPeriodId(obj, adGroupIndexForPositionUs, this.period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j2);
        return mediaPeriodId;
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Object obj) {
        int i = this.timeline.getPeriodByUid(obj, this.period).windowIndex;
        Object obj2 = this.oldFrontPeriodUid;
        if (obj2 != null) {
            int indexOfPeriod = this.timeline.getIndexOfPeriod(obj2);
            if (indexOfPeriod != -1 && this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
                return this.oldFrontPeriodWindowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder = this.playing; mediaPeriodHolder != null; mediaPeriodHolder = mediaPeriodHolder.getNext()) {
            if (mediaPeriodHolder.uid.equals(obj)) {
                return mediaPeriodHolder.info.f1475id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder2 = this.playing; mediaPeriodHolder2 != null; mediaPeriodHolder2 = mediaPeriodHolder2.getNext()) {
            int indexOfPeriod2 = this.timeline.getIndexOfPeriod(mediaPeriodHolder2.uid);
            if (indexOfPeriod2 != -1 && this.timeline.getPeriod(indexOfPeriod2, this.period).windowIndex == i) {
                return mediaPeriodHolder2.info.f1475id.windowSequenceNumber;
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        if (this.playing == null) {
            this.oldFrontPeriodUid = obj;
            this.oldFrontPeriodWindowSequenceNumber = j;
        }
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodInfo mediaPeriodInfo, MediaPeriodInfo mediaPeriodInfo2) {
        return mediaPeriodInfo.startPositionUs == mediaPeriodInfo2.startPositionUs && mediaPeriodInfo.f1475id.equals(mediaPeriodInfo2.f1475id);
    }

    private boolean updateForPlaybackModeChange() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return true;
        }
        int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodHolder.uid);
        while (true) {
            indexOfPeriod = this.timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (mediaPeriodHolder.getNext() != null && !mediaPeriodHolder.info.isLastInTimelinePeriod) {
                mediaPeriodHolder = mediaPeriodHolder.getNext();
            }
            MediaPeriodHolder next = mediaPeriodHolder.getNext();
            if (indexOfPeriod == -1 || next == null || this.timeline.getIndexOfPeriod(next.uid) != indexOfPeriod) {
                boolean removeAfter = removeAfter(mediaPeriodHolder);
                mediaPeriodHolder.info = getUpdatedMediaPeriodInfo(mediaPeriodHolder.info);
            } else {
                mediaPeriodHolder = next;
            }
        }
        boolean removeAfter2 = removeAfter(mediaPeriodHolder);
        mediaPeriodHolder.info = getUpdatedMediaPeriodInfo(mediaPeriodHolder.info);
        return !removeAfter2;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    private MediaPeriodInfo getFollowingMediaPeriodInfo(MediaPeriodHolder mediaPeriodHolder, long j) {
        long j2;
        long j3;
        long j4;
        Object obj;
        long j5;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder.info;
        long rendererOffset = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - j;
        long j6 = 0;
        MediaPeriodInfo mediaPeriodInfo2 = null;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodInfo.f1475id.periodUid);
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int i = this.timeline.getPeriod(nextPeriodIndex, this.period, true).windowIndex;
            Object obj2 = this.period.uid;
            long j7 = mediaPeriodInfo.f1475id.windowSequenceNumber;
            if (this.timeline.getWindow(i, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair periodPosition = this.timeline.getPeriodPosition(this.window, this.period, i, C1996C.TIME_UNSET, Math.max(0, rendererOffset));
                if (periodPosition == null) {
                    return null;
                }
                Object obj3 = periodPosition.first;
                long longValue = ((Long) periodPosition.second).longValue();
                MediaPeriodHolder next = mediaPeriodHolder.getNext();
                if (next == null || !next.uid.equals(obj3)) {
                    j5 = this.nextWindowSequenceNumber;
                    this.nextWindowSequenceNumber = 1 + j5;
                } else {
                    j5 = next.info.f1475id.windowSequenceNumber;
                }
                j6 = longValue;
                j3 = C1996C.TIME_UNSET;
                j4 = j5;
                obj = obj3;
            } else {
                obj = obj2;
                j4 = j7;
                j3 = 0;
            }
            return getMediaPeriodInfo(resolveMediaPeriodIdForAds(obj, j6, j4), j3, j6);
        }
        MediaPeriodId mediaPeriodId = mediaPeriodInfo.f1475id;
        this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (mediaPeriodId.isAd()) {
            int i2 = mediaPeriodId.adGroupIndex;
            int adCountInAdGroup = this.period.getAdCountInAdGroup(i2);
            if (adCountInAdGroup == -1) {
                return null;
            }
            int nextAdIndexToPlay = this.period.getNextAdIndexToPlay(i2, mediaPeriodId.adIndexInAdGroup);
            if (nextAdIndexToPlay < adCountInAdGroup) {
                if (this.period.isAdAvailable(i2, nextAdIndexToPlay)) {
                    mediaPeriodInfo2 = getMediaPeriodInfoForAd(mediaPeriodId.periodUid, i2, nextAdIndexToPlay, mediaPeriodInfo.contentPositionUs, mediaPeriodId.windowSequenceNumber);
                }
                return mediaPeriodInfo2;
            }
            long j8 = mediaPeriodInfo.contentPositionUs;
            if (j8 == C1996C.TIME_UNSET) {
                Timeline timeline2 = this.timeline;
                Window window2 = this.window;
                Period period2 = this.period;
                Pair periodPosition2 = timeline2.getPeriodPosition(window2, period2, period2.windowIndex, C1996C.TIME_UNSET, Math.max(0, rendererOffset));
                if (periodPosition2 == null) {
                    return null;
                }
                j2 = ((Long) periodPosition2.second).longValue();
            } else {
                j2 = j8;
            }
            return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, j2, mediaPeriodId.windowSequenceNumber);
        }
        int adGroupIndexForPositionUs = this.period.getAdGroupIndexForPositionUs(mediaPeriodInfo.endPositionUs);
        if (adGroupIndexForPositionUs == -1) {
            return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
        }
        int firstAdIndexToPlay = this.period.getFirstAdIndexToPlay(adGroupIndexForPositionUs);
        if (this.period.isAdAvailable(adGroupIndexForPositionUs, firstAdIndexToPlay)) {
            mediaPeriodInfo2 = getMediaPeriodInfoForAd(mediaPeriodId.periodUid, adGroupIndexForPositionUs, firstAdIndexToPlay, mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
        }
        return mediaPeriodInfo2;
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaPeriodId mediaPeriodId, long j, long j2) {
        this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (!mediaPeriodId.isAd()) {
            return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, j2, mediaPeriodId.windowSequenceNumber);
        } else if (!this.period.isAdAvailable(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup)) {
            return null;
        } else {
            return getMediaPeriodInfoForAd(mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j, mediaPeriodId.windowSequenceNumber);
        }
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Object obj, int i, int i2, long j, long j2) {
        MediaPeriodId mediaPeriodId = new MediaPeriodId(obj, i, i2, j2);
        MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(mediaPeriodId, i2 == this.period.getFirstAdIndexToPlay(i) ? this.period.getAdResumePositionUs() : 0, j, C1996C.TIME_UNSET, this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup), false, false);
        return mediaPeriodInfo;
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(Object obj, long j, long j2) {
        long j3;
        int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(j);
        Object obj2 = obj;
        MediaPeriodId mediaPeriodId = new MediaPeriodId(obj, j2, adGroupIndexAfterPositionUs);
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        long adGroupTimeUs = adGroupIndexAfterPositionUs != -1 ? this.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs) : -9223372036854775807L;
        if (adGroupTimeUs == C1996C.TIME_UNSET || adGroupTimeUs == Long.MIN_VALUE) {
            j3 = this.period.durationUs;
        } else {
            j3 = adGroupTimeUs;
        }
        MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(mediaPeriodId, j, C1996C.TIME_UNSET, adGroupTimeUs, j3, isLastInPeriod, isLastInTimeline);
        return mediaPeriodInfo;
    }

    private boolean isLastInPeriod(MediaPeriodId mediaPeriodId) {
        return !mediaPeriodId.isAd() && mediaPeriodId.nextAdGroupIndex == -1;
    }

    private boolean isLastInTimeline(MediaPeriodId mediaPeriodId, boolean z) {
        int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        return !this.timeline.getWindow(this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex, this.window).isDynamic && this.timeline.isLastPeriod(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && z;
    }
}
