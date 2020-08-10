package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoCategoryListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<VideoCategory> items;
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
        Data.nullOf(VideoCategory.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public VideoCategoryListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public VideoCategoryListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<VideoCategory> getItems() {
        return this.items;
    }

    public VideoCategoryListResponse setItems(List<VideoCategory> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public VideoCategoryListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public VideoCategoryListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public VideoCategoryListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public VideoCategoryListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public VideoCategoryListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public VideoCategoryListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public VideoCategoryListResponse set(String str, Object obj) {
        return (VideoCategoryListResponse) super.set(str, obj);
    }

    public VideoCategoryListResponse clone() {
        return (VideoCategoryListResponse) super.clone();
    }
}
