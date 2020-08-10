package com.startapp.android.publish.ads.banner.bannerstandard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.p021b.p022a.p023a.p024a.p026b.C0961a;
import com.p021b.p022a.p023a.p024a.p026b.C0963b;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerBase;
import com.startapp.android.publish.ads.banner.BannerInterface;
import com.startapp.android.publish.ads.banner.BannerListener;
import com.startapp.android.publish.ads.banner.BannerOptions;
import com.startapp.android.publish.ads.banner.C4786c;
import com.startapp.android.publish.ads.banner.C4787d;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4925Ad.AdState;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5040i;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.adinformation.C4969b;
import com.startapp.android.publish.adsCommon.adinformation.C4969b.C4977b;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.html.JsInterface;
import com.startapp.android.publish.omsdk.C5132a;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.TimeUnit;

/* compiled from: StartAppSDK */
public class BannerStandard extends BannerBase implements BannerInterface, AdEventListener {
    private static final int ID_WEBVIEW = 159868225;
    private static final String TAG = "BannerHtml";
    protected C4784a adHtml;
    /* access modifiers changed from: private */
    public RelativeLayout adInformationContatiner;
    private C4969b adInformationLayout;
    protected AdPreferences adPreferences;
    /* access modifiers changed from: private */
    public C0963b adSession;
    private boolean callbackSent;
    private boolean defaultLoad;
    private boolean initBannerCalled;
    /* access modifiers changed from: private */
    public boolean jsTag;
    protected BannerListener listener;
    private boolean loaded;
    private BannerOptions options;
    private C5040i scheduledImpression;
    private C4787d size;
    private boolean visible;
    protected WebView webView;
    /* access modifiers changed from: private */
    public boolean webViewTouched;

    /* compiled from: StartAppSDK */
    class MyWebViewClient extends WebViewClient {
        private boolean callbackSent = false;

        MyWebViewClient() {
        }

        public void onPageFinished(WebView webView, String str) {
            if (MetaData.getInstance().isOmsdkEnabled()) {
                BannerStandard bannerStandard = BannerStandard.this;
                bannerStandard.adSession = C5132a.m3700a(bannerStandard.webView);
                if (BannerStandard.this.adSession != null && BannerStandard.this.webView != null) {
                    if (BannerStandard.this.adInformationContatiner != null) {
                        BannerStandard.this.adSession.mo11478b(BannerStandard.this.adInformationContatiner);
                    }
                    BannerStandard.this.adSession.mo11476a(BannerStandard.this.webView);
                    BannerStandard.this.adSession.mo11475a();
                    C0961a.m103a(BannerStandard.this.adSession).mo11464a();
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (BannerStandard.this.jsTag && !BannerStandard.this.webViewTouched) {
                return false;
            }
            if (!this.callbackSent) {
                this.callbackSent = true;
                if (BannerStandard.this.listener != null) {
                    BannerStandard.this.listener.onClick(BannerStandard.this);
                }
            }
            BannerStandard.this.cancelScheduledImpression(true);
            boolean a = C4988c.m3118a(BannerStandard.this.getContext(), Placement.INAPP_BANNER);
            if (!BannerStandard.this.jsTag) {
                if (str.contains("index=")) {
                    try {
                        int a2 = C4988c.m3093a(str);
                        String str2 = null;
                        if (!BannerStandard.this.adHtml.mo62239d(a2) || a) {
                            Context context = BannerStandard.this.getContext();
                            if (a2 < BannerStandard.this.adHtml.mo62250m().length) {
                                str2 = BannerStandard.this.adHtml.mo62250m()[a2];
                            }
                            C4988c.m3105a(context, str, str2, new C5002b(BannerStandard.this.getAdTag()), BannerStandard.this.adHtml.mo62241e(a2) && !a, false);
                            BannerStandard.this.webView.stopLoading();
                            BannerStandard.this.setClicked(true);
                            return true;
                        }
                        Context context2 = BannerStandard.this.getContext();
                        String str3 = a2 < BannerStandard.this.adHtml.mo62250m().length ? BannerStandard.this.adHtml.mo62250m()[a2] : null;
                        if (a2 < BannerStandard.this.adHtml.mo62252o().length) {
                            str2 = BannerStandard.this.adHtml.mo62252o()[a2];
                        }
                        C4988c.m3107a(context2, str, str3, str2, new C5002b(BannerStandard.this.getAdTag()), C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), BannerStandard.this.adHtml.mo62241e(a2), BannerStandard.this.adHtml.mo62242f(a2), false);
                        BannerStandard.this.webView.stopLoading();
                        BannerStandard.this.setClicked(true);
                        return true;
                    } catch (Exception unused) {
                        C5155g.m3807a(BannerStandard.TAG, 6, "Error while trying parsing index from url");
                        return false;
                    }
                }
            } else {
                String str4 = str;
            }
            if (!BannerStandard.this.adHtml.mo62239d(0) || a) {
                C4988c.m3105a(BannerStandard.this.getContext(), str, BannerStandard.this.adHtml.mo62250m()[0], new C5002b(BannerStandard.this.getAdTag()), BannerStandard.this.adHtml.mo62241e(0) && !a, false);
                BannerStandard.this.webView.stopLoading();
                BannerStandard.this.setClicked(true);
                return true;
            }
            C4988c.m3107a(BannerStandard.this.getContext(), str, BannerStandard.this.adHtml.mo62250m()[0], BannerStandard.this.adHtml.mo62252o()[0], new C5002b(BannerStandard.this.getAdTag()), C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), BannerStandard.this.adHtml.mo62241e(0), BannerStandard.this.adHtml.mo62242f(0), false);
            BannerStandard.this.webView.stopLoading();
            BannerStandard.this.setClicked(true);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public String getBannerName() {
        return "StartApp Banner";
    }

    /* access modifiers changed from: protected */
    public int getBannerType() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getHeightInDp() {
        return 50;
    }

    /* access modifiers changed from: protected */
    public int getWidthInDp() {
        return 300;
    }

    public BannerStandard(Activity activity) {
        this((Context) activity);
    }

    public BannerStandard(Activity activity, AdPreferences adPreferences2) {
        this((Context) activity, adPreferences2);
    }

    public BannerStandard(Activity activity, BannerListener bannerListener) {
        this((Context) activity, bannerListener);
    }

    public BannerStandard(Activity activity, AdPreferences adPreferences2, BannerListener bannerListener) {
        this((Context) activity, adPreferences2, bannerListener);
    }

    public BannerStandard(Activity activity, boolean z) {
        this((Context) activity, z);
    }

    public BannerStandard(Activity activity, boolean z, AdPreferences adPreferences2) {
        this((Context) activity, z, adPreferences2);
    }

    public BannerStandard(Activity activity, AttributeSet attributeSet) {
        this((Context) activity, attributeSet);
    }

    public BannerStandard(Activity activity, AttributeSet attributeSet, int i) {
        this((Context) activity, attributeSet, i);
    }

    @Deprecated
    public BannerStandard(Context context) {
        this(context, true, (AdPreferences) null);
    }

    @Deprecated
    public BannerStandard(Context context, AdPreferences adPreferences2) {
        this(context, true, adPreferences2);
    }

    @Deprecated
    public BannerStandard(Context context, BannerListener bannerListener) {
        this(context, true, (AdPreferences) null);
        setBannerListener(bannerListener);
    }

    @Deprecated
    public BannerStandard(Context context, AdPreferences adPreferences2, BannerListener bannerListener) {
        this(context, true, adPreferences2);
        setBannerListener(bannerListener);
    }

    @Deprecated
    public BannerStandard(Context context, boolean z) {
        this(context, z, (AdPreferences) null);
    }

    @Deprecated
    public BannerStandard(Context context, boolean z, AdPreferences adPreferences2) {
        super(context);
        this.loaded = false;
        this.webViewTouched = true;
        this.jsTag = false;
        this.defaultLoad = true;
        this.visible = true;
        this.initBannerCalled = false;
        this.callbackSent = false;
        this.adInformationLayout = null;
        this.adInformationContatiner = null;
        try {
            this.defaultLoad = z;
            this.adPreferences = adPreferences2;
            init();
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "BannerStandard.constructor - unexpected error occurd", e.getMessage(), "");
        }
    }

    @Deprecated
    public BannerStandard(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Deprecated
    public BannerStandard(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.loaded = false;
        this.webViewTouched = true;
        this.jsTag = false;
        this.defaultLoad = true;
        this.visible = true;
        this.initBannerCalled = false;
        this.callbackSent = false;
        this.adInformationLayout = null;
        this.adInformationContatiner = null;
        try {
            init();
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "BannerStandard.constructor - unexpected error occurd", e.getMessage(), "");
        }
    }

    private void addAdInformationLayout() {
        LayoutParams layoutParams = new LayoutParams(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        layoutParams.addRule(13);
        if (this.adInformationLayout == null && this.adInformationContatiner == null) {
            this.adInformationContatiner = new RelativeLayout(getContext());
            this.adInformationLayout = new C4969b(getContext(), C4977b.SMALL, Placement.INAPP_BANNER, this.adHtml.getAdInfoOverride());
            this.adInformationLayout.mo62113a(this.adInformationContatiner);
        }
        try {
            ViewGroup viewGroup = (ViewGroup) this.adInformationContatiner.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.adInformationContatiner);
            }
        } catch (Exception unused) {
        }
        addView(this.adInformationContatiner, layoutParams);
    }

    public void hideBanner() {
        this.visible = false;
        setVisibility(8);
    }

    public void showBanner() {
        this.visible = true;
        setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void initRuntime() {
        try {
            this.options = new BannerOptions();
            this.adHtml = new C4784a(getContext(), getOffset());
            if (this.adPreferences == null) {
                this.adPreferences = new AdPreferences();
            }
            this.size = new C4787d(getWidthInDp(), getHeightInDp());
            this.webView = new WebView(getContext());
            if (getId() == -1) {
                setId(getBannerId());
            }
            this.webView.setId(ID_WEBVIEW);
            setVisibility(8);
            this.webView.setBackgroundColor(0);
            this.webView.setHorizontalScrollBarEnabled(false);
            this.webView.getSettings().setJavaScriptEnabled(true);
            this.webView.setVerticalScrollBarEnabled(false);
            this.webView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    BannerStandard.this.webViewTouched = true;
                    if (motionEvent.getAction() == 2) {
                        return true;
                    }
                    return false;
                }
            });
            this.webView.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return true;
                }
            });
            this.webView.setLongClickable(false);
            this.options = C4786c.m2356a().mo61370c();
            getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    C5146c.m3755a(BannerStandard.this.getViewTreeObserver(), (OnGlobalLayoutListener) this);
                    BannerStandard bannerStandard = BannerStandard.this;
                    bannerStandard.setHardwareAcceleration(bannerStandard.adPreferences);
                    BannerStandard.this.initBanner();
                }
            });
        } catch (Exception e) {
            String str = "BannerStandard.init - webview failed";
            C5017f.m3256a(getContext(), C5015d.EXCEPTION, str, e.getMessage(), "");
            C5155g.m3807a(TAG, 6, "webVIew exception");
            hideBanner();
            onFailedToReceiveBanner(str);
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (layoutParams.width > 0 && layoutParams.height > 0) {
            new Handler().post(new Runnable() {
                public void run() {
                    BannerStandard.this.initBanner();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void initBanner() {
        if (!this.initBannerCalled && this.webView != null) {
            String str = TAG;
            C5155g.m3807a(str, 3, "Initializing BannerHtml");
            this.initBannerCalled = true;
            int a = C4945h.m2891a(getContext(), this.size.mo61371a());
            int a2 = C4945h.m2891a(getContext(), this.size.mo61374b());
            setMinimumWidth(a);
            setMinimumHeight(a2);
            this.webView.addJavascriptInterface(new JsInterface(getContext(), (Runnable) new Runnable() {
                public void run() {
                }
            }, new C5002b(getAdTag()), this.adHtml.mo62241e(0)), "startappwall");
            this.webView.setWebViewClient(new MyWebViewClient());
            if (this.loaded) {
                C5155g.m3807a(str, 3, "BannerHTML already Loaded");
                onReceiveAd(this.adHtml);
            } else if (this.defaultLoad) {
                loadBanner();
            }
            LayoutParams layoutParams = new LayoutParams(a, a2);
            layoutParams.addRule(13);
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.addView(this.webView, layoutParams);
            if (getLayoutParams() != null) {
                LayoutParams layoutParams2 = new LayoutParams(getLayoutParams().width, getLayoutParams().height);
                layoutParams2.addRule(13);
                addView(relativeLayout, layoutParams2);
                return;
            }
            addView(relativeLayout);
        }
    }

    /* access modifiers changed from: protected */
    public void reload() {
        C5155g.m3807a(TAG, 3, "Loading from network");
        C0963b bVar = this.adSession;
        if (bVar != null) {
            bVar.mo11477b();
            this.adSession = null;
        }
        if (this.adPreferences == null) {
            this.adPreferences = new AdPreferences();
        }
        Point availableSize = getAvailableSize();
        this.adHtml.setState(AdState.UN_INITIALIZED);
        this.adHtml.mo62231a(availableSize.x, availableSize.y);
        this.adHtml.mo61365a(getBannerType());
        this.adHtml.load(this.adPreferences, this);
    }

    private Point getAvailableSize() {
        Point point = new Point();
        if (getLayoutParams() != null && getLayoutParams().width > 0) {
            point.x = C4945h.m2900b(getContext(), getLayoutParams().width + 1);
        }
        if (getLayoutParams() != null && getLayoutParams().height > 0) {
            point.y = C4945h.m2900b(getContext(), getLayoutParams().height + 1);
        }
        if (getLayoutParams() != null && getLayoutParams().width > 0 && getLayoutParams().height > 0) {
            this.adHtml.mo61366a(true);
        }
        if (getLayoutParams() == null || getLayoutParams().width <= 0 || getLayoutParams().height <= 0) {
            Context context = getContext();
            if (context instanceof Activity) {
                View decorView = ((Activity) context).getWindow().getDecorView();
                try {
                    View view = (View) getParent();
                    if (view instanceof Banner) {
                        view = (View) view.getParent();
                    }
                    while (view != null && (view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0)) {
                        if (view.getMeasuredWidth() > 0) {
                            setPointWidthIfNotSet(point, C4945h.m2900b(getContext(), (view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight()));
                        }
                        if (view.getMeasuredHeight() > 0) {
                            setPointHeightIfNotSet(point, C4945h.m2900b(getContext(), (view.getMeasuredHeight() - view.getPaddingBottom()) - view.getPaddingTop()));
                        }
                        view = (View) view.getParent();
                    }
                    if (view == null) {
                        determineSizeByScreen(point, decorView);
                    } else {
                        setPointWidthIfNotSet(point, C4945h.m2900b(getContext(), (view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight()));
                        setPointHeightIfNotSet(point, C4945h.m2900b(getContext(), (view.getMeasuredHeight() - view.getPaddingBottom()) - view.getPaddingTop()));
                    }
                } catch (Exception unused) {
                    determineSizeByScreen(point, decorView);
                }
            } else {
                try {
                    WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
                    setPointWidthIfNotSet(point, getWidthInDp());
                    setPointHeightIfNotSet(point, getHeightInDp());
                    if (!(windowManager == null || context == null)) {
                        C4945h.m2897a(context, windowManager, point);
                    }
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, "BannerStandard.getAvailableSize - system service failed", e.getMessage(), "");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("============ exit Application Size [");
        sb.append(point.x);
        sb.append(",");
        sb.append(point.y);
        sb.append("] =========");
        C5155g.m3807a(TAG, 3, sb.toString());
        return point;
    }

    private void determineSizeByScreen(Point point, View view) {
        setPointWidthIfNotSet(point, C4945h.m2900b(getContext(), view.getMeasuredWidth()));
        setPointHeightIfNotSet(point, C4945h.m2900b(getContext(), view.getMeasuredHeight()));
    }

    private void setPointWidthIfNotSet(Point point, int i) {
        if (point.x <= 0) {
            point.x = i;
        }
    }

    private void setPointHeightIfNotSet(Point point, int i) {
        if (point.y <= 0) {
            point.y = i;
        }
    }

    public void onReceiveAd(C4925Ad ad) {
        String str = TAG;
        C5155g.m3807a(str, 3, " Html Ad Recievied OK");
        this.webViewTouched = false;
        removeView(this.adInformationContatiner);
        C4784a aVar = this.adHtml;
        if (aVar == null || aVar.mo62243f() == null || this.adHtml.mo62243f().compareTo("") == 0) {
            C5155g.m3807a(str, 6, "No Banner recieved");
            onFailedToReceiveBanner("No Banner received");
            return;
        }
        String str2 = "@jsTag@";
        this.jsTag = "true".equals(C4946i.m2908a(this.adHtml.mo62243f(), str2, str2));
        loadHtml();
        String str3 = "@width@";
        String str4 = "@height@";
        try {
            if (setSize(Integer.parseInt(C4946i.m2908a(this.adHtml.mo62243f(), str3, str3)), Integer.parseInt(C4946i.m2908a(this.adHtml.mo62243f(), str4, str4)))) {
                this.loaded = true;
                addAdInformationLayout();
                makeImpression();
                addDisplayEventOnLoad();
                if (this.listener != null && !this.callbackSent) {
                    this.callbackSent = true;
                    this.listener.onReceiveAd(this);
                }
                if (this.visible) {
                    setVisibility(0);
                }
                C5155g.m3807a(str, 3, "Done Loading HTML Banner");
                return;
            }
            onFailedToReceiveBanner("Banner cannot be displayed (not enough room)");
        } catch (NumberFormatException unused) {
            String str5 = "Error Casting width & height from HTML";
            C5155g.m3807a(str, 6, str5);
            onFailedToReceiveBanner(str5);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown error occurred ");
            sb.append(e.getMessage());
            C5155g.m3807a(str, 6, sb.toString());
            onFailedToReceiveBanner(e.getMessage());
        }
    }

    private void onFailedToReceiveBanner(String str) {
        setErrorMessage(str);
        BannerListener bannerListener = this.listener;
        if (bannerListener != null && !this.callbackSent) {
            this.callbackSent = true;
            bannerListener.onFailedToReceiveAd(this);
        }
    }

    private void loadHtml() {
        C4946i.m2912a(getContext(), this.webView, this.adHtml.mo62243f());
    }

    /* access modifiers changed from: protected */
    public void makeImpression() {
        C5155g.m3807a(TAG, 3, "BannerStandard Scheduling visibility check");
        C5040i iVar = new C5040i(getContext(), this.adHtml.mo62249l(), new C5002b(getAdTag()), getImpressionDelayMillis());
        this.scheduledImpression = iVar;
        startVisibilityRunnable(this.scheduledImpression);
    }

    private long getImpressionDelayMillis() {
        if (this.adHtml.mo62253p() != null) {
            return TimeUnit.SECONDS.toMillis(this.adHtml.mo62253p().longValue());
        }
        return TimeUnit.SECONDS.toMillis(MetaData.getInstance().getIABDisplayImpressionDelayInSeconds());
    }

    private boolean setSize(int i, int i2) {
        Point availableSize = getAvailableSize();
        if (availableSize.x < i || availableSize.y < i2) {
            Point point = new Point(0, 0);
            ViewGroup.LayoutParams layoutParams = this.webView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(point.x, point.y);
            } else {
                layoutParams.width = point.x;
                layoutParams.height = point.y;
            }
            this.webView.setLayoutParams(layoutParams);
            return false;
        }
        this.size.mo61373a(i, i2);
        int a = C4945h.m2891a(getContext(), this.size.mo61371a());
        int a2 = C4945h.m2891a(getContext(), this.size.mo61374b());
        setMinimumWidth(a);
        setMinimumHeight(a2);
        ViewGroup.LayoutParams layoutParams2 = this.webView.getLayoutParams();
        if (layoutParams2 == null) {
            layoutParams2 = new LayoutParams(a, a2);
        } else {
            layoutParams2.width = a;
            layoutParams2.height = a2;
        }
        this.webView.setLayoutParams(layoutParams2);
        return true;
    }

    public void onFailedToReceiveAd(C4925Ad ad) {
        onFailedToReceiveBanner(ad.getErrorMessage());
    }

    private void onResume() {
        WebView webView2 = this.webView;
        if (webView2 != null) {
            C5146c.m3770c(webView2);
        }
    }

    private void onPause() {
        WebView webView2 = this.webView;
        if (webView2 != null) {
            C5146c.m3768b(webView2);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onResume();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onPause();
        cancelScheduledImpression(false);
        C0963b bVar = this.adSession;
        if (bVar != null) {
            bVar.mo11477b();
            this.adSession = null;
            C4946i.m2921a((Object) this.webView, 1000);
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            onResume();
        } else {
            onPause();
        }
    }

    public void setBannerListener(BannerListener bannerListener) {
        this.listener = bannerListener;
    }

    /* access modifiers changed from: protected */
    public int getRefreshRate() {
        return this.options.mo61281i();
    }

    /* access modifiers changed from: protected */
    public int getOffset() {
        C4784a aVar = this.adHtml;
        if (aVar == null) {
            return 0;
        }
        return aVar.mo61220a();
    }

    /* access modifiers changed from: protected */
    public int getBannerId() {
        return this.innerBannerStandardId;
    }

    /* access modifiers changed from: protected */
    public void setBannerId(int i) {
        this.innerBannerStandardId = i;
    }

    public void setAdTag(String str) {
        this.adTag = str;
    }

    /* access modifiers changed from: protected */
    public void cancelScheduledImpression(boolean z) {
        C5040i iVar = this.scheduledImpression;
        if (iVar != null) {
            iVar.mo62345a(z);
        }
    }
}
