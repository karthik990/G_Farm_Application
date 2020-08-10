package com.wdullaer.materialdatetimepicker.time;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Timepoint implements Parcelable, Comparable<Timepoint> {
    public static final Creator<Timepoint> CREATOR = new Creator<Timepoint>() {
        public Timepoint createFromParcel(Parcel parcel) {
            return new Timepoint(parcel);
        }

        public Timepoint[] newArray(int i) {
            return new Timepoint[i];
        }
    };
    private int hour;
    private int minute;
    private int second;

    public enum TYPE {
        HOUR,
        MINUTE,
        SECOND
    }

    public int describeContents() {
        return 0;
    }

    public Timepoint(Timepoint timepoint) {
        this(timepoint.hour, timepoint.minute, timepoint.second);
    }

    public Timepoint(int i, int i2, int i3) {
        this.hour = i % 24;
        this.minute = i2 % 60;
        this.second = i3 % 60;
    }

    public Timepoint(int i, int i2) {
        this(i, i2, 0);
    }

    public Timepoint(int i) {
        this(i, 0);
    }

    public Timepoint(Parcel parcel) {
        this.hour = parcel.readInt();
        this.minute = parcel.readInt();
        this.second = parcel.readInt();
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public boolean isAM() {
        return this.hour < 12;
    }

    public boolean isPM() {
        int i = this.hour;
        return i >= 12 && i < 24;
    }

    public void setAM() {
        int i = this.hour;
        if (i >= 12) {
            this.hour = i % 12;
        }
    }

    public void setPM() {
        int i = this.hour;
        if (i < 12) {
            this.hour = (i + 12) % 24;
        }
    }

    public boolean equals(Object obj) {
        try {
            Timepoint timepoint = (Timepoint) obj;
            if (timepoint.getHour() == this.hour && timepoint.getMinute() == this.minute && timepoint.getSecond() == this.second) {
                return true;
            }
            return false;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public int compareTo(Timepoint timepoint) {
        return ((this.hour - timepoint.hour) * 3600) + ((this.minute - timepoint.minute) * 60) + (this.second - timepoint.second);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.hour);
        parcel.writeInt(this.minute);
        parcel.writeInt(this.second);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.hour);
        sb.append("h ");
        sb.append(this.minute);
        sb.append("m ");
        sb.append(this.second);
        sb.append("s");
        return sb.toString();
    }
}
