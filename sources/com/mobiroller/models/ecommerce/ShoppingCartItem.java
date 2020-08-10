package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class ShoppingCartItem implements Serializable {
    public String createDate;

    /* renamed from: id */
    public String f2184id;
    public boolean isValid;
    public List<ShoppingCartMessage> messages;
    public double price;
    public ProductDetailModel product;
    public String productId;
    public int quantity;
    public String updateDate;
}
