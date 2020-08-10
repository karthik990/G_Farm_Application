package com.mobiroller.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveTweetViewFragment_ViewBinding implements Unbinder {
    private aveTweetViewFragment target;

    public aveTweetViewFragment_ViewBinding(aveTweetViewFragment avetweetviewfragment, View view) {
        this.target = avetweetviewfragment;
        avetweetviewfragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.twitter_list, "field 'list'", RecyclerView.class);
        avetweetviewfragment.twitterLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.twitter_layout, "field 'twitterLayout'", RelativeLayout.class);
        avetweetviewfragment.twitter_relative_layout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.twitter_relative_overlay, "field 'twitter_relative_layout'", RelativeLayout.class);
    }

    public void unbind() {
        aveTweetViewFragment avetweetviewfragment = this.target;
        if (avetweetviewfragment != null) {
            this.target = null;
            avetweetviewfragment.list = null;
            avetweetviewfragment.twitterLayout = null;
            avetweetviewfragment.twitter_relative_layout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
