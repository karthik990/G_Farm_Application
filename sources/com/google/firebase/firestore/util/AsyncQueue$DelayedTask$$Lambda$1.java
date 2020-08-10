package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AsyncQueue$DelayedTask$$Lambda$1 implements Runnable {
    private final DelayedTask arg$1;

    private AsyncQueue$DelayedTask$$Lambda$1(DelayedTask delayedTask) {
        this.arg$1 = delayedTask;
    }

    public static Runnable lambdaFactory$(DelayedTask delayedTask) {
        return new AsyncQueue$DelayedTask$$Lambda$1(delayedTask);
    }

    public void run() {
        this.arg$1.handleDelayElapsed();
    }
}
