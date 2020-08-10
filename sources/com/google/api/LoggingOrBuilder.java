package com.google.api;

import com.google.api.Logging.LoggingDestination;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface LoggingOrBuilder extends MessageLiteOrBuilder {
    LoggingDestination getConsumerDestinations(int i);

    int getConsumerDestinationsCount();

    List<LoggingDestination> getConsumerDestinationsList();

    LoggingDestination getProducerDestinations(int i);

    int getProducerDestinationsCount();

    List<LoggingDestination> getProducerDestinationsList();
}
