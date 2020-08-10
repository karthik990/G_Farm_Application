package com.flurry.sdk;

import android.os.SystemClock;
import com.flurry.sdk.C1924jk.C1925a;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.flurry.sdk.jj */
public final class C1923jj extends C1927jm {

    /* renamed from: a */
    private static String f1324a = "UserPropertiesFrame";

    /* renamed from: b */
    private static String f1325b = "true";

    /* renamed from: c */
    private static final AtomicInteger f1326c = new AtomicInteger(0);

    private C1923jj(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.USER_PROPERTY;
    }

    /* renamed from: a */
    private static void m1176a(String str, String str2, C1925a aVar) {
        m1178a(str, str2 == null ? Collections.emptyList() : Collections.singletonList(str2), aVar);
    }

    /* renamed from: a */
    private static void m1178a(String str, List<String> list, C1925a aVar) {
        C1924jk jkVar = new C1924jk(f1326c.incrementAndGet(), SystemClock.elapsedRealtime(), str, list, aVar);
        C1771fb.m926a().mo16467a(new C1923jj(jkVar));
    }

    /* renamed from: a */
    public static void m1175a(String str, String str2) {
        m1176a(str, str2, C1925a.Assign);
    }

    /* renamed from: a */
    public static void m1177a(String str, List<String> list) {
        m1178a(str, list, C1925a.Set);
    }

    /* renamed from: b */
    public static void m1180b(String str, String str2) {
        if (str2 == null) {
            C1685cy.m754a(2, f1324a, "User Property is null, do not send the frame.");
        } else {
            m1176a(str, str2, C1925a.Add);
        }
    }

    /* renamed from: b */
    public static void m1181b(String str, List<String> list) {
        if (list == null || list.isEmpty()) {
            C1685cy.m754a(2, f1324a, "User Properties list is empty, do not send the frame.");
        } else {
            m1178a(str, list, C1925a.Add);
        }
    }

    /* renamed from: c */
    public static void m1182c(String str, String str2) {
        if (str2 == null) {
            C1685cy.m754a(2, f1324a, "User Property is null, do not send the frame.");
        } else {
            m1176a(str, str2, C1925a.Remove);
        }
    }

    /* renamed from: c */
    public static void m1183c(String str, List<String> list) {
        if (list == null || list.isEmpty()) {
            C1685cy.m754a(2, f1324a, "User Properties list is empty, do not send the frame.");
        } else {
            m1178a(str, list, C1925a.Remove);
        }
    }

    /* renamed from: a */
    public static void m1174a(String str) {
        m1178a(str, Collections.emptyList(), C1925a.Clear);
    }

    /* renamed from: b */
    public static void m1179b(String str) {
        m1176a(str, f1325b, C1925a.Flag);
    }
}
