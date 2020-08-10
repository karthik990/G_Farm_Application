package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class ActivitySnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String channelTitle;
    @Key
    private String description;
    @Key
    private String groupId;
    @Key
    private DateTime publishedAt;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;
    @Key
    private String type;

    public String getChannelId() {
        return this.channelId;
    }

    public ActivitySnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public ActivitySnippet setChannelTitle(String str) {
        this.channelTitle = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public ActivitySnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public ActivitySnippet setGroupId(String str) {
        this.groupId = str;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public ActivitySnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public ActivitySnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ActivitySnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public ActivitySnippet setType(String str) {
        this.type = str;
        return this;
    }

    public ActivitySnippet set(String str, Object obj) {
        return (ActivitySnippet) super.set(str, obj);
    }

    public ActivitySnippet clone() {
        return (ActivitySnippet) super.clone();
    }
}
