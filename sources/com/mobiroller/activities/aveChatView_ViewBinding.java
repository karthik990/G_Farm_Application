package com.mobiroller.activities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerFloatingActionButton;

public class aveChatView_ViewBinding implements Unbinder {
    private aveChatView target;
    private View view7f0a0244;

    public aveChatView_ViewBinding(aveChatView avechatview) {
        this(avechatview, avechatview.getWindow().getDecorView());
    }

    public aveChatView_ViewBinding(final aveChatView avechatview, View view) {
        this.target = avechatview;
        avechatview.frameContainer = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.chat_container, "field 'frameContainer'", FrameLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.fab, "field 'fabButton' and method 'fabOnClick'");
        avechatview.fabButton = (MobirollerFloatingActionButton) C0812Utils.castView(findRequiredView, R.id.fab, "field 'fabButton'", MobirollerFloatingActionButton.class);
        this.view7f0a0244 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avechatview.fabOnClick();
            }
        });
        avechatview.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avechatview.overlayLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveChatView avechatview = this.target;
        if (avechatview != null) {
            this.target = null;
            avechatview.frameContainer = null;
            avechatview.fabButton = null;
            avechatview.mainLayout = null;
            avechatview.overlayLayout = null;
            this.view7f0a0244.setOnClickListener(null);
            this.view7f0a0244 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
