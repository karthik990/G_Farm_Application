package com.p021b.p022a.p023a.p024a.p029d;

import android.view.View;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p028c.C0972a;
import com.p021b.p022a.p023a.p024a.p029d.C0981a.C0982a;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0992f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.d.c */
public class C0984c implements C0981a {

    /* renamed from: a */
    private final C0981a f162a;

    public C0984c(C0981a aVar) {
        this.f162a = aVar;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public ArrayList<View> mo11549a() {
        ArrayList<View> arrayList = new ArrayList<>();
        C0972a a = C0972a.m170a();
        if (a != null) {
            Collection<C0970i> c = a.mo11515c();
            IdentityHashMap identityHashMap = new IdentityHashMap((c.size() * 2) + 3);
            for (C0970i h : c) {
                View h2 = h.mo11500h();
                if (h2 != null && C0992f.m268c(h2)) {
                    View rootView = h2.getRootView();
                    if (rootView != null && !identityHashMap.containsKey(rootView)) {
                        identityHashMap.put(rootView, rootView);
                        float a2 = C0992f.m266a(rootView);
                        int size = arrayList.size();
                        while (size > 0 && C0992f.m266a((View) arrayList.get(size - 1)) > a2) {
                            size--;
                        }
                        arrayList.add(size, rootView);
                    }
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public JSONObject mo11545a(View view) {
        return C0987b.m236a(0, 0, 0, 0);
    }

    /* renamed from: a */
    public void mo11546a(View view, JSONObject jSONObject, C0982a aVar, boolean z) {
        Iterator it = mo11549a().iterator();
        while (it.hasNext()) {
            aVar.mo11547a((View) it.next(), this.f162a, jSONObject);
        }
    }
}
