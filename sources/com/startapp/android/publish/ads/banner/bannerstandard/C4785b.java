package com.startapp.android.publish.ads.banner.bannerstandard;

import android.content.Context;
import com.startapp.android.publish.ads.banner.C4765a;
import com.startapp.android.publish.ads.banner.C4786c;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.html.C5124a;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.ads.banner.bannerstandard.b */
/* compiled from: StartAppSDK */
public class C4785b extends C5124a {

    /* renamed from: i */
    private int f2554i = 0;

    public C4785b(Context context, C5003e eVar, int i, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, eVar, adPreferences, adEventListener, Placement.INAPP_BANNER, false);
        this.f2554i = i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        C4784a aVar = (C4784a) this.f3197b;
        C4765a aVar2 = new C4765a();
        mo62215b((GetAdRequest) aVar2);
        aVar2.setWidth(aVar.mo62245h());
        aVar2.setHeight(aVar.mo62247j());
        aVar2.setOffset(this.f2554i);
        aVar2.setAdsNumber(C4786c.m2356a().mo61369b().mo61279g());
        aVar2.mo61294a(aVar.mo61367b());
        aVar2.mo61293a(aVar.mo61368c());
        return aVar2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        mo62804a(bool.booleanValue());
        StringBuilder sb = new StringBuilder();
        sb.append("Html onPostExecute, result=[");
        sb.append(bool);
        sb.append("]");
        C5155g.m3805a(4, sb.toString());
    }
}
