package com.google.firestore.p050v1;

import com.google.firestore.p050v1.BatchGetDocumentsResponse.ResultCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.BatchGetDocumentsResponseOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface BatchGetDocumentsResponseOrBuilder extends MessageLiteOrBuilder {
    Document getFound();

    String getMissing();

    ByteString getMissingBytes();

    Timestamp getReadTime();

    ResultCase getResultCase();

    ByteString getTransaction();

    boolean hasReadTime();
}
