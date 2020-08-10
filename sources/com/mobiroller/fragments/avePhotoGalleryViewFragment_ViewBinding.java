package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.twowayview.TwoWayView;

public class avePhotoGalleryViewFragment_ViewBinding implements Unbinder {
    private avePhotoGalleryViewFragment target;

    public avePhotoGalleryViewFragment_ViewBinding(avePhotoGalleryViewFragment avephotogalleryviewfragment, View view) {
        this.target = avephotogalleryviewfragment;
        avephotogalleryviewfragment.recyclerView = (TwoWayView) C0812Utils.findRequiredViewAsType(view, R.id.gallery_grid_view, "field 'recyclerView'", TwoWayView.class);
        avephotogalleryviewfragment.galleryRelativeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.gallery_layout, "field 'galleryRelativeLayout'", RelativeLayout.class);
    }

    public void unbind() {
        avePhotoGalleryViewFragment avephotogalleryviewfragment = this.target;
        if (avephotogalleryviewfragment != null) {
            this.target = null;
            avephotogalleryviewfragment.recyclerView = null;
            avephotogalleryviewfragment.galleryRelativeLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
