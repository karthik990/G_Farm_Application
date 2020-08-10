package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatModeratorSnippet extends GenericJson {
    @Key
    private String liveChatId;
    @Key
    private ChannelProfileDetails moderatorDetails;

    public String getLiveChatId() {
        return this.liveChatId;
    }

    public LiveChatModeratorSnippet setLiveChatId(String str) {
        this.liveChatId = str;
        return this;
    }

    public ChannelProfileDetails getModeratorDetails() {
        return this.moderatorDetails;
    }

    public LiveChatModeratorSnippet setModeratorDetails(ChannelProfileDetails channelProfileDetails) {
        this.moderatorDetails = channelProfileDetails;
        return this;
    }

    public LiveChatModeratorSnippet set(String str, Object obj) {
        return (LiveChatModeratorSnippet) super.set(str, obj);
    }

    public LiveChatModeratorSnippet clone() {
        return (LiveChatModeratorSnippet) super.clone();
    }
}
