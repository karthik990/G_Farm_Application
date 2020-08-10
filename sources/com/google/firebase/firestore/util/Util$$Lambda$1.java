package com.google.firebase.firestore.util;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class Util$$Lambda$1 implements Continuation {
    private static final Util$$Lambda$1 instance = new Util$$Lambda$1();

    private Util$$Lambda$1() {
    }

    public static Continuation lambdaFactory$() {
        return instance;
    }

    public Object then(Task task) {
        return Util.lambda$static$0(task);
    }
}
