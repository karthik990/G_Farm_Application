package com.flurry.sdk;

import android.os.SystemClock;
import org.json.JSONException;

/* renamed from: com.flurry.sdk.jm */
public abstract class C1927jm implements C1930jp {

    /* renamed from: a */
    private long f1342a = System.currentTimeMillis();

    /* renamed from: b */
    private long f1343b = SystemClock.elapsedRealtime();

    /* renamed from: c */
    private C1929jo f1344c;

    /* renamed from: d */
    private boolean f1345d;

    public C1927jm(C1929jo joVar) {
        this.f1344c = joVar;
        this.f1345d = true;
    }

    /* renamed from: c */
    public final long mo16516c() {
        return this.f1342a;
    }

    /* renamed from: d */
    public final long mo16517d() {
        return this.f1343b;
    }

    /* renamed from: e */
    public final String mo16518e() {
        try {
            return this.f1344c.mo16502a().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* renamed from: f */
    public final C1929jo mo16519f() {
        return this.f1344c;
    }

    /* renamed from: h */
    public final boolean mo16521h() {
        return this.f1345d;
    }

    /* renamed from: g */
    public final byte mo16520g() {
        return (this.f1345d ^ true) | true ? (byte) 1 : 0;
    }
}
