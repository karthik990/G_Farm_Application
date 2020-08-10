package com.startapp.android.publish.cache;

import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.cache.f */
/* compiled from: StartAppSDK */
public class C5084f extends C5082e {
    /* access modifiers changed from: protected */
    /* renamed from: e */
    public String mo62483e() {
        return "CacheTTLReloadTimer";
    }

    public C5084f(C5085g gVar) {
        super(gVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public boolean mo62481c() {
        return C5059m.m3378a().mo62378a(this.f3397a.mo62504c());
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public long mo62482d() {
        C5021g b = this.f3397a.mo62502b();
        String str = "CacheTTLReloadTimer";
        if (b == null) {
            C5155g.m3807a(str, 3, "Missing ad");
            return -1;
        }
        Long adCacheTtl = b.getAdCacheTtl();
        Long lastLoadTime = b.getLastLoadTime();
        if (adCacheTtl == null || lastLoadTime == null) {
            C5155g.m3807a(str, 3, "Missing TTL or last loading time");
            return -1;
        }
        long longValue = adCacheTtl.longValue() - (System.currentTimeMillis() - lastLoadTime.longValue());
        if (longValue < 0) {
            longValue = 0;
        }
        return longValue;
    }
}
