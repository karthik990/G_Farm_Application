package com.startapp.android.publish.ads.p068c.p070b;

import android.content.Context;
import com.startapp.android.publish.ads.list3d.C4810e;
import com.startapp.android.publish.ads.list3d.C4811f;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.p065a.C4732a;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.c.b.a */
/* compiled from: StartAppSDK */
public class C4790a extends C4732a {
    public C4790a(Context context, C4791b bVar, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, bVar, adPreferences, adEventListener, Placement.INAPP_OFFER_WALL);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        GetAdRequest a = super.mo61353a();
        if (a == null) {
            return null;
        }
        a.setAdsNumber(C4983b.m3032a().mo62167g());
        return a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61144a(C4925Ad ad) {
        C4791b bVar = (C4791b) ad;
        List<AdDetails> d = bVar.mo62336d();
        C4810e a = C4811f.m2473a().mo61493a(bVar.mo61342a());
        a.mo61483a();
        if (d != null) {
            for (AdDetails a2 : d) {
                a.mo61486a(a2);
            }
        }
    }
}
