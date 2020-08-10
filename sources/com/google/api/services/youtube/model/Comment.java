package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Comment extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1837id;
    @Key
    private String kind;
    @Key
    private CommentSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public Comment setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1837id;
    }

    public Comment setId(String str) {
        this.f1837id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Comment setKind(String str) {
        this.kind = str;
        return this;
    }

    public CommentSnippet getSnippet() {
        return this.snippet;
    }

    public Comment setSnippet(CommentSnippet commentSnippet) {
        this.snippet = commentSnippet;
        return this;
    }

    public Comment set(String str, Object obj) {
        return (Comment) super.set(str, obj);
    }

    public Comment clone() {
        return (Comment) super.clone();
    }
}
