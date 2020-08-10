package com.google.firebase.storage;

import com.google.android.gms.tasks.OnFailureListener;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$2 implements OnRaise {
    private final StorageTask arg$1;

    private StorageTask$$Lambda$2(StorageTask storageTask) {
        this.arg$1 = storageTask;
    }

    public static OnRaise lambdaFactory$(StorageTask storageTask) {
        return new StorageTask$$Lambda$2(storageTask);
    }

    public void raise(Object obj, Object obj2) {
        StorageTask.lambda$new$1(this.arg$1, (OnFailureListener) obj, (ProvideError) obj2);
    }
}
