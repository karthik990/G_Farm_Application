package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoLocalization extends GenericJson {
    @Key
    private String description;
    @Key
    private String title;

    public String getDescription() {
        return this.description;
    }

    public VideoLocalization setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public VideoLocalization setTitle(String str) {
        this.title = str;
        return this;
    }

    public VideoLocalization set(String str, Object obj) {
        return (VideoLocalization) super.set(str, obj);
    }

    public VideoLocalization clone() {
        return (VideoLocalization) super.clone();
    }
}
