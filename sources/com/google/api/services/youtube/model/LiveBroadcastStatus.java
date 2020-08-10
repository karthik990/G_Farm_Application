package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveBroadcastStatus extends GenericJson {
    @Key
    private String lifeCycleStatus;
    @Key
    private String liveBroadcastPriority;
    @Key
    private String privacyStatus;
    @Key
    private String recordingStatus;

    public String getLifeCycleStatus() {
        return this.lifeCycleStatus;
    }

    public LiveBroadcastStatus setLifeCycleStatus(String str) {
        this.lifeCycleStatus = str;
        return this;
    }

    public String getLiveBroadcastPriority() {
        return this.liveBroadcastPriority;
    }

    public LiveBroadcastStatus setLiveBroadcastPriority(String str) {
        this.liveBroadcastPriority = str;
        return this;
    }

    public String getPrivacyStatus() {
        return this.privacyStatus;
    }

    public LiveBroadcastStatus setPrivacyStatus(String str) {
        this.privacyStatus = str;
        return this;
    }

    public String getRecordingStatus() {
        return this.recordingStatus;
    }

    public LiveBroadcastStatus setRecordingStatus(String str) {
        this.recordingStatus = str;
        return this;
    }

    public LiveBroadcastStatus set(String str, Object obj) {
        return (LiveBroadcastStatus) super.set(str, obj);
    }

    public LiveBroadcastStatus clone() {
        return (LiveBroadcastStatus) super.clone();
    }
}
