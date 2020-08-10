package com.mobiroller.activities.ecommerce;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ShoppingCartActivity_ViewBinding implements Unbinder {
    private ShoppingCartActivity target;
    private View view7f0a0173;
    private View view7f0a0186;
    private View view7f0a056a;

    public ShoppingCartActivity_ViewBinding(ShoppingCartActivity shoppingCartActivity) {
        this(shoppingCartActivity, shoppingCartActivity.getWindow().getDecorView());
    }

    public ShoppingCartActivity_ViewBinding(final ShoppingCartActivity shoppingCartActivity, View view) {
        this.target = shoppingCartActivity;
        shoppingCartActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        shoppingCartActivity.emptyLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_layout, "field 'emptyLayout'", ConstraintLayout.class);
        shoppingCartActivity.contentLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.content_layout, "field 'contentLayout'", ConstraintLayout.class);
        shoppingCartActivity.itemCountTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.item_count_text_view, "field 'itemCountTextView'", MobirollerTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.confirm_button, "field 'confirmButton' and method 'onClickCheckout'");
        shoppingCartActivity.confirmButton = (MobirollerButton) C0812Utils.castView(findRequiredView, R.id.confirm_button, "field 'confirmButton'", MobirollerButton.class);
        this.view7f0a0186 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartActivity.onClickCheckout();
            }
        });
        shoppingCartActivity.campaignLayout = (MobirollerClickableLayout) C0812Utils.findRequiredViewAsType(view, R.id.campaign_layout, "field 'campaignLayout'", MobirollerClickableLayout.class);
        shoppingCartActivity.productSubTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.product_total_description_text_view, "field 'productSubTotalTextView'", MobirollerTextView.class);
        shoppingCartActivity.shippingTotalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.shipping_total_description_text_view, "field 'shippingTotalTextView'", MobirollerTextView.class);
        shoppingCartActivity.totalTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.total, "field 'totalTextView'", MobirollerTextView.class);
        shoppingCartActivity.infoTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.info_text_view, "field 'infoTextView'", MobirollerTextView.class);
        shoppingCartActivity.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", RecyclerView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.start_shopping_button, "method 'onClickStartShoppingButton'");
        this.view7f0a056a = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartActivity.onClickStartShoppingButton();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.clear_cart_text_view, "method 'onClickClearShoppingCart'");
        this.view7f0a0173 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartActivity.onClickClearShoppingCart();
            }
        });
    }

    public void unbind() {
        ShoppingCartActivity shoppingCartActivity = this.target;
        if (shoppingCartActivity != null) {
            this.target = null;
            shoppingCartActivity.toolbar = null;
            shoppingCartActivity.emptyLayout = null;
            shoppingCartActivity.contentLayout = null;
            shoppingCartActivity.itemCountTextView = null;
            shoppingCartActivity.confirmButton = null;
            shoppingCartActivity.campaignLayout = null;
            shoppingCartActivity.productSubTotalTextView = null;
            shoppingCartActivity.shippingTotalTextView = null;
            shoppingCartActivity.totalTextView = null;
            shoppingCartActivity.infoTextView = null;
            shoppingCartActivity.list = null;
            this.view7f0a0186.setOnClickListener(null);
            this.view7f0a0186 = null;
            this.view7f0a056a.setOnClickListener(null);
            this.view7f0a056a = null;
            this.view7f0a0173.setOnClickListener(null);
            this.view7f0a0173 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
