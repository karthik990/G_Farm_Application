package com.mobiroller.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import java.util.Map;
import p099im.delight.android.webview.AdvancedWebView;

public class VideoEnabledWebViewWithTouch extends AdvancedWebView {
    private boolean addedJavascriptInterface = false;
    /* access modifiers changed from: private */
    public VideoEnabledWebChromeClient videoEnabledWebChromeClient;

    public class JavascriptInterface {
        public JavascriptInterface() {
        }

        @android.webkit.JavascriptInterface
        public void notifyVideoEnd() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (VideoEnabledWebViewWithTouch.this.videoEnabledWebChromeClient != null) {
                        VideoEnabledWebViewWithTouch.this.videoEnabledWebChromeClient.onHideCustomView();
                    }
                }
            });
        }
    }

    public VideoEnabledWebViewWithTouch(Context context) {
        super(context);
    }

    public VideoEnabledWebViewWithTouch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VideoEnabledWebViewWithTouch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean isVideoFullscreen() {
        VideoEnabledWebChromeClient videoEnabledWebChromeClient2 = this.videoEnabledWebChromeClient;
        return videoEnabledWebChromeClient2 != null && videoEnabledWebChromeClient2.isVideoFullscreen();
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        getSettings().setJavaScriptEnabled(true);
        if (webChromeClient instanceof VideoEnabledWebChromeClient) {
            this.videoEnabledWebChromeClient = (VideoEnabledWebChromeClient) webChromeClient;
        }
        super.setWebChromeClient(webChromeClient);
    }

    public void loadData(String str, String str2, String str3) {
        addJavascriptInterface();
        super.loadData(str, str2, str3);
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        addJavascriptInterface();
        super.loadDataWithBaseURL(str, str2, str3, str4, str5);
    }

    public void loadUrl(String str) {
        addJavascriptInterface();
        super.loadUrl(str);
    }

    public void loadUrl(String str, Map<String, String> map) {
        addJavascriptInterface();
        super.loadUrl(str, map);
    }

    private void addJavascriptInterface() {
        if (!this.addedJavascriptInterface) {
            addJavascriptInterface(new JavascriptInterface(), "_VideoEnabledWebView");
            this.addedJavascriptInterface = true;
        }
    }
}
