package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class ChannelSnippet extends GenericJson {
    @Key
    private String country;
    @Key
    private String customUrl;
    @Key
    private String defaultLanguage;
    @Key
    private String description;
    @Key
    private ChannelLocalization localized;
    @Key
    private DateTime publishedAt;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;

    public String getCountry() {
        return this.country;
    }

    public ChannelSnippet setCountry(String str) {
        this.country = str;
        return this;
    }

    public String getCustomUrl() {
        return this.customUrl;
    }

    public ChannelSnippet setCustomUrl(String str) {
        this.customUrl = str;
        return this;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public ChannelSnippet setDefaultLanguage(String str) {
        this.defaultLanguage = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public ChannelSnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public ChannelLocalization getLocalized() {
        return this.localized;
    }

    public ChannelSnippet setLocalized(ChannelLocalization channelLocalization) {
        this.localized = channelLocalization;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public ChannelSnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public ChannelSnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ChannelSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public ChannelSnippet set(String str, Object obj) {
        return (ChannelSnippet) super.set(str, obj);
    }

    public ChannelSnippet clone() {
        return (ChannelSnippet) super.clone();
    }
}
