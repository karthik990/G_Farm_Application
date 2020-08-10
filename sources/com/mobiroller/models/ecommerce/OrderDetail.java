package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class OrderDetail implements Serializable {
    @SerializedName("order")
    public OrderDetailModel order;
}
