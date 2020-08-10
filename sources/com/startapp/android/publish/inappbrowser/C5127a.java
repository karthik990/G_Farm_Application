package com.startapp.android.publish.inappbrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.startapp.android.publish.ads.p066a.C4736b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.inappbrowser.a */
/* compiled from: StartAppSDK */
public class C5127a extends C4736b implements OnClickListener {

    /* renamed from: j */
    protected static boolean f3496j = false;

    /* renamed from: d */
    protected RelativeLayout f3497d;

    /* renamed from: e */
    protected C5130b f3498e;

    /* renamed from: f */
    protected WebView f3499f;

    /* renamed from: g */
    protected AnimatingProgressBar f3500g;

    /* renamed from: h */
    protected FrameLayout f3501h;

    /* renamed from: i */
    protected String f3502i;

    /* renamed from: com.startapp.android.publish.inappbrowser.a$a */
    /* compiled from: StartAppSDK */
    private static class C5129a extends WebViewClient {

        /* renamed from: a */
        private Context f3504a;

        /* renamed from: b */
        private C5127a f3505b;

        /* renamed from: c */
        private C5130b f3506c;

        /* renamed from: d */
        private AnimatingProgressBar f3507d;

        /* renamed from: e */
        private int f3508e = 0;

        /* renamed from: f */
        private boolean f3509f = false;

        public C5129a(Context context, C5130b bVar, AnimatingProgressBar animatingProgressBar, C5127a aVar) {
            this.f3504a = context;
            this.f3507d = animatingProgressBar;
            this.f3506c = bVar;
            this.f3505b = aVar;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (!C5127a.f3496j) {
                StringBuilder sb = new StringBuilder();
                sb.append("IABWebViewClient::onPageStarted - [");
                sb.append(str);
                sb.append("]");
                sb.append("REDIRECTED  -> ");
                sb.append(this.f3508e);
                sb.append(" Can go back ");
                sb.append(webView.canGoBack());
                C5155g.m3807a("IABrowserMode", 3, sb.toString());
                if (this.f3509f) {
                    this.f3508e = 1;
                    this.f3507d.mo62807a();
                    this.f3506c.mo62821a(webView);
                } else {
                    this.f3508e = Math.max(this.f3508e, 1);
                }
                this.f3507d.setVisibility(0);
                this.f3506c.getUrlTxt().setText(str);
                this.f3506c.mo62821a(webView);
                super.onPageStarted(webView, str, bitmap);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("IABWebViewClient::shouldOverrideUrlLoading - [");
            sb.append(str);
            sb.append("]");
            C5155g.m3807a("IABrowserMode", 3, sb.toString());
            if (!C5127a.f3496j) {
                if (!this.f3509f) {
                    this.f3509f = true;
                    this.f3507d.mo62807a();
                    this.f3508e = 0;
                }
                this.f3508e++;
                if (C4988c.m3132d(str) && !C4988c.m3126b(str)) {
                    return false;
                }
                this.f3508e = 1;
                C4988c.m3127c(this.f3504a, str);
                C5127a aVar = this.f3505b;
                if (aVar != null) {
                    aVar.mo62813x();
                }
            }
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            if (!C5127a.f3496j) {
                StringBuilder sb = new StringBuilder();
                sb.append("IABWebViewClient::onPageFinished - [");
                sb.append(str);
                sb.append("]");
                C5155g.m3807a("IABrowserMode", 3, sb.toString());
                this.f3506c.mo62821a(webView);
                int i = this.f3508e - 1;
                this.f3508e = i;
                if (i == 0) {
                    this.f3509f = false;
                    this.f3507d.mo62807a();
                    if (this.f3507d.isShown()) {
                        this.f3507d.setVisibility(8);
                    }
                    this.f3506c.mo62821a(webView);
                }
                super.onPageFinished(webView, str);
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            StringBuilder sb = new StringBuilder();
            sb.append("IABWebViewClient::onReceivedError - [");
            sb.append(str);
            sb.append("], [");
            sb.append(str2);
            sb.append("]");
            C5155g.m3807a("IABrowserMode", 3, sb.toString());
            this.f3507d.mo62807a();
            super.onReceivedError(webView, i, str, str2);
        }
    }

    /* renamed from: s */
    public void mo61180s() {
    }

    /* renamed from: u */
    public void mo61182u() {
    }

    public C5127a(String str) {
        this.f3502i = str;
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        super.mo61151a(bundle);
        f3496j = false;
        this.f3497d = new RelativeLayout(mo61159b());
        m3676b(this.f3502i);
        if (bundle != null) {
            mo61164c(bundle);
        }
        mo61159b().setContentView(this.f3497d, new LayoutParams(-2, -2));
    }

    /* renamed from: b */
    private void m3676b(String str) {
        C5155g.m3807a("IABrowserMode", 3, "initUi");
        if (this.f3498e == null) {
            this.f3498e = new C5130b(mo61159b());
            this.f3498e.mo62820a();
            this.f3498e.mo62822b();
            this.f3498e.setButtonsListener(this);
        }
        this.f3497d.addView(this.f3498e);
        this.f3500g = new AnimatingProgressBar(mo61159b(), null, 16842872);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setColor(Color.parseColor("#45d200"));
        this.f3500g.setProgressDrawable(new ClipDrawable(shapeDrawable, 3, 1));
        this.f3500g.setBackgroundColor(-1);
        this.f3500g.setId(2108);
        LayoutParams layoutParams = new LayoutParams(-1, C4945h.m2891a((Context) mo61159b(), 4));
        layoutParams.addRule(3, 2101);
        this.f3497d.addView(this.f3500g, layoutParams);
        this.f3501h = new FrameLayout(mo61159b());
        if (this.f3499f == null) {
            try {
                m3677y();
                this.f3499f.loadUrl(str);
            } catch (Exception e) {
                this.f3498e.mo62824c();
                C4988c.m3127c(mo61159b(), str);
                C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "IABrowserMode.initUi - Webvie  failed", e.getMessage(), "");
                mo61159b().finish();
            }
        }
        this.f3501h.addView(this.f3499f);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(15);
        layoutParams2.addRule(3, 2108);
        this.f3497d.addView(this.f3501h, layoutParams2);
    }

    /* renamed from: b */
    public void mo61161b(Bundle bundle) {
        this.f3499f.saveState(bundle);
    }

    /* renamed from: c */
    public void mo61164c(Bundle bundle) {
        this.f3499f.restoreState(bundle);
    }

    /* renamed from: y */
    private void m3677y() {
        this.f3499f = new WebView(mo61159b());
        m3678z();
        this.f3499f.setWebViewClient(new C5129a(mo61159b(), this.f3498e, this.f3500g, this));
        this.f3499f.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                C5127a.this.f3500g.setProgress(i);
            }

            public void onReceivedTitle(WebView webView, String str) {
                if (str != null && !str.equals("")) {
                    C5127a.this.f3498e.getTitleTxt().setText(str);
                }
            }
        });
    }

    /* renamed from: z */
    private void m3678z() {
        this.f3499f.getSettings().setJavaScriptEnabled(true);
        this.f3499f.getSettings().setUseWideViewPort(true);
        this.f3499f.getSettings().setLoadWithOverviewMode(true);
        this.f3499f.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.f3499f.getSettings().setBuiltInZoomControls(true);
        if (VERSION.SDK_INT >= 11) {
            this.f3499f.getSettings().setDisplayZoomControls(false);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2103:
                mo62813x();
                return;
            case 2104:
                if (this.f3499f != null) {
                    C4988c.m3127c(mo61159b(), this.f3499f.getUrl());
                    mo62813x();
                    return;
                }
                return;
            case 2105:
                WebView webView = this.f3499f;
                if (webView != null && webView.canGoBack()) {
                    this.f3500g.mo62807a();
                    this.f3499f.goBack();
                    return;
                }
                return;
            case 2106:
                WebView webView2 = this.f3499f;
                if (webView2 != null && webView2.canGoForward()) {
                    this.f3500g.mo62807a();
                    this.f3499f.goForward();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* renamed from: a */
    public boolean mo61158a(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || i != 4) {
            return super.mo61158a(i, keyEvent);
        }
        WebView webView = this.f3499f;
        String str = "IABrowserMode";
        if (webView == null || !webView.canGoBack()) {
            C5155g.m3807a(str, 3, "IABWebViewClient::KEYCODE_BACK canT go back");
            mo62813x();
        } else {
            C5155g.m3807a(str, 3, "IABWebViewClient::KEYCODE_BACK can go back");
            this.f3500g.mo62807a();
            this.f3499f.goBack();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: x */
    public void mo62813x() {
        m3675A();
        this.f3498e.mo62824c();
        mo61159b().finish();
    }

    /* renamed from: A */
    private void m3675A() {
        try {
            f3496j = true;
            this.f3499f.stopLoading();
            this.f3499f.removeAllViews();
            this.f3499f.postInvalidate();
            C5146c.m3768b(this.f3499f);
            this.f3499f.destroy();
            this.f3499f = null;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("IABrowserMode::destroyWebview error ");
            sb.append(e.getMessage());
            C5155g.m3807a("IABrowserMode", 6, sb.toString());
        }
    }
}
