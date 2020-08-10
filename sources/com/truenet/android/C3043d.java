package com.truenet.android;

import java.lang.Thread.UncaughtExceptionHandler;
import p000a.p001a.p003b.p004a.C0023b;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.d */
/* compiled from: StartAppSDK */
final class C3043d implements UncaughtExceptionHandler {

    /* renamed from: a */
    private final /* synthetic */ C0023b f1956a;

    C3043d(C0023b bVar) {
        this.f1956a = bVar;
    }

    public final /* synthetic */ void uncaughtException(Thread thread, Throwable th) {
        C0032h.m41a(this.f1956a.mo46a(thread, th), "invoke(...)");
    }
}
