package com.startapp.android.publish.ads.splash;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p077a.C4959g;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.cache.C5080c;
import com.startapp.android.publish.common.metaData.C5116d;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5160b;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5159i;

/* renamed from: com.startapp.android.publish.ads.splash.c */
/* compiled from: StartAppSDK */
public class C4825c {

    /* renamed from: a */
    Activity f2684a;

    /* renamed from: b */
    boolean f2685b;

    /* renamed from: c */
    C4830a f2686c;

    /* renamed from: d */
    private boolean f2687d;

    /* renamed from: e */
    private boolean f2688e;

    /* renamed from: f */
    private boolean f2689f;

    /* renamed from: g */
    private boolean f2690g;

    /* renamed from: h */
    private boolean f2691h;

    /* renamed from: i */
    private C4831d f2692i;

    /* renamed from: j */
    private BroadcastReceiver f2693j;

    /* renamed from: com.startapp.android.publish.ads.splash.c$a */
    /* compiled from: StartAppSDK */
    enum C4830a {
        LOADING,
        RECEIVED,
        DISPLAYED,
        HIDDEN,
        DO_NOT_DISPLAY
    }

    public C4825c(Activity activity) {
        this.f2687d = false;
        this.f2688e = true;
        this.f2689f = false;
        this.f2690g = false;
        this.f2691h = false;
        this.f2685b = false;
        this.f2686c = C4830a.LOADING;
        this.f2692i = null;
        this.f2693j = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                C4825c.this.mo61598i();
            }
        };
        this.f2684a = activity;
    }

    public C4825c(Activity activity, C4831d dVar) {
        this(activity);
        this.f2692i = dVar;
    }

    /* renamed from: a */
    public void mo61588a(final Runnable runnable, final C5080c cVar) {
        C5155g.m3807a("Splash", 4, "Minimum splash screen time has passed");
        this.f2687d = true;
        C48261 r0 = new C5116d() {

            /* renamed from: d */
            private Runnable f2697d = new Runnable() {
                public void run() {
                    C4825c.this.f2685b = true;
                    if (C4825c.this.f2686c != C4830a.DO_NOT_DISPLAY) {
                        C4825c.this.mo61592c(runnable, cVar);
                    }
                }
            };

            /* renamed from: a */
            public void mo61600a() {
                C5155g.m3807a("Splash", 4, "MetaData received");
                C4825c.this.f2684a.runOnUiThread(this.f2697d);
            }

            /* renamed from: b */
            public void mo61601b() {
                C5155g.m3807a("Splash", 4, "MetaData failed to receive - proceeding with old MetaData");
                C4825c.this.f2684a.runOnUiThread(this.f2697d);
            }
        };
        if (this.f2686c != C4830a.DO_NOT_DISPLAY) {
            m2488a((C5116d) r0);
        } else {
            m2490k();
        }
    }

    /* renamed from: a */
    public void mo61584a() {
        this.f2687d = true;
    }

    /* renamed from: a */
    private void m2488a(C5116d dVar) {
        synchronized (MetaData.getLock()) {
            if (MetaData.getInstance().isReady()) {
                dVar.mo61600a();
            } else {
                MetaData.getInstance().addMetaDataListener(dVar);
            }
        }
    }

    /* renamed from: a */
    public void mo61587a(Runnable runnable) {
        C5155g.m3807a("Splash", 4, "Splash ad received");
        if (this.f2686c == C4830a.LOADING) {
            this.f2686c = C4830a.RECEIVED;
        }
        m2489b(runnable);
    }

    /* renamed from: b */
    public void mo61589b() {
        C5155g.m3807a("Splash", 4, "Error receiving Ad");
        this.f2686c = C4830a.DO_NOT_DISPLAY;
        m2489b(null);
    }

    /* renamed from: b */
    public boolean mo61590b(Runnable runnable, C5080c cVar) {
        if (!this.f2691h) {
            String str = "Splash";
            if (this.f2686c == C4830a.LOADING) {
                C5155g.m3807a(str, 4, "Splash Loading Timer Expired");
                this.f2688e = false;
                this.f2686c = C4830a.DO_NOT_DISPLAY;
                m2490k();
                return true;
            } else if (this.f2686c == C4830a.RECEIVED) {
                C5155g.m3807a(str, 4, "MetaData Loading Timer Expired - proceeding with old MetaData");
                this.f2685b = true;
                mo61592c(runnable, cVar);
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public void mo61592c(Runnable runnable, C5080c cVar) {
        C4958f a = C4959g.m2962a().mo62067b().mo62061a(Placement.INAPP_SPLASH, null);
        StringBuilder sb = new StringBuilder();
        sb.append("checkAdRulesAndShowAd: shouldDisplayAd ");
        sb.append(a.mo62064a());
        String str = "Splash";
        C5155g.m3807a(str, 4, sb.toString());
        if (a.mo62064a()) {
            C5155g.m3807a(str, 4, "checkAdRulesAndShowAd: showAd");
            m2489b(runnable);
            return;
        }
        C5155g.m3807a(str, 4, "Should not display splash ad");
        this.f2686c = C4830a.DO_NOT_DISPLAY;
        if (cVar != null) {
            C4988c.m3112a((Context) this.f2684a, C4988c.m3119a(C5071a.m3485a().mo62465b(cVar)), (String) null, a.mo62066c());
        }
        if (Constants.m3707a().booleanValue()) {
            C5159i.m3829a().mo62876a(this.f2684a, a.mo62065b());
        }
        m2490k();
    }

    /* renamed from: c */
    public void mo61591c() {
        C5155g.m3807a("Splash", 4, "Splash Screen has been hidden");
        this.f2686c = C4830a.HIDDEN;
        mo61596g();
        if (!this.f2684a.isFinishing()) {
            this.f2684a.finish();
        }
    }

    /* renamed from: d */
    public void mo61593d() {
        this.f2686c = C4830a.DISPLAYED;
    }

    /* renamed from: b */
    private void m2489b(Runnable runnable) {
        if (!this.f2687d) {
            return;
        }
        if (!this.f2685b && runnable != null) {
            return;
        }
        if (this.f2686c == C4830a.RECEIVED && runnable != null) {
            this.f2688e = false;
            runnable.run();
        } else if (this.f2686c != C4830a.LOADING) {
            m2490k();
        }
    }

    /* renamed from: a */
    public void mo61586a(StartAppAd startAppAd) {
        if (this.f2686c == C4830a.DISPLAYED) {
            String str = "Splash";
            C5155g.m3807a(str, 4, "Splash Ad Display Timeout");
            if (!this.f2690g) {
                C5155g.m3807a(str, 4, "Closing Splash Ad");
                startAppAd.close();
                mo61591c();
            }
        }
    }

    /* renamed from: e */
    public void mo61594e() {
        if (this.f2686c != C4830a.DISPLAYED && this.f2686c != C4830a.DO_NOT_DISPLAY) {
            this.f2686c = C4830a.DO_NOT_DISPLAY;
            if (this.f2688e) {
                mo61595f();
            }
        }
    }

    /* renamed from: f */
    public void mo61595f() {
        C5155g.m3807a("Splash", 4, "User Canceled Splash Screen");
        mo61596g();
    }

    /* renamed from: k */
    private void m2490k() {
        mo61585a(this.f2692i, (C4833e) new C4833e() {
            /* renamed from: a */
            public void mo61603a() {
                C5155g.m3807a("Splash", 4, "Loading Main Activity");
                C4825c.this.mo61596g();
                if (!C4825c.this.f2684a.isFinishing()) {
                    C4825c.this.f2684a.finish();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: g */
    public void mo61596g() {
        if (!this.f2689f) {
            this.f2689f = true;
            C5160b.m3831a((Context) this.f2684a).mo62880a(new Intent("com.startapp.android.splashHidden"));
        }
        if (this.f2693j != null) {
            try {
                Log.v("startapp", "unregistering receiver");
                C5160b.m3831a((Context) this.f2684a).mo62878a(this.f2693j);
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    /* renamed from: h */
    public void mo61597h() {
        this.f2691h = true;
    }

    /* renamed from: i */
    public void mo61598i() {
        this.f2690g = true;
    }

    /* renamed from: j */
    public void mo61599j() {
        C5160b.m3831a((Context) this.f2684a).mo62879a(this.f2693j, new IntentFilter("com.startapp.android.adInfoWasClickedBroadcastListener"));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61585a(C4831d dVar, C4833e eVar) {
        if (dVar == null) {
            eVar.mo61603a();
            return;
        }
        dVar.mo61607a(eVar);
        dVar.mo61608b();
    }
}
