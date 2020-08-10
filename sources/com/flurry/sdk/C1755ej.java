package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryModule;
import java.util.List;

/* renamed from: com.flurry.sdk.ej */
public final class C1755ej {

    /* renamed from: a */
    private static boolean f1014a;

    /* renamed from: b */
    private static boolean f1015b;

    /* renamed from: a */
    public static synchronized void m900a() {
        synchronized (C1755ej.class) {
            if (!f1014a) {
                try {
                    C1689db.m782a(Class.forName("com.flurry.android.bridge.FlurryBridgeModule"));
                } catch (ClassNotFoundException | NoClassDefFoundError unused) {
                    C1685cy.m754a(3, "FlurrySDK", "Ads module not available");
                }
                f1014a = true;
            }
        }
    }

    /* renamed from: a */
    public static synchronized void m901a(Context context) {
        synchronized (C1755ej.class) {
            C1689db.m785b(context);
        }
    }

    /* renamed from: a */
    public static synchronized void m902a(List<FlurryModule> list) {
        synchronized (C1755ej.class) {
            if (!f1015b) {
                if (list != null) {
                    for (FlurryModule flurryModule : list) {
                        C1689db.m781a((C1690dc) flurryModule);
                    }
                }
                f1015b = true;
            }
        }
    }

    /* renamed from: b */
    public static synchronized void m903b() {
        synchronized (C1755ej.class) {
            C1689db.m779a();
            C1689db.m784b();
            f1014a = false;
            f1015b = false;
        }
    }
}
