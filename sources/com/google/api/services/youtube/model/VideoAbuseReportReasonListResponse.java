package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoAbuseReportReasonListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<VideoAbuseReportReason> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    static {
        Data.nullOf(VideoAbuseReportReason.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public VideoAbuseReportReasonListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public VideoAbuseReportReasonListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<VideoAbuseReportReason> getItems() {
        return this.items;
    }

    public VideoAbuseReportReasonListResponse setItems(List<VideoAbuseReportReason> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public VideoAbuseReportReasonListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public VideoAbuseReportReasonListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public VideoAbuseReportReasonListResponse set(String str, Object obj) {
        return (VideoAbuseReportReasonListResponse) super.set(str, obj);
    }

    public VideoAbuseReportReasonListResponse clone() {
        return (VideoAbuseReportReasonListResponse) super.clone();
    }
}
