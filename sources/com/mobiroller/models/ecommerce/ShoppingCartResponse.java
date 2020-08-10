package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartResponse implements Serializable {
    public String appId;
    public String createDate;
    public String culture;
    public String currency;
    public List<ShoppingCartItem> invalidItems;
    public List<ShoppingCartItem> items;
    public List<ShoppingCartMessage> messages;
    public double shippingPrice;
    public double subTotalPrice;
    public double totalPrice;
    public String updateDate;
    public String userId;
}
