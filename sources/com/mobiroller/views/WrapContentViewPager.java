package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import androidx.viewpager.widget.ViewPager;

public class WrapContentViewPager extends ViewPager {
    private int mCurrentPagePosition = 0;

    public WrapContentViewPager(Context context) {
        super(context);
    }

    public WrapContentViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        try {
            if (MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
                View childAt = getChildAt(this.mCurrentPagePosition);
                if (childAt != null) {
                    childAt.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
                    i2 = MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), 1073741824);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(i, i2);
    }

    public void reMeasureCurrentPage(int i) {
        this.mCurrentPagePosition = i;
        requestLayout();
    }
}
