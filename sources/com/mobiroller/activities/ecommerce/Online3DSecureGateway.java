package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import timber.log.Timber;

public class Online3DSecureGateway extends ECommerceBaseActivity {
    private SharedPrefHelper mSharedPref;
    private String paymentHtml;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @BindView(2131363276)
    MobirollerToolbar toolbar;
    ToolbarHelper toolbarHelper;
    @BindView(2131363395)
    WebView webView;

    private class Online3DWebViewClient extends WebViewClient {
        private Online3DWebViewClient() {
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            DialogUtil.showNoConnectionInfo(Online3DSecureGateway.this, new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    Intent intent = new Intent();
                    intent.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED, false);
                    Online3DSecureGateway.this.setResult(-1, intent);
                    Online3DSecureGateway.this.finish();
                }
            });
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!NetworkHelper.isConnected(Online3DSecureGateway.this)) {
                DialogUtil.showNoConnectionInfo(Online3DSecureGateway.this, new SingleButtonCallback() {
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        Intent intent = new Intent();
                        intent.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED, false);
                        Online3DSecureGateway.this.setResult(-1, intent);
                        Online3DSecureGateway.this.finish();
                    }
                });
            }
            Timber.tag("WebClient").mo23221e(str, new Object[0]);
            if (str.equalsIgnoreCase(Constants.Applyze_Payment_Complete)) {
                Intent intent = new Intent();
                intent.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_SUCCESS, true);
                Online3DSecureGateway.this.setResult(-1, intent);
                Online3DSecureGateway.this.finish();
            } else if (str.equalsIgnoreCase(Constants.Applyze_Payment_Failed)) {
                Intent intent2 = new Intent();
                intent2.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED, false);
                Online3DSecureGateway.this.setResult(-1, intent2);
                Online3DSecureGateway.this.finish();
            }
            webView.loadUrl(str);
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!Online3DSecureGateway.this.isFinishing() && Online3DSecureGateway.this.progressViewHelper != null && Online3DSecureGateway.this.progressViewHelper.isShowing()) {
                Online3DSecureGateway.this.progressViewHelper.dismiss();
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            Log.e("onPageStarted", str);
            if (!Online3DSecureGateway.this.isFinishing() && Online3DSecureGateway.this.progressViewHelper != null && !Online3DSecureGateway.this.progressViewHelper.isShowing()) {
                Online3DSecureGateway.this.progressViewHelper.show();
            }
            if (str.equalsIgnoreCase(Constants.Applyze_Payment_Complete) || str.equalsIgnoreCase(Constants.Applyze_Payment_Complete_1) || str.contains(Constants.Applyze_Payment_Complete_2)) {
                Intent intent = new Intent();
                intent.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_SUCCESS, true);
                Online3DSecureGateway.this.setResult(-1, intent);
                Online3DSecureGateway.this.finish();
            } else if (str.equalsIgnoreCase(Constants.Applyze_Payment_Failed) || str.equalsIgnoreCase(Constants.Applyze_Payment_Failed_1) || str.contains(Constants.Applyze_Payment_Failed_2)) {
                Map hashMap = new HashMap();
                try {
                    hashMap = Online3DSecureGateway.splitQuery(new URL(str));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e2) {
                    e2.printStackTrace();
                }
                String str2 = "status";
                String str3 = hashMap.containsKey(str2) ? (String) hashMap.get(str2) : "";
                Intent intent2 = new Intent();
                intent2.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED_STATUS_CODE, str3);
                intent2.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED, true);
                Online3DSecureGateway.this.setResult(-1, intent2);
                Online3DSecureGateway.this.finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_online_3d_secure_web_view);
        ButterKnife.bind((Activity) this);
        this.mSharedPref = UtilManager.sharedPrefHelper();
        this.toolbarHelper = new ToolbarHelper(this.mSharedPref);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        ToolbarHelper toolbarHelper2 = this.toolbarHelper;
        ToolbarHelper.setToolbar(this, (Toolbar) findViewById(R.id.toolbar_top));
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        if (VERSION.SDK_INT >= 19) {
            this.toolbar.getNavigationIcon().setAutoMirrored(true);
        }
        String str = "";
        this.toolbar.setTitle((CharSequence) str);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Online3DSecureGateway.this.onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) str);
        this.toolbarHelper.setStatusBar(this);
        getSupportActionBar().setTitle((CharSequence) getString(R.string.e_commerce_payment_secure_payment_title));
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        this.paymentHtml = getIntent().getStringExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML);
        this.webView.setWebViewClient(new Online3DWebViewClient());
        settings.setDefaultTextEncodingName("utf-8");
        this.webView.loadDataWithBaseURL(null, this.paymentHtml, "text/html", "utf-8", null);
    }

    public void onBackPressed() {
        if (!isFinishing()) {
            ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
            if (progressViewHelper2 != null && progressViewHelper2.isShowing()) {
                this.progressViewHelper.dismiss();
            }
        }
        Intent intent = new Intent();
        intent.putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED, true);
        setResult(-1, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (!isFinishing()) {
            ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
            if (progressViewHelper2 != null && progressViewHelper2.isShowing()) {
                this.progressViewHelper.dismiss();
            }
        }
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        String[] split;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String query = url.getQuery();
        if (query != null) {
            for (String str : query.split("&")) {
                int indexOf = str.indexOf("=");
                String str2 = "UTF-8";
                linkedHashMap.put(URLDecoder.decode(str.substring(0, indexOf), str2), URLDecoder.decode(str.substring(indexOf + 1), str2));
            }
        }
        return linkedHashMap;
    }
}
