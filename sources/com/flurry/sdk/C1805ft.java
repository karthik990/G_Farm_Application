package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.sdk.C1806fu.C1807a;
import com.flurry.sdk.C1806fu.C1808b;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.ft */
public final class C1805ft implements C1806fu {

    /* renamed from: h */
    private final Set<String> f1108h = new HashSet();

    /* renamed from: i */
    private final Set<Integer> f1109i = new HashSet();

    /* renamed from: j */
    private final Set<Integer> f1110j = new HashSet();

    /* renamed from: a */
    public final C1807a mo16488a(C1930jp jpVar) {
        if (jpVar.mo16501a().equals(C1928jn.FLUSH_FRAME)) {
            return new C1807a(C1808b.DO_NOT_DROP, new C1841gn(new C1842go(this.f1109i.size() + this.f1110j.size(), this.f1110j.isEmpty())));
        }
        if (!jpVar.mo16501a().equals(C1928jn.ANALYTICS_EVENT)) {
            return f1111a;
        }
        C1839gm gmVar = (C1839gm) jpVar.mo16519f();
        String str = gmVar.f1209a;
        int i = gmVar.f1210b;
        if (TextUtils.isEmpty(str)) {
            return f1113c;
        }
        if (m1012a(gmVar) && !this.f1109i.contains(Integer.valueOf(i))) {
            this.f1110j.add(Integer.valueOf(i));
            return f1115e;
        } else if (this.f1109i.size() >= 1000 && !m1012a(gmVar)) {
            this.f1110j.add(Integer.valueOf(i));
            return f1114d;
        } else if (this.f1108h.contains(str) || this.f1108h.size() < 500) {
            this.f1108h.add(str);
            this.f1109i.add(Integer.valueOf(i));
            return f1111a;
        } else {
            this.f1110j.add(Integer.valueOf(i));
            return f1112b;
        }
    }

    /* renamed from: a */
    public final void mo16489a() {
        this.f1108h.clear();
        this.f1109i.clear();
        this.f1110j.clear();
    }

    /* renamed from: a */
    private static boolean m1012a(C1839gm gmVar) {
        return gmVar.f1213e && !gmVar.f1214f;
    }
}
