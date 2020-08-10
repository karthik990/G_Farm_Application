package com.mobiroller.fragments.ecommerce;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;

public class OrderChooseAddressFragment_ViewBinding implements Unbinder {
    private OrderChooseAddressFragment target;
    private View view7f0a0041;
    private View view7f0a0042;
    private View view7f0a0043;
    private View view7f0a0044;
    private View view7f0a00be;
    private View view7f0a00e0;
    private View view7f0a019a;
    private View view7f0a019b;
    private View view7f0a042b;
    private View view7f0a042c;
    private View view7f0a0535;
    private View view7f0a05d1;

    public OrderChooseAddressFragment_ViewBinding(final OrderChooseAddressFragment orderChooseAddressFragment, View view) {
        this.target = orderChooseAddressFragment;
        orderChooseAddressFragment.deliveryTitleLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.delivery_title_layout, "field 'deliveryTitleLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.deliveryEmptyLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.delivery_empty_layout, "field 'deliveryEmptyLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.deliveryContentLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.delivery_content_layout, "field 'deliveryContentLayout'", ConstraintLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.other_shipping_addresses, "field 'otherShippingAddresses' and method 'onViewClicked'");
        orderChooseAddressFragment.otherShippingAddresses = (MobirollerTextView) C0812Utils.castView(findRequiredView, R.id.other_shipping_addresses, "field 'otherShippingAddresses'", MobirollerTextView.class);
        this.view7f0a042c = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onViewClicked(view);
            }
        });
        orderChooseAddressFragment.deliveryLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.delivery_layout, "field 'deliveryLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.billingTitleLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.billing_title_layout, "field 'billingTitleLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.billingEmptyLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.billing_empty_layout, "field 'billingEmptyLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.billingContentLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.billing_content_layout, "field 'billingContentLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.billingLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.billing_layout, "field 'billingLayout'", ConstraintLayout.class);
        orderChooseAddressFragment.shippingAddressTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_address_title, "field 'shippingAddressTitle'", MobirollerTextView.class);
        orderChooseAddressFragment.shippingAddressDescription = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_address_description, "field 'shippingAddressDescription'", MobirollerTextView.class);
        orderChooseAddressFragment.shippingAddressDescriptionFirst = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_address_description_one, "field 'shippingAddressDescriptionFirst'", MobirollerTextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.other_billing_addresses, "field 'otherBillingAddresses' and method 'onViewClicked'");
        orderChooseAddressFragment.otherBillingAddresses = (MobirollerTextView) C0812Utils.castView(findRequiredView2, R.id.other_billing_addresses, "field 'otherBillingAddresses'", MobirollerTextView.class);
        this.view7f0a042b = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onViewClicked(view);
            }
        });
        orderChooseAddressFragment.billingAddressTitle = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_address_title, "field 'billingAddressTitle'", TextView.class);
        orderChooseAddressFragment.billingAddressDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_address_description, "field 'billingAddressDescription'", TextView.class);
        orderChooseAddressFragment.billingAddressDescriptionFirst = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_address_description_one, "field 'billingAddressDescriptionFirst'", MobirollerTextView.class);
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.action_add_new_shipping_address, "field 'actionAddNewShippingAddress' and method 'onClickNewShipping'");
        orderChooseAddressFragment.actionAddNewShippingAddress = (MobirollerTextView) C0812Utils.castView(findRequiredView3, R.id.action_add_new_shipping_address, "field 'actionAddNewShippingAddress'", MobirollerTextView.class);
        this.view7f0a0043 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickNewShipping();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.action_add_new_billing_address, "field 'actionAddNewBillingAddress' and method 'onClickNewBilling'");
        orderChooseAddressFragment.actionAddNewBillingAddress = (MobirollerTextView) C0812Utils.castView(findRequiredView4, R.id.action_add_new_billing_address, "field 'actionAddNewBillingAddress'", MobirollerTextView.class);
        this.view7f0a0041 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickNewBilling();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.continue_button, "field 'continueButton' and method 'onViewClicked'");
        orderChooseAddressFragment.continueButton = (FloatingActionButton) C0812Utils.castView(findRequiredView5, R.id.continue_button, "field 'continueButton'", FloatingActionButton.class);
        this.view7f0a019a = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onViewClicked();
            }
        });
        View findRequiredView6 = C0812Utils.findRequiredView(view, R.id.bottom_layout, "field 'bottomLayout' and method 'onTouchEvent'");
        orderChooseAddressFragment.bottomLayout = (ConstraintLayout) C0812Utils.castView(findRequiredView6, R.id.bottom_layout, "field 'bottomLayout'", ConstraintLayout.class);
        this.view7f0a00e0 = findRequiredView6;
        findRequiredView6.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return orderChooseAddressFragment.onTouchEvent(motionEvent);
            }
        });
        View findRequiredView7 = C0812Utils.findRequiredView(view, R.id.action_add_new_shipping_address_empty, "method 'onClickNewShipping'");
        this.view7f0a0044 = findRequiredView7;
        findRequiredView7.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickNewShipping();
            }
        });
        View findRequiredView8 = C0812Utils.findRequiredView(view, R.id.action_add_new_billing_address_empty, "method 'onClickNewBilling'");
        this.view7f0a0042 = findRequiredView8;
        findRequiredView8.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickNewBilling();
            }
        });
        View findRequiredView9 = C0812Utils.findRequiredView(view, R.id.billing_address_action_edit, "method 'onClickBillingEdit'");
        this.view7f0a00be = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickBillingEdit();
            }
        });
        View findRequiredView10 = C0812Utils.findRequiredView(view, R.id.shipping_address_action_edit, "method 'onClickShippingEdit'");
        this.view7f0a0535 = findRequiredView10;
        findRequiredView10.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderChooseAddressFragment.onClickShippingEdit();
            }
        });
        String str = "method 'onTouchEvent'";
        View findRequiredView11 = C0812Utils.findRequiredView(view, R.id.continue_layout, str);
        this.view7f0a019b = findRequiredView11;
        findRequiredView11.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return orderChooseAddressFragment.onTouchEvent(motionEvent);
            }
        });
        View findRequiredView12 = C0812Utils.findRequiredView(view, R.id.top_layout, str);
        this.view7f0a05d1 = findRequiredView12;
        findRequiredView12.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return orderChooseAddressFragment.onTouchEvent(motionEvent);
            }
        });
    }

    public void unbind() {
        OrderChooseAddressFragment orderChooseAddressFragment = this.target;
        if (orderChooseAddressFragment != null) {
            this.target = null;
            orderChooseAddressFragment.deliveryTitleLayout = null;
            orderChooseAddressFragment.deliveryEmptyLayout = null;
            orderChooseAddressFragment.deliveryContentLayout = null;
            orderChooseAddressFragment.otherShippingAddresses = null;
            orderChooseAddressFragment.deliveryLayout = null;
            orderChooseAddressFragment.billingTitleLayout = null;
            orderChooseAddressFragment.billingEmptyLayout = null;
            orderChooseAddressFragment.billingContentLayout = null;
            orderChooseAddressFragment.billingLayout = null;
            orderChooseAddressFragment.shippingAddressTitle = null;
            orderChooseAddressFragment.shippingAddressDescription = null;
            orderChooseAddressFragment.shippingAddressDescriptionFirst = null;
            orderChooseAddressFragment.otherBillingAddresses = null;
            orderChooseAddressFragment.billingAddressTitle = null;
            orderChooseAddressFragment.billingAddressDescription = null;
            orderChooseAddressFragment.billingAddressDescriptionFirst = null;
            orderChooseAddressFragment.actionAddNewShippingAddress = null;
            orderChooseAddressFragment.actionAddNewBillingAddress = null;
            orderChooseAddressFragment.continueButton = null;
            orderChooseAddressFragment.bottomLayout = null;
            this.view7f0a042c.setOnClickListener(null);
            this.view7f0a042c = null;
            this.view7f0a042b.setOnClickListener(null);
            this.view7f0a042b = null;
            this.view7f0a0043.setOnClickListener(null);
            this.view7f0a0043 = null;
            this.view7f0a0041.setOnClickListener(null);
            this.view7f0a0041 = null;
            this.view7f0a019a.setOnClickListener(null);
            this.view7f0a019a = null;
            this.view7f0a00e0.setOnTouchListener(null);
            this.view7f0a00e0 = null;
            this.view7f0a0044.setOnClickListener(null);
            this.view7f0a0044 = null;
            this.view7f0a0042.setOnClickListener(null);
            this.view7f0a0042 = null;
            this.view7f0a00be.setOnClickListener(null);
            this.view7f0a00be = null;
            this.view7f0a0535.setOnClickListener(null);
            this.view7f0a0535 = null;
            this.view7f0a019b.setOnTouchListener(null);
            this.view7f0a019b = null;
            this.view7f0a05d1.setOnTouchListener(null);
            this.view7f0a05d1 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
