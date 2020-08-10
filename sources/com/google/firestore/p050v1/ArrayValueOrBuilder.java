package com.google.firestore.p050v1;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.firestore.v1.ArrayValueOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ArrayValueOrBuilder extends MessageLiteOrBuilder {
    Value getValues(int i);

    int getValuesCount();

    List<Value> getValuesList();
}
