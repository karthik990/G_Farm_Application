package com.google.android.exoplayer2.text.ssa;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

final class SsaSubtitle implements Subtitle {
    private final List<Long> cueTimesUs;
    private final List<List<Cue>> cues;

    public SsaSubtitle(List<List<Cue>> list, List<Long> list2) {
        this.cues = list;
        this.cueTimesUs = list2;
    }

    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.cueTimesUs, Long.valueOf(j), false, false);
        if (binarySearchCeil < this.cueTimesUs.size()) {
            return binarySearchCeil;
        }
        return -1;
    }

    public int getEventTimeCount() {
        return this.cueTimesUs.size();
    }

    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.cueTimesUs.size()) {
            z = false;
        }
        Assertions.checkArgument(z);
        return ((Long) this.cueTimesUs.get(i)).longValue();
    }

    public List<Cue> getCues(long j) {
        int binarySearchFloor = Util.binarySearchFloor(this.cueTimesUs, Long.valueOf(j), true, false);
        if (binarySearchFloor == -1) {
            return Collections.emptyList();
        }
        return (List) this.cues.get(binarySearchFloor);
    }
}
