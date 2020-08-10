package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class PlaylistItem extends GenericJson {
    @Key
    private PlaylistItemContentDetails contentDetails;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1852id;
    @Key
    private String kind;
    @Key
    private PlaylistItemSnippet snippet;
    @Key
    private PlaylistItemStatus status;

    public PlaylistItemContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public PlaylistItem setContentDetails(PlaylistItemContentDetails playlistItemContentDetails) {
        this.contentDetails = playlistItemContentDetails;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public PlaylistItem setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1852id;
    }

    public PlaylistItem setId(String str) {
        this.f1852id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public PlaylistItem setKind(String str) {
        this.kind = str;
        return this;
    }

    public PlaylistItemSnippet getSnippet() {
        return this.snippet;
    }

    public PlaylistItem setSnippet(PlaylistItemSnippet playlistItemSnippet) {
        this.snippet = playlistItemSnippet;
        return this;
    }

    public PlaylistItemStatus getStatus() {
        return this.status;
    }

    public PlaylistItem setStatus(PlaylistItemStatus playlistItemStatus) {
        this.status = playlistItemStatus;
        return this;
    }

    public PlaylistItem set(String str, Object obj) {
        return (PlaylistItem) super.set(str, obj);
    }

    public PlaylistItem clone() {
        return (PlaylistItem) super.clone();
    }
}
