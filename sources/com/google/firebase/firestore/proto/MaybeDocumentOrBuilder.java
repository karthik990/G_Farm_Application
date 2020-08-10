package com.google.firebase.firestore.proto;

import com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase;
import com.google.firestore.p050v1.Document;
import com.google.protobuf.MessageLiteOrBuilder;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface MaybeDocumentOrBuilder extends MessageLiteOrBuilder {
    Document getDocument();

    DocumentTypeCase getDocumentTypeCase();

    boolean getHasCommittedMutations();

    NoDocument getNoDocument();

    UnknownDocument getUnknownDocument();
}
