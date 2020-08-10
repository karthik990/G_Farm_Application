package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.math.BigInteger;
import java.util.List;

public final class LiveStreamHealthStatus extends GenericJson {
    @Key
    private List<LiveStreamConfigurationIssue> configurationIssues;
    @JsonString
    @Key
    private BigInteger lastUpdateTimeSeconds;
    @Key
    private String status;

    static {
        Data.nullOf(LiveStreamConfigurationIssue.class);
    }

    public List<LiveStreamConfigurationIssue> getConfigurationIssues() {
        return this.configurationIssues;
    }

    public LiveStreamHealthStatus setConfigurationIssues(List<LiveStreamConfigurationIssue> list) {
        this.configurationIssues = list;
        return this;
    }

    public BigInteger getLastUpdateTimeSeconds() {
        return this.lastUpdateTimeSeconds;
    }

    public LiveStreamHealthStatus setLastUpdateTimeSeconds(BigInteger bigInteger) {
        this.lastUpdateTimeSeconds = bigInteger;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public LiveStreamHealthStatus setStatus(String str) {
        this.status = str;
        return this;
    }

    public LiveStreamHealthStatus set(String str, Object obj) {
        return (LiveStreamHealthStatus) super.set(str, obj);
    }

    public LiveStreamHealthStatus clone() {
        return (LiveStreamHealthStatus) super.clone();
    }
}
