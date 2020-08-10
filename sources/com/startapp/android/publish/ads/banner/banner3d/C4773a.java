package com.startapp.android.publish.ads.banner.banner3d;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5033h;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.banner.banner3d.a */
/* compiled from: StartAppSDK */
public class C4773a extends C5033h {
    private static final long serialVersionUID = 1;
    private boolean fixedSize;
    private int offset;

    public C4773a(Context context, int i) {
        super(context, Placement.INAPP_BANNER);
        this.offset = i;
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        C4778c cVar = new C4778c(this.context, this, this.offset, adPreferences, adEventListener);
        cVar.mo62216c();
        this.offset++;
    }

    /* renamed from: a */
    public int mo61342a() {
        return this.offset;
    }

    /* renamed from: a */
    public void mo61343a(boolean z) {
        this.fixedSize = z;
    }

    /* renamed from: b */
    public boolean mo61344b() {
        return this.fixedSize;
    }
}
