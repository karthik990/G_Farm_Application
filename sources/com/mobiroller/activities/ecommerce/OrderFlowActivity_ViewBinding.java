package com.mobiroller.activities.ecommerce;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.StatusViewScroller;
import com.mobiroller.views.custom.MobirollerToolbar;

public class OrderFlowActivity_ViewBinding implements Unbinder {
    private OrderFlowActivity target;

    public OrderFlowActivity_ViewBinding(OrderFlowActivity orderFlowActivity) {
        this(orderFlowActivity, orderFlowActivity.getWindow().getDecorView());
    }

    public OrderFlowActivity_ViewBinding(OrderFlowActivity orderFlowActivity, View view) {
        this.target = orderFlowActivity;
        orderFlowActivity.stepView = (StatusViewScroller) C0812Utils.findRequiredViewAsType(view, R.id.step_view, "field 'stepView'", StatusViewScroller.class);
        orderFlowActivity.stepViewMainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.step_view_main_layout, "field 'stepViewMainLayout'", ConstraintLayout.class);
        orderFlowActivity.frameLayout = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.frame_layout, "field 'frameLayout'", FrameLayout.class);
        orderFlowActivity.topTitleLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.top_title_layout, "field 'topTitleLayout'", RelativeLayout.class);
        orderFlowActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        orderFlowActivity.constraintLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.constraintLayout, "field 'constraintLayout'", ConstraintLayout.class);
        orderFlowActivity.nestedScrollView = (NestedScrollView) C0812Utils.findRequiredViewAsType(view, R.id.nested_scroll_view, "field 'nestedScrollView'", NestedScrollView.class);
    }

    public void unbind() {
        OrderFlowActivity orderFlowActivity = this.target;
        if (orderFlowActivity != null) {
            this.target = null;
            orderFlowActivity.stepView = null;
            orderFlowActivity.stepViewMainLayout = null;
            orderFlowActivity.frameLayout = null;
            orderFlowActivity.topTitleLayout = null;
            orderFlowActivity.toolbar = null;
            orderFlowActivity.constraintLayout = null;
            orderFlowActivity.nestedScrollView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
