package com.google.firestore.p050v1;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.Map;

/* renamed from: com.google.firestore.v1.MapValueOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface MapValueOrBuilder extends MessageLiteOrBuilder {
    boolean containsFields(String str);

    @Deprecated
    Map<String, Value> getFields();

    int getFieldsCount();

    Map<String, Value> getFieldsMap();

    Value getFieldsOrDefault(String str, Value value);

    Value getFieldsOrThrow(String str);
}
