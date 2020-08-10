package com.google.firestore.p050v1;

import com.google.firestore.p050v1.Precondition.ConditionTypeCase;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.PreconditionOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface PreconditionOrBuilder extends MessageLiteOrBuilder {
    ConditionTypeCase getConditionTypeCase();

    boolean getExists();

    Timestamp getUpdateTime();
}
