package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import com.mobiroller.enums.ColorEnum;
import com.mobiroller.preview.C4290R;

public class MobirollerImageView extends AppCompatImageView {
    private int color = -1;

    public MobirollerImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerImageView, 0, 0);
        try {
            if (obtainStyledAttributes.getInteger(0, -1) != -1) {
                this.color = ColorEnum.getResIdByResOrder(obtainStyledAttributes.getInteger(0, -1));
            }
        } finally {
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16843868, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        int i = this.color;
        if (i != -1) {
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(i));
        }
    }
}
