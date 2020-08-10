package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class Snippet implements Serializable {
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("channelTitle")
    @Expose
    private String channelTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("liveBroadcastContent")
    @Expose
    private String liveBroadcastContent;
    @SerializedName("localized")
    @Expose
    private Localized localized;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
    @SerializedName("title")
    @Expose
    private String title;

    public Localized getLocalized() {
        return this.localized;
    }

    public void setLocalized(Localized localized2) {
        this.localized = localized2;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> list) {
        this.tags = list;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String str) {
        this.publishedAt = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Thumbnails getThumbnails() {
        return this.thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails2) {
        this.thumbnails = thumbnails2;
    }

    public String getChannelTitle() {
        return this.channelTitle;
    }

    public void setChannelTitle(String str) {
        this.channelTitle = str;
    }

    public String getLiveBroadcastContent() {
        return this.liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String str) {
        this.liveBroadcastContent = str;
    }
}
