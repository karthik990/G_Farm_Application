package com.braintreepayments.api.threedsecure;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.braintreepayments.api.internal.BraintreeHttpClient;

@Deprecated
public class ThreeDSecureWebView extends WebView {
    public ThreeDSecureWebView(Context context) {
        super(context);
    }

    public ThreeDSecureWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ThreeDSecureWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ThreeDSecureWebView(Context context, AttributeSet attributeSet, int i, boolean z) {
        super(context, attributeSet, i, z);
    }

    public ThreeDSecureWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void init(ThreeDSecureWebViewActivity threeDSecureWebViewActivity) {
        setId(16908312);
        WebSettings settings = getSettings();
        settings.setUserAgentString(BraintreeHttpClient.getUserAgent());
        settings.setCacheMode(1);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        setWebChromeClient(new ThreeDSecureWebChromeClient(threeDSecureWebViewActivity));
        setWebViewClient(new ThreeDSecureWebViewClient(threeDSecureWebViewActivity));
    }
}
