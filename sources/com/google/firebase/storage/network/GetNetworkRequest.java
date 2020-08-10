package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class GetNetworkRequest extends NetworkRequest {
    private static final String TAG = "GetNetworkRequest";

    /* access modifiers changed from: protected */
    public String getAction() {
        return "GET";
    }

    public GetNetworkRequest(Uri uri, FirebaseApp firebaseApp, long j) {
        super(uri, firebaseApp);
        if (j != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("bytes=");
            sb.append(j);
            sb.append("-");
            super.setCustomHeader("Range", sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public String getQueryParameters() throws UnsupportedEncodingException {
        return getPostDataString(Collections.singletonList("alt"), Collections.singletonList("media"), true);
    }
}
