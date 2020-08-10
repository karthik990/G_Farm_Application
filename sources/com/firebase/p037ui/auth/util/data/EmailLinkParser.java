package com.firebase.p037ui.auth.util.data;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.util.data.EmailLinkParser */
public class EmailLinkParser {
    private static final String CONTINUE_URL = "continueUrl";
    private static String LINK = "link";
    private static final String OOB_CODE = "oobCode";
    private Map<String, String> mParams;

    /* renamed from: com.firebase.ui.auth.util.data.EmailLinkParser$LinkParameters */
    public static class LinkParameters {
        public static final String ANONYMOUS_USER_ID_IDENTIFIER = "ui_auid";
        public static final String FORCE_SAME_DEVICE_IDENTIFIER = "ui_sd";
        public static final String PROVIDER_ID_IDENTIFIER = "ui_pid";
        public static final String SESSION_IDENTIFIER = "ui_sid";
    }

    public EmailLinkParser(String str) {
        Preconditions.checkNotEmpty(str);
        this.mParams = parseUri(Uri.parse(str));
        if (this.mParams.isEmpty()) {
            throw new IllegalArgumentException("Invalid link: no parameters found");
        }
    }

    public String getOobCode() {
        return (String) this.mParams.get(OOB_CODE);
    }

    public String getSessionId() {
        return (String) this.mParams.get(LinkParameters.SESSION_IDENTIFIER);
    }

    public String getAnonymousUserId() {
        return (String) this.mParams.get(LinkParameters.ANONYMOUS_USER_ID_IDENTIFIER);
    }

    public boolean getForceSameDeviceBit() {
        String str = (String) this.mParams.get(LinkParameters.FORCE_SAME_DEVICE_IDENTIFIER);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
    }

    public String getProviderId() {
        return (String) this.mParams.get(LinkParameters.PROVIDER_ID_IDENTIFIER);
    }

    private Map<String, String> parseUri(Uri uri) {
        HashMap hashMap = new HashMap();
        try {
            for (String str : uri.getQueryParameterNames()) {
                if (!str.equalsIgnoreCase(LINK)) {
                    if (!str.equalsIgnoreCase(CONTINUE_URL)) {
                        String queryParameter = uri.getQueryParameter(str);
                        if (queryParameter != null) {
                            hashMap.put(str, queryParameter);
                        }
                    }
                }
                Map parseUri = parseUri(Uri.parse(uri.getQueryParameter(str)));
                if (parseUri != null) {
                    hashMap.putAll(parseUri);
                }
            }
        } catch (Exception unused) {
        }
        return hashMap;
    }
}
