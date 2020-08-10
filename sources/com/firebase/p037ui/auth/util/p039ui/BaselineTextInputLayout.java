package com.firebase.p037ui.auth.util.p039ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.BaselineTextInputLayout */
public class BaselineTextInputLayout extends TextInputLayout {
    public BaselineTextInputLayout(Context context) {
        super(context);
    }

    public BaselineTextInputLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaselineTextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getBaseline() {
        EditText editText = getEditText();
        if (editText == null) {
            return super.getBaseline();
        }
        return editText.getBaseline() + editText.getPaddingTop();
    }
}
