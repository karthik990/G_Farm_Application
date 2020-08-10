package com.mobiroller.viewholders.user;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class OrderViewHolder_ViewBinding implements Unbinder {
    private OrderViewHolder target;
    private View view7f0a0359;

    public OrderViewHolder_ViewBinding(final OrderViewHolder orderViewHolder, View view) {
        this.target = orderViewHolder;
        orderViewHolder.orderNumber = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_number, "field 'orderNumber'", MobirollerTextView.class);
        orderViewHolder.orderDate = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_date, "field 'orderDate'", MobirollerTextView.class);
        orderViewHolder.orderTime = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_time, "field 'orderTime'", MobirollerTextView.class);
        orderViewHolder.orderPaid = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_paid, "field 'orderPaid'", MobirollerTextView.class);
        orderViewHolder.orderStatusBackground = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.status_background_view, "field 'orderStatusBackground'", ConstraintLayout.class);
        orderViewHolder.orderStatusTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.status_text_view, "field 'orderStatusTextView'", MobirollerTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.main_layout, "method 'onClickOrderDetail'");
        this.view7f0a0359 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderViewHolder.onClickOrderDetail();
            }
        });
    }

    public void unbind() {
        OrderViewHolder orderViewHolder = this.target;
        if (orderViewHolder != null) {
            this.target = null;
            orderViewHolder.orderNumber = null;
            orderViewHolder.orderDate = null;
            orderViewHolder.orderTime = null;
            orderViewHolder.orderPaid = null;
            orderViewHolder.orderStatusBackground = null;
            orderViewHolder.orderStatusTextView = null;
            this.view7f0a0359.setOnClickListener(null);
            this.view7f0a0359 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
