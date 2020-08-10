package com.truenet.android;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.startapp.common.C5177c;
import com.truenet.android.p096a.C5196c;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a */
/* compiled from: StartAppSDK */
public final class C3029a {

    /* renamed from: a */
    private final Context f1911a;

    /* renamed from: b */
    private final TelephonyManager f1912b;

    public C3029a(Context context, TelephonyManager telephonyManager) {
        C0032h.m44b(context, "context");
        C0032h.m44b(telephonyManager, "telephonyManager");
        this.f1911a = context;
        this.f1912b = telephonyManager;
        C5177c.m3890c(this.f1911a);
    }

    public /* synthetic */ C3029a(Context context, TelephonyManager telephonyManager, int i, C0029e eVar) {
        if ((i & 2) != 0) {
            telephonyManager = C5196c.m3938a(context);
        }
        this(context, telephonyManager);
    }

    /* renamed from: b */
    private final boolean m1828b() {
        Resources system = Resources.getSystem();
        C0032h.m41a((Object) system, "Resources.getSystem()");
        DisplayMetrics displayMetrics = system.getDisplayMetrics();
        double d = (double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi);
        double d2 = (double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi);
        Double.isNaN(d);
        Double.isNaN(d);
        double d3 = d * d;
        Double.isNaN(d2);
        Double.isNaN(d2);
        return Math.sqrt(d3 + (d2 * d2)) > 6.5d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00f0  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.truenet.android.DeviceInfo mo44587a() {
        /*
            r28 = this;
            r0 = r28
            java.util.List r1 = p000a.p001a.p002a.C0007g.m5a()
            r2 = r1
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            r3 = 1
            r2 = r2 ^ r3
            java.lang.String r4 = ""
            if (r2 == 0) goto L_0x0032
            java.lang.Object r2 = p000a.p001a.p002a.C0007g.m13c(r1)
            android.location.Location r2 = (android.location.Location) r2
            double r5 = r2.getLatitude()
            java.lang.String r2 = java.lang.String.valueOf(r5)
            java.lang.Object r1 = p000a.p001a.p002a.C0007g.m13c(r1)
            android.location.Location r1 = (android.location.Location) r1
            double r5 = r1.getLongitude()
            java.lang.String r1 = java.lang.String.valueOf(r5)
            r7 = r1
            r6 = r2
            goto L_0x0034
        L_0x0032:
            r6 = r4
            r7 = r6
        L_0x0034:
            android.content.Context r1 = r0.f1911a
            android.content.res.Resources r1 = r1.getResources()
            java.lang.String r2 = "context.resources"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r2)
            android.content.res.Configuration r1 = r1.getConfiguration()
            java.lang.String r2 = "context.resources.configuration"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r2)
            java.util.Locale r1 = com.truenet.android.p096a.C5195b.m3937a(r1)
            com.startapp.common.a.b r2 = com.startapp.common.p092a.C5139b.m3719a()
            android.content.Context r5 = r0.f1911a
            com.startapp.common.a.b$c r2 = r2.mo62840a(r5)
            java.lang.String r5 = "AdvertisingIdSingleton.g…getAdvertisingId(context)"
            p000a.p001a.p003b.p005b.C0032h.m41a(r2, r5)
            java.lang.String r10 = r2.mo62849a()
            android.telephony.TelephonyManager r2 = r0.f1912b
            int r2 = r2.getPhoneType()
            if (r2 == 0) goto L_0x0075
            r5 = 2
            if (r2 == r5) goto L_0x0075
            android.telephony.TelephonyManager r2 = r0.f1912b
            java.lang.String r2 = r2.getNetworkOperatorName()
            if (r2 == 0) goto L_0x0075
            r17 = r2
            goto L_0x0077
        L_0x0075:
            r17 = r4
        L_0x0077:
            android.telephony.TelephonyManager r2 = r0.f1912b
            int r5 = r2.getSimState()
            r8 = 5
            if (r5 != r8) goto L_0x0085
            java.lang.String r2 = r2.getSimOperator()
            goto L_0x0086
        L_0x0085:
            r2 = r4
        L_0x0086:
            android.telephony.TelephonyManager r5 = r0.f1912b
            int r9 = r5.getSimState()
            if (r9 != r8) goto L_0x0094
            java.lang.String r5 = r5.getSimOperatorName()
            r8 = r5
            goto L_0x0095
        L_0x0094:
            r8 = r4
        L_0x0095:
            android.content.Context r5 = r0.f1911a
            java.lang.String r9 = "android.permission.ACCESS_FINE_LOCATION"
            boolean r5 = com.truenet.android.p096a.C5202h.m3951a(r5, r9)
            if (r5 != 0) goto L_0x00ab
            android.content.Context r5 = r0.f1911a
            java.lang.String r9 = "android.permission.ACCESS_COARSE_LOCATION"
            boolean r5 = com.truenet.android.p096a.C5202h.m3951a(r5, r9)
            if (r5 == 0) goto L_0x00aa
            goto L_0x00ab
        L_0x00aa:
            r3 = 0
        L_0x00ab:
            if (r3 == 0) goto L_0x00be
            android.telephony.TelephonyManager r5 = r0.f1912b
            int r5 = com.truenet.android.p096a.C5199f.m3943a(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r20 = r5
            goto L_0x00c0
        L_0x00be:
            r20 = r4
        L_0x00c0:
            if (r3 == 0) goto L_0x00d0
            android.telephony.TelephonyManager r3 = r0.f1912b
            int r3 = com.truenet.android.p096a.C5199f.m3944b(r3)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.String r4 = java.lang.String.valueOf(r3)
        L_0x00d0:
            r21 = r4
            com.startapp.common.c r3 = com.startapp.common.C5177c.m3887a()
            java.lang.String r4 = "NetworkStats.get()"
            p000a.p001a.p003b.p005b.C0032h.m41a(r3, r4)
            java.lang.String r3 = r3.mo62899b()
            com.truenet.android.a.i$a r4 = com.truenet.android.p096a.C5203i.f3648a
            android.content.Context r5 = r0.f1911a
            java.lang.String r4 = r4.mo62918a(r5)
            boolean r5 = r28.m1828b()
            if (r5 == 0) goto L_0x00f0
            java.lang.String r5 = "tablet"
            goto L_0x00f2
        L_0x00f0:
            java.lang.String r5 = "phone"
        L_0x00f2:
            r24 = r5
            com.truenet.android.DeviceInfo r27 = new com.truenet.android.DeviceInfo
            r5 = r27
            java.lang.String r1 = r1.toString()
            r9 = r1
            java.lang.String r11 = "locale.toString()"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r11)
            java.lang.String r1 = "advertisingId"
            p000a.p001a.p003b.p005b.C0032h.m41a(r10, r1)
            int r1 = android.os.Build.VERSION.SDK_INT
            java.lang.String r12 = java.lang.String.valueOf(r1)
            java.lang.String r1 = android.os.Build.MODEL
            r13 = r1
            java.lang.String r11 = "Build.MODEL"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r11)
            java.lang.String r1 = android.os.Build.MANUFACTURER
            r14 = r1
            java.lang.String r11 = "Build.MANUFACTURER"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r11)
            java.lang.String r1 = android.os.Build.VERSION.RELEASE
            r15 = r1
            java.lang.String r11 = "Build.VERSION.RELEASE"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r11)
            android.content.Context r1 = r0.f1911a
            java.lang.String r1 = r1.getPackageName()
            r16 = r1
            java.lang.String r11 = "context.packageName"
            p000a.p001a.p003b.p005b.C0032h.m41a(r1, r11)
            java.lang.String r1 = "ips"
            p000a.p001a.p003b.p005b.C0032h.m41a(r2, r1)
            java.lang.String r1 = "ipsName"
            p000a.p001a.p003b.p005b.C0032h.m41a(r8, r1)
            android.content.Context r1 = r0.f1911a
            com.truenet.android.a.e r1 = com.truenet.android.p096a.C5197d.m3941b(r1)
            java.lang.String r22 = r1.mo62916a()
            java.lang.String r1 = "signalLevel"
            p000a.p001a.p003b.p005b.C0032h.m41a(r3, r1)
            java.lang.String r11 = "android"
            java.lang.String r25 = "1.0.16"
            java.lang.String r26 = ""
            r1 = r8
            r8 = r4
            r18 = r2
            r19 = r1
            r23 = r3
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
            return r27
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.C3029a.mo44587a():com.truenet.android.DeviceInfo");
    }
}
