package com.mobiroller.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class ScrollingTextView extends AppCompatTextView {
    public boolean isFocused() {
        return true;
    }

    public ScrollingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ScrollingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScrollingTextView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            super.onFocusChanged(z, i, rect);
        }
    }

    public void onWindowFocusChanged(boolean z) {
        if (z) {
            super.onWindowFocusChanged(z);
        }
    }
}
