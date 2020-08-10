package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class UpdateCartItemQuantity implements Serializable {
    int amount;

    public UpdateCartItemQuantity(int i) {
        this.amount = i;
    }
}
