package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CompleteOrder implements Serializable {
    @SerializedName("bankAccount")
    public String bankAccount;
    @SerializedName("card")
    public OrderCard card;
    @SerializedName("nonce")
    public String nonce;
    @SerializedName("orderId")
    public String orderId;
    @SerializedName("paymentAccount")
    public BankAccount paymentAccount;
    @SerializedName("paymentType")
    public String paymentType;
    @SerializedName("userNote")
    public String userNote;

    public CompleteOrder() {
    }

    public CompleteOrder(String str, String str2, String str3, OrderCard orderCard) {
        this.orderId = str;
        this.userNote = str2;
        this.paymentType = str3;
        this.card = orderCard;
    }

    public CompleteOrder(String str, String str2, String str3, String str4, BankAccount bankAccount2) {
        this.orderId = str;
        this.userNote = str2;
        this.paymentType = str3;
        this.bankAccount = str4;
        this.paymentAccount = bankAccount2;
    }

    public CompleteOrder(String str, String str2, String str3) {
        this.orderId = str;
        this.userNote = str2;
        this.paymentType = str3;
    }
}
