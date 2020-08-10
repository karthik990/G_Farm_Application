package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveFAQViewFragment_ViewBinding implements Unbinder {
    private aveFAQViewFragment target;

    public aveFAQViewFragment_ViewBinding(aveFAQViewFragment avefaqviewfragment, View view) {
        this.target = avefaqviewfragment;
        avefaqviewfragment.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.faq_recycler_view, "field 'recyclerView'", RecyclerView.class);
        avefaqviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.faq_main_layout, "field 'mainLayout'", RelativeLayout.class);
        avefaqviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveFAQViewFragment avefaqviewfragment = this.target;
        if (avefaqviewfragment != null) {
            this.target = null;
            avefaqviewfragment.recyclerView = null;
            avefaqviewfragment.mainLayout = null;
            avefaqviewfragment.overlayLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
