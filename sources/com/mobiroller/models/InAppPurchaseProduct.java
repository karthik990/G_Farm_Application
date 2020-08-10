package com.mobiroller.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InAppPurchaseProduct implements Serializable, Comparable<InAppPurchaseProduct> {
    @SerializedName("bat")
    public String buyActionText;
    @SerializedName("d")
    public String description;
    @SerializedName("dat")
    public String detailActionText;
    @SerializedName("dau")
    public String detailActionUrl;
    @SerializedName("ia")
    public boolean isActive;
    @SerializedName("aoti")
    public String oneTimeProductId;
    @SerializedName("oi")
    public int orderIndex;
    @SerializedName("pi")
    public int productId;
    @SerializedName("pil")
    public List<InAppPurchaseProductImageModel> productImages = new ArrayList();
    @SerializedName("sl")
    public List<String> screenList = new ArrayList();
    @SerializedName("asil")
    public String[] subscriptionProductId;
    @SerializedName("t")
    public String title;
    @SerializedName("ty")
    public int type;
    @SerializedName("updateDate")
    public String updateDate;
    @SerializedName("vu")
    public String videoUrl;

    public int compareTo(InAppPurchaseProduct inAppPurchaseProduct) {
        return this.orderIndex > inAppPurchaseProduct.orderIndex ? 1 : -1;
    }
}
