package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.util.AttributeSet;

public class SimpleDayPickerView extends DayPickerView {
    public SimpleDayPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SimpleDayPickerView(Context context, DatePickerController datePickerController) {
        super(context, datePickerController);
    }

    public MonthAdapter createMonthAdapter(DatePickerController datePickerController) {
        return new SimpleMonthAdapter(datePickerController);
    }
}
