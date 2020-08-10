package com.flurry.sdk;

/* renamed from: com.flurry.sdk.ax */
public final class C1571ax extends C1942m<C1570aw> {

    /* renamed from: a */
    public String f597a;

    /* renamed from: b */
    public boolean f598b = false;

    /* renamed from: d */
    protected C1949o<C1955r> f599d = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1571ax axVar = C1571ax.this;
            axVar.notifyObservers(new C1570aw(axVar.f597a, C1571ax.this.f598b));
        }
    };

    /* renamed from: e */
    private C1951q f600e;

    public C1571ax(C1951q qVar) {
        super("NotificationProvider");
        this.f600e = qVar;
        this.f600e.subscribe(this.f599d);
    }

    public final void subscribe(final C1949o<C1570aw> oVar) {
        super.subscribe(oVar);
        final C1570aw awVar = new C1570aw(this.f597a, this.f598b);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                oVar.mo16242a(awVar);
            }
        });
    }

    public final void destroy() {
        super.destroy();
        this.f600e.unsubscribe(this.f599d);
    }
}
