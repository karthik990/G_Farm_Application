package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class LiveChatBanSnippet extends GenericJson {
    @JsonString
    @Key
    private BigInteger banDurationSeconds;
    @Key
    private ChannelProfileDetails bannedUserDetails;
    @Key
    private String liveChatId;
    @Key
    private String type;

    public BigInteger getBanDurationSeconds() {
        return this.banDurationSeconds;
    }

    public LiveChatBanSnippet setBanDurationSeconds(BigInteger bigInteger) {
        this.banDurationSeconds = bigInteger;
        return this;
    }

    public ChannelProfileDetails getBannedUserDetails() {
        return this.bannedUserDetails;
    }

    public LiveChatBanSnippet setBannedUserDetails(ChannelProfileDetails channelProfileDetails) {
        this.bannedUserDetails = channelProfileDetails;
        return this;
    }

    public String getLiveChatId() {
        return this.liveChatId;
    }

    public LiveChatBanSnippet setLiveChatId(String str) {
        this.liveChatId = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public LiveChatBanSnippet setType(String str) {
        this.type = str;
        return this;
    }

    public LiveChatBanSnippet set(String str, Object obj) {
        return (LiveChatBanSnippet) super.set(str, obj);
    }

    public LiveChatBanSnippet clone() {
        return (LiveChatBanSnippet) super.clone();
    }
}
