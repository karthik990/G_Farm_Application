package com.google.firestore.p050v1;

import com.google.firestore.p050v1.Target.DocumentsTarget;
import com.google.firestore.p050v1.Target.QueryTarget;
import com.google.firestore.p050v1.Target.ResumeTypeCase;
import com.google.firestore.p050v1.Target.TargetTypeCase;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

/* renamed from: com.google.firestore.v1.TargetOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface TargetOrBuilder extends MessageLiteOrBuilder {
    DocumentsTarget getDocuments();

    boolean getOnce();

    QueryTarget getQuery();

    Timestamp getReadTime();

    ByteString getResumeToken();

    ResumeTypeCase getResumeTypeCase();

    int getTargetId();

    TargetTypeCase getTargetTypeCase();
}
