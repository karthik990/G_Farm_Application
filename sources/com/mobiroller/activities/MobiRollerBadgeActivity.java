package com.mobiroller.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.MobirollerBadgeModel;
import com.mobiroller.views.theme.Theme;

public class MobiRollerBadgeActivity extends AppCompatActivity {
    @BindView(2131362099)
    TextView buttonText;
    @BindView(2131362252)
    TextView description;
    @BindView(2131362260)
    ConstraintLayout designLayout;
    private MobirollerBadgeModel mMobirollerBadge;
    private String mWebUrl = "https://mobiroller.com";
    @BindView(2131363395)
    WebView webView;
    @BindView(2131363396)
    ConstraintLayout webViewLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_mobiroller_badge_layout);
        ButterKnife.bind((Activity) this);
        this.mMobirollerBadge = JSONStorage.getMobirollerBadgeModel();
        loadUi();
    }

    private void loadUi() {
        MobirollerBadgeModel mobirollerBadgeModel = this.mMobirollerBadge;
        if (mobirollerBadgeModel != null) {
            String str = "{package_name}";
            if (mobirollerBadgeModel.displayUrl != null) {
                if (this.mMobirollerBadge.displayUrl.contains(str)) {
                    MobirollerBadgeModel mobirollerBadgeModel2 = this.mMobirollerBadge;
                    mobirollerBadgeModel2.displayUrl = mobirollerBadgeModel2.displayUrl.replace(str, getPackageName());
                }
                this.webView.loadUrl(this.mMobirollerBadge.displayUrl);
                this.webViewLayout.setVisibility(0);
                this.designLayout.setVisibility(8);
            } else if (this.mMobirollerBadge.design != null) {
                if (this.mMobirollerBadge.design.buttonText != null) {
                    this.buttonText.setText(this.mMobirollerBadge.design.buttonText);
                }
                if (this.mMobirollerBadge.design.content != null) {
                    this.description.setText(this.mMobirollerBadge.design.content);
                }
                if (this.mMobirollerBadge.design.launchUrl != null) {
                    if (this.mMobirollerBadge.design.launchUrl.contains(str)) {
                        this.mMobirollerBadge.design.launchUrl = this.mMobirollerBadge.design.launchUrl.replace(str, getPackageName());
                    }
                    this.mWebUrl = URLUtil.guessUrl(this.mMobirollerBadge.design.launchUrl);
                }
            }
        }
    }

    @OnClick({2131362036})
    public void onClickGoButton() {
        Bundle bundle = new Bundle();
        bundle.putString("app_name", getString(R.string.app_name));
        bundle.putString("package", getPackageName());
        FirebaseAnalytics.getInstance(this).logEvent("badge_detail_click", bundle);
        Builder builder = new Builder();
        builder.setToolbarColor(Theme.primaryColor);
        builder.setShowTitle(true);
        builder.addDefaultShareMenuItem();
        builder.build().launchUrl(this, Uri.parse(this.mWebUrl));
    }

    @OnClick({2131362167})
    public void onClickCloseButton() {
        onBackPressed();
    }
}
