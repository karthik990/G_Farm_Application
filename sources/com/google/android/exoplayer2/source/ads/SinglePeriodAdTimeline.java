package com.google.android.exoplayer2.source.ads;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.util.Assertions;

public final class SinglePeriodAdTimeline extends ForwardingTimeline {
    private final AdPlaybackState adPlaybackState;

    public SinglePeriodAdTimeline(Timeline timeline, AdPlaybackState adPlaybackState2) {
        super(timeline);
        boolean z = false;
        Assertions.checkState(timeline.getPeriodCount() == 1);
        if (timeline.getWindowCount() == 1) {
            z = true;
        }
        Assertions.checkState(z);
        this.adPlaybackState = adPlaybackState2;
    }

    public Period getPeriod(int i, Period period, boolean z) {
        this.timeline.getPeriod(i, period, z);
        period.set(period.f1476id, period.uid, period.windowIndex, period.durationUs, period.getPositionInWindowUs(), this.adPlaybackState);
        return period;
    }

    public Window getWindow(int i, Window window, long j) {
        Window window2 = super.getWindow(i, window, j);
        if (window2.durationUs == C1996C.TIME_UNSET) {
            window2.durationUs = this.adPlaybackState.contentDurationUs;
        }
        return window2;
    }
}
