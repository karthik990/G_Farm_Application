package com.startapp.android.publish.adsCommon.adinformation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adinformation.AdInformationConfig.ImageResourceType;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5016e;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.C5122h;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.adsCommon.adinformation.b */
/* compiled from: StartAppSDK */
public class C4969b implements OnClickListener {

    /* renamed from: a */
    Context f3105a;

    /* renamed from: b */
    RelativeLayout f3106b;

    /* renamed from: c */
    RelativeLayout f3107c;

    /* renamed from: d */
    AdInformationConfig f3108d;

    /* renamed from: e */
    C5122h f3109e;

    /* renamed from: f */
    private C4979d f3110f;

    /* renamed from: g */
    private WebView f3111g;

    /* renamed from: h */
    private Dialog f3112h = null;

    /* renamed from: i */
    private Placement f3113i;

    /* renamed from: j */
    private Handler f3114j = new Handler();

    /* renamed from: k */
    private C4976a f3115k = C4976a.REGULAR;

    /* renamed from: l */
    private boolean f3116l = false;

    /* renamed from: m */
    private C4978c f3117m;

    /* renamed from: n */
    private Runnable f3118n = new Runnable() {
        public void run() {
            try {
                C4969b.this.mo62117d();
                C4969b.this.f3109e.mo62661a(C4969b.this.f3105a, true);
                C4969b.this.f3108d.mo62087a(C4969b.this.f3105a, true);
                C4969b.this.mo62114a(false);
            } catch (Exception e) {
                C5017f.m3258a(C4969b.this.f3105a, new C5016e(C5015d.EXCEPTION, "AdInformationObject.acceptRunnable failed", e.getMessage()), "");
            }
        }
    };

    /* renamed from: o */
    private Runnable f3119o = new Runnable() {
        public void run() {
            try {
                C4969b.this.mo62117d();
                C5053l.m3364b();
                C4969b.this.f3109e.mo62661a(C4969b.this.f3105a, false);
                C4969b.this.f3108d.mo62087a(C4969b.this.f3105a, true);
                C4969b.this.mo62114a(false);
            } catch (Exception e) {
                C5017f.m3258a(C4969b.this.f3105a, new C5016e(C5015d.EXCEPTION, "AdInformationObject.declineRunnable failed", e.getMessage()), "");
            }
        }
    };

    /* renamed from: p */
    private Runnable f3120p = new Runnable() {
        public void run() {
            try {
                C4969b.this.mo62117d();
                C4969b.this.mo62116c();
                C4969b.this.mo62114a(false);
            } catch (Exception e) {
                C5017f.m3258a(C4969b.this.f3105a, new C5016e(C5015d.EXCEPTION, "AdInformationObject.fullPrivacyPolicy failed", e.getMessage()), "");
            }
        }
    };

    /* renamed from: com.startapp.android.publish.adsCommon.adinformation.b$6 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C49756 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3128a = new int[C4976a.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.startapp.android.publish.adsCommon.adinformation.b$a[] r0 = com.startapp.android.publish.adsCommon.adinformation.C4969b.C4976a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3128a = r0
                int[] r0 = f3128a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.adsCommon.adinformation.b$a r1 = com.startapp.android.publish.adsCommon.adinformation.C4969b.C4976a.LAYOUT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3128a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.adsCommon.adinformation.b$a r1 = com.startapp.android.publish.adsCommon.adinformation.C4969b.C4976a.REGULAR     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.adinformation.C4969b.C49756.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.adsCommon.adinformation.b$a */
    /* compiled from: StartAppSDK */
    public enum C4976a {
        REGULAR,
        LAYOUT
    }

    /* renamed from: com.startapp.android.publish.adsCommon.adinformation.b$b */
    /* compiled from: StartAppSDK */
    public enum C4977b {
        SMALL(ImageResourceType.INFO_S, ImageResourceType.INFO_EX_S),
        LARGE(ImageResourceType.INFO_L, ImageResourceType.INFO_EX_L);
        
        private ImageResourceType infoExtendedType;
        private ImageResourceType infoType;

        private C4977b(ImageResourceType imageResourceType, ImageResourceType imageResourceType2) {
            this.infoType = imageResourceType;
            this.infoExtendedType = imageResourceType2;
        }

        /* renamed from: a */
        public ImageResourceType mo62124a() {
            return this.infoType;
        }
    }

    public C4969b(Context context, C4977b bVar, Placement placement, C4978c cVar) {
        this.f3105a = context;
        this.f3113i = placement;
        this.f3117m = cVar;
        this.f3108d = m2996e();
        this.f3109e = MetaData.getInstance().getSimpleTokenConfig();
        C4979d dVar = new C4979d(context, bVar, placement, cVar, this);
        this.f3110f = dVar;
    }

    /* renamed from: a */
    public void mo62113a(RelativeLayout relativeLayout) {
        boolean z;
        if (m2997f() == null || !m2997f().mo62130e()) {
            z = m2996e().mo62089a(this.f3105a);
        } else {
            z = m2997f().mo62127b();
        }
        if (z) {
            this.f3107c = relativeLayout;
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            if (m2997f() == null || !m2997f().mo62129d()) {
                m2996e().mo62085a(this.f3113i).addRules(layoutParams);
            } else {
                m2997f().mo62128c().addRules(layoutParams);
            }
            this.f3107c.addView(this.f3110f, layoutParams);
        }
    }

    /* renamed from: a */
    public View mo62112a() {
        return this.f3110f;
    }

    /* renamed from: b */
    public boolean mo62115b() {
        return this.f3116l;
    }

    /* renamed from: a */
    public static AdInformationConfig m2991a(Context context) {
        return C4968a.m2985b().mo62109a();
    }

    /* renamed from: e */
    private AdInformationConfig m2996e() {
        return C4968a.m2985b().mo62109a();
    }

    /* renamed from: f */
    private C4978c m2997f() {
        return this.f3117m;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo62114a(boolean z) {
        if (!this.f3113i.isInterstitial()) {
            Context context = this.f3105a;
            if (context instanceof Activity) {
                C4946i.m2911a((Activity) context, z);
            }
        }
    }

    public void onClick(View view) {
        String str = "";
        if (!this.f3109e.mo62664b(this.f3105a) || !(this.f3105a instanceof Activity)) {
            mo62116c();
        } else {
            mo62114a(true);
            this.f3106b = new RelativeLayout(this.f3105a);
            try {
                this.f3111g = new WebView(this.f3105a);
                this.f3111g.setWebViewClient(new WebViewClient());
                this.f3111g.setWebChromeClient(new WebChromeClient());
                this.f3111g.getSettings().setJavaScriptEnabled(true);
                this.f3111g.setHorizontalScrollBarEnabled(false);
                this.f3111g.setVerticalScrollBarEnabled(false);
                this.f3111g.loadUrl(m2992a(this.f3108d.mo62094f()));
                this.f3111g.addJavascriptInterface(new AdInformationJsInterface(this.f3105a, this.f3118n, this.f3119o, this.f3120p), "startappwall");
                Point point = new Point(1, 1);
                try {
                    C4945h.m2898a((WindowManager) this.f3105a.getSystemService("window"), point);
                    LayoutParams layoutParams = new LayoutParams(-1, -1);
                    layoutParams.addRule(13);
                    this.f3111g.setPadding(0, 0, 0, 0);
                    layoutParams.setMargins(0, 0, 0, 0);
                    this.f3106b.addView(this.f3111g, layoutParams);
                    m2998g();
                    int i = C49756.f3128a[this.f3115k.ordinal()];
                    if (i == 1) {
                        m2994b(this.f3106b, point);
                    } else if (i == 2) {
                        m2993a(this.f3106b, point);
                    }
                } catch (Exception e) {
                    C5017f.m3256a(this.f3105a, C5015d.EXCEPTION, "AdInformationObject.onClick - system service failed", e.getMessage(), str);
                    mo62114a(false);
                }
            } catch (Exception e2) {
                C5017f.m3256a(this.f3105a, C5015d.EXCEPTION, "AdInformationObject.onClick - webview instantiation failed", e2.getMessage(), str);
                mo62114a(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void mo62116c() {
        if (!C4946i.m2923a(256) || !MetaData.getInstance().isInAppBrowser()) {
            C4988c.m3127c(this.f3105a, this.f3108d.mo62090b());
        } else {
            C4988c.m3125b(this.f3105a, this.f3108d.mo62090b(), "");
        }
    }

    /* renamed from: d */
    public void mo62117d() {
        this.f3116l = false;
        int i = C49756.f3128a[this.f3115k.ordinal()];
        if (i == 1) {
            this.f3114j.post(new Runnable() {
                public void run() {
                    C4969b.this.f3107c.removeView(C4969b.this.f3106b);
                }
            });
        } else if (i == 2) {
            this.f3112h.dismiss();
        }
    }

    /* renamed from: g */
    private void m2998g() {
        String a = C4988c.m3096a(this.f3105a, (String) null);
        if (a != null) {
            WebView webView = this.f3111g;
            StringBuilder sb = new StringBuilder();
            sb.append("javascript:window.onload=function(){document.getElementById('titlePlacement').innerText='");
            sb.append(a);
            sb.append("';}");
            webView.loadUrl(sb.toString());
        }
    }

    /* renamed from: a */
    private void m2993a(ViewGroup viewGroup, Point point) {
        this.f3116l = true;
        this.f3112h = new Dialog(this.f3105a);
        this.f3112h.requestWindowFeature(1);
        this.f3112h.setContentView(viewGroup);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.f3112h.getWindow().getAttributes());
        layoutParams.width = (int) (((float) point.x) * 0.9f);
        layoutParams.height = (int) (((float) point.y) * 0.85f);
        this.f3112h.show();
        this.f3112h.getWindow().setAttributes(layoutParams);
    }

    /* renamed from: b */
    private void m2994b(final ViewGroup viewGroup, Point point) {
        this.f3116l = true;
        final LayoutParams layoutParams = new LayoutParams((int) (((float) point.x) * 0.9f), (int) (((float) point.y) * 0.85f));
        layoutParams.addRule(13);
        this.f3114j.post(new Runnable() {
            public void run() {
                C4969b.this.f3107c.addView(viewGroup, layoutParams);
            }
        });
    }

    /* renamed from: a */
    private String m2992a(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (m2995b(this.f3105a)) {
            sb.append("?le=true");
        }
        return sb.toString();
    }

    /* renamed from: b */
    public static boolean m2995b(Context context) {
        return C5051k.m3335a(context, "shared_prefs_using_location", Boolean.valueOf(false)).booleanValue();
    }
}
