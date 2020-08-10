package com.p021b.p022a.p023a.p024a.p030e;

import android.text.TextUtils;
import com.p021b.p022a.p023a.p024a.C0954a;
import com.p021b.p022a.p023a.p024a.p026b.C0967f;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;

/* renamed from: com.b.a.a.a.e.e */
public class C0991e {
    /* renamed from: a */
    public static void m253a() {
        if (!C0954a.m92b()) {
            throw new IllegalStateException("Method called before OMID activation");
        }
    }

    /* renamed from: a */
    public static void m254a(C0967f fVar) {
        if (fVar.equals(C0967f.NONE)) {
            throw new IllegalArgumentException("Impression owner is none");
        }
    }

    /* renamed from: a */
    public static void m255a(C0970i iVar) {
        if (iVar.mo11502j()) {
            throw new IllegalStateException("AdSession is started");
        }
    }

    /* renamed from: a */
    public static void m256a(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str);
        }
    }

    /* renamed from: a */
    public static void m257a(String str, int i, String str2) {
        if (str.length() > i) {
            throw new IllegalArgumentException(str2);
        }
    }

    /* renamed from: a */
    public static void m258a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(str2);
        }
    }

    /* renamed from: b */
    public static void m259b(C0970i iVar) {
        if (iVar.mo11503k()) {
            throw new IllegalStateException("AdSession is finished");
        }
    }

    /* renamed from: c */
    public static void m260c(C0970i iVar) {
        m265h(iVar);
        m259b(iVar);
    }

    /* renamed from: d */
    public static void m261d(C0970i iVar) {
        if (iVar.mo11498f().mo11564d() != null) {
            throw new IllegalStateException("AdEvents already exists for AdSession");
        }
    }

    /* renamed from: e */
    public static void m262e(C0970i iVar) {
        if (iVar.mo11498f().mo11565e() != null) {
            throw new IllegalStateException("VideoEvents already exists for AdSession");
        }
    }

    /* renamed from: f */
    public static void m263f(C0970i iVar) {
        if (!iVar.mo11504l()) {
            throw new IllegalStateException("Impression event is not expected from the Native AdSession");
        }
    }

    /* renamed from: g */
    public static void m264g(C0970i iVar) {
        if (!iVar.mo11505m()) {
            throw new IllegalStateException("Cannot create VideoEvents for JavaScript AdSession");
        }
    }

    /* renamed from: h */
    private static void m265h(C0970i iVar) {
        if (!iVar.mo11502j()) {
            throw new IllegalStateException("AdSession is not started");
        }
    }
}
