package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C1996C;

public final class TimestampAdjuster {
    public static final long DO_NOT_OFFSET = Long.MAX_VALUE;
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    private long firstSampleTimestampUs;
    private volatile long lastSampleTimestampUs = C1996C.TIME_UNSET;
    private long timestampOffsetUs;

    public TimestampAdjuster(long j) {
        setFirstSampleTimestampUs(j);
    }

    public synchronized void setFirstSampleTimestampUs(long j) {
        Assertions.checkState(this.lastSampleTimestampUs == C1996C.TIME_UNSET);
        this.firstSampleTimestampUs = j;
    }

    public long getFirstSampleTimestampUs() {
        return this.firstSampleTimestampUs;
    }

    public long getLastAdjustedTimestampUs() {
        if (this.lastSampleTimestampUs != C1996C.TIME_UNSET) {
            return this.timestampOffsetUs + this.lastSampleTimestampUs;
        }
        long j = this.firstSampleTimestampUs;
        return j != Long.MAX_VALUE ? j : C1996C.TIME_UNSET;
    }

    public long getTimestampOffsetUs() {
        if (this.firstSampleTimestampUs == Long.MAX_VALUE) {
            return 0;
        }
        return this.lastSampleTimestampUs == C1996C.TIME_UNSET ? C1996C.TIME_UNSET : this.timestampOffsetUs;
    }

    public void reset() {
        this.lastSampleTimestampUs = C1996C.TIME_UNSET;
    }

    public long adjustTsTimestamp(long j) {
        if (j == C1996C.TIME_UNSET) {
            return C1996C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C1996C.TIME_UNSET) {
            long usToPts = usToPts(this.lastSampleTimestampUs);
            long j2 = (4294967296L + usToPts) / MAX_PTS_PLUS_ONE;
            long j3 = ((j2 - 1) * MAX_PTS_PLUS_ONE) + j;
            j += j2 * MAX_PTS_PLUS_ONE;
            if (Math.abs(j3 - usToPts) < Math.abs(j - usToPts)) {
                j = j3;
            }
        }
        return adjustSampleTimestamp(ptsToUs(j));
    }

    public long adjustSampleTimestamp(long j) {
        if (j == C1996C.TIME_UNSET) {
            return C1996C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C1996C.TIME_UNSET) {
            this.lastSampleTimestampUs = j;
        } else {
            long j2 = this.firstSampleTimestampUs;
            if (j2 != Long.MAX_VALUE) {
                this.timestampOffsetUs = j2 - j;
            }
            synchronized (this) {
                this.lastSampleTimestampUs = j;
                notifyAll();
            }
        }
        return j + this.timestampOffsetUs;
    }

    public synchronized void waitUntilInitialized() throws InterruptedException {
        while (this.lastSampleTimestampUs == C1996C.TIME_UNSET) {
            wait();
        }
    }

    public static long ptsToUs(long j) {
        return (j * 1000000) / 90000;
    }

    public static long usToPts(long j) {
        return (j * 90000) / 1000000;
    }
}
