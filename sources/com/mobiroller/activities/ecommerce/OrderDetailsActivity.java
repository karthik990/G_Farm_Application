package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.adapters.ecommerce.OrderDetailProductListAdapter;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.OrderDetailModel;
import com.mobiroller.models.ecommerce.OrderDetailResponse;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.text.ParseException;
import java.util.TimeZone;

public class OrderDetailsActivity extends ECommerceBaseActivity {
    @BindView(2131361968)
    MobirollerTextView bankAccountTextView;
    @BindView(2131361972)
    MobirollerTextView bankIbanTextView;
    @BindView(2131361973)
    MobirollerTextView bankNameTextView;
    @BindView(2131361975)
    LinearLayout bankTransferLayout;
    @BindView(2131361983)
    MobirollerTextView billingAddressDescription;
    @BindView(2131361986)
    MobirollerTextView billingAddressTitle;
    @BindView(2131362016)
    ConstraintLayout bottomLayout;
    @BindView(2131362115)
    TextView cargoCompanyNameTextView;
    @BindView(2131362117)
    CardView cargoLayout;
    @BindView(2131362118)
    LinearLayout cargoMainLayout;
    @BindView(2131362120)
    MobirollerTextView cargoTitle;
    @BindView(2131362122)
    TextView cargoTrackingNumberTextView;
    @BindView(2131362241)
    MobirollerTextView deliveryAddressDescription;
    @BindView(2131362243)
    MobirollerTextView deliveryAddressTitle;
    ECommerceRequestHelper eCommerceRequestHelper;
    LocalizationHelper localizationHelper;
    /* access modifiers changed from: private */
    public OrderDetailModel mOrderDetailModel;
    private String mOrderId;
    @BindView(2131362649)
    ConstraintLayout mainLayout;
    @BindView(2131362848)
    MobirollerTextView orderCodeTextView;
    @BindView(2131362849)
    MobirollerTextView orderDate;
    @BindView(2131362850)
    CardView orderLayout;
    @BindView(2131362854)
    MobirollerTextView orderStatus;
    @BindView(2131362855)
    ImageView orderStatusImage;
    @BindView(2131362856)
    ConstraintLayout orderStatusLayout;
    @BindView(2131362858)
    MobirollerTextView orderTitle;
    @BindView(2131362900)
    CardView paymentLayout;
    @BindView(2131362902)
    MobirollerTextView paymentMethodDescription;
    @BindView(2131362906)
    MobirollerTextView paymentMethodTitle;
    @BindView(2131362908)
    MobirollerTextView paymentTitle;
    @BindView(2131362978)
    CardView previewLayout;
    @BindView(2131362991)
    CardView productLayout;
    @BindView(2131362992)
    RecyclerView productList;
    @BindView(2131362999)
    MobirollerTextView productSubTotalTextView;
    private ProgressViewHelper progressViewHelper;
    @BindView(2131363020)
    MobirollerTextView receiverNameTextView;
    @BindView(2131363136)
    MobirollerTextView shippingTotalTextView;
    @BindView(2131363207)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @BindView(2131363286)
    MobirollerTextView totalTextView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_order_detail);
        ButterKnife.bind((Activity) this);
        if (DynamicConstants.MobiRoller_Stage) {
            this.previewLayout.setVisibility(0);
        }
        new ToolbarHelper().setStatusBar(this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.localizationHelper = UtilManager.localizationHelper();
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                OrderDetailsActivity.this.onBackPressed();
            }
        });
        setTitle(getString(R.string.e_commerce_my_orders_title));
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
        if (UtilManager.networkHelper().isConnected()) {
            this.progressViewHelper.show();
            String str = "orderId";
            if (getIntent().hasExtra(str)) {
                this.mOrderId = getIntent().getStringExtra(str);
                getOrder();
            } else {
                Intent intent = getIntent();
                String str2 = ECommerceConstant.ORDER_DETAIL_MODEL;
                if (intent.hasExtra(str2)) {
                    this.mOrderDetailModel = (OrderDetailModel) getIntent().getSerializableExtra(str2);
                    this.mOrderId = this.mOrderDetailModel.f2177id;
                    setupView();
                } else {
                    finish();
                    this.progressViewHelper.dismiss();
                }
            }
            if (DynamicConstants.MobiRoller_Stage) {
                this.swipeRefreshLayout.setEnabled(false);
            }
            this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    OrderDetailsActivity.this.getOrder();
                }
            });
            return;
        }
        DialogUtil.showNoConnectionError(this);
    }

    /* access modifiers changed from: private */
    public void getOrder() {
        if (DynamicConstants.MobiRoller_Stage) {
            this.mOrderDetailModel = ((OrderDetailResponse) new Gson().fromJson("{'data':{'ba':{'ci':'Antalya','co':'Türkiye','des':'cuxuxx','id':'5c7fe18a1390ee00017443a5','idn':'123456778877777','ph':'245588888888','st':'Antalya','zip':'07050'},'buyer':{'n':'dud','sn':'cuud'},'sa':{'ci':'Antalya','co':'Türkiye','des':'cuxuxx','id':'5c7fe18a1390ee00017443a5','ph':'245588888888','st':'Antalya','zip':'07050'},'cd':'2019-03-06T15:05:05','c':'TRY','cs':'Delivered','id':'5c7fe1a14fb2dd00016ac3e5','code':'MBR7709295','pt':'PayAtDoor','pl':[{'code':'MY-PLYSTTN-GMR-3','fi':{'n':'https://mobirollercdn.blob.core.windows.net/applyze/40924/ecommerce/products/b3b4922ba7763088.png','t':'https://mobirollercdn.blob.core.windows.net/applyze/40924/ecommerce/products/b3b4922ba7763088.thumb'},'id':'5c5313942f420d000157987e','pp':1800.0,'q':1,'t':'{<TR>Powerway WRX01 Taşınabilir Kablosuz Bluetooth Hoparlör EXTRA BASS <TR>}{<EN>Powerway WRX01 Taşınabilir Kablosuz Bluetooth Hoparlör EXTRA BASS <EN>}{<ES>Powerway WRX01 Taşınabilir Kablosuz Bluetooth Hoparlör EXTRA BASS <ES>}{<RU>Powerway WRX01 Taşınabilir Kablosuz Bluetooth Hoparlör EXTRA BASS <RU>}'}],'sp':5.0,'tp':120.0},'isUserFriendlyMessage':false}", OrderDetailResponse.class)).data;
            setupView();
            return;
        }
        this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getOrder(this.mOrderId), new ECommerceCallBack<OrderDetailModel>() {
            public void onNetworkError(String str) {
            }

            public void done() {
                OrderDetailsActivity.this.swipeRefreshLayout.setRefreshing(false);
            }

            public void onSuccess(OrderDetailModel orderDetailModel) {
                OrderDetailsActivity.this.mOrderDetailModel = orderDetailModel;
                OrderDetailsActivity.this.setupView();
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(OrderDetailsActivity.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setupView() {
        this.orderStatusImage.setImageDrawable(ContextCompat.getDrawable(this, ECommerceUtil.getOrderStatusIcon(this.mOrderDetailModel.currentStatus)));
        if (VERSION.SDK_INT >= 21) {
            this.orderStatusImage.setBackgroundTintList(ColorStateList.valueOf(ECommerceUtil.getOrderStatusColor(this.mOrderDetailModel.currentStatus, this)));
        }
        this.orderStatus.setText(ECommerceUtil.getOrderStatus(this.mOrderDetailModel.currentStatus, this));
        if (!(this.mOrderDetailModel.shippingTrackingCode == null && this.mOrderDetailModel.shippingTrackingCompany == null) && (this.mOrderDetailModel.currentStatus.equalsIgnoreCase(ECommerceConstant.SHIPPED) || this.mOrderDetailModel.currentStatus.equalsIgnoreCase(ECommerceConstant.DELIVERED))) {
            this.cargoMainLayout.setVisibility(0);
            if (this.mOrderDetailModel.shippingTrackingCompany != null) {
                this.cargoCompanyNameTextView.setText(this.mOrderDetailModel.shippingTrackingCompany);
            } else {
                this.cargoCompanyNameTextView.setVisibility(8);
            }
            if (this.mOrderDetailModel.shippingTrackingCode != null) {
                this.cargoTrackingNumberTextView.setText(this.mOrderDetailModel.shippingTrackingCode);
            } else {
                this.cargoTrackingNumberTextView.setVisibility(8);
            }
        } else {
            this.cargoMainLayout.setVisibility(8);
        }
        try {
            ECommerceUtil.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            this.orderDate.setText(ECommerceUtil.dateFormatOrderDetail.format(ECommerceUtil.dateFormat.parse(this.mOrderDetailModel.createdDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.orderCodeTextView.setText(this.mOrderDetailModel.orderCode);
        if (DynamicConstants.MobiRoller_Stage) {
            this.deliveryAddressDescription.setText(getString(R.string.preview_e_commerce_shipping_address_sample));
            this.billingAddressDescription.setText(getString(R.string.preview_e_commerce_shipping_address_sample));
        } else {
            this.deliveryAddressDescription.setText(String.format(this.mOrderDetailModel.shippingAddress.getDescriptionArea(), new Object[]{this.mOrderDetailModel.buyer.getFullName()}));
            this.billingAddressDescription.setText(String.format(this.mOrderDetailModel.billingAddress.getBillingDescriptionArea(), new Object[]{this.mOrderDetailModel.buyer.getFullName()}));
        }
        if (this.mOrderDetailModel.paymentType != null) {
            if (this.mOrderDetailModel.paymentType.equalsIgnoreCase(ECommerceConstant.PAY_AT_DOOR)) {
                this.paymentMethodTitle.setText(getString(R.string.e_commerce_payment_method_selection_pay_at_door));
                this.paymentMethodDescription.setVisibility(8);
                this.paymentLayout.requestLayout();
            } else if (this.mOrderDetailModel.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE) || this.mOrderDetailModel.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
                this.paymentMethodTitle.setText(getString(R.string.e_commerce_payment_method_selection_credit_card));
                if (this.mOrderDetailModel.cardNumber != null) {
                    MobirollerTextView mobirollerTextView = this.paymentMethodDescription;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mOrderDetailModel.cardNumber.replaceAll("[^0-9]", ""));
                    sb.append(" **** **** ****");
                    mobirollerTextView.setText(sb.toString());
                    this.paymentMethodDescription.setVisibility(0);
                }
            } else if (this.mOrderDetailModel.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
                this.paymentMethodTitle.setText(getString(R.string.e_commerce_payment_method_selection_transfer));
                if (this.mOrderDetailModel.paymentAccount != null) {
                    this.bankTransferLayout.setVisibility(0);
                    MobirollerTextView mobirollerTextView2 = this.bankNameTextView;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.mOrderDetailModel.paymentAccount.name);
                    sb2.append(" - ");
                    sb2.append(this.mOrderDetailModel.paymentAccount.accountName);
                    sb2.append(" / ");
                    sb2.append(this.mOrderDetailModel.paymentAccount.accountCode);
                    mobirollerTextView2.setText(sb2.toString());
                    this.receiverNameTextView.setText(Html.fromHtml(getString(R.string.e_commerce_order_details_bank_receiver, new Object[]{this.mOrderDetailModel.paymentAccount.nameSurname})));
                    this.bankAccountTextView.setText(Html.fromHtml(getString(R.string.e_commerce_order_details_bank_account, new Object[]{this.mOrderDetailModel.paymentAccount.accountNumber})));
                    this.bankIbanTextView.setText(Html.fromHtml(getString(R.string.e_commerce_order_details_bank_iban, new Object[]{this.mOrderDetailModel.paymentAccount.accountAddress})));
                    this.paymentMethodDescription.setVisibility(8);
                } else if (this.mOrderDetailModel.bankAccount != null) {
                    this.paymentMethodDescription.setText(this.mOrderDetailModel.bankAccount);
                    this.paymentMethodDescription.setVisibility(0);
                } else {
                    this.paymentMethodDescription.setVisibility(8);
                }
            } else if (this.mOrderDetailModel.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
                this.paymentMethodTitle.setText(getString(R.string.e_commerce_payment_method_selection_paypal));
            }
        }
        MobirollerTextView mobirollerTextView3 = this.productSubTotalTextView;
        StringBuilder sb3 = new StringBuilder();
        String str = ": ";
        sb3.append(str);
        sb3.append(ECommerceUtil.getPriceString(this.mOrderDetailModel.totalPrice - this.mOrderDetailModel.shippingPrice));
        String str2 = " ";
        sb3.append(str2);
        sb3.append(ECommerceUtil.getCurrency(this.mOrderDetailModel.currency));
        mobirollerTextView3.setText(sb3.toString());
        MobirollerTextView mobirollerTextView4 = this.shippingTotalTextView;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(ECommerceUtil.getPriceString(this.mOrderDetailModel.shippingPrice));
        sb4.append(str2);
        sb4.append(ECommerceUtil.getCurrency(this.mOrderDetailModel.currency));
        mobirollerTextView4.setText(sb4.toString());
        MobirollerTextView mobirollerTextView5 = this.totalTextView;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(ECommerceUtil.getPriceString(this.mOrderDetailModel.totalPrice));
        sb5.append(str2);
        sb5.append(ECommerceUtil.getCurrency(this.mOrderDetailModel.currency));
        mobirollerTextView5.setText(sb5.toString());
        OrderDetailProductListAdapter orderDetailProductListAdapter = new OrderDetailProductListAdapter(this, this.mOrderDetailModel.productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.productList.setLayoutManager(linearLayoutManager);
        this.productList.setAdapter(orderDetailProductListAdapter);
        if (this.productList.getItemDecorationCount() == 0) {
            this.productList.addItemDecoration(new DividerItemDecoration(this.productList.getContext(), linearLayoutManager.getOrientation()));
        }
        this.mainLayout.setVisibility(0);
        this.bottomLayout.setVisibility(0);
        this.progressViewHelper.dismiss();
    }

    public static void startActivity(Context context, String str) {
        context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra("orderId", str));
    }

    public static void startActivity(Context context, OrderDetailModel orderDetailModel) {
        context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra(ECommerceConstant.ORDER_DETAIL_MODEL, orderDetailModel));
    }

    @OnClick({2131362122})
    public void onClickCargoText() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("cargo", this.mOrderDetailModel.shippingTrackingCode));
        Toast.makeText(this, getString(R.string.e_commerce_order_details_cargo_copy_toast_message), 0).show();
    }

    @OnClick({2131361972})
    public void onClickIbanText() {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("iban", this.mOrderDetailModel.paymentAccount.accountAddress));
        Toast.makeText(this, getString(R.string.e_commerce_order_details_bank_copy_toast_message), 0).show();
    }
}
