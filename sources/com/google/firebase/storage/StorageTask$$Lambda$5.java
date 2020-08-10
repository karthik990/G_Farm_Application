package com.google.firebase.storage;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$5 implements OnRaise {
    private static final StorageTask$$Lambda$5 instance = new StorageTask$$Lambda$5();

    private StorageTask$$Lambda$5() {
    }

    public static OnRaise lambdaFactory$() {
        return instance;
    }

    public void raise(Object obj, Object obj2) {
        ((OnProgressListener) obj).onProgress((ProvideError) obj2);
    }
}
