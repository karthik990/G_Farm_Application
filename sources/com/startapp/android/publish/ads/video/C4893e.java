package com.startapp.android.publish.ads.video;

import android.content.Context;
import com.startapp.android.publish.ads.p067b.C4759c;
import com.startapp.android.publish.ads.splash.SplashConfig.Orientation;
import com.startapp.android.publish.ads.video.p073c.p074a.C4886e;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4876b;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.p042c.C5180b;

/* renamed from: com.startapp.android.publish.ads.video.e */
/* compiled from: StartAppSDK */
public class C4893e extends C4759c {
    private static final long serialVersionUID = 1;
    private VideoAdDetails videoAdDetails = null;

    public C4893e(Context context) {
        super(context, Placement.INAPP_OVERLAY);
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        new C4852b(this.context, this, adPreferences, adEventListener).mo62216c();
    }

    /* renamed from: b */
    public void mo61769b(String str) {
        super.mo61769b(str);
        m2739f(mo62230a(str, "@videoJson@"));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61220a() {
        return this.videoAdDetails != null;
    }

    /* renamed from: d */
    public VideoAdDetails mo61770d() {
        return this.videoAdDetails;
    }

    /* renamed from: a */
    public void mo61768a(C4886e eVar, boolean z) {
        if (eVar != null) {
            this.videoAdDetails = new VideoAdDetails(eVar, z);
            C4876b g = eVar.mo61759g();
            if (g == null) {
                return;
            }
            if (g.mo61728d().intValue() > g.mo61730e().intValue()) {
                mo62232a(Orientation.LANDSCAPE);
            } else {
                mo62232a(Orientation.PORTRAIT);
            }
        }
    }

    /* renamed from: e */
    public void mo61771e() {
        this.videoAdDetails = null;
    }

    /* renamed from: f */
    private void m2739f(String str) {
        if (str != null) {
            this.videoAdDetails = (VideoAdDetails) C5180b.m3908a(str, VideoAdDetails.class);
        }
    }
}
