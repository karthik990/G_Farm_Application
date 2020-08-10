package p000a.p001a.p003b.p005b;

import p000a.p001a.p003b.C0021a;
import p000a.p001a.p007d.C0048b;

/* renamed from: a.a.b.b.c */
/* compiled from: StartAppSDK */
public final class C0027c implements C0026b, C0048b<Object> {

    /* renamed from: a */
    private final Class<?> f5a;

    public C0027c(Class<?> cls) {
        C0032h.m44b(cls, "jClass");
        this.f5a = cls;
    }

    /* renamed from: a */
    public Class<?> mo53a() {
        return this.f5a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof C0027c) && C0032h.m43a((Object) C0021a.m23a(this), (Object) C0021a.m23a((C0048b) obj));
    }

    public int hashCode() {
        return C0021a.m23a(this).hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mo53a().toString());
        sb.append(" (Kotlin reflection is not available)");
        return sb.toString();
    }
}
