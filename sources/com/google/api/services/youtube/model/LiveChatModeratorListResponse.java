package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class LiveChatModeratorListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<LiveChatModerator> items;
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
        Data.nullOf(LiveChatModerator.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public LiveChatModeratorListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public LiveChatModeratorListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<LiveChatModerator> getItems() {
        return this.items;
    }

    public LiveChatModeratorListResponse setItems(List<LiveChatModerator> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public LiveChatModeratorListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public LiveChatModeratorListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public LiveChatModeratorListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public LiveChatModeratorListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public LiveChatModeratorListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public LiveChatModeratorListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public LiveChatModeratorListResponse set(String str, Object obj) {
        return (LiveChatModeratorListResponse) super.set(str, obj);
    }

    public LiveChatModeratorListResponse clone() {
        return (LiveChatModeratorListResponse) super.clone();
    }
}
