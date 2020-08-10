package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class ECommerceErrorResponse implements Serializable {
    public List<String> errors;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
    public Order order;
    public OrderPaymentResult paymentResult;
}
