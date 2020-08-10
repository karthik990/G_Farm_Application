package com.mobiroller.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;
import com.mobiroller.preview.C4290R;

public class MaxHeightScrollView extends ScrollView {
    private final int defaultHeight = 200;
    private int maxHeight;

    public MaxHeightScrollView(Context context) {
        super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (!isInEditMode()) {
            init(context, attributeSet);
        }
    }

    public MaxHeightScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (!isInEditMode()) {
            init(context, attributeSet);
        }
    }

    public MaxHeightScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (!isInEditMode()) {
            init(context, attributeSet);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.MaxHeightScrollView);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(0, 200);
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(this.maxHeight, Integer.MIN_VALUE));
    }
}
