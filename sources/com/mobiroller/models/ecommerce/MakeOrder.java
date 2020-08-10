package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.models.user.UserBillingAddressModel;
import com.mobiroller.models.user.UserShippingAddressModel;
import java.io.Serializable;
import java.util.List;

public class MakeOrder implements Serializable {
    @SerializedName("bankAccount")
    public String bankAccount;
    public BankAccount bankAccountModel;
    @SerializedName("billingAddress")
    public MakeOrderAddress billingAddress;
    @SerializedName("buyer")
    public BuyerOrderModel buyer;
    @SerializedName("creditCard")
    public OrderCard card;
    public String currency;
    public String orderId;
    @SerializedName("paymentAccount")
    public BankAccount paymentAccount;
    @SerializedName("paymentType")
    public String paymentType;
    public double productPriceTotal;
    @SerializedName("products")
    public List<OrderProductModel> products;
    @SerializedName("shippingAddress")
    public MakeOrderAddress shippingAddress;
    public double shippingPrice;
    public boolean tryAgain;
    public UserBillingAddressModel userBillingAddressModel;
    @SerializedName("userId")
    public String userId;
    @SerializedName("userNote")
    public String userNote;
    public UserShippingAddressModel userShippingAddressModel;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MakeOrder{userId='");
        sb.append(this.userId);
        sb.append('\'');
        sb.append(", userNote='");
        sb.append(this.userNote);
        sb.append('\'');
        sb.append(", bankAccount='");
        sb.append(this.bankAccount);
        sb.append('\'');
        sb.append(", paymentType='");
        sb.append(this.paymentType);
        sb.append('\'');
        sb.append(", products=");
        sb.append(this.products);
        sb.append(", shippingAddress=");
        sb.append(this.shippingAddress);
        sb.append(", billingAddress=");
        sb.append(this.billingAddress);
        sb.append(", buyer=");
        sb.append(this.buyer);
        sb.append(", card=");
        sb.append(this.card);
        sb.append(", productPriceTotal=");
        sb.append(this.productPriceTotal);
        sb.append(", shippingPrice=");
        sb.append(this.shippingPrice);
        sb.append(", currency='");
        sb.append(this.currency);
        sb.append('\'');
        sb.append(", bankAccountModel=");
        sb.append(this.bankAccountModel);
        sb.append(", userShippingAddressModel=");
        sb.append(this.userShippingAddressModel);
        sb.append(", userBillingAddressModel=");
        sb.append(this.userBillingAddressModel);
        sb.append('}');
        return sb.toString();
    }

    public CompleteOrder getCompleteOrderModel() {
        if (this.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE3DS) || this.paymentType.equalsIgnoreCase(ECommerceConstant.ONLINE)) {
            return new CompleteOrder(this.orderId, this.userNote, this.paymentType, this.card);
        }
        if (!this.paymentType.equalsIgnoreCase(ECommerceConstant.TRANSFER)) {
            return new CompleteOrder(this.orderId, this.userNote, this.paymentType);
        }
        CompleteOrder completeOrder = new CompleteOrder(this.orderId, this.userNote, this.paymentType, this.bankAccount, this.paymentAccount);
        return completeOrder;
    }
}
