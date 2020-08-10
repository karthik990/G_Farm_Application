package com.p021b.p022a.p023a.p024a.p028c;

import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* renamed from: com.b.a.a.a.c.a */
public class C0972a {

    /* renamed from: a */
    private static C0972a f132a = new C0972a();

    /* renamed from: b */
    private final ArrayList<C0970i> f133b = new ArrayList<>();

    /* renamed from: c */
    private final ArrayList<C0970i> f134c = new ArrayList<>();

    private C0972a() {
    }

    /* renamed from: a */
    public static C0972a m170a() {
        return f132a;
    }

    /* renamed from: a */
    public void mo11512a(C0970i iVar) {
        this.f133b.add(iVar);
    }

    /* renamed from: b */
    public Collection<C0970i> mo11513b() {
        return Collections.unmodifiableCollection(this.f133b);
    }

    /* renamed from: b */
    public void mo11514b(C0970i iVar) {
        boolean d = mo11517d();
        this.f134c.add(iVar);
        if (!d) {
            C0979e.m206a().mo11542b();
        }
    }

    /* renamed from: c */
    public Collection<C0970i> mo11515c() {
        return Collections.unmodifiableCollection(this.f134c);
    }

    /* renamed from: c */
    public void mo11516c(C0970i iVar) {
        boolean d = mo11517d();
        this.f133b.remove(iVar);
        this.f134c.remove(iVar);
        if (d && !mo11517d()) {
            C0979e.m206a().mo11543c();
        }
    }

    /* renamed from: d */
    public boolean mo11517d() {
        return this.f134c.size() > 0;
    }
}
