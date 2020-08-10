package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Thumbnail extends GenericJson {
    @Key
    private Long height;
    @Key
    private String url;
    @Key
    private Long width;

    public Long getHeight() {
        return this.height;
    }

    public Thumbnail setHeight(Long l) {
        this.height = l;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public Thumbnail setUrl(String str) {
        this.url = str;
        return this;
    }

    public Long getWidth() {
        return this.width;
    }

    public Thumbnail setWidth(Long l) {
        this.width = l;
        return this;
    }

    public Thumbnail set(String str, Object obj) {
        return (Thumbnail) super.set(str, obj);
    }

    public Thumbnail clone() {
        return (Thumbnail) super.clone();
    }
}
