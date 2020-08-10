package com.mobiroller.activities.ecommerce;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class OrderDetailsActivity_ViewBinding implements Unbinder {
    private OrderDetailsActivity target;
    private View view7f0a00b4;
    private View view7f0a014a;

    public OrderDetailsActivity_ViewBinding(OrderDetailsActivity orderDetailsActivity) {
        this(orderDetailsActivity, orderDetailsActivity.getWindow().getDecorView());
    }

    public OrderDetailsActivity_ViewBinding(final OrderDetailsActivity orderDetailsActivity, View view) {
        this.target = orderDetailsActivity;
        orderDetailsActivity.orderTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_title, "field 'orderTitle'", MobirollerTextView.class);
        orderDetailsActivity.orderStatus = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_status, "field 'orderStatus'", MobirollerTextView.class);
        orderDetailsActivity.orderStatusLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.order_status_layout, "field 'orderStatusLayout'", ConstraintLayout.class);
        orderDetailsActivity.orderDate = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_date, "field 'orderDate'", MobirollerTextView.class);
        orderDetailsActivity.orderCodeTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.order_code, "field 'orderCodeTextView'", MobirollerTextView.class);
        orderDetailsActivity.orderLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.order_layout, "field 'orderLayout'", CardView.class);
        orderDetailsActivity.paymentTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_title, "field 'paymentTitle'", MobirollerTextView.class);
        orderDetailsActivity.paymentMethodTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_method_title, "field 'paymentMethodTitle'", MobirollerTextView.class);
        orderDetailsActivity.paymentMethodDescription = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.payment_method_description, "field 'paymentMethodDescription'", MobirollerTextView.class);
        orderDetailsActivity.paymentLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.payment_layout, "field 'paymentLayout'", CardView.class);
        orderDetailsActivity.cargoTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_title, "field 'cargoTitle'", MobirollerTextView.class);
        orderDetailsActivity.deliveryAddressTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.delivery_address_title, "field 'deliveryAddressTitle'", MobirollerTextView.class);
        orderDetailsActivity.deliveryAddressDescription = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.delivery_address_description, "field 'deliveryAddressDescription'", MobirollerTextView.class);
        orderDetailsActivity.billingAddressTitle = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_address_title, "field 'billingAddressTitle'", MobirollerTextView.class);
        orderDetailsActivity.billingAddressDescription = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.billing_address_description, "field 'billingAddressDescription'", MobirollerTextView.class);
        orderDetailsActivity.cargoLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_layout, "field 'cargoLayout'", CardView.class);
        orderDetailsActivity.orderStatusImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.order_status_image, "field 'orderStatusImage'", ImageView.class);
        orderDetailsActivity.productLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.product_layout, "field 'productLayout'", CardView.class);
        orderDetailsActivity.swipeRefreshLayout = (SwipeRefreshLayout) C0812Utils.findRequiredViewAsType(view, R.id.swipe_refresh_layout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        orderDetailsActivity.cargoMainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.cargo_main_layout, "field 'cargoMainLayout'", LinearLayout.class);
        orderDetailsActivity.mainLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", ConstraintLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.cargo_tracking_number, "field 'cargoTrackingNumberTextView' and method 'onClickCargoText'");
        orderDetailsActivity.cargoTrackingNumberTextView = (TextView) C0812Utils.castView(findRequiredView, R.id.cargo_tracking_number, "field 'cargoTrackingNumberTextView'", TextView.class);
        this.view7f0a014a = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderDetailsActivity.onClickCargoText();
            }
        });
        orderDetailsActivity.cargoCompanyNameTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_company_name, "field 'cargoCompanyNameTextView'", TextView.class);
        orderDetailsActivity.previewLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.preview_layout, "field 'previewLayout'", CardView.class);
        orderDetailsActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        orderDetailsActivity.productList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.product_list, "field 'productList'", RecyclerView.class);
        orderDetailsActivity.bottomLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.bottom_layout, "field 'bottomLayout'", ConstraintLayout.class);
        orderDetailsActivity.bankTransferLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.bank_transfer_layout, "field 'bankTransferLayout'", LinearLayout.class);
        orderDetailsActivity.bankNameTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_name, "field 'bankNameTextView'", MobirollerTextView.class);
        orderDetailsActivity.receiverNameTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.receiver_name, "field 'receiverNameTextView'", MobirollerTextView.class);
        orderDetailsActivity.bankAccountTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.bank_account, "field 'bankAccountTextView'", MobirollerTextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.bank_iban, "field 'bankIbanTextView' and method 'onClickIbanText'");
        orderDetailsActivity.bankIbanTextView = (MobirollerTextView) C0812Utils.castView(findRequiredView2, R.id.bank_iban, "field 'bankIbanTextView'", MobirollerTextView.class);
        this.view7f0a00b4 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderDetailsActivity.onClickIbanText();
            }
        });
        orderDetailsActivity.productSubTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.product_total_description_text_view, "field 'productSubTotalTextView'", MobirollerTextView.class);
        orderDetailsActivity.shippingTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_total_description_text_view, "field 'shippingTotalTextView'", MobirollerTextView.class);
        orderDetailsActivity.totalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.total, "field 'totalTextView'", MobirollerTextView.class);
    }

    public void unbind() {
        OrderDetailsActivity orderDetailsActivity = this.target;
        if (orderDetailsActivity != null) {
            this.target = null;
            orderDetailsActivity.orderTitle = null;
            orderDetailsActivity.orderStatus = null;
            orderDetailsActivity.orderStatusLayout = null;
            orderDetailsActivity.orderDate = null;
            orderDetailsActivity.orderCodeTextView = null;
            orderDetailsActivity.orderLayout = null;
            orderDetailsActivity.paymentTitle = null;
            orderDetailsActivity.paymentMethodTitle = null;
            orderDetailsActivity.paymentMethodDescription = null;
            orderDetailsActivity.paymentLayout = null;
            orderDetailsActivity.cargoTitle = null;
            orderDetailsActivity.deliveryAddressTitle = null;
            orderDetailsActivity.deliveryAddressDescription = null;
            orderDetailsActivity.billingAddressTitle = null;
            orderDetailsActivity.billingAddressDescription = null;
            orderDetailsActivity.cargoLayout = null;
            orderDetailsActivity.orderStatusImage = null;
            orderDetailsActivity.productLayout = null;
            orderDetailsActivity.swipeRefreshLayout = null;
            orderDetailsActivity.cargoMainLayout = null;
            orderDetailsActivity.mainLayout = null;
            orderDetailsActivity.cargoTrackingNumberTextView = null;
            orderDetailsActivity.cargoCompanyNameTextView = null;
            orderDetailsActivity.previewLayout = null;
            orderDetailsActivity.toolbar = null;
            orderDetailsActivity.productList = null;
            orderDetailsActivity.bottomLayout = null;
            orderDetailsActivity.bankTransferLayout = null;
            orderDetailsActivity.bankNameTextView = null;
            orderDetailsActivity.receiverNameTextView = null;
            orderDetailsActivity.bankAccountTextView = null;
            orderDetailsActivity.bankIbanTextView = null;
            orderDetailsActivity.productSubTotalTextView = null;
            orderDetailsActivity.shippingTotalTextView = null;
            orderDetailsActivity.totalTextView = null;
            this.view7f0a014a.setOnClickListener(null);
            this.view7f0a014a = null;
            this.view7f0a00b4.setOnClickListener(null);
            this.view7f0a00b4 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
