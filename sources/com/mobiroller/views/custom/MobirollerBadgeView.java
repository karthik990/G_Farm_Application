package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.C4290R;

public class MobirollerBadgeView extends ConstraintLayout {
    private int backgroundColor;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    private String text = "";
    private int textColor;
    @BindView(2131361966)
    MobirollerTextView textView;

    public MobirollerBadgeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerBadgeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerBadgeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerBadgeView, 0, 0);
        try {
            this.textColor = obtainStyledAttributes.getColor(2, 0);
            this.backgroundColor = obtainStyledAttributes.getColor(0, 0);
            this.text = obtainStyledAttributes.getString(1);
        } finally {
            inflate(getContext(), R.layout.mobiroller_badge_view, this);
            ButterKnife.bind((View) this);
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(16.0f);
        gradientDrawable.setColor(this.backgroundColor);
        this.mainLayout.setBackground(gradientDrawable);
        setText(this.text);
        this.textView.setTextColor(this.textColor);
    }

    public void setText(String str) {
        this.textView.setText(str);
        this.textView.setAllCaps(true);
    }
}
