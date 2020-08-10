package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsUpload extends GenericJson {
    @Key
    private String videoId;

    public String getVideoId() {
        return this.videoId;
    }

    public ActivityContentDetailsUpload setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public ActivityContentDetailsUpload set(String str, Object obj) {
        return (ActivityContentDetailsUpload) super.set(str, obj);
    }

    public ActivityContentDetailsUpload clone() {
        return (ActivityContentDetailsUpload) super.clone();
    }
}
