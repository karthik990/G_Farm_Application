package com.braintreepayments.api.threedsecure;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.braintreepayments.api.models.ThreeDSecureAuthenticationResponse;
import com.braintreepayments.api.models.ThreeDSecureLookup;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Stack;

@Deprecated
public class ThreeDSecureWebViewActivity extends Activity {
    public static final String EXTRA_THREE_D_SECURE_LOOKUP = "com.braintreepayments.api.EXTRA_THREE_D_SECURE_LOOKUP";
    public static final String EXTRA_THREE_D_SECURE_RESULT = "com.braintreepayments.api.EXTRA_THREE_D_SECURE_RESULT";
    private ActionBar mActionBar;
    private FrameLayout mRootView;
    private Stack<ThreeDSecureWebView> mThreeDSecureWebViews;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String str = "UTF-8";
        super.onCreate(bundle);
        requestWindowFeature(2);
        ThreeDSecureLookup threeDSecureLookup = (ThreeDSecureLookup) getIntent().getParcelableExtra(EXTRA_THREE_D_SECURE_LOOKUP);
        if (threeDSecureLookup != null) {
            setupActionBar();
            this.mThreeDSecureWebViews = new Stack<>();
            this.mRootView = (FrameLayout) findViewById(16908290);
            StringBuilder sb = new StringBuilder();
            try {
                sb.append("PaReq=");
                sb.append(URLEncoder.encode(threeDSecureLookup.getPareq(), str));
                sb.append("&MD=");
                sb.append(URLEncoder.encode(threeDSecureLookup.getMd(), str));
                sb.append("&TermUrl=");
                sb.append(URLEncoder.encode(threeDSecureLookup.getTermUrl(), str));
            } catch (UnsupportedEncodingException unused) {
                finish();
            }
            ThreeDSecureWebView threeDSecureWebView = new ThreeDSecureWebView(this);
            threeDSecureWebView.init(this);
            threeDSecureWebView.postUrl(threeDSecureLookup.getAcsUrl(), sb.toString().getBytes());
            pushNewWebView(threeDSecureWebView);
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("A ThreeDSecureLookup must be specified with ");
        sb2.append(ThreeDSecureLookup.class.getSimpleName());
        sb2.append(".EXTRA_THREE_D_SECURE_LOOKUP extra");
        throw new IllegalArgumentException(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public void pushNewWebView(ThreeDSecureWebView threeDSecureWebView) {
        this.mThreeDSecureWebViews.push(threeDSecureWebView);
        this.mRootView.removeAllViews();
        this.mRootView.addView(threeDSecureWebView);
    }

    /* access modifiers changed from: protected */
    public void popCurrentWebView() {
        this.mThreeDSecureWebViews.pop();
        pushNewWebView((ThreeDSecureWebView) this.mThreeDSecureWebViews.pop());
    }

    /* access modifiers changed from: protected */
    public void finishWithResult(ThreeDSecureAuthenticationResponse threeDSecureAuthenticationResponse) {
        setResult(-1, new Intent().putExtra(EXTRA_THREE_D_SECURE_RESULT, threeDSecureAuthenticationResponse));
        finish();
    }

    public void onBackPressed() {
        if (((ThreeDSecureWebView) this.mThreeDSecureWebViews.peek()).canGoBack()) {
            ((ThreeDSecureWebView) this.mThreeDSecureWebViews.peek()).goBack();
        } else if (this.mThreeDSecureWebViews.size() > 1) {
            popCurrentWebView();
        } else {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void setActionBarTitle(String str) {
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.setTitle(str);
        }
    }

    private void setupActionBar() {
        this.mActionBar = getActionBar();
        if (this.mActionBar != null) {
            setActionBarTitle("");
            this.mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        setResult(0);
        finish();
        return true;
    }
}
