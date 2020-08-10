package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class CommentThread extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1838id;
    @Key
    private String kind;
    @Key
    private CommentThreadReplies replies;
    @Key
    private CommentThreadSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public CommentThread setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1838id;
    }

    public CommentThread setId(String str) {
        this.f1838id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentThread setKind(String str) {
        this.kind = str;
        return this;
    }

    public CommentThreadReplies getReplies() {
        return this.replies;
    }

    public CommentThread setReplies(CommentThreadReplies commentThreadReplies) {
        this.replies = commentThreadReplies;
        return this;
    }

    public CommentThreadSnippet getSnippet() {
        return this.snippet;
    }

    public CommentThread setSnippet(CommentThreadSnippet commentThreadSnippet) {
        this.snippet = commentThreadSnippet;
        return this;
    }

    public CommentThread set(String str, Object obj) {
        return (CommentThread) super.set(str, obj);
    }

    public CommentThread clone() {
        return (CommentThread) super.clone();
    }
}
