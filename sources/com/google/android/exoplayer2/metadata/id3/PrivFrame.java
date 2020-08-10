package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class PrivFrame extends Id3Frame {
    public static final Creator<PrivFrame> CREATOR = new Creator<PrivFrame>() {
        public PrivFrame createFromParcel(Parcel parcel) {
            return new PrivFrame(parcel);
        }

        public PrivFrame[] newArray(int i) {
            return new PrivFrame[i];
        }
    };

    /* renamed from: ID */
    public static final String f1490ID = "PRIV";
    public final String owner;
    public final byte[] privateData;

    public PrivFrame(String str, byte[] bArr) {
        super(f1490ID);
        this.owner = str;
        this.privateData = bArr;
    }

    PrivFrame(Parcel parcel) {
        super(f1490ID);
        this.owner = (String) Util.castNonNull(parcel.readString());
        this.privateData = (byte[]) Util.castNonNull(parcel.createByteArray());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrivFrame privFrame = (PrivFrame) obj;
        if (!Util.areEqual(this.owner, privFrame.owner) || !Arrays.equals(this.privateData, privFrame.privateData)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.owner;
        return ((527 + (str != null ? str.hashCode() : 0)) * 31) + Arrays.hashCode(this.privateData);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f1487id);
        sb.append(": owner=");
        sb.append(this.owner);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.owner);
        parcel.writeByteArray(this.privateData);
    }
}
