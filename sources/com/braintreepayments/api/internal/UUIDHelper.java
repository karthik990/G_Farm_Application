package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

public class UUIDHelper {
    private static final String BRAINTREE_UUID_KEY = "braintreeUUID";

    public static String getPersistentUUID(Context context) {
        SharedPreferences sharedPreferences = BraintreeSharedPreferences.getSharedPreferences(context);
        String str = BRAINTREE_UUID_KEY;
        String string = sharedPreferences.getString(str, null);
        if (string != null) {
            return string;
        }
        String formattedUUID = getFormattedUUID();
        sharedPreferences.edit().putString(str, formattedUUID).apply();
        return formattedUUID;
    }

    public static String getFormattedUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
