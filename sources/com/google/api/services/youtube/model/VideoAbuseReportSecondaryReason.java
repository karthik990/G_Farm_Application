package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoAbuseReportSecondaryReason extends GenericJson {
    @Key

    /* renamed from: id */
    private String f1859id;
    @Key
    private String label;

    public String getId() {
        return this.f1859id;
    }

    public VideoAbuseReportSecondaryReason setId(String str) {
        this.f1859id = str;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public VideoAbuseReportSecondaryReason setLabel(String str) {
        this.label = str;
        return this;
    }

    public VideoAbuseReportSecondaryReason set(String str, Object obj) {
        return (VideoAbuseReportSecondaryReason) super.set(str, obj);
    }

    public VideoAbuseReportSecondaryReason clone() {
        return (VideoAbuseReportSecondaryReason) super.clone();
    }
}
