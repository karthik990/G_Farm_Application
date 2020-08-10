package com.google.firebase.storage.network;

import android.net.Uri;
import android.text.TextUtils;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.internal.SlashUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ResumableUploadStartRequest extends ResumableNetworkRequest {
    private final String contentType;
    private final JSONObject metadata;

    /* access modifiers changed from: protected */
    public String getAction() {
        return "POST";
    }

    public ResumableUploadStartRequest(Uri uri, FirebaseApp firebaseApp, JSONObject jSONObject, String str) {
        super(uri, firebaseApp);
        this.metadata = jSONObject;
        this.contentType = str;
        if (TextUtils.isEmpty(this.contentType)) {
            this.mException = new IllegalArgumentException("mContentType is null or empty");
        }
        super.setCustomHeader("X-Goog-Upload-Protocol", "resumable");
        super.setCustomHeader("X-Goog-Upload-Command", TtmlNode.START);
        super.setCustomHeader("X-Goog-Upload-Header-Content-Type", this.contentType);
    }

    /* access modifiers changed from: protected */
    public String getURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(sUploadUrl);
        sb.append(this.mGsUri.getAuthority());
        sb.append("/o");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getQueryParameters() throws UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String pathWithoutBucket = getPathWithoutBucket();
        arrayList.add(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        arrayList2.add(pathWithoutBucket != null ? SlashUtil.unSlashize(pathWithoutBucket) : "");
        arrayList.add("uploadType");
        arrayList2.add("resumable");
        return getPostDataString(arrayList, arrayList2, false);
    }

    /* access modifiers changed from: protected */
    public JSONObject getOutputJSON() {
        return this.metadata;
    }
}
