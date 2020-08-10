package com.mobiroller.fragments.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.OrderFailedResponse;
import com.mobiroller.models.ecommerce.OrderResponseInner;
import com.mobiroller.models.events.PaymentTryAgainEvent;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerTextView;
import org.greenrobot.eventbus.EventBus;

public class OrderResultFragment extends OrderFlowBaseFragment {
    @BindView(2131362204)
    MobirollerButton continueShoppingButton;
    @BindView(2131362370)
    MobirollerTextView extraDescriptionTextView;
    @BindView(2131362387)
    LinearLayout failedDescriptionLayout;
    @BindView(2131362386)
    MobirollerTextView failedDescriptionTextView;
    @BindView(2131362388)
    MobirollerEmptyView failedLayout;
    /* access modifiers changed from: private */
    public OrderFailedResponse orderFailedResponse;
    /* access modifiers changed from: private */
    public OrderResponseInner orderResponse;
    @BindView(2131363045)
    MobirollerButton resultButton;
    @BindView(2131363199)
    MobirollerEmptyView successLayout;
    Unbinder unbinder;

    public boolean isValid() {
        return false;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_e_commerce_result, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        Bundle arguments = getArguments();
        String str = ECommerceConstant.ORDER_RESPONSE_MODEL;
        if (arguments.containsKey(str)) {
            this.orderResponse = (OrderResponseInner) getArguments().getSerializable(str);
        } else {
            this.orderFailedResponse = (OrderFailedResponse) getArguments().getSerializable(ECommerceConstant.ORDER_FAILED_RESPONSE_MODEL);
        }
        if (DynamicConstants.MobiRoller_Stage) {
            setSuccess();
        } else if (!getArguments().getBoolean(ECommerceConstant.IS_PAYMENT_SUCCESS, false)) {
            setFailed();
        } else if (!this.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE) && !this.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
            setSuccess();
        } else if (this.orderResponse.paymentResult.isSuccess) {
            setSuccess();
        } else {
            setFailed();
        }
        return inflate;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    private void setSuccess() {
        this.resultButton.setText(getString(R.string.e_commerce_result_success_action_my_orders));
        this.failedLayout.setVisibility(8);
        this.successLayout.setVisibility(0);
        this.continueShoppingButton.setVisibility(0);
        this.failedDescriptionLayout.setVisibility(8);
        if (this.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
            MobirollerEmptyView mobirollerEmptyView = this.successLayout;
            mobirollerEmptyView.setDescription(mobirollerEmptyView.getDescription());
            this.extraDescriptionTextView.setText(getResources().getString(R.string.e_commerce_result_success_reference_number, new Object[]{this.orderResponse.order.orderCode}));
            this.extraDescriptionTextView.setVisibility(0);
        }
        if (this.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
            EventBus.getDefault().post(new ShoppingCartCountEvent(0));
        } else {
            new ECommerceUtil().getBadgeCount();
        }
        this.resultButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                OrderResultFragment orderResultFragment = OrderResultFragment.this;
                orderResultFragment.startActivity(new Intent(orderResultFragment.getActivity(), UserOrderActivity.class));
                OrderResultFragment.this.getActivity().setResult(-1);
                OrderResultFragment.this.getActivity().finish();
            }
        });
    }

    private void setFailed() {
        this.failedLayout.setVisibility(0);
        this.successLayout.setVisibility(8);
        this.continueShoppingButton.setVisibility(8);
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = ECommerceConstant.ORDER_FAILED_STATUS_CODE;
            if (arguments.containsKey(str) && getArguments().getString(str) != null) {
                String string = getArguments().getString(str);
                if (ECommerceConstant.PAYMENT_STATUS_CODES.containsKey(string)) {
                    this.failedDescriptionLayout.setVisibility(0);
                    this.failedDescriptionTextView.setText(getString(((Integer) ECommerceConstant.PAYMENT_STATUS_CODES.get(string)).intValue()));
                } else {
                    this.failedDescriptionLayout.setVisibility(8);
                }
                this.resultButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (!UtilManager.networkHelper().isConnected()) {
                            DialogUtil.showNoConnectionInfo(OrderResultFragment.this.getContext());
                        } else if (OrderResultFragment.this.orderResponse != null && OrderResultFragment.this.orderResponse.order != null && OrderResultFragment.this.orderResponse.order.f2177id != null) {
                            EventBus.getDefault().post(new PaymentTryAgainEvent(OrderResultFragment.this.orderResponse.order.f2177id));
                        } else if (OrderResultFragment.this.orderFailedResponse == null || OrderResultFragment.this.orderFailedResponse.order == null || OrderResultFragment.this.orderFailedResponse.order.f2177id == null) {
                            Toast.makeText(OrderResultFragment.this.getActivity(), R.string.common_error, 0).show();
                        } else {
                            EventBus.getDefault().post(new PaymentTryAgainEvent(OrderResultFragment.this.orderFailedResponse.order.f2177id));
                        }
                    }
                });
            }
        }
        this.failedDescriptionLayout.setVisibility(8);
        this.resultButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!UtilManager.networkHelper().isConnected()) {
                    DialogUtil.showNoConnectionInfo(OrderResultFragment.this.getContext());
                } else if (OrderResultFragment.this.orderResponse != null && OrderResultFragment.this.orderResponse.order != null && OrderResultFragment.this.orderResponse.order.f2177id != null) {
                    EventBus.getDefault().post(new PaymentTryAgainEvent(OrderResultFragment.this.orderResponse.order.f2177id));
                } else if (OrderResultFragment.this.orderFailedResponse == null || OrderResultFragment.this.orderFailedResponse.order == null || OrderResultFragment.this.orderFailedResponse.order.f2177id == null) {
                    Toast.makeText(OrderResultFragment.this.getActivity(), R.string.common_error, 0).show();
                } else {
                    EventBus.getDefault().post(new PaymentTryAgainEvent(OrderResultFragment.this.orderFailedResponse.order.f2177id));
                }
            }
        });
    }

    @OnClick({2131362204})
    public void onClickContinueShoppingButton() {
        getActivity().setResult(-1);
        getActivity().finish();
    }
}
