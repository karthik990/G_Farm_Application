package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class PlaylistSnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String channelTitle;
    @Key
    private String defaultLanguage;
    @Key
    private String description;
    @Key
    private PlaylistLocalization localized;
    @Key
    private DateTime publishedAt;
    @Key
    private List<String> tags;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;

    public String getChannelId() {
        return this.channelId;
    }

    public PlaylistSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public PlaylistSnippet setChannelTitle(String str) {
        this.channelTitle = str;
        return this;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public PlaylistSnippet setDefaultLanguage(String str) {
        this.defaultLanguage = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public PlaylistSnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public PlaylistLocalization getLocalized() {
        return this.localized;
    }

    public PlaylistSnippet setLocalized(PlaylistLocalization playlistLocalization) {
        this.localized = playlistLocalization;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public PlaylistSnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public PlaylistSnippet setTags(List<String> list) {
        this.tags = list;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public PlaylistSnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public PlaylistSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public PlaylistSnippet set(String str, Object obj) {
        return (PlaylistSnippet) super.set(str, obj);
    }

    public PlaylistSnippet clone() {
        return (PlaylistSnippet) super.clone();
    }
}
