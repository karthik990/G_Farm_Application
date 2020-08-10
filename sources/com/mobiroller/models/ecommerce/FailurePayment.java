package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class FailurePayment implements Serializable {
    public String orderId;
    public String paymentId;

    public FailurePayment() {
    }

    public FailurePayment(String str) {
        this.orderId = str;
    }
}
