package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class SuperChatEventListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<SuperChatEvent> items;
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private PageInfo pageInfo;
    @Key
    private TokenPagination tokenPagination;
    @Key
    private String visitorId;

    static {
        Data.nullOf(SuperChatEvent.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public SuperChatEventListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public SuperChatEventListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<SuperChatEvent> getItems() {
        return this.items;
    }

    public SuperChatEventListResponse setItems(List<SuperChatEvent> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public SuperChatEventListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public SuperChatEventListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public SuperChatEventListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public SuperChatEventListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public SuperChatEventListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public SuperChatEventListResponse set(String str, Object obj) {
        return (SuperChatEventListResponse) super.set(str, obj);
    }

    public SuperChatEventListResponse clone() {
        return (SuperChatEventListResponse) super.clone();
    }
}
