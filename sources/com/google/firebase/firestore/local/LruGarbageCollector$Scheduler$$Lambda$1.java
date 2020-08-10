package com.google.firebase.firestore.local;

import com.google.firebase.firestore.local.LruGarbageCollector.Scheduler;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class LruGarbageCollector$Scheduler$$Lambda$1 implements Runnable {
    private final Scheduler arg$1;

    private LruGarbageCollector$Scheduler$$Lambda$1(Scheduler scheduler) {
        this.arg$1 = scheduler;
    }

    public static Runnable lambdaFactory$(Scheduler scheduler) {
        return new LruGarbageCollector$Scheduler$$Lambda$1(scheduler);
    }

    public void run() {
        Scheduler.lambda$scheduleGC$0(this.arg$1);
    }
}
