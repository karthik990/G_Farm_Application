package com.mobiroller.fragments.ecommerce;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductDescriptionBottomSheetFragment_ViewBinding implements Unbinder {
    private ProductDescriptionBottomSheetFragment target;

    public ProductDescriptionBottomSheetFragment_ViewBinding(ProductDescriptionBottomSheetFragment productDescriptionBottomSheetFragment, View view) {
        this.target = productDescriptionBottomSheetFragment;
        productDescriptionBottomSheetFragment.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        productDescriptionBottomSheetFragment.webView = (VideoEnabledWebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'webView'", VideoEnabledWebView.class);
    }

    public void unbind() {
        ProductDescriptionBottomSheetFragment productDescriptionBottomSheetFragment = this.target;
        if (productDescriptionBottomSheetFragment != null) {
            this.target = null;
            productDescriptionBottomSheetFragment.toolbar = null;
            productDescriptionBottomSheetFragment.webView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
