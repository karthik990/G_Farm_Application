package com.mobiroller.fragments.ecommerce;

import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerTextView;

public class OrderResultFragment_ViewBinding implements Unbinder {
    private OrderResultFragment target;
    private View view7f0a019c;

    public OrderResultFragment_ViewBinding(final OrderResultFragment orderResultFragment, View view) {
        this.target = orderResultFragment;
        orderResultFragment.resultButton = (MobirollerButton) C0812Utils.findRequiredViewAsType(view, R.id.result_button, "field 'resultButton'", MobirollerButton.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.continue_shopping_button, "field 'continueShoppingButton' and method 'onClickContinueShoppingButton'");
        orderResultFragment.continueShoppingButton = (MobirollerButton) C0812Utils.castView(findRequiredView, R.id.continue_shopping_button, "field 'continueShoppingButton'", MobirollerButton.class);
        this.view7f0a019c = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                orderResultFragment.onClickContinueShoppingButton();
            }
        });
        orderResultFragment.failedLayout = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.failed_layout, "field 'failedLayout'", MobirollerEmptyView.class);
        orderResultFragment.successLayout = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.success_layout, "field 'successLayout'", MobirollerEmptyView.class);
        orderResultFragment.extraDescriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.extra_description_text_view, "field 'extraDescriptionTextView'", MobirollerTextView.class);
        orderResultFragment.failedDescriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.failed_description, "field 'failedDescriptionTextView'", MobirollerTextView.class);
        orderResultFragment.failedDescriptionLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.failed_description_layout, "field 'failedDescriptionLayout'", LinearLayout.class);
    }

    public void unbind() {
        OrderResultFragment orderResultFragment = this.target;
        if (orderResultFragment != null) {
            this.target = null;
            orderResultFragment.resultButton = null;
            orderResultFragment.continueShoppingButton = null;
            orderResultFragment.failedLayout = null;
            orderResultFragment.successLayout = null;
            orderResultFragment.extraDescriptionTextView = null;
            orderResultFragment.failedDescriptionTextView = null;
            orderResultFragment.failedDescriptionLayout = null;
            this.view7f0a019c.setOnClickListener(null);
            this.view7f0a019c = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
