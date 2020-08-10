package com.mobiroller.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.preview.C4290R;
import com.mobiroller.views.theme.Theme;

public class MobirollerTextInputEditText extends ConstraintLayout {
    private int maxLength;
    @BindView(2131363240)
    TextInputEditText textInputEditText;
    @BindView(2131363242)
    TextInputLayout textInputLayout;

    public MobirollerTextInputEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MobirollerTextInputEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public MobirollerTextInputEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.MobirollerTextInputEditText, 0, 0);
        try {
            this.maxLength = obtainStyledAttributes.getInteger(0, 0);
        } finally {
            inflate(getContext(), R.layout.mobiroller_text_input_edit_text, this);
            ButterKnife.bind((View) this);
            setTheme();
            obtainStyledAttributes.recycle();
        }
    }

    public void setTheme() {
        this.textInputEditText.setHintTextColor(Theme.primaryColor);
        this.textInputEditText.setTextColor(Theme.primaryColor);
        this.textInputLayout.setBoxBackgroundColor(Theme.primaryColor);
        setMaxLength(this.maxLength);
    }

    public void setMaxLength(int i) {
        this.textInputEditText.setFilters(new InputFilter[]{new LengthFilter(i)});
    }
}
