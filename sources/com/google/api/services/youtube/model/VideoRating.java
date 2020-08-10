package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoRating extends GenericJson {
    @Key
    private String rating;
    @Key
    private String videoId;

    public String getRating() {
        return this.rating;
    }

    public VideoRating setRating(String str) {
        this.rating = str;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public VideoRating setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public VideoRating set(String str, Object obj) {
        return (VideoRating) super.set(str, obj);
    }

    public VideoRating clone() {
        return (VideoRating) super.clone();
    }
}
