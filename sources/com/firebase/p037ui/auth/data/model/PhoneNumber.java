package com.firebase.p037ui.auth.data.model;

import android.text.TextUtils;

/* renamed from: com.firebase.ui.auth.data.model.PhoneNumber */
public final class PhoneNumber {
    private static final PhoneNumber EMPTY_PHONE_NUMBER;
    private final String mCountryCode;
    private final String mCountryIso;
    private final String mPhoneNumber;

    static {
        String str = "";
        EMPTY_PHONE_NUMBER = new PhoneNumber(str, str, str);
    }

    public PhoneNumber(String str, String str2, String str3) {
        this.mPhoneNumber = str;
        this.mCountryIso = str2;
        this.mCountryCode = str3;
    }

    public static PhoneNumber emptyPhone() {
        return EMPTY_PHONE_NUMBER;
    }

    public static boolean isValid(PhoneNumber phoneNumber) {
        return phoneNumber != null && !EMPTY_PHONE_NUMBER.equals(phoneNumber) && !TextUtils.isEmpty(phoneNumber.getPhoneNumber()) && !TextUtils.isEmpty(phoneNumber.getCountryCode()) && !TextUtils.isEmpty(phoneNumber.getCountryIso());
    }

    public static boolean isCountryValid(PhoneNumber phoneNumber) {
        return phoneNumber != null && !EMPTY_PHONE_NUMBER.equals(phoneNumber) && !TextUtils.isEmpty(phoneNumber.getCountryCode()) && !TextUtils.isEmpty(phoneNumber.getCountryIso());
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }
}
