package com.google.android.exoplayer2;

import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.util.Util;

final class MediaPeriodInfo {
    public final long contentPositionUs;
    public final long durationUs;
    public final long endPositionUs;

    /* renamed from: id */
    public final MediaPeriodId f1475id;
    public final boolean isFinal;
    public final boolean isLastInTimelinePeriod;
    public final long startPositionUs;

    MediaPeriodInfo(MediaPeriodId mediaPeriodId, long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this.f1475id = mediaPeriodId;
        this.startPositionUs = j;
        this.contentPositionUs = j2;
        this.endPositionUs = j3;
        this.durationUs = j4;
        this.isLastInTimelinePeriod = z;
        this.isFinal = z2;
    }

    public MediaPeriodInfo copyWithStartPositionUs(long j) {
        if (j == this.startPositionUs) {
            return this;
        }
        MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(this.f1475id, j, this.contentPositionUs, this.endPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        return mediaPeriodInfo;
    }

    public MediaPeriodInfo copyWithContentPositionUs(long j) {
        if (j == this.contentPositionUs) {
            return this;
        }
        MediaPeriodInfo mediaPeriodInfo = new MediaPeriodInfo(this.f1475id, this.startPositionUs, j, this.endPositionUs, this.durationUs, this.isLastInTimelinePeriod, this.isFinal);
        return mediaPeriodInfo;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MediaPeriodInfo mediaPeriodInfo = (MediaPeriodInfo) obj;
        if (!(this.startPositionUs == mediaPeriodInfo.startPositionUs && this.contentPositionUs == mediaPeriodInfo.contentPositionUs && this.endPositionUs == mediaPeriodInfo.endPositionUs && this.durationUs == mediaPeriodInfo.durationUs && this.isLastInTimelinePeriod == mediaPeriodInfo.isLastInTimelinePeriod && this.isFinal == mediaPeriodInfo.isFinal && Util.areEqual(this.f1475id, mediaPeriodInfo.f1475id))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((((((527 + this.f1475id.hashCode()) * 31) + ((int) this.startPositionUs)) * 31) + ((int) this.contentPositionUs)) * 31) + ((int) this.endPositionUs)) * 31) + ((int) this.durationUs)) * 31) + (this.isLastInTimelinePeriod ? 1 : 0)) * 31) + (this.isFinal ? 1 : 0);
    }
}
