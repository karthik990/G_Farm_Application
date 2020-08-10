package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class DeleteNetworkRequest extends NetworkRequest {
    /* access modifiers changed from: protected */
    public String getAction() {
        return "DELETE";
    }

    public DeleteNetworkRequest(Uri uri, FirebaseApp firebaseApp) {
        super(uri, firebaseApp);
    }
}
