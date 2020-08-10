package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
abstract class ResumableNetworkRequest extends NetworkRequest {
    static final String COMMAND = "X-Goog-Upload-Command";
    static final String CONTENT_TYPE = "X-Goog-Upload-Header-Content-Type";
    static final String OFFSET = "X-Goog-Upload-Offset";
    static final String PROTOCOL = "X-Goog-Upload-Protocol";

    ResumableNetworkRequest(Uri uri, FirebaseApp firebaseApp) {
        super(uri, firebaseApp);
    }
}
