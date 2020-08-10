package p000a.p001a.p003b.p005b;

import p000a.p001a.p007d.C0047a;
import p000a.p001a.p007d.C0051e;

/* renamed from: a.a.b.b.j */
/* compiled from: StartAppSDK */
public abstract class C0034j extends C0024a implements C0051e {
    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0034j) {
            C0034j jVar = (C0034j) obj;
            if (!mo47a().equals(jVar.mo47a()) || !mo48b().equals(jVar.mo48b()) || !mo49c().equals(jVar.mo49c()) || !C0032h.m43a(mo51e(), jVar.mo51e())) {
                z = false;
            }
            return z;
        } else if (obj instanceof C0051e) {
            return obj.equals(mo52f());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (((mo47a().hashCode() * 31) + mo48b().hashCode()) * 31) + mo49c().hashCode();
    }

    public String toString() {
        C0047a f = mo52f();
        if (f != this) {
            return f.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("property ");
        sb.append(mo48b());
        sb.append(" (Kotlin reflection is not available)");
        return sb.toString();
    }
}
