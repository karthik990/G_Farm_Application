package com.wdullaer.materialdatetimepicker.date;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.wdullaer.materialdatetimepicker.C5272Utils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TimeZone;
import java.util.TreeSet;

class DefaultDateRangeLimiter implements DateRangeLimiter {
    public static final Creator<DefaultDateRangeLimiter> CREATOR = new Creator<DefaultDateRangeLimiter>() {
        public DefaultDateRangeLimiter createFromParcel(Parcel parcel) {
            return new DefaultDateRangeLimiter(parcel);
        }

        public DefaultDateRangeLimiter[] newArray(int i) {
            return new DefaultDateRangeLimiter[i];
        }
    };
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private HashSet<Calendar> disabledDays = new HashSet<>();
    private transient DatePickerController mController;
    private Calendar mMaxDate;
    private int mMaxYear = DEFAULT_END_YEAR;
    private Calendar mMinDate;
    private int mMinYear = DEFAULT_START_YEAR;
    private TreeSet<Calendar> selectableDays = new TreeSet<>();

    public int describeContents() {
        return 0;
    }

    DefaultDateRangeLimiter() {
    }

    public DefaultDateRangeLimiter(Parcel parcel) {
        this.mMinYear = parcel.readInt();
        this.mMaxYear = parcel.readInt();
        this.mMinDate = (Calendar) parcel.readSerializable();
        this.mMaxDate = (Calendar) parcel.readSerializable();
        this.selectableDays = (TreeSet) parcel.readSerializable();
        this.disabledDays = (HashSet) parcel.readSerializable();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMinYear);
        parcel.writeInt(this.mMaxYear);
        parcel.writeSerializable(this.mMinDate);
        parcel.writeSerializable(this.mMaxDate);
        parcel.writeSerializable(this.selectableDays);
        parcel.writeSerializable(this.disabledDays);
    }

    /* access modifiers changed from: 0000 */
    public void setSelectableDays(Calendar[] calendarArr) {
        for (Calendar trimToMidnight : calendarArr) {
            C5272Utils.trimToMidnight(trimToMidnight);
        }
        this.selectableDays.addAll(Arrays.asList(calendarArr));
    }

    /* access modifiers changed from: 0000 */
    public void setDisabledDays(Calendar[] calendarArr) {
        for (Calendar trimToMidnight : calendarArr) {
            C5272Utils.trimToMidnight(trimToMidnight);
        }
        this.disabledDays.addAll(Arrays.asList(calendarArr));
    }

    /* access modifiers changed from: 0000 */
    public void setMinDate(Calendar calendar) {
        this.mMinDate = C5272Utils.trimToMidnight((Calendar) calendar.clone());
    }

    /* access modifiers changed from: 0000 */
    public void setMaxDate(Calendar calendar) {
        this.mMaxDate = C5272Utils.trimToMidnight((Calendar) calendar.clone());
    }

    /* access modifiers changed from: 0000 */
    public void setController(DatePickerController datePickerController) {
        this.mController = datePickerController;
    }

    /* access modifiers changed from: 0000 */
    public void setYearRange(int i, int i2) {
        if (i2 >= i) {
            this.mMinYear = i;
            this.mMaxYear = i2;
            return;
        }
        throw new IllegalArgumentException("Year end must be larger than or equal to year start");
    }

    /* access modifiers changed from: 0000 */
    public Calendar getMinDate() {
        return this.mMinDate;
    }

    /* access modifiers changed from: 0000 */
    public Calendar getMaxDate() {
        return this.mMaxDate;
    }

    /* access modifiers changed from: 0000 */
    public Calendar[] getSelectableDays() {
        if (this.selectableDays.isEmpty()) {
            return null;
        }
        return (Calendar[]) this.selectableDays.toArray(new Calendar[0]);
    }

    /* access modifiers changed from: 0000 */
    public Calendar[] getDisabledDays() {
        if (this.disabledDays.isEmpty()) {
            return null;
        }
        return (Calendar[]) this.disabledDays.toArray(new Calendar[0]);
    }

    public int getMinYear() {
        if (!this.selectableDays.isEmpty()) {
            return ((Calendar) this.selectableDays.first()).get(1);
        }
        Calendar calendar = this.mMinDate;
        return (calendar == null || calendar.get(1) <= this.mMinYear) ? this.mMinYear : this.mMinDate.get(1);
    }

    public int getMaxYear() {
        if (!this.selectableDays.isEmpty()) {
            return ((Calendar) this.selectableDays.last()).get(1);
        }
        Calendar calendar = this.mMaxDate;
        return (calendar == null || calendar.get(1) >= this.mMaxYear) ? this.mMaxYear : this.mMaxDate.get(1);
    }

    public Calendar getStartDate() {
        if (!this.selectableDays.isEmpty()) {
            return (Calendar) ((Calendar) this.selectableDays.first()).clone();
        }
        Calendar calendar = this.mMinDate;
        if (calendar != null) {
            return (Calendar) calendar.clone();
        }
        DatePickerController datePickerController = this.mController;
        Calendar instance = Calendar.getInstance(datePickerController == null ? TimeZone.getDefault() : datePickerController.getTimeZone());
        instance.set(1, this.mMinYear);
        instance.set(5, 1);
        instance.set(2, 0);
        return instance;
    }

    public Calendar getEndDate() {
        if (!this.selectableDays.isEmpty()) {
            return (Calendar) ((Calendar) this.selectableDays.last()).clone();
        }
        Calendar calendar = this.mMaxDate;
        if (calendar != null) {
            return (Calendar) calendar.clone();
        }
        DatePickerController datePickerController = this.mController;
        Calendar instance = Calendar.getInstance(datePickerController == null ? TimeZone.getDefault() : datePickerController.getTimeZone());
        instance.set(1, this.mMaxYear);
        instance.set(5, 31);
        instance.set(2, 11);
        return instance;
    }

    public boolean isOutOfRange(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(1, i);
        instance.set(2, i2);
        instance.set(5, i3);
        return isOutOfRange(instance);
    }

    private boolean isOutOfRange(Calendar calendar) {
        C5272Utils.trimToMidnight(calendar);
        return isDisabled(calendar) || !isSelectable(calendar);
    }

    private boolean isDisabled(Calendar calendar) {
        return this.disabledDays.contains(C5272Utils.trimToMidnight(calendar)) || isBeforeMin(calendar) || isAfterMax(calendar);
    }

    private boolean isSelectable(Calendar calendar) {
        return this.selectableDays.isEmpty() || this.selectableDays.contains(C5272Utils.trimToMidnight(calendar));
    }

    private boolean isBeforeMin(Calendar calendar) {
        Calendar calendar2 = this.mMinDate;
        return (calendar2 != null && calendar.before(calendar2)) || calendar.get(1) < this.mMinYear;
    }

    private boolean isAfterMax(Calendar calendar) {
        Calendar calendar2 = this.mMaxDate;
        return (calendar2 != null && calendar.after(calendar2)) || calendar.get(1) > this.mMaxYear;
    }

    public Calendar setToNearestDate(Calendar calendar) {
        if (!this.selectableDays.isEmpty()) {
            Calendar calendar2 = null;
            Calendar calendar3 = (Calendar) this.selectableDays.ceiling(calendar);
            Calendar calendar4 = (Calendar) this.selectableDays.lower(calendar);
            if (calendar3 == null && calendar4 != null) {
                calendar2 = calendar4;
            } else if (calendar4 == null && calendar3 != null) {
                calendar2 = calendar3;
            }
            if (calendar2 != null || calendar3 == null) {
                if (calendar2 != null) {
                    calendar = calendar2;
                }
                DatePickerController datePickerController = this.mController;
                calendar.setTimeZone(datePickerController == null ? TimeZone.getDefault() : datePickerController.getTimeZone());
                return (Calendar) calendar.clone();
            }
            if (Math.abs(calendar.getTimeInMillis() - calendar4.getTimeInMillis()) < Math.abs(calendar3.getTimeInMillis() - calendar.getTimeInMillis())) {
                return (Calendar) calendar4.clone();
            }
            return (Calendar) calendar3.clone();
        }
        if (!this.disabledDays.isEmpty()) {
            Calendar startDate = isBeforeMin(calendar) ? getStartDate() : (Calendar) calendar.clone();
            Calendar endDate = isAfterMax(calendar) ? getEndDate() : (Calendar) calendar.clone();
            while (isDisabled(startDate) && isDisabled(endDate)) {
                startDate.add(5, 1);
                endDate.add(5, -1);
            }
            if (!isDisabled(endDate)) {
                return endDate;
            }
            if (!isDisabled(startDate)) {
                return startDate;
            }
        }
        if (this.mMinDate != null && isBeforeMin(calendar)) {
            return (Calendar) this.mMinDate.clone();
        }
        if (this.mMaxDate != null && isAfterMax(calendar)) {
            calendar = (Calendar) this.mMaxDate.clone();
        }
        return calendar;
    }
}
