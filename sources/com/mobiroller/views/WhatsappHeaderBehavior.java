package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import com.google.android.material.appbar.AppBarLayout;
import com.mobiroller.mobi942763453128.R;

public class WhatsappHeaderBehavior extends Behavior<HeaderView> {
    private boolean isHide;
    private Context mContext;
    private int mEndMarginLeft;
    private int mMarginRight;
    private int mStartMarginBottom;
    private int mStartMarginLeft;
    private float mTitleEndSize;
    private float mTitleStartSize;

    /* access modifiers changed from: protected */
    public float getTranslationOffset(float f, float f2, float f3) {
        return f + (f3 * (f2 - f));
    }

    public WhatsappHeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }

    public WhatsappHeaderBehavior(Context context, AttributeSet attributeSet, Context context2) {
        super(context, attributeSet);
        this.mContext = context2;
    }

    public static int getToolbarHeight(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843499, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, HeaderView headerView, View view) {
        return view instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, HeaderView headerView, View view) {
        shouldInitProperties();
        int totalScrollRange = ((AppBarLayout) view).getTotalScrollRange();
        float abs = Math.abs(view.getY()) / ((float) totalScrollRange);
        float height = (((((float) view.getHeight()) + view.getY()) - ((float) headerView.getHeight())) - ((((float) (getToolbarHeight(this.mContext) - headerView.getHeight())) * abs) / 2.0f)) - (((float) this.mStartMarginBottom) * (1.0f - abs));
        LayoutParams layoutParams = (LayoutParams) headerView.getLayoutParams();
        int i = totalScrollRange / 2;
        float f = (float) i;
        if (Math.abs(view.getY()) >= f) {
            float abs2 = (Math.abs(view.getY()) - f) / ((float) Math.abs(i));
            layoutParams.leftMargin = ((int) (((float) this.mEndMarginLeft) * abs2)) + this.mStartMarginLeft;
            headerView.setTextSize(getTranslationOffset(this.mTitleStartSize, this.mTitleEndSize, abs2));
        } else {
            layoutParams.leftMargin = this.mStartMarginLeft;
        }
        layoutParams.rightMargin = this.mMarginRight;
        headerView.setLayoutParams(layoutParams);
        headerView.setY(height);
        if (this.isHide && abs < 1.0f) {
            headerView.setVisibility(0);
            this.isHide = false;
        } else if (!this.isHide && abs == 1.0f) {
            headerView.setVisibility(8);
            this.isHide = true;
        }
        return true;
    }

    private void shouldInitProperties() {
        if (this.mStartMarginLeft == 0) {
            this.mStartMarginLeft = this.mContext.getResources().getDimensionPixelOffset(R.dimen.header_view_start_margin_left);
        }
        if (this.mEndMarginLeft == 0) {
            this.mEndMarginLeft = this.mContext.getResources().getDimensionPixelOffset(R.dimen.header_view_end_margin_left);
        }
        if (this.mStartMarginBottom == 0) {
            this.mStartMarginBottom = this.mContext.getResources().getDimensionPixelOffset(R.dimen.header_view_start_margin_bottom);
        }
        if (this.mMarginRight == 0) {
            this.mMarginRight = this.mContext.getResources().getDimensionPixelOffset(R.dimen.header_view_end_margin_right);
        }
        if (this.mTitleStartSize == 0.0f) {
            this.mTitleEndSize = (float) this.mContext.getResources().getDimensionPixelSize(R.dimen.header_view_end_text_size);
        }
        if (this.mTitleStartSize == 0.0f) {
            this.mTitleStartSize = (float) this.mContext.getResources().getDimensionPixelSize(R.dimen.header_view_start_text_size);
        }
    }
}
