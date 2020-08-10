package com.firebase.p037ui.auth.util.p039ui.fieldvalidators;

import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.NoOpValidator */
public class NoOpValidator extends BaseValidator {
    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return true;
    }

    public NoOpValidator(TextInputLayout textInputLayout) {
        super(textInputLayout);
    }
}
