package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelSectionListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<ChannelSection> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    static {
        Data.nullOf(ChannelSection.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public ChannelSectionListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public ChannelSectionListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<ChannelSection> getItems() {
        return this.items;
    }

    public ChannelSectionListResponse setItems(List<ChannelSection> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public ChannelSectionListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public ChannelSectionListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public ChannelSectionListResponse set(String str, Object obj) {
        return (ChannelSectionListResponse) super.set(str, obj);
    }

    public ChannelSectionListResponse clone() {
        return (ChannelSectionListResponse) super.clone();
    }
}
