package p000a.p001a.p003b.p005b;

import p000a.p001a.p007d.C0047a;
import p000a.p001a.p007d.C0050d;

/* renamed from: a.a.b.b.g */
/* compiled from: StartAppSDK */
public class C0031g extends C0024a implements C0030f, C0050d {
    private final int arity;

    public C0031g(int i, Object obj) {
        super(obj);
        this.arity = i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public C0047a mo50d() {
        return C0039n.m50a(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
        if (p000a.p001a.p003b.p005b.C0032h.m43a(mo51e(), r5.mo51e()) != false) goto L_0x0052;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof p000a.p001a.p003b.p005b.C0031g
            r2 = 0
            if (r1 == 0) goto L_0x0053
            a.a.b.b.g r5 = (p000a.p001a.p003b.p005b.C0031g) r5
            a.a.d.c r1 = r4.mo47a()
            if (r1 != 0) goto L_0x0018
            a.a.d.c r1 = r5.mo47a()
            if (r1 != 0) goto L_0x0051
            goto L_0x0026
        L_0x0018:
            a.a.d.c r1 = r4.mo47a()
            a.a.d.c r3 = r5.mo47a()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0051
        L_0x0026:
            java.lang.String r1 = r4.mo48b()
            java.lang.String r3 = r5.mo48b()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0051
            java.lang.String r1 = r4.mo49c()
            java.lang.String r3 = r5.mo49c()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0051
            java.lang.Object r1 = r4.mo51e()
            java.lang.Object r5 = r5.mo51e()
            boolean r5 = p000a.p001a.p003b.p005b.C0032h.m43a(r1, r5)
            if (r5 == 0) goto L_0x0051
            goto L_0x0052
        L_0x0051:
            r0 = 0
        L_0x0052:
            return r0
        L_0x0053:
            boolean r0 = r5 instanceof p000a.p001a.p007d.C0050d
            if (r0 == 0) goto L_0x0060
            a.a.d.a r0 = r4.mo52f()
            boolean r5 = r5.equals(r0)
            return r5
        L_0x0060:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p000a.p001a.p003b.p005b.C0031g.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (((mo47a() == null ? 0 : mo47a().hashCode() * 31) + mo48b().hashCode()) * 31) + mo49c().hashCode();
    }

    public String toString() {
        String str;
        C0047a f = mo52f();
        if (f != this) {
            return f.toString();
        }
        if ("<init>".equals(mo48b())) {
            str = "constructor (Kotlin reflection is not available)";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("function ");
            sb.append(mo48b());
            sb.append(" (Kotlin reflection is not available)");
            str = sb.toString();
        }
        return str;
    }
}
