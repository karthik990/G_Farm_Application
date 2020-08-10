package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class SubscriptionSubscriberSnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private String description;
    @Key
    private ThumbnailDetails thumbnails;
    @Key
    private String title;

    public String getChannelId() {
        return this.channelId;
    }

    public SubscriptionSubscriberSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public SubscriptionSubscriberSnippet setDescription(String str) {
        this.description = str;
        return this;
    }

    public ThumbnailDetails getThumbnails() {
        return this.thumbnails;
    }

    public SubscriptionSubscriberSnippet setThumbnails(ThumbnailDetails thumbnailDetails) {
        this.thumbnails = thumbnailDetails;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public SubscriptionSubscriberSnippet setTitle(String str) {
        this.title = str;
        return this;
    }

    public SubscriptionSubscriberSnippet set(String str, Object obj) {
        return (SubscriptionSubscriberSnippet) super.set(str, obj);
    }

    public SubscriptionSubscriberSnippet clone() {
        return (SubscriptionSubscriberSnippet) super.clone();
    }
}
