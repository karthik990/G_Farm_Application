package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.gms.maps.MapView;
import com.mobiroller.mobi942763453128.R;

public class aveMapViewFragment_ViewBinding implements Unbinder {
    private aveMapViewFragment target;

    public aveMapViewFragment_ViewBinding(aveMapViewFragment avemapviewfragment, View view) {
        this.target = avemapviewfragment;
        avemapviewfragment.mapView = (MapView) C0812Utils.findRequiredViewAsType(view, R.id.mapView, "field 'mapView'", MapView.class);
        avemapviewfragment.mapLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.map_layout, "field 'mapLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveMapViewFragment avemapviewfragment = this.target;
        if (avemapviewfragment != null) {
            this.target = null;
            avemapviewfragment.mapView = null;
            avemapviewfragment.mapLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
