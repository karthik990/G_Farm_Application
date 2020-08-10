package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue.TimerId;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AsyncQueue$$Lambda$7 implements Runnable {
    private final AsyncQueue arg$1;
    private final TimerId arg$2;

    private AsyncQueue$$Lambda$7(AsyncQueue asyncQueue, TimerId timerId) {
        this.arg$1 = asyncQueue;
        this.arg$2 = timerId;
    }

    public static Runnable lambdaFactory$(AsyncQueue asyncQueue, TimerId timerId) {
        return new AsyncQueue$$Lambda$7(asyncQueue, timerId);
    }

    public void run() {
        AsyncQueue.lambda$runDelayedTasksUntil$8(this.arg$1, this.arg$2);
    }
}
