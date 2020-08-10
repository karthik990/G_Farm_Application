package com.mobiroller.interfaces.ecommerce;

public interface ShoppingCartListener {
    void onClickRemoveItem(String str);

    void onClickUpdateQuantity(String str, int i);
}
