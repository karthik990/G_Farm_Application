package com.google.firebase.storage;

import com.google.android.gms.tasks.OnSuccessListener;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$1 implements OnRaise {
    private final StorageTask arg$1;

    private StorageTask$$Lambda$1(StorageTask storageTask) {
        this.arg$1 = storageTask;
    }

    public static OnRaise lambdaFactory$(StorageTask storageTask) {
        return new StorageTask$$Lambda$1(storageTask);
    }

    public void raise(Object obj, Object obj2) {
        StorageTask.lambda$new$0(this.arg$1, (OnSuccessListener) obj, (ProvideError) obj2);
    }
}
