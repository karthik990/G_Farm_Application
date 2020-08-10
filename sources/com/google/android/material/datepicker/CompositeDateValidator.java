package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.core.util.Preconditions;
import com.google.android.material.datepicker.CalendarConstraints.DateValidator;
import java.util.List;

public final class CompositeDateValidator implements DateValidator {
    public static final Creator<CompositeDateValidator> CREATOR = new Creator<CompositeDateValidator>() {
        public CompositeDateValidator createFromParcel(Parcel parcel) {
            return new CompositeDateValidator((List) Preconditions.checkNotNull(parcel.readArrayList(DateValidator.class.getClassLoader())));
        }

        public CompositeDateValidator[] newArray(int i) {
            return new CompositeDateValidator[i];
        }
    };
    private final List<DateValidator> validators;

    public int describeContents() {
        return 0;
    }

    private CompositeDateValidator(List<DateValidator> list) {
        this.validators = list;
    }

    public static DateValidator allOf(List<DateValidator> list) {
        return new CompositeDateValidator(list);
    }

    public boolean isValid(long j) {
        for (DateValidator dateValidator : this.validators) {
            if (dateValidator != null && !dateValidator.isValid(j)) {
                return false;
            }
        }
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.validators);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompositeDateValidator)) {
            return false;
        }
        return this.validators.equals(((CompositeDateValidator) obj).validators);
    }

    public int hashCode() {
        return this.validators.hashCode();
    }
}
