package com.wdullaer.materialdatetimepicker.date;

import android.os.Parcelable;
import java.util.Calendar;

public interface DateRangeLimiter extends Parcelable {
    Calendar getEndDate();

    int getMaxYear();

    int getMinYear();

    Calendar getStartDate();

    boolean isOutOfRange(int i, int i2, int i3);

    Calendar setToNearestDate(Calendar calendar);
}
