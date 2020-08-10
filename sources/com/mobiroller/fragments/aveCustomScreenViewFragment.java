package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.VideoEnabledWebChromeClient;
import com.mobiroller.views.VideoEnabledWebChromeClient.ToggledFullscreenCallback;
import com.mobiroller.views.VideoEnabledWebView;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;
import p099im.delight.android.webview.AdvancedWebView.Listener;

public class aveCustomScreenViewFragment extends BaseModuleFragment implements Listener {
    private String htmlContent;
    @BindView(2131362572)
    RelativeLayout innerLayout;
    @BindView(2131363395)
    VideoEnabledWebView mWebView;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @BindView(2131362799)
    LinearLayout nonVideoLayout;
    @BindView(2131363360)
    RelativeLayout videoLayout;
    @BindView(2131363394)
    RelativeLayout webLayout;

    public void onExternalPageRequest(String str) {
    }

    public void onPageError(int i, String str, String str2) {
    }

    public void onPageFinished(String str) {
    }

    public void onPageStarted(String str, Bitmap bitmap) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.new_web_view, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        loadUi();
        setWebViewSettings();
        this.htmlContent = this.screenModel.getContentHtml();
        this.mWebView.loadDataWithBaseURL(null, this.htmlContent, "text/html", "utf-8", null);
        return inflate;
    }

    private int getScale() {
        double width = (double) ((WindowManager) getActivity().getSystemService("window")).getDefaultDisplay().getWidth();
        Double.isNaN(width);
        return Double.valueOf(Double.valueOf(width / 400.0d).doubleValue() * 100.0d).intValue();
    }

    public void onResume() {
        super.onResume();
        if (this.nonVideoLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.innerLayout);
        }
    }

    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (identifier > 0) {
            return getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private void setWebViewSettings() {
        this.mWebView.setPadding(0, 0, 0, 0);
        this.mWebView.setInitialScale(getScale());
        this.mWebView.clearHistory();
        this.mWebView.clearFormData();
        this.mWebView.clearCache(true);
        this.mWebView.getSettings().setSupportZoom(false);
        this.mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setPluginState(PluginState.ON);
        this.mWebView.getSettings().setAllowFileAccess(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.getSettings().setSupportMultipleWindows(true);
        this.mWebView.setListener((Activity) getActivity(), (Listener) this);
        C40991 r2 = new VideoEnabledWebChromeClient(this.webLayout, this.videoLayout, null, this.mWebView) {
            public void onProgressChanged(WebView webView, int i) {
            }

            public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                String extra = webView.getHitTestResult().getExtra();
                Context context = webView.getContext();
                if (extra == null) {
                    return false;
                }
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(extra)));
                return true;
            }
        };
        r2.setOnToggledFullscreen(new ToggledFullscreenCallback() {
            public void toggledFullscreen(boolean z) {
                if (z) {
                    try {
                        aveCustomScreenViewFragment.this.getActivity().getWindow().getDecorView().findViewById(16908290).findViewById(R.id.toolbar_top).setVisibility(8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LayoutParams attributes = aveCustomScreenViewFragment.this.getActivity().getWindow().getAttributes();
                    attributes.flags |= 1024;
                    attributes.flags |= 128;
                    aveCustomScreenViewFragment.this.getActivity().getWindow().setAttributes(attributes);
                    if (VERSION.SDK_INT >= 14) {
                        aveCustomScreenViewFragment.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(1);
                        return;
                    }
                    return;
                }
                try {
                    aveCustomScreenViewFragment.this.getActivity().getWindow().getDecorView().findViewById(16908290).findViewById(R.id.toolbar_top).setVisibility(0);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                LayoutParams attributes2 = aveCustomScreenViewFragment.this.getActivity().getWindow().getAttributes();
                attributes2.flags &= -1025;
                attributes2.flags &= -129;
                aveCustomScreenViewFragment.this.getActivity().getWindow().setAttributes(attributes2);
                if (VERSION.SDK_INT >= 14) {
                    aveCustomScreenViewFragment.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
                }
            }
        });
        this.mWebView.setWebChromeClient(r2);
        this.mWebView.setWebViewClient(new WebViewClient());
    }

    public void loadUi() {
        this.mWebView.setBackgroundColor(Color.argb(1, 0, 0, 0));
        ImageManager.loadBackgroundImageFromImageModel(this.mainLayout, this.screenModel.getBackgroundImageName());
    }

    public void onDestroyView() {
        try {
            this.mWebView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    public void onDownloadRequested(String str, String str2, String str3, long j, String str4, String str5) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        startActivity(intent);
        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.web_module_redirecting_for_download), 1).show();
        this.mWebView.goBack();
    }
}
