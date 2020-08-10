package com.firebase.p037ui.auth.util.data;

import android.text.TextUtils;
import com.firebase.p037ui.auth.util.data.EmailLinkParser.LinkParameters;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.common.internal.Preconditions;

/* renamed from: com.firebase.ui.auth.util.data.ContinueUrlBuilder */
public class ContinueUrlBuilder {
    private StringBuilder mContinueUrl;

    public ContinueUrlBuilder(String str) {
        Preconditions.checkNotEmpty(str);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("?");
        this.mContinueUrl = new StringBuilder(sb.toString());
    }

    public ContinueUrlBuilder appendSessionId(String str) {
        addQueryParam(LinkParameters.SESSION_IDENTIFIER, str);
        return this;
    }

    public ContinueUrlBuilder appendAnonymousUserId(String str) {
        addQueryParam(LinkParameters.ANONYMOUS_USER_ID_IDENTIFIER, str);
        return this;
    }

    public ContinueUrlBuilder appendProviderId(String str) {
        addQueryParam(LinkParameters.PROVIDER_ID_IDENTIFIER, str);
        return this;
    }

    public ContinueUrlBuilder appendForceSameDeviceBit(boolean z) {
        addQueryParam(LinkParameters.FORCE_SAME_DEVICE_IDENTIFIER, z ? IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE : "0");
        return this;
    }

    private void addQueryParam(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            StringBuilder sb = this.mContinueUrl;
            this.mContinueUrl.append(String.format("%s%s=%s", new Object[]{sb.charAt(sb.length() - 1) == '?' ? "" : "&", str, str2}));
        }
    }

    public String build() {
        StringBuilder sb = this.mContinueUrl;
        if (sb.charAt(sb.length() - 1) == '?') {
            StringBuilder sb2 = this.mContinueUrl;
            sb2.setLength(sb2.length() - 1);
        }
        return this.mContinueUrl.toString();
    }
}
