package com.google.firestore.p050v1;

import com.google.firestore.p050v1.GetDocumentRequest.ConsistencySelectorCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.GetDocumentRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface GetDocumentRequestOrBuilder extends MessageLiteOrBuilder {
    ConsistencySelectorCase getConsistencySelectorCase();

    DocumentMask getMask();

    String getName();

    ByteString getNameBytes();

    Timestamp getReadTime();

    ByteString getTransaction();

    boolean hasMask();
}
