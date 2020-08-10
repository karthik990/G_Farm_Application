package com.startapp.android.publish.ads.list3d;

import com.startapp.common.p092a.C5155g;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.startapp.android.publish.ads.list3d.f */
/* compiled from: StartAppSDK */
public class C4811f {

    /* renamed from: a */
    private static C4811f f2664a = new C4811f();

    /* renamed from: b */
    private Map<String, C4810e> f2665b = new ConcurrentHashMap();

    private C4811f() {
    }

    /* renamed from: a */
    public static C4811f m2473a() {
        return f2664a;
    }

    /* renamed from: a */
    public C4810e mo61493a(String str) {
        if (this.f2665b.containsKey(str)) {
            return (C4810e) this.f2665b.get(str);
        }
        C4810e eVar = new C4810e();
        this.f2665b.put(str, eVar);
        StringBuilder sb = new StringBuilder();
        sb.append("Created new model for uuid ");
        sb.append(str);
        sb.append(", Size = ");
        sb.append(this.f2665b.size());
        C5155g.m3807a("ListModelManager", 3, sb.toString());
        return eVar;
    }

    /* renamed from: b */
    public void mo61494b(String str) {
        this.f2665b.remove(str);
        StringBuilder sb = new StringBuilder();
        sb.append("Model for ");
        sb.append(str);
        sb.append(" was removed, Size = ");
        sb.append(this.f2665b.size());
        C5155g.m3807a("ListModelManager", 3, sb.toString());
    }
}
