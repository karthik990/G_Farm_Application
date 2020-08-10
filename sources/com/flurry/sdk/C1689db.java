package com.flurry.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.flurry.sdk.db */
public final class C1689db {

    /* renamed from: a */
    private static List<C1690dc> f877a = new ArrayList();

    /* renamed from: b */
    private static final Map<Class<? extends C1690dc>, C1686cz> f878b = new LinkedHashMap();

    /* renamed from: c */
    private static List<C1686cz> f879c;

    /* renamed from: d */
    private static final Map<Class<? extends C1690dc>, C1690dc> f880d = new LinkedHashMap();

    /* renamed from: e */
    private static List<String> f881e;

    static {
        ArrayList arrayList = new ArrayList();
        f881e = arrayList;
        arrayList.add("com.flurry.android.marketing.core.FlurryMarketingCoreModule");
        f881e.add("com.flurry.android.marketing.FlurryMarketingModule");
        f881e.add("com.flurry.android.config.killswitch.FlurryKillSwitchModule");
        f881e.add("com.flurry.android.nativecrash.FlurryNativeCrashModule");
        f881e.add("com.flurry.android.nativecrash.internal.FlurryNativeCrashModuleStreamingImpl");
        f881e.add("com.flurry.android.FlurryAdModule");
        f881e.add("com.flurry.android.ymadlite.YahooAdModule");
    }

    /* renamed from: a */
    public static void m782a(Class<? extends C1690dc> cls) {
        String str = "FlurryModuleManager";
        C1685cy.m754a(3, str, "Register Ads ".concat(String.valueOf(cls)));
        if (cls != null) {
            synchronized (f878b) {
                f878b.put(cls, new C1686cz(cls));
            }
        }
    }

    /* renamed from: a */
    public static void m781a(C1690dc dcVar) {
        String str = "FlurryModuleManager";
        C1685cy.m754a(3, str, "Register Add-On ".concat(String.valueOf(dcVar)));
        if (dcVar != null) {
            boolean z = false;
            Iterator it = f877a.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((C1690dc) it.next()).getClass().getSimpleName().equals(dcVar.getClass().getSimpleName())) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                f877a.add(dcVar);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(dcVar);
            sb.append(" has been register already as addOn module");
            C1685cy.m754a(3, str, sb.toString());
            return;
        }
        C1685cy.m754a(5, str, "Module is null, cannot register it");
    }

    /* renamed from: a */
    public static void m780a(Context context) {
        C1685cy.m754a(3, "FlurryModuleManager", "Init Ads");
        synchronized (f878b) {
            f879c = new ArrayList(f878b.values());
        }
        for (C1686cz czVar : f879c) {
            C1685cy.m754a(5, "FlurryModuleManager", "registration ".concat(String.valueOf(czVar)));
            try {
                if (czVar.f870a != null && VERSION.SDK_INT >= czVar.f871b) {
                    C1690dc dcVar = (C1690dc) czVar.f870a.newInstance();
                    dcVar.init(context);
                    f880d.put(czVar.f870a, dcVar);
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Flurry Module for class ");
                sb.append(czVar.f870a);
                sb.append(" is not available:");
                C1685cy.m755a(5, "FlurryModuleManager", sb.toString(), e);
            }
        }
    }

    /* renamed from: b */
    public static void m785b(Context context) {
        C1685cy.m754a(2, "FlurryModuleManager", "Init Add on modules");
        synchronized (f880d) {
            for (C1690dc dcVar : f877a) {
                try {
                    C1685cy.m754a(2, "FlurryModuleManager", "Module list: ".concat(String.valueOf(dcVar)));
                    if (f880d.containsKey(dcVar.getClass())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(dcVar.getClass());
                        sb.append(" has been initialized");
                        C1685cy.m754a(5, "FlurryModuleManager", sb.toString());
                    } else {
                        dcVar.init(context);
                        f880d.put(dcVar.getClass(), dcVar);
                        StringBuilder sb2 = new StringBuilder("Initialized modules: ");
                        sb2.append(dcVar.getClass());
                        C1685cy.m754a(3, "FlurryModuleManager", sb2.toString());
                    }
                } catch (C1688da e) {
                    C1685cy.m762b("FlurryModuleManager", e.getMessage());
                }
            }
        }
    }

    /* renamed from: a */
    public static void m779a() {
        C1685cy.m754a(3, "FlurryModuleManager", "Unregister Ads");
        synchronized (f878b) {
            f878b.clear();
        }
    }

    /* renamed from: b */
    public static void m784b() {
        C1685cy.m754a(3, "FlurryModuleManager", "Unregister Add On");
        synchronized (f877a) {
            f877a.clear();
        }
    }

    /* renamed from: c */
    public static synchronized void m786c() {
        synchronized (C1689db.class) {
            C1685cy.m754a(3, "FlurryModuleManager", "Destroy Streaming");
            List d = m787d();
            for (int size = d.size() - 1; size >= 0; size--) {
                try {
                    ((C1690dc) f880d.remove(((C1690dc) d.get(size)).getClass())).destroy();
                } catch (Exception e) {
                    C1685cy.m755a(5, "FlurryModuleManager", "Error destroying module:", e);
                }
            }
        }
    }

    /* renamed from: d */
    private static List<C1690dc> m787d() {
        ArrayList arrayList;
        C1685cy.m754a(3, "FlurryModuleManager", "Get Streaming module list");
        synchronized (f880d) {
            arrayList = new ArrayList(f880d.values());
        }
        return arrayList;
    }

    /* renamed from: a */
    public static boolean m783a(String str) {
        return f881e.contains(str);
    }
}
