package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ShoppingCartCount implements Serializable {
    @SerializedName("data")
    public int count;
}
