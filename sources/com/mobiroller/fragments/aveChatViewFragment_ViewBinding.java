package com.mobiroller.fragments;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerFloatingActionButton;

public class aveChatViewFragment_ViewBinding implements Unbinder {
    private aveChatViewFragment target;
    private View view7f0a0244;

    public aveChatViewFragment_ViewBinding(final aveChatViewFragment avechatviewfragment, View view) {
        this.target = avechatviewfragment;
        avechatviewfragment.toolbarTop = (Toolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbarTop'", Toolbar.class);
        avechatviewfragment.frameContainer = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_container_fragment, "field 'frameContainer'", FrameLayout.class);
        avechatviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avechatviewfragment.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.fab, "field 'fabButton' and method 'openUserListActivity'");
        avechatviewfragment.fabButton = (MobirollerFloatingActionButton) C0812Utils.castView(findRequiredView, R.id.fab, "field 'fabButton'", MobirollerFloatingActionButton.class);
        this.view7f0a0244 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avechatviewfragment.openUserListActivity();
            }
        });
    }

    public void unbind() {
        aveChatViewFragment avechatviewfragment = this.target;
        if (avechatviewfragment != null) {
            this.target = null;
            avechatviewfragment.toolbarTop = null;
            avechatviewfragment.frameContainer = null;
            avechatviewfragment.mainLayout = null;
            avechatviewfragment.overlayLayout = null;
            avechatviewfragment.fabButton = null;
            this.view7f0a0244.setOnClickListener(null);
            this.view7f0a0244 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
