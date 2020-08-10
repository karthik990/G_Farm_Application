package com.truenet.android;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import p000a.p001a.C0075j;
import p000a.p001a.p003b.p004a.C0022a;
import p000a.p001a.p003b.p004a.C0023b;
import p000a.p001a.p003b.p005b.C0032h;
import p000a.p001a.p003b.p005b.C0033i;

/* renamed from: com.truenet.android.c */
/* compiled from: StartAppSDK */
public final class C3039c {

    /* renamed from: a */
    public static final C3040a f1943a = new C3040a(null);

    /* renamed from: b */
    private final ExecutorService f1944b;

    /* renamed from: c */
    private C0022a<C0075j> f1945c = C3042c.f1955a;

    /* renamed from: d */
    private int f1946d;

    /* renamed from: e */
    private final Context f1947e;

    /* renamed from: f */
    private final List<String> f1948f;

    /* renamed from: g */
    private final long f1949g;

    /* renamed from: h */
    private final int f1950h;

    /* renamed from: com.truenet.android.c$a */
    /* compiled from: StartAppSDK */
    public static final class C3040a {
        private C3040a() {
        }

        public /* synthetic */ C3040a(C0029e eVar) {
            this();
        }
    }

    /* renamed from: com.truenet.android.c$b */
    /* compiled from: StartAppSDK */
    static final class C3041b implements Runnable {

        /* renamed from: a */
        final /* synthetic */ C3030b f1951a;

        /* renamed from: b */
        final /* synthetic */ int f1952b;

        /* renamed from: c */
        final /* synthetic */ C3039c f1953c;

        /* renamed from: d */
        final /* synthetic */ C0023b f1954d;

        C3041b(C3030b bVar, int i, C3039c cVar, C0023b bVar2) {
            this.f1951a = bVar;
            this.f1952b = i;
            this.f1953c = cVar;
            this.f1954d = bVar2;
        }

        public final void run() {
            this.f1951a.mo44594g();
            this.f1954d.mo46a(this.f1951a, Integer.valueOf(this.f1952b));
            this.f1953c.m1867b();
        }
    }

    /* renamed from: com.truenet.android.c$c */
    /* compiled from: StartAppSDK */
    static final class C3042c extends C0033i implements C0022a<C0075j> {

        /* renamed from: a */
        public static final C3042c f1955a = new C3042c();

        C3042c() {
            super(0);
        }

        /* renamed from: b */
        public final void mo44616b() {
        }

        /* renamed from: a */
        public /* synthetic */ Object mo45a() {
            mo44616b();
            return C0075j.f19a;
        }
    }

    public C3039c(Context context, List<String> list, ThreadFactory threadFactory, long j, int i, int i2) {
        C0032h.m44b(context, "context");
        C0032h.m44b(list, "links");
        C0032h.m44b(threadFactory, "threadFactory");
        this.f1947e = context;
        this.f1948f = list;
        this.f1949g = j;
        this.f1950h = i;
        this.f1944b = Executors.newFixedThreadPool(i2, threadFactory);
    }

    /* renamed from: a */
    public final void mo44613a(C0022a<C0075j> aVar) {
        C0032h.m44b(aVar, "<set-?>");
        this.f1945c = aVar;
    }

    /* renamed from: a */
    private final int m1865a() {
        int i;
        synchronized (this) {
            this.f1946d++;
            i = this.f1946d;
        }
        return i;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public final void m1867b() {
        synchronized (this) {
            this.f1946d--;
            if (this.f1946d <= 0) {
                this.f1945c.mo45a();
            }
            C0075j jVar = C0075j.f19a;
        }
    }

    /* renamed from: a */
    public final void mo44614a(C0023b<? super C3030b, ? super Integer, C0075j> bVar) {
        C0032h.m44b(bVar, "block");
        int i = 0;
        for (String str : this.f1948f) {
            int i2 = i + 1;
            m1865a();
            C3030b bVar2 = new C3030b(this.f1947e, str, this.f1950h, this.f1949g);
            this.f1944b.execute(new C3041b(bVar2, i, this, bVar));
            i = i2;
        }
    }
}
