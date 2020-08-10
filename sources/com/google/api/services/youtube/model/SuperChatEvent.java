package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class SuperChatEvent extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1856id;
    @Key
    private String kind;
    @Key
    private SuperChatEventSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public SuperChatEvent setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1856id;
    }

    public SuperChatEvent setId(String str) {
        this.f1856id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public SuperChatEvent setKind(String str) {
        this.kind = str;
        return this;
    }

    public SuperChatEventSnippet getSnippet() {
        return this.snippet;
    }

    public SuperChatEvent setSnippet(SuperChatEventSnippet superChatEventSnippet) {
        this.snippet = superChatEventSnippet;
        return this;
    }

    public SuperChatEvent set(String str, Object obj) {
        return (SuperChatEvent) super.set(str, obj);
    }

    public SuperChatEvent clone() {
        return (SuperChatEvent) super.clone();
    }
}
