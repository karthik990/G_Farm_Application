package com.google.firebase.storage;

import com.google.android.gms.tasks.OnCompleteListener;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$3 implements OnRaise {
    private final StorageTask arg$1;

    private StorageTask$$Lambda$3(StorageTask storageTask) {
        this.arg$1 = storageTask;
    }

    public static OnRaise lambdaFactory$(StorageTask storageTask) {
        return new StorageTask$$Lambda$3(storageTask);
    }

    public void raise(Object obj, Object obj2) {
        StorageTask.lambda$new$2(this.arg$1, (OnCompleteListener) obj, (ProvideError) obj2);
    }
}
