package com.mobiroller.viewholders.user;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import com.mobiroller.views.custom.MobirollerTextView;

public class PopupAddressViewHolder extends ViewHolder {
    @BindView(2131361920)
    ConstraintLayout addressContentInnerLayout;
    @BindView(2131361921)
    MobirollerTextView addressDescriptionFirstLine;
    @BindView(2131361922)
    MobirollerTextView addressDescriptionSecondLine;
    @BindView(2131361923)
    MobirollerTextView addressDescriptionThirdLine;
    @BindView(2131361925)
    ConstraintLayout addressMainLayout;
    @BindView(2131361926)
    MobirollerTextView addressTitle;
    private UserBillingAddressModel billingAddressModel;
    private AppCompatActivity context;
    private UserShippingAddressModel shippingAddressModel;

    public PopupAddressViewHolder(View view, AppCompatActivity appCompatActivity) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.context = appCompatActivity;
    }

    public void bindShipping(UserShippingAddressModel userShippingAddressModel) {
        this.shippingAddressModel = userShippingAddressModel;
        this.addressTitle.setText(userShippingAddressModel.title);
        this.addressDescriptionFirstLine.setText(userShippingAddressModel.getPopupAddressFirstLine());
        this.addressDescriptionSecondLine.setText(userShippingAddressModel.getPopupAddressSecondLine());
        this.addressDescriptionThirdLine.setText(userShippingAddressModel.getPopupAddressThirdLine());
    }

    public void bindBilling(UserBillingAddressModel userBillingAddressModel) {
        this.billingAddressModel = userBillingAddressModel;
        this.addressTitle.setText(userBillingAddressModel.title);
        this.addressDescriptionFirstLine.setText(userBillingAddressModel.getPopupAddressFirstLine());
        this.addressDescriptionSecondLine.setText(userBillingAddressModel.getPopupAddressSecondLine());
        this.addressDescriptionThirdLine.setText(userBillingAddressModel.getPopupAddressThirdLine());
    }
}
