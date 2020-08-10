package com.google.android.exoplayer2.text.webvtt;

import android.text.SpannableStringBuilder;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.webvtt.WebvttCue.Builder;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.mobiroller.constants.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class WebvttSubtitle implements Subtitle {
    private final long[] cueTimesUs = new long[(this.numCues * 2)];
    private final List<WebvttCue> cues;
    private final int numCues;
    private final long[] sortedCueTimesUs;

    public WebvttSubtitle(List<WebvttCue> list) {
        this.cues = list;
        this.numCues = list.size();
        for (int i = 0; i < this.numCues; i++) {
            WebvttCue webvttCue = (WebvttCue) list.get(i);
            int i2 = i * 2;
            this.cueTimesUs[i2] = webvttCue.startTime;
            this.cueTimesUs[i2 + 1] = webvttCue.endTime;
        }
        long[] jArr = this.cueTimesUs;
        this.sortedCueTimesUs = Arrays.copyOf(jArr, jArr.length);
        Arrays.sort(this.sortedCueTimesUs);
    }

    public int getNextEventTimeIndex(long j) {
        int binarySearchCeil = Util.binarySearchCeil(this.sortedCueTimesUs, j, false, false);
        if (binarySearchCeil < this.sortedCueTimesUs.length) {
            return binarySearchCeil;
        }
        return -1;
    }

    public int getEventTimeCount() {
        return this.sortedCueTimesUs.length;
    }

    public long getEventTime(int i) {
        boolean z = true;
        Assertions.checkArgument(i >= 0);
        if (i >= this.sortedCueTimesUs.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return this.sortedCueTimesUs[i];
    }

    public List<Cue> getCues(long j) {
        ArrayList arrayList = new ArrayList();
        SpannableStringBuilder spannableStringBuilder = null;
        WebvttCue webvttCue = null;
        for (int i = 0; i < this.numCues; i++) {
            long[] jArr = this.cueTimesUs;
            int i2 = i * 2;
            if (jArr[i2] <= j && j < jArr[i2 + 1]) {
                WebvttCue webvttCue2 = (WebvttCue) this.cues.get(i);
                if (!webvttCue2.isNormalCue()) {
                    arrayList.add(webvttCue2);
                } else if (webvttCue == null) {
                    webvttCue = webvttCue2;
                } else {
                    String str = Constants.NEW_LINE;
                    if (spannableStringBuilder == null) {
                        spannableStringBuilder = new SpannableStringBuilder();
                        spannableStringBuilder.append((CharSequence) Assertions.checkNotNull(webvttCue.text)).append(str).append((CharSequence) Assertions.checkNotNull(webvttCue2.text));
                    } else {
                        spannableStringBuilder.append(str).append((CharSequence) Assertions.checkNotNull(webvttCue2.text));
                    }
                }
            }
        }
        if (spannableStringBuilder != null) {
            arrayList.add(new Builder().setText(spannableStringBuilder).build());
        } else if (webvttCue != null) {
            arrayList.add(webvttCue);
        }
        return arrayList;
    }
}
