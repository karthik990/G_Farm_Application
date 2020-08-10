package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderProduct implements Serializable {
    @SerializedName("campaignPrice")
    public double campaignPrice;
    @SerializedName("currency")
    public String currency;
    @SerializedName("featuredImage")
    public ProductImage featuredImage;
    @SerializedName("id")

    /* renamed from: id */
    public String f2179id;
    @SerializedName("paidPrice")
    public double price;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("title")
    public String title;
}
