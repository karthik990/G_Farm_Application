package com.braintreepayments.api.threedsecure;

import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;

@Deprecated
public class ThreeDSecureWebChromeClient extends WebChromeClient {
    private ThreeDSecureWebViewActivity mActivity;

    public ThreeDSecureWebChromeClient(ThreeDSecureWebViewActivity threeDSecureWebViewActivity) {
        this.mActivity = threeDSecureWebViewActivity;
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        ThreeDSecureWebView threeDSecureWebView = new ThreeDSecureWebView(this.mActivity.getApplicationContext());
        threeDSecureWebView.init(this.mActivity);
        this.mActivity.pushNewWebView(threeDSecureWebView);
        ((WebViewTransport) message.obj).setWebView(threeDSecureWebView);
        message.sendToTarget();
        return true;
    }

    public void onCloseWindow(WebView webView) {
        this.mActivity.popCurrentWebView();
    }

    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
        if (i < 100) {
            this.mActivity.setProgress(i);
            this.mActivity.setProgressBarVisibility(true);
            return;
        }
        this.mActivity.setProgressBarVisibility(false);
    }
}
