package com.google.api;

import com.google.api.Monitoring.MonitoringDestination;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface MonitoringOrBuilder extends MessageLiteOrBuilder {
    MonitoringDestination getConsumerDestinations(int i);

    int getConsumerDestinationsCount();

    List<MonitoringDestination> getConsumerDestinationsList();

    MonitoringDestination getProducerDestinations(int i);

    int getProducerDestinationsCount();

    List<MonitoringDestination> getProducerDestinationsList();
}
