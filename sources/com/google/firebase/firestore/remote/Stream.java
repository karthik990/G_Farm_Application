package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.Stream.StreamCallback;
import p043io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface Stream<CallbackType extends StreamCallback> {

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum State {
        Initial,
        Starting,
        Open,
        Error,
        Backoff
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public interface StreamCallback {
        void onClose(Status status);

        void onOpen();
    }

    void inhibitBackoff();

    boolean isOpen();

    boolean isStarted();

    void start();

    void stop();
}
