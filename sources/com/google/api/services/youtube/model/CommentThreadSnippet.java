package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class CommentThreadSnippet extends GenericJson {
    @Key
    private Boolean canReply;
    @Key
    private String channelId;
    @Key
    private Boolean isPublic;
    @Key
    private Comment topLevelComment;
    @Key
    private Long totalReplyCount;
    @Key
    private String videoId;

    public Boolean getCanReply() {
        return this.canReply;
    }

    public CommentThreadSnippet setCanReply(Boolean bool) {
        this.canReply = bool;
        return this;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public CommentThreadSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public Boolean getIsPublic() {
        return this.isPublic;
    }

    public CommentThreadSnippet setIsPublic(Boolean bool) {
        this.isPublic = bool;
        return this;
    }

    public Comment getTopLevelComment() {
        return this.topLevelComment;
    }

    public CommentThreadSnippet setTopLevelComment(Comment comment) {
        this.topLevelComment = comment;
        return this;
    }

    public Long getTotalReplyCount() {
        return this.totalReplyCount;
    }

    public CommentThreadSnippet setTotalReplyCount(Long l) {
        this.totalReplyCount = l;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public CommentThreadSnippet setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public CommentThreadSnippet set(String str, Object obj) {
        return (CommentThreadSnippet) super.set(str, obj);
    }

    public CommentThreadSnippet clone() {
        return (CommentThreadSnippet) super.clone();
    }
}
