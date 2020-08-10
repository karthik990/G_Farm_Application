package com.flurry.sdk;

import android.os.Bundle;
import com.flurry.sdk.C1550ao.C1551a;
import java.util.HashMap;

/* renamed from: com.flurry.sdk.q */
public final class C1951q extends C1942m<C1955r> {

    /* renamed from: a */
    protected C1949o<C1550ao> f1436a = new C1949o<C1550ao>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1550ao aoVar = (C1550ao) obj;
            int i = C19543.f1443a[aoVar.f526a.ordinal()];
            if (i == 1) {
                C1951q.m1232a(C1951q.this, true);
            } else if (i != 2) {
                if (i == 3) {
                    Bundle bundle = aoVar.f527b;
                    if (bundle != null) {
                        String str = "trim_memory_level";
                        if (bundle.containsKey(str) && bundle.getInt(str) == 20) {
                            C1951q.m1232a(C1951q.this, false);
                        }
                    }
                }
            } else {
                C1951q.m1232a(C1951q.this, false);
            }
        }
    };

    /* renamed from: b */
    private C1552ap f1437b;

    /* renamed from: d */
    private C1955r f1438d = null;

    /* renamed from: com.flurry.sdk.q$3 */
    static /* synthetic */ class C19543 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1443a = new int[C1551a.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.flurry.sdk.ao$a[] r0 = com.flurry.sdk.C1550ao.C1551a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1443a = r0
                int[] r0 = f1443a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.ao$a r1 = com.flurry.sdk.C1550ao.C1551a.STARTED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1443a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.ao$a r1 = com.flurry.sdk.C1550ao.C1551a.APP_BACKGROUND     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1443a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.flurry.sdk.ao$a r1 = com.flurry.sdk.C1550ao.C1551a.TRIM_MEMORY     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1951q.C19543.<clinit>():void");
        }
    }

    public C1951q(C1552ap apVar) {
        super("AppStateChangeProvider");
        this.f1437b = apVar;
        C1950p pVar = C1950p.UNKNOWN;
        this.f1438d = new C1955r(pVar, pVar);
        this.f1437b.subscribe(this.f1436a);
    }

    public final void subscribe(final C1949o<C1955r> oVar) {
        super.subscribe(oVar);
        final C1955r rVar = this.f1438d;
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                oVar.mo16242a(rVar);
            }
        });
    }

    /* renamed from: a */
    public final C1950p mo16243a() {
        C1955r rVar = this.f1438d;
        if (rVar == null) {
            return C1950p.UNKNOWN;
        }
        return rVar.f1445b;
    }

    public final void destroy() {
        super.destroy();
        this.f1437b.unsubscribe(this.f1436a);
    }

    /* renamed from: a */
    static /* synthetic */ void m1232a(C1951q qVar, boolean z) {
        C1950p pVar = z ? C1950p.FOREGROUND : C1950p.BACKGROUND;
        if (qVar.f1438d.f1445b != pVar) {
            qVar.f1438d = new C1955r(qVar.f1438d.f1445b, pVar);
            StringBuilder sb = new StringBuilder("AppStateChangeRule: prev ");
            sb.append(qVar.f1438d.f1444a);
            sb.append(" stateData.currentState:");
            sb.append(qVar.f1438d.f1445b);
            C1685cy.m754a(2, "AppStateChangeProvider", sb.toString());
            HashMap hashMap = new HashMap();
            hashMap.put("previous_state", qVar.f1438d.f1444a.name());
            hashMap.put("current_state", qVar.f1438d.f1445b.name());
            C1588be.m517a();
            C1588be.m519a("AppStateChangeProvider: app state change", hashMap);
            qVar.notifyObservers(new C1955r(qVar.f1438d.f1444a, qVar.f1438d.f1445b));
        }
    }
}
