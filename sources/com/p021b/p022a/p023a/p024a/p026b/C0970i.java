package com.p021b.p022a.p023a.p024a.p026b;

import android.view.View;
import com.p021b.p022a.p023a.p024a.p028c.C0972a;
import com.p021b.p022a.p023a.p024a.p028c.C0979e;
import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import com.p021b.p022a.p023a.p024a.p031f.C0993a;
import com.p021b.p022a.p023a.p024a.p032g.C0995a;
import com.p021b.p022a.p023a.p024a.p032g.C0997b;
import com.p021b.p022a.p023a.p024a.p032g.C0998c;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/* renamed from: com.b.a.a.a.b.i */
public class C0970i extends C0963b {

    /* renamed from: a */
    private final C0965d f122a;

    /* renamed from: b */
    private final C0964c f123b;

    /* renamed from: c */
    private final List<C0993a> f124c = new ArrayList();

    /* renamed from: d */
    private C0993a f125d;

    /* renamed from: e */
    private C0995a f126e;

    /* renamed from: f */
    private boolean f127f = false;

    /* renamed from: g */
    private boolean f128g = false;

    /* renamed from: h */
    private String f129h;

    /* renamed from: i */
    private boolean f130i;

    C0970i(C0964c cVar, C0965d dVar) {
        this.f123b = cVar;
        this.f122a = dVar;
        this.f129h = UUID.randomUUID().toString();
        m144e(null);
        this.f126e = dVar.mo11487f() == C0966e.HTML ? new C0997b(dVar.mo11484c()) : new C0998c(dVar.mo11483b(), dVar.mo11486e());
        this.f126e.mo11550a();
        C0972a.m170a().mo11512a(this);
        this.f126e.mo11555a(cVar);
    }

    /* renamed from: c */
    private C0993a m142c(View view) {
        for (C0993a aVar : this.f124c) {
            if (aVar.get() == view) {
                return aVar;
            }
        }
        return null;
    }

    /* renamed from: d */
    private void m143d(View view) {
        if (view == null) {
            throw new IllegalArgumentException("FriendlyObstruction is null");
        }
    }

    /* renamed from: e */
    private void m144e(View view) {
        this.f125d = new C0993a(view);
    }

    /* renamed from: f */
    private void m145f(View view) {
        Collection<C0970i> b = C0972a.m170a().mo11513b();
        if (b != null && b.size() > 0) {
            for (C0970i iVar : b) {
                if (iVar != this && iVar.mo11500h() == view) {
                    iVar.f125d.clear();
                }
            }
        }
    }

    /* renamed from: n */
    private void m146n() {
        if (this.f130i) {
            throw new IllegalStateException("Impression event can only be sent once");
        }
    }

    /* renamed from: a */
    public void mo11475a() {
        if (!this.f127f) {
            this.f127f = true;
            C0972a.m170a().mo11514b(this);
            this.f126e.mo11551a(C0979e.m206a().mo11544d());
            this.f126e.mo11556a(this, this.f122a);
        }
    }

    /* renamed from: a */
    public void mo11476a(View view) {
        if (!this.f128g) {
            C0991e.m256a((Object) view, "AdView is null");
            if (mo11500h() != view) {
                m144e(view);
                mo11498f().mo11569i();
                m145f(view);
            }
        }
    }

    /* renamed from: b */
    public void mo11477b() {
        if (!this.f128g) {
            this.f125d.clear();
            mo11495c();
            this.f128g = true;
            mo11498f().mo11567g();
            C0972a.m170a().mo11516c(this);
            mo11498f().mo11561b();
            this.f126e = null;
        }
    }

    /* renamed from: b */
    public void mo11478b(View view) {
        if (!this.f128g) {
            m143d(view);
            if (m142c(view) == null) {
                this.f124c.add(new C0993a(view));
            }
        }
    }

    /* renamed from: c */
    public void mo11495c() {
        if (!this.f128g) {
            this.f124c.clear();
        }
    }

    /* renamed from: d */
    public List<C0993a> mo11496d() {
        return this.f124c;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public void mo11497e() {
        m146n();
        mo11498f().mo11568h();
        this.f130i = true;
    }

    /* renamed from: f */
    public C0995a mo11498f() {
        return this.f126e;
    }

    /* renamed from: g */
    public String mo11499g() {
        return this.f129h;
    }

    /* renamed from: h */
    public View mo11500h() {
        return (View) this.f125d.get();
    }

    /* renamed from: i */
    public boolean mo11501i() {
        return this.f127f && !this.f128g;
    }

    /* renamed from: j */
    public boolean mo11502j() {
        return this.f127f;
    }

    /* renamed from: k */
    public boolean mo11503k() {
        return this.f128g;
    }

    /* renamed from: l */
    public boolean mo11504l() {
        return this.f123b.mo11479a();
    }

    /* renamed from: m */
    public boolean mo11505m() {
        return this.f123b.mo11480b();
    }
}
