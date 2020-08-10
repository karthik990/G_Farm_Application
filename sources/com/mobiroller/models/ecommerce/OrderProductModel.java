package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderProductModel implements Serializable {
    @SerializedName("dn")
    public String displayName;
    @SerializedName("id")

    /* renamed from: id */
    public String f2180id;
    @SerializedName("q")
    public int quantity;

    public OrderProductModel(String str, String str2, int i) {
        this.f2180id = str;
        this.quantity = i;
        this.displayName = str2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderProductModel{id='");
        sb.append(this.f2180id);
        sb.append('\'');
        sb.append(", displayName='");
        sb.append(this.displayName);
        sb.append('\'');
        sb.append(", quantity=");
        sb.append(this.quantity);
        sb.append('}');
        return sb.toString();
    }
}
