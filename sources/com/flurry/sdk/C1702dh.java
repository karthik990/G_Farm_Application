package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.dh */
final class C1702dh {

    /* renamed from: a */
    final String f927a;

    /* renamed from: b */
    final String f928b;

    /* renamed from: c */
    final String f929c;

    /* renamed from: d */
    final long f930d = System.nanoTime();

    /* renamed from: e */
    final Map<String, String> f931e = new HashMap();

    /* renamed from: f */
    boolean f932f = false;

    /* renamed from: g */
    long f933g = 0;

    /* renamed from: h */
    long f934h = 0;

    C1702dh(String str, String str2) {
        this.f927a = "Flurry.ScreenTime: ".concat(String.valueOf(str));
        this.f928b = str;
        this.f929c = str2;
    }
}
