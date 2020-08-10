package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class SponsorListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<Sponsor> items;
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
        Data.nullOf(Sponsor.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public SponsorListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public SponsorListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<Sponsor> getItems() {
        return this.items;
    }

    public SponsorListResponse setItems(List<Sponsor> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public SponsorListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public SponsorListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public SponsorListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public SponsorListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public SponsorListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public SponsorListResponse set(String str, Object obj) {
        return (SponsorListResponse) super.set(str, obj);
    }

    public SponsorListResponse clone() {
        return (SponsorListResponse) super.clone();
    }
}
