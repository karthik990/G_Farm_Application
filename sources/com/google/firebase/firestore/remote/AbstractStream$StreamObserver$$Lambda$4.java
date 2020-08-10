package com.google.firebase.firestore.remote;

import p043io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AbstractStream$StreamObserver$$Lambda$4 implements Runnable {
    private final StreamObserver arg$1;
    private final Status arg$2;

    private AbstractStream$StreamObserver$$Lambda$4(StreamObserver streamObserver, Status status) {
        this.arg$1 = streamObserver;
        this.arg$2 = status;
    }

    public static Runnable lambdaFactory$(StreamObserver streamObserver, Status status) {
        return new AbstractStream$StreamObserver$$Lambda$4(streamObserver, status);
    }

    public void run() {
        StreamObserver.lambda$onClose$3(this.arg$1, this.arg$2);
    }
}
