package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class SearchResult extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private ResourceId f1854id;
    @Key
    private String kind;
    @Key
    private SearchResultSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public SearchResult setEtag(String str) {
        this.etag = str;
        return this;
    }

    public ResourceId getId() {
        return this.f1854id;
    }

    public SearchResult setId(ResourceId resourceId) {
        this.f1854id = resourceId;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public SearchResult setKind(String str) {
        this.kind = str;
        return this;
    }

    public SearchResultSnippet getSnippet() {
        return this.snippet;
    }

    public SearchResult setSnippet(SearchResultSnippet searchResultSnippet) {
        this.snippet = searchResultSnippet;
        return this;
    }

    public SearchResult set(String str, Object obj) {
        return (SearchResult) super.set(str, obj);
    }

    public SearchResult clone() {
        return (SearchResult) super.clone();
    }
}
