package com.mobiroller.views;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class HeaderView_ViewBinding implements Unbinder {
    private HeaderView target;

    public HeaderView_ViewBinding(HeaderView headerView) {
        this(headerView, headerView);
    }

    public HeaderView_ViewBinding(HeaderView headerView, View view) {
        this.target = headerView;
        headerView.name = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.name, "field 'name'", TextView.class);
        headerView.lastSeen = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.last_seen, "field 'lastSeen'", TextView.class);
    }

    public void unbind() {
        HeaderView headerView = this.target;
        if (headerView != null) {
            this.target = null;
            headerView.name = null;
            headerView.lastSeen = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
