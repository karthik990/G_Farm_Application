package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class InvideoPosition extends GenericJson {
    @Key
    private String cornerPosition;
    @Key
    private String type;

    public String getCornerPosition() {
        return this.cornerPosition;
    }

    public InvideoPosition setCornerPosition(String str) {
        this.cornerPosition = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public InvideoPosition setType(String str) {
        this.type = str;
        return this;
    }

    public InvideoPosition set(String str, Object obj) {
        return (InvideoPosition) super.set(str, obj);
    }

    public InvideoPosition clone() {
        return (InvideoPosition) super.clone();
    }
}
