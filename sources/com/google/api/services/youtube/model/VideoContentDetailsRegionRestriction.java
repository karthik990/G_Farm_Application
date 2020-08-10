package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoContentDetailsRegionRestriction extends GenericJson {
    @Key
    private List<String> allowed;
    @Key
    private List<String> blocked;

    public List<String> getAllowed() {
        return this.allowed;
    }

    public VideoContentDetailsRegionRestriction setAllowed(List<String> list) {
        this.allowed = list;
        return this;
    }

    public List<String> getBlocked() {
        return this.blocked;
    }

    public VideoContentDetailsRegionRestriction setBlocked(List<String> list) {
        this.blocked = list;
        return this;
    }

    public VideoContentDetailsRegionRestriction set(String str, Object obj) {
        return (VideoContentDetailsRegionRestriction) super.set(str, obj);
    }

    public VideoContentDetailsRegionRestriction clone() {
        return (VideoContentDetailsRegionRestriction) super.clone();
    }
}
