package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetMetadataNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class GetDownloadUrlTask implements Runnable {
    private static final String DOWNLOAD_TOKENS_KEY = "downloadTokens";
    private static final String TAG = "GetMetadataTask";
    private TaskCompletionSource<Uri> pendingResult;
    private ExponentialBackoffSender sender;
    private StorageReference storageRef;

    GetDownloadUrlTask(StorageReference storageReference, TaskCompletionSource<Uri> taskCompletionSource) {
        Preconditions.checkNotNull(storageReference);
        Preconditions.checkNotNull(taskCompletionSource);
        this.storageRef = storageReference;
        this.pendingResult = taskCompletionSource;
        FirebaseStorage storage = this.storageRef.getStorage();
        this.sender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxOperationRetryTimeMillis());
    }

    private Uri extractDownloadUrl(JSONObject jSONObject) {
        String optString = jSONObject.optString(DOWNLOAD_TOKENS_KEY);
        if (TextUtils.isEmpty(optString)) {
            return null;
        }
        String str = optString.split(",", -1)[0];
        String str2 = NetworkRequest.getdefaultURL(this.storageRef.getStorageUri());
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("?alt=media&token=");
        sb.append(str);
        return Uri.parse(sb.toString());
    }

    public void run() {
        GetMetadataNetworkRequest getMetadataNetworkRequest = new GetMetadataNetworkRequest(this.storageRef.getStorageUri(), this.storageRef.getApp());
        this.sender.sendWithExponentialBackoff(getMetadataNetworkRequest);
        Uri extractDownloadUrl = getMetadataNetworkRequest.isResultSuccess() ? extractDownloadUrl(getMetadataNetworkRequest.getResultBody()) : null;
        TaskCompletionSource<Uri> taskCompletionSource = this.pendingResult;
        if (taskCompletionSource != null) {
            getMetadataNetworkRequest.completeTask(taskCompletionSource, extractDownloadUrl);
        }
    }
}
