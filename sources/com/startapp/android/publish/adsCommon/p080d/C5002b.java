package com.startapp.android.publish.adsCommon.p080d;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.android.publish.adsCommon.Utils.C4944g;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5154f;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* renamed from: com.startapp.android.publish.adsCommon.d.b */
/* compiled from: StartAppSDK */
public class C5002b implements Serializable {
    private static final long serialVersionUID = 1;
    private String adTag;
    private String clientSessionId;
    private String location;
    private String nonImpressionReason;
    private int offset;
    private String profileId;

    public C5002b() {
        this(null);
    }

    public C5002b(String str) {
        this.adTag = str;
        this.clientSessionId = C4944g.m2886d().mo62033a();
        this.profileId = MetaData.getInstance().getProfileId();
        this.offset = 0;
    }

    public String getAdTag() {
        return this.adTag;
    }

    public String getClientSessionId() {
        return this.clientSessionId;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public int getOffset() {
        return this.offset;
    }

    public C5002b setOffset(int i) {
        this.offset = i;
        return this;
    }

    public String getNonImpressionReason() {
        return this.nonImpressionReason;
    }

    public C5002b setNonImpressionReason(String str) {
        this.nonImpressionReason = str;
        return this;
    }

    public void setLocation(Context context) {
        try {
            C5059m.m3378a().mo62401h(context);
            this.location = C5154f.m3802a(C5154f.m3803a(context, MetaData.getInstance().getLocationConfig().isFiEnabled(), MetaData.getInstance().getLocationConfig().isCoEnabled()));
        } catch (Exception unused) {
            this.location = null;
        }
    }

    private String getLocationQuery() {
        String str = this.location;
        String str2 = "";
        if (str == null || str.equals(str2)) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&locations=");
        sb.append(encode(C5138a.m3717c(this.location)));
        return sb.toString();
    }

    private String getNonImpressionReasonQuery() {
        String str = this.nonImpressionReason;
        String str2 = "";
        if (str == null || str.equals(str2)) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&isShown=false&reason=");
        sb.append(encode(this.nonImpressionReason));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String getOffsetQuery() {
        if (this.offset <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&offset=");
        sb.append(this.offset);
        return sb.toString();
    }

    private String getAdTagQuery() {
        String str = this.adTag;
        String str2 = "";
        if (str == null || str.equals(str2)) {
            return str2;
        }
        int i = 200;
        if (this.adTag.length() < 200) {
            i = this.adTag.length();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&adTag=");
        sb.append(encode(this.adTag.substring(0, i)));
        return sb.toString();
    }

    private String getClientSessionIdQuery() {
        if (this.clientSessionId == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&clientSessionId=");
        sb.append(encode(this.clientSessionId));
        return sb.toString();
    }

    private String getProfileIdQuery() {
        if (this.profileId == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&profileId=");
        sb.append(encode(this.profileId));
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAdTagQuery());
        sb.append(getClientSessionIdQuery());
        sb.append(getProfileIdQuery());
        sb.append(getOffsetQuery());
        sb.append(getNonImpressionReasonQuery());
        sb.append(getLocationQuery());
        return sb.toString();
    }
}
