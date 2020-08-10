package com.braintreepayments.api;

import android.content.Intent;
import java.lang.reflect.InvocationTargetException;

class VisaCheckoutFacade {
    static String sVisaCheckoutClassName = "com.braintreepayments.api.VisaCheckout";

    VisaCheckoutFacade() {
    }

    static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        try {
            Class.forName(sVisaCheckoutClassName).getDeclaredMethod("onActivityResult", new Class[]{BraintreeFragment.class, Integer.TYPE, Intent.class}).invoke(null, new Object[]{braintreeFragment, Integer.valueOf(i), intent});
        } catch (ClassNotFoundException | IllegalAccessException | NoClassDefFoundError | NoSuchMethodException | InvocationTargetException unused) {
        }
    }
}
