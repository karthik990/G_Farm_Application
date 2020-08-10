package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.api.client.googleapis.MethodOverride;
import com.google.firebase.FirebaseApp;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class UpdateMetadataNetworkRequest extends NetworkRequest {
    private final JSONObject metadata;

    /* access modifiers changed from: protected */
    public String getAction() {
        return "PUT";
    }

    public UpdateMetadataNetworkRequest(Uri uri, FirebaseApp firebaseApp, JSONObject jSONObject) {
        super(uri, firebaseApp);
        this.metadata = jSONObject;
        setCustomHeader(MethodOverride.HEADER, "PATCH");
    }

    /* access modifiers changed from: protected */
    public JSONObject getOutputJSON() {
        return this.metadata;
    }
}
