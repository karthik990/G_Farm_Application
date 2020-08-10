package com.startapp.android.publish.ads.p068c.p069a;

import android.content.Context;
import com.startapp.android.publish.ads.p067b.C4759c;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.c.a.b */
/* compiled from: StartAppSDK */
public class C4789b extends C4759c {
    private static final long serialVersionUID = 1;

    public C4789b(Context context) {
        super(context, Placement.INAPP_OFFER_WALL);
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        new C4788a(this.context, this, adPreferences, adEventListener).mo62216c();
    }
}
