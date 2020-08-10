package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoSnippet extends GenericJson {
    @Key
    private String categoryId;
    @Key
    private String channelId;
    @Key
    private String channelTitle;
    @Key
    private String defaultAudioLanguage;
    @Key
    private String defaultLanguage;
    @Key
    private String description;
    @Key
    private String liveBroadcastContent;
    @Key
    private VideoLocalization localized;
    @Key
    private DateTime publishedAt;
    @Key
    private List<String> tags;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;

    public String getCategoryId() {
        return this.categoryId;
    }

    public VideoSnippet setCategoryId(String str) {
        this.categoryId = str;
        return this;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public VideoSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public VideoSnippet setChannelTitle(String str) {
        this.channelTitle = str;
        return this;
    }

    public String getDefaultAudioLanguage() {
        return this.defaultAudioLanguage;
    }

    public VideoSnippet setDefaultAudioLanguage(String str) {
        this.defaultAudioLanguage = str;
        return this;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public VideoSnippet setDefaultLanguage(String str) {
        this.defaultLanguage = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public VideoSnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getLiveBroadcastContent() {
        return this.liveBroadcastContent;
    }

    public VideoSnippet setLiveBroadcastContent(String str) {
        this.liveBroadcastContent = str;
        return this;
    }

    public VideoLocalization getLocalized() {
        return this.localized;
    }

    public VideoSnippet setLocalized(VideoLocalization videoLocalization) {
        this.localized = videoLocalization;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public VideoSnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public VideoSnippet setTags(List<String> list) {
        this.tags = list;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public VideoSnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public VideoSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public VideoSnippet set(String str, Object obj) {
        return (VideoSnippet) super.set(str, obj);
    }

    public VideoSnippet clone() {
        return (VideoSnippet) super.clone();
    }
}
