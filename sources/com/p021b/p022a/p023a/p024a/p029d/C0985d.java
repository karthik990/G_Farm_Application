package com.p021b.p022a.p023a.p024a.p029d;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import com.p021b.p022a.p023a.p024a.p029d.C0981a.C0982a;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.d.d */
public class C0985d implements C0981a {

    /* renamed from: a */
    private final int[] f163a = new int[2];

    /* renamed from: a */
    private void m227a(ViewGroup viewGroup, JSONObject jSONObject, C0982a aVar) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            aVar.mo11547a(viewGroup.getChildAt(i), this, jSONObject);
        }
    }

    /* renamed from: b */
    private void m228b(ViewGroup viewGroup, JSONObject jSONObject, C0982a aVar) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            ArrayList arrayList = (ArrayList) hashMap.get(Float.valueOf(childAt.getZ()));
            if (arrayList == null) {
                arrayList = new ArrayList();
                hashMap.put(Float.valueOf(childAt.getZ()), arrayList);
            }
            arrayList.add(childAt);
        }
        ArrayList arrayList2 = new ArrayList(hashMap.keySet());
        Collections.sort(arrayList2);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((ArrayList) hashMap.get((Float) it.next())).iterator();
            while (it2.hasNext()) {
                aVar.mo11547a((View) it2.next(), this, jSONObject);
            }
        }
    }

    /* renamed from: a */
    public JSONObject mo11545a(View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        view.getLocationOnScreen(this.f163a);
        int[] iArr = this.f163a;
        return C0987b.m236a(iArr[0], iArr[1], width, height);
    }

    /* renamed from: a */
    public void mo11546a(View view, JSONObject jSONObject, C0982a aVar, boolean z) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!z || VERSION.SDK_INT < 21) {
                m227a(viewGroup, jSONObject, aVar);
            } else {
                m228b(viewGroup, jSONObject, aVar);
            }
        }
    }
}
