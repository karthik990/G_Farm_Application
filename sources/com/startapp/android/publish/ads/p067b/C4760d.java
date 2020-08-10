package com.startapp.android.publish.ads.p067b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.b.d */
/* compiled from: StartAppSDK */
public class C4760d extends C4759c {
    private static final long serialVersionUID = 1;

    public C4760d(Context context) {
        super(context, Placement.INAPP_OVERLAY);
    }

    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        new C4757a(this.context, this, adPreferences, adEventListener).mo62216c();
    }
}
