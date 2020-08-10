package com.truenet.android.p096a;

import android.content.Context;
import android.net.NetworkInfo;

/* renamed from: com.truenet.android.a.e */
/* compiled from: StartAppSDK */
public final class C5198e {

    /* renamed from: a */
    private final NetworkInfo f3642a;

    /* renamed from: b */
    private final boolean f3643b;

    /* renamed from: c */
    private final boolean f3644c;

    /* renamed from: d */
    private final boolean f3645d;

    /* renamed from: e */
    private final String f3646e;

    /* renamed from: f */
    private final Context f3647f;

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
        if (r4 != null) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0072, code lost:
        if (r4 != null) goto L_0x0064;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public C5198e(android.content.Context r4) {
        /*
            r3 = this;
            java.lang.String r0 = "context"
            p000a.p001a.p003b.p005b.C0032h.m44b(r4, r0)
            r3.<init>()
            r3.f3647f = r4
            android.content.Context r4 = r3.f3647f
            java.lang.String r0 = "android.permission.ACCESS_NETWORK_STATE"
            boolean r4 = com.truenet.android.p096a.C5202h.m3951a(r4, r0)
            if (r4 == 0) goto L_0x001f
            android.content.Context r4 = r3.f3647f
            android.net.ConnectivityManager r4 = com.truenet.android.p096a.C5197d.m3940a(r4)
            android.net.NetworkInfo r4 = r4.getActiveNetworkInfo()
            goto L_0x0020
        L_0x001f:
            r4 = 0
        L_0x0020:
            r3.f3642a = r4
            android.net.NetworkInfo r4 = r3.f3642a
            r0 = 0
            if (r4 == 0) goto L_0x002c
            boolean r4 = r4.isConnected()
            goto L_0x002d
        L_0x002c:
            r4 = 0
        L_0x002d:
            r3.f3643b = r4
            android.net.NetworkInfo r4 = r3.f3642a
            r1 = 1
            if (r4 == 0) goto L_0x0040
            boolean r2 = r3.f3643b
            if (r2 == 0) goto L_0x0040
            int r4 = r4.getType()
            if (r4 != r1) goto L_0x0040
            r4 = 1
            goto L_0x0041
        L_0x0040:
            r4 = 0
        L_0x0041:
            r3.f3644c = r4
            android.net.NetworkInfo r4 = r3.f3642a
            if (r4 == 0) goto L_0x0052
            boolean r2 = r3.f3643b
            if (r2 == 0) goto L_0x0052
            int r4 = r4.getType()
            if (r4 != 0) goto L_0x0052
            r0 = 1
        L_0x0052:
            r3.f3645d = r0
            boolean r4 = r3.f3645d
            java.lang.String r0 = ""
            if (r4 == 0) goto L_0x0066
            android.net.NetworkInfo r4 = r3.f3642a
            if (r4 == 0) goto L_0x0075
            java.lang.String r4 = r4.getSubtypeName()
            if (r4 == 0) goto L_0x0075
        L_0x0064:
            r0 = r4
            goto L_0x0075
        L_0x0066:
            boolean r4 = r3.f3644c
            if (r4 == 0) goto L_0x0075
            android.net.NetworkInfo r4 = r3.f3642a
            if (r4 == 0) goto L_0x0075
            java.lang.String r4 = r4.getTypeName()
            if (r4 == 0) goto L_0x0075
            goto L_0x0064
        L_0x0075:
            r3.f3646e = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.p096a.C5198e.<init>(android.content.Context):void");
    }

    /* renamed from: a */
    public final String mo62916a() {
        return this.f3646e;
    }
}
