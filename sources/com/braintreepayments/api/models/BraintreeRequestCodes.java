package com.braintreepayments.api.models;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface BraintreeRequestCodes {
    public static final int ANDROID_PAY = 13489;
    public static final int GOOGLE_PAYMENT = 13593;
    public static final int IDEAL = 13594;
    public static final int LOCAL_PAYMENT = 13596;
    public static final int PAYPAL = 13591;
    public static final int SAMSUNG_PAY = 13595;
    public static final int THREE_D_SECURE = 13487;
    public static final int VENMO = 13488;
    public static final int VISA_CHECKOUT = 13592;
}
