package com.startapp.android.publish.adsCommon.p077a;

import com.startapp.android.publish.common.model.AdPreferences.Placement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.startapp.android.publish.adsCommon.a.b */
/* compiled from: StartAppSDK */
public class C4954b {

    /* renamed from: a */
    private static C4954b f3069a = new C4954b();

    /* renamed from: b */
    private List<C4953a> f3070b = new ArrayList();

    /* renamed from: c */
    private Map<Placement, List<C4953a>> f3071c = new HashMap();

    /* renamed from: d */
    private Map<String, List<C4953a>> f3072d = new HashMap();

    /* renamed from: a */
    public static C4954b m2946a() {
        return f3069a;
    }

    /* renamed from: b */
    public void mo62056b() {
        this.f3070b.clear();
        this.f3071c.clear();
        this.f3072d.clear();
    }

    /* renamed from: c */
    public List<C4953a> mo62057c() {
        return this.f3070b;
    }

    /* renamed from: a */
    public List<C4953a> mo62053a(Placement placement) {
        return (List) this.f3071c.get(placement);
    }

    /* renamed from: a */
    public List<C4953a> mo62054a(String str) {
        return (List) this.f3072d.get(str);
    }

    /* renamed from: a */
    public synchronized void mo62055a(C4953a aVar) {
        this.f3070b.add(0, aVar);
        List list = (List) this.f3071c.get(aVar.mo62049a());
        if (list == null) {
            list = new ArrayList();
            this.f3071c.put(aVar.mo62049a(), list);
        }
        list.add(0, aVar);
        List list2 = (List) this.f3072d.get(aVar.mo62050b());
        if (list2 == null) {
            list2 = new ArrayList();
            this.f3072d.put(aVar.mo62050b(), list2);
        }
        list2.add(0, aVar);
    }

    /* renamed from: d */
    public int mo62058d() {
        return this.f3070b.size();
    }
}
