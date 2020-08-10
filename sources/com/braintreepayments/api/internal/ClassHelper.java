package com.braintreepayments.api.internal;

public class ClassHelper {
    public static boolean isClassAvailable(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
