package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductDescriptionActivity extends ECommerceBaseActivity {
    public static String INTENT_PRODUCT_DESCRIPTION = "ProductDescription";
    public String description = "";
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @BindView(2131363395)
    VideoEnabledWebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_e_commerce_product_description);
        ButterKnife.bind((Activity) this);
        new ToolbarHelper().setStatusBar(this);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_close_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductDescriptionActivity.this.onBackPressed();
            }
        });
        setTitle(getString(R.string.e_commerce_product_detail_description));
        if (getIntent().hasExtra(INTENT_PRODUCT_DESCRIPTION)) {
            this.description = getIntent().getStringExtra(INTENT_PRODUCT_DESCRIPTION);
        }
        if (VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
            this.webView.setLayerType(2, null);
        } else if (VERSION.SDK_INT >= 19) {
            this.webView.setLayerType(2, null);
        } else {
            this.webView.setLayerType(1, null);
        }
        VideoEnabledWebView videoEnabledWebView = this.webView;
        StringBuilder sb = new StringBuilder();
        sb.append("<style>img{display: inline;height: auto;max-width: 100%;}</style>");
        sb.append(this.description);
        videoEnabledWebView.loadDataWithBaseURL("", sb.toString(), "text/html", "UTF-8", "");
    }
}
