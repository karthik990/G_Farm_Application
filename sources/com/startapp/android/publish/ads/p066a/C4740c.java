package com.startapp.android.publish.ads.p066a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.core.view.ViewCompat;
import com.p021b.p022a.p023a.p024a.p026b.C0961a;
import com.p021b.p022a.p023a.p024a.p026b.C0963b;
import com.startapp.android.publish.ads.p067b.C4759c;
import com.startapp.android.publish.ads.splash.C4824b;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5040i;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p080d.C5001a;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.html.JsInterface;
import com.startapp.android.publish.omsdk.C5132a;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.ads.a.c */
/* compiled from: StartAppSDK */
public class C4740c extends C4736b {

    /* renamed from: d */
    protected WebView f2489d;

    /* renamed from: e */
    protected C0963b f2490e;

    /* renamed from: f */
    protected RelativeLayout f2491f;

    /* renamed from: g */
    protected Runnable f2492g = new Runnable() {
        public void run() {
            C4740c.this.mo61188B();
            C4740c.this.mo61177p();
        }
    };

    /* renamed from: h */
    protected Runnable f2493h = new Runnable() {
        public void run() {
            C4740c.this.f2499n = true;
            C4740c cVar = C4740c.this;
            cVar.mo61196b(cVar.f2489d);
        }
    };

    /* renamed from: i */
    private Long f2494i;

    /* renamed from: j */
    private Long f2495j;

    /* renamed from: k */
    private long f2496k = 0;

    /* renamed from: l */
    private long f2497l = 0;

    /* renamed from: m */
    private C5040i f2498m;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public boolean f2499n = true;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public boolean f2500o = false;

    /* renamed from: com.startapp.android.publish.ads.a.c$a */
    /* compiled from: StartAppSDK */
    class C4747a extends WebViewClient {
        C4747a() {
        }

        public void onPageFinished(WebView webView, String str) {
            C4740c.this.mo61198c(webView);
            C4740c cVar = C4740c.this;
            cVar.mo61194a("gClientInterface.setMode", cVar.mo61168g());
            C4740c.this.mo61194a("enableScheme", "externalLinks");
            C4740c.this.mo61187A();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("MyWebViewClient::shouldOverrideUrlLoading - [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(2, sb.toString());
            if (!C4740c.this.f2500o || C4740c.this.f2499n) {
                return C4740c.this.mo61195a(str, false);
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void mo61198c(WebView webView) {
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        super.mo61151a(bundle);
        String str = "adCacheTtl";
        String str2 = "lastLoadTime";
        if (bundle == null) {
            if (mo61149a().hasExtra(str2)) {
                this.f2494i = (Long) mo61149a().getSerializableExtra(str2);
            }
            if (mo61149a().hasExtra(str)) {
                this.f2495j = (Long) mo61149a().getSerializableExtra(str);
                return;
            }
            return;
        }
        String str3 = "postrollHtml";
        if (bundle.containsKey(str3)) {
            mo61154a(bundle.getString(str3));
        }
        if (bundle.containsKey(str2)) {
            this.f2494i = (Long) bundle.getSerializable(str2);
        }
        if (bundle.containsKey(str)) {
            this.f2495j = (Long) bundle.getSerializable(str);
        }
    }

    /* renamed from: b */
    public void mo61161b(Bundle bundle) {
        super.mo61161b(bundle);
        if (mo61167f() != null) {
            bundle.putString("postrollHtml", mo61167f());
        }
        Long l = this.f2494i;
        if (l != null) {
            bundle.putLong("lastLoadTime", l.longValue());
        }
        Long l2 = this.f2495j;
        if (l2 != null) {
            bundle.putLong("adCacheTtl", l2.longValue());
        }
    }

    /* renamed from: u */
    public void mo61182u() {
        String str = "@jsTag@";
        if (mo61772G()) {
            C5155g.m3807a("InterstitialMode", 3, "Ad Cache TTL passed, finishing");
            mo61177p();
            return;
        }
        C5059m.m3378a().mo62377a(true);
        if (this.f2498m == null) {
            C5040i iVar = new C5040i(mo61159b(), mo61169h(), mo61190D(), mo61192F());
            this.f2498m = iVar;
        }
        WebView webView = this.f2489d;
        if (webView == null) {
            this.f2491f = new RelativeLayout(mo61159b());
            this.f2491f.setContentDescription("StartApp Ad");
            this.f2491f.setId(AdsConstants.STARTAPP_AD_MAIN_LAYOUT_ID);
            mo61159b().setContentView(this.f2491f);
            try {
                this.f2489d = new WebView(mo61159b().getApplicationContext());
                this.f2489d.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                mo61159b().getWindow().getDecorView().findViewById(16908290).setBackgroundColor(7829367);
                this.f2489d.setVerticalScrollBarEnabled(false);
                this.f2489d.setHorizontalScrollBarEnabled(false);
                this.f2489d.getSettings().setJavaScriptEnabled(true);
                C5146c.m3756a(this.f2489d);
                if (this.f2468c) {
                    C5146c.m3757a(this.f2489d, (Paint) null);
                }
                this.f2489d.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        return true;
                    }
                });
                this.f2489d.setLongClickable(false);
                this.f2489d.addJavascriptInterface(mo61200y(), "startappwall");
                mo61201z();
                mo61193a(this.f2489d);
                C4946i.m2912a((Context) mo61159b(), this.f2489d, mo61167f());
                this.f2500o = "true".equals(C4946i.m2908a(mo61167f(), str, str));
                mo61199x();
                this.f2491f.addView(this.f2489d, new LayoutParams(-1, -1));
                mo61152a(this.f2491f);
            } catch (Exception e) {
                C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "InterstitialMode.onResume - WebView failed", e.getMessage(), "");
                mo61177p();
            }
        } else {
            C5146c.m3770c(webView);
            this.f2498m.mo62344a();
        }
        this.f2496k = System.currentTimeMillis();
    }

    /* renamed from: v */
    public void mo61183v() {
        super.mo61183v();
        C0963b bVar = this.f2490e;
        if (bVar != null) {
            bVar.mo11477b();
            this.f2490e = null;
        }
        C4946i.m2921a((Object) this.f2489d, 1000);
    }

    /* access modifiers changed from: protected */
    /* renamed from: x */
    public void mo61199x() {
        this.f2489d.setWebViewClient(new C4747a());
        this.f2489d.setWebChromeClient(new WebChromeClient());
    }

    /* renamed from: G */
    private boolean mo61772G() {
        if (mo61184w() instanceof C4759c) {
            return ((C4759c) mo61184w()).hasAdCacheTtlPassed();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: y */
    public JsInterface mo61200y() {
        Activity b = mo61159b();
        Runnable runnable = this.f2492g;
        JsInterface jsInterface = new JsInterface(b, runnable, runnable, this.f2493h, mo61189C(), mo61157a(0));
        return jsInterface;
    }

    /* access modifiers changed from: protected */
    /* renamed from: z */
    public void mo61201z() {
        this.f2498m.mo62344a();
    }

    /* renamed from: a */
    public void mo61193a(WebView webView) {
        this.f2499n = false;
        webView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                C4740c.this.f2499n = true;
                if (motionEvent.getAction() == 2) {
                    return true;
                }
                return false;
            }
        });
    }

    /* renamed from: b */
    public void mo61196b(WebView webView) {
        if (webView != null) {
            webView.setOnTouchListener(null);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: A */
    public void mo61187A() {
        if (MetaData.getInstance().isOmsdkEnabled() && this.f2490e == null) {
            this.f2490e = C5132a.m3700a(this.f2489d);
            if (this.f2490e != null && this.f2489d != null) {
                if (this.f2466a != null) {
                    View a = this.f2466a.mo62112a();
                    if (a != null) {
                        this.f2490e.mo11478b(a);
                    }
                }
                this.f2490e.mo11476a(this.f2489d);
                this.f2490e.mo11475a();
                C0961a.m103a(this.f2490e).mo11464a();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61194a(String str, Object... objArr) {
        C4946i.m2917a(this.f2489d, str, objArr);
    }

    /* renamed from: b */
    private boolean m2236b(C4925Ad ad) {
        return C4946i.m2923a(8) && (ad instanceof C4824b);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61195a(String str, boolean z) {
        this.f2498m.mo62345a(true);
        boolean z2 = C4988c.m3118a(mo61159b().getApplicationContext(), this.f2467b) && !m2236b(mo61184w());
        if (mo61197b(str)) {
            try {
                int a = C4988c.m3093a(str);
                if (!mo61165d()[a] || z2) {
                    C5155g.m3805a(6, "forceExternal - interMode - redirect");
                    m2234b(str, a, z);
                } else {
                    C5155g.m3805a(6, "forceExternal -interMode - smartredirect");
                    m2231a(str, a, z);
                }
            } catch (Exception unused) {
                C5155g.m3805a(6, "Error while trying parsing index from url");
                return false;
            }
        } else if (!mo61165d()[0] || z2) {
            C5155g.m3805a(6, "forceExternal - interMode - smartredirect");
            m2234b(str, 0, z);
        } else {
            C5155g.m3805a(6, "forceExternal - interMode - redirectr");
            m2231a(str, 0, z);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public boolean mo61197b(String str) {
        return !this.f2500o && str.contains("index=");
    }

    /* access modifiers changed from: protected */
    /* renamed from: B */
    public void mo61188B() {
        String[] l = mo61173l();
        if (l != null && l.length > 0 && mo61173l()[0] != null) {
            C4988c.m3124b((Context) mo61159b(), mo61173l()[0], mo61189C());
        }
    }

    /* renamed from: a */
    private void m2231a(String str, int i, boolean z) {
        int i2 = i;
        Activity b = mo61159b();
        String str2 = null;
        String str3 = i2 < mo61170i().length ? mo61170i()[i2] : null;
        if (i2 < mo61171j().length) {
            str2 = mo61171j()[i2];
        }
        C4988c.m3108a(b, str, str3, str2, mo61189C(), C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), mo61157a(i2), mo61160b(i2), z, new Runnable() {
            public void run() {
                C4740c.this.mo61177p();
            }
        });
    }

    /* renamed from: b */
    private void m2234b(String str, int i, boolean z) {
        C5160b.m3831a((Context) mo61159b()).mo62880a(new Intent("com.startapp.android.OnClickCallback"));
        C4988c.m3105a(mo61159b(), str, i < mo61170i().length ? mo61170i()[i] : null, mo61189C(), mo61157a(i) && !C4988c.m3118a(mo61159b().getApplicationContext(), this.f2467b), z);
        mo61177p();
    }

    /* renamed from: p */
    public void mo61177p() {
        super.mo61177p();
        C5059m.m3378a().mo62377a(false);
        C5040i iVar = this.f2498m;
        if (iVar != null) {
            iVar.mo62345a(false);
        }
        mo61159b().runOnUiThread(new Runnable() {
            public void run() {
                if (C4740c.this.f2489d != null) {
                    C5146c.m3768b(C4740c.this.f2489d);
                }
            }
        });
    }

    /* renamed from: s */
    public void mo61180s() {
        C5040i iVar = this.f2498m;
        if (iVar != null) {
            iVar.mo62346b();
        }
        if (this.f2466a != null && this.f2466a.mo62115b()) {
            this.f2466a.mo62117d();
        }
        WebView webView = this.f2489d;
        if (webView != null) {
            C5146c.m3768b(webView);
        }
        if (mo61168g().equals("back")) {
            mo61177p();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: C */
    public C5002b mo61189C() {
        return new C5001a(mo61191E(), mo61175n());
    }

    /* access modifiers changed from: protected */
    /* renamed from: D */
    public C5002b mo61190D() {
        return new C5002b(mo61175n());
    }

    /* access modifiers changed from: protected */
    /* renamed from: E */
    public String mo61191E() {
        this.f2497l = System.currentTimeMillis();
        double d = (double) (this.f2497l - this.f2496k);
        Double.isNaN(d);
        return String.valueOf(d / 1000.0d);
    }

    /* renamed from: r */
    public boolean mo61179r() {
        mo61188B();
        C5059m.m3378a().mo62377a(false);
        this.f2498m.mo62345a(false);
        return false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: F */
    public long mo61192F() {
        if (mo61176o() != null) {
            return TimeUnit.SECONDS.toMillis(mo61176o().longValue());
        }
        return TimeUnit.SECONDS.toMillis(MetaData.getInstance().getIABDisplayImpressionDelayInSeconds());
    }
}
