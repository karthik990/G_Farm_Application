package com.braintreepayments.api.interfaces;

import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import java.util.Collection;

public interface TokenizationParametersListener {
    void onResult(PaymentMethodTokenizationParameters paymentMethodTokenizationParameters, Collection<Integer> collection);
}
