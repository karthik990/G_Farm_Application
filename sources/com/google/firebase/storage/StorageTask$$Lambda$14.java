package com.google.firebase.storage;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class StorageTask$$Lambda$14 implements OnSuccessListener {
    private final TaskCompletionSource arg$1;

    private StorageTask$$Lambda$14(TaskCompletionSource taskCompletionSource) {
        this.arg$1 = taskCompletionSource;
    }

    public static OnSuccessListener lambdaFactory$(TaskCompletionSource taskCompletionSource) {
        return new StorageTask$$Lambda$14(taskCompletionSource);
    }

    public void onSuccess(Object obj) {
        this.arg$1.setResult(obj);
    }
}
