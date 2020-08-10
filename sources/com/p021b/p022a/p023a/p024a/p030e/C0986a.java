package com.p021b.p022a.p023a.p024a.p030e;

import android.os.Build;
import android.os.Build.VERSION;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.e.a */
public final class C0986a {
    /* renamed from: a */
    public static String m231a() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append("; ");
        sb.append(Build.MODEL);
        return sb.toString();
    }

    /* renamed from: b */
    public static String m232b() {
        return Integer.toString(VERSION.SDK_INT);
    }

    /* renamed from: c */
    public static String m233c() {
        return "Android";
    }

    /* renamed from: d */
    public static JSONObject m234d() {
        JSONObject jSONObject = new JSONObject();
        C0987b.m240a(jSONObject, "deviceType", m231a());
        C0987b.m240a(jSONObject, "osVersion", m232b());
        C0987b.m240a(jSONObject, "os", m233c());
        return jSONObject;
    }
}
