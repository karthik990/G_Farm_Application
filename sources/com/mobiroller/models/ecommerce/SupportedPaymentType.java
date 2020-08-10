package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class SupportedPaymentType implements Serializable {
    public PaymentConfiguration configuration;
    public String description;
    public String paymentType;

    public SupportedPaymentType() {
    }

    public SupportedPaymentType(String str, String str2) {
        this.paymentType = str;
        this.description = str2;
    }
}
