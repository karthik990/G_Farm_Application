package com.startapp.android.publish.ads.splash;

import android.content.Context;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.html.C5124a;

/* renamed from: com.startapp.android.publish.ads.splash.a */
/* compiled from: StartAppSDK */
public class C4823a extends C5124a {
    public C4823a(Context context, C4824b bVar, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, bVar, adPreferences, adEventListener, Placement.INAPP_SPLASH, true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        mo62804a(bool.booleanValue());
    }
}
