package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ThumbnailDetails extends GenericJson {
    @Key("default")
    private Thumbnail default__;
    @Key
    private Thumbnail high;
    @Key
    private Thumbnail maxres;
    @Key
    private Thumbnail medium;
    @Key
    private Thumbnail standard;

    public Thumbnail getDefault() {
        return this.default__;
    }

    public ThumbnailDetails setDefault(Thumbnail thumbnail) {
        this.default__ = thumbnail;
        return this;
    }

    public Thumbnail getHigh() {
        return this.high;
    }

    public ThumbnailDetails setHigh(Thumbnail thumbnail) {
        this.high = thumbnail;
        return this;
    }

    public Thumbnail getMaxres() {
        return this.maxres;
    }

    public ThumbnailDetails setMaxres(Thumbnail thumbnail) {
        this.maxres = thumbnail;
        return this;
    }

    public Thumbnail getMedium() {
        return this.medium;
    }

    public ThumbnailDetails setMedium(Thumbnail thumbnail) {
        this.medium = thumbnail;
        return this;
    }

    public Thumbnail getStandard() {
        return this.standard;
    }

    public ThumbnailDetails setStandard(Thumbnail thumbnail) {
        this.standard = thumbnail;
        return this;
    }

    public ThumbnailDetails set(String str, Object obj) {
        return (ThumbnailDetails) super.set(str, obj);
    }

    public ThumbnailDetails clone() {
        return (ThumbnailDetails) super.clone();
    }
}
