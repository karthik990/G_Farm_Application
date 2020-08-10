package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class CommentListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<Comment> items;
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
        Data.nullOf(Comment.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public CommentListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public CommentListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<Comment> getItems() {
        return this.items;
    }

    public CommentListResponse setItems(List<Comment> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public CommentListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public CommentListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public CommentListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public CommentListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public CommentListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public CommentListResponse set(String str, Object obj) {
        return (CommentListResponse) super.set(str, obj);
    }

    public CommentListResponse clone() {
        return (CommentListResponse) super.clone();
    }
}
