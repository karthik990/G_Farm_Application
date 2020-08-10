package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class CaptionListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<Caption> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    static {
        Data.nullOf(Caption.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public CaptionListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public CaptionListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<Caption> getItems() {
        return this.items;
    }

    public CaptionListResponse setItems(List<Caption> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CaptionListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public CaptionListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public CaptionListResponse set(String str, Object obj) {
        return (CaptionListResponse) super.set(str, obj);
    }

    public CaptionListResponse clone() {
        return (CaptionListResponse) super.clone();
    }
}
