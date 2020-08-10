package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.Format;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Track {
    public static final int TRANSFORMATION_CEA608_CDAT = 1;
    public static final int TRANSFORMATION_NONE = 0;
    public final long durationUs;
    public final long[] editListDurations;
    public final long[] editListMediaTimes;
    public final Format format;

    /* renamed from: id */
    public final int f1479id;
    public final long movieTimescale;
    public final int nalUnitLengthFieldLength;
    private final TrackEncryptionBox[] sampleDescriptionEncryptionBoxes;
    public final int sampleTransformation;
    public final long timescale;
    public final int type;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transformation {
    }

    public Track(int i, int i2, long j, long j2, long j3, Format format2, int i3, TrackEncryptionBox[] trackEncryptionBoxArr, int i4, long[] jArr, long[] jArr2) {
        this.f1479id = i;
        this.type = i2;
        this.timescale = j;
        this.movieTimescale = j2;
        this.durationUs = j3;
        this.format = format2;
        this.sampleTransformation = i3;
        this.sampleDescriptionEncryptionBoxes = trackEncryptionBoxArr;
        this.nalUnitLengthFieldLength = i4;
        this.editListDurations = jArr;
        this.editListMediaTimes = jArr2;
    }

    public TrackEncryptionBox getSampleDescriptionEncryptionBox(int i) {
        TrackEncryptionBox[] trackEncryptionBoxArr = this.sampleDescriptionEncryptionBoxes;
        if (trackEncryptionBoxArr == null) {
            return null;
        }
        return trackEncryptionBoxArr[i];
    }

    public Track copyWithFormat(Format format2) {
        Track track = new Track(this.f1479id, this.type, this.timescale, this.movieTimescale, this.durationUs, format2, this.sampleTransformation, this.sampleDescriptionEncryptionBoxes, this.nalUnitLengthFieldLength, this.editListDurations, this.editListMediaTimes);
        return track;
    }
}
