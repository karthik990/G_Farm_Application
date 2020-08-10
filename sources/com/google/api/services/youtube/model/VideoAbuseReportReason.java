package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoAbuseReportReason extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1858id;
    @Key
    private String kind;
    @Key
    private VideoAbuseReportReasonSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public VideoAbuseReportReason setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1858id;
    }

    public VideoAbuseReportReason setId(String str) {
        this.f1858id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public VideoAbuseReportReason setKind(String str) {
        this.kind = str;
        return this;
    }

    public VideoAbuseReportReasonSnippet getSnippet() {
        return this.snippet;
    }

    public VideoAbuseReportReason setSnippet(VideoAbuseReportReasonSnippet videoAbuseReportReasonSnippet) {
        this.snippet = videoAbuseReportReasonSnippet;
        return this;
    }

    public VideoAbuseReportReason set(String str, Object obj) {
        return (VideoAbuseReportReason) super.set(str, obj);
    }

    public VideoAbuseReportReason clone() {
        return (VideoAbuseReportReason) super.clone();
    }
}
