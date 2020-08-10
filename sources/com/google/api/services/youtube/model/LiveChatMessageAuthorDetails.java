package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatMessageAuthorDetails extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String channelUrl;
    @Key
    private String displayName;
    @Key
    private Boolean isChatModerator;
    @Key
    private Boolean isChatOwner;
    @Key
    private Boolean isChatSponsor;
    @Key
    private Boolean isVerified;
    @Key
    private String profileImageUrl;

    public String getChannelId() {
        return this.channelId;
    }

    public LiveChatMessageAuthorDetails setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelUrl() {
        return this.channelUrl;
    }

    public LiveChatMessageAuthorDetails setChannelUrl(String str) {
        this.channelUrl = str;
        return this;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public LiveChatMessageAuthorDetails setDisplayName(String str) {
        this.displayName = str;
        return this;
    }

    public Boolean getIsChatModerator() {
        return this.isChatModerator;
    }

    public LiveChatMessageAuthorDetails setIsChatModerator(Boolean bool) {
        this.isChatModerator = bool;
        return this;
    }

    public Boolean getIsChatOwner() {
        return this.isChatOwner;
    }

    public LiveChatMessageAuthorDetails setIsChatOwner(Boolean bool) {
        this.isChatOwner = bool;
        return this;
    }

    public Boolean getIsChatSponsor() {
        return this.isChatSponsor;
    }

    public LiveChatMessageAuthorDetails setIsChatSponsor(Boolean bool) {
        this.isChatSponsor = bool;
        return this;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public LiveChatMessageAuthorDetails setIsVerified(Boolean bool) {
        this.isVerified = bool;
        return this;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public LiveChatMessageAuthorDetails setProfileImageUrl(String str) {
        this.profileImageUrl = str;
        return this;
    }

    public LiveChatMessageAuthorDetails set(String str, Object obj) {
        return (LiveChatMessageAuthorDetails) super.set(str, obj);
    }

    public LiveChatMessageAuthorDetails clone() {
        return (LiveChatMessageAuthorDetails) super.clone();
    }
}
