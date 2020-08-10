package com.mobiroller.models.events;

import com.mobiroller.models.ecommerce.BankAccount;
import com.mobiroller.models.ecommerce.OrderCard;
import java.io.Serializable;

public class OrderPaymentEvent implements Serializable {
    public BankAccount bankAccount;
    public OrderCard orderCard;
    public String paymentType;
}
