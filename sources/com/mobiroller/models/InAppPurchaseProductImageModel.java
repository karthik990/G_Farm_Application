package com.mobiroller.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class InAppPurchaseProductImageModel implements Serializable, Comparable<InAppPurchaseProductImageModel> {
    @SerializedName("iu")
    public String imageUrl;
    @SerializedName("oi")
    public int orderIndex;
    @SerializedName("updateDate")
    public String updateDate;

    public InAppPurchaseProductImageModel(String str) {
        this.imageUrl = str;
    }

    public int compareTo(InAppPurchaseProductImageModel inAppPurchaseProductImageModel) {
        return this.orderIndex > inAppPurchaseProductImageModel.orderIndex ? 1 : -1;
    }
}
