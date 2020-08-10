package com.startapp.p054a.p055a.p062g;

import com.startapp.p054a.p055a.p056a.C4696a;
import com.startapp.p054a.p055a.p059d.C4711e;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.startapp.a.a.g.c */
/* compiled from: StartAppSDK */
public class C4722c {

    /* renamed from: a */
    private final Map<C4719a, C4721b> f2437a = new HashMap();

    public C4722c() {
        this.f2437a.put(C4719a.ZERO, new C4726g());
        this.f2437a.put(C4719a.THREE, new C4725f());
        this.f2437a.put(C4719a.FOUR, new C4724e());
        this.f2437a.put(C4719a.FIVE, new C4723d());
    }

    /* renamed from: a */
    public C4696a mo61131a(C4719a aVar) {
        return ((C4721b) this.f2437a.get(aVar)).mo61130b();
    }

    /* renamed from: b */
    public C4711e mo61132b(C4719a aVar) {
        return ((C4721b) this.f2437a.get(aVar)).mo61129a();
    }
}
