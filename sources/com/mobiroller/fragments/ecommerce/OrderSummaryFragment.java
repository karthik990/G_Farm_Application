package com.mobiroller.fragments.ecommerce;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.ecommerce.OrderFlowActivity;
import com.mobiroller.activities.ecommerce.ShoppingCartActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.BuyerOrderModel;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.MakeOrder;
import com.mobiroller.models.ecommerce.OrderFailedResponse;
import com.mobiroller.models.ecommerce.OrderResponseInner;
import com.mobiroller.models.ecommerce.PaymentSettings;
import com.mobiroller.models.ecommerce.ShoppingCartResponse;
import com.mobiroller.models.events.OrderResponseEvent;
import com.mobiroller.serviceinterfaces.ECommerceServiceInterface;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerDialog.Builder;
import com.mobiroller.views.custom.MobirollerDialog.DialogButtonCallback;
import com.mobiroller.views.custom.MobirollerTextView;
import org.greenrobot.eventbus.EventBus;

public class OrderSummaryFragment extends OrderFlowBaseFragment {
    @BindView(2131361931)
    CheckBox agreement;
    @BindView(2131361932)
    MobirollerTextView agreementDescription;
    private ECommerceServiceInterface applyzeECommerceService;
    @BindView(2131361991)
    MobirollerTextView billingDescriptionTextView;
    @BindView(2131362016)
    ConstraintLayout bottomLayout;
    @BindView(2131362124)
    MobirollerTextView cardDescriptionTExtView;
    @BindView(2131362182)
    MobirollerButton confirmButton;
    private ECommerceRequestHelper eCommerceRequestHelper;
    @BindView(2131362633)
    LottieAnimationView loadingAnimation;
    @BindView(2131362649)
    ConstraintLayout mainLayout;
    private MakeOrder makeOrder;
    @BindView(2131362813)
    CardView noteLayout;
    @BindView(2131362844)
    TextInputEditText orderNoteTextInputEditText;
    @BindView(2131362895)
    MobirollerTextView paymentDescriptionTextView;
    /* access modifiers changed from: private */
    public PaymentSettings paymentSettings;
    @BindView(2131362999)
    MobirollerTextView productSubTotalTextView;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @BindView(2131363130)
    MobirollerTextView shippingDescriptionTextView;
    @BindView(2131363136)
    MobirollerTextView shippingTotalTextView;
    @BindView(2131363286)
    MobirollerTextView totalTextView;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_order_summary, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
        this.applyzeECommerceService = this.eCommerceRequestHelper.getAPIService();
        this.makeOrder = (MakeOrder) getArguments().getSerializable(ECommerceConstant.MAKE_ORDER_MODEL);
        this.agreementDescription.setText(Html.fromHtml(getString(R.string.e_commerce_order_summary_agreement_description)));
        this.agreementDescription.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                OrderSummaryFragment.this.getTerms();
            }
        });
        setUpView();
        setConfirmButton(false);
        this.agreement.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                OrderSummaryFragment.this.setConfirmButton(z);
            }
        });
        this.mainLayout.setVisibility(8);
        this.bottomLayout.setVisibility(8);
        getShoppingCart(false);
        return inflate;
    }

    private void setUpView() {
        if (DynamicConstants.MobiRoller_Stage) {
            this.shippingDescriptionTextView.setText(getString(R.string.preview_e_commerce_shipping_address_sample));
            this.billingDescriptionTextView.setText(getString(R.string.preview_e_commerce_billing_address_sample));
            this.paymentDescriptionTextView.setText(getString(R.string.e_commerce_payment_method_selection_credit_card));
            MobirollerTextView mobirollerTextView = this.productSubTotalTextView;
            StringBuilder sb = new StringBuilder();
            sb.append(": 115 ");
            String str = "TRY";
            sb.append(ECommerceUtil.getCurrency(str));
            mobirollerTextView.setText(sb.toString());
            MobirollerTextView mobirollerTextView2 = this.shippingTotalTextView;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(": 5 ");
            sb2.append(ECommerceUtil.getCurrency(str));
            mobirollerTextView2.setText(sb2.toString());
            MobirollerTextView mobirollerTextView3 = this.totalTextView;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("20 ");
            sb3.append(ECommerceUtil.getCurrency(str));
            mobirollerTextView3.setText(sb3.toString());
            return;
        }
        this.shippingDescriptionTextView.setText(this.makeOrder.userShippingAddressModel.getSummaryDescriptionArea());
        this.billingDescriptionTextView.setText(this.makeOrder.userBillingAddressModel.getSummaryDescriptionArea());
        if (this.makeOrder.paymentType.equalsIgnoreCase(ECommerceConstant.PAY_AT_DOOR)) {
            this.paymentDescriptionTextView.setText(getString(R.string.e_commerce_payment_method_selection_pay_at_door));
            return;
        }
        boolean equalsIgnoreCase = this.makeOrder.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE);
        String str2 = Constants.NEW_LINE;
        if (equalsIgnoreCase || this.makeOrder.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
            MobirollerTextView mobirollerTextView4 = this.paymentDescriptionTextView;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(getString(R.string.e_commerce_payment_method_selection_credit_card));
            sb4.append(str2);
            StringBuilder sb5 = new StringBuilder();
            sb5.append("**** ");
            sb5.append(this.makeOrder.card.cardNumber.substring(12, 16));
            sb4.append(Html.fromHtml(sb5.toString()));
            mobirollerTextView4.setText(sb4.toString());
        } else if (this.makeOrder.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
            MobirollerTextView mobirollerTextView5 = this.paymentDescriptionTextView;
            StringBuilder sb6 = new StringBuilder();
            sb6.append(getString(R.string.e_commerce_payment_method_selection_transfer));
            sb6.append(str2);
            sb6.append(this.makeOrder.bankAccountModel.toString());
            mobirollerTextView5.setText(sb6.toString());
        } else if (this.makeOrder.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
            this.paymentDescriptionTextView.setText(getString(R.string.e_commerce_payment_method_selection_paypal));
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public boolean isValid() {
        return this.agreement.isChecked();
    }

    @OnClick({2131362182})
    public void onClickConfirmButton() {
        if (isValid()) {
            getShoppingCart(true);
        } else {
            new Builder().setContext(getActivity()).setType(MobirollerDialogType.BASIC).setTitle(getString(R.string.e_commerce_order_summary_agreement_popup_title)).setDescription(getString(R.string.e_commerce_order_summary_agreement_popup_description)).setIconResource(R.drawable.ic_outline_info_24).setColor(Color.parseColor("#F8E7D8")).setListener(new DialogButtonCallback() {
                public void onClickButton() {
                    if (OrderSummaryFragment.this.agreement != null) {
                        OrderSummaryFragment.this.agreement.setChecked(true);
                    }
                }
            }).setButtonText(getString(R.string.e_commerce_order_summary_agreement_popup_button)).show();
        }
    }

    /* access modifiers changed from: private */
    public void showUpdateShoppingCartDialog() {
        new Builder().setContext(getActivity()).setType(MobirollerDialogType.BASIC).setTitle(getString(R.string.e_commerce_order_summary_update_order_popup_title)).setDescription(getString(R.string.e_commerce_order_summary_update_order_popup_description)).setIconResource(R.drawable.ic_edit_white_24dp).setColor(Color.parseColor("#F8E7D8")).setListener(new DialogButtonCallback() {
            public void onClickButton() {
                OrderSummaryFragment orderSummaryFragment = OrderSummaryFragment.this;
                orderSummaryFragment.startActivity(new Intent(orderSummaryFragment.getActivity(), ShoppingCartActivity.class).addFlags(67108864));
                OrderSummaryFragment.this.getActivity().finish();
            }
        }).setButtonText(getString(R.string.e_commerce_order_summary_update_order_popup_button)).show();
    }

    private void getShoppingCart(final boolean z) {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getShoppingCart(UserHelper.getUserId()), new ECommerceCallBack<ShoppingCartResponse>() {
            public void done() {
                if (!OrderSummaryFragment.this.getActivity().isFinishing() && OrderSummaryFragment.this.progressViewHelper != null && OrderSummaryFragment.this.progressViewHelper.isShowing()) {
                    OrderSummaryFragment.this.progressViewHelper.dismiss();
                }
            }

            public void onSuccess(ShoppingCartResponse shoppingCartResponse) {
                OrderSummaryFragment.this.mainLayout.setVisibility(0);
                OrderSummaryFragment.this.bottomLayout.setVisibility(0);
                if (!z) {
                    OrderSummaryFragment.this.setCartLayout(shoppingCartResponse);
                } else if (shoppingCartResponse.invalidItems == null || shoppingCartResponse.invalidItems.size() == 0) {
                    OrderSummaryFragment.this.makeOrder();
                } else {
                    OrderSummaryFragment.this.showUpdateShoppingCartDialog();
                }
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(OrderSummaryFragment.this.getActivity());
            }

            public void onNetworkError(String str) {
                ErrorUtils.showErrorToast(OrderSummaryFragment.this.getActivity());
            }
        });
    }

    /* access modifiers changed from: private */
    public void setCartLayout(ShoppingCartResponse shoppingCartResponse) {
        MobirollerTextView mobirollerTextView = this.cardDescriptionTExtView;
        StringBuilder sb = new StringBuilder();
        sb.append(ECommerceUtil.getPriceString(shoppingCartResponse.totalPrice));
        String str = " ";
        sb.append(str);
        sb.append(ECommerceUtil.getCurrency(shoppingCartResponse.currency));
        mobirollerTextView.setText(getString(R.string.e_commerce_order_summary_cart_description, String.valueOf(shoppingCartResponse.items.size()), sb.toString()));
        MobirollerTextView mobirollerTextView2 = this.productSubTotalTextView;
        StringBuilder sb2 = new StringBuilder();
        String str2 = ": ";
        sb2.append(str2);
        sb2.append(ECommerceUtil.getPriceString(shoppingCartResponse.subTotalPrice));
        sb2.append(str);
        sb2.append(ECommerceUtil.getCurrency(shoppingCartResponse.currency));
        mobirollerTextView2.setText(sb2.toString());
        MobirollerTextView mobirollerTextView3 = this.shippingTotalTextView;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(ECommerceUtil.getPriceString(shoppingCartResponse.shippingPrice));
        sb3.append(str);
        sb3.append(ECommerceUtil.getCurrency(shoppingCartResponse.currency));
        mobirollerTextView3.setText(sb3.toString());
        MobirollerTextView mobirollerTextView4 = this.totalTextView;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(ECommerceUtil.getPriceString(shoppingCartResponse.totalPrice));
        sb4.append(str);
        sb4.append(ECommerceUtil.getCurrency(shoppingCartResponse.currency));
        mobirollerTextView4.setText(sb4.toString());
    }

    /* access modifiers changed from: private */
    public void setConfirmButton(boolean z) {
        this.confirmButton.setOpacity(z);
    }

    /* access modifiers changed from: private */
    public void makeOrder() {
        if (DynamicConstants.MobiRoller_Stage) {
            long currentTimeMillis = System.currentTimeMillis();
            this.loadingAnimation.setVisibility(0);
            this.bottomLayout.setVisibility(8);
            this.loadingAnimation.loop(true);
            if (!this.loadingAnimation.isAnimating()) {
                this.loadingAnimation.playAnimation();
            }
            long j = 0;
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (currentTimeMillis2 < 1500) {
                j = 1500 - currentTimeMillis2;
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!OrderSummaryFragment.this.getActivity().isFinishing()) {
                        OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                        OrderSummaryFragment.this.bottomLayout.setVisibility(0);
                        OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                        EventBus.getDefault().post(new OrderResponseEvent(new OrderResponseInner()));
                    }
                }
            }, j);
            this.mainLayout.setVisibility(8);
            return;
        }
        if (UtilManager.networkHelper().isConnected()) {
            final long currentTimeMillis3 = System.currentTimeMillis();
            this.loadingAnimation.setVisibility(0);
            this.bottomLayout.setVisibility(8);
            this.loadingAnimation.loop(true);
            if (!this.loadingAnimation.isAnimating()) {
                this.loadingAnimation.playAnimation();
            }
            this.makeOrder.buyer.email = UserHelper.getUserEmail();
            this.makeOrder.userNote = this.orderNoteTextInputEditText.getText().toString();
            String str = " ";
            String[] split = this.makeOrder.userBillingAddressModel.contact.nameSurname.split(str);
            if (split.length != 0) {
                this.makeOrder.buyer.name = split[0];
                this.makeOrder.buyer.surname = "";
                if (split.length > 1) {
                    for (int i = 1; i < split.length; i++) {
                        if (i == 1) {
                            StringBuilder sb = new StringBuilder();
                            BuyerOrderModel buyerOrderModel = this.makeOrder.buyer;
                            sb.append(buyerOrderModel.surname);
                            sb.append(split[i]);
                            buyerOrderModel.surname = sb.toString();
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            BuyerOrderModel buyerOrderModel2 = this.makeOrder.buyer;
                            sb2.append(buyerOrderModel2.surname);
                            sb2.append(str);
                            sb2.append(split[i]);
                            buyerOrderModel2.surname = sb2.toString();
                        }
                    }
                }
            }
            this.mainLayout.setVisibility(8);
            if (this.makeOrder.tryAgain) {
                this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().tryAgain(this.makeOrder.getCompleteOrderModel()), new ECommerceCallBack<OrderResponseInner>() {
                    public void done() {
                        if (!OrderSummaryFragment.this.getActivity().isFinishing() && OrderSummaryFragment.this.progressViewHelper.isShowing()) {
                            OrderSummaryFragment.this.progressViewHelper.dismiss();
                        }
                    }

                    public void onSuccess(final OrderResponseInner orderResponseInner) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                if (!OrderSummaryFragment.this.getActivity().isFinishing()) {
                                    EventBus.getDefault().post(new OrderResponseEvent(orderResponseInner));
                                    if (!orderResponseInner.order.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
                                        OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                        OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                                    }
                                }
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }

                    public void onFailure(final ECommerceErrorResponse eCommerceErrorResponse) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                EventBus.getDefault().post(new OrderResponseEvent(new OrderFailedResponse(eCommerceErrorResponse)));
                                OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }

                    public void onNetworkError(String str) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                                OrderSummaryFragment.this.mainLayout.setVisibility(0);
                                OrderSummaryFragment.this.bottomLayout.setVisibility(0);
                                Toast.makeText(OrderSummaryFragment.this.getActivity(), OrderSummaryFragment.this.getString(R.string.common_error), 1).show();
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }
                });
            } else {
                this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().makeOrder(UserHelper.getUserId(), this.makeOrder), new ECommerceCallBack<OrderResponseInner>() {
                    public void done() {
                        if (!OrderSummaryFragment.this.getActivity().isFinishing() && OrderSummaryFragment.this.progressViewHelper.isShowing()) {
                            OrderSummaryFragment.this.progressViewHelper.dismiss();
                        }
                    }

                    public void onSuccess(final OrderResponseInner orderResponseInner) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                if (!OrderSummaryFragment.this.getActivity().isFinishing()) {
                                    EventBus.getDefault().post(new OrderResponseEvent(orderResponseInner));
                                    if (!orderResponseInner.order.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
                                        OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                        OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                                    }
                                }
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }

                    public void onFailure(final ECommerceErrorResponse eCommerceErrorResponse) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                EventBus.getDefault().post(new OrderResponseEvent(new OrderFailedResponse(eCommerceErrorResponse)));
                                OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }

                    public void onNetworkError(String str) {
                        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillis3;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                OrderSummaryFragment.this.loadingAnimation.setVisibility(8);
                                OrderSummaryFragment.this.loadingAnimation.cancelAnimation();
                                OrderSummaryFragment.this.mainLayout.setVisibility(0);
                                OrderSummaryFragment.this.bottomLayout.setVisibility(0);
                                Toast.makeText(OrderSummaryFragment.this.getActivity(), OrderSummaryFragment.this.getString(R.string.common_error), 1).show();
                            }
                        }, currentTimeMillis < 1500 ? 1500 - currentTimeMillis : 0);
                    }
                });
            }
        } else {
            DialogUtil.showNoConnectionInfo(getContext());
        }
    }

    /* access modifiers changed from: private */
    public void getTerms() {
        if (this.paymentSettings != null) {
            openTermLink();
        } else if (UtilManager.networkHelper().isConnected()) {
            this.progressViewHelper.show();
            this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getPaymentSettings(), new ECommerceCallBack<PaymentSettings>() {
                public void done() {
                    if (!OrderSummaryFragment.this.getActivity().isFinishing() && OrderSummaryFragment.this.progressViewHelper.isShowing()) {
                        OrderSummaryFragment.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(PaymentSettings paymentSettings) {
                    OrderSummaryFragment.this.paymentSettings = paymentSettings;
                    OrderSummaryFragment.this.openTermLink();
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(OrderSummaryFragment.this.getContext());
                }

                public void onNetworkError(String str) {
                    ErrorUtils.showErrorToast(OrderSummaryFragment.this.getContext());
                }
            });
        } else {
            DialogUtil.showNoConnectionInfo(getContext());
        }
    }

    /* access modifiers changed from: private */
    public void openTermLink() {
        PaymentSettings paymentSettings2 = this.paymentSettings;
        if (paymentSettings2 != null && paymentSettings2.distanceSalesContract != null) {
            try {
                new CustomTabsIntent.Builder().build().launchUrl(getActivity(), Uri.parse(URLUtil.guessUrl(UtilManager.localizationHelper().getLocalizedTitle(this.paymentSettings.distanceSalesContract))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({2131362125})
    public void onClickCartLayout() {
        startActivity(new Intent(getActivity(), ShoppingCartActivity.class).addFlags(67108864));
        getActivity().finish();
    }

    @OnClick({2131362898})
    public void onClickPaymentLayout() {
        ((OrderFlowActivity) getActivity()).loadChoosePayment(false);
    }

    @OnClick({2131361985, 2131362242})
    public void onClickAddressLayout() {
        ((OrderFlowActivity) getActivity()).loadChooseAddress(false);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.loadingAnimation.setVisibility(8);
        this.loadingAnimation.cancelAnimation();
    }
}
