package com.google.firestore.p050v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.RunQueryResponseOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface RunQueryResponseOrBuilder extends MessageLiteOrBuilder {
    Document getDocument();

    Timestamp getReadTime();

    int getSkippedResults();

    ByteString getTransaction();

    boolean hasDocument();

    boolean hasReadTime();
}
