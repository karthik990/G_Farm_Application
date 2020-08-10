package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class SubscriptionSnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String channelTitle;
    @Key
    private String description;
    @Key
    private DateTime publishedAt;
    @Key
    private ResourceId resourceId;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;

    public String getChannelId() {
        return this.channelId;
    }

    public SubscriptionSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public SubscriptionSnippet setChannelTitle(String str) {
        this.channelTitle = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public SubscriptionSnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public SubscriptionSnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public SubscriptionSnippet setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public SubscriptionSnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public SubscriptionSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public SubscriptionSnippet set(String str, Object obj) {
        return (SubscriptionSnippet) super.set(str, obj);
    }

    public SubscriptionSnippet clone() {
        return (SubscriptionSnippet) super.clone();
    }
}
