package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class CdnSettings extends GenericJson {
    @Key
    private String format;
    @Key
    private String frameRate;
    @Key
    private IngestionInfo ingestionInfo;
    @Key
    private String ingestionType;
    @Key
    private String resolution;

    public String getFormat() {
        return this.format;
    }

    public CdnSettings setFormat(String str) {
        this.format = str;
        return this;
    }

    public String getFrameRate() {
        return this.frameRate;
    }

    public CdnSettings setFrameRate(String str) {
        this.frameRate = str;
        return this;
    }

    public IngestionInfo getIngestionInfo() {
        return this.ingestionInfo;
    }

    public CdnSettings setIngestionInfo(IngestionInfo ingestionInfo2) {
        this.ingestionInfo = ingestionInfo2;
        return this;
    }

    public String getIngestionType() {
        return this.ingestionType;
    }

    public CdnSettings setIngestionType(String str) {
        this.ingestionType = str;
        return this;
    }

    public String getResolution() {
        return this.resolution;
    }

    public CdnSettings setResolution(String str) {
        this.resolution = str;
        return this;
    }

    public CdnSettings set(String str, Object obj) {
        return (CdnSettings) super.set(str, obj);
    }

    public CdnSettings clone() {
        return (CdnSettings) super.clone();
    }
}
