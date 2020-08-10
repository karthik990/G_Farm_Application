package com.flurry.sdk;

import android.content.SharedPreferences.Editor;
import java.util.Locale;

/* renamed from: com.flurry.sdk.fe */
public final class C1775fe {
    /* renamed from: a */
    public static void m936a(String str, long j) {
        Editor edit = C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putLong(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), j);
        edit.apply();
    }

    /* renamed from: a */
    public static void m937a(String str, String str2) {
        Editor edit = C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putString(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), str2);
        edit.apply();
    }

    /* renamed from: a */
    public static void m935a(String str, int i) {
        Editor edit = C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putInt(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), i);
        edit.apply();
    }

    /* renamed from: b */
    public static String m940b(String str, String str2) {
        return C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).getString(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), str2);
    }

    /* renamed from: b */
    public static long m939b(String str, long j) {
        return C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).getLong(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), j);
    }

    /* renamed from: b */
    public static int m938b(String str, int i) {
        return C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).getInt(String.format(Locale.US, "com.flurry.sdk.%s", new Object[]{str}), i);
    }

    /* renamed from: a */
    public static void m934a(String str) {
        C1576b.m502a().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit().remove(str).apply();
    }
}
