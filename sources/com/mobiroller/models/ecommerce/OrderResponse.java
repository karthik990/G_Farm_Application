package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable {
    public OrderResponseInner data;
    public List<String> errors;
}
