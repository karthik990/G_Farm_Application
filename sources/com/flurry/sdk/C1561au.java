package com.flurry.sdk;

/* renamed from: com.flurry.sdk.au */
public final class C1561au {

    /* renamed from: a */
    public final boolean f560a;

    /* renamed from: b */
    public final C1562a f561b;

    /* renamed from: c */
    public final String f562c;

    /* renamed from: d */
    public final String f563d;

    /* renamed from: e */
    public final String f564e;

    /* renamed from: f */
    public final String f565f;

    /* renamed from: g */
    public final String f566g;

    /* renamed from: h */
    public final String f567h;

    /* renamed from: i */
    public final int f568i;

    /* renamed from: com.flurry.sdk.au$a */
    public enum C1562a {
        NONE_OR_UNKNOWN(0),
        NETWORK_AVAILABLE(1),
        WIFI(2),
        CELL(3);
        

        /* renamed from: e */
        public int f574e;

        private C1562a(int i) {
            this.f574e = i;
        }
    }

    C1561au(C1562a aVar, boolean z, String str, String str2, String str3, String str4, String str5, String str6, int i) {
        this.f560a = z;
        this.f561b = aVar;
        this.f562c = str;
        this.f563d = str2;
        this.f564e = str3;
        this.f565f = str4;
        this.f566g = str5;
        this.f567h = str6;
        this.f568i = i;
    }
}
