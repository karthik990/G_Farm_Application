package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<Channel> items;
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
        Data.nullOf(Channel.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public ChannelListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public ChannelListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<Channel> getItems() {
        return this.items;
    }

    public ChannelListResponse setItems(List<Channel> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public ChannelListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public ChannelListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public ChannelListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public ChannelListResponse setPrevPageToken(String str) {
        this.prevPageToken = str;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public ChannelListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public ChannelListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public ChannelListResponse set(String str, Object obj) {
        return (ChannelListResponse) super.set(str, obj);
    }

    public ChannelListResponse clone() {
        return (ChannelListResponse) super.clone();
    }
}
