package com.flurry.sdk;

import com.flurry.android.FlurryAgentListener;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/* renamed from: com.flurry.sdk.bb */
public final class C1578bb extends C1942m<C1577ba> {

    /* renamed from: a */
    public AtomicLong f616a = new AtomicLong(0);

    /* renamed from: b */
    public AtomicLong f617b = new AtomicLong(0);

    /* renamed from: d */
    public AtomicBoolean f618d = new AtomicBoolean(true);

    /* renamed from: e */
    public long f619e;

    /* renamed from: f */
    public List<FlurryAgentListener> f620f = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: g */
    public long f621g;

    /* renamed from: h */
    private C1951q f622h;

    /* renamed from: i */
    private C1949o<C1955r> f623i = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            int i = C15857.f635a[((C1955r) obj).f1445b.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    C1578bb.this.mo16286b(C1587bd.FOREGROUND, false);
                }
                return;
            }
            C1578bb.this.mo16285a(C1587bd.FOREGROUND, false);
        }
    };

    /* renamed from: com.flurry.sdk.bb$7 */
    static /* synthetic */ class C15857 {

        /* renamed from: a */
        static final /* synthetic */ int[] f635a = new int[C1950p.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.flurry.sdk.p[] r0 = com.flurry.sdk.C1950p.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f635a = r0
                int[] r0 = f635a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.p r1 = com.flurry.sdk.C1950p.FOREGROUND     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f635a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.p r1 = com.flurry.sdk.C1950p.BACKGROUND     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1578bb.C15857.<clinit>():void");
        }
    }

    public C1578bb(C1951q qVar) {
        super("ReportingProvider");
        this.f622h = qVar;
        this.f622h.subscribe(this.f623i);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1578bb.this.f621g = C1775fe.m939b("initial_run_time", Long.MIN_VALUE);
            }
        });
    }

    /* renamed from: a */
    public final void mo16284a(FlurryAgentListener flurryAgentListener) {
        if (flurryAgentListener == null) {
            C1685cy.m754a(2, "ReportingProvider", "Cannot register with null listener");
        } else {
            this.f620f.add(flurryAgentListener);
        }
    }

    /* renamed from: a */
    public final String mo16243a() {
        return String.valueOf(this.f616a.get());
    }

    /* renamed from: a */
    public final void mo16283a(long j, long j2) {
        this.f616a.set(j);
        this.f617b.set(j2);
        if (!this.f620f.isEmpty()) {
            final ArrayList arrayList = new ArrayList(this.f620f);
            postOnMainThread(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    for (FlurryAgentListener flurryAgentListener : arrayList) {
                        if (flurryAgentListener != null) {
                            flurryAgentListener.onSessionStarted();
                        }
                    }
                }
            });
        }
    }

    /* renamed from: a */
    public final void mo16285a(final C1587bd bdVar, final boolean z) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                StringBuilder sb = new StringBuilder("Start session: ");
                sb.append(bdVar.name());
                sb.append(", isManualSession: ");
                sb.append(z);
                C1685cy.m754a(3, "ReportingProvider", sb.toString());
                C1578bb.m505a(C1578bb.this, bdVar, C1586bc.SESSION_START, z);
            }
        });
    }

    /* renamed from: b */
    public final void mo16286b(final C1587bd bdVar, final boolean z) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                StringBuilder sb = new StringBuilder("End session: ");
                sb.append(bdVar.name());
                sb.append(", isManualSession: ");
                sb.append(z);
                C1685cy.m754a(3, "ReportingProvider", sb.toString());
                C1578bb.m505a(C1578bb.this, bdVar, C1586bc.SESSION_END, z);
            }
        });
    }

    public final void destroy() {
        super.destroy();
        this.f622h.unsubscribe(this.f623i);
    }

    /* renamed from: a */
    static /* synthetic */ void m505a(C1578bb bbVar, C1587bd bdVar, C1586bc bcVar, boolean z) {
        long j;
        long currentTimeMillis = System.currentTimeMillis();
        if (bbVar.f621g == Long.MIN_VALUE) {
            bbVar.f621g = currentTimeMillis;
            C1775fe.m936a("initial_run_time", bbVar.f621g);
            C1685cy.m754a(3, "ReportingProvider", "Refresh initial timestamp");
        }
        if (bdVar.equals(C1587bd.FOREGROUND)) {
            j = bbVar.f619e;
        } else {
            j = DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
        }
        C1587bd bdVar2 = bdVar;
        C1577ba baVar = new C1577ba(bdVar2, currentTimeMillis, bbVar.f621g, j, bcVar, z);
        bbVar.notifyObservers(baVar);
    }
}
