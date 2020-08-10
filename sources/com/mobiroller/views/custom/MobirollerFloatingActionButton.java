package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.enums.ColorEnum;
import com.mobiroller.preview.C4290R;
import com.mobiroller.util.ColorUtil;

public class MobirollerFloatingActionButton extends FloatingActionButton {
    private int themeColor;

    public MobirollerFloatingActionButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerFloatingActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerFloatingActionButton, 0, 0);
        try {
            this.themeColor = obtainStyledAttributes.getInteger(0, -1);
        } finally {
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        int i = this.themeColor;
        int i2 = -1;
        if (i != -1) {
            setBackgroundTintList(ColorStateList.valueOf(ColorEnum.getResIdByResOrder(i)));
            Drawable newDrawable = getDrawable().getConstantState().newDrawable();
            Drawable mutate = newDrawable.mutate();
            if (!ColorUtil.isColorDark(ColorEnum.getResIdByResOrder(this.themeColor))) {
                i2 = ViewCompat.MEASURED_STATE_MASK;
            }
            mutate.setColorFilter(i2, Mode.MULTIPLY);
            setImageDrawable(newDrawable);
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (z) {
            setBackgroundTintList(ColorStateList.valueOf(ColorEnum.getResIdByResOrder(this.themeColor)));
        } else {
            setBackgroundTintList(ColorStateList.valueOf(ColorUtil.getColorWithAlpha(ColorEnum.getResIdByResOrder(this.themeColor), 0.4f)));
        }
    }
}
