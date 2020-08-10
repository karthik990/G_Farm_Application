package com.google.firestore.p050v1;

import com.google.firestore.p050v1.TransactionOptions.ModeCase;
import com.google.firestore.p050v1.TransactionOptions.ReadOnly;
import com.google.firestore.p050v1.TransactionOptions.ReadWrite;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.TransactionOptionsOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface TransactionOptionsOrBuilder extends MessageLiteOrBuilder {
    ModeCase getModeCase();

    ReadOnly getReadOnly();

    ReadWrite getReadWrite();
}
