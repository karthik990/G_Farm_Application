package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.enums.FontTypeEnum;
import com.mobiroller.fragments.ecommerce.OrderChooseAddressFragment;
import com.mobiroller.fragments.ecommerce.OrderChoosePaymentFragment;
import com.mobiroller.fragments.ecommerce.OrderFlowBaseFragment;
import com.mobiroller.fragments.ecommerce.OrderResultFragment;
import com.mobiroller.fragments.ecommerce.OrderSummaryFragment;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.MakeOrder;
import com.mobiroller.models.ecommerce.OrderFailedResponse;
import com.mobiroller.models.ecommerce.OrderResponseInner;
import com.mobiroller.models.ecommerce.PaymentSettings;
import com.mobiroller.models.events.AddressContinueEvent;
import com.mobiroller.models.events.AnimationFinishedEvent;
import com.mobiroller.models.events.OrderAddressesEvent;
import com.mobiroller.models.events.OrderPaymentEvent;
import com.mobiroller.models.events.OrderResponseEvent;
import com.mobiroller.models.events.PaymentContinueEvent;
import com.mobiroller.models.events.PaymentTryAgainEvent;
import com.mobiroller.models.user.DefaultAddressModel;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.StatusViewScroller;
import com.mobiroller.views.custom.MobirollerToolbar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class OrderFlowActivity extends ECommerceBaseActivity {
    private OrderChooseAddressFragment chooseAddressFragment;
    private OrderChoosePaymentFragment choosePaymentFragment;
    @BindView(2131362187)
    ConstraintLayout constraintLayout;
    private OrderFlowBaseFragment currentFragment;
    private int currentStep = 1;
    ECommerceRequestHelper eCommerceRequestHelper;
    @BindView(2131362461)
    FrameLayout frameLayout;
    private boolean mIsSuccess;
    private MakeOrder makeOrder;
    @BindView(2131362794)
    NestedScrollView nestedScrollView;
    private OrderResponseEvent orderResponseEvent;
    /* access modifiers changed from: private */
    public OrderResultFragment orderResultFragment;
    private OrderSummaryFragment orderSummaryFragment;
    /* access modifiers changed from: private */
    public PaymentSettings paymentSettings;
    ProgressViewHelper progressViewHelper;
    RequestHelper requestHelper;
    private SharedPrefHelper sharedPrefHelper;
    @BindView(2131363185)
    StatusViewScroller stepView;
    @BindView(2131363186)
    ConstraintLayout stepViewMainLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @BindView(2131363284)
    RelativeLayout topTitleLayout;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_e_commerce_order_flow);
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind((Activity) this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        new ToolbarHelper().setStatusBar(this);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                OrderFlowActivity.this.onBackPressed();
            }
        });
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.requestHelper = new RequestHelper();
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
        Intent intent = getIntent();
        String str = ECommerceConstant.MAKE_ORDER_MODEL;
        if (intent.hasExtra(str)) {
            this.makeOrder = (MakeOrder) getIntent().getSerializableExtra(str);
        }
        loadChooseAddress(true);
        this.stepView.getStatusView().setLabelsTypeface(FontTypeEnum.getResIdByResOrder(3));
        this.stepView.getStatusView().setStatusTypeface(FontTypeEnum.getResIdByResOrder(3));
        this.stepView.getStatusView().setLineColor(ViewCompat.MEASURED_STATE_MASK);
        String str2 = "#19000000";
        this.stepView.getStatusView().setLineColorIncomplete(Color.parseColor(str2));
        this.stepView.getStatusView().setCircleFillColorIncomplete(Color.parseColor(str2));
        this.stepView.getStatusView().setCircleStrokeColorIncomplete(Color.parseColor(str2));
    }

    public void loadChooseAddress(boolean z) {
        this.stepViewMainLayout.setVisibility(0);
        this.currentStep = 1;
        this.nestedScrollView.scrollTo(0, 0);
        if (this.chooseAddressFragment == null) {
            this.chooseAddressFragment = new OrderChooseAddressFragment();
        }
        this.currentFragment = this.chooseAddressFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.chooseAddressFragment);
        setTitle(getString(R.string.e_commerce_address_selection_title));
    }

    public void loadChoosePayment(boolean z) {
        PaymentSettings paymentSettings2 = this.paymentSettings;
        if (paymentSettings2 != null) {
            loadChoosePayment(paymentSettings2, z);
        } else {
            getPaymentOptions(z);
        }
    }

    /* access modifiers changed from: private */
    public void loadChoosePayment(PaymentSettings paymentSettings2, boolean z) {
        this.stepViewMainLayout.setVisibility(0);
        setDefaultAddresses();
        this.currentStep = 2;
        this.nestedScrollView.scrollTo(0, 0);
        if (this.choosePaymentFragment == null) {
            this.choosePaymentFragment = new OrderChoosePaymentFragment();
        }
        this.currentFragment = this.choosePaymentFragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable(ECommerceConstant.MAKE_ORDER_MODEL, this.makeOrder);
        bundle.putSerializable(ECommerceConstant.PAYMENT_SETTINGS_MODEL, paymentSettings2);
        this.choosePaymentFragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.choosePaymentFragment);
        setTitle(getString(R.string.e_commerce_payment_method_selection_title));
    }

    private void replaceFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add((int) R.id.frame_layout, fragment);
        }
        for (Fragment fragment2 : getSupportFragmentManager().getFragments()) {
            if (fragment2 != fragment && fragment2.isAdded()) {
                fragmentTransaction.hide(fragment2);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Subscribe
    public void onPostAnimationFinishedEvent(AnimationFinishedEvent animationFinishedEvent) {
        if (this.currentStep != 4 || this.mIsSuccess) {
            this.stepView.getStatusView().setCurrentCount(this.currentStep);
        }
    }

    private void loadOrderSummary(boolean z) {
        this.stepViewMainLayout.setVisibility(0);
        this.currentStep = 3;
        this.nestedScrollView.scrollTo(0, 0);
        try {
            if (this.orderSummaryFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(this.orderResultFragment).commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.orderSummaryFragment = new OrderSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ECommerceConstant.MAKE_ORDER_MODEL, this.makeOrder);
        this.orderSummaryFragment.setArguments(bundle);
        this.currentFragment = this.orderSummaryFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.right_to_left_enter, R.anim.right_to_left_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.orderSummaryFragment);
        setTitle(getString(R.string.e_commerce_order_summary_title));
    }

    private void loadOrderResultSuccess(OrderResponseInner orderResponseInner, boolean z) {
        Timber.m1342d("loadOrderResultSuccess OrderResponse", new Object[0]);
        this.stepViewMainLayout.setVisibility(8);
        this.currentStep = 4;
        this.nestedScrollView.scrollTo(0, 0);
        if (this.orderResultFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(this.orderResultFragment).commitAllowingStateLoss();
        }
        this.orderResultFragment = new OrderResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ECommerceConstant.ORDER_RESPONSE_MODEL, orderResponseInner);
        bundle.putBoolean(ECommerceConstant.IS_PAYMENT_SUCCESS, true);
        this.mIsSuccess = true;
        this.orderResultFragment.setArguments(bundle);
        this.currentFragment = this.orderResultFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_out_up);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.orderResultFragment);
        setTitle("");
    }

    private void loadOrderResultFailed(OrderFailedResponse orderFailedResponse, boolean z) {
        Timber.m1342d("loadOrderResultFailed OrderFailedResponse", new Object[0]);
        this.stepViewMainLayout.setVisibility(8);
        this.currentStep = 4;
        this.nestedScrollView.scrollTo(0, 0);
        if (this.orderResultFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(this.orderResultFragment).commitAllowingStateLoss();
        }
        this.orderResultFragment = new OrderResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ECommerceConstant.ORDER_FAILED_RESPONSE_MODEL, orderFailedResponse);
        bundle.putBoolean(ECommerceConstant.IS_PAYMENT_SUCCESS, false);
        this.mIsSuccess = false;
        this.orderResultFragment.setArguments(bundle);
        this.currentFragment = this.orderResultFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_out_up);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.orderResultFragment);
        setTitle("");
    }

    private void loadOrderResultFailed(OrderResponseInner orderResponseInner, String str, boolean z) {
        this.stepViewMainLayout.setVisibility(8);
        Timber.m1342d("loadOrderResultFailed OrderResponse", new Object[0]);
        this.currentStep = 4;
        this.nestedScrollView.scrollTo(0, 0);
        this.orderResultFragment = new OrderResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ECommerceConstant.ORDER_RESPONSE_MODEL, orderResponseInner);
        bundle.putBoolean(ECommerceConstant.IS_PAYMENT_SUCCESS, false);
        bundle.putString(ECommerceConstant.ORDER_FAILED_STATUS_CODE, str);
        this.mIsSuccess = false;
        this.orderResultFragment.setArguments(bundle);
        this.currentFragment = this.orderResultFragment;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_out_up);
        } else {
            beginTransaction.setCustomAnimations(R.anim.left_to_right_enter, R.anim.left_to_right_exit, R.anim.left_to_right_enter, R.anim.left_to_right_exit);
        }
        replaceFragment(beginTransaction, this.orderResultFragment);
        setTitle("");
    }

    public void onClickContinueButton() {
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionInfo(this);
        } else if (validateStep()) {
            int i = this.currentStep;
            if (i == 1) {
                getPaymentOptions(true);
            } else if (i == 2) {
                loadOrderSummary(true);
            }
        }
    }

    public boolean validateStep() {
        return this.currentFragment.isValid();
    }

    public static void startActivity(MakeOrder makeOrder2, Context context) {
        Intent intent = new Intent(context, OrderFlowActivity.class);
        intent.putExtra(ECommerceConstant.MAKE_ORDER_MODEL, makeOrder2);
        context.startActivity(intent);
    }

    public static void startActivityForResult(MakeOrder makeOrder2, AppCompatActivity appCompatActivity) {
        Intent intent = new Intent(appCompatActivity, OrderFlowActivity.class);
        intent.putExtra(ECommerceConstant.MAKE_ORDER_MODEL, makeOrder2);
        appCompatActivity.startActivityForResult(intent, 11);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, OrderFlowActivity.class));
    }

    @Subscribe
    public void onPostPaymentTryAgainEvent(PaymentTryAgainEvent paymentTryAgainEvent) {
        MakeOrder makeOrder2 = this.makeOrder;
        makeOrder2.tryAgain = true;
        makeOrder2.orderId = paymentTryAgainEvent.orderId;
        getPaymentOptions(false);
    }

    @Subscribe
    public void onPostOrderPaymentEvent(OrderPaymentEvent orderPaymentEvent) {
        this.makeOrder.paymentType = orderPaymentEvent.paymentType;
        MakeOrder makeOrder2 = this.makeOrder;
        makeOrder2.bankAccount = null;
        makeOrder2.paymentAccount = null;
        makeOrder2.bankAccountModel = null;
        makeOrder2.card = null;
        if (orderPaymentEvent.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE) || orderPaymentEvent.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
            this.makeOrder.card = orderPaymentEvent.orderCard;
        } else if (orderPaymentEvent.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
            this.makeOrder.bankAccount = orderPaymentEvent.bankAccount.toString();
            this.makeOrder.bankAccountModel = orderPaymentEvent.bankAccount;
            this.makeOrder.paymentAccount = orderPaymentEvent.bankAccount;
        }
    }

    @Subscribe
    public void onPostOrderResponseEvent(OrderResponseEvent orderResponseEvent2) {
        if (DynamicConstants.MobiRoller_Stage) {
            loadOrderResultSuccess(orderResponseEvent2.orderResponse, true);
            return;
        }
        this.orderResponseEvent = orderResponseEvent2;
        if (!(this.currentFragment instanceof OrderResultFragment)) {
            if (orderResponseEvent2.orderResponse != null && orderResponseEvent2.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL) && orderResponseEvent2.orderResponse.paymentResult != null && orderResponseEvent2.orderResponse.paymentResult.token != null) {
                startActivityForResult(new Intent(this, PayPalActivity.class).putExtra(PayPalActivity.PAYPAL_AUTH_TOKEN_KEY, orderResponseEvent2.orderResponse.paymentResult.token).putExtra(PayPalActivity.PAYPAL_AMOUNT_KEY, orderResponseEvent2.orderResponse.order.totalPrice).putExtra(PayPalActivity.PAYPAL_CURRENCY_CODE_KEY, orderResponseEvent2.orderResponse.order.currency).putExtra(PayPalActivity.PAYPAL_ORDER_ID_KEY, orderResponseEvent2.orderResponse.order.f2177id).putExtra(PayPalActivity.PAYPAL_DISPLAY_NAME_KEY, orderResponseEvent2.orderResponse.order.orderCode), 1004);
            } else if (orderResponseEvent2.orderResponse != null && orderResponseEvent2.orderResponse.order.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS) && orderResponseEvent2.orderResponse.paymentResult != null && orderResponseEvent2.orderResponse.paymentResult._3DSecureHtml != null) {
                startActivityForResult(new Intent(this, Online3DSecureGateway.class).putExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML, orderResponseEvent2.orderResponse.paymentResult._3DSecureHtml), 1003);
            } else if (orderResponseEvent2.orderResponse != null || orderResponseEvent2.failedResponse == null) {
                loadOrderResultSuccess(orderResponseEvent2.orderResponse, true);
            } else {
                loadOrderResultFailed(orderResponseEvent2.failedResponse, true);
            }
        }
    }

    @Subscribe
    public void onPostOrderAddressesEvent(OrderAddressesEvent orderAddressesEvent) {
        if (!DynamicConstants.MobiRoller_Stage) {
            this.makeOrder.userBillingAddressModel = orderAddressesEvent.userBillingAddressModel;
            this.makeOrder.userShippingAddressModel = orderAddressesEvent.userShippingAddressModel;
            this.makeOrder.billingAddress = orderAddressesEvent.userBillingAddressModel.getOrderAddress();
            this.makeOrder.shippingAddress = orderAddressesEvent.userShippingAddressModel.getOrderAddress();
        }
    }

    @Subscribe
    public void onPostAddressContinueEvent(AddressContinueEvent addressContinueEvent) {
        onClickContinueButton();
    }

    @Subscribe
    public void onPostPaymentContinueEvent(PaymentContinueEvent paymentContinueEvent) {
        onClickContinueButton();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        OrderSummaryFragment orderSummaryFragment2 = this.orderSummaryFragment;
        if (orderSummaryFragment2 != null) {
            orderSummaryFragment2.onActivityResult(i, i2, intent);
        }
        String str = "0";
        if (i == 1003) {
            if (i2 != -1) {
                return;
            }
            if (intent == null || !intent.getBooleanExtra(ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_SUCCESS, false)) {
                if (intent != null) {
                    String str2 = ECommerceConstant.ONLINE_PAYMENT_3D_HTML_REQUEST_FAILED_STATUS_CODE;
                    if (intent.hasExtra(str2)) {
                        loadOrderResultFailed(this.orderResponseEvent.orderResponse, intent.getStringExtra(str2), true);
                        return;
                    }
                }
                loadOrderResultFailed(this.orderResponseEvent.orderResponse, str, true);
                return;
            }
            loadOrderResultSuccess(this.orderResponseEvent.orderResponse, true);
        } else if (i == 1004) {
            if (i2 == -1 && intent != null) {
                String str3 = ECommerceConstant.PAY_PAL_REQUEST_SUCCESS;
                if (intent.hasExtra(str3) && intent.getBooleanExtra(str3, false)) {
                    loadOrderResultSuccess(this.orderResponseEvent.orderResponse, true);
                    return;
                }
            }
            if (intent != null) {
                String str4 = ECommerceConstant.PAY_PAL_REQUEST_FAILED_STATUS_CODE;
                if (intent.hasExtra(str4)) {
                    loadOrderResultFailed(this.orderResponseEvent.orderResponse, intent.getStringExtra(str4), true);
                    return;
                }
            }
            loadOrderResultFailed(this.orderResponseEvent.orderResponse, str, true);
        }
    }

    public void onBackPressed() {
        int i = this.currentStep;
        if (i == 1) {
            finish();
        } else if (i == 2) {
            loadChooseAddress(false);
        } else if (i == 3) {
            loadChoosePayment(false);
        } else if (i == 4) {
            setResult(-1);
            finish();
        }
    }

    private void setDefaultAddresses() {
        if (!DynamicConstants.MobiRoller_Stage) {
            DefaultAddressModel defaultAddressModel = new DefaultAddressModel(getString(R.string.applyze_app_key), getString(R.string.applyze_api_key));
            this.requestHelper.getApplyzeUserAPIService().setDefaultBillingAddress(UserHelper.getUserId(), this.makeOrder.userBillingAddressModel.f2189id, defaultAddressModel).enqueue(new Callback<Void>() {
                public void onFailure(Call<Void> call, Throwable th) {
                }

                public void onResponse(Call<Void> call, Response<Void> response) {
                }
            });
            this.requestHelper.getApplyzeUserAPIService().setDefaultShippingAddress(UserHelper.getUserId(), this.makeOrder.userShippingAddressModel.f2189id, defaultAddressModel).enqueue(new Callback<Void>() {
                public void onFailure(Call<Void> call, Throwable th) {
                }

                public void onResponse(Call<Void> call, Response<Void> response) {
                }
            });
        }
    }

    private void getPaymentOptions(final boolean z) {
        if (this.paymentSettings != null) {
            if (this.orderResultFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(this.orderResultFragment).commitAllowingStateLoss();
            }
            loadChoosePayment(this.paymentSettings, z);
            return;
        }
        if (!isFinishing() && !this.progressViewHelper.isShowing()) {
            this.progressViewHelper.show();
        }
        this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getPaymentSettings(), new ECommerceCallBack<PaymentSettings>() {
            public void done() {
                if (!OrderFlowActivity.this.isFinishing() && OrderFlowActivity.this.progressViewHelper.isShowing()) {
                    OrderFlowActivity.this.progressViewHelper.dismiss();
                }
            }

            public void onSuccess(PaymentSettings paymentSettings) {
                OrderFlowActivity.this.paymentSettings = paymentSettings;
                if (OrderFlowActivity.this.orderResultFragment != null) {
                    OrderFlowActivity.this.getSupportFragmentManager().beginTransaction().remove(OrderFlowActivity.this.orderResultFragment).commitAllowingStateLoss();
                }
                OrderFlowActivity orderFlowActivity = OrderFlowActivity.this;
                orderFlowActivity.loadChoosePayment(orderFlowActivity.paymentSettings, z);
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(OrderFlowActivity.this);
            }

            public void onNetworkError(String str) {
                ErrorUtils.showErrorToast(OrderFlowActivity.this);
            }
        });
    }

    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
