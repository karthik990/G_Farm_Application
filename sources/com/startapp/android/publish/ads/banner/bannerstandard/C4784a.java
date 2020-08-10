package com.startapp.android.publish.ads.banner.bannerstandard;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.banner.bannerstandard.a */
/* compiled from: StartAppSDK */
public class C4784a extends C5003e {
    private static final long serialVersionUID = 1;
    private int bannerType;
    private boolean fixedSize;
    private int offset = 0;

    public C4784a(Context context, int i) {
        super(context, Placement.INAPP_BANNER);
        this.offset = i;
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        C4785b bVar = new C4785b(this.context, this, this.offset, adPreferences, adEventListener);
        bVar.mo62216c();
        this.offset++;
    }

    /* renamed from: a */
    public int mo61220a() {
        return this.offset;
    }

    /* renamed from: a */
    public void mo61366a(boolean z) {
        this.fixedSize = z;
    }

    /* renamed from: b */
    public boolean mo61367b() {
        return this.fixedSize;
    }

    /* renamed from: c */
    public int mo61368c() {
        return this.bannerType;
    }

    /* renamed from: a */
    public void mo61365a(int i) {
        this.bannerType = i;
    }
}
