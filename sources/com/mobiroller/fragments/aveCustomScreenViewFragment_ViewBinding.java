package com.mobiroller.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;

public class aveCustomScreenViewFragment_ViewBinding implements Unbinder {
    private aveCustomScreenViewFragment target;

    public aveCustomScreenViewFragment_ViewBinding(aveCustomScreenViewFragment avecustomscreenviewfragment, View view) {
        this.target = avecustomscreenviewfragment;
        avecustomscreenviewfragment.mWebView = (VideoEnabledWebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'mWebView'", VideoEnabledWebView.class);
        avecustomscreenviewfragment.videoLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.videoLayout, "field 'videoLayout'", RelativeLayout.class);
        avecustomscreenviewfragment.webLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.web_layout, "field 'webLayout'", RelativeLayout.class);
        avecustomscreenviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avecustomscreenviewfragment.innerLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.inner_layout, "field 'innerLayout'", RelativeLayout.class);
        avecustomscreenviewfragment.nonVideoLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.nonVideoLayout, "field 'nonVideoLayout'", LinearLayout.class);
    }

    public void unbind() {
        aveCustomScreenViewFragment avecustomscreenviewfragment = this.target;
        if (avecustomscreenviewfragment != null) {
            this.target = null;
            avecustomscreenviewfragment.mWebView = null;
            avecustomscreenviewfragment.videoLayout = null;
            avecustomscreenviewfragment.webLayout = null;
            avecustomscreenviewfragment.mainLayout = null;
            avecustomscreenviewfragment.innerLayout = null;
            avecustomscreenviewfragment.nonVideoLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
