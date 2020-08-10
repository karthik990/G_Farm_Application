package com.google.firebase.firestore.util;

import com.google.android.gms.tasks.TaskExecutors;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Executors {
    public static final Executor DEFAULT_CALLBACK_EXECUTOR = TaskExecutors.MAIN_THREAD;
    public static final Executor DIRECT_EXECUTOR = Executors$$Lambda$1.lambdaFactory$();

    private Executors() {
    }
}
