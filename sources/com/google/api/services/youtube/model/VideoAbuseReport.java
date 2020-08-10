package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoAbuseReport extends GenericJson {
    @Key
    private String comments;
    @Key
    private String language;
    @Key
    private String reasonId;
    @Key
    private String secondaryReasonId;
    @Key
    private String videoId;

    public String getComments() {
        return this.comments;
    }

    public VideoAbuseReport setComments(String str) {
        this.comments = str;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public VideoAbuseReport setLanguage(String str) {
        this.language = str;
        return this;
    }

    public String getReasonId() {
        return this.reasonId;
    }

    public VideoAbuseReport setReasonId(String str) {
        this.reasonId = str;
        return this;
    }

    public String getSecondaryReasonId() {
        return this.secondaryReasonId;
    }

    public VideoAbuseReport setSecondaryReasonId(String str) {
        this.secondaryReasonId = str;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public VideoAbuseReport setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public VideoAbuseReport set(String str, Object obj) {
        return (VideoAbuseReport) super.set(str, obj);
    }

    public VideoAbuseReport clone() {
        return (VideoAbuseReport) super.clone();
    }
}
