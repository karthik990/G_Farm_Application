package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.startapp.android.publish.adsCommon.StartAppAd.AdMode;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.adsCommon.f */
/* compiled from: StartAppSDK */
public class C5008f {

    /* renamed from: a */
    protected StartAppAd f3210a;

    /* renamed from: b */
    private boolean f3211b;

    /* renamed from: c */
    private AutoInterstitialPreferences f3212c;

    /* renamed from: d */
    private long f3213d;

    /* renamed from: e */
    private int f3214e;

    /* renamed from: com.startapp.android.publish.adsCommon.f$a */
    /* compiled from: StartAppSDK */
    private static class C5010a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C5008f f3216a = new C5008f();
    }

    private C5008f() {
        this.f3211b = false;
        this.f3212c = null;
        this.f3213d = -1;
        this.f3214e = -1;
        this.f3210a = null;
    }

    /* renamed from: a */
    public static C5008f m3205a() {
        return C5010a.f3216a;
    }

    /* renamed from: b */
    public void mo62261b() {
        this.f3211b = true;
    }

    /* renamed from: c */
    public void mo62262c() {
        this.f3211b = false;
    }

    /* renamed from: a */
    public void mo62260a(AutoInterstitialPreferences autoInterstitialPreferences) {
        this.f3212c = autoInterstitialPreferences;
        this.f3213d = -1;
        this.f3214e = -1;
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public void mo62263d() {
        this.f3213d = System.currentTimeMillis();
        this.f3214e = 0;
    }

    /* renamed from: e */
    private boolean m3208e() {
        return this.f3211b && C4983b.m3032a().mo62155I();
    }

    /* renamed from: f */
    private boolean m3209f() {
        if (this.f3212c == null) {
            this.f3212c = new AutoInterstitialPreferences();
        }
        boolean z = this.f3213d <= 0 || System.currentTimeMillis() >= this.f3213d + ((long) (this.f3212c.getSecondsBetweenAds() * 1000));
        int i = this.f3214e;
        boolean z2 = i <= 0 || i >= this.f3212c.getActivitiesBetweenAds();
        if (!z || !z2) {
            return false;
        }
        return true;
    }

    /* renamed from: a */
    private boolean m3206a(Activity activity) {
        String name = activity.getClass().getName();
        StringBuilder sb = new StringBuilder();
        String str = "com.startapp.android.publish.";
        sb.append(str);
        sb.append("adsCommon.activities.OverlayActivity");
        if (!name.startsWith(sb.toString())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("adsCommon.activities.FullScreenActivity");
            if (!name.startsWith(sb2.toString())) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append("ads.list3d.List3DActivity");
                if (!name.startsWith(sb3.toString())) {
                    return false;
                }
            }
        }
        return true;
    }

    /* renamed from: b */
    private boolean m3207b(Activity activity) {
        return activity.getClass().getName().equals(C5059m.m3378a().mo62400g());
    }

    /* renamed from: a */
    public void mo62259a(Context context) {
        if (m3208e() && m3209f()) {
            if (this.f3210a == null) {
                this.f3210a = new StartAppAd(context);
            }
            this.f3210a.loadAd(AdMode.AUTOMATIC, new AdPreferences().setAi(Boolean.valueOf(true)), new AdEventListener() {
                public void onReceiveAd(C4925Ad ad) {
                    if (C5008f.this.f3210a.showAd()) {
                        C5155g.m3807a("InterActivityAdManager", 3, "ShowInterActivityAd");
                        C5008f.this.mo62263d();
                    }
                }

                public void onFailedToReceiveAd(C4925Ad ad) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("FailedToShowInterActivityAd, error: [");
                    sb.append(ad.errorMessage);
                    sb.append("]");
                    C5155g.m3807a("InterActivityAdManager", 3, sb.toString());
                }
            });
        }
    }

    /* renamed from: a */
    public void mo62258a(Activity activity, Bundle bundle) {
        if (bundle == null && !m3206a(activity) && !m3207b(activity)) {
            this.f3214e++;
            mo62259a((Context) activity);
        }
    }
}
