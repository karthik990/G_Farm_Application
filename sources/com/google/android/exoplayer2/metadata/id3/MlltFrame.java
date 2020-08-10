package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class MlltFrame extends Id3Frame {
    public static final Creator<MlltFrame> CREATOR = new Creator<MlltFrame>() {
        public MlltFrame createFromParcel(Parcel parcel) {
            return new MlltFrame(parcel);
        }

        public MlltFrame[] newArray(int i) {
            return new MlltFrame[i];
        }
    };

    /* renamed from: ID */
    public static final String f1489ID = "MLLT";
    public final int bytesBetweenReference;
    public final int[] bytesDeviations;
    public final int millisecondsBetweenReference;
    public final int[] millisecondsDeviations;
    public final int mpegFramesBetweenReference;

    public int describeContents() {
        return 0;
    }

    public MlltFrame(int i, int i2, int i3, int[] iArr, int[] iArr2) {
        super(f1489ID);
        this.mpegFramesBetweenReference = i;
        this.bytesBetweenReference = i2;
        this.millisecondsBetweenReference = i3;
        this.bytesDeviations = iArr;
        this.millisecondsDeviations = iArr2;
    }

    MlltFrame(Parcel parcel) {
        super(f1489ID);
        this.mpegFramesBetweenReference = parcel.readInt();
        this.bytesBetweenReference = parcel.readInt();
        this.millisecondsBetweenReference = parcel.readInt();
        this.bytesDeviations = (int[]) Util.castNonNull(parcel.createIntArray());
        this.millisecondsDeviations = (int[]) Util.castNonNull(parcel.createIntArray());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MlltFrame mlltFrame = (MlltFrame) obj;
        if (!(this.mpegFramesBetweenReference == mlltFrame.mpegFramesBetweenReference && this.bytesBetweenReference == mlltFrame.bytesBetweenReference && this.millisecondsBetweenReference == mlltFrame.millisecondsBetweenReference && Arrays.equals(this.bytesDeviations, mlltFrame.bytesDeviations) && Arrays.equals(this.millisecondsDeviations, mlltFrame.millisecondsDeviations))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((527 + this.mpegFramesBetweenReference) * 31) + this.bytesBetweenReference) * 31) + this.millisecondsBetweenReference) * 31) + Arrays.hashCode(this.bytesDeviations)) * 31) + Arrays.hashCode(this.millisecondsDeviations);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mpegFramesBetweenReference);
        parcel.writeInt(this.bytesBetweenReference);
        parcel.writeInt(this.millisecondsBetweenReference);
        parcel.writeIntArray(this.bytesDeviations);
        parcel.writeIntArray(this.millisecondsDeviations);
    }
}
