package com.p021b.p022a.p023a.p024a.p026b;

import com.p021b.p022a.p023a.p024a.p030e.C0991e;

/* renamed from: com.b.a.a.a.b.g */
public class C0968g {

    /* renamed from: a */
    private final String f117a;

    /* renamed from: b */
    private final String f118b;

    private C0968g(String str, String str2) {
        this.f117a = str;
        this.f118b = str2;
    }

    /* renamed from: a */
    public static C0968g m135a(String str, String str2) {
        C0991e.m258a(str, "Name is null or empty");
        C0991e.m258a(str2, "Version is null or empty");
        return new C0968g(str, str2);
    }

    /* renamed from: a */
    public String mo11490a() {
        return this.f117a;
    }

    /* renamed from: b */
    public String mo11491b() {
        return this.f118b;
    }
}
