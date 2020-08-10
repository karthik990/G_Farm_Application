package com.mobiroller.views.custom;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.mobi942763453128.R;

public class MobirollerTextInputEditText_ViewBinding implements Unbinder {
    private MobirollerTextInputEditText target;

    public MobirollerTextInputEditText_ViewBinding(MobirollerTextInputEditText mobirollerTextInputEditText) {
        this(mobirollerTextInputEditText, mobirollerTextInputEditText);
    }

    public MobirollerTextInputEditText_ViewBinding(MobirollerTextInputEditText mobirollerTextInputEditText, View view) {
        this.target = mobirollerTextInputEditText;
        mobirollerTextInputEditText.textInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.text_input_layout, "field 'textInputLayout'", TextInputLayout.class);
        mobirollerTextInputEditText.textInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.text_input_edit_text, "field 'textInputEditText'", TextInputEditText.class);
    }

    public void unbind() {
        MobirollerTextInputEditText mobirollerTextInputEditText = this.target;
        if (mobirollerTextInputEditText != null) {
            this.target = null;
            mobirollerTextInputEditText.textInputLayout = null;
            mobirollerTextInputEditText.textInputEditText = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
