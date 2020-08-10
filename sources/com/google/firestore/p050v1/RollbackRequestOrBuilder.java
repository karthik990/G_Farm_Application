package com.google.firestore.p050v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.RollbackRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface RollbackRequestOrBuilder extends MessageLiteOrBuilder {
    String getDatabase();

    ByteString getDatabaseBytes();

    ByteString getTransaction();
}
