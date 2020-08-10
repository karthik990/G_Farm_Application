package com.p021b.p022a.p023a.p024a.p033h.p034a;

import com.p021b.p022a.p023a.p024a.p033h.p034a.C1006b.C1007a;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.b.a.a.a.h.a.c */
public class C1009c implements C1007a {

    /* renamed from: a */
    private final BlockingQueue<Runnable> f200a = new LinkedBlockingQueue();

    /* renamed from: b */
    private final ThreadPoolExecutor f201b;

    /* renamed from: c */
    private final ArrayDeque<C1006b> f202c = new ArrayDeque<>();

    /* renamed from: d */
    private C1006b f203d = null;

    public C1009c() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, this.f200a);
        this.f201b = threadPoolExecutor;
    }

    /* renamed from: a */
    private void m321a() {
        this.f203d = (C1006b) this.f202c.poll();
        C1006b bVar = this.f203d;
        if (bVar != null) {
            bVar.mo11582a(this.f201b);
        }
    }

    /* renamed from: a */
    public void mo11584a(C1006b bVar) {
        this.f203d = null;
        m321a();
    }

    /* renamed from: b */
    public void mo11587b(C1006b bVar) {
        bVar.mo11580a((C1007a) this);
        this.f202c.add(bVar);
        if (this.f203d == null) {
            m321a();
        }
    }
}
