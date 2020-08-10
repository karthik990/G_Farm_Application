package com.google.android.exoplayer2.offline;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class StreamKey implements Comparable<StreamKey>, Parcelable {
    public static final Creator<StreamKey> CREATOR = new Creator<StreamKey>() {
        public StreamKey createFromParcel(Parcel parcel) {
            return new StreamKey(parcel);
        }

        public StreamKey[] newArray(int i) {
            return new StreamKey[i];
        }
    };
    public final int groupIndex;
    public final int periodIndex;
    public final int trackIndex;

    public int describeContents() {
        return 0;
    }

    public StreamKey(int i, int i2) {
        this(0, i, i2);
    }

    public StreamKey(int i, int i2, int i3) {
        this.periodIndex = i;
        this.groupIndex = i2;
        this.trackIndex = i3;
    }

    StreamKey(Parcel parcel) {
        this.periodIndex = parcel.readInt();
        this.groupIndex = parcel.readInt();
        this.trackIndex = parcel.readInt();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.periodIndex);
        String str = ".";
        sb.append(str);
        sb.append(this.groupIndex);
        sb.append(str);
        sb.append(this.trackIndex);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StreamKey streamKey = (StreamKey) obj;
        if (!(this.periodIndex == streamKey.periodIndex && this.groupIndex == streamKey.groupIndex && this.trackIndex == streamKey.trackIndex)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((this.periodIndex * 31) + this.groupIndex) * 31) + this.trackIndex;
    }

    public int compareTo(StreamKey streamKey) {
        int i = this.periodIndex - streamKey.periodIndex;
        if (i != 0) {
            return i;
        }
        int i2 = this.groupIndex - streamKey.groupIndex;
        return i2 == 0 ? this.trackIndex - streamKey.trackIndex : i2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.periodIndex);
        parcel.writeInt(this.groupIndex);
        parcel.writeInt(this.trackIndex);
    }
}
