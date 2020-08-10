package com.startapp.android.publish.ads.p067b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.cache.C5081d;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.b.e */
/* compiled from: StartAppSDK */
public class C4761e extends C4759c {
    private static final long serialVersionUID = 1;

    public C4761e(Context context) {
        super(context, Placement.INAPP_RETURN);
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        new C4758b(this.context, this, adPreferences, adEventListener).mo62216c();
    }

    /* access modifiers changed from: protected */
    public long getFallbackAdCacheTtl() {
        return C5081d.m3532a().mo62488b().getReturnAdCacheTTL();
    }
}
