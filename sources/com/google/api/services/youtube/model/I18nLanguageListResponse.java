package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class I18nLanguageListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<I18nLanguage> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    static {
        Data.nullOf(I18nLanguage.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public I18nLanguageListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public I18nLanguageListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<I18nLanguage> getItems() {
        return this.items;
    }

    public I18nLanguageListResponse setItems(List<I18nLanguage> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public I18nLanguageListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public I18nLanguageListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public I18nLanguageListResponse set(String str, Object obj) {
        return (I18nLanguageListResponse) super.set(str, obj);
    }

    public I18nLanguageListResponse clone() {
        return (I18nLanguageListResponse) super.clone();
    }
}
