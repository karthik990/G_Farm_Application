package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.adapters.ecommerce.ShoppingCartListAdapter;
import com.mobiroller.adapters.ecommerce.ShoppingInvalidCartListAdapter;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ecommerce.ShoppingCartListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.BuyerOrderModel;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.ECommerceResponse;
import com.mobiroller.models.ecommerce.MakeOrder;
import com.mobiroller.models.ecommerce.ShoppingCartMessage;
import com.mobiroller.models.ecommerce.ShoppingCartResponse;
import com.mobiroller.models.ecommerce.UpdateCartItemQuantity;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerDialog.Builder;
import com.mobiroller.views.custom.MobirollerDialog.DialogButtonCallback;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends ECommerceBaseActivity implements ShoppingCartListener {
    static final int SHOPPING_CART_REQUEST_CODE = 11;
    private ShoppingCartListAdapter adapter;
    @BindView(2131362106)
    MobirollerClickableLayout campaignLayout;
    @BindView(2131362182)
    MobirollerButton confirmButton;
    @BindView(2131362194)
    ConstraintLayout contentLayout;
    private ECommerceRequestHelper eCommerceRequestHelper = new ECommerceRequestHelper();
    @BindView(2131362329)
    ConstraintLayout emptyLayout;
    @BindView(2131362571)
    MobirollerTextView infoTextView;
    /* access modifiers changed from: private */
    public MaterialDialog invalidDialog;
    @BindView(2131362581)
    MobirollerTextView itemCountTextView;
    @BindView(2131362617)
    RecyclerView list;
    @BindView(2131362999)
    MobirollerTextView productSubTotalTextView;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    private Parcelable recyclerViewState;
    @BindView(2131363136)
    MobirollerTextView shippingTotalTextView;
    /* access modifiers changed from: private */
    public ShoppingCartResponse shoppingCart;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @BindView(2131363286)
    MobirollerTextView totalTextView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_e_commerce_shopping_cart);
        ButterKnife.bind((Activity) this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        new ToolbarHelper().setStatusBar(this);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShoppingCartActivity.this.onBackPressed();
            }
        });
        setTitle(getString(R.string.e_commerce_shopping_cart_title));
        Intent intent = getIntent();
        String str = ECommerceConstant.SHOPPING_CART;
        if (intent.hasExtra(str)) {
            this.shoppingCart = (ShoppingCartResponse) getIntent().getSerializableExtra(str);
            setContent();
            return;
        }
        getShoppingCart();
    }

    /* access modifiers changed from: 0000 */
    public void setEmpty() {
        this.contentLayout.setVisibility(8);
        this.emptyLayout.setVisibility(0);
    }

    /* access modifiers changed from: 0000 */
    public void setContent() {
        MaterialDialog materialDialog = this.invalidDialog;
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
        this.contentLayout.setVisibility(0);
        this.emptyLayout.setVisibility(8);
        if (this.shoppingCart.items == null || this.shoppingCart.items.size() == 0) {
            setEmpty();
        } else {
            setAdapter();
            boolean z = true;
            this.itemCountTextView.setText(getString(R.string.e_commerce_shopping_cart_item_count, new Object[]{Integer.valueOf(this.shoppingCart.items.size())}));
            MobirollerTextView mobirollerTextView = this.productSubTotalTextView;
            StringBuilder sb = new StringBuilder();
            String str = ": ";
            sb.append(str);
            sb.append(ECommerceUtil.getPriceString(this.shoppingCart.subTotalPrice));
            String str2 = " ";
            sb.append(str2);
            sb.append(ECommerceUtil.getCurrency(this.shoppingCart.currency));
            mobirollerTextView.setText(sb.toString());
            MobirollerTextView mobirollerTextView2 = this.shippingTotalTextView;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(ECommerceUtil.getPriceString(this.shoppingCart.shippingPrice));
            sb2.append(str2);
            sb2.append(ECommerceUtil.getCurrency(this.shoppingCart.currency));
            mobirollerTextView2.setText(sb2.toString());
            MobirollerTextView mobirollerTextView3 = this.totalTextView;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ECommerceUtil.getPriceString(this.shoppingCart.totalPrice));
            sb3.append(str2);
            sb3.append(ECommerceUtil.getCurrency(this.shoppingCart.currency));
            mobirollerTextView3.setText(sb3.toString());
            if (this.shoppingCart.messages == null || this.shoppingCart.messages.size() == 0) {
                this.campaignLayout.setVisibility(8);
            } else {
                int i = 0;
                while (true) {
                    if (i >= this.shoppingCart.messages.size()) {
                        z = false;
                        break;
                    } else if (((ShoppingCartMessage) this.shoppingCart.messages.get(i)).type.equalsIgnoreCase("Campaign")) {
                        this.campaignLayout.setVisibility(0);
                        this.infoTextView.setText(((ShoppingCartMessage) this.shoppingCart.messages.get(i)).message);
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    this.campaignLayout.setVisibility(8);
                }
            }
        }
        if (this.shoppingCart.invalidItems != null && this.shoppingCart.invalidItems.size() > 0) {
            ShoppingInvalidCartListAdapter shoppingInvalidCartListAdapter = new ShoppingInvalidCartListAdapter(this, this.shoppingCart.invalidItems, this);
            MaterialDialog materialDialog2 = this.invalidDialog;
            if (materialDialog2 != null) {
                materialDialog2.dismiss();
            }
            this.invalidDialog = new Builder().setContext(this).setIconResource(R.drawable.ic_edit_white_24dp).setColor(Color.parseColor("#F8E7D8")).setTitle(getString(R.string.e_commerce_shopping_cart_updating_popup_title)).setAutoDismiss(false).setListener(new DialogButtonCallback() {
                public void onClickButton() {
                    if (!(ShoppingCartActivity.this.shoppingCart.items == null || ShoppingCartActivity.this.shoppingCart.items.size() == 0)) {
                        ShoppingCartActivity.this.invalidDialog.dismiss();
                    }
                    ShoppingCartActivity.this.validateShopping();
                }
            }).setDescription(getString(R.string.e_commerce_shopping_cart_updating_popup_description)).setType(MobirollerDialogType.LIST_WITH_BUTTON).setButtonText(getString(R.string.e_commerce_shopping_cart_invalid_popup_button)).setAdapter(shoppingInvalidCartListAdapter).show();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAdapter() {
        this.adapter = new ShoppingCartListAdapter(this, this.shoppingCart.items, this);
        this.list.setAdapter(this.adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.list.setLayoutManager(linearLayoutManager);
        if (this.list.getItemDecorationCount() == 0) {
            this.list.addItemDecoration(new DividerItemDecoration(this.list.getContext(), linearLayoutManager.getOrientation()));
        }
        if (this.recyclerViewState != null) {
            this.list.getLayoutManager().onRestoreInstanceState(this.recyclerViewState);
        }
    }

    /* access modifiers changed from: private */
    public void getShoppingCart() {
        if (this.adapter != null) {
            this.recyclerViewState = this.list.getLayoutManager().onSaveInstanceState();
        }
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getShoppingCart(UserHelper.getUserId()), new ECommerceCallBack<ShoppingCartResponse>() {
            public void done() {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
            }

            public void onSuccess(ShoppingCartResponse shoppingCartResponse) {
                ShoppingCartActivity.this.shoppingCart = shoppingCartResponse;
                if (!(ShoppingCartActivity.this.shoppingCart == null || ShoppingCartActivity.this.shoppingCart.items == null)) {
                    ECommerceUtil.badgeCount = ShoppingCartActivity.this.shoppingCart.items.size();
                    EventBus.getDefault().post(new ShoppingCartCountEvent(ShoppingCartActivity.this.shoppingCart.items.size()));
                }
                ShoppingCartActivity.this.setContent();
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }

            public void onNetworkError(String str) {
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void validateShopping() {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().validateShoppingCart(UserHelper.getUserId()), new ECommerceCallBack<ShoppingCartResponse>() {
            public void done() {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
            }

            public void onSuccess(ShoppingCartResponse shoppingCartResponse) {
                ShoppingCartActivity.this.shoppingCart = shoppingCartResponse;
                ShoppingCartActivity.this.setContent();
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }

            public void onNetworkError(String str) {
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }
        });
    }

    private void removeItemFromShoppingCart(String str) {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.getAPIService().removeItemFromShoppingCart(UserHelper.getUserId(), str).enqueue(new Callback<ECommerceResponse>() {
            public void onResponse(Call<ECommerceResponse> call, Response<ECommerceResponse> response) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    ShoppingCartActivity.this.getShoppingCart();
                }
            }

            public void onFailure(Call<ECommerceResponse> call, Throwable th) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }
        });
    }

    private void updateCartItemQuantity(String str, int i) {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.getAPIService().updateItemQuantity(UserHelper.getUserId(), str, new UpdateCartItemQuantity(i)).enqueue(new Callback<ECommerceResponse>() {
            public void onResponse(Call<ECommerceResponse> call, Response<ECommerceResponse> response) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    ShoppingCartActivity.this.getShoppingCart();
                }
            }

            public void onFailure(Call<ECommerceResponse> call, Throwable th) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void clearShoppingCart() {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.getAPIService().clearShoppingCart(UserHelper.getUserId()).enqueue(new Callback<ECommerceResponse>() {
            public void onResponse(Call<ECommerceResponse> call, Response<ECommerceResponse> response) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful()) {
                    ShoppingCartActivity.this.getShoppingCart();
                }
            }

            public void onFailure(Call<ECommerceResponse> call, Throwable th) {
                if (!ShoppingCartActivity.this.isFinishing() && ShoppingCartActivity.this.progressViewHelper != null && ShoppingCartActivity.this.progressViewHelper.isShowing()) {
                    ShoppingCartActivity.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(ShoppingCartActivity.this);
            }
        });
    }

    public void onClickRemoveItem(String str) {
        removeItemFromShoppingCart(str);
    }

    public void onClickUpdateQuantity(String str, int i) {
        updateCartItemQuantity(str, i);
    }

    @OnClick({2131362182})
    public void onClickCheckout() {
        MakeOrder makeOrder = new MakeOrder();
        makeOrder.userId = UserHelper.getUserId();
        makeOrder.buyer = new BuyerOrderModel();
        OrderFlowActivity.startActivityForResult(makeOrder, this);
    }

    @OnClick({2131363178})
    public void onClickStartShoppingButton() {
        finish();
    }

    @OnClick({2131362163})
    public void onClickClearShoppingCart() {
        new Builder().setContext(this).setType(MobirollerDialogType.BUTTON).setColor(Color.parseColor("#F8E7D8")).setButtonText(getString(R.string.e_commerce_shopping_cart_clear_cart_button)).setListener(new DialogButtonCallback() {
            public void onClickButton() {
                ShoppingCartActivity.this.clearShoppingCart();
            }
        }).setIconResource(R.drawable.ic_trash_icon).setTitle(getString(R.string.e_commerce_shopping_cart_clear_cart_title)).setDescription(getString(R.string.e_commerce_shopping_cart_clear_cart_description)).show();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 11 && i2 == -1) {
            finish();
        }
    }
}
