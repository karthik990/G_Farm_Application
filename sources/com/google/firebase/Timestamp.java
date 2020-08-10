package com.google.firebase;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.airbnb.lottie.utils.C0894Utils;
import com.google.common.base.Preconditions;
import java.util.Date;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Timestamp implements Comparable<Timestamp>, Parcelable {
    public static final Creator<Timestamp> CREATOR = new Creator<Timestamp>() {
        public Timestamp createFromParcel(Parcel parcel) {
            return new Timestamp(parcel);
        }

        public Timestamp[] newArray(int i) {
            return new Timestamp[i];
        }
    };
    private final int nanoseconds;
    private final long seconds;

    public int describeContents() {
        return 0;
    }

    public Timestamp(long j, int i) {
        validateRange(j, i);
        this.seconds = j;
        this.nanoseconds = i;
    }

    protected Timestamp(Parcel parcel) {
        this.seconds = parcel.readLong();
        this.nanoseconds = parcel.readInt();
    }

    public Timestamp(Date date) {
        long time = date.getTime();
        long j = time / 1000;
        int i = ((int) (time % 1000)) * 1000000;
        if (i < 0) {
            j--;
            i += C0894Utils.SECOND_IN_NANOS;
        }
        validateRange(j, i);
        this.seconds = j;
        this.nanoseconds = i;
    }

    public static Timestamp now() {
        return new Timestamp(new Date());
    }

    public long getSeconds() {
        return this.seconds;
    }

    public int getNanoseconds() {
        return this.nanoseconds;
    }

    public Date toDate() {
        return new Date((this.seconds * 1000) + ((long) (this.nanoseconds / 1000000)));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.seconds);
        parcel.writeInt(this.nanoseconds);
    }

    public int compareTo(Timestamp timestamp) {
        long j = this.seconds;
        long j2 = timestamp.seconds;
        if (j == j2) {
            return Integer.signum(this.nanoseconds - timestamp.nanoseconds);
        }
        return Long.signum(j - j2);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Timestamp)) {
            return false;
        }
        if (compareTo((Timestamp) obj) != 0) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = this.seconds;
        return (((((int) j) * 37 * 37) + ((int) (j >> 32))) * 37) + this.nanoseconds;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Timestamp(seconds=");
        sb.append(this.seconds);
        sb.append(", nanoseconds=");
        sb.append(this.nanoseconds);
        sb.append(")");
        return sb.toString();
    }

    private static void validateRange(long j, int i) {
        boolean z = true;
        String str = "Timestamp nanoseconds out of range: %s";
        Preconditions.checkArgument(i >= 0, str, i);
        Preconditions.checkArgument(((double) i) < 1.0E9d, str, i);
        String str2 = "Timestamp seconds out of range: %s";
        Preconditions.checkArgument(j >= -62135596800L, str2, j);
        if (j >= 253402300800L) {
            z = false;
        }
        Preconditions.checkArgument(z, str2, j);
    }
}
