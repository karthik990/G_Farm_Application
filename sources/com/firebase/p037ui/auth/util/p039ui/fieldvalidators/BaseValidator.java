package com.firebase.p037ui.auth.util.p039ui.fieldvalidators;

import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.BaseValidator */
public class BaseValidator {
    protected String mEmptyMessage;
    protected TextInputLayout mErrorContainer;
    protected String mErrorMessage = "";

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return true;
    }

    public BaseValidator(TextInputLayout textInputLayout) {
        this.mErrorContainer = textInputLayout;
    }

    public boolean validate(CharSequence charSequence) {
        if (this.mEmptyMessage != null && (charSequence == null || charSequence.length() == 0)) {
            this.mErrorContainer.setError(this.mEmptyMessage);
            return false;
        } else if (isValid(charSequence)) {
            this.mErrorContainer.setError("");
            return true;
        } else {
            this.mErrorContainer.setError(this.mErrorMessage);
            return false;
        }
    }
}
