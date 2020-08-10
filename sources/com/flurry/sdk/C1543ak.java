package com.flurry.sdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.ak */
public final class C1543ak {

    /* renamed from: a */
    protected Map<C1544al, String> f507a;

    /* renamed from: b */
    public boolean f508b;

    C1543ak() {
        this.f507a = new HashMap();
    }

    private C1543ak(Map<C1544al, String> map, boolean z) {
        this.f507a = map;
        this.f508b = z;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo16261a(C1544al alVar, String str) {
        this.f507a.put(alVar, str);
    }

    /* renamed from: a */
    public final Map<C1544al, String> mo16260a() {
        return this.f507a;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f507a);
        sb.append(this.f508b);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final C1543ak mo16262b() {
        return new C1543ak(Collections.unmodifiableMap(this.f507a), this.f508b);
    }
}
