package com.google.firestore.p050v1;

import com.google.firestore.p050v1.ListenRequest.TargetChangeCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.Map;

/* renamed from: com.google.firestore.v1.ListenRequestOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface ListenRequestOrBuilder extends MessageLiteOrBuilder {
    boolean containsLabels(String str);

    Target getAddTarget();

    String getDatabase();

    ByteString getDatabaseBytes();

    @Deprecated
    Map<String, String> getLabels();

    int getLabelsCount();

    Map<String, String> getLabelsMap();

    String getLabelsOrDefault(String str, String str2);

    String getLabelsOrThrow(String str);

    int getRemoveTarget();

    TargetChangeCase getTargetChangeCase();
}
