package com.startapp.android.publish.ads.splash;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.startapp.android.publish.ads.splash.SplashConfig.MaxAdDisplayTime;
import com.startapp.android.publish.ads.splash.SplashConfig.Orientation;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.cache.C5080c;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.ads.splash.h */
/* compiled from: StartAppSDK */
public class C4836h {

    /* renamed from: a */
    Activity f2716a;

    /* renamed from: b */
    C4825c f2717b;

    /* renamed from: c */
    C5080c f2718c;

    /* renamed from: d */
    C4831d f2719d = null;

    /* renamed from: e */
    boolean f2720e = false;

    /* renamed from: f */
    C4846a f2721f;

    /* renamed from: g */
    Runnable f2722g = new Runnable() {
        public void run() {
            C4836h.this.f2717b.mo61585a(C4836h.this.f2719d, (C4833e) new C4833e() {
                /* renamed from: a */
                public void mo61603a() {
                    if (!C4836h.this.f2720e && C4836h.this.f2721f != null) {
                        C5155g.m3807a("Splash", 4, "Displaying Splash ad");
                        C4836h.this.f2721f.showAd((AdDisplayListener) new AdDisplayListener() {
                            public void adNotDisplayed(C4925Ad ad) {
                            }

                            public void adHidden(C4925Ad ad) {
                                C4836h.this.f2717b.mo61591c();
                            }

                            public void adDisplayed(C4925Ad ad) {
                                C4836h.this.f2717b.mo61593d();
                            }

                            public void adClicked(C4925Ad ad) {
                                C4836h.this.f2717b.mo61598i();
                            }
                        });
                        C4836h.this.mo61619f();
                        C4836h.this.f2716a.finish();
                    }
                }
            });
        }
    };

    /* renamed from: h */
    private SplashConfig f2723h;

    /* renamed from: i */
    private Handler f2724i = new Handler();

    /* renamed from: j */
    private AdPreferences f2725j;

    /* renamed from: k */
    private Runnable f2726k = new Runnable() {
        public void run() {
            if (C4836h.this.mo61616c()) {
                C4836h.this.mo61617d();
                C4836h.this.mo61618e();
                return;
            }
            C4836h.this.f2716a.finish();
        }
    };

    /* renamed from: l */
    private AdEventListener f2727l = new AdEventListener() {
        public void onReceiveAd(C4925Ad ad) {
            C5155g.m3807a("Splash", 4, "Splash ad received");
            C4836h.this.f2717b.mo61587a(C4836h.this.f2722g);
        }

        public void onFailedToReceiveAd(C4925Ad ad) {
            if (C4836h.this.f2721f != null) {
                C4836h.this.f2717b.mo61589b();
            }
        }
    };

    /* renamed from: com.startapp.android.publish.ads.splash.h$7 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C48457 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2736a = new int[Orientation.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.startapp.android.publish.ads.splash.SplashConfig$Orientation[] r0 = com.startapp.android.publish.ads.splash.SplashConfig.Orientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2736a = r0
                int[] r0 = f2736a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.ads.splash.SplashConfig$Orientation r1 = com.startapp.android.publish.ads.splash.SplashConfig.Orientation.PORTRAIT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2736a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.ads.splash.SplashConfig$Orientation r1 = com.startapp.android.publish.ads.splash.SplashConfig.Orientation.LANDSCAPE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.splash.C4836h.C48457.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.ads.splash.h$a */
    /* compiled from: StartAppSDK */
    private static class C4846a extends StartAppAd {
        private static final long serialVersionUID = 1;

        public C4846a(Context context) {
            super(context);
            this.placement = Placement.INAPP_SPLASH;
        }

        /* access modifiers changed from: protected */
        public C4958f shouldDisplayAd(String str, Placement placement) {
            return new C4958f(true);
        }
    }

    /* renamed from: a */
    public void mo61613a() {
    }

    public C4836h(Activity activity, SplashConfig splashConfig, AdPreferences adPreferences) {
        this.f2716a = activity;
        this.f2723h = splashConfig;
        this.f2725j = adPreferences;
        try {
            m2529h();
            this.f2717b = new C4825c(activity, this.f2719d);
        } catch (Exception e) {
            this.f2717b = new C4825c(activity);
            this.f2717b.mo61584a();
            this.f2717b.mo61589b();
            C5017f.m3256a(activity, C5015d.EXCEPTION, "SplashScreen.constructor - WebView failed", e.getMessage(), "");
        }
    }

    /* renamed from: h */
    private void m2529h() {
        this.f2723h.initSplashLogo(this.f2716a);
        if (!m2532k()) {
            this.f2719d = this.f2723h.initSplashHtml(this.f2716a);
        }
    }

    /* renamed from: a */
    public void mo61614a(Bundle bundle) {
        String str = "Splash";
        C5155g.m3807a(str, 4, "========= Splash Screen Feature =========");
        this.f2717b.mo61599j();
        if (!m2530i()) {
            this.f2724i.post(this.f2726k);
            return;
        }
        this.f2724i.postDelayed(this.f2726k, 100);
        C5155g.m3807a(str, 4, "Splash screen orientation is being modified");
    }

    /* renamed from: b */
    public void mo61615b() {
        this.f2724i.removeCallbacks(this.f2726k);
        this.f2717b.mo61594e();
    }

    /* renamed from: i */
    private boolean m2530i() {
        int i = this.f2716a.getResources().getConfiguration().orientation;
        if (this.f2723h.getOrientation() == Orientation.AUTO) {
            if (i == 2) {
                this.f2723h.setOrientation(Orientation.LANDSCAPE);
            } else {
                this.f2723h.setOrientation(Orientation.PORTRAIT);
            }
        }
        int i2 = C48457.f2736a[this.f2723h.getOrientation().ordinal()];
        boolean z = false;
        if (i2 == 1) {
            if (i == 2) {
                z = true;
            }
            C5146c.m3749a(this.f2716a);
        } else if (i2 == 2) {
            if (i == 1) {
                z = true;
            }
            C5146c.m3767b(this.f2716a);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Set Orientation: [");
        sb.append(this.f2723h.getOrientation().toString());
        sb.append("]");
        C5155g.m3807a("Splash", 4, sb.toString());
        return z;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public boolean mo61616c() {
        C5155g.m3807a("Splash", 4, "Displaying Splash screen");
        if (this.f2723h.validate(this.f2716a)) {
            View j = m2531j();
            if (j == null) {
                return false;
            }
            this.f2716a.setContentView(j, new LayoutParams(-1, -1));
            return true;
        }
        throw new IllegalArgumentException(this.f2723h.getErrorMessage());
    }

    /* renamed from: j */
    private View m2531j() {
        if (m2532k()) {
            return this.f2723h.getLayout(this.f2716a);
        }
        C4831d dVar = this.f2719d;
        if (dVar != null) {
            return dVar.mo61610c();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public void mo61617d() {
        C5155g.m3807a("Splash", 4, "Loading Splash Ad");
        this.f2721f = new C4846a(this.f2716a.getApplicationContext());
        this.f2718c = this.f2721f.loadSplash(this.f2725j, this.f2727l);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public void mo61618e() {
        C5155g.m3807a("Splash", 4, "Started Splash Loading Timer");
        this.f2724i.postDelayed(new Runnable() {
            public void run() {
                if (C4836h.this.f2717b.mo61590b(C4836h.this.f2722g, C4836h.this.f2718c)) {
                    C4836h hVar = C4836h.this;
                    hVar.f2721f = null;
                    hVar.f2718c = null;
                }
            }
        }, this.f2723h.getMaxLoadAdTimeout().longValue());
        this.f2724i.postDelayed(new Runnable() {
            public void run() {
                C4836h.this.f2717b.mo61588a(C4836h.this.f2722g, C4836h.this.f2718c);
            }
        }, this.f2723h.getMinSplashTime().getIndex());
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: f */
    public void mo61619f() {
        C5155g.m3807a("Splash", 4, "Started Splash Display Timer");
        if (this.f2723h.getMaxAdDisplayTime() != MaxAdDisplayTime.FOR_EVER) {
            this.f2724i.postDelayed(new Runnable() {
                public void run() {
                    C4836h.this.f2717b.mo61586a((StartAppAd) C4836h.this.f2721f);
                }
            }, this.f2723h.getMaxAdDisplayTime().getIndex());
        }
    }

    /* renamed from: g */
    public void mo61620g() {
        this.f2720e = true;
        this.f2717b.mo61597h();
    }

    /* renamed from: k */
    private boolean m2532k() {
        return !this.f2723h.isHtmlSplash() || this.f2723h.isUserDefinedSplash();
    }
}
