package com.startapp.android.publish.ads.p067b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.html.C5124a;

/* renamed from: com.startapp.android.publish.ads.b.a */
/* compiled from: StartAppSDK */
public class C4757a extends C5124a {
    public C4757a(Context context, C5003e eVar, AdPreferences adPreferences, AdEventListener adEventListener) {
        super(context, eVar, adPreferences, adEventListener, Placement.INAPP_OVERLAY, true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        mo62804a(bool.booleanValue());
    }
}
