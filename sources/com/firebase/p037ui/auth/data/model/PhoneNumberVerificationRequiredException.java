package com.firebase.p037ui.auth.data.model;

import com.firebase.p037ui.auth.FirebaseUiException;

/* renamed from: com.firebase.ui.auth.data.model.PhoneNumberVerificationRequiredException */
public class PhoneNumberVerificationRequiredException extends FirebaseUiException {
    private final String mPhoneNumber;

    public PhoneNumberVerificationRequiredException(String str) {
        super(4, "Phone number requires verification.");
        this.mPhoneNumber = str;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }
}
