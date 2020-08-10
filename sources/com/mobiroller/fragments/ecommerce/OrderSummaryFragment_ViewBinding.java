package com.mobiroller.fragments.ecommerce;

import android.view.View;
import android.widget.CheckBox;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerTextView;

public class OrderSummaryFragment_ViewBinding implements Unbinder {
    private OrderSummaryFragment target;
    private View view7f0a00c1;
    private View view7f0a014d;
    private View view7f0a0186;
    private View view7f0a01c2;
    private View view7f0a0452;

    public OrderSummaryFragment_ViewBinding(final OrderSummaryFragment orderSummaryFragment, View view) {
        this.target = orderSummaryFragment;
        orderSummaryFragment.agreementDescription = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.agreement_description, "field 'agreementDescription'", MobirollerTextView.class);
        orderSummaryFragment.orderNoteTextInputEditText = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.orderNoteTextInputEditText, "field 'orderNoteTextInputEditText'", TextInputEditText.class);
        orderSummaryFragment.noteLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.note_layout, "field 'noteLayout'", CardView.class);
        orderSummaryFragment.agreement = (CheckBox) C0812Utils.findRequiredViewAsType(view, R.id.agreement, "field 'agreement'", CheckBox.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.confirm_button, "field 'confirmButton' and method 'onClickConfirmButton'");
        orderSummaryFragment.confirmButton = (MobirollerButton) C0812Utils.castView(findRequiredView, R.id.confirm_button, "field 'confirmButton'", MobirollerButton.class);
        this.view7f0a0186 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderSummaryFragment.onClickConfirmButton();
            }
        });
        orderSummaryFragment.mainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", ConstraintLayout.class);
        orderSummaryFragment.bottomLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.bottom_layout, "field 'bottomLayout'", ConstraintLayout.class);
        orderSummaryFragment.loadingAnimation = (LottieAnimationView) C0812Utils.findRequiredViewAsType(view, R.id.loading_animation, "field 'loadingAnimation'", LottieAnimationView.class);
        orderSummaryFragment.productSubTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.product_total_description_text_view, "field 'productSubTotalTextView'", MobirollerTextView.class);
        orderSummaryFragment.shippingTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_total_description_text_view, "field 'shippingTotalTextView'", MobirollerTextView.class);
        orderSummaryFragment.totalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.total, "field 'totalTextView'", MobirollerTextView.class);
        orderSummaryFragment.paymentDescriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_description_text_view, "field 'paymentDescriptionTextView'", MobirollerTextView.class);
        orderSummaryFragment.billingDescriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_description_text_view, "field 'billingDescriptionTextView'", MobirollerTextView.class);
        orderSummaryFragment.shippingDescriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_description_text_view, "field 'shippingDescriptionTextView'", MobirollerTextView.class);
        orderSummaryFragment.cardDescriptionTExtView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.cart_description_text_view, "field 'cardDescriptionTExtView'", MobirollerTextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.cart_grid_layout, "method 'onClickCartLayout'");
        this.view7f0a014d = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderSummaryFragment.onClickCartLayout();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.payment_grid_layout, "method 'onClickPaymentLayout'");
        this.view7f0a0452 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderSummaryFragment.onClickPaymentLayout();
            }
        });
        String str = "method 'onClickAddressLayout'";
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.billing_address_grid_layout, str);
        this.view7f0a00c1 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderSummaryFragment.onClickAddressLayout();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.delivery_address_layout, str);
        this.view7f0a01c2 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderSummaryFragment.onClickAddressLayout();
            }
        });
    }

    public void unbind() {
        OrderSummaryFragment orderSummaryFragment = this.target;
        if (orderSummaryFragment != null) {
            this.target = null;
            orderSummaryFragment.agreementDescription = null;
            orderSummaryFragment.orderNoteTextInputEditText = null;
            orderSummaryFragment.noteLayout = null;
            orderSummaryFragment.agreement = null;
            orderSummaryFragment.confirmButton = null;
            orderSummaryFragment.mainLayout = null;
            orderSummaryFragment.bottomLayout = null;
            orderSummaryFragment.loadingAnimation = null;
            orderSummaryFragment.productSubTotalTextView = null;
            orderSummaryFragment.shippingTotalTextView = null;
            orderSummaryFragment.totalTextView = null;
            orderSummaryFragment.paymentDescriptionTextView = null;
            orderSummaryFragment.billingDescriptionTextView = null;
            orderSummaryFragment.shippingDescriptionTextView = null;
            orderSummaryFragment.cardDescriptionTExtView = null;
            this.view7f0a0186.setOnClickListener(null);
            this.view7f0a0186 = null;
            this.view7f0a014d.setOnClickListener(null);
            this.view7f0a014d = null;
            this.view7f0a0452.setOnClickListener(null);
            this.view7f0a0452 = null;
            this.view7f0a00c1.setOnClickListener(null);
            this.view7f0a00c1 = null;
            this.view7f0a01c2.setOnClickListener(null);
            this.view7f0a01c2 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
