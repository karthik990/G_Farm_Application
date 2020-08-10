package com.mobiroller.activities.ecommerce;

import android.view.View;
import android.webkit.WebView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class Online3DSecureGateway_ViewBinding implements Unbinder {
    private Online3DSecureGateway target;

    public Online3DSecureGateway_ViewBinding(Online3DSecureGateway online3DSecureGateway) {
        this(online3DSecureGateway, online3DSecureGateway.getWindow().getDecorView());
    }

    public Online3DSecureGateway_ViewBinding(Online3DSecureGateway online3DSecureGateway, View view) {
        this.target = online3DSecureGateway;
        online3DSecureGateway.webView = (WebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'webView'", WebView.class);
        online3DSecureGateway.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", MobirollerToolbar.class);
    }

    public void unbind() {
        Online3DSecureGateway online3DSecureGateway = this.target;
        if (online3DSecureGateway != null) {
            this.target = null;
            online3DSecureGateway.webView = null;
            online3DSecureGateway.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
