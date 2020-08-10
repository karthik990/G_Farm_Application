package com.startapp.android.publish.ads.p066a;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import com.mobiroller.constants.Constants;
import com.startapp.android.publish.ads.splash.C4835g;
import com.startapp.android.publish.ads.video.C4894f;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adinformation.C4969b;
import com.startapp.android.publish.adsCommon.adinformation.C4969b.C4977b;
import com.startapp.android.publish.adsCommon.adinformation.C4978c;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.inappbrowser.C5127a;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.ads.a.b */
/* compiled from: StartAppSDK */
public abstract class C4736b {

    /* renamed from: a */
    protected C4969b f2466a = null;

    /* renamed from: b */
    protected Placement f2467b;

    /* renamed from: c */
    protected boolean f2468c = false;

    /* renamed from: d */
    private Intent f2469d;

    /* renamed from: e */
    private Activity f2470e;

    /* renamed from: f */
    private BroadcastReceiver f2471f = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            C4736b.this.mo61177p();
        }
    };

    /* renamed from: g */
    private String[] f2472g;

    /* renamed from: h */
    private boolean[] f2473h;

    /* renamed from: i */
    private boolean[] f2474i = {true};

    /* renamed from: j */
    private String f2475j;

    /* renamed from: k */
    private String[] f2476k;

    /* renamed from: l */
    private String[] f2477l;

    /* renamed from: m */
    private String[] f2478m;

    /* renamed from: n */
    private C4925Ad f2479n;

    /* renamed from: o */
    private String f2480o;

    /* renamed from: p */
    private boolean f2481p;

    /* renamed from: q */
    private C4978c f2482q;

    /* renamed from: r */
    private String f2483r;

    /* renamed from: s */
    private Long f2484s;

    /* renamed from: t */
    private Boolean[] f2485t = null;

    /* renamed from: a */
    public void mo61150a(Configuration configuration) {
    }

    /* renamed from: a */
    public boolean mo61158a(int i, KeyEvent keyEvent) {
        return false;
    }

    /* renamed from: b */
    public void mo61161b(Bundle bundle) {
    }

    /* renamed from: c */
    public void mo61164c(Bundle bundle) {
    }

    /* renamed from: r */
    public boolean mo61179r() {
        return false;
    }

    /* renamed from: t */
    public void mo61181t() {
    }

    /* renamed from: u */
    public abstract void mo61182u();

    /* renamed from: a */
    public static C4736b m2181a(Activity activity, Intent intent, Placement placement) {
        C4736b bVar = null;
        switch (placement) {
            case INAPP_OFFER_WALL:
                if (C4946i.m2923a(128) || C4946i.m2923a(64)) {
                    bVar = new C4754e();
                    break;
                }
            case INAPP_RETURN:
            case INAPP_OVERLAY:
                if (!C4946i.m2923a(4) || !intent.getBooleanExtra("videoAd", false)) {
                    if (!intent.getBooleanExtra("mraidAd", false)) {
                        bVar = new C4755f();
                        break;
                    } else {
                        bVar = new C4748d();
                        break;
                    }
                } else {
                    bVar = new C4894f();
                    break;
                }
                break;
            case INAPP_SPLASH:
                if (C4946i.m2923a(8)) {
                    bVar = new C4835g();
                    break;
                }
                break;
            case INAPP_FULL_SCREEN:
            case INAPP_BROWSER:
                if (C4946i.m2923a(256)) {
                    Uri data = intent.getData();
                    if (data != null) {
                        bVar = new C5127a(data.toString());
                        break;
                    } else {
                        return null;
                    }
                }
                break;
            default:
                bVar = new C4735a();
                break;
        }
        bVar.m2183a(intent);
        bVar.m2182a(activity);
        bVar.m2191c(intent.getStringExtra(Constants.KEY_RSS_POSITION));
        bVar.m2190b(intent.getStringArrayExtra("tracking"));
        bVar.m2192c(intent.getStringArrayExtra("trackingClickUrl"));
        bVar.m2193d(intent.getStringArrayExtra("packageNames"));
        String str = "closingUrl";
        bVar.m2188a(intent.getStringArrayExtra(str));
        bVar.mo61156a(intent.getBooleanArrayExtra("smartRedirect"));
        bVar.mo61162b(intent.getBooleanArrayExtra("browserEnabled"));
        String stringExtra = intent.getStringExtra("htmlUuid");
        if (stringExtra != null) {
            if (AdsConstants.OVERRIDE_NETWORK.booleanValue()) {
                bVar.mo61154a(C5071a.m3485a().mo62466b(stringExtra));
            } else {
                bVar.mo61154a(C5071a.m3485a().mo62471c(stringExtra));
            }
        }
        bVar.mo61794a(intent.getBooleanExtra("isSplash", false));
        bVar.m2184a((C4978c) intent.getSerializableExtra("adInfoOverride"));
        bVar.mo61197b(intent.getStringExtra("adTag"));
        bVar.m2185a(placement);
        bVar.m2188a(intent.getStringArrayExtra(str));
        if (bVar.mo61165d() == null) {
            bVar.mo61156a(new boolean[]{true});
        }
        if (bVar.mo61166e() == null) {
            bVar.mo61162b(new boolean[]{true});
        }
        bVar.mo61153a((C4925Ad) intent.getSerializableExtra("ad"));
        long longExtra = intent.getLongExtra("delayImpressionSeconds", -1);
        if (longExtra != -1) {
            bVar.m2186a(Long.valueOf(longExtra));
        }
        bVar.mo61155a((Boolean[]) intent.getSerializableExtra("sendRedirectHops"));
        StringBuilder sb = new StringBuilder();
        sb.append("Placement=[");
        sb.append(bVar.mo61172k());
        sb.append("]");
        C5155g.m3807a("GenericMode", 3, sb.toString());
        return bVar;
    }

    /* renamed from: a */
    private void m2188a(String[] strArr) {
        this.f2472g = strArr;
    }

    /* renamed from: a */
    private void m2183a(Intent intent) {
        this.f2469d = intent;
    }

    /* renamed from: a */
    private void m2185a(Placement placement) {
        this.f2467b = placement;
    }

    /* renamed from: a */
    private void mo61794a(boolean z) {
        this.f2481p = z;
    }

    /* renamed from: b */
    private void mo61197b(String str) {
        this.f2483r = str;
    }

    /* renamed from: a */
    public Intent mo61149a() {
        return this.f2469d;
    }

    /* renamed from: a */
    private void m2182a(Activity activity) {
        this.f2470e = activity;
    }

    /* renamed from: a */
    private void m2184a(C4978c cVar) {
        this.f2482q = cVar;
    }

    /* renamed from: b */
    public Activity mo61159b() {
        return this.f2470e;
    }

    /* renamed from: c */
    public void mo61163c() {
        this.f2468c = true;
    }

    /* renamed from: c */
    private void m2191c(String str) {
        this.f2475j = str;
    }

    /* renamed from: b */
    private void m2190b(String[] strArr) {
        this.f2476k = strArr;
    }

    /* renamed from: c */
    private void m2192c(String[] strArr) {
        this.f2477l = strArr;
    }

    /* renamed from: d */
    private void m2193d(String[] strArr) {
        this.f2478m = strArr;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61154a(String str) {
        this.f2480o = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61156a(boolean[] zArr) {
        this.f2473h = zArr;
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public boolean[] mo61165d() {
        return this.f2473h;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo61162b(boolean[] zArr) {
        this.f2474i = zArr;
    }

    /* renamed from: e */
    public boolean[] mo61166e() {
        return this.f2474i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61157a(int i) {
        boolean[] zArr = this.f2474i;
        if (zArr == null || i < 0 || i >= zArr.length) {
            return true;
        }
        return zArr[i];
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public String mo61167f() {
        return this.f2480o;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public String mo61168g() {
        return this.f2475j;
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public String[] mo61169h() {
        return this.f2476k;
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public String[] mo61170i() {
        return this.f2477l;
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public String[] mo61171j() {
        return this.f2478m;
    }

    /* access modifiers changed from: protected */
    /* renamed from: k */
    public Placement mo61172k() {
        return this.f2467b;
    }

    /* access modifiers changed from: protected */
    /* renamed from: l */
    public String[] mo61173l() {
        return this.f2472g;
    }

    /* access modifiers changed from: protected */
    /* renamed from: m */
    public C4978c mo61174m() {
        return this.f2482q;
    }

    /* access modifiers changed from: protected */
    /* renamed from: n */
    public String mo61175n() {
        return this.f2483r;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61152a(RelativeLayout relativeLayout) {
        this.f2466a = new C4969b(mo61159b(), C4977b.LARGE, mo61172k(), mo61174m());
        this.f2466a.mo62113a(relativeLayout);
    }

    /* renamed from: o */
    public Long mo61176o() {
        return this.f2484s;
    }

    /* renamed from: a */
    private void m2186a(Long l) {
        this.f2484s = l;
    }

    /* renamed from: b */
    public Boolean mo61160b(int i) {
        Boolean[] boolArr = this.f2485t;
        if (boolArr == null || i < 0 || i >= boolArr.length) {
            return null;
        }
        return boolArr[i];
    }

    /* renamed from: a */
    public void mo61155a(Boolean[] boolArr) {
        this.f2485t = boolArr;
    }

    /* renamed from: p */
    public void mo61177p() {
        mo61159b().runOnUiThread(new Runnable() {
            public void run() {
                C4736b.this.mo61159b().finish();
            }
        });
    }

    /* renamed from: q */
    public void mo61178q() {
        C5160b.m3831a((Context) mo61159b()).mo62880a(new Intent("com.startapp.android.HideDisplayBroadcastListener"));
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        C5160b.m3831a((Context) mo61159b()).mo62879a(this.f2471f, new IntentFilter("com.startapp.android.CloseAdActivity"));
    }

    /* renamed from: s */
    public void mo61180s() {
        mo61177p();
    }

    /* renamed from: v */
    public void mo61183v() {
        if (this.f2471f != null) {
            C5160b.m3831a((Context) mo61159b()).mo62878a(this.f2471f);
        }
        this.f2471f = null;
    }

    /* renamed from: w */
    public C4925Ad mo61184w() {
        return this.f2479n;
    }

    /* renamed from: a */
    public void mo61153a(C4925Ad ad) {
        this.f2479n = ad;
    }
}
