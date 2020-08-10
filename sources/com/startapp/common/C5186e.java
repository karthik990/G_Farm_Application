package com.startapp.common;

/* renamed from: com.startapp.common.e */
/* compiled from: StartAppSDK */
public class C5186e extends Exception {

    /* renamed from: b */
    private boolean f3626b;

    /* renamed from: c */
    private int f3627c;

    public C5186e(String str, Throwable th, boolean z, int i) {
        super(str, th);
        this.f3626b = false;
        this.f3627c = 0;
        this.f3626b = z;
        this.f3627c = i;
    }

    public C5186e(String str, Throwable th, int i) {
        this(str, th, false, i);
    }

    /* renamed from: a */
    public int mo62911a() {
        return this.f3627c;
    }

    public C5186e() {
        this.f3626b = false;
        this.f3627c = 0;
    }

    public C5186e(String str, Throwable th) {
        this(str, th, false);
    }

    public C5186e(String str, Throwable th, boolean z) {
        this(str, th, z, 0);
    }

    /* renamed from: b */
    public boolean mo62912b() {
        return this.f3626b;
    }
}
