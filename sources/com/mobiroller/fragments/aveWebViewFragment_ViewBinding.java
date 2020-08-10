package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;

public class aveWebViewFragment_ViewBinding implements Unbinder {
    private aveWebViewFragment target;

    public aveWebViewFragment_ViewBinding(aveWebViewFragment avewebviewfragment, View view) {
        this.target = avewebviewfragment;
        avewebviewfragment.mWebView = (VideoEnabledWebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'mWebView'", VideoEnabledWebView.class);
        avewebviewfragment.relLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.web_layout, "field 'relLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveWebViewFragment avewebviewfragment = this.target;
        if (avewebviewfragment != null) {
            this.target = null;
            avewebviewfragment.mWebView = null;
            avewebviewfragment.relLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
