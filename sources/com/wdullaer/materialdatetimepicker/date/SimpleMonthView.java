package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class SimpleMonthView extends MonthView {
    public SimpleMonthView(Context context, AttributeSet attributeSet, DatePickerController datePickerController) {
        super(context, attributeSet, datePickerController);
    }

    public void drawMonthDay(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (this.mSelectedDay == i3) {
            canvas.drawCircle((float) i4, (float) (i5 - (MINI_DAY_NUMBER_TEXT_SIZE / 3)), (float) DAY_SELECTED_CIRCLE_SIZE, this.mSelectedCirclePaint);
        }
        if (isHighlighted(i, i2, i3)) {
            this.mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        } else {
            this.mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
        }
        if (this.mController.isOutOfRange(i, i2, i3)) {
            this.mMonthNumPaint.setColor(this.mDisabledDayTextColor);
        } else if (this.mSelectedDay == i3) {
            this.mMonthNumPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
            this.mMonthNumPaint.setColor(this.mSelectedDayTextColor);
        } else if (!this.mHasToday || this.mToday != i3) {
            this.mMonthNumPaint.setColor(isHighlighted(i, i2, i3) ? this.mHighlightedDayTextColor : this.mDayTextColor);
        } else {
            this.mMonthNumPaint.setColor(this.mTodayNumberColor);
        }
        canvas.drawText(String.valueOf(i3), (float) i4, (float) i5, this.mMonthNumPaint);
    }
}
