package com.mobiroller.activities.ecommerce;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductDescriptionActivity_ViewBinding implements Unbinder {
    private ProductDescriptionActivity target;

    public ProductDescriptionActivity_ViewBinding(ProductDescriptionActivity productDescriptionActivity) {
        this(productDescriptionActivity, productDescriptionActivity.getWindow().getDecorView());
    }

    public ProductDescriptionActivity_ViewBinding(ProductDescriptionActivity productDescriptionActivity, View view) {
        this.target = productDescriptionActivity;
        productDescriptionActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        productDescriptionActivity.webView = (VideoEnabledWebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'webView'", VideoEnabledWebView.class);
    }

    public void unbind() {
        ProductDescriptionActivity productDescriptionActivity = this.target;
        if (productDescriptionActivity != null) {
            this.target = null;
            productDescriptionActivity.toolbar = null;
            productDescriptionActivity.webView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
