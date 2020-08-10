package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveFavoriteViewFragment_ViewBinding implements Unbinder {
    private aveFavoriteViewFragment target;

    public aveFavoriteViewFragment_ViewBinding(aveFavoriteViewFragment avefavoriteviewfragment, View view) {
        this.target = avefavoriteviewfragment;
        avefavoriteviewfragment.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.favorite_recycler_view, "field 'recyclerView'", RecyclerView.class);
        avefavoriteviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.favorite_main_layout, "field 'mainLayout'", RelativeLayout.class);
        avefavoriteviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.favorite_overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        avefavoriteviewfragment.emptyView = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", RelativeLayout.class);
        avefavoriteviewfragment.emptyImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.favorite_empty_image, "field 'emptyImageView'", ImageView.class);
        avefavoriteviewfragment.emptyTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.favorite_empty_text, "field 'emptyTextView'", TextView.class);
    }

    public void unbind() {
        aveFavoriteViewFragment avefavoriteviewfragment = this.target;
        if (avefavoriteviewfragment != null) {
            this.target = null;
            avefavoriteviewfragment.recyclerView = null;
            avefavoriteviewfragment.mainLayout = null;
            avefavoriteviewfragment.overlayLayout = null;
            avefavoriteviewfragment.emptyView = null;
            avefavoriteviewfragment.emptyImageView = null;
            avefavoriteviewfragment.emptyTextView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
