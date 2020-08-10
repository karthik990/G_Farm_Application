package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class PaymentTypeViewHolder_ViewBinding implements Unbinder {
    private PaymentTypeViewHolder target;

    public PaymentTypeViewHolder_ViewBinding(PaymentTypeViewHolder paymentTypeViewHolder, View view) {
        this.target = paymentTypeViewHolder;
        paymentTypeViewHolder.paymentTypeIcon = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.payment_type_icon, "field 'paymentTypeIcon'", ImageView.class);
        paymentTypeViewHolder.paymentTypeName = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_type_name, "field 'paymentTypeName'", MobirollerTextView.class);
    }

    public void unbind() {
        PaymentTypeViewHolder paymentTypeViewHolder = this.target;
        if (paymentTypeViewHolder != null) {
            this.target = null;
            paymentTypeViewHolder.paymentTypeIcon = null;
            paymentTypeViewHolder.paymentTypeName = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
