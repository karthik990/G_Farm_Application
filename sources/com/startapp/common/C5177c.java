package com.startapp.common;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* renamed from: com.startapp.common.c */
/* compiled from: StartAppSDK */
public class C5177c {

    /* renamed from: b */
    private static C5177c f3615b;

    /* renamed from: a */
    protected String f3616a = "e106";

    /* renamed from: c */
    private Context f3617c;

    /* renamed from: d */
    private PhoneStateListener f3618d = m3889c();

    private C5177c(Context context) {
        this.f3617c = context.getApplicationContext();
    }

    /* renamed from: a */
    public void mo62898a(Context context) {
        m3888a(context, 256);
    }

    /* renamed from: b */
    public void mo62900b(Context context) {
        m3888a(context, 0);
    }

    /* renamed from: a */
    private void m3888a(Context context, int i) {
        try {
            ((TelephonyManager) context.getSystemService("phone")).listen(this.f3618d, i);
        } catch (Exception unused) {
        }
    }

    /* renamed from: c */
    public static void m3890c(Context context) {
        if (f3615b == null) {
            f3615b = new C5177c(context);
            m3887a().mo62898a(context);
        }
    }

    /* renamed from: a */
    public static C5177c m3887a() {
        return f3615b;
    }

    /* renamed from: b */
    public String mo62899b() {
        return this.f3616a;
    }

    /* renamed from: c */
    private PhoneStateListener m3889c() {
        try {
            return new PhoneStateListener() {
                public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                    String str = "e105";
                    try {
                        Method method = SignalStrength.class.getMethod("getLevel", null);
                        C5177c.this.f3616a = Integer.toString(((Integer) method.invoke(signalStrength, null)).intValue());
                    } catch (NoSuchMethodException unused) {
                        C5177c.this.f3616a = "e104";
                    } catch (IllegalAccessException unused2) {
                        C5177c.this.f3616a = str;
                    } catch (IllegalArgumentException unused3) {
                        C5177c.this.f3616a = str;
                    } catch (InvocationTargetException unused4) {
                        C5177c.this.f3616a = str;
                    }
                }
            };
        } catch (Exception unused) {
            return null;
        }
    }
}
