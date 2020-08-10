package com.google.firebase.storage;

import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StorageMetadata.Builder;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.UpdateMetadataNetworkRequest;
import org.json.JSONException;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class UpdateMetadataTask implements Runnable {
    private static final String TAG = "UpdateMetadataTask";
    private final StorageMetadata mNewMetadata;
    private final TaskCompletionSource<StorageMetadata> mPendingResult;
    private StorageMetadata mResultMetadata = null;
    private ExponentialBackoffSender mSender;
    private final StorageReference mStorageRef;

    public UpdateMetadataTask(StorageReference storageReference, TaskCompletionSource<StorageMetadata> taskCompletionSource, StorageMetadata storageMetadata) {
        this.mStorageRef = storageReference;
        this.mPendingResult = taskCompletionSource;
        this.mNewMetadata = storageMetadata;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxOperationRetryTimeMillis());
    }

    public void run() {
        String str = TAG;
        try {
            UpdateMetadataNetworkRequest updateMetadataNetworkRequest = new UpdateMetadataNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp(), this.mNewMetadata.createJSONObject());
            this.mSender.sendWithExponentialBackoff(updateMetadataNetworkRequest);
            if (updateMetadataNetworkRequest.isResultSuccess()) {
                try {
                    this.mResultMetadata = new Builder(updateMetadataNetworkRequest.getResultBody(), this.mStorageRef).build();
                } catch (JSONException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to parse a valid JSON object from resulting metadata:");
                    sb.append(updateMetadataNetworkRequest.getRawResult());
                    Log.e(str, sb.toString(), e);
                    this.mPendingResult.setException(StorageException.fromException(e));
                    return;
                }
            }
            TaskCompletionSource<StorageMetadata> taskCompletionSource = this.mPendingResult;
            if (taskCompletionSource != null) {
                updateMetadataNetworkRequest.completeTask(taskCompletionSource, this.mResultMetadata);
            }
        } catch (JSONException e2) {
            Log.e(str, "Unable to create the request from metadata.", e2);
            this.mPendingResult.setException(StorageException.fromException(e2));
        }
    }
}
