package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;
import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AsyncQueue$$Lambda$8 implements Comparator {
    private static final AsyncQueue$$Lambda$8 instance = new AsyncQueue$$Lambda$8();

    private AsyncQueue$$Lambda$8() {
    }

    public static Comparator lambdaFactory$() {
        return instance;
    }

    public int compare(Object obj, Object obj2) {
        return AsyncQueue.lambda$runDelayedTasksUntil$7((DelayedTask) obj, (DelayedTask) obj2);
    }
}
