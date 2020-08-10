package com.mobiroller.viewholders.user;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.constants.UserConstants;
import com.mobiroller.fragments.user.AddressBottomSheetDialogFragment;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.DeleteAddressModel;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerTextView;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressViewHolder extends ViewHolder {
    @BindView(2131361917)
    MobirollerTextView address;
    /* access modifiers changed from: private */
    public UserBillingAddressModel billingAddressModel;
    @BindView(2131362108)
    Button cancelButton;
    /* access modifiers changed from: private */
    public AppCompatActivity context;
    @BindView(2131361881)
    ImageView delete;
    @BindView(2131362239)
    ConstraintLayout deleteLayout;
    @BindView(2131362240)
    MobirollerTextView deleteTextView;
    @BindView(2131361885)
    ImageView edit;
    /* access modifiers changed from: private */
    public UserShippingAddressModel shippingAddressModel;
    @BindView(2131363258)
    MobirollerTextView title;

    public AddressViewHolder(View view, AppCompatActivity appCompatActivity) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.context = appCompatActivity;
        String str = "#f4f6f6";
        this.cancelButton.setBackgroundColor(Color.parseColor(str));
        if (VERSION.SDK_INT >= 21) {
            this.cancelButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(str)));
        }
    }

    public void bindShipping(UserShippingAddressModel userShippingAddressModel) {
        this.shippingAddressModel = userShippingAddressModel;
        this.title.setText(userShippingAddressModel.title);
        this.address.setText(userShippingAddressModel.getListDeliveryDescriptionArea());
        MobirollerTextView mobirollerTextView = this.deleteTextView;
        mobirollerTextView.setText(Html.fromHtml(mobirollerTextView.getContext().getString(R.string.user_my_address_delete_address_title, new Object[]{userShippingAddressModel.title})));
    }

    public void bindBilling(UserBillingAddressModel userBillingAddressModel) {
        this.billingAddressModel = userBillingAddressModel;
        this.title.setText(userBillingAddressModel.title);
        this.address.setText(userBillingAddressModel.getListBillingDescriptionArea());
        MobirollerTextView mobirollerTextView = this.deleteTextView;
        mobirollerTextView.setText(Html.fromHtml(mobirollerTextView.getContext().getString(R.string.user_my_address_delete_address_title, new Object[]{userBillingAddressModel.title})));
    }

    @OnClick({2131361881})
    public void onClickDeleteAction() {
        this.deleteLayout.setVisibility(0);
    }

    @OnClick({2131361885})
    public void onClickEditAction() {
        AddressBottomSheetDialogFragment addressBottomSheetDialogFragment = new AddressBottomSheetDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(UserConstants.BUNDLE_EXTRA_USER_ADDRESS_EDIT, true);
        UserBillingAddressModel userBillingAddressModel = this.billingAddressModel;
        String str = UserConstants.BUNDLE_EXTRA_USER_ADDRESS_MODEL;
        String str2 = UserConstants.BUNDLE_EXTRA_USER_ADDRESS;
        if (userBillingAddressModel != null) {
            bundle.putString(str2, UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS);
            bundle.putSerializable(str, this.billingAddressModel);
        } else {
            bundle.putString(str2, UserConstants.BUNDLE_EXTRA_USER_SHIPPING_ADDRESS);
            bundle.putSerializable(str, this.shippingAddressModel);
        }
        addressBottomSheetDialogFragment.setArguments(bundle);
        addressBottomSheetDialogFragment.show(this.context.getSupportFragmentManager(), addressBottomSheetDialogFragment.getTag());
    }

    @OnClick({2131362108})
    public void onClickCancelButton() {
        this.deleteLayout.setVisibility(8);
    }

    @OnClick({2131362238})
    public void onClickDeleteButton() {
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionInfo(this.context);
            return;
        }
        RequestHelper requestHelper = new RequestHelper(this.title.getContext(), UtilManager.sharedPrefHelper());
        if (this.billingAddressModel != null) {
            requestHelper.getApplyzeUserAPIService().deleteBillingAddress(UserHelper.getUserId(), this.billingAddressModel.f2189id, this.context.getString(R.string.applyze_api_key), this.context.getString(R.string.applyze_app_key)).enqueue(new Callback<Void>() {
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        EventBus.getDefault().post(new DeleteAddressModel(AddressViewHolder.this.billingAddressModel.f2189id));
                    } else {
                        ErrorUtils.showErrorToast(AddressViewHolder.this.context);
                    }
                }

                public void onFailure(Call<Void> call, Throwable th) {
                    ErrorUtils.showErrorToast(AddressViewHolder.this.context);
                }
            });
        } else {
            requestHelper.getApplyzeUserAPIService().deleteShippingAddress(UserHelper.getUserId(), this.shippingAddressModel.f2189id, this.context.getString(R.string.applyze_api_key), this.context.getString(R.string.applyze_app_key)).enqueue(new Callback<Void>() {
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        EventBus.getDefault().post(new DeleteAddressModel(AddressViewHolder.this.shippingAddressModel.f2189id));
                    } else {
                        ErrorUtils.showErrorToast(AddressViewHolder.this.context);
                    }
                }

                public void onFailure(Call<Void> call, Throwable th) {
                    ErrorUtils.showErrorToast(AddressViewHolder.this.context);
                }
            });
        }
    }
}
