package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;

public final class VideoPlayer extends GenericJson {
    @JsonString
    @Key
    private Long embedHeight;
    @Key
    private String embedHtml;
    @JsonString
    @Key
    private Long embedWidth;

    public Long getEmbedHeight() {
        return this.embedHeight;
    }

    public VideoPlayer setEmbedHeight(Long l) {
        this.embedHeight = l;
        return this;
    }

    public String getEmbedHtml() {
        return this.embedHtml;
    }

    public VideoPlayer setEmbedHtml(String str) {
        this.embedHtml = str;
        return this;
    }

    public Long getEmbedWidth() {
        return this.embedWidth;
    }

    public VideoPlayer setEmbedWidth(Long l) {
        this.embedWidth = l;
        return this;
    }

    public VideoPlayer set(String str, Object obj) {
        return (VideoPlayer) super.set(str, obj);
    }

    public VideoPlayer clone() {
        return (VideoPlayer) super.clone();
    }
}
