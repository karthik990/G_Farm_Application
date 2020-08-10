package com.p021b.p022a.p023a.p024a.p028c;

import android.content.Context;
import android.os.Handler;
import com.p021b.p022a.p023a.p024a.p025a.C0956b;
import com.p021b.p022a.p023a.p024a.p025a.C0957c;
import com.p021b.p022a.p023a.p024a.p025a.C0958d;
import com.p021b.p022a.p023a.p024a.p025a.C0959e;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p028c.C0973b.C0975a;
import com.p021b.p022a.p023a.p024a.p033h.C1000a;

/* renamed from: com.b.a.a.a.c.e */
public class C0979e implements C0957c, C0975a {

    /* renamed from: a */
    private static C0979e f148a;

    /* renamed from: b */
    private float f149b = 0.0f;

    /* renamed from: c */
    private final C0959e f150c;

    /* renamed from: d */
    private final C0956b f151d;

    /* renamed from: e */
    private C0958d f152e;

    /* renamed from: f */
    private C0972a f153f;

    public C0979e(C0959e eVar, C0956b bVar) {
        this.f150c = eVar;
        this.f151d = bVar;
    }

    /* renamed from: a */
    public static C0979e m206a() {
        if (f148a == null) {
            f148a = new C0979e(new C0959e(), new C0956b());
        }
        return f148a;
    }

    /* renamed from: e */
    private C0972a m207e() {
        if (this.f153f == null) {
            this.f153f = C0972a.m170a();
        }
        return this.f153f;
    }

    /* renamed from: a */
    public void mo11459a(float f) {
        this.f149b = f;
        for (C0970i f2 : m207e().mo11515c()) {
            f2.mo11498f().mo11551a(f);
        }
    }

    /* renamed from: a */
    public void mo11541a(Context context) {
        this.f152e = this.f150c.mo11463a(new Handler(), context, this.f151d.mo11458a(), this);
    }

    /* renamed from: a */
    public void mo11524a(boolean z) {
        if (z) {
            C1000a.m294a().mo11572b();
        } else {
            C1000a.m294a().mo11574d();
        }
    }

    /* renamed from: b */
    public void mo11542b() {
        C0973b.m177a().mo11519a((C0975a) this);
        C0973b.m177a().mo11520b();
        if (C0973b.m177a().mo11522d()) {
            C1000a.m294a().mo11572b();
        }
        this.f152e.mo11460a();
    }

    /* renamed from: c */
    public void mo11543c() {
        C1000a.m294a().mo11573c();
        C0973b.m177a().mo11521c();
        this.f152e.mo11461b();
    }

    /* renamed from: d */
    public float mo11544d() {
        return this.f149b;
    }
}
