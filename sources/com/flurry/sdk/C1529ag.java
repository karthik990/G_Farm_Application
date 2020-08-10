package com.flurry.sdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.flurry.sdk.ag */
public final class C1529ag extends C1942m<C1543ak> {

    /* renamed from: a */
    protected C1543ak f478a = null;

    /* renamed from: b */
    protected C1951q f479b;

    /* renamed from: d */
    protected C1540ai f480d;

    /* renamed from: e */
    protected C1527af f481e;

    /* renamed from: f */
    protected C1526ae f482f;

    /* renamed from: g */
    protected AtomicBoolean f483g = new AtomicBoolean(false);

    /* renamed from: h */
    protected C1949o<C1955r> f484h = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1955r rVar = (C1955r) obj;
            C1529ag.this.mo16246b();
            C1529ag.m420a(rVar);
        }
    };

    public C1529ag(C1951q qVar) {
        super("IdProvider");
        this.f479b = qVar;
        this.f480d = new C1540ai();
        this.f481e = new C1527af();
        this.f482f = new C1526ae();
        this.f478a = new C1543ak();
        this.f479b.subscribe(this.f484h);
    }

    public final void subscribe(C1949o<C1543ak> oVar) {
        super.subscribe(oVar);
    }

    public final void start() {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1529ag.this.f480d.mo16254a();
            }
        });
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1588be.m517a();
                C1588be.m519a("IdProvider: Provider start", Collections.emptyMap());
                C1529ag.this.mo16246b();
            }
        });
    }

    /* renamed from: a */
    public final C1543ak mo16243a() {
        return this.f478a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16246b() {
        runAsync(new C1738eb() {
            /* JADX WARNING: Removed duplicated region for block: B:33:0x012a A[Catch:{ Exception -> 0x0134 }] */
            /* JADX WARNING: Removed duplicated region for block: B:39:0x015d A[Catch:{ Exception -> 0x01c3 }] */
            /* JADX WARNING: Removed duplicated region for block: B:56:0x01b5 A[Catch:{ Exception -> 0x01c3 }] */
            /* JADX WARNING: Removed duplicated region for block: B:61:0x01f2  */
            /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
            /* renamed from: a */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void mo16236a() {
                /*
                    r13 = this;
                    java.lang.String r0 = "IdProvider"
                    com.flurry.sdk.ag r1 = com.flurry.sdk.C1529ag.this
                    java.util.concurrent.atomic.AtomicBoolean r1 = r1.f483g
                    r2 = 0
                    r1.set(r2)
                    com.flurry.sdk.ag r1 = com.flurry.sdk.C1529ag.this
                    com.flurry.sdk.ai r1 = r1.f480d
                    r1.mo16254a()
                    r1 = 6
                    r3 = 1
                    r4 = 0
                    com.flurry.sdk.ag r5 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ae r5 = r5.f482f     // Catch:{ Exception -> 0x007e }
                    java.lang.String r6 = "advertising_id"
                    java.lang.String r6 = com.flurry.sdk.C1775fe.m940b(r6, r4)     // Catch:{ Exception -> 0x007e }
                    java.lang.String r7 = "ad_tracking_enabled"
                    android.content.Context r8 = com.flurry.sdk.C1576b.m502a()     // Catch:{ Exception -> 0x007e }
                    java.lang.String r9 = "FLURRY_SHARED_PREFERENCES"
                    android.content.SharedPreferences r8 = r8.getSharedPreferences(r9, r2)     // Catch:{ Exception -> 0x007e }
                    java.util.Locale r9 = java.util.Locale.US     // Catch:{ Exception -> 0x007e }
                    java.lang.String r10 = "com.flurry.sdk.%s"
                    java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x007e }
                    r11[r2] = r7     // Catch:{ Exception -> 0x007e }
                    java.lang.String r7 = java.lang.String.format(r9, r10, r11)     // Catch:{ Exception -> 0x007e }
                    boolean r2 = r8.getBoolean(r7, r2)     // Catch:{ Exception -> 0x007e }
                    r2 = r2 ^ r3
                    if (r6 == 0) goto L_0x0042
                    r5.f472a = r6     // Catch:{ Exception -> 0x007e }
                    r5.f473b = r2     // Catch:{ Exception -> 0x007e }
                    goto L_0x0045
                L_0x0042:
                    r5.mo16243a()     // Catch:{ Exception -> 0x007e }
                L_0x0045:
                    com.flurry.sdk.ag r2 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ae r2 = r2.f482f     // Catch:{ Exception -> 0x007e }
                    java.lang.String r2 = r2.f472a     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ag r5 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ae r5 = r5.f482f     // Catch:{ Exception -> 0x007e }
                    boolean r5 = r5.f473b     // Catch:{ Exception -> 0x007e }
                    boolean r6 = r2.isEmpty()     // Catch:{ Exception -> 0x007e }
                    if (r6 != 0) goto L_0x0066
                    com.flurry.sdk.ag r6 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ak r6 = r6.f478a     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.al r7 = com.flurry.sdk.C1544al.AndroidAdvertisingId     // Catch:{ Exception -> 0x007e }
                    r6.mo16261a(r7, r2)     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ag r6 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ak r6 = r6.f478a     // Catch:{ Exception -> 0x007e }
                    r6.f508b = r5     // Catch:{ Exception -> 0x007e }
                L_0x0066:
                    com.flurry.sdk.ag r6 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ae r6 = r6.f482f     // Catch:{ Exception -> 0x007e }
                    java.util.concurrent.atomic.AtomicBoolean r6 = r6.f474d     // Catch:{ Exception -> 0x007e }
                    boolean r6 = r6.get()     // Catch:{ Exception -> 0x007e }
                    r6 = r6 ^ r3
                    if (r6 == 0) goto L_0x009e
                    com.flurry.sdk.ag r6 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x007e }
                    com.flurry.sdk.ag$4$1 r7 = new com.flurry.sdk.ag$4$1     // Catch:{ Exception -> 0x007e }
                    r7.<init>(r5, r2)     // Catch:{ Exception -> 0x007e }
                    r6.runAsync(r7)     // Catch:{ Exception -> 0x007e }
                    goto L_0x009e
                L_0x007e:
                    r2 = move-exception
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    java.lang.String r6 = "Error Fetching Ad Id - "
                    r5.<init>(r6)
                    java.lang.String r6 = r2.getLocalizedMessage()
                    r5.append(r6)
                    java.lang.String r5 = r5.toString()
                    com.flurry.sdk.C1685cy.m754a(r1, r0, r5)
                    com.flurry.sdk.C1588be.m517a()
                    java.lang.String r5 = "Error fetching Ad Id"
                    java.lang.String r6 = "Exception happened during fetching Ad Id"
                    com.flurry.sdk.C1588be.m518a(r5, r6, r2)
                L_0x009e:
                    com.flurry.sdk.ag r2 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x0134 }
                    com.flurry.sdk.af r2 = r2.f481e     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r5 = r2.f475a     // Catch:{ Exception -> 0x0134 }
                    boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0134 }
                    if (r5 == 0) goto L_0x0126
                    android.content.Context r5 = com.flurry.sdk.C1576b.m502a()     // Catch:{ Exception -> 0x0134 }
                    android.content.ContentResolver r5 = r5.getContentResolver()     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r6 = "android_id"
                    java.lang.String r5 = android.provider.Settings.Secure.getString(r5, r6)     // Catch:{ Exception -> 0x0134 }
                    java.util.Set<java.lang.String> r6 = r2.f476b     // Catch:{ Exception -> 0x0134 }
                    java.util.Locale r7 = java.util.Locale.US     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r7 = r5.toLowerCase(r7)     // Catch:{ Exception -> 0x0134 }
                    boolean r6 = r6.contains(r7)     // Catch:{ Exception -> 0x0134 }
                    if (r6 == 0) goto L_0x00c8
                    r5 = r4
                    goto L_0x00d2
                L_0x00c8:
                    java.lang.String r6 = "AND"
                    java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r5 = r6.concat(r5)     // Catch:{ Exception -> 0x0134 }
                L_0x00d2:
                    boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0134 }
                    if (r6 != 0) goto L_0x00d9
                    goto L_0x0128
                L_0x00d9:
                    java.lang.String r5 = com.flurry.sdk.C1527af.m415a()     // Catch:{ Exception -> 0x0134 }
                    boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0134 }
                    if (r6 == 0) goto L_0x0124
                    java.lang.String r5 = r2.mo16244b()     // Catch:{ Exception -> 0x0134 }
                    boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0134 }
                    if (r6 == 0) goto L_0x0121
                    double r5 = java.lang.Math.random()     // Catch:{ Exception -> 0x0134 }
                    long r5 = java.lang.Double.doubleToLongBits(r5)     // Catch:{ Exception -> 0x0134 }
                    long r7 = java.lang.System.nanoTime()     // Catch:{ Exception -> 0x0134 }
                    android.content.Context r9 = com.flurry.sdk.C1576b.m502a()     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r9 = com.flurry.sdk.C1731dx.m857a(r9)     // Catch:{ Exception -> 0x0134 }
                    long r9 = com.flurry.sdk.C1734dz.m877e(r9)     // Catch:{ Exception -> 0x0134 }
                    r11 = 37
                    long r9 = r9 * r11
                    long r7 = r7 + r9
                    long r7 = r7 * r11
                    long r5 = r5 + r7
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r8 = "ID"
                    r7.<init>(r8)     // Catch:{ Exception -> 0x0134 }
                    r8 = 16
                    java.lang.String r5 = java.lang.Long.toString(r5, r8)     // Catch:{ Exception -> 0x0134 }
                    r7.append(r5)     // Catch:{ Exception -> 0x0134 }
                    java.lang.String r5 = r7.toString()     // Catch:{ Exception -> 0x0134 }
                L_0x0121:
                    com.flurry.sdk.C1527af.m416a(r5)     // Catch:{ Exception -> 0x0134 }
                L_0x0124:
                    r2.f475a = r5     // Catch:{ Exception -> 0x0134 }
                L_0x0126:
                    java.lang.String r5 = r2.f475a     // Catch:{ Exception -> 0x0134 }
                L_0x0128:
                    if (r5 == 0) goto L_0x0154
                    com.flurry.sdk.ag r2 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x0134 }
                    com.flurry.sdk.ak r2 = r2.f478a     // Catch:{ Exception -> 0x0134 }
                    com.flurry.sdk.al r6 = com.flurry.sdk.C1544al.DeviceId     // Catch:{ Exception -> 0x0134 }
                    r2.mo16261a(r6, r5)     // Catch:{ Exception -> 0x0134 }
                    goto L_0x0154
                L_0x0134:
                    r2 = move-exception
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    java.lang.String r6 = "Error Fetching Device Id - "
                    r5.<init>(r6)
                    java.lang.String r6 = r2.getLocalizedMessage()
                    r5.append(r6)
                    java.lang.String r5 = r5.toString()
                    com.flurry.sdk.C1685cy.m754a(r1, r0, r5)
                    com.flurry.sdk.C1588be.m517a()
                    java.lang.String r5 = "Error fetching Device Id"
                    java.lang.String r6 = "Exception happened during fetching Device Id"
                    com.flurry.sdk.C1588be.m518a(r5, r6, r2)
                L_0x0154:
                    com.flurry.sdk.ag r2 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x01c3 }
                    com.flurry.sdk.ai r2 = r2.f480d     // Catch:{ Exception -> 0x01c3 }
                    byte[] r5 = r2.f500a     // Catch:{ Exception -> 0x01c3 }
                    r6 = 2
                    if (r5 != 0) goto L_0x01b3
                    int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x01c3 }
                    r7 = 23
                    if (r5 >= r7) goto L_0x0166
                    com.flurry.sdk.k$a r5 = com.flurry.sdk.C1939k.C1940a.CRYPTO_ALGO_PADDING_5     // Catch:{ Exception -> 0x01c3 }
                    goto L_0x0168
                L_0x0166:
                    com.flurry.sdk.k$a r5 = com.flurry.sdk.C1939k.C1940a.CRYPTO_ALGO_PADDING_7     // Catch:{ Exception -> 0x01c3 }
                L_0x0168:
                    java.security.Key r7 = r2.mo16257b()     // Catch:{ Exception -> 0x01c3 }
                    byte[] r7 = r2.mo16256a(r7)     // Catch:{ Exception -> 0x01c3 }
                    if (r7 != 0) goto L_0x01b0
                    java.util.UUID r7 = java.util.UUID.randomUUID()     // Catch:{ Exception -> 0x01c3 }
                    java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x01c3 }
                    java.util.Locale r8 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x01c3 }
                    java.lang.String r7 = r7.toLowerCase(r8)     // Catch:{ Exception -> 0x01c3 }
                    boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x01c3 }
                    if (r8 == 0) goto L_0x0187
                    goto L_0x01ab
                L_0x0187:
                    java.lang.String r8 = "[^a-f0-9]+"
                    java.lang.String r9 = ""
                    java.lang.String r7 = r7.replaceAll(r8, r9)     // Catch:{ Exception -> 0x01c3 }
                    int r8 = r7.length()     // Catch:{ Exception -> 0x01c3 }
                    int r8 = r8 % r6
                    if (r8 == 0) goto L_0x01a7
                    r8 = 4
                    java.lang.String r9 = "InstallationIdProvider"
                    java.lang.String r10 = "Input string must contain an even number of characters "
                    java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ Exception -> 0x01c3 }
                    java.lang.String r7 = r10.concat(r7)     // Catch:{ Exception -> 0x01c3 }
                    com.flurry.sdk.C1685cy.m754a(r8, r9, r7)     // Catch:{ Exception -> 0x01c3 }
                    goto L_0x01ab
                L_0x01a7:
                    byte[] r4 = com.flurry.sdk.C1734dz.m875c(r7)     // Catch:{ Exception -> 0x01c3 }
                L_0x01ab:
                    r2.mo16255a(r4, r5)     // Catch:{ Exception -> 0x01c3 }
                    r5 = r4
                    goto L_0x01b1
                L_0x01b0:
                    r5 = r7
                L_0x01b1:
                    r2.f500a = r5     // Catch:{ Exception -> 0x01c3 }
                L_0x01b3:
                    if (r5 == 0) goto L_0x01e3
                    java.lang.String r2 = android.util.Base64.encodeToString(r5, r6)     // Catch:{ Exception -> 0x01c3 }
                    com.flurry.sdk.ag r4 = com.flurry.sdk.C1529ag.this     // Catch:{ Exception -> 0x01c3 }
                    com.flurry.sdk.ak r4 = r4.f478a     // Catch:{ Exception -> 0x01c3 }
                    com.flurry.sdk.al r5 = com.flurry.sdk.C1544al.AndroidInstallationId     // Catch:{ Exception -> 0x01c3 }
                    r4.mo16261a(r5, r2)     // Catch:{ Exception -> 0x01c3 }
                    goto L_0x01e3
                L_0x01c3:
                    r2 = move-exception
                    java.lang.StringBuilder r4 = new java.lang.StringBuilder
                    java.lang.String r5 = "Error Fetching Install Id - "
                    r4.<init>(r5)
                    java.lang.String r5 = r2.getLocalizedMessage()
                    r4.append(r5)
                    java.lang.String r4 = r4.toString()
                    com.flurry.sdk.C1685cy.m754a(r1, r0, r4)
                    com.flurry.sdk.C1588be.m517a()
                    java.lang.String r0 = "Error fetching Install Id"
                    java.lang.String r1 = "Exception happened during fetching Install Id"
                    com.flurry.sdk.C1588be.m518a(r0, r1, r2)
                L_0x01e3:
                    com.flurry.sdk.ag r0 = com.flurry.sdk.C1529ag.this
                    java.util.concurrent.atomic.AtomicBoolean r0 = r0.f483g
                    r0.set(r3)
                    com.flurry.sdk.ag r0 = com.flurry.sdk.C1529ag.this
                    boolean r0 = r0.mo16247c()
                    if (r0 == 0) goto L_0x01fd
                    com.flurry.sdk.ag r0 = com.flurry.sdk.C1529ag.this
                    com.flurry.sdk.ak r1 = r0.f478a
                    com.flurry.sdk.ak r1 = r1.mo16262b()
                    r0.notifyObservers(r1)
                L_0x01fd:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1529ag.C15334.mo16236a():void");
            }
        });
    }

    /* renamed from: c */
    public final boolean mo16247c() {
        boolean z = this.f483g.get();
        C1543ak akVar = this.f478a;
        return z && (akVar != null && akVar.mo16260a() != null && this.f478a.mo16260a().size() > 0);
    }

    public final void destroy() {
        super.destroy();
        this.f479b.unsubscribe(this.f484h);
    }

    /* renamed from: a */
    static /* synthetic */ void m420a(C1955r rVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("previous_state", rVar.f1444a.name());
        hashMap.put("current_state", rVar.f1445b.name());
        C1588be.m517a();
        C1588be.m519a("IdProvider: App State Change", hashMap);
    }
}
