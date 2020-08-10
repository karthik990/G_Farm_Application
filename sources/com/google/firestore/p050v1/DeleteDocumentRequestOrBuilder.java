package com.google.firestore.p050v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.DeleteDocumentRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface DeleteDocumentRequestOrBuilder extends MessageLiteOrBuilder {
    Precondition getCurrentDocument();

    String getName();

    ByteString getNameBytes();

    boolean hasCurrentDocument();
}
