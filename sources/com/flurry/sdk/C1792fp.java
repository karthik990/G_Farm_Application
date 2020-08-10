package com.flurry.sdk;

import com.flurry.sdk.C1788fm.C1789a;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.flurry.sdk.fp */
final class C1792fp extends C1798fr {

    /* renamed from: a */
    protected List<C1812fy> f1080a = new ArrayList();

    /* renamed from: b */
    protected C1811fx f1081b = new C1811fx() {
        /* renamed from: a */
        public final void mo16483a(C1930jp jpVar) {
            C1792fp.this.mo16476c(jpVar);
        }

        /* renamed from: b */
        public final void mo16485b(C1930jp jpVar) {
            C1792fp.this.mo16487d(jpVar);
        }

        /* renamed from: a */
        public final void mo16484a(Runnable runnable) {
            C1792fp.this.runAsync(runnable);
        }
    };

    C1792fp(C1788fm fmVar) {
        super("PolicyModule", fmVar);
        this.f1080a.add(new C1814fz(this.f1081b));
    }

    /* renamed from: a */
    public final void mo16471a(final C1930jp jpVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1792fp.m981a(C1792fp.this, jpVar);
                C1792fp.this.mo16487d(jpVar);
            }
        });
    }

    /* renamed from: b */
    public final C1789a mo16472b(final C1930jp jpVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1792fp.m981a(C1792fp.this, jpVar);
            }
        });
        return super.mo16472b(jpVar);
    }

    /* renamed from: c */
    public final void mo16482c() {
        for (C1812fy a : this.f1080a) {
            a.mo16490a();
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m981a(C1792fp fpVar, C1930jp jpVar) {
        for (C1812fy a : fpVar.f1080a) {
            a.mo16491a(jpVar);
        }
    }
}
