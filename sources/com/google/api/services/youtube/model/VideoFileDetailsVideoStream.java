package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class VideoFileDetailsVideoStream extends GenericJson {
    @Key
    private Double aspectRatio;
    @JsonString
    @Key
    private BigInteger bitrateBps;
    @Key
    private String codec;
    @Key
    private Double frameRateFps;
    @Key
    private Long heightPixels;
    @Key
    private String rotation;
    @Key
    private String vendor;
    @Key
    private Long widthPixels;

    public Double getAspectRatio() {
        return this.aspectRatio;
    }

    public VideoFileDetailsVideoStream setAspectRatio(Double d) {
        this.aspectRatio = d;
        return this;
    }

    public BigInteger getBitrateBps() {
        return this.bitrateBps;
    }

    public VideoFileDetailsVideoStream setBitrateBps(BigInteger bigInteger) {
        this.bitrateBps = bigInteger;
        return this;
    }

    public String getCodec() {
        return this.codec;
    }

    public VideoFileDetailsVideoStream setCodec(String str) {
        this.codec = str;
        return this;
    }

    public Double getFrameRateFps() {
        return this.frameRateFps;
    }

    public VideoFileDetailsVideoStream setFrameRateFps(Double d) {
        this.frameRateFps = d;
        return this;
    }

    public Long getHeightPixels() {
        return this.heightPixels;
    }

    public VideoFileDetailsVideoStream setHeightPixels(Long l) {
        this.heightPixels = l;
        return this;
    }

    public String getRotation() {
        return this.rotation;
    }

    public VideoFileDetailsVideoStream setRotation(String str) {
        this.rotation = str;
        return this;
    }

    public String getVendor() {
        return this.vendor;
    }

    public VideoFileDetailsVideoStream setVendor(String str) {
        this.vendor = str;
        return this;
    }

    public Long getWidthPixels() {
        return this.widthPixels;
    }

    public VideoFileDetailsVideoStream setWidthPixels(Long l) {
        this.widthPixels = l;
        return this;
    }

    public VideoFileDetailsVideoStream set(String str, Object obj) {
        return (VideoFileDetailsVideoStream) super.set(str, obj);
    }

    public VideoFileDetailsVideoStream clone() {
        return (VideoFileDetailsVideoStream) super.clone();
    }
}
