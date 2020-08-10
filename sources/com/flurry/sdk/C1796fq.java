package com.flurry.sdk;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: com.flurry.sdk.fq */
final class C1796fq extends C1798fr {

    /* renamed from: a */
    protected static C1928jn[] f1087a = {C1928jn.SESSION_INFO, C1928jn.APP_INFO, C1928jn.REPORTED_ID, C1928jn.DEVICE_PROPERTIES, C1928jn.NOTIFICATION, C1928jn.REFERRER, C1928jn.LAUNCH_OPTIONS, C1928jn.CONSENT, C1928jn.APP_STATE, C1928jn.NETWORK, C1928jn.LOCALE, C1928jn.TIMEZONE, C1928jn.APP_ORIENTATION, C1928jn.DYNAMIC_SESSION_INFO, C1928jn.LOCATION, C1928jn.USER_ID, C1928jn.BIRTHDATE, C1928jn.GENDER};

    /* renamed from: b */
    protected static C1928jn[] f1088b = {C1928jn.ORIGIN_ATTRIBUTE};
    /* access modifiers changed from: private */

    /* renamed from: g */
    public EnumMap<C1928jn, C1930jp> f1089g = new EnumMap<>(C1928jn.class);
    /* access modifiers changed from: private */

    /* renamed from: h */
    public EnumMap<C1928jn, List<C1930jp>> f1090h = new EnumMap<>(C1928jn.class);

    C1796fq(C1788fm fmVar) {
        super("StickyModule", fmVar);
        for (C1928jn put : f1087a) {
            this.f1089g.put(put, null);
        }
        for (C1928jn put2 : f1088b) {
            this.f1090h.put(put2, null);
        }
    }

    /* renamed from: a */
    public final void mo16471a(final C1930jp jpVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1796fq.this.mo16487d(jpVar);
                C1796fq.m991a(C1796fq.this, jpVar);
                if (C1928jn.FLUSH_FRAME.equals(jpVar.mo16501a())) {
                    for (Entry value : C1796fq.this.f1089g.entrySet()) {
                        C1930jp jpVar = (C1930jp) value.getValue();
                        if (jpVar != null) {
                            C1796fq.this.mo16487d(jpVar);
                        }
                    }
                    for (Entry value2 : C1796fq.this.f1090h.entrySet()) {
                        List list = (List) value2.getValue();
                        if (!(list == null || list.size() == 0)) {
                            for (int i = 0; i < list.size(); i++) {
                                C1796fq.this.mo16487d((C1930jp) list.get(i));
                            }
                        }
                    }
                }
            }
        });
    }

    /* renamed from: a */
    static /* synthetic */ void m991a(C1796fq fqVar, C1930jp jpVar) {
        C1928jn a = jpVar.mo16501a();
        List arrayList = new ArrayList();
        if (fqVar.f1089g.containsKey(a)) {
            fqVar.f1089g.put(a, jpVar);
        }
        if (fqVar.f1090h.containsKey(a)) {
            if (fqVar.f1090h.get(a) != null) {
                arrayList = (List) fqVar.f1090h.get(a);
            }
            arrayList.add(jpVar);
            fqVar.f1090h.put(a, arrayList);
        }
    }
}
