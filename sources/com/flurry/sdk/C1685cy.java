package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Log;

/* renamed from: com.flurry.sdk.cy */
public final class C1685cy {

    /* renamed from: a */
    private static boolean f867a = false;

    /* renamed from: b */
    private static int f868b = 5;

    /* renamed from: c */
    private static boolean f869c = false;

    /* renamed from: a */
    public static void m752a() {
        f867a = true;
    }

    /* renamed from: b */
    public static void m759b() {
        f867a = false;
    }

    /* renamed from: c */
    public static int m764c() {
        return f868b;
    }

    /* renamed from: a */
    public static void m753a(int i) {
        f868b = i;
    }

    /* renamed from: a */
    public static void m758a(boolean z) {
        f869c = z;
    }

    /* renamed from: b */
    private static void m761b(int i, String str, String str2, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        m760b(i, str, sb.toString());
    }

    /* renamed from: b */
    private static void m760b(int i, String str, String str2) {
        if (!f867a && f868b <= i) {
            m765c(i, str, str2);
        }
    }

    /* renamed from: c */
    private static void m765c(int i, String str, String str2) {
        if (!f869c) {
            str = "FlurryAgent";
        }
        int i2 = 0;
        int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
        while (i2 < length) {
            int i3 = 4000 > length - i2 ? length : i2 + 4000;
            if (Log.println(i, str, str2.substring(i2, i3)) > 0) {
                i2 = i3;
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    public static void m756a(String str, String str2) {
        m760b(3, str, str2);
    }

    /* renamed from: a */
    public static void m757a(String str, String str2, Throwable th) {
        m761b(6, str, str2, th);
    }

    /* renamed from: b */
    public static void m762b(String str, String str2) {
        m760b(6, str, str2);
    }

    /* renamed from: c */
    public static void m766c(String str, String str2) {
        m760b(4, str, str2);
    }

    /* renamed from: d */
    public static void m768d(String str, String str2) {
        m760b(2, str, str2);
    }

    /* renamed from: b */
    public static void m763b(String str, String str2, Throwable th) {
        m761b(5, str, str2, th);
    }

    /* renamed from: e */
    public static void m769e(String str, String str2) {
        m760b(5, str, str2);
    }

    /* renamed from: d */
    private static void m767d(int i, String str, String str2) {
        if (f869c) {
            m765c(i, str, str2);
        }
    }

    /* renamed from: a */
    public static void m754a(int i, String str, String str2) {
        m767d(i, str, str2);
    }

    /* renamed from: a */
    public static void m755a(int i, String str, String str2, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(10);
        sb.append(Log.getStackTraceString(th));
        m767d(i, str, sb.toString());
    }
}
