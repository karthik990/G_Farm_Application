package com.startapp.android.publish.ads.banner;

import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.common.model.GetAdRequest;

/* renamed from: com.startapp.android.publish.ads.banner.a */
/* compiled from: StartAppSDK */
public class C4765a extends GetAdRequest {

    /* renamed from: a */
    private boolean f2527a;

    /* renamed from: b */
    private int f2528b;

    /* renamed from: a */
    public void mo61294a(boolean z) {
        this.f2527a = z;
    }

    /* renamed from: a */
    public boolean mo61295a() {
        return this.f2527a;
    }

    /* renamed from: b */
    public int mo61296b() {
        return this.f2528b;
    }

    /* renamed from: a */
    public void mo61293a(int i) {
        this.f2528b = i;
    }

    public C4941e getNameValueMap() {
        C4941e nameValueMap = super.getNameValueMap();
        if (nameValueMap == null) {
            nameValueMap = new C4940d();
        }
        m2313a(nameValueMap);
        return nameValueMap;
    }

    /* renamed from: a */
    private void m2313a(C4941e eVar) {
        eVar.mo62030a("fixedSize", (Object) Boolean.valueOf(mo61295a()), false);
        eVar.mo62030a("bnrt", (Object) Integer.valueOf(mo61296b()), false);
    }
}
