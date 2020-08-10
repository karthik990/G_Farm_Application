package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatMessage extends GenericJson {
    @Key
    private LiveChatMessageAuthorDetails authorDetails;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1846id;
    @Key
    private String kind;
    @Key
    private LiveChatMessageSnippet snippet;

    public LiveChatMessageAuthorDetails getAuthorDetails() {
        return this.authorDetails;
    }

    public LiveChatMessage setAuthorDetails(LiveChatMessageAuthorDetails liveChatMessageAuthorDetails) {
        this.authorDetails = liveChatMessageAuthorDetails;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public LiveChatMessage setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1846id;
    }

    public LiveChatMessage setId(String str) {
        this.f1846id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public LiveChatMessage setKind(String str) {
        this.kind = str;
        return this;
    }

    public LiveChatMessageSnippet getSnippet() {
        return this.snippet;
    }

    public LiveChatMessage setSnippet(LiveChatMessageSnippet liveChatMessageSnippet) {
        this.snippet = liveChatMessageSnippet;
        return this;
    }

    public LiveChatMessage set(String str, Object obj) {
        return (LiveChatMessage) super.set(str, obj);
    }

    public LiveChatMessage clone() {
        return (LiveChatMessage) super.clone();
    }
}
