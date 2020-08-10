package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class CustomHorizontalScrollView extends ScrollView {
    public OnScrollChangedListener mOnScrollChangedListener;

    public interface OnScrollChangedListener {
        void onScrollChanged(int i, int i2, int i3, int i4);
    }

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        OnScrollChangedListener onScrollChangedListener = this.mOnScrollChangedListener;
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(i, i2, i3, i4);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.mOnScrollChangedListener = onScrollChangedListener;
    }
}
