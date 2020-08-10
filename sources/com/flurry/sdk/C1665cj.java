package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.cj */
public final class C1665cj implements Comparable<C1665cj> {

    /* renamed from: a */
    C1660cg f826a;

    /* renamed from: b */
    public int f827b;

    /* renamed from: c */
    public int f828c;

    /* renamed from: d */
    JSONObject f829d;

    /* renamed from: e */
    Map<String, C1642by> f830e = new HashMap();

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        C1665cj cjVar = (C1665cj) obj;
        C1660cg cgVar = this.f826a;
        if (cgVar != cjVar.f826a) {
            return cgVar == C1660cg.f812a ? -1 : 1;
        }
        return this.f827b - cjVar.f827b;
    }

    public C1665cj(C1665cj cjVar) {
        this.f826a = cjVar.f826a;
        this.f827b = cjVar.f827b;
        this.f828c = cjVar.f828c;
        this.f829d = cjVar.f829d;
        this.f830e = new HashMap(cjVar.f830e);
    }

    public C1665cj(C1660cg cgVar) {
        this.f826a = cgVar;
    }

    /* renamed from: a */
    public final C1642by mo16355a(String str) {
        return (C1642by) this.f830e.get(str);
    }

    /* renamed from: a */
    public final Set<Entry<String, C1642by>> mo16356a() {
        return this.f830e.entrySet();
    }

    /* renamed from: a */
    public final void mo16357a(C1665cj cjVar) {
        for (Entry entry : cjVar.mo16356a()) {
            String str = (String) entry.getKey();
            if (!this.f830e.containsKey(str)) {
                this.f830e.put(str, entry.getValue());
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C1665cj)) {
            return false;
        }
        C1665cj cjVar = (C1665cj) obj;
        return this.f826a == cjVar.f826a && this.f827b == cjVar.f827b;
    }

    public final int hashCode() {
        return (this.f826a.hashCode() * 31) + this.f827b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f826a);
        String str = ":";
        sb.append(str);
        sb.append(this.f827b);
        sb.append(str);
        sb.append(this.f828c);
        return sb.toString();
    }
}
