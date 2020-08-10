package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CustomHorizontalScrollView;

public class aveFormViewFragment_ViewBinding implements Unbinder {
    private aveFormViewFragment target;

    public aveFormViewFragment_ViewBinding(aveFormViewFragment aveformviewfragment, View view) {
        this.target = aveformviewfragment;
        aveformviewfragment.imgView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.form_img, "field 'imgView'", ImageView.class);
        aveformviewfragment.mFormText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.form_text, "field 'mFormText'", TextView.class);
        aveformviewfragment.scrollView = (CustomHorizontalScrollView) C0812Utils.findRequiredViewAsType(view, R.id.form_scroll_text, "field 'scrollView'", CustomHorizontalScrollView.class);
        aveformviewfragment.formLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_layout, "field 'formLayout'", RelativeLayout.class);
        aveformviewfragment.formLayoutOverlay = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_layout_overlay, "field 'formLayoutOverlay'", RelativeLayout.class);
        aveformviewfragment.formMainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.form_main_layout, "field 'formMainLayout'", RelativeLayout.class);
        aveformviewfragment.formList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.form_list, "field 'formList'", RecyclerView.class);
    }

    public void unbind() {
        aveFormViewFragment aveformviewfragment = this.target;
        if (aveformviewfragment != null) {
            this.target = null;
            aveformviewfragment.imgView = null;
            aveformviewfragment.mFormText = null;
            aveformviewfragment.scrollView = null;
            aveformviewfragment.formLayout = null;
            aveformviewfragment.formLayoutOverlay = null;
            aveformviewfragment.formMainLayout = null;
            aveformviewfragment.formList = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
