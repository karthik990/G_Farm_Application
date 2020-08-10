package com.mobiroller.models.ecommerce;

import java.util.List;

public class OrderListResponse {
    public List<Order> data;
    public List<String> errors;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
}
