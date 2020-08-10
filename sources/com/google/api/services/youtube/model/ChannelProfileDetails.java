package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ChannelProfileDetails extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String channelUrl;
    @Key
    private String displayName;
    @Key
    private String profileImageUrl;

    public String getChannelId() {
        return this.channelId;
    }

    public ChannelProfileDetails setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelUrl() {
        return this.channelUrl;
    }

    public ChannelProfileDetails setChannelUrl(String str) {
        this.channelUrl = str;
        return this;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ChannelProfileDetails setDisplayName(String str) {
        this.displayName = str;
        return this;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public ChannelProfileDetails setProfileImageUrl(String str) {
        this.profileImageUrl = str;
        return this;
    }

    public ChannelProfileDetails set(String str, Object obj) {
        return (ChannelProfileDetails) super.set(str, obj);
    }

    public ChannelProfileDetails clone() {
        return (ChannelProfileDetails) super.clone();
    }
}
