package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class PlaylistItemListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<PlaylistItem> items;
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
        Data.nullOf(PlaylistItem.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public PlaylistItemListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public PlaylistItemListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<PlaylistItem> getItems() {
        return this.items;
    }

    public PlaylistItemListResponse setItems(List<PlaylistItem> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public PlaylistItemListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public PlaylistItemListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public PlaylistItemListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public PlaylistItemListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public PlaylistItemListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public PlaylistItemListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public PlaylistItemListResponse set(String str, Object obj) {
        return (PlaylistItemListResponse) super.set(str, obj);
    }

    public PlaylistItemListResponse clone() {
        return (PlaylistItemListResponse) super.clone();
    }
}
