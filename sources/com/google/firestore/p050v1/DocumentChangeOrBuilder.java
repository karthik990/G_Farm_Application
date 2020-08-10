package com.google.firestore.p050v1;

import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.firestore.v1.DocumentChangeOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface DocumentChangeOrBuilder extends MessageLiteOrBuilder {
    Document getDocument();

    int getRemovedTargetIds(int i);

    int getRemovedTargetIdsCount();

    List<Integer> getRemovedTargetIdsList();

    int getTargetIds(int i);

    int getTargetIdsCount();

    List<Integer> getTargetIdsList();

    boolean hasDocument();
}
