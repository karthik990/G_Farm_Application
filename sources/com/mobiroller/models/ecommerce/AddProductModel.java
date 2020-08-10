package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class AddProductModel implements Serializable {
    public String displayName;
    public String productId;
    public int quantity;

    public AddProductModel(String str, int i, String str2) {
        this.productId = str;
        this.quantity = i;
        if (str2 == null || str2.length() <= 200) {
            this.displayName = str2;
        } else {
            this.displayName = str2.substring(0, 200);
        }
    }
}
