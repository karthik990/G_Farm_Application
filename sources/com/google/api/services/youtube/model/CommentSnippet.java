package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class CommentSnippet extends GenericJson {
    @Key
    private Object authorChannelId;
    @Key
    private String authorChannelUrl;
    @Key
    private String authorDisplayName;
    @Key
    private String authorProfileImageUrl;
    @Key
    private Boolean canRate;
    @Key
    private String channelId;
    @Key
    private Long likeCount;
    @Key
    private String moderationStatus;
    @Key
    private String parentId;
    @Key
    private DateTime publishedAt;
    @Key
    private String textDisplay;
    @Key
    private String textOriginal;
    @Key
    private DateTime updatedAt;
    @Key
    private String videoId;
    @Key
    private String viewerRating;

    public Object getAuthorChannelId() {
        return this.authorChannelId;
    }

    public CommentSnippet setAuthorChannelId(Object obj) {
        this.authorChannelId = obj;
        return this;
    }

    public String getAuthorChannelUrl() {
        return this.authorChannelUrl;
    }

    public CommentSnippet setAuthorChannelUrl(String str) {
        this.authorChannelUrl = str;
        return this;
    }

    public String getAuthorDisplayName() {
        return this.authorDisplayName;
    }

    public CommentSnippet setAuthorDisplayName(String str) {
        this.authorDisplayName = str;
        return this;
    }

    public String getAuthorProfileImageUrl() {
        return this.authorProfileImageUrl;
    }

    public CommentSnippet setAuthorProfileImageUrl(String str) {
        this.authorProfileImageUrl = str;
        return this;
    }

    public Boolean getCanRate() {
        return this.canRate;
    }

    public CommentSnippet setCanRate(Boolean bool) {
        this.canRate = bool;
        return this;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public CommentSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public Long getLikeCount() {
        return this.likeCount;
    }

    public CommentSnippet setLikeCount(Long l) {
        this.likeCount = l;
        return this;
    }

    public String getModerationStatus() {
        return this.moderationStatus;
    }

    public CommentSnippet setModerationStatus(String str) {
        this.moderationStatus = str;
        return this;
    }

    public String getParentId() {
        return this.parentId;
    }

    public CommentSnippet setParentId(String str) {
        this.parentId = str;
        return this;
    }

    public DateTime getPublishedAt() {
        return this.publishedAt;
    }

    public CommentSnippet setPublishedAt(DateTime dateTime) {
        this.publishedAt = dateTime;
        return this;
    }

    public String getTextDisplay() {
        return this.textDisplay;
    }

    public CommentSnippet setTextDisplay(String str) {
        this.textDisplay = str;
        return this;
    }

    public String getTextOriginal() {
        return this.textOriginal;
    }

    public CommentSnippet setTextOriginal(String str) {
        this.textOriginal = str;
        return this;
    }

    public DateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public CommentSnippet setUpdatedAt(DateTime dateTime) {
        this.updatedAt = dateTime;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public CommentSnippet setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public String getViewerRating() {
        return this.viewerRating;
    }

    public CommentSnippet setViewerRating(String str) {
        this.viewerRating = str;
        return this;
    }

    public CommentSnippet set(String str, Object obj) {
        return (CommentSnippet) super.set(str, obj);
    }

    public CommentSnippet clone() {
        return (CommentSnippet) super.clone();
    }
}
