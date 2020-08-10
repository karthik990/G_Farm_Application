package com.p021b.p022a.p023a.p024a.p026b;

import com.p021b.p022a.p023a.p024a.p030e.C0991e;

/* renamed from: com.b.a.a.a.b.a */
public final class C0961a {

    /* renamed from: a */
    private final C0970i f99a;

    private C0961a(C0970i iVar) {
        this.f99a = iVar;
    }

    /* renamed from: a */
    public static C0961a m103a(C0963b bVar) {
        C0970i iVar = (C0970i) bVar;
        C0991e.m256a((Object) bVar, "AdSession is null");
        C0991e.m261d(iVar);
        C0991e.m259b(iVar);
        C0961a aVar = new C0961a(iVar);
        iVar.mo11498f().mo11554a(aVar);
        return aVar;
    }

    /* renamed from: a */
    public void mo11464a() {
        C0991e.m259b(this.f99a);
        C0991e.m263f(this.f99a);
        if (!this.f99a.mo11501i()) {
            try {
                this.f99a.mo11475a();
            } catch (Exception unused) {
            }
        }
        if (this.f99a.mo11501i()) {
            this.f99a.mo11497e();
        }
    }
}
