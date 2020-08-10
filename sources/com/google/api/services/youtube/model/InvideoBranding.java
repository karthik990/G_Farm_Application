package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Key;

public final class InvideoBranding extends GenericJson {
    @Key
    private String imageBytes;
    @Key
    private String imageUrl;
    @Key
    private InvideoPosition position;
    @Key
    private String targetChannelId;
    @Key
    private InvideoTiming timing;

    public String getImageBytes() {
        return this.imageBytes;
    }

    public byte[] decodeImageBytes() {
        return Base64.decodeBase64(this.imageBytes);
    }

    public InvideoBranding setImageBytes(String str) {
        this.imageBytes = str;
        return this;
    }

    public InvideoBranding encodeImageBytes(byte[] bArr) {
        this.imageBytes = Base64.encodeBase64URLSafeString(bArr);
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public InvideoBranding setImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public InvideoPosition getPosition() {
        return this.position;
    }

    public InvideoBranding setPosition(InvideoPosition invideoPosition) {
        this.position = invideoPosition;
        return this;
    }

    public String getTargetChannelId() {
        return this.targetChannelId;
    }

    public InvideoBranding setTargetChannelId(String str) {
        this.targetChannelId = str;
        return this;
    }

    public InvideoTiming getTiming() {
        return this.timing;
    }

    public InvideoBranding setTiming(InvideoTiming invideoTiming) {
        this.timing = invideoTiming;
        return this;
    }

    public InvideoBranding set(String str, Object obj) {
        return (InvideoBranding) super.set(str, obj);
    }

    public InvideoBranding clone() {
        return (InvideoBranding) super.clone();
    }
}
