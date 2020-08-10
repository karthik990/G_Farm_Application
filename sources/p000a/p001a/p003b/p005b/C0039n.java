package p000a.p001a.p003b.p005b;

import p000a.p001a.p007d.C0048b;
import p000a.p001a.p007d.C0050d;
import p000a.p001a.p007d.C0052f;

/* renamed from: a.a.b.b.n */
/* compiled from: StartAppSDK */
public class C0039n {

    /* renamed from: a */
    private static final C0040o f7a;

    /* renamed from: b */
    private static final C0048b[] f8b = new C0048b[0];

    static {
        C0040o oVar = null;
        try {
            oVar = (C0040o) Class.forName("truenet.kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (oVar == null) {
            oVar = new C0040o();
        }
        f7a = oVar;
    }

    /* renamed from: a */
    public static C0048b m49a(Class cls) {
        return f7a.mo65a(cls);
    }

    /* renamed from: a */
    public static String m52a(C0033i iVar) {
        return f7a.mo68a(iVar);
    }

    /* renamed from: a */
    public static C0050d m50a(C0031g gVar) {
        return f7a.mo66a(gVar);
    }

    /* renamed from: a */
    public static C0052f m51a(C0035k kVar) {
        return f7a.mo67a(kVar);
    }
}
