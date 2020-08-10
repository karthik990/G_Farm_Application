package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    @SerializedName("cardNumber")
    public String cardNumber;
    @SerializedName("createDate")
    public String createdDate;
    @SerializedName("currency")
    public String currency;
    @SerializedName("currentStatus")
    public String currentStatus;
    @SerializedName("id")

    /* renamed from: id */
    public String f2177id;
    @SerializedName("orderCode")
    public String orderCode;
    @SerializedName("paymentType")
    public String paymentType;
    @SerializedName("productList")
    public List<OrderProduct> productList;
    @SerializedName("shippingPrice")
    public double shippingPrice;
    @SerializedName("shippingTrackingCode")
    public String shippingTrackingCode;
    @SerializedName("shippingTrackingCompany")
    public String shippingTrackingCompany;
    @SerializedName("totalPrice")
    public double totalPrice;
    public String userNote;
}
