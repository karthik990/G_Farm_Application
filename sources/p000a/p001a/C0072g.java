package p000a.p001a;

import java.io.Serializable;
import p000a.p001a.p003b.p004a.C0022a;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.g */
/* compiled from: StartAppSDK */
final class C0072g<T> implements C0041c<T>, Serializable {
    private volatile Object _value;
    private C0022a<? extends T> initializer;
    private final Object lock;

    public C0072g(C0022a<? extends T> aVar, Object obj) {
        C0032h.m44b(aVar, "initializer");
        this.initializer = aVar;
        this._value = C0074i.f18a;
        if (obj == 0) {
            obj = this;
        }
        this.lock = obj;
    }

    public /* synthetic */ C0072g(C0022a aVar, Object obj, int i, C0029e eVar) {
        if ((i & 2) != 0) {
            obj = null;
        }
        this(aVar, obj);
    }

    /* renamed from: a */
    public T mo1a() {
        T t;
        T t2 = this._value;
        if (t2 != C0074i.f18a) {
            return t2;
        }
        synchronized (this.lock) {
            t = this._value;
            if (t == C0074i.f18a) {
                C0022a<? extends T> aVar = this.initializer;
                if (aVar == null) {
                    C0032h.m40a();
                }
                t = aVar.mo45a();
                this._value = t;
                this.initializer = null;
            }
        }
        return t;
    }

    /* renamed from: b */
    public boolean mo71b() {
        return this._value != C0074i.f18a;
    }

    public String toString() {
        return mo71b() ? String.valueOf(mo1a()) : "Lazy value not initialized yet.";
    }

    private final Object writeReplace() {
        return new C0000a(mo1a());
    }
}
