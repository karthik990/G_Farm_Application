package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class I18nRegionSnippet extends GenericJson {
    @Key

    /* renamed from: gl */
    private String f1843gl;
    @Key
    private String name;

    public String getGl() {
        return this.f1843gl;
    }

    public I18nRegionSnippet setGl(String str) {
        this.f1843gl = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public I18nRegionSnippet setName(String str) {
        this.name = str;
        return this;
    }

    public I18nRegionSnippet set(String str, Object obj) {
        return (I18nRegionSnippet) super.set(str, obj);
    }

    public I18nRegionSnippet clone() {
        return (I18nRegionSnippet) super.clone();
    }
}
