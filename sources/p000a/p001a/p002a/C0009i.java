package p000a.p001a.p002a;

import java.util.List;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.a.i */
/* compiled from: StartAppSDK */
class C0009i extends C0008h {
    /* renamed from: a */
    public static final <T> List<T> m5a() {
        return C0019s.f1a;
    }

    /* renamed from: a */
    public static final <T> List<T> m6a(T... tArr) {
        C0032h.m44b(tArr, "elements");
        return tArr.length > 0 ? C0001a.m1a(tArr) : C0007g.m5a();
    }

    /* renamed from: a */
    public static final <T> int m4a(List<? extends T> list) {
        C0032h.m44b(list, "$receiver");
        return list.size() - 1;
    }

    /* renamed from: b */
    public static final <T> List<T> m7b(List<? extends T> list) {
        C0032h.m44b(list, "$receiver");
        int size = list.size();
        if (size == 0) {
            return C0007g.m5a();
        }
        if (size != 1) {
            return list;
        }
        return C0007g.m3a(list.get(0));
    }
}
