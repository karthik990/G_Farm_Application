package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;

interface Seeker extends SeekMap {

    public static class UnseekableSeeker extends Unseekable implements Seeker {
        public long getDataEndPosition() {
            return -1;
        }

        public long getTimeUs(long j) {
            return 0;
        }

        public UnseekableSeeker() {
            super(C1996C.TIME_UNSET);
        }
    }

    long getDataEndPosition();

    long getTimeUs(long j);
}
