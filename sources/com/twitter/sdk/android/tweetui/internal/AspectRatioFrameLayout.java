package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.twitter.sdk.android.tweetui.C5234R;

public class AspectRatioFrameLayout extends FrameLayout {
    static final int ADJUST_DIMENSION_HEIGHT = 0;
    static final int ADJUST_DIMENSION_WIDTH = 1;
    private static final int DEFAULT_ADJUST_DIMENSION = 0;
    private static final float DEFAULT_ASPECT_RATIO = 1.0f;
    protected double aspectRatio;
    private int dimensionToAdjust;

    public AspectRatioFrameLayout(Context context) {
        this(context, null);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAttributes(i);
    }

    private void initAttributes(int i) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(i, C5234R.styleable.AspectRatioFrameLayout);
        try {
            this.aspectRatio = (double) obtainStyledAttributes.getFloat(C5234R.styleable.AspectRatioFrameLayout_tw__frame_layout_aspect_ratio, 1.0f);
            this.dimensionToAdjust = obtainStyledAttributes.getInt(C5234R.styleable.AspectRatioFrameLayout_tw__frame_layout_dimension_to_adjust, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public void setAspectRatio(double d) {
        this.aspectRatio = d;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.dimensionToAdjust == 0) {
            if (MeasureSpec.getMode(i) == 1073741824) {
                i6 = MeasureSpec.getSize(i);
            } else {
                super.onMeasure(i, i2);
                i6 = getMeasuredWidth();
            }
            i4 = i6 - paddingLeft;
            double d = (double) i4;
            double d2 = this.aspectRatio;
            Double.isNaN(d);
            i3 = (int) (d / d2);
        } else {
            if (MeasureSpec.getMode(i2) == 1073741824) {
                i5 = MeasureSpec.getSize(i2);
            } else {
                super.onMeasure(i, i2);
                i5 = getMeasuredHeight();
            }
            i3 = i5 - paddingBottom;
            double d3 = (double) i3;
            double d4 = this.aspectRatio;
            Double.isNaN(d3);
            i4 = (int) (d3 * d4);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(i4 + paddingLeft, 1073741824), MeasureSpec.makeMeasureSpec(i3 + paddingBottom, 1073741824));
    }
}
