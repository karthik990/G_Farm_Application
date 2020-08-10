package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class I18nRegionListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<I18nRegion> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    static {
        Data.nullOf(I18nRegion.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public I18nRegionListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public I18nRegionListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<I18nRegion> getItems() {
        return this.items;
    }

    public I18nRegionListResponse setItems(List<I18nRegion> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public I18nRegionListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public I18nRegionListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public I18nRegionListResponse set(String str, Object obj) {
        return (I18nRegionListResponse) super.set(str, obj);
    }

    public I18nRegionListResponse clone() {
        return (I18nRegionListResponse) super.clone();
    }
}
