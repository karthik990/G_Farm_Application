package com.mobiroller.models.events;

public class ShoppingCartCountEvent {
    public int shoppingCartItemCount;

    public ShoppingCartCountEvent(int i) {
        this.shoppingCartItemCount = i;
    }
}
