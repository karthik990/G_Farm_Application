package com.mobiroller.fragments.ecommerce;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerTextView;

public class OrderChoosePaymentFragment_ViewBinding implements Unbinder {
    private OrderChoosePaymentFragment target;
    private View view7f0a019a;
    private View view7f0a0458;

    public OrderChoosePaymentFragment_ViewBinding(final OrderChoosePaymentFragment orderChoosePaymentFragment, View view) {
        this.target = orderChoosePaymentFragment;
        orderChoosePaymentFragment.creditCardViewStub = (ViewStub) C0812Utils.findRequiredViewAsType(view, R.id.e_commerce_payment_credit_card_layout_group, "field 'creditCardViewStub'", ViewStub.class);
        orderChoosePaymentFragment.creditCardLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.credit_card_layout, "field 'creditCardLayout'", ConstraintLayout.class);
        orderChoosePaymentFragment.payAtDoorLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.pay_at_door_layout, "field 'payAtDoorLayout'", ConstraintLayout.class);
        orderChoosePaymentFragment.payAtDoorDesc = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.pay_at_door_desc, "field 'payAtDoorDesc'", TextView.class);
        orderChoosePaymentFragment.bankAccountList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.bank_account_list, "field 'bankAccountList'", RecyclerView.class);
        orderChoosePaymentFragment.transferLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.transfer_layout, "field 'transferLayout'", ConstraintLayout.class);
        orderChoosePaymentFragment.bottomLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.bottom_layout, "field 'bottomLayout'", ConstraintLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.continue_button, "field 'continueButton' and method 'onViewClicked'");
        orderChoosePaymentFragment.continueButton = (FloatingActionButton) C0812Utils.castView(findRequiredView, R.id.continue_button, "field 'continueButton'", FloatingActionButton.class);
        this.view7f0a019a = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChoosePaymentFragment.onViewClicked();
            }
        });
        orderChoosePaymentFragment.paymentTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_text_view, "field 'paymentTextView'", MobirollerTextView.class);
        orderChoosePaymentFragment.paymentMethodTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_method_text_view, "field 'paymentMethodTextView'", MobirollerTextView.class);
        orderChoosePaymentFragment.emptyView = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", MobirollerEmptyView.class);
        orderChoosePaymentFragment.contentLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_layout, "field 'contentLayout'", ConstraintLayout.class);
        orderChoosePaymentFragment.payPalLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.paypal_layout, "field 'payPalLayout'", ConstraintLayout.class);
        orderChoosePaymentFragment.payPalDescriptionTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.paypal_desc, "field 'payPalDescriptionTextView'", TextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.payment_method_selection_layout, "method 'onClickPaymentTypeSelection'");
        this.view7f0a0458 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChoosePaymentFragment.onClickPaymentTypeSelection();
            }
        });
    }

    public void unbind() {
        OrderChoosePaymentFragment orderChoosePaymentFragment = this.target;
        if (orderChoosePaymentFragment != null) {
            this.target = null;
            orderChoosePaymentFragment.creditCardViewStub = null;
            orderChoosePaymentFragment.creditCardLayout = null;
            orderChoosePaymentFragment.payAtDoorLayout = null;
            orderChoosePaymentFragment.payAtDoorDesc = null;
            orderChoosePaymentFragment.bankAccountList = null;
            orderChoosePaymentFragment.transferLayout = null;
            orderChoosePaymentFragment.bottomLayout = null;
            orderChoosePaymentFragment.continueButton = null;
            orderChoosePaymentFragment.paymentTextView = null;
            orderChoosePaymentFragment.paymentMethodTextView = null;
            orderChoosePaymentFragment.emptyView = null;
            orderChoosePaymentFragment.contentLayout = null;
            orderChoosePaymentFragment.payPalLayout = null;
            orderChoosePaymentFragment.payPalDescriptionTextView = null;
            this.view7f0a019a.setOnClickListener(null);
            this.view7f0a019a = null;
            this.view7f0a0458.setOnClickListener(null);
            this.view7f0a0458 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
