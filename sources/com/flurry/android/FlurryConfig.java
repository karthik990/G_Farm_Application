package com.flurry.android;

import android.os.Handler;
import android.util.Pair;
import com.flurry.sdk.C1479a;
import com.flurry.sdk.C1637bw;
import com.flurry.sdk.C1642by;
import com.flurry.sdk.C1644bz;
import com.flurry.sdk.C1660cg;
import com.flurry.sdk.C1738eb;
import java.util.Map;

public final class FlurryConfig {

    /* renamed from: a */
    private static FlurryConfig f303a;

    /* renamed from: b */
    private C1644bz f304b = C1644bz.m630a();

    public static synchronized FlurryConfig getInstance() {
        FlurryConfig flurryConfig;
        synchronized (FlurryConfig.class) {
            if (f303a == null) {
                if (C1479a.m361i()) {
                    f303a = new FlurryConfig();
                } else {
                    throw new IllegalStateException("Flurry SDK must be initialized before starting config");
                }
            }
            flurryConfig = f303a;
        }
        return flurryConfig;
    }

    private FlurryConfig() {
    }

    public final void fetchConfig() {
        this.f304b.mo16338d();
    }

    public final boolean activateConfig() {
        return this.f304b.mo16336a((C1660cg) null);
    }

    public final void resetState() {
        C1644bz bzVar = this.f304b;
        bzVar.runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1673cr.m735c(C1576b.m502a());
                if (C1644bz.this.f764f != null) {
                    C1644bz.this.f764f.mo16364a();
                }
                C1644bz.this.f761a.mo16346c();
                C1644bz.this.f770m = false;
                C1644bz.this.f772o = C1651a.f786d;
                C1644bz.this.f771n = false;
                for (C1660cg cgVar : C1660cg.m676a()) {
                    Map d = C1644bz.this.f768j;
                    Boolean bool = Boolean.FALSE;
                    d.put(cgVar, new Pair(bool, bool));
                }
            }
        });
    }

    public final void registerListener(FlurryConfigListener flurryConfigListener) {
        this.f304b.mo16334a(flurryConfigListener, C1660cg.f812a, null);
    }

    public final void registerListener(FlurryConfigListener flurryConfigListener, Handler handler) {
        this.f304b.mo16334a(flurryConfigListener, C1660cg.f812a, handler);
    }

    public final void unregisterListener(FlurryConfigListener flurryConfigListener) {
        this.f304b.mo16333a(flurryConfigListener);
    }

    public final String getString(String str, String str2) {
        return this.f304b.mo16337c().mo16324a(str, str2, C1660cg.f812a);
    }

    public final boolean getBoolean(String str, boolean z) {
        C1637bw c = this.f304b.mo16337c();
        C1642by a = c.f738b.mo16362a(str, C1660cg.f812a);
        if (a == null) {
            a = c.f737a.mo16349a(str);
        }
        return a != null ? Boolean.parseBoolean(a.mo16330a()) : z;
    }

    public final int getInt(String str, int i) {
        return this.f304b.mo16337c().mo16322a(str, i, C1660cg.f812a);
    }

    public final long getLong(String str, long j) {
        return this.f304b.mo16337c().mo16323a(str, j, C1660cg.f812a);
    }

    public final double getDouble(String str, double d) {
        return this.f304b.mo16337c().mo16320a(str, d, C1660cg.f812a);
    }

    public final float getFloat(String str, float f) {
        return this.f304b.mo16337c().mo16321a(str, f, C1660cg.f812a);
    }

    public final String toString() {
        return this.f304b.toString();
    }
}
