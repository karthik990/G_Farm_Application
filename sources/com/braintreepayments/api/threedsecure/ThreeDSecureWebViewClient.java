package com.braintreepayments.api.threedsecure;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.braintreepayments.api.models.ThreeDSecureAuthenticationResponse;

@Deprecated
public class ThreeDSecureWebViewClient extends WebViewClient {
    private ThreeDSecureWebViewActivity mActivity;

    public ThreeDSecureWebViewClient(ThreeDSecureWebViewActivity threeDSecureWebViewActivity) {
        this.mActivity = threeDSecureWebViewActivity;
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (str.contains("html/authentication_complete_frame")) {
            webView.stopLoading();
            this.mActivity.finishWithResult(ThreeDSecureAuthenticationResponse.fromJson(Uri.parse(str).getQueryParameter("auth_response")));
            return;
        }
        super.onPageStarted(webView, str, bitmap);
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        this.mActivity.setActionBarTitle(webView.getTitle());
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        webView.stopLoading();
        this.mActivity.finishWithResult(ThreeDSecureAuthenticationResponse.fromException(str));
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.cancel();
        webView.stopLoading();
        this.mActivity.finishWithResult(ThreeDSecureAuthenticationResponse.fromException(sslError.toString()));
    }
}
