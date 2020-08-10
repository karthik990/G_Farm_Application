package com.mobiroller.models.ecommerce;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.annotations.SerializedName;
import com.mobiroller.util.ECommerceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailModel extends ProductListModel implements Serializable {
    @SerializedName("code")
    public String code;
    @SerializedName("description")
    public String description;
    @SerializedName("images")
    public List<ProductImage> images = new ArrayList();
    @SerializedName("maxQuantityPerOrder")
    public int maxQuantityPerOrder;
    @SerializedName("useFixPrice")
    public boolean useFixPrice;

    public String getPriceString() {
        String str = " ";
        if (this.campaignPrice != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            StringBuilder sb = new StringBuilder();
            sb.append(ECommerceUtil.getPriceString(this.campaignPrice));
            sb.append(str);
            sb.append(ECommerceUtil.getCurrency(this.currency));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ECommerceUtil.getPriceString(this.price));
        sb2.append(str);
        sb2.append(ECommerceUtil.getCurrency(this.currency));
        return sb2.toString();
    }
}
