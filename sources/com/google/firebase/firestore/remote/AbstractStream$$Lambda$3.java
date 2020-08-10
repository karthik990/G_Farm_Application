package com.google.firebase.firestore.remote;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class AbstractStream$$Lambda$3 implements Runnable {
    private final AbstractStream arg$1;

    private AbstractStream$$Lambda$3(AbstractStream abstractStream) {
        this.arg$1 = abstractStream;
    }

    public static Runnable lambdaFactory$(AbstractStream abstractStream) {
        return new AbstractStream$$Lambda$3(abstractStream);
    }

    public void run() {
        AbstractStream.lambda$start$0(this.arg$1);
    }
}
