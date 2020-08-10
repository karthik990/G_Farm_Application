package com.google.api;

import com.google.api.Property.PropertyType;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface PropertyOrBuilder extends MessageLiteOrBuilder {
    String getDescription();

    ByteString getDescriptionBytes();

    String getName();

    ByteString getNameBytes();

    PropertyType getType();

    int getTypeValue();
}
