package com.flurry.sdk;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import com.flurry.android.FlurryConfigListener;
import com.flurry.sdk.C1638bx.C1641a;
import com.flurry.sdk.C1655cc.C1656a;
import com.flurry.sdk.C1756ex.C1758a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.bz */
public final class C1644bz extends C1766f {

    /* renamed from: b */
    private static volatile C1644bz f758b;

    /* renamed from: k */
    private static final Object f759k = new Object();

    /* renamed from: p */
    private static C1667cl f760p;

    /* renamed from: a */
    public C1653ca f761a;

    /* renamed from: d */
    private C1637bw f762d;

    /* renamed from: e */
    private C1658ce f763e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1666ck f764f;

    /* renamed from: g */
    private C1672cq f765g;

    /* renamed from: h */
    private Handler f766h;

    /* renamed from: i */
    private final Map<FlurryConfigListener, Pair<C1660cg, WeakReference<Handler>>> f767i;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public final Map<C1660cg, Pair<Boolean, Boolean>> f768j;

    /* renamed from: l */
    private volatile boolean f769l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public volatile boolean f770m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public volatile boolean f771n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public C1651a f772o;

    /* renamed from: com.flurry.sdk.bz$6 */
    static /* synthetic */ class C16506 {

        /* renamed from: a */
        static final /* synthetic */ int[] f782a = new int[C1651a.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.flurry.sdk.bz$a[] r0 = com.flurry.sdk.C1644bz.C1651a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f782a = r0
                int[] r0 = f782a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.bz$a r1 = com.flurry.sdk.C1644bz.C1651a.f786d     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f782a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.bz$a r1 = com.flurry.sdk.C1644bz.C1651a.f783a     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f782a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.flurry.sdk.bz$a r1 = com.flurry.sdk.C1644bz.C1651a.CompleteNoChange     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f782a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.flurry.sdk.bz$a r1 = com.flurry.sdk.C1644bz.C1651a.f785c     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1644bz.C16506.<clinit>():void");
        }
    }

    /* renamed from: com.flurry.sdk.bz$a */
    enum C1651a {
        f783a(r1, 3),
        CompleteNoChange("No Change", 2),
        f785c(r5, 1),
        f786d(r5, 0);
        

        /* renamed from: e */
        int f788e;

        /* renamed from: f */
        private String f789f;

        private C1651a(String str, int i) {
            this.f789f = str;
            this.f788e = i;
        }

        public final String toString() {
            return this.f789f;
        }
    }

    /* renamed from: a */
    public static synchronized C1644bz m630a() {
        C1644bz f;
        synchronized (C1644bz.class) {
            f = m640f();
        }
        return f;
    }

    /* renamed from: f */
    private static synchronized C1644bz m640f() {
        C1644bz bzVar;
        synchronized (C1644bz.class) {
            if (f758b == null) {
                f758b = new C1644bz(0);
            }
            bzVar = f758b;
        }
        return bzVar;
    }

    private C1644bz() {
        this(0);
    }

    private C1644bz(byte b) {
        super("ConfigManager", C1756ex.m904a(C1758a.CONFIG));
        this.f767i = new ConcurrentHashMap();
        this.f768j = new HashMap();
        this.f769l = false;
        this.f770m = false;
        this.f771n = false;
        this.f772o = C1651a.f786d;
        f760p = null;
        for (C1660cg cgVar : C1660cg.m676a()) {
            Map<C1660cg, Pair<Boolean, Boolean>> map = this.f768j;
            Boolean bool = Boolean.FALSE;
            map.put(cgVar, new Pair(bool, bool));
        }
        this.f763e = new C1658ce();
        this.f764f = new C1666ck();
        this.f761a = new C1653ca();
        this.f765g = new C1672cq();
        this.f766h = new Handler(Looper.getMainLooper());
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                String str = "ConfigManager";
                try {
                    String b = C1673cr.m732b(C1576b.m502a());
                    C1685cy.m756a(str, "Cached Data: ".concat(String.valueOf(b)));
                    if (b != null) {
                        String d = C1644bz.this.f761a.mo16347d();
                        C1653ca a = C1644bz.this.f761a;
                        String str2 = null;
                        if (a.f790a != null) {
                            str2 = a.f790a.getString("lastRSA", null);
                        }
                        if (C1673cr.m730a(d, b, str2)) {
                            C1666ck b2 = C1644bz.this.f764f;
                            if (b != null) {
                                try {
                                    b2.mo16366a(C1654cb.m668a(new JSONObject(b)));
                                } catch (Exception e) {
                                    C1685cy.m757a("VariantsManager", "Cached variants parsing error: ", (Throwable) e);
                                }
                            }
                            if (C1644bz.m634b() != null) {
                                C1644bz.m634b();
                                C1667cl.m707a(b2);
                            }
                        } else {
                            C1685cy.m762b(str, "Incorrect signature for cache.");
                            C1673cr.m735c(C1576b.m502a());
                            C1644bz.this.f761a.mo16346c();
                        }
                    }
                    C1644bz.m636c(C1644bz.this);
                    if (C1644bz.this.f764f.mo16372e() > 0) {
                        for (C1660cg cgVar : C1644bz.this.f764f.mo16371d()) {
                            C1644bz.this.f768j.put(cgVar, new Pair(Boolean.TRUE, Boolean.FALSE));
                            C1644bz.this.mo16335a(cgVar, true);
                        }
                    }
                } catch (Exception e2) {
                    C1685cy.m757a(str, "Exception!", (Throwable) e2);
                    C1644bz.m636c(C1644bz.this);
                    if (C1644bz.this.f764f.mo16372e() > 0) {
                        for (C1660cg cgVar2 : C1644bz.this.f764f.mo16371d()) {
                            C1644bz.this.f768j.put(cgVar2, new Pair(Boolean.TRUE, Boolean.FALSE));
                            C1644bz.this.mo16335a(cgVar2, true);
                        }
                    }
                } catch (Throwable th) {
                    C1644bz.m636c(C1644bz.this);
                    if (C1644bz.this.f764f.mo16372e() > 0) {
                        for (C1660cg cgVar3 : C1644bz.this.f764f.mo16371d()) {
                            C1644bz.this.f768j.put(cgVar3, new Pair(Boolean.TRUE, Boolean.FALSE));
                            C1644bz.this.mo16335a(cgVar3, true);
                        }
                    }
                    throw th;
                }
            }
        });
    }

    /* renamed from: b */
    public static C1667cl m634b() {
        return f760p;
    }

    /* renamed from: c */
    public final C1637bw mo16337c() {
        if (this.f762d == null) {
            m641g();
            this.f762d = new C1637bw(this.f763e, this.f764f);
        }
        return this.f762d;
    }

    /* renamed from: g */
    private void m641g() {
        synchronized (f759k) {
            while (!this.f769l) {
                try {
                    f759k.wait();
                } catch (InterruptedException e) {
                    C1685cy.m757a("ConfigManager", "Interrupted Exception!", (Throwable) e);
                }
            }
        }
    }

    /* renamed from: d */
    public final void mo16338d() {
        String str = "ConfigManager";
        if (this.f770m) {
            C1685cy.m754a(3, str, "Preventing re-entry...");
            return;
        }
        this.f770m = true;
        C1685cy.m754a(3, str, "Fetch started");
        for (C1638bx a : C1657cd.m670a(C1672cq.m725a(C1576b.m502a(), "https://cfg.flurry.com/sdk/v1/config"), new C1641a() {
            /* renamed from: a */
            public final void mo16329a(C1655cc ccVar, boolean z) {
                C1651a aVar;
                if (!z) {
                    C1644bz.this.f770m = false;
                }
                String str = "ConfigManager";
                if (ccVar.f798d == C1656a.SUCCEED) {
                    C1685cy.m756a(str, "Fetch succeeded.");
                    aVar = C1651a.f783a;
                    C1644bz.this.f771n = true;
                    for (C1660cg cgVar : C1660cg.m676a()) {
                        boolean z2 = false;
                        if (C1644bz.this.f768j.containsKey(cgVar)) {
                            z2 = ((Boolean) ((Pair) C1644bz.this.f768j.get(cgVar)).first).booleanValue();
                        }
                        C1644bz.this.f768j.put(cgVar, new Pair(Boolean.valueOf(z2), Boolean.FALSE));
                    }
                } else if (ccVar.f798d == C1656a.NO_CHANGE) {
                    C1685cy.m756a(str, "Fetch finished.");
                    aVar = C1651a.CompleteNoChange;
                } else {
                    C1685cy.m756a(str, "Error occured while fetching: ".concat(String.valueOf(ccVar)));
                    aVar = C1651a.f785c;
                }
                if (C1644bz.this.f772o.f788e <= aVar.f788e) {
                    C1644bz.this.f772o = aVar;
                }
                C1644bz.m635b(C1644bz.this, aVar);
            }
        }, this.f761a, this.f764f)) {
            a.mo16243a();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008c, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo16334a(com.flurry.android.FlurryConfigListener r5, com.flurry.sdk.C1660cg r6, android.os.Handler r7) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Map<com.flurry.android.FlurryConfigListener, android.util.Pair<com.flurry.sdk.cg, java.lang.ref.WeakReference<android.os.Handler>>> r0 = r4.f767i
            monitor-enter(r0)
            java.util.Map<com.flurry.android.FlurryConfigListener, android.util.Pair<com.flurry.sdk.cg, java.lang.ref.WeakReference<android.os.Handler>>> r1 = r4.f767i     // Catch:{ all -> 0x008d }
            boolean r1 = r1.containsKey(r5)     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x0018
            r5 = 5
            java.lang.String r6 = "ConfigManager"
            java.lang.String r7 = "The listener is already registered"
            com.flurry.sdk.C1685cy.m754a(r5, r6, r7)     // Catch:{ all -> 0x008d }
            monitor-exit(r0)     // Catch:{ all -> 0x008d }
            return
        L_0x0018:
            java.util.Map<com.flurry.android.FlurryConfigListener, android.util.Pair<com.flurry.sdk.cg, java.lang.ref.WeakReference<android.os.Handler>>> r1 = r4.f767i     // Catch:{ all -> 0x008d }
            android.util.Pair r2 = new android.util.Pair     // Catch:{ all -> 0x008d }
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x008d }
            r3.<init>(r7)     // Catch:{ all -> 0x008d }
            r2.<init>(r6, r3)     // Catch:{ all -> 0x008d }
            r1.put(r5, r2)     // Catch:{ all -> 0x008d }
            int[] r7 = com.flurry.sdk.C1644bz.C16506.f782a     // Catch:{ all -> 0x008d }
            com.flurry.sdk.bz$a r1 = r4.f772o     // Catch:{ all -> 0x008d }
            int r1 = r1.ordinal()     // Catch:{ all -> 0x008d }
            r7 = r7[r1]     // Catch:{ all -> 0x008d }
            r1 = 1
            if (r7 == r1) goto L_0x004b
            r2 = 2
            if (r7 == r2) goto L_0x0048
            r2 = 3
            if (r7 == r2) goto L_0x0044
            r2 = 4
            if (r7 == r2) goto L_0x003e
            goto L_0x004b
        L_0x003e:
            boolean r7 = r4.f770m     // Catch:{ all -> 0x008d }
            r5.onFetchError(r7)     // Catch:{ all -> 0x008d }
            goto L_0x004b
        L_0x0044:
            r5.onFetchNoChange()     // Catch:{ all -> 0x008d }
            goto L_0x004b
        L_0x0048:
            r5.onFetchSuccess()     // Catch:{ all -> 0x008d }
        L_0x004b:
            java.util.Map<com.flurry.sdk.cg, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> r7 = r4.f768j     // Catch:{ all -> 0x008d }
            boolean r7 = r7.containsKey(r6)     // Catch:{ all -> 0x008d }
            if (r7 == 0) goto L_0x007f
            java.util.Map<com.flurry.sdk.cg, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> r7 = r4.f768j     // Catch:{ all -> 0x008d }
            java.lang.Object r6 = r7.get(r6)     // Catch:{ all -> 0x008d }
            android.util.Pair r6 = (android.util.Pair) r6     // Catch:{ all -> 0x008d }
            java.lang.Object r7 = r6.first     // Catch:{ all -> 0x008d }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x008d }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x008d }
            if (r7 != 0) goto L_0x006f
            java.lang.Object r7 = r6.second     // Catch:{ all -> 0x008d }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ all -> 0x008d }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x008d }
            if (r7 == 0) goto L_0x008b
        L_0x006f:
            java.lang.Object r6 = r6.second     // Catch:{ all -> 0x008d }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x008d }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x008d }
            if (r6 != 0) goto L_0x007a
            goto L_0x007b
        L_0x007a:
            r1 = 0
        L_0x007b:
            r5.onActivateComplete(r1)     // Catch:{ all -> 0x008d }
            goto L_0x008b
        L_0x007f:
            java.util.Map<com.flurry.sdk.cg, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> r5 = r4.f768j     // Catch:{ all -> 0x008d }
            android.util.Pair r7 = new android.util.Pair     // Catch:{ all -> 0x008d }
            java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x008d }
            r7.<init>(r1, r1)     // Catch:{ all -> 0x008d }
            r5.put(r6, r7)     // Catch:{ all -> 0x008d }
        L_0x008b:
            monitor-exit(r0)     // Catch:{ all -> 0x008d }
            return
        L_0x008d:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x008d }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1644bz.mo16334a(com.flurry.android.FlurryConfigListener, com.flurry.sdk.cg, android.os.Handler):void");
    }

    /* renamed from: a */
    public final void mo16333a(FlurryConfigListener flurryConfigListener) {
        if (flurryConfigListener != null) {
            synchronized (this.f767i) {
                this.f767i.remove(flurryConfigListener);
            }
        }
    }

    /* renamed from: a */
    public final void mo16335a(C1660cg cgVar, final boolean z) {
        synchronized (this.f767i) {
            for (Entry entry : this.f767i.entrySet()) {
                if (cgVar == null || cgVar == ((Pair) entry.getValue()).first) {
                    final FlurryConfigListener flurryConfigListener = (FlurryConfigListener) entry.getKey();
                    Handler handler = (Handler) ((WeakReference) ((Pair) entry.getValue()).second).get();
                    C16484 r4 = new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                            flurryConfigListener.onActivateComplete(z);
                        }
                    };
                    if (handler == null) {
                        this.f766h.post(r4);
                    } else {
                        handler.post(r4);
                    }
                }
            }
        }
    }

    /* renamed from: e */
    public final List<C1665cj> mo16339e() {
        C1666ck ckVar = this.f764f;
        if (ckVar != null) {
            return ckVar.mo16368b();
        }
        return null;
    }

    /* renamed from: a */
    public final boolean mo16336a(C1660cg cgVar) {
        boolean z;
        if (!this.f771n) {
            return false;
        }
        boolean z2 = true;
        if (cgVar == null) {
            boolean z3 = false;
            for (Entry entry : this.f768j.entrySet()) {
                Pair pair = (Pair) entry.getValue();
                if (!((Boolean) pair.second).booleanValue()) {
                    entry.setValue(new Pair(pair.first, Boolean.TRUE));
                    z3 = true;
                }
            }
            z2 = z3;
        } else {
            Pair pair2 = (Pair) this.f768j.get(cgVar);
            if (pair2 == null || !((Boolean) pair2.second).booleanValue()) {
                Map<C1660cg, Pair<Boolean, Boolean>> map = this.f768j;
                if (pair2 == null) {
                    z = false;
                } else {
                    z = ((Boolean) pair2.first).booleanValue();
                }
                map.put(cgVar, new Pair(Boolean.valueOf(z), Boolean.TRUE));
            } else {
                z2 = false;
            }
        }
        if (!z2) {
            return z2;
        }
        this.f764f.mo16365a(cgVar);
        mo16335a(cgVar, false);
        return z2;
    }

    public final String toString() {
        m641g();
        ArrayList arrayList = new ArrayList();
        List<C1665cj> e = mo16339e();
        if (e == null || e.isEmpty()) {
            return "No variants were found!";
        }
        for (C1665cj cjVar : e) {
            arrayList.add(cjVar.toString());
        }
        return TextUtils.join(",", arrayList);
    }

    /* renamed from: c */
    static /* synthetic */ void m636c(C1644bz bzVar) {
        synchronized (f759k) {
            bzVar.f769l = true;
            f759k.notifyAll();
        }
    }

    /* renamed from: b */
    static /* synthetic */ void m635b(C1644bz bzVar, final C1651a aVar) {
        synchronized (bzVar.f767i) {
            for (Entry entry : bzVar.f767i.entrySet()) {
                final FlurryConfigListener flurryConfigListener = (FlurryConfigListener) entry.getKey();
                Handler handler = (Handler) ((WeakReference) ((Pair) entry.getValue()).second).get();
                C16473 r4 = new C1738eb() {
                    /* renamed from: a */
                    public final void mo16236a() {
                        int i = C16506.f782a[aVar.ordinal()];
                        if (i == 2) {
                            flurryConfigListener.onFetchSuccess();
                        } else if (i != 3) {
                            if (i == 4) {
                                flurryConfigListener.onFetchError(C1644bz.this.f770m);
                            }
                        } else {
                            flurryConfigListener.onFetchNoChange();
                        }
                    }
                };
                if (handler == null) {
                    bzVar.f766h.post(r4);
                } else {
                    handler.post(r4);
                }
            }
        }
    }
}
