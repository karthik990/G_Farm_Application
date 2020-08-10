package com.flurry.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.cg */
public final class C1660cg {

    /* renamed from: a */
    public static final C1660cg f812a = new C1660cg("APP");

    /* renamed from: b */
    public static final C1660cg f813b = new C1660cg("KILLSWITCH");

    /* renamed from: c */
    private static final Map<String, C1660cg> f814c = new HashMap();

    /* renamed from: d */
    private String f815d;

    private C1660cg(String str) {
        this.f815d = str;
        f814c.put(str, this);
    }

    /* renamed from: a */
    public static C1660cg m675a(String str) {
        if (f814c.containsKey(str)) {
            return (C1660cg) f814c.get(str);
        }
        return new C1660cg(str);
    }

    /* renamed from: a */
    public static Collection<C1660cg> m676a() {
        return f814c.values();
    }

    public final String toString() {
        return this.f815d;
    }
}
