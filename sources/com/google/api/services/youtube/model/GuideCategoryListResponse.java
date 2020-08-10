package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class GuideCategoryListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<GuideCategory> items;
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
        Data.nullOf(GuideCategory.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public GuideCategoryListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public GuideCategoryListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<GuideCategory> getItems() {
        return this.items;
    }

    public GuideCategoryListResponse setItems(List<GuideCategory> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public GuideCategoryListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public GuideCategoryListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public GuideCategoryListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public GuideCategoryListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public GuideCategoryListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public GuideCategoryListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public GuideCategoryListResponse set(String str, Object obj) {
        return (GuideCategoryListResponse) super.set(str, obj);
    }

    public GuideCategoryListResponse clone() {
        return (GuideCategoryListResponse) super.clone();
    }
}
