package com.google.firebase.firestore.remote;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AbstractStream$StreamObserver$$Lambda$2 implements Runnable {
    private final StreamObserver arg$1;
    private final Object arg$2;

    private AbstractStream$StreamObserver$$Lambda$2(StreamObserver streamObserver, Object obj) {
        this.arg$1 = streamObserver;
        this.arg$2 = obj;
    }

    public static Runnable lambdaFactory$(StreamObserver streamObserver, Object obj) {
        return new AbstractStream$StreamObserver$$Lambda$2(streamObserver, obj);
    }

    public void run() {
        StreamObserver.lambda$onNext$1(this.arg$1, this.arg$2);
    }
}
