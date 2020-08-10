package com.firebase.p037ui.auth.util.p039ui.fieldvalidators;

import com.firebase.p037ui.auth.C1330R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.RequiredFieldValidator */
public class RequiredFieldValidator extends BaseValidator {
    public RequiredFieldValidator(TextInputLayout textInputLayout) {
        super(textInputLayout);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C1330R.C1335string.fui_required_field);
    }

    public RequiredFieldValidator(TextInputLayout textInputLayout, String str) {
        super(textInputLayout);
        this.mErrorMessage = str;
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }
}
