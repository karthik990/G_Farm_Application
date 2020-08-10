package com.google.firebase.firestore.util;

import p043io.grpc.Metadata;
import p043io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface IncomingStreamObserver<RespT> {
    void onClose(Status status);

    void onHeaders(Metadata metadata);

    void onNext(RespT respt);

    void onReady();
}
