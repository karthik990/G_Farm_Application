package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class InvideoTiming extends GenericJson {
    @JsonString
    @Key
    private BigInteger durationMs;
    @JsonString
    @Key
    private BigInteger offsetMs;
    @Key
    private String type;

    public BigInteger getDurationMs() {
        return this.durationMs;
    }

    public InvideoTiming setDurationMs(BigInteger bigInteger) {
        this.durationMs = bigInteger;
        return this;
    }

    public BigInteger getOffsetMs() {
        return this.offsetMs;
    }

    public InvideoTiming setOffsetMs(BigInteger bigInteger) {
        this.offsetMs = bigInteger;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public InvideoTiming setType(String str) {
        this.type = str;
        return this;
    }

    public InvideoTiming set(String str, Object obj) {
        return (InvideoTiming) super.set(str, obj);
    }

    public InvideoTiming clone() {
        return (InvideoTiming) super.clone();
    }
}
