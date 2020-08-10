package com.flurry.sdk;

import android.util.SparseArray;
import com.flurry.sdk.C1642by.C1643a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ck */
public final class C1666ck {

    /* renamed from: a */
    Map<C1660cg, SparseArray<C1665cj>> f831a;

    /* renamed from: b */
    Map<C1660cg, Map<String, C1642by>> f832b;

    /* renamed from: c */
    long f833c;

    /* renamed from: d */
    private Map<C1660cg, Map<String, C1642by>> f834d;

    public C1666ck() {
        mo16364a();
    }

    /* renamed from: a */
    public final synchronized void mo16366a(List<C1665cj> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                m688a(list, this.f831a);
                m693c(list);
            }
        }
    }

    /* renamed from: b */
    public final synchronized boolean mo16369b(List<C1665cj> list) {
        if (list == null) {
            return false;
        }
        if (list.size() != mo16372e()) {
            return true;
        }
        for (C1665cj cjVar : list) {
            SparseArray sparseArray = (SparseArray) this.f831a.get(cjVar.f826a);
            if (sparseArray == null) {
                return true;
            }
            C1665cj cjVar2 = (C1665cj) sparseArray.get(cjVar.f827b);
            if (cjVar2 == null) {
                return true;
            }
            if (cjVar.f828c != cjVar2.f828c) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public final synchronized boolean mo16367a(List<C1665cj> list, boolean z) {
        if (list == null) {
            return true;
        }
        if (list.isEmpty()) {
            m694f();
            return true;
        } else if (z) {
            m694f();
            m688a(list, this.f831a);
            m692b(list, this.f832b);
            return true;
        } else {
            HashMap hashMap = new HashMap();
            m691a(this.f831a, (Map<C1660cg, SparseArray<C1665cj>>) hashMap, true, true);
            HashMap hashMap2 = new HashMap();
            m690a(this.f832b, (Map<C1660cg, Map<String, C1642by>>) hashMap2, (C1660cg) null, true);
            m689a(list, (Map<C1660cg, SparseArray<C1665cj>>) hashMap, (Map<C1660cg, Map<String, C1642by>>) hashMap2);
            m688a(list, (Map<C1660cg, SparseArray<C1665cj>>) hashMap);
            m692b(list, hashMap2);
            C1685cy.m756a("VariantsManager", "Verify ETag merged JSON: ".concat(String.valueOf(mo16363a((Map<C1660cg, SparseArray<C1665cj>>) hashMap, (Map<C1660cg, Map<String, C1642by>>) hashMap2, true))));
            m691a((Map<C1660cg, SparseArray<C1665cj>>) hashMap, this.f831a, false, false);
            m690a((Map<C1660cg, Map<String, C1642by>>) hashMap2, this.f832b, (C1660cg) null, false);
            return true;
        }
    }

    /* renamed from: a */
    private static void m691a(Map<C1660cg, SparseArray<C1665cj>> map, Map<C1660cg, SparseArray<C1665cj>> map2, boolean z, boolean z2) {
        SparseArray sparseArray;
        for (Entry entry : map.entrySet()) {
            C1660cg cgVar = (C1660cg) entry.getKey();
            if (z) {
                SparseArray sparseArray2 = (SparseArray) entry.getValue();
                sparseArray = new SparseArray(sparseArray2.size());
                for (int i = 0; i < sparseArray2.size(); i++) {
                    C1665cj cjVar = (C1665cj) sparseArray2.valueAt(i);
                    int i2 = cjVar.f827b;
                    if (z2) {
                        cjVar = new C1665cj(cjVar);
                    }
                    sparseArray.put(i2, cjVar);
                }
            } else {
                sparseArray = (SparseArray) entry.getValue();
            }
            map2.put(cgVar, sparseArray);
        }
    }

    /* renamed from: a */
    private synchronized void m689a(List<C1665cj> list, Map<C1660cg, SparseArray<C1665cj>> map, Map<C1660cg, Map<String, C1642by>> map2) {
        HashMap hashMap = new HashMap();
        m691a(map, (Map<C1660cg, SparseArray<C1665cj>>) hashMap, true, false);
        for (C1665cj cjVar : list) {
            SparseArray sparseArray = (SparseArray) hashMap.get(cjVar.f826a);
            if (sparseArray != null) {
                sparseArray.remove(cjVar.f827b);
            }
        }
        for (Entry entry : hashMap.entrySet()) {
            C1660cg cgVar = (C1660cg) entry.getKey();
            SparseArray sparseArray2 = (SparseArray) entry.getValue();
            SparseArray sparseArray3 = (SparseArray) map.get(cgVar);
            Map map3 = (Map) map2.get(cgVar);
            for (int i = 0; i < sparseArray2.size(); i++) {
                C1665cj cjVar2 = (C1665cj) sparseArray2.valueAt(i);
                sparseArray3.remove(cjVar2.f827b);
                for (String remove : cjVar2.f830e.keySet()) {
                    map3.remove(remove);
                }
            }
        }
    }

    /* renamed from: a */
    private synchronized void m688a(List<C1665cj> list, Map<C1660cg, SparseArray<C1665cj>> map) {
        for (C1665cj cjVar : list) {
            int i = cjVar.f827b;
            C1660cg cgVar = cjVar.f826a;
            SparseArray sparseArray = (SparseArray) map.get(cgVar);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                map.put(cgVar, sparseArray);
            } else {
                C1665cj cjVar2 = (C1665cj) sparseArray.get(i);
                if (cjVar2 != null) {
                    cjVar.mo16357a(cjVar2);
                }
            }
            sparseArray.put(i, cjVar);
        }
    }

    /* renamed from: a */
    private static void m690a(Map<C1660cg, Map<String, C1642by>> map, Map<C1660cg, Map<String, C1642by>> map2, C1660cg cgVar, boolean z) {
        for (Entry entry : map.entrySet()) {
            C1660cg cgVar2 = (C1660cg) entry.getKey();
            if (cgVar == null || cgVar == cgVar2) {
                Map map3 = (Map) entry.getValue();
                if (z) {
                    map3 = new HashMap(map3);
                }
                map2.put(cgVar2, map3);
            }
        }
    }

    /* renamed from: c */
    private synchronized void m693c(List<C1665cj> list) {
        for (C1665cj cjVar : list) {
            C1660cg cgVar = cjVar.f826a;
            Map map = (Map) this.f834d.get(cgVar);
            if (map == null) {
                map = new HashMap();
                this.f834d.put(cgVar, map);
            }
            Map map2 = (Map) this.f832b.get(cgVar);
            if (map2 == null) {
                map2 = new HashMap();
                this.f832b.put(cgVar, map2);
            }
            for (Entry entry : cjVar.mo16356a()) {
                String str = (String) entry.getKey();
                C1642by byVar = (C1642by) entry.getValue();
                map.put(str, byVar);
                map2.put(str, byVar);
            }
        }
    }

    /* renamed from: b */
    private synchronized void m692b(List<C1665cj> list, Map<C1660cg, Map<String, C1642by>> map) {
        for (C1665cj cjVar : list) {
            C1660cg cgVar = cjVar.f826a;
            Map map2 = (Map) map.get(cgVar);
            if (map2 == null) {
                map2 = new HashMap();
                map.put(cgVar, map2);
            }
            for (Entry entry : cjVar.mo16356a()) {
                String str = (String) entry.getKey();
                C1642by byVar = (C1642by) entry.getValue();
                if (byVar.f751a == C1643a.Tombstone) {
                    map2.remove(str);
                } else {
                    map2.put(str, byVar);
                }
            }
        }
    }

    /* renamed from: a */
    public final synchronized void mo16365a(C1660cg cgVar) {
        String str = "VariantsManager";
        StringBuilder sb = new StringBuilder("original Variants properties:");
        sb.append(this.f834d.keySet().toString());
        sb.append(" with: ");
        sb.append(this.f831a.values().toString());
        C1685cy.m754a(3, str, sb.toString());
        m690a(this.f832b, this.f834d, cgVar, true);
        StringBuilder sb2 = new StringBuilder("new Variants properties:");
        sb2.append(this.f834d.keySet().toString());
        C1685cy.m754a(3, "VariantsManager", sb2.toString());
    }

    /* renamed from: a */
    public final C1642by mo16362a(String str, C1660cg cgVar) {
        if (cgVar == null) {
            for (Map map : this.f834d.values()) {
                C1642by byVar = (C1642by) map.get(str);
                if (byVar != null) {
                    return byVar;
                }
            }
        } else {
            Map map2 = (Map) this.f834d.get(cgVar);
            if (map2 != null) {
                return (C1642by) map2.get(str);
            }
        }
        return null;
    }

    /* renamed from: a */
    public final synchronized void mo16364a() {
        m694f();
        this.f834d = new HashMap();
        for (C1660cg put : C1660cg.m676a()) {
            this.f834d.put(put, new HashMap());
        }
    }

    /* renamed from: f */
    private synchronized void m694f() {
        this.f831a = new HashMap();
        this.f832b = new HashMap();
        for (C1660cg cgVar : C1660cg.m676a()) {
            this.f831a.put(cgVar, new SparseArray());
            this.f832b.put(cgVar, new HashMap());
        }
    }

    /* renamed from: b */
    public final synchronized List<C1665cj> mo16368b() {
        return m687a(this.f831a);
    }

    /* renamed from: a */
    private synchronized List<C1665cj> m687a(Map<C1660cg, SparseArray<C1665cj>> map) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (SparseArray sparseArray : map.values()) {
            for (int i = 0; i < sparseArray.size(); i++) {
                arrayList.add(sparseArray.valueAt(i));
            }
        }
        return arrayList;
    }

    /* renamed from: c */
    public final synchronized String mo16370c() {
        StringBuilder sb;
        sb = new StringBuilder();
        int i = 0;
        for (SparseArray sparseArray : this.f831a.values()) {
            i += sparseArray.size();
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                C1665cj cjVar = (C1665cj) sparseArray.valueAt(i2);
                StringBuilder sb2 = new StringBuilder(",");
                sb2.append(cjVar.f827b);
                sb.append(sb2.toString());
                StringBuilder sb3 = new StringBuilder(",");
                sb3.append(cjVar.f828c);
                sb.append(sb3.toString());
            }
        }
        sb.insert(0, i);
        return sb.toString();
    }

    /* renamed from: d */
    public final synchronized List<C1660cg> mo16371d() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (Entry entry : this.f831a.entrySet()) {
            if (((SparseArray) entry.getValue()).size() > 0) {
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }

    /* renamed from: e */
    public final synchronized int mo16372e() {
        int i;
        i = 0;
        for (SparseArray size : this.f831a.values()) {
            i += size.size();
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final JSONObject mo16363a(Map<C1660cg, SparseArray<C1665cj>> map, Map<C1660cg, Map<String, C1642by>> map2, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            List<C1665cj> a = m687a(map);
            if (z) {
                Collections.sort(a);
            }
            for (C1665cj cjVar : a) {
                Map map3 = (Map) map2.get(cjVar.f826a);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(TtmlNode.ATTR_ID, cjVar.f827b);
                jSONObject2.put(ClientCookie.VERSION_ATTR, cjVar.f828c);
                jSONObject2.put("document", cjVar.f826a.toString());
                JSONArray jSONArray2 = new JSONArray();
                for (Entry key : z ? new TreeMap(cjVar.f830e).entrySet() : cjVar.mo16356a()) {
                    String str = (String) key.getKey();
                    C1642by byVar = (C1642by) map3.get(str);
                    if (byVar != null) {
                        jSONArray2.put(byVar.mo16331a(str));
                    }
                }
                jSONObject2.put("items", jSONArray2);
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("variants", jSONArray);
            jSONObject.put("refreshInSeconds", this.f833c);
            return jSONObject;
        } catch (JSONException e) {
            C1685cy.m757a("VariantsManager", "Error to create JSON object.", (Throwable) e);
            return null;
        }
    }
}
