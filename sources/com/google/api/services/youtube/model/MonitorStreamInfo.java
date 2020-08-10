package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class MonitorStreamInfo extends GenericJson {
    @Key
    private Long broadcastStreamDelayMs;
    @Key
    private String embedHtml;
    @Key
    private Boolean enableMonitorStream;

    public Long getBroadcastStreamDelayMs() {
        return this.broadcastStreamDelayMs;
    }

    public MonitorStreamInfo setBroadcastStreamDelayMs(Long l) {
        this.broadcastStreamDelayMs = l;
        return this;
    }

    public String getEmbedHtml() {
        return this.embedHtml;
    }

    public MonitorStreamInfo setEmbedHtml(String str) {
        this.embedHtml = str;
        return this;
    }

    public Boolean getEnableMonitorStream() {
        return this.enableMonitorStream;
    }

    public MonitorStreamInfo setEnableMonitorStream(Boolean bool) {
        this.enableMonitorStream = bool;
        return this;
    }

    public MonitorStreamInfo set(String str, Object obj) {
        return (MonitorStreamInfo) super.set(str, obj);
    }

    public MonitorStreamInfo clone() {
        return (MonitorStreamInfo) super.clone();
    }
}
