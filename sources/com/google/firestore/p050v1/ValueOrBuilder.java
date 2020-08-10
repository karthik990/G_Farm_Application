package com.google.firestore.p050v1;

import com.google.firestore.p050v1.Value.ValueTypeCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.NullValue;
import com.google.protobuf.Timestamp;
import com.google.type.LatLng;

/* renamed from: com.google.firestore.v1.ValueOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ValueOrBuilder extends MessageLiteOrBuilder {
    ArrayValue getArrayValue();

    boolean getBooleanValue();

    ByteString getBytesValue();

    double getDoubleValue();

    LatLng getGeoPointValue();

    long getIntegerValue();

    MapValue getMapValue();

    NullValue getNullValue();

    int getNullValueValue();

    String getReferenceValue();

    ByteString getReferenceValueBytes();

    String getStringValue();

    ByteString getStringValueBytes();

    Timestamp getTimestampValue();

    ValueTypeCase getValueTypeCase();
}
