package com.google.firestore.p050v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.firestore.v1.DocumentMaskOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface DocumentMaskOrBuilder extends MessageLiteOrBuilder {
    String getFieldPaths(int i);

    ByteString getFieldPathsBytes(int i);

    int getFieldPathsCount();

    List<String> getFieldPathsList();
}
