package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class SuperStickerMetadata extends GenericJson {
    @Key
    private String altText;
    @Key
    private String altTextLanguage;
    @Key
    private String stickerId;

    public String getAltText() {
        return this.altText;
    }

    public SuperStickerMetadata setAltText(String str) {
        this.altText = str;
        return this;
    }

    public String getAltTextLanguage() {
        return this.altTextLanguage;
    }

    public SuperStickerMetadata setAltTextLanguage(String str) {
        this.altTextLanguage = str;
        return this;
    }

    public String getStickerId() {
        return this.stickerId;
    }

    public SuperStickerMetadata setStickerId(String str) {
        this.stickerId = str;
        return this;
    }

    public SuperStickerMetadata set(String str, Object obj) {
        return (SuperStickerMetadata) super.set(str, obj);
    }

    public SuperStickerMetadata clone() {
        return (SuperStickerMetadata) super.clone();
    }
}
