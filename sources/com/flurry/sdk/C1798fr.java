package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import com.flurry.sdk.C1788fm.C1789a;
import java.util.LinkedList;
import java.util.Queue;

/* renamed from: com.flurry.sdk.fr */
public abstract class C1798fr extends C1766f implements C1788fm {

    /* renamed from: a */
    private C1788fm f1093a;

    /* renamed from: d */
    volatile int f1094d = C1803b.f1101a;

    /* renamed from: e */
    protected Queue<C1930jp> f1095e;

    /* renamed from: f */
    protected C1790fn f1096f;

    /* renamed from: com.flurry.sdk.fr$1 */
    static /* synthetic */ class C17991 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1097a = new int[C1803b.m1009a().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0011 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0019 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0021 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0029 */
        static {
            /*
                int[] r0 = com.flurry.sdk.C1798fr.C1803b.m1009a()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1097a = r0
                r0 = 1
                int[] r1 = f1097a     // Catch:{ NoSuchFieldError -> 0x0011 }
                int r2 = com.flurry.sdk.C1798fr.C1803b.f1101a     // Catch:{ NoSuchFieldError -> 0x0011 }
                int r2 = r2 - r0
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0011 }
            L_0x0011:
                int[] r1 = f1097a     // Catch:{ NoSuchFieldError -> 0x0019 }
                int r2 = com.flurry.sdk.C1798fr.C1803b.f1105e     // Catch:{ NoSuchFieldError -> 0x0019 }
                int r2 = r2 - r0
                r3 = 2
                r1[r2] = r3     // Catch:{ NoSuchFieldError -> 0x0019 }
            L_0x0019:
                int[] r1 = f1097a     // Catch:{ NoSuchFieldError -> 0x0021 }
                int r2 = com.flurry.sdk.C1798fr.C1803b.f1102b     // Catch:{ NoSuchFieldError -> 0x0021 }
                int r2 = r2 - r0
                r3 = 3
                r1[r2] = r3     // Catch:{ NoSuchFieldError -> 0x0021 }
            L_0x0021:
                int[] r1 = f1097a     // Catch:{ NoSuchFieldError -> 0x0029 }
                int r2 = com.flurry.sdk.C1798fr.C1803b.f1103c     // Catch:{ NoSuchFieldError -> 0x0029 }
                int r2 = r2 - r0
                r3 = 4
                r1[r2] = r3     // Catch:{ NoSuchFieldError -> 0x0029 }
            L_0x0029:
                int[] r1 = f1097a     // Catch:{ NoSuchFieldError -> 0x0031 }
                int r2 = com.flurry.sdk.C1798fr.C1803b.f1104d     // Catch:{ NoSuchFieldError -> 0x0031 }
                int r2 = r2 - r0
                r0 = 5
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0031 }
            L_0x0031:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1798fr.C17991.<clinit>():void");
        }
    }

    /* renamed from: com.flurry.sdk.fr$a */
    class C1800a implements C1790fn {
        private C1800a() {
        }

        /* synthetic */ C1800a(C1798fr frVar, byte b) {
            this();
        }

        /* renamed from: a */
        public final void mo16477a() {
            C1798fr.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() {
                    C1798fr.this.mo16486d();
                    C1798fr.this.f1094d = C1803b.f1104d;
                    C1798fr.this.runAsync(new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                            if (C1798fr.this.f1096f != null) {
                                C1798fr.this.f1096f.mo16477a();
                            }
                        }
                    });
                }
            });
        }
    }

    /* renamed from: com.flurry.sdk.fr$b */
    public enum C1803b {
        ;
        

        /* renamed from: a */
        public static final int f1101a = 1;

        /* renamed from: b */
        public static final int f1102b = 2;

        /* renamed from: c */
        public static final int f1103c = 3;

        /* renamed from: d */
        public static final int f1104d = 4;

        /* renamed from: e */
        public static final int f1105e = 5;

        static {
            f1106f = new int[]{f1101a, f1102b, f1103c, f1104d, f1105e};
        }

        /* renamed from: a */
        public static int[] m1009a() {
            return (int[]) f1106f.clone();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo16243a() {
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo16471a(C1930jp jpVar);

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void mo16482c() {
    }

    C1798fr(String str, C1788fm fmVar) {
        super(str, C1756ex.m904a(C1758a.CORE));
        this.f1093a = fmVar;
        this.f1095e = new LinkedList();
        this.f1094d = C1803b.f1102b;
    }

    /* renamed from: a */
    public final void mo16474a(C1790fn fnVar) {
        this.f1094d = C1803b.f1103c;
        this.f1096f = fnVar;
        mo16243a();
        C1788fm fmVar = this.f1093a;
        if (fmVar != null) {
            fmVar.mo16474a(new C1800a(this, 0));
            return;
        }
        if (fnVar != null) {
            fnVar.mo16477a();
        }
        this.f1094d = C1803b.f1104d;
    }

    /* renamed from: c */
    public final C1789a mo16476c(C1930jp jpVar) {
        C1789a aVar = C1789a.ERROR;
        int i = C17991.f1097a[this.f1094d - 1];
        if (i == 1 || i == 2) {
            return aVar;
        }
        if (i == 3 || i == 4) {
            C1789a aVar2 = C1789a.DEFERRED;
            this.f1095e.add(jpVar);
            StringBuilder sb = new StringBuilder("Adding frame to deferred queue:");
            sb.append(jpVar.mo16518e());
            C1685cy.m754a(4, "StreamingCoreModule", sb.toString());
            return aVar2;
        } else if (i != 5) {
            return aVar;
        } else {
            C1789a aVar3 = C1789a.QUEUED;
            mo16471a(jpVar);
            return aVar3;
        }
    }

    /* renamed from: d */
    public final void mo16487d(C1930jp jpVar) {
        C1788fm fmVar = this.f1093a;
        if (fmVar != null) {
            C1789a c = fmVar.mo16476c(jpVar);
            StringBuilder sb = new StringBuilder("Enqueue message status for module: ");
            sb.append(this.f1093a);
            sb.append(" is: ");
            sb.append(c);
            C1685cy.m754a(4, "StreamingCoreModule", sb.toString());
        }
    }

    /* renamed from: b */
    public C1789a mo16472b(C1930jp jpVar) {
        C1789a aVar = C1789a.ERROR;
        C1788fm fmVar = this.f1093a;
        return fmVar != null ? fmVar.mo16472b(jpVar) : aVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public final void mo16486d() {
        while (this.f1095e.peek() != null) {
            C1930jp jpVar = (C1930jp) this.f1095e.poll();
            StringBuilder sb = new StringBuilder("Processing deferred message status for module: ");
            sb.append(jpVar.mo16518e());
            C1685cy.m754a(4, "StreamingCoreModule", sb.toString());
            mo16471a(jpVar);
        }
    }

    /* renamed from: b */
    public final void mo16475b() {
        mo16482c();
        C1788fm fmVar = this.f1093a;
        if (fmVar != null) {
            fmVar.mo16475b();
        }
    }
}
