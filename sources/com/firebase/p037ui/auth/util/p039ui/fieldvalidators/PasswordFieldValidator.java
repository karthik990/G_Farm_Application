package com.firebase.p037ui.auth.util.p039ui.fieldvalidators;

import android.content.res.Resources;
import com.firebase.p037ui.auth.C1330R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.PasswordFieldValidator */
public class PasswordFieldValidator extends BaseValidator {
    private int mMinLength;

    public PasswordFieldValidator(TextInputLayout textInputLayout, int i) {
        super(textInputLayout);
        this.mMinLength = i;
        Resources resources = this.mErrorContainer.getResources();
        int i2 = C1330R.plurals.fui_error_weak_password;
        int i3 = this.mMinLength;
        this.mErrorMessage = resources.getQuantityString(i2, i3, new Object[]{Integer.valueOf(i3)});
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return charSequence.length() >= this.mMinLength;
    }
}
