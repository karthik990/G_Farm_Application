package com.mobiroller.models.ecommerce;

import java.io.Serializable;
import java.util.List;

public class PaymentSettings implements Serializable {
    public String cancellationProcedure;
    public String deliveryConditions;
    public String description;
    public String distanceSalesContract;
    public List<BankAccount> paymentAccounts;
    public List<SupportedPaymentType> supportedPaymentTypes;
}
