package com.google.firestore.p050v1;

import com.google.firestore.p050v1.ListenResponse.ResponseTypeCase;
import com.google.protobuf.MessageLiteOrBuilder;

/* renamed from: com.google.firestore.v1.ListenResponseOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ListenResponseOrBuilder extends MessageLiteOrBuilder {
    DocumentChange getDocumentChange();

    DocumentDelete getDocumentDelete();

    DocumentRemove getDocumentRemove();

    ExistenceFilter getFilter();

    ResponseTypeCase getResponseTypeCase();

    TargetChange getTargetChange();
}
