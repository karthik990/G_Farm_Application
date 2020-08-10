package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class I18nRegion extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1842id;
    @Key
    private String kind;
    @Key
    private I18nRegionSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public I18nRegion setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1842id;
    }

    public I18nRegion setId(String str) {
        this.f1842id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public I18nRegion setKind(String str) {
        this.kind = str;
        return this;
    }

    public I18nRegionSnippet getSnippet() {
        return this.snippet;
    }

    public I18nRegion setSnippet(I18nRegionSnippet i18nRegionSnippet) {
        this.snippet = i18nRegionSnippet;
        return this;
    }

    public I18nRegion set(String str, Object obj) {
        return (I18nRegion) super.set(str, obj);
    }

    public I18nRegion clone() {
        return (I18nRegion) super.clone();
    }
}
