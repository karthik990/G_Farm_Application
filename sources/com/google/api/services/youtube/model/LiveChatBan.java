package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatBan extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1845id;
    @Key
    private String kind;
    @Key
    private LiveChatBanSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public LiveChatBan setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1845id;
    }

    public LiveChatBan setId(String str) {
        this.f1845id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public LiveChatBan setKind(String str) {
        this.kind = str;
        return this;
    }

    public LiveChatBanSnippet getSnippet() {
        return this.snippet;
    }

    public LiveChatBan setSnippet(LiveChatBanSnippet liveChatBanSnippet) {
        this.snippet = liveChatBanSnippet;
        return this;
    }

    public LiveChatBan set(String str, Object obj) {
        return (LiveChatBan) super.set(str, obj);
    }

    public LiveChatBan clone() {
        return (LiveChatBan) super.clone();
    }
}
