package com.flurry.sdk;

import com.flurry.android.FlurryAgent;
import java.util.Collections;
import java.util.Map;

/* renamed from: com.flurry.sdk.be */
public final class C1588be {

    /* renamed from: a */
    private static boolean f644a = false;

    /* renamed from: b */
    private static C1588be f645b;

    private C1588be() {
    }

    /* renamed from: a */
    public static synchronized C1588be m517a() {
        C1588be beVar;
        synchronized (C1588be.class) {
            if (f645b == null) {
                f645b = new C1588be();
            }
            beVar = f645b;
        }
        return beVar;
    }

    /* renamed from: a */
    public static void m519a(String str, Map<String, String> map) {
        if (f644a) {
            FlurryAgent.logEvent(str, map);
            StringBuilder sb = new StringBuilder("Log SDK internal events. ");
            sb.append(str);
            sb.append("SDKLogManager");
            C1685cy.m754a(4, "FlurryErrorProvider", sb.toString());
        }
    }

    /* renamed from: a */
    public static void m518a(String str, String str2, Throwable th) {
        FlurryAgent.onError(str, str2, th, Collections.emptyMap());
        StringBuilder sb = new StringBuilder("Log SDK internal errors. ");
        sb.append(str2);
        sb.append("SDKLogManager");
        C1685cy.m754a(4, "FlurryErrorProvider", sb.toString());
    }
}
