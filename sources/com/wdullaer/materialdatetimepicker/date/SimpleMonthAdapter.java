package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;

public class SimpleMonthAdapter extends MonthAdapter {
    public SimpleMonthAdapter(DatePickerController datePickerController) {
        super(datePickerController);
    }

    public MonthView createMonthView(Context context) {
        return new SimpleMonthView(context, null, this.mController);
    }
}
