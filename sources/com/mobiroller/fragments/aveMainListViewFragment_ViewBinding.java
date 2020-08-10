package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveMainListViewFragment_ViewBinding implements Unbinder {
    private aveMainListViewFragment target;

    public aveMainListViewFragment_ViewBinding(aveMainListViewFragment avemainlistviewfragment, View view) {
        this.target = avemainlistviewfragment;
        avemainlistviewfragment.imgView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.content_img, "field 'imgView'", ImageView.class);
        avemainlistviewfragment.textView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.content_text, "field 'textView'", TextView.class);
        avemainlistviewfragment.scrollView = (ScrollView) C0812Utils.findRequiredViewAsType(view, R.id.scroll_text, "field 'scrollView'", ScrollView.class);
        avemainlistviewfragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.content_list, "field 'list'", RecyclerView.class);
        avemainlistviewfragment.contentListLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_list_layout, "field 'contentListLayout'", LinearLayout.class);
        avemainlistviewfragment.contentLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_layout, "field 'contentLayout'", RelativeLayout.class);
        avemainlistviewfragment.contentOverlay = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_overlay, "field 'contentOverlay'", RelativeLayout.class);
    }

    public void unbind() {
        aveMainListViewFragment avemainlistviewfragment = this.target;
        if (avemainlistviewfragment != null) {
            this.target = null;
            avemainlistviewfragment.imgView = null;
            avemainlistviewfragment.textView = null;
            avemainlistviewfragment.scrollView = null;
            avemainlistviewfragment.list = null;
            avemainlistviewfragment.contentListLayout = null;
            avemainlistviewfragment.contentLayout = null;
            avemainlistviewfragment.contentOverlay = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
