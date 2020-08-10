package com.google.firebase.auth.api.internal;

public final class zzfe {
    public static String getProperty(String str) {
        try {
            Object invoke = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class}).invoke(null, new Object[]{str});
            if (invoke != null && String.class.isAssignableFrom(invoke.getClass())) {
                return (String) invoke;
            }
        } catch (Exception unused) {
        }
        return null;
    }
}
