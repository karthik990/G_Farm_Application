package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.mobiroller.enums.ColorEnum;
import com.mobiroller.enums.FontTypeEnum;
import com.mobiroller.preview.C4290R;

public class MobirollerTextView extends AppCompatTextView {
    private int color;
    private int size;

    public MobirollerTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerTextView, 0, 0);
        try {
            this.color = obtainStyledAttributes.getInteger(0, -1);
            this.size = obtainStyledAttributes.getInteger(1, 0);
        } finally {
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        int i = this.color;
        if (i != -1) {
            setTextColor(ColorEnum.getResIdByResOrder(i));
        }
        setTextSize(2, FontTypeEnum.getFontSizeByResOrder(this.size));
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        setTypeface(FontTypeEnum.getResIdByResOrder(this.size));
    }
}
