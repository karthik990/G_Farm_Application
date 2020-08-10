package com.flurry.sdk;

import android.text.TextUtils;
import java.io.File;
import java.util.Locale;

/* renamed from: com.flurry.sdk.ff */
public final class C1776ff {
    /* renamed from: a */
    public static boolean m942a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /* renamed from: b */
    public static boolean m944b(String str) {
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m941a() {
        File file = new File(m947e());
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    /* renamed from: b */
    public static String m943b() {
        StringBuilder sb = new StringBuilder(m947e());
        sb.append(File.separator);
        sb.append("fCompleted");
        return sb.toString();
    }

    /* renamed from: c */
    public static String m945c() {
        return String.format(Locale.US, "completed-%d", new Object[]{Long.valueOf(System.currentTimeMillis())});
    }

    /* renamed from: d */
    public static String m946d() {
        StringBuilder sb = new StringBuilder(m947e());
        sb.append(File.separator);
        sb.append("fInProgress");
        return sb.toString();
    }

    /* renamed from: e */
    private static String m947e() {
        StringBuilder sb = new StringBuilder(C1732dy.m859a().toString());
        sb.append(File.separator);
        sb.append(".fstreaming");
        return sb.toString();
    }
}
