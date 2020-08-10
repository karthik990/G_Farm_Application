package com.flurry.sdk;

/* renamed from: com.flurry.sdk.bw */
public final class C1637bw {

    /* renamed from: a */
    public final C1658ce f737a;

    /* renamed from: b */
    public C1666ck f738b;

    public C1637bw(C1658ce ceVar, C1666ck ckVar) {
        this.f737a = ceVar;
        this.f738b = ckVar;
    }

    /* renamed from: a */
    public final String mo16324a(String str, String str2, C1660cg cgVar) {
        C1642by a = this.f738b.mo16362a(str, cgVar);
        if (a == null) {
            a = this.f737a.mo16349a(str);
        }
        return a != null ? a.mo16330a() : str2;
    }

    /* renamed from: a */
    public final int mo16322a(String str, int i, C1660cg cgVar) {
        C1642by a = this.f738b.mo16362a(str, cgVar);
        if (a == null) {
            a = this.f737a.mo16349a(str);
        }
        if (a != null) {
            try {
                return Integer.decode(a.mo16330a()).intValue();
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    /* renamed from: a */
    public final float mo16321a(String str, float f, C1660cg cgVar) {
        C1642by a = this.f738b.mo16362a(str, cgVar);
        if (a == null) {
            a = this.f737a.mo16349a(str);
        }
        if (a != null) {
            try {
                return Float.parseFloat(a.mo16330a());
            } catch (NumberFormatException unused) {
            }
        }
        return f;
    }

    /* renamed from: a */
    public final long mo16323a(String str, long j, C1660cg cgVar) {
        C1642by a = this.f738b.mo16362a(str, cgVar);
        if (a == null) {
            a = this.f737a.mo16349a(str);
        }
        if (a != null) {
            try {
                return Long.decode(a.mo16330a()).longValue();
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    /* renamed from: a */
    public final double mo16320a(String str, double d, C1660cg cgVar) {
        C1642by a = this.f738b.mo16362a(str, cgVar);
        if (a == null) {
            a = this.f737a.mo16349a(str);
        }
        if (a != null) {
            try {
                return Double.parseDouble(a.mo16330a());
            } catch (NumberFormatException unused) {
            }
        }
        return d;
    }
}
