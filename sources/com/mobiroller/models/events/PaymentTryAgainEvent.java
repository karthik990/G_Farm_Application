package com.mobiroller.models.events;

import java.io.Serializable;

public class PaymentTryAgainEvent implements Serializable {
    public String orderId;

    public PaymentTryAgainEvent(String str) {
        this.orderId = str;
    }
}
