package com.google.firebase.firestore.remote;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AbstractStream$$Lambda$1 implements Runnable {
    private final AbstractStream arg$1;
    private final CloseGuardedRunner arg$2;

    private AbstractStream$$Lambda$1(AbstractStream abstractStream, CloseGuardedRunner closeGuardedRunner) {
        this.arg$1 = abstractStream;
        this.arg$2 = closeGuardedRunner;
    }

    public static Runnable lambdaFactory$(AbstractStream abstractStream, CloseGuardedRunner closeGuardedRunner) {
        return new AbstractStream$$Lambda$1(abstractStream, closeGuardedRunner);
    }

    public void run() {
        this.arg$2.run(AbstractStream$$Lambda$3.lambdaFactory$(this.arg$1));
    }
}
