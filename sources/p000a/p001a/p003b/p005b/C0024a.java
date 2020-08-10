package p000a.p001a.p003b.p005b;

import java.io.Serializable;
import p000a.p001a.p007d.C0047a;
import p000a.p001a.p007d.C0049c;

/* renamed from: a.a.b.b.a */
/* compiled from: StartAppSDK */
public abstract class C0024a implements C0047a, Serializable {

    /* renamed from: a */
    public static final Object f2a = C0025a.f4a;

    /* renamed from: b */
    private transient C0047a f3b;
    protected final Object receiver;

    /* renamed from: a.a.b.b.a$a */
    /* compiled from: StartAppSDK */
    private static class C0025a implements Serializable {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C0025a f4a = new C0025a();

        private C0025a() {
        }

        private Object readResolve() {
            return f4a;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public abstract C0047a mo50d();

    public C0024a() {
        this(f2a);
    }

    protected C0024a(Object obj) {
        this.receiver = obj;
    }

    /* renamed from: e */
    public Object mo51e() {
        return this.receiver;
    }

    /* renamed from: f */
    public C0047a mo52f() {
        C0047a aVar = this.f3b;
        if (aVar != null) {
            return aVar;
        }
        C0047a d = mo50d();
        this.f3b = d;
        return d;
    }

    /* renamed from: a */
    public C0049c mo47a() {
        throw new AbstractMethodError();
    }

    /* renamed from: b */
    public String mo48b() {
        throw new AbstractMethodError();
    }

    /* renamed from: c */
    public String mo49c() {
        throw new AbstractMethodError();
    }
}
