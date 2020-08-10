package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class GuideCategory extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1839id;
    @Key
    private String kind;
    @Key
    private GuideCategorySnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public GuideCategory setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1839id;
    }

    public GuideCategory setId(String str) {
        this.f1839id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public GuideCategory setKind(String str) {
        this.kind = str;
        return this;
    }

    public GuideCategorySnippet getSnippet() {
        return this.snippet;
    }

    public GuideCategory setSnippet(GuideCategorySnippet guideCategorySnippet) {
        this.snippet = guideCategorySnippet;
        return this;
    }

    public GuideCategory set(String str, Object obj) {
        return (GuideCategory) super.set(str, obj);
    }

    public GuideCategory clone() {
        return (GuideCategory) super.clone();
    }
}
