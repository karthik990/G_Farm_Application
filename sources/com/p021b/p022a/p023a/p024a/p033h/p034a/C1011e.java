package com.p021b.p022a.p023a.p024a.p033h.p034a;

import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p028c.C0972a;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1006b.C1008b;
import java.util.HashSet;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.h.a.e */
public class C1011e extends C1005a {
    public C1011e(C1008b bVar, HashSet<String> hashSet, JSONObject jSONObject, double d) {
        super(bVar, hashSet, jSONObject, d);
    }

    /* renamed from: b */
    private void m325b(String str) {
        C0972a a = C0972a.m170a();
        if (a != null) {
            for (C0970i iVar : a.mo11513b()) {
                if (this.f195a.contains(iVar.mo11499g())) {
                    iVar.mo11498f().mo11562b(str, this.f197c);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String doInBackground(Object... objArr) {
        return this.f196b.toString();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(String str) {
        m325b(str);
        super.onPostExecute(str);
    }
}
