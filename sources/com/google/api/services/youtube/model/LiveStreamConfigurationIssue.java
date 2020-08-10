package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveStreamConfigurationIssue extends GenericJson {
    @Key
    private String description;
    @Key
    private String reason;
    @Key
    private String severity;
    @Key
    private String type;

    public String getDescription() {
        return this.description;
    }

    public LiveStreamConfigurationIssue setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getReason() {
        return this.reason;
    }

    public LiveStreamConfigurationIssue setReason(String str) {
        this.reason = str;
        return this;
    }

    public String getSeverity() {
        return this.severity;
    }

    public LiveStreamConfigurationIssue setSeverity(String str) {
        this.severity = str;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public LiveStreamConfigurationIssue setType(String str) {
        this.type = str;
        return this;
    }

    public LiveStreamConfigurationIssue set(String str, Object obj) {
        return (LiveStreamConfigurationIssue) super.set(str, obj);
    }

    public LiveStreamConfigurationIssue clone() {
        return (LiveStreamConfigurationIssue) super.clone();
    }
}
