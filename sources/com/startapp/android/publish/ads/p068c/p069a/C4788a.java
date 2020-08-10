package com.startapp.android.publish.ads.p068c.p069a;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.html.C5124a;

/* renamed from: com.startapp.android.publish.ads.c.a.a */
/* compiled from: StartAppSDK */
public class C4788a extends C5124a {
    public C4788a(Context context, C4789b bVar, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, bVar, adPreferences, adEventListener, Placement.INAPP_OFFER_WALL, true);
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
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        mo62804a(bool.booleanValue());
    }
}
