package com.p021b.p022a.p023a.p024a.p026b;

import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import java.net.URL;

/* renamed from: com.b.a.a.a.b.h */
public final class C0969h {

    /* renamed from: a */
    private final String f119a;

    /* renamed from: b */
    private final URL f120b;

    /* renamed from: c */
    private final String f121c;

    private C0969h(String str, URL url, String str2) {
        this.f119a = str;
        this.f120b = url;
        this.f121c = str2;
    }

    /* renamed from: a */
    public static C0969h m138a(String str, URL url, String str2) {
        C0991e.m258a(str, "VendorKey is null or empty");
        C0991e.m256a((Object) url, "ResourceURL is null");
        C0991e.m258a(str2, "VerificationParameters is null or empty");
        return new C0969h(str, url, str2);
    }

    /* renamed from: a */
    public String mo11492a() {
        return this.f119a;
    }

    /* renamed from: b */
    public URL mo11493b() {
        return this.f120b;
    }

    /* renamed from: c */
    public String mo11494c() {
        return this.f121c;
    }
}
