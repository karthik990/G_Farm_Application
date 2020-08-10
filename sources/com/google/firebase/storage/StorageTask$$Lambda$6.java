package com.google.firebase.storage;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$6 implements OnRaise {
    private static final StorageTask$$Lambda$6 instance = new StorageTask$$Lambda$6();

    private StorageTask$$Lambda$6() {
    }

    public static OnRaise lambdaFactory$() {
        return instance;
    }

    public void raise(Object obj, Object obj2) {
        ((OnPausedListener) obj).onPaused((ProvideError) obj2);
    }
}
