package com.nightonke.boommenu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

class BMBFrameLayout extends FrameLayout {
    private boolean requestLayoutNotFinish = false;

    public BMBFrameLayout(Context context) {
        super(context);
    }

    public BMBFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BMBFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void requestLayout() {
        if (!this.requestLayoutNotFinish) {
            this.requestLayoutNotFinish = true;
            super.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.requestLayoutNotFinish = false;
    }
}
