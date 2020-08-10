package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class PlaylistListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<Playlist> items;
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private PageInfo pageInfo;
    @Key
    private String prevPageToken;
    @Key
    private TokenPagination tokenPagination;
    @Key
    private String visitorId;

    static {
        Data.nullOf(Playlist.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public PlaylistListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public PlaylistListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<Playlist> getItems() {
        return this.items;
    }

    public PlaylistListResponse setItems(List<Playlist> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public PlaylistListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public PlaylistListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public PlaylistListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public PlaylistListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public PlaylistListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public PlaylistListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public PlaylistListResponse set(String str, Object obj) {
        return (PlaylistListResponse) super.set(str, obj);
    }

    public PlaylistListResponse clone() {
        return (PlaylistListResponse) super.clone();
    }
}
