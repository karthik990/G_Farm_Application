package com.mobiroller.models.ecommerce;

import java.util.List;

public class OrderList {
    public List<String> errors;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
    public List<Order> order;
}
