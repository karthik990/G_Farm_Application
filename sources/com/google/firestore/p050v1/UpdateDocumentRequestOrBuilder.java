package com.google.firestore.p050v1;

import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.UpdateDocumentRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface UpdateDocumentRequestOrBuilder extends MessageLiteOrBuilder {
    Precondition getCurrentDocument();

    Document getDocument();

    DocumentMask getMask();

    DocumentMask getUpdateMask();

    boolean hasCurrentDocument();

    boolean hasDocument();

    boolean hasMask();

    boolean hasUpdateMask();
}
