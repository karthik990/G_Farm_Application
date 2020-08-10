package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ChannelSectionSnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String defaultLanguage;
    @Key
    private ChannelSectionLocalization localized;
    @Key
    private Long position;
    @Key
    private String style;
    @Key
    private String title;
    @Key
    private String type;

    public String getChannelId() {
        return this.channelId;
    }

    public ChannelSectionSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public ChannelSectionSnippet setDefaultLanguage(String str) {
        this.defaultLanguage = str;
        return this;
    }

    public ChannelSectionLocalization getLocalized() {
        return this.localized;
    }

    public ChannelSectionSnippet setLocalized(ChannelSectionLocalization channelSectionLocalization) {
        this.localized = channelSectionLocalization;
        return this;
    }

    public Long getPosition() {
        return this.position;
    }

    public ChannelSectionSnippet setPosition(Long l) {
        this.position = l;
        return this;
    }

    public String getStyle() {
        return this.style;
    }

    public ChannelSectionSnippet setStyle(String str) {
        this.style = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ChannelSectionSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public ChannelSectionSnippet setType(String str) {
        this.type = str;
        return this;
    }

    public ChannelSectionSnippet set(String str, Object obj) {
        return (ChannelSectionSnippet) super.set(str, obj);
    }

    public ChannelSectionSnippet clone() {
        return (ChannelSectionSnippet) super.clone();
    }
}
