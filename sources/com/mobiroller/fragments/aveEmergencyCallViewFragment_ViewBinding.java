package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveEmergencyCallViewFragment_ViewBinding implements Unbinder {
    private aveEmergencyCallViewFragment target;

    public aveEmergencyCallViewFragment_ViewBinding(aveEmergencyCallViewFragment aveemergencycallviewfragment, View view) {
        this.target = aveemergencycallviewfragment;
        aveemergencycallviewfragment.recyclerView = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.faq_recycler_view, "field 'recyclerView'", RecyclerView.class);
        aveemergencycallviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.faq_main_layout, "field 'mainLayout'", RelativeLayout.class);
        aveemergencycallviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveEmergencyCallViewFragment aveemergencycallviewfragment = this.target;
        if (aveemergencycallviewfragment != null) {
            this.target = null;
            aveemergencycallviewfragment.recyclerView = null;
            aveemergencycallviewfragment.mainLayout = null;
            aveemergencycallviewfragment.overlayLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
