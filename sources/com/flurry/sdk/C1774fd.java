package com.flurry.sdk;

import android.content.Context;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.flurry.sdk.fd */
public final class C1774fd {

    /* renamed from: a */
    private static AtomicBoolean f1054a;

    /* renamed from: b */
    private static AtomicBoolean f1055b;

    /* renamed from: c */
    private static AtomicBoolean f1056c;

    /* renamed from: d */
    private static AtomicBoolean f1057d;

    /* renamed from: a */
    public static boolean m929a() {
        AtomicBoolean atomicBoolean = f1054a;
        if (atomicBoolean != null) {
            return atomicBoolean.get();
        }
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(m930a("android.permission.ACCESS_FINE_LOCATION"));
        f1054a = atomicBoolean2;
        return atomicBoolean2.get();
    }

    /* renamed from: b */
    public static boolean m931b() {
        AtomicBoolean atomicBoolean = f1055b;
        if (atomicBoolean != null) {
            return atomicBoolean.get();
        }
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(m930a("android.permission.ACCESS_COARSE_LOCATION"));
        f1055b = atomicBoolean2;
        return atomicBoolean2.get();
    }

    /* renamed from: c */
    public static boolean m932c() {
        AtomicBoolean atomicBoolean = f1056c;
        if (atomicBoolean != null) {
            return atomicBoolean.get();
        }
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(m930a("android.permission.ACCESS_NETWORK_STATE"));
        f1056c = atomicBoolean2;
        return atomicBoolean2.get();
    }

    /* renamed from: d */
    public static boolean m933d() {
        AtomicBoolean atomicBoolean = f1057d;
        if (atomicBoolean != null) {
            return atomicBoolean.get();
        }
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(m930a("android.permission.READ_PHONE_STATE"));
        f1057d = atomicBoolean2;
        return atomicBoolean2.get();
    }

    /* renamed from: a */
    private static boolean m930a(String str) {
        Context a = C1576b.m502a();
        if (a == null) {
            C1685cy.m754a(6, "PermissionUtil", "Context is null when checking permission.");
            return false;
        } else if (a.checkCallingOrSelfPermission(str) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
