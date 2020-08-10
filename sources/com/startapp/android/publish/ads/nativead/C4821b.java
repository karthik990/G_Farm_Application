package com.startapp.android.publish.ads.nativead;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5033h;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.nativead.b */
/* compiled from: StartAppSDK */
public class C4821b extends C5033h {
    private static final long serialVersionUID = 1;
    private NativeAdPreferences config;

    public C4821b(Context context, NativeAdPreferences nativeAdPreferences) {
        super(context, Placement.INAPP_NATIVE);
        this.config = nativeAdPreferences;
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        C4820a aVar = new C4820a(this.context, this, adPreferences, adEventListener, this.config);
        aVar.mo62216c();
    }
}
