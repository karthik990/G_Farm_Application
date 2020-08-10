package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class CommentThreadListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<CommentThread> items;
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
        Data.nullOf(CommentThread.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public CommentThreadListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public CommentThreadListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<CommentThread> getItems() {
        return this.items;
    }

    public CommentThreadListResponse setItems(List<CommentThread> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentThreadListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public CommentThreadListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public CommentThreadListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public CommentThreadListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public CommentThreadListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public CommentThreadListResponse set(String str, Object obj) {
        return (CommentThreadListResponse) super.set(str, obj);
    }

    public CommentThreadListResponse clone() {
        return (CommentThreadListResponse) super.clone();
    }
}
