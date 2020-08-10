package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Caption extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1834id;
    @Key
    private String kind;
    @Key
    private CaptionSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public Caption setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1834id;
    }

    public Caption setId(String str) {
        this.f1834id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Caption setKind(String str) {
        this.kind = str;
        return this;
    }

    public CaptionSnippet getSnippet() {
        return this.snippet;
    }

    public Caption setSnippet(CaptionSnippet captionSnippet) {
        this.snippet = captionSnippet;
        return this;
    }

    public Caption set(String str, Object obj) {
        return (Caption) super.set(str, obj);
    }

    public Caption clone() {
        return (Caption) super.clone();
    }
}
