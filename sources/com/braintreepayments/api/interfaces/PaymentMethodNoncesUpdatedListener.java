package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.PaymentMethodNonce;
import java.util.List;

public interface PaymentMethodNoncesUpdatedListener extends BraintreeListener {
    void onPaymentMethodNoncesUpdated(List<PaymentMethodNonce> list);
}
