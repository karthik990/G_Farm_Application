package com.mobiroller.models.events;

import com.mobiroller.models.ecommerce.OrderFailedResponse;
import com.mobiroller.models.ecommerce.OrderResponseInner;

public class OrderResponseEvent {
    public OrderFailedResponse failedResponse;
    public OrderResponseInner orderResponse;

    public OrderResponseEvent(OrderResponseInner orderResponseInner) {
        this.orderResponse = orderResponseInner;
    }

    public OrderResponseEvent(OrderFailedResponse orderFailedResponse) {
        this.failedResponse = orderFailedResponse;
    }
}
