package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.SupportedPaymentType;
import com.mobiroller.views.custom.MobirollerTextView;

public class PaymentTypeViewHolder extends ViewHolder {
    public View itemView;
    @BindView(2131362910)
    ImageView paymentTypeIcon;
    @BindView(2131362911)
    MobirollerTextView paymentTypeName;

    public PaymentTypeViewHolder(View view) {
        super(view);
        this.itemView = view;
        ButterKnife.bind((Object) this, view);
    }

    public void bind(SupportedPaymentType supportedPaymentType) {
        if (supportedPaymentType.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE)) {
            this.paymentTypeName.setText(this.itemView.getContext().getString(R.string.e_commerce_payment_method_selection_credit_card));
            this.paymentTypeIcon.setImageResource(R.drawable.ic_credit_card_icon);
        } else if (supportedPaymentType.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS)) {
            this.paymentTypeName.setText(this.itemView.getContext().getString(R.string.e_commerce_payment_method_selection_credit_card));
            this.paymentTypeIcon.setImageResource(R.drawable.ic_credit_card_icon);
        } else if (supportedPaymentType.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
            this.paymentTypeName.setText(this.itemView.getContext().getString(R.string.e_commerce_payment_method_selection_transfer));
            this.paymentTypeIcon.setImageResource(R.drawable.ic_bank_iconn);
        } else if (supportedPaymentType.paymentType.equalsIgnoreCase(ECommerceConstant.PAY_AT_DOOR)) {
            this.paymentTypeName.setText(this.itemView.getContext().getString(R.string.e_commerce_payment_method_selection_pay_at_door));
            this.paymentTypeIcon.setImageResource(R.drawable.ic_pay_at_door_icon);
        } else if (supportedPaymentType.paymentType.equalsIgnoreCase(ECommerceConstant.PAYPAL)) {
            this.paymentTypeName.setText(this.itemView.getContext().getString(R.string.e_commerce_payment_method_selection_paypal));
            this.paymentTypeIcon.setImageResource(R.drawable.ic_paypal);
        }
    }
}
