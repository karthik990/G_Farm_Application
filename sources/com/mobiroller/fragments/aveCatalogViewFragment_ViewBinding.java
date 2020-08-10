package com.mobiroller.fragments;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveCatalogViewFragment_ViewBinding implements Unbinder {
    private aveCatalogViewFragment target;

    public aveCatalogViewFragment_ViewBinding(aveCatalogViewFragment avecatalogviewfragment, View view) {
        this.target = avecatalogviewfragment;
        avecatalogviewfragment.childContainer = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.child_container, "field 'childContainer'", FrameLayout.class);
        avecatalogviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avecatalogviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveCatalogViewFragment avecatalogviewfragment = this.target;
        if (avecatalogviewfragment != null) {
            this.target = null;
            avecatalogviewfragment.childContainer = null;
            avecatalogviewfragment.mainLayout = null;
            avecatalogviewfragment.overlayLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
