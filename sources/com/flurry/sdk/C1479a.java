package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import com.flurry.android.Consent;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.android.FlurryModule;
import com.flurry.sdk.C1756ex.C1758a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.flurry.sdk.a */
public final class C1479a extends C1766f {

    /* renamed from: b */
    public static AtomicBoolean f315b = new AtomicBoolean(false);

    /* renamed from: d */
    private static C1479a f316d = null;

    /* renamed from: a */
    public List<FlurryModule> f317a = new ArrayList();

    public C1479a() {
        super("FlurryAgentImpl", C1756ex.m904a(C1758a.PUBLIC_API));
    }

    /* renamed from: a */
    public static C1479a m353a() {
        if (f316d == null) {
            f316d = new C1479a();
        }
        return f316d;
    }

    /* renamed from: b */
    public static int m354b() {
        C1601bl.m537a();
        return 313;
    }

    /* renamed from: c */
    public static String m355c() {
        return C1601bl.m537a().f670b;
    }

    /* renamed from: a */
    public final void mo16233a(Context context) {
        String str = "FlurryAgentImpl";
        if (context instanceof Activity) {
            C1685cy.m756a(str, "Activity's session is controlled by Flurry SDK");
        } else if (!f315b.get()) {
            C1685cy.m768d(str, "Invalid call to onStartSession. Flurry is not initialized");
        } else {
            runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() {
                    C1898il.m1145b();
                    C1948n.m1229a().f1425k.mo16285a(C1587bd.FOREGROUND, true);
                }
            });
        }
    }

    /* renamed from: i */
    public static boolean m361i() {
        return f315b.get();
    }

    /* renamed from: a */
    public final void mo16235a(final String str, final String str2, final Map<String, String> map) {
        if (!f315b.get()) {
            C1685cy.m768d("FlurryAgentImpl", "Invalid call to addOrigin. Flurry is not initialized");
        } else {
            runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() {
                    C1575az azVar = C1948n.m1229a().f1429o;
                    String str = str;
                    String str2 = str2;
                    Map map = map;
                    if (map == null) {
                        map = new HashMap();
                    }
                    map.put("fl.origin.attribute.version", str2);
                    azVar.f608a.put(str, map);
                    azVar.notifyObservers(new C1574ay(str, str2, map));
                }
            });
        }
    }

    /* renamed from: d */
    public static Consent m356d() {
        if (f315b.get()) {
            return C1948n.m1229a().f1426l.f1451a;
        }
        C1685cy.m768d("FlurryAgentImpl", "Invalid call to getFlurryConsent. Flurry is not initialized");
        return null;
    }

    /* renamed from: e */
    public static String m357e() {
        if (f315b.get()) {
            return C1948n.m1229a().f1427m.mo16243a();
        }
        C1685cy.m768d("FlurryAgentImpl", "Invalid call to getInstantAppName. Flurry is not initialized");
        return null;
    }

    /* renamed from: f */
    public static boolean m358f() {
        if (f315b.get()) {
            return C1948n.m1229a().f1425k.f618d.get();
        }
        C1685cy.m768d("FlurryAgentImpl", "Invalid call to isSessionActive. Flurry is not initialized");
        return false;
    }

    /* renamed from: g */
    public static String m359g() {
        if (f315b.get()) {
            return C1948n.m1229a().f1425k.mo16243a();
        }
        C1685cy.m768d("FlurryAgentImpl", "Invalid call to getSessionId. Flurry is not initialized");
        return null;
    }

    /* renamed from: a */
    public final void mo16234a(String str, String str2, String str3, Map<String, String> map, StackTraceElement[] stackTraceElementArr) {
        if (!f315b.get()) {
            C1685cy.m768d("FlurryAgentImpl", "Invalid call to onError. Flurry is not initialized");
            return;
        }
        final Throwable th = new Throwable(str2);
        th.setStackTrace(stackTraceElementArr);
        final long currentTimeMillis = System.currentTimeMillis();
        final HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        C15169 r0 = new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1948n.m1229a().f1420f.mo16239a(str4, currentTimeMillis, str5, str6, th, null, hashMap);
            }
        };
        runAsync(r0);
    }

    /* renamed from: h */
    public static void m360h() {
        if (f315b.get()) {
            C1689db.m786c();
            C1755ej.m903b();
            C1877ht.m1122b();
            C1948n.m1230b();
            C1771fb.m927b();
            f315b.set(false);
        }
    }

    /* renamed from: a */
    public final FlurryEventRecordStatus mo16232a(String str, Map<String, String> map, boolean z, boolean z2, long j, long j2) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        Map<String, String> map2 = map;
        if (!f315b.get()) {
            C1685cy.m768d("FlurryAgentImpl", "Invalid call to logEvent. Flurry is not initialized");
            return null;
        } else if (C1734dz.m867a(str).length() == 0) {
            return FlurryEventRecordStatus.kFlurryEventFailed;
        } else {
            final HashMap hashMap = new HashMap();
            if (map2 != null) {
                hashMap.putAll(map);
            }
            if (hashMap.size() > 10) {
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventParamsCountExceeded;
            } else {
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
            }
            final String str2 = str;
            final boolean z3 = z;
            final boolean z4 = z2;
            final long j3 = j;
            final long j4 = j2;
            C150431 r1 = new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() {
                    C1837gl.m1075a(str2, hashMap, z3, z4, j3, j4);
                }
            };
            runAsync(r1);
            return flurryEventRecordStatus;
        }
    }
}
