package com.flurry.sdk;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* renamed from: com.flurry.sdk.z */
public final class C1964z {

    /* renamed from: c */
    private static C1964z f1468c;

    /* renamed from: a */
    final UncaughtExceptionHandler f1469a = Thread.getDefaultUncaughtExceptionHandler();

    /* renamed from: b */
    final Map<UncaughtExceptionHandler, Void> f1470b = new WeakHashMap();

    /* renamed from: com.flurry.sdk.z$a */
    final class C1965a implements UncaughtExceptionHandler {
        private C1965a() {
        }

        /* synthetic */ C1965a(C1964z zVar, byte b) {
            this();
        }

        public final void uncaughtException(Thread thread, Throwable th) {
            for (UncaughtExceptionHandler uncaughtException : C1964z.this.mo16539c()) {
                try {
                    uncaughtException.uncaughtException(thread, th);
                } catch (Throwable unused) {
                }
            }
            if (C1964z.this.f1469a != null) {
                try {
                    C1964z.this.f1469a.uncaughtException(thread, th);
                } catch (Throwable unused2) {
                }
            }
        }
    }

    private C1964z() {
        Thread.setDefaultUncaughtExceptionHandler(new C1965a(this, 0));
    }

    /* renamed from: a */
    public static synchronized C1964z m1252a() {
        C1964z zVar;
        synchronized (C1964z.class) {
            if (f1468c == null) {
                f1468c = new C1964z();
            }
            zVar = f1468c;
        }
        return zVar;
    }

    /* renamed from: b */
    public static synchronized void m1253b() {
        synchronized (C1964z.class) {
            if (f1468c != null) {
                Thread.setDefaultUncaughtExceptionHandler(f1468c.f1469a);
            }
            f1468c = null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final Set<UncaughtExceptionHandler> mo16539c() {
        Set<UncaughtExceptionHandler> keySet;
        synchronized (this.f1470b) {
            keySet = this.f1470b.keySet();
        }
        return keySet;
    }
}
