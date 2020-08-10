package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ViewAnimator;

public class AccessibleDateAnimator extends ViewAnimator {
    private long mDateMillis;

    public AccessibleDateAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setDateMillis(long j) {
        this.mDateMillis = j;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        accessibilityEvent.getText().clear();
        accessibilityEvent.getText().add(DateUtils.formatDateTime(getContext(), this.mDateMillis, 22));
        return true;
    }
}
