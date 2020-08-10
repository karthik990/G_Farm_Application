package com.google.firestore.p050v1;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.firestore.v1.CursorOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface CursorOrBuilder extends MessageLiteOrBuilder {
    boolean getBefore();

    Value getValues(int i);

    int getValuesCount();

    List<Value> getValuesList();
}
