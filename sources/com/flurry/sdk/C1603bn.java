package com.flurry.sdk;

import com.flurry.sdk.C1622bq.C1623a;
import com.flurry.sdk.C1756ex.C1758a;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.flurry.sdk.bn */
public abstract class C1603bn extends C1766f {

    /* renamed from: a */
    protected final String f672a;

    /* renamed from: b */
    protected String f673b;

    /* renamed from: d */
    protected C1602bm f674d;

    /* renamed from: e */
    Set<String> f675e = new HashSet();

    /* renamed from: f */
    C1616bp f676f;

    /* renamed from: g */
    private C1563av f677g = C1948n.m1229a().f1416b;

    /* renamed from: h */
    private C1949o<C1561au> f678h = new C1949o<C1561au>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1561au auVar = (C1561au) obj;
            String str = C1603bn.this.f672a;
            StringBuilder sb = new StringBuilder("NetworkAvailabilityChanged : NetworkAvailable = ");
            sb.append(auVar.f560a);
            C1685cy.m766c(str, sb.toString());
            if (auVar.f560a) {
                C1603bn.this.mo16296b();
            }
        }
    };

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16293a(int i, String str, String str2);

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public abstract String mo16299d();

    public C1603bn(String str, String str2) {
        super(str2, C1756ex.m904a(C1758a.REPORTS));
        this.f672a = str2;
        this.f673b = "AnalyticsData_";
        this.f677g.subscribe(this.f678h);
        this.f676f = new C1616bp(str);
    }

    /* renamed from: a */
    public final void mo16243a() {
        C1616bp bpVar = this.f676f;
        String str = bpVar.f704b;
        bpVar.f705c = new LinkedHashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();
        File fileStreamPath = C1576b.m502a().getFileStreamPath(".FlurrySenderIndex.info.".concat(String.valueOf(str)));
        StringBuilder sb = new StringBuilder("isOldIndexFilePresent: for ");
        sb.append(str);
        sb.append(fileStreamPath.exists());
        String str2 = "FlurryDataSenderIndex";
        C1685cy.m754a(5, str2, sb.toString());
        if (fileStreamPath.exists()) {
            List a = bpVar.mo16303a(str);
            if (a != null && a.size() > 0) {
                arrayList.addAll(a);
                for (String b : arrayList) {
                    bpVar.mo16306b(b);
                }
            }
            C1616bp.m570c(str);
        } else {
            List<C1622bq> list = (List) new C1941l(C1576b.m502a().getFileStreamPath(C1616bp.m571d(bpVar.f704b)), str, 1, new C1729dv<List<C1622bq>>() {
                /* renamed from: a */
                public final C1724ds<List<C1622bq>> mo16258a(int i) {
                    return new C1721dr(new C1623a());
                }
            }).mo16527a();
            if (list == null) {
                C1685cy.m766c(str2, "New main file also not found. returning..");
                mo16296b();
            }
            for (C1622bq bqVar : list) {
                arrayList.add(bqVar.f711a);
            }
        }
        for (String str3 : arrayList) {
            List e = bpVar.mo16307e(str3);
            if (e != null && !e.isEmpty()) {
                bpVar.f705c.put(str3, e);
            }
        }
        mo16296b();
    }

    /* renamed from: a */
    public final void mo16294a(C1602bm bmVar) {
        this.f674d = bmVar;
    }

    /* renamed from: a */
    public final void mo16295a(final byte[] bArr, final String str, final String str2) {
        if (bArr == null || bArr.length == 0) {
            C1685cy.m754a(6, this.f672a, "Report that has to be sent is EMPTY or NULL");
            return;
        }
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1603bn.this.mo16297b(bArr, str, str2);
            }
        });
        mo16296b();
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16296b() {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1603bn.this.mo16298c();
            }
        });
    }

    /* JADX WARNING: type inference failed for: r6v8, types: [byte[], RequestObjectType] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v8, types: [byte[], RequestObjectType]
      assigns: [byte[]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[], RequestObjectType]
      mth insns count: 107
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo16298c() {
        /*
            r11 = this;
            boolean r0 = com.flurry.sdk.C1652c.m655a()
            r1 = 5
            if (r0 != 0) goto L_0x000f
            java.lang.String r0 = r11.f672a
            java.lang.String r2 = "Reports were not sent! No Internet connection!"
            com.flurry.sdk.C1685cy.m754a(r1, r0, r2)
            return
        L_0x000f:
            com.flurry.sdk.bp r0 = r11.f676f
            java.util.ArrayList r2 = new java.util.ArrayList
            java.util.LinkedHashMap<java.lang.String, java.util.List<java.lang.String>> r0 = r0.f705c
            java.util.Set r0 = r0.keySet()
            r2.<init>(r0)
            boolean r0 = r2.isEmpty()
            r3 = 4
            if (r0 == 0) goto L_0x002b
            java.lang.String r0 = r11.f672a
            java.lang.String r1 = "No more reports to send."
            com.flurry.sdk.C1685cy.m754a(r3, r0, r1)
            return
        L_0x002b:
            java.util.Iterator r0 = r2.iterator()
        L_0x002f:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0135
            java.lang.Object r2 = r0.next()
            java.lang.String r2 = (java.lang.String) r2
            boolean r4 = r11.m546e()
            if (r4 == 0) goto L_0x0135
            com.flurry.sdk.bp r4 = r11.f676f
            java.util.List r4 = r4.mo16308f(r2)
            java.lang.String r5 = r11.f672a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Number of not sent blocks = "
            r6.<init>(r7)
            int r7 = r4.size()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.flurry.sdk.C1685cy.m754a(r3, r5, r6)
            java.util.Iterator r4 = r4.iterator()
        L_0x0062:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x002f
            java.lang.Object r5 = r4.next()
            java.lang.String r5 = (java.lang.String) r5
            java.util.Set<java.lang.String> r6 = r11.f675e
            boolean r6 = r6.contains(r5)
            if (r6 != 0) goto L_0x0062
            boolean r6 = r11.m546e()
            if (r6 == 0) goto L_0x002f
            com.flurry.sdk.l r6 = com.flurry.sdk.C1611bo.m564b(r5)
            java.lang.Object r6 = r6.mo16527a()
            com.flurry.sdk.bo r6 = (com.flurry.sdk.C1611bo) r6
            r7 = 6
            if (r6 != 0) goto L_0x0096
            java.lang.String r6 = r11.f672a
            java.lang.String r8 = "Internal ERROR! Cannot read!"
            com.flurry.sdk.C1685cy.m754a(r7, r6, r8)
            com.flurry.sdk.bp r6 = r11.f676f
            r6.mo16305a(r5, r2)
            goto L_0x0062
        L_0x0096:
            byte[] r6 = r6.f699b
            if (r6 == 0) goto L_0x0127
            int r8 = r6.length
            if (r8 != 0) goto L_0x009f
            goto L_0x0127
        L_0x009f:
            java.lang.String r7 = r11.f672a
            java.lang.String r8 = java.lang.String.valueOf(r5)
            java.lang.String r9 = "Reading block info "
            java.lang.String r8 = r9.concat(r8)
            com.flurry.sdk.C1685cy.m754a(r1, r7, r8)
            java.util.Set<java.lang.String> r7 = r11.f675e
            r7.add(r5)
            java.lang.String r7 = r11.mo16299d()
            java.lang.String r8 = r11.f672a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "FlurryDataSender: start upload data with id = "
            r9.<init>(r10)
            r9.append(r5)
            java.lang.String r10 = " to "
            r9.append(r10)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
            com.flurry.sdk.C1685cy.m754a(r3, r8, r9)
            com.flurry.sdk.dd r8 = new com.flurry.sdk.dd
            r8.<init>()
            r8.f897f = r7
            r9 = 100000(0x186a0, float:1.4013E-40)
            r8.f997p = r9
            com.flurry.sdk.df$a r9 = com.flurry.sdk.C1696df.C1699a.kPost
            r8.f898g = r9
            java.lang.String r9 = "Content-Type"
            java.lang.String r10 = "application/octet-stream"
            r8.mo16403a(r9, r10)
            com.flurry.sdk.bi r9 = com.flurry.sdk.C1598bi.m531a()
            java.lang.String r9 = r9.mo16288b()
            java.lang.String r10 = "X-Flurry-Api-Key"
            r8.mo16403a(r10, r9)
            com.flurry.sdk.do r9 = new com.flurry.sdk.do
            r9.<init>()
            r8.f884c = r9
            com.flurry.sdk.dt r9 = new com.flurry.sdk.dt
            r9.<init>()
            r8.f885d = r9
            r8.f883b = r6
            com.flurry.sdk.n r6 = com.flurry.sdk.C1948n.m1229a()
            com.flurry.sdk.ac r6 = r6.f1422h
            if (r6 == 0) goto L_0x0114
            boolean r6 = r6.f447e
            if (r6 == 0) goto L_0x0114
            r6 = 1
            goto L_0x0115
        L_0x0114:
            r6 = 0
        L_0x0115:
            r8.f905n = r6
            com.flurry.sdk.bn$4 r6 = new com.flurry.sdk.bn$4
            r6.<init>(r5, r7, r2)
            r8.f882a = r6
            com.flurry.sdk.ct r5 = com.flurry.sdk.C1675ct.m738a()
            r5.mo16389a(r11, r8)
            goto L_0x0062
        L_0x0127:
            java.lang.String r6 = r11.f672a
            java.lang.String r8 = "Internal ERROR! Report is empty!"
            com.flurry.sdk.C1685cy.m754a(r7, r6, r8)
            com.flurry.sdk.bp r6 = r11.f676f
            r6.mo16305a(r5, r2)
            goto L_0x0062
        L_0x0135:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1603bn.mo16298c():void");
    }

    /* renamed from: e */
    private boolean m546e() {
        return m547f() <= 5;
    }

    /* renamed from: f */
    private int m547f() {
        return this.f675e.size();
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16297b(byte[] bArr, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f673b);
        sb.append(str);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(str2);
        String sb2 = sb.toString();
        C1611bo boVar = new C1611bo(bArr);
        String str3 = boVar.f698a;
        C1611bo.m564b(str3).mo16528a(boVar);
        String str4 = this.f672a;
        StringBuilder sb3 = new StringBuilder("Saving Block File ");
        sb3.append(str3);
        sb3.append(" at ");
        sb3.append(C1576b.m502a().getFileStreamPath(C1611bo.m563a(str3)));
        C1685cy.m754a(5, str4, sb3.toString());
        this.f676f.mo16304a(boVar, sb2);
    }

    /* renamed from: a */
    static /* synthetic */ String m544a(String str) {
        if (str != null) {
            String str2 = "<title>";
            if (str.contains(str2)) {
                String str3 = "</title>";
                if (str.contains(str3)) {
                    return str.substring(str.indexOf(str2) + 7, str.indexOf(str3));
                }
            }
        }
        StringBuilder sb = new StringBuilder("Can not parse http error message: ");
        if (str == null) {
            str = "NULL";
        }
        sb.append(str);
        return sb.toString();
    }
}
