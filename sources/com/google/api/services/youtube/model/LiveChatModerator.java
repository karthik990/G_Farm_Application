package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatModerator extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1847id;
    @Key
    private String kind;
    @Key
    private LiveChatModeratorSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public LiveChatModerator setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1847id;
    }

    public LiveChatModerator setId(String str) {
        this.f1847id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public LiveChatModerator setKind(String str) {
        this.kind = str;
        return this;
    }

    public LiveChatModeratorSnippet getSnippet() {
        return this.snippet;
    }

    public LiveChatModerator setSnippet(LiveChatModeratorSnippet liveChatModeratorSnippet) {
        this.snippet = liveChatModeratorSnippet;
        return this;
    }

    public LiveChatModerator set(String str, Object obj) {
        return (LiveChatModerator) super.set(str, obj);
    }

    public LiveChatModerator clone() {
        return (LiveChatModerator) super.clone();
    }
}
