package com.firebase.p037ui.auth.util.p039ui.fieldvalidators;

import android.util.Patterns;
import com.firebase.p037ui.auth.C1330R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.EmailFieldValidator */
public class EmailFieldValidator extends BaseValidator {
    public EmailFieldValidator(TextInputLayout textInputLayout) {
        super(textInputLayout);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C1330R.C1335string.fui_invalid_email_address);
        this.mEmptyMessage = this.mErrorContainer.getResources().getString(C1330R.C1335string.fui_missing_email_address);
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
}
