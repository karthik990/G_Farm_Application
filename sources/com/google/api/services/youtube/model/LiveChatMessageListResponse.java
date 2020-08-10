package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class LiveChatMessageListResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<LiveChatMessage> items;
    @Key
    private String kind;
    @Key
    private String nextPageToken;
    @Key
    private DateTime offlineAt;
    @Key
    private PageInfo pageInfo;
    @Key
    private Long pollingIntervalMillis;
    @Key
    private TokenPagination tokenPagination;
    @Key
    private String visitorId;

    static {
        Data.nullOf(LiveChatMessage.class);
    }

    public String getEtag() {
        return this.etag;
    }

    public LiveChatMessageListResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public LiveChatMessageListResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<LiveChatMessage> getItems() {
        return this.items;
    }

    public LiveChatMessageListResponse setItems(List<LiveChatMessage> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public LiveChatMessageListResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public LiveChatMessageListResponse setNextPageToken(String str) {
        this.nextPageToken = str;
        return this;
    }

    public DateTime getOfflineAt() {
        return this.offlineAt;
    }

    public LiveChatMessageListResponse setOfflineAt(DateTime dateTime) {
        this.offlineAt = dateTime;
        return this;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public LiveChatMessageListResponse setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
        return this;
    }

    public Long getPollingIntervalMillis() {
        return this.pollingIntervalMillis;
    }

    public LiveChatMessageListResponse setPollingIntervalMillis(Long l) {
        this.pollingIntervalMillis = l;
        return this;
    }

    public TokenPagination getTokenPagination() {
        return this.tokenPagination;
    }

    public LiveChatMessageListResponse setTokenPagination(TokenPagination tokenPagination2) {
        this.tokenPagination = tokenPagination2;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public LiveChatMessageListResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public LiveChatMessageListResponse set(String str, Object obj) {
        return (LiveChatMessageListResponse) super.set(str, obj);
    }

    public LiveChatMessageListResponse clone() {
        return (LiveChatMessageListResponse) super.clone();
    }
}
