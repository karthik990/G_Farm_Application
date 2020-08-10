package p000a.p001a.p003b.p005b;

import p000a.p001a.p007d.C0048b;
import p000a.p001a.p007d.C0050d;
import p000a.p001a.p007d.C0052f;

/* renamed from: a.a.b.b.o */
/* compiled from: StartAppSDK */
public class C0040o {
    /* renamed from: a */
    public C0050d mo66a(C0031g gVar) {
        return gVar;
    }

    /* renamed from: a */
    public C0052f mo67a(C0035k kVar) {
        return kVar;
    }

    /* renamed from: a */
    public C0048b mo65a(Class cls) {
        return new C0027c(cls);
    }

    /* renamed from: a */
    public String mo68a(C0033i iVar) {
        String obj = iVar.getClass().getGenericInterfaces()[0].toString();
        return obj.startsWith("truenet.kotlin.jvm.functions.") ? obj.substring(29) : obj;
    }
}
