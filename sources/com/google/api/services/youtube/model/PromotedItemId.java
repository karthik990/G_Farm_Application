package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class PromotedItemId extends GenericJson {
    @Key
    private String recentlyUploadedBy;
    @Key
    private String type;
    @Key
    private String videoId;
    @Key
    private String websiteUrl;

    public String getRecentlyUploadedBy() {
        return this.recentlyUploadedBy;
    }

    public PromotedItemId setRecentlyUploadedBy(String str) {
        this.recentlyUploadedBy = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public PromotedItemId setType(String str) {
        this.type = str;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public PromotedItemId setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public String getWebsiteUrl() {
        return this.websiteUrl;
    }

    public PromotedItemId setWebsiteUrl(String str) {
        this.websiteUrl = str;
        return this;
    }

    public PromotedItemId set(String str, Object obj) {
        return (PromotedItemId) super.set(str, obj);
    }

    public PromotedItemId clone() {
        return (PromotedItemId) super.clone();
    }
}
