package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ProductListModel implements Serializable {
    @SerializedName("campaignPrice")
    public double campaignPrice;
    @SerializedName("currency")
    public String currency;
    @SerializedName("featuredImage")
    public ProductImage featuredImage;
    @SerializedName("id")

    /* renamed from: id */
    public String f2183id;
    @SerializedName("price")
    public double price;
    @SerializedName("shippingPrice")
    public double shippingPrice;
    @SerializedName("stock")
    public int stock;
    @SerializedName("title")
    public String title;

    public boolean isValid() {
        return (this.f2183id == null || this.title == null || this.currency == null) ? false : true;
    }
}
