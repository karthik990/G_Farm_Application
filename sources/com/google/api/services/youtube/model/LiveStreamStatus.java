package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveStreamStatus extends GenericJson {
    @Key
    private LiveStreamHealthStatus healthStatus;
    @Key
    private String streamStatus;

    public LiveStreamHealthStatus getHealthStatus() {
        return this.healthStatus;
    }

    public LiveStreamStatus setHealthStatus(LiveStreamHealthStatus liveStreamHealthStatus) {
        this.healthStatus = liveStreamHealthStatus;
        return this;
    }

    public String getStreamStatus() {
        return this.streamStatus;
    }

    public LiveStreamStatus setStreamStatus(String str) {
        this.streamStatus = str;
        return this;
    }

    public LiveStreamStatus set(String str, Object obj) {
        return (LiveStreamStatus) super.set(str, obj);
    }

    public LiveStreamStatus clone() {
        return (LiveStreamStatus) super.clone();
    }
}
