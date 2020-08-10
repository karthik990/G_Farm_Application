package com.google.firestore.p050v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.BeginTransactionRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface BeginTransactionRequestOrBuilder extends MessageLiteOrBuilder {
    String getDatabase();

    ByteString getDatabaseBytes();

    TransactionOptions getOptions();

    boolean hasOptions();
}
