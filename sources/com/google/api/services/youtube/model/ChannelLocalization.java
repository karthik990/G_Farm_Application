package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ChannelLocalization extends GenericJson {
    @Key
    private String description;
    @Key
    private String title;

    public String getDescription() {
        return this.description;
    }

    public ChannelLocalization setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ChannelLocalization setTitle(String str) {
        this.title = str;
        return this;
    }

    public ChannelLocalization set(String str, Object obj) {
        return (ChannelLocalization) super.set(str, obj);
    }

    public ChannelLocalization clone() {
        return (ChannelLocalization) super.clone();
    }
}
