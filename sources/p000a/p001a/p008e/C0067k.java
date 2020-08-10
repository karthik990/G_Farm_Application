package p000a.p001a.p008e;

import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.e.k */
/* compiled from: StartAppSDK */
class C0067k extends C0066j {
    /* renamed from: a */
    public static /* bridge */ /* synthetic */ boolean m63a(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return C0059c.m62a(str, str2, z);
    }

    /* renamed from: a */
    public static final boolean m62a(String str, String str2, boolean z) {
        C0032h.m44b(str, "$receiver");
        C0032h.m44b(str2, "prefix");
        if (!z) {
            return str.startsWith(str2);
        }
        return C0059c.m61a(str, 0, str2, 0, str2.length(), z);
    }

    /* renamed from: a */
    public static final boolean m61a(String str, int i, String str2, int i2, int i3, boolean z) {
        C0032h.m44b(str, "$receiver");
        C0032h.m44b(str2, "other");
        if (!z) {
            return str.regionMatches(i, str2, i2, i3);
        }
        return str.regionMatches(z, i, str2, i2, i3);
    }
}
