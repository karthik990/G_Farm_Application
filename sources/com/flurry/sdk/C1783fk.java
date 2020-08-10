package com.flurry.sdk;

import com.flurry.sdk.C1812fy.C1813a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.flurry.sdk.fk */
final class C1783fk extends C1798fr {

    /* renamed from: a */
    protected List<C1806fu> f1062a = new ArrayList();

    /* renamed from: b */
    protected final Map<String, List<C1930jp>> f1063b = new HashMap();

    C1783fk(C1788fm fmVar) {
        super("DropModule", fmVar);
        this.f1062a.add(new C1805ft());
        this.f1062a.add(new C1804fs());
        this.f1062a.add(new C1809fv());
        this.f1062a.add(new C1810fw());
    }

    /* renamed from: a */
    public final void mo16471a(final C1930jp jpVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1783fk.m954a(C1783fk.this, C1783fk.m953a(C1783fk.this, jpVar));
                C1783fk.m955b(C1783fk.this, jpVar);
            }
        });
    }

    /* renamed from: e */
    private List<C1930jp> m956e(C1930jp jpVar) {
        ArrayList arrayList = new ArrayList();
        for (Entry value : this.f1063b.entrySet()) {
            for (C1930jp f : (List) value.getValue()) {
                C1839gm gmVar = (C1839gm) f.mo16519f();
                String str = gmVar.f1209a;
                int i = gmVar.f1210b;
                long currentTimeMillis = System.currentTimeMillis();
                arrayList.add(C1837gl.m1077a(str, i, gmVar.f1211c, gmVar.f1212d, currentTimeMillis, currentTimeMillis - gmVar.f1217i));
            }
        }
        arrayList.add(jpVar);
        return arrayList;
    }

    /* renamed from: f */
    private static boolean m957f(C1930jp jpVar) {
        return jpVar.mo16501a().equals(C1928jn.FLUSH_FRAME) && ((C1901io) jpVar.mo16519f()).f1296b.equals(C1813a.REASON_SESSION_FINALIZE.f1141j);
    }

    /* renamed from: a */
    static /* synthetic */ List m953a(C1783fk fkVar, C1930jp jpVar) {
        if (jpVar.mo16501a().equals(C1928jn.ANALYTICS_EVENT) && ((C1839gm) jpVar.mo16519f()).f1213e) {
            ArrayList arrayList = new ArrayList();
            String str = ((C1839gm) jpVar.mo16519f()).f1209a;
            List list = (List) fkVar.f1063b.get(str);
            if (((C1839gm) jpVar.mo16519f()).f1214f) {
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(jpVar);
                fkVar.f1063b.put(str, list);
                arrayList.add(jpVar);
                return arrayList;
            } else if (list == null || list.isEmpty()) {
                return arrayList;
            } else {
                C1839gm gmVar = (C1839gm) ((C1930jp) list.remove(0)).mo16519f();
                C1839gm gmVar2 = (C1839gm) jpVar.mo16519f();
                gmVar2.f1210b = gmVar.f1210b;
                gmVar2.f1215g = gmVar2.f1217i - gmVar.f1217i;
                Map<String, String> map = gmVar.f1211c;
                Map<String, String> map2 = gmVar2.f1211c;
                if (!(map == null || map2 == null)) {
                    Map<String, String> map3 = gmVar.f1212d;
                    Map<String, String> map4 = gmVar2.f1212d;
                    if (((String) map3.get(C1734dz.m874b("fl.parameter.limit.exceeded"))) != null) {
                        map4.putAll(map3);
                        map2.clear();
                    } else if (map.size() + map2.size() > 10) {
                        map4.put(C1734dz.m874b("fl.parameter.limit.exceeded.on.endevent"), C1734dz.m874b(String.valueOf(map2.size())));
                        map2.clear();
                        map2.putAll(map);
                    } else if (!map.isEmpty()) {
                        map2.putAll(map);
                    }
                }
                arrayList.add(jpVar);
                return arrayList;
            }
        } else if (m957f(jpVar)) {
            return fkVar.m956e(jpVar);
        } else {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(jpVar);
            return arrayList2;
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<com.flurry.sdk.jp>, for r6v0, types: [java.util.List, java.util.List<com.flurry.sdk.jp>] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void m954a(com.flurry.sdk.C1783fk r5, java.util.List<com.flurry.sdk.C1930jp> r6) {
        /*
            java.util.Iterator r6 = r6.iterator()
        L_0x0004:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x007d
            java.lang.Object r0 = r6.next()
            com.flurry.sdk.jp r0 = (com.flurry.sdk.C1930jp) r0
            java.util.List<com.flurry.sdk.fu> r1 = r5.f1062a
            java.util.Iterator r1 = r1.iterator()
        L_0x0016:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x003c
            java.lang.Object r2 = r1.next()
            com.flurry.sdk.fu r2 = (com.flurry.sdk.C1806fu) r2
            com.flurry.sdk.fu$a r2 = r2.mo16488a(r0)
            com.flurry.sdk.fu$b r3 = r2.f1118a
            com.flurry.sdk.fu$b r4 = com.flurry.sdk.C1806fu.C1808b.DO_NOT_DROP
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0032
            r1 = 1
            goto L_0x003d
        L_0x0032:
            com.flurry.sdk.jp r3 = r2.f1119b
            if (r3 == 0) goto L_0x0016
            com.flurry.sdk.jp r2 = r2.f1119b
            r5.mo16487d(r2)
            goto L_0x0016
        L_0x003c:
            r1 = 0
        L_0x003d:
            java.lang.String r2 = "DropModule"
            r3 = 4
            if (r1 != 0) goto L_0x005b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "Adding Frame:"
            r1.<init>(r4)
            java.lang.String r4 = r0.mo16518e()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            com.flurry.sdk.C1685cy.m754a(r3, r2, r1)
            r5.mo16487d(r0)
            goto L_0x0004
        L_0x005b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "Dropping Frame: "
            r1.<init>(r4)
            com.flurry.sdk.jn r4 = r0.mo16501a()
            r1.append(r4)
            java.lang.String r4 = ": "
            r1.append(r4)
            java.lang.String r0 = r0.mo16518e()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.flurry.sdk.C1685cy.m754a(r3, r2, r0)
            goto L_0x0004
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1783fk.m954a(com.flurry.sdk.fk, java.util.List):void");
    }

    /* renamed from: b */
    static /* synthetic */ void m955b(C1783fk fkVar, C1930jp jpVar) {
        if (m957f(jpVar)) {
            String str = "DropModule";
            C1685cy.m754a(4, str, "Resetting drop rules");
            for (C1806fu a : fkVar.f1062a) {
                a.mo16489a();
            }
            C1685cy.m754a(4, str, "Reset start timed event record");
            fkVar.f1063b.clear();
        }
    }
}
