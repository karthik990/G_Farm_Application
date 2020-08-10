package com.mobiroller.models.ecommerce;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class OrderResponseInner implements Serializable {
    public List<String> errors;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
    @SerializedName("order")
    public Order order;
    @SerializedName("payment")
    public OrderPaymentResult paymentResult;
}
