package com.flurry.sdk;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collections;
import java.util.Map;

/* renamed from: com.flurry.sdk.ab */
public final class C1518ab extends C1942m<C1517aa> implements UncaughtExceptionHandler {

    /* renamed from: a */
    public C1961w f442a = new C1961w();

    /* renamed from: b */
    public boolean f443b = false;

    public C1518ab() {
        super("FlurryErrorProvider");
        C1964z a = C1964z.m1252a();
        synchronized (a.f1470b) {
            a.f1470b.put(this, null);
        }
    }

    public final void destroy() {
        super.destroy();
        C1964z.m1253b();
        C1961w wVar = this.f442a;
        if (wVar != null) {
            wVar.f1461a = null;
            this.f442a = null;
        }
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        String str;
        th.printStackTrace();
        long currentTimeMillis = System.currentTimeMillis();
        if (this.f443b) {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace == null || stackTrace.length <= 0) {
                str = th.getMessage() != null ? th.getMessage() : "";
            } else {
                StringBuilder sb = new StringBuilder();
                if (th.getMessage() != null) {
                    sb.append(" (");
                    sb.append(th.getMessage());
                    sb.append(")\n");
                }
                str = sb.toString();
            }
            C1517aa aaVar = new C1517aa(C1963y.UNCAUGHT_EXCEPTION_ID.f1467c, currentTimeMillis, str, th.getClass().getName(), th, C1962x.m1243a(), null, this.f442a.mo16536a());
            notifyObservers(aaVar);
        }
    }

    /* renamed from: a */
    public final void mo16239a(String str, long j, String str2, String str3, Throwable th, Map<String, String> map, Map<String, String> map2) {
        C1517aa aaVar = new C1517aa(str, j, str2, str3, th, map, map2, Collections.emptyList());
        notifyObservers(aaVar);
    }
}
