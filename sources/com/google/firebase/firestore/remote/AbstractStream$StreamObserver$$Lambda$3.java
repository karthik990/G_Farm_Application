package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.util.Logger;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AbstractStream$StreamObserver$$Lambda$3 implements Runnable {
    private final StreamObserver arg$1;

    private AbstractStream$StreamObserver$$Lambda$3(StreamObserver streamObserver) {
        this.arg$1 = streamObserver;
    }

    public static Runnable lambdaFactory$(StreamObserver streamObserver) {
        return new AbstractStream$StreamObserver$$Lambda$3(streamObserver);
    }

    public void run() {
        Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream is ready", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
    }
}
