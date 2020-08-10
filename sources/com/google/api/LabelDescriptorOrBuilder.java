package com.google.api;

import com.google.api.LabelDescriptor.ValueType;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface LabelDescriptorOrBuilder extends MessageLiteOrBuilder {
    String getDescription();

    ByteString getDescriptionBytes();

    String getKey();

    ByteString getKeyBytes();

    ValueType getValueType();

    int getValueTypeValue();
}
