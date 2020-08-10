package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class OrderDetailResponse implements Serializable {
    public OrderDetailModel data;
    public List<String> errors;
    public boolean isUserFriendlyMessage;
    public String key;
    public String message;
}
