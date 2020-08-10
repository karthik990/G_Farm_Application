package com.mobiroller.models.ecommerce;

import java.io.Serializable;

public class OrderFailedResponse implements Serializable {
    public Order order;
    public OrderPaymentResult paymentResult;

    public OrderFailedResponse(ECommerceErrorResponse eCommerceErrorResponse) {
        this.order = eCommerceErrorResponse.order;
        this.paymentResult = eCommerceErrorResponse.paymentResult;
    }

    public OrderFailedResponse() {
    }
}
