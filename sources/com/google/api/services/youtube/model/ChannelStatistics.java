package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class ChannelStatistics extends GenericJson {
    @JsonString
    @Key
    private BigInteger commentCount;
    @Key
    private Boolean hiddenSubscriberCount;
    @JsonString
    @Key
    private BigInteger subscriberCount;
    @JsonString
    @Key
    private BigInteger videoCount;
    @JsonString
    @Key
    private BigInteger viewCount;

    public BigInteger getCommentCount() {
        return this.commentCount;
    }

    public ChannelStatistics setCommentCount(BigInteger bigInteger) {
        this.commentCount = bigInteger;
        return this;
    }

    public Boolean getHiddenSubscriberCount() {
        return this.hiddenSubscriberCount;
    }

    public ChannelStatistics setHiddenSubscriberCount(Boolean bool) {
        this.hiddenSubscriberCount = bool;
        return this;
    }

    public BigInteger getSubscriberCount() {
        return this.subscriberCount;
    }

    public ChannelStatistics setSubscriberCount(BigInteger bigInteger) {
        this.subscriberCount = bigInteger;
        return this;
    }

    public BigInteger getVideoCount() {
        return this.videoCount;
    }

    public ChannelStatistics setVideoCount(BigInteger bigInteger) {
        this.videoCount = bigInteger;
        return this;
    }

    public BigInteger getViewCount() {
        return this.viewCount;
    }

    public ChannelStatistics setViewCount(BigInteger bigInteger) {
        this.viewCount = bigInteger;
        return this;
    }

    public ChannelStatistics set(String str, Object obj) {
        return (ChannelStatistics) super.set(str, obj);
    }

    public ChannelStatistics clone() {
        return (ChannelStatistics) super.clone();
    }
}
