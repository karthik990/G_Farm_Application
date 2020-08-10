package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveStreamContentDetails extends GenericJson {
    @Key
    private String closedCaptionsIngestionUrl;
    @Key
    private Boolean isReusable;

    public String getClosedCaptionsIngestionUrl() {
        return this.closedCaptionsIngestionUrl;
    }

    public LiveStreamContentDetails setClosedCaptionsIngestionUrl(String str) {
        this.closedCaptionsIngestionUrl = str;
        return this;
    }

    public Boolean getIsReusable() {
        return this.isReusable;
    }

    public LiveStreamContentDetails setIsReusable(Boolean bool) {
        this.isReusable = bool;
        return this;
    }

    public LiveStreamContentDetails set(String str, Object obj) {
        return (LiveStreamContentDetails) super.set(str, obj);
    }

    public LiveStreamContentDetails clone() {
        return (LiveStreamContentDetails) super.clone();
    }
}
