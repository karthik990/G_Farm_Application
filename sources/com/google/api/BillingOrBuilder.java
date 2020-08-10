package com.google.api;

import com.google.api.Billing.BillingDestination;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface BillingOrBuilder extends MessageLiteOrBuilder {
    BillingDestination getConsumerDestinations(int i);

    int getConsumerDestinationsCount();

    List<BillingDestination> getConsumerDestinationsList();
}
