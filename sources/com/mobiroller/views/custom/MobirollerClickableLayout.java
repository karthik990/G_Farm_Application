package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MobirollerClickableLayout extends ConstraintLayout {
    public MobirollerClickableLayout(Context context) {
        super(context);
        if (VERSION.SDK_INT >= 21) {
            init(context, null, 0);
        }
    }

    public MobirollerClickableLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (VERSION.SDK_INT >= 21) {
            init(context, attributeSet, 0);
        }
    }

    public MobirollerClickableLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (VERSION.SDK_INT >= 21) {
            init(context, attributeSet, i);
        }
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        if (VERSION.SDK_INT >= 21) {
            setBackground(getBackgroundDrawable(-7829368, getBackground()));
        }
    }

    public static Drawable getBackgroundDrawable(int i, Drawable drawable) {
        return new RippleDrawable(getPressedState(i), drawable, null);
    }

    public static ColorStateList getPressedState(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setAlpha(z ? 1.0f : 0.5f);
    }
}
