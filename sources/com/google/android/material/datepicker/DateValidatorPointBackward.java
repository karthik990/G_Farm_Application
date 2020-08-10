package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.material.datepicker.CalendarConstraints.DateValidator;
import java.util.Arrays;

public class DateValidatorPointBackward implements DateValidator {
    public static final Creator<DateValidatorPointBackward> CREATOR = new Creator<DateValidatorPointBackward>() {
        public DateValidatorPointBackward createFromParcel(Parcel parcel) {
            return new DateValidatorPointBackward(parcel.readLong());
        }

        public DateValidatorPointBackward[] newArray(int i) {
            return new DateValidatorPointBackward[i];
        }
    };
    private final long point;

    public int describeContents() {
        return 0;
    }

    private DateValidatorPointBackward(long j) {
        this.point = j;
    }

    public static DateValidatorPointBackward before(long j) {
        return new DateValidatorPointBackward(j);
    }

    public static DateValidatorPointBackward now() {
        return before(UtcDates.getTodayCalendar().getTimeInMillis());
    }

    public boolean isValid(long j) {
        return j <= this.point;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.point);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DateValidatorPointBackward)) {
            return false;
        }
        if (this.point != ((DateValidatorPointBackward) obj).point) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.point)});
    }
}
