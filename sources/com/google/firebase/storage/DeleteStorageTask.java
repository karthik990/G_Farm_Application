package com.google.firebase.storage;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.DeleteNetworkRequest;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class DeleteStorageTask implements Runnable {
    private static final String TAG = "DeleteStorageTask";
    private TaskCompletionSource<Void> mPendingResult;
    private ExponentialBackoffSender mSender;
    private StorageReference mStorageRef;

    public DeleteStorageTask(StorageReference storageReference, TaskCompletionSource<Void> taskCompletionSource) {
        Preconditions.checkNotNull(storageReference);
        Preconditions.checkNotNull(taskCompletionSource);
        this.mStorageRef = storageReference;
        this.mPendingResult = taskCompletionSource;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    public void run() {
        DeleteNetworkRequest deleteNetworkRequest = new DeleteNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp());
        this.mSender.sendWithExponentialBackoff(deleteNetworkRequest);
        deleteNetworkRequest.completeTask(this.mPendingResult, null);
    }
}
