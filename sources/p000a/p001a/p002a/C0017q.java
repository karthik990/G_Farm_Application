package p000a.p001a.p002a;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: a.a.a.q */
/* compiled from: StartAppSDK */
class C0017q extends C0016p {
    /* renamed from: c */
    public static final <T> T m13c(List<? extends T> list) {
        C0032h.m44b(list, "$receiver");
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    /* renamed from: d */
    public static final <T> T m14d(List<? extends T> list) {
        C0032h.m44b(list, "$receiver");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /* renamed from: e */
    public static final <T> T m15e(List<? extends T> list) {
        C0032h.m44b(list, "$receiver");
        if (!list.isEmpty()) {
            return list.get(C0007g.m4a(list));
        }
        throw new NoSuchElementException("List is empty.");
    }

    /* renamed from: a */
    public static final <T, C extends Collection<? super T>> C m9a(Iterable<? extends T> iterable, C c) {
        C0032h.m44b(iterable, "$receiver");
        C0032h.m44b(c, Param.DESTINATION);
        for (Object add : iterable) {
            c.add(add);
        }
        return c;
    }

    /* renamed from: a */
    public static final <T> List<T> m10a(Iterable<? extends T> iterable) {
        List<T> list;
        C0032h.m44b(iterable, "$receiver");
        if (!(iterable instanceof Collection)) {
            return C0007g.m7b(C0007g.m12b(iterable));
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            list = C0007g.m5a();
        } else if (size != 1) {
            list = C0007g.m11a(collection);
        } else {
            list = C0007g.m3a(iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
        }
        return list;
    }

    /* renamed from: b */
    public static final <T> List<T> m12b(Iterable<? extends T> iterable) {
        C0032h.m44b(iterable, "$receiver");
        if (iterable instanceof Collection) {
            return C0007g.m11a((Collection) iterable);
        }
        return (List) C0007g.m9a(iterable, new ArrayList());
    }

    /* renamed from: a */
    public static final <T> List<T> m11a(Collection<? extends T> collection) {
        C0032h.m44b(collection, "$receiver");
        return new ArrayList<>(collection);
    }
}
