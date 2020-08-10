package com.mobiroller.models.youtube;

import com.google.api.services.youtube.model.Video;
import java.io.Serializable;
import java.math.BigInteger;

public class YoutubeVideoDetailModel implements Serializable {
    String channelId;
    String channelImageUrl;
    String channelName;
    BigInteger channelSubscriberCount;
    BigInteger commentCount;
    BigInteger dislikeCount;
    BigInteger likeCount;
    String videoDescription;
    String videoId;
    String videoTitle;
    BigInteger videoViewCount;

    public String getVideoTitle() {
        return this.videoTitle;
    }

    public BigInteger getVideoViewCount() {
        return this.videoViewCount;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public BigInteger getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(BigInteger bigInteger) {
        this.likeCount = bigInteger;
    }

    public void setDislikeCount(BigInteger bigInteger) {
        this.dislikeCount = bigInteger;
    }

    public BigInteger getDislikeCount() {
        return this.dislikeCount;
    }

    public BigInteger getCommentCount() {
        return this.commentCount;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getChannelImageUrl() {
        return this.channelImageUrl;
    }

    public BigInteger getChannelSubscriberCount() {
        return this.channelSubscriberCount;
    }

    public String getVideoDescription() {
        return this.videoDescription;
    }

    public void setVideo(Video video) {
        this.videoId = video.getId();
        this.videoDescription = video.getSnippet().getDescription();
        this.videoTitle = video.getSnippet().getTitle();
        this.channelName = video.getSnippet().getChannelTitle();
        this.channelId = video.getSnippet().getChannelId();
        this.videoViewCount = video.getStatistics().getViewCount();
        this.likeCount = video.getStatistics().getLikeCount();
        this.dislikeCount = video.getStatistics().getDislikeCount();
        this.commentCount = video.getStatistics().getCommentCount();
    }

    public void setChannelImageUrl(String str) {
        this.channelImageUrl = str;
    }

    public void setChannelSubscriberCount(BigInteger bigInteger) {
        this.channelSubscriberCount = bigInteger;
    }
}
