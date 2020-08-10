package com.mobiroller.fragments;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerEmptyView;

public class aveECommerceViewFragment_ViewBinding implements Unbinder {
    private aveECommerceViewFragment target;

    public aveECommerceViewFragment_ViewBinding(aveECommerceViewFragment aveecommerceviewfragment, View view) {
        this.target = aveecommerceviewfragment;
        aveecommerceviewfragment.mRecyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'mRecyclerView'", RecyclerView.class);
        aveecommerceviewfragment.swipeRefreshLayout = (SwipeRefreshLayout) C0812Utils.findRequiredViewAsType(view, R.id.swipe_refresh_layout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        aveecommerceviewfragment.emptyView = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", MobirollerEmptyView.class);
    }

    public void unbind() {
        aveECommerceViewFragment aveecommerceviewfragment = this.target;
        if (aveecommerceviewfragment != null) {
            this.target = null;
            aveecommerceviewfragment.mRecyclerView = null;
            aveecommerceviewfragment.swipeRefreshLayout = null;
            aveecommerceviewfragment.emptyView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
