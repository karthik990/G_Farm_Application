package com.wdullaer.materialdatetimepicker.date;

import com.wdullaer.materialdatetimepicker.date.MonthAdapter.CalendarDay;
import java.util.Calendar;
import java.util.TimeZone;

public interface DatePickerController {
    int getAccentColor();

    Calendar getEndDate();

    int getFirstDayOfWeek();

    int getMaxYear();

    int getMinYear();

    CalendarDay getSelectedDay();

    Calendar getStartDate();

    TimeZone getTimeZone();

    boolean isHighlighted(int i, int i2, int i3);

    boolean isOutOfRange(int i, int i2, int i3);

    boolean isThemeDark();

    void onDayOfMonthSelected(int i, int i2, int i3);

    void onYearSelected(int i);

    void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener);

    void tryVibrate();

    void unregisterOnDateChangedListener(OnDateChangedListener onDateChangedListener);
}
