package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ResumableUploadCancelRequest extends ResumableNetworkRequest {
    public static boolean CANCEL_CALLED = false;
    private final String uploadURL;

    /* access modifiers changed from: protected */
    public String getAction() {
        return "POST";
    }

    public ResumableUploadCancelRequest(Uri uri, FirebaseApp firebaseApp, String str) {
        super(uri, firebaseApp);
        CANCEL_CALLED = true;
        if (TextUtils.isEmpty(str)) {
            this.mException = new IllegalArgumentException("uploadURL is null or empty");
        }
        this.uploadURL = str;
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", "cancel");
    }

    /* access modifiers changed from: protected */
    public String getURL() {
        return this.uploadURL;
    }
}
