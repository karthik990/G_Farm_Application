package com.startapp.android.publish.ads.banner.banner3d;

import android.content.Context;
import com.startapp.android.publish.ads.banner.C4765a;
import com.startapp.android.publish.ads.banner.C4786c;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.p065a.C4732a;

/* renamed from: com.startapp.android.publish.ads.banner.banner3d.c */
/* compiled from: StartAppSDK */
public class C4778c extends C4732a {

    /* renamed from: g */
    private int f2553g = 0;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61144a(C4925Ad ad) {
    }

    public C4778c(Context context, C4773a aVar, int i, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, aVar, adPreferences, adEventListener, Placement.INAPP_BANNER);
        this.f2553g = i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        C4773a aVar = (C4773a) this.f3197b;
        C4765a aVar2 = new C4765a();
        mo62215b((GetAdRequest) aVar2);
        aVar2.setAdsNumber(C4786c.m2356a().mo61369b().mo61278f());
        aVar2.setOffset(this.f2553g);
        aVar2.mo61294a(aVar.mo61344b());
        return aVar2;
    }
}
