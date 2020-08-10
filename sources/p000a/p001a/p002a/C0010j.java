package p000a.p001a.p002a;

import java.util.Collection;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.a.j */
/* compiled from: StartAppSDK */
class C0010j extends C0009i {
    /* renamed from: a */
    public static final <T> int m8a(Iterable<? extends T> iterable, int i) {
        C0032h.m44b(iterable, "$receiver");
        return iterable instanceof Collection ? ((Collection) iterable).size() : i;
    }
}
