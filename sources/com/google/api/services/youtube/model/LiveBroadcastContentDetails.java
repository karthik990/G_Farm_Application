package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Base64;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class LiveBroadcastContentDetails extends GenericJson {
    @Key
    private String boundStreamId;
    @Key
    private DateTime boundStreamLastUpdateTimeMs;
    @Key
    private String closedCaptionsType;
    @Key
    private Boolean enableAutoStart;
    @Key
    private Boolean enableClosedCaptions;
    @Key
    private Boolean enableContentEncryption;
    @Key
    private Boolean enableDvr;
    @Key
    private Boolean enableEmbed;
    @Key
    private Boolean enableLowLatency;
    @Key
    private String latencyPreference;
    @Key
    private String mesh;
    @Key
    private MonitorStreamInfo monitorStream;
    @Key
    private String projection;
    @Key
    private Boolean recordFromStart;
    @Key
    private Boolean startWithSlate;
    @Key
    private String stereoLayout;

    public String getBoundStreamId() {
        return this.boundStreamId;
    }

    public LiveBroadcastContentDetails setBoundStreamId(String str) {
        this.boundStreamId = str;
        return this;
    }

    public DateTime getBoundStreamLastUpdateTimeMs() {
        return this.boundStreamLastUpdateTimeMs;
    }

    public LiveBroadcastContentDetails setBoundStreamLastUpdateTimeMs(DateTime dateTime) {
        this.boundStreamLastUpdateTimeMs = dateTime;
        return this;
    }

    public String getClosedCaptionsType() {
        return this.closedCaptionsType;
    }

    public LiveBroadcastContentDetails setClosedCaptionsType(String str) {
        this.closedCaptionsType = str;
        return this;
    }

    public Boolean getEnableAutoStart() {
        return this.enableAutoStart;
    }

    public LiveBroadcastContentDetails setEnableAutoStart(Boolean bool) {
        this.enableAutoStart = bool;
        return this;
    }

    public Boolean getEnableClosedCaptions() {
        return this.enableClosedCaptions;
    }

    public LiveBroadcastContentDetails setEnableClosedCaptions(Boolean bool) {
        this.enableClosedCaptions = bool;
        return this;
    }

    public Boolean getEnableContentEncryption() {
        return this.enableContentEncryption;
    }

    public LiveBroadcastContentDetails setEnableContentEncryption(Boolean bool) {
        this.enableContentEncryption = bool;
        return this;
    }

    public Boolean getEnableDvr() {
        return this.enableDvr;
    }

    public LiveBroadcastContentDetails setEnableDvr(Boolean bool) {
        this.enableDvr = bool;
        return this;
    }

    public Boolean getEnableEmbed() {
        return this.enableEmbed;
    }

    public LiveBroadcastContentDetails setEnableEmbed(Boolean bool) {
        this.enableEmbed = bool;
        return this;
    }

    public Boolean getEnableLowLatency() {
        return this.enableLowLatency;
    }

    public LiveBroadcastContentDetails setEnableLowLatency(Boolean bool) {
        this.enableLowLatency = bool;
        return this;
    }

    public String getLatencyPreference() {
        return this.latencyPreference;
    }

    public LiveBroadcastContentDetails setLatencyPreference(String str) {
        this.latencyPreference = str;
        return this;
    }

    public String getMesh() {
        return this.mesh;
    }

    public byte[] decodeMesh() {
        return Base64.decodeBase64(this.mesh);
    }

    public LiveBroadcastContentDetails setMesh(String str) {
        this.mesh = str;
        return this;
    }

    public LiveBroadcastContentDetails encodeMesh(byte[] bArr) {
        this.mesh = Base64.encodeBase64URLSafeString(bArr);
        return this;
    }

    public MonitorStreamInfo getMonitorStream() {
        return this.monitorStream;
    }

    public LiveBroadcastContentDetails setMonitorStream(MonitorStreamInfo monitorStreamInfo) {
        this.monitorStream = monitorStreamInfo;
        return this;
    }

    public String getProjection() {
        return this.projection;
    }

    public LiveBroadcastContentDetails setProjection(String str) {
        this.projection = str;
        return this;
    }

    public Boolean getRecordFromStart() {
        return this.recordFromStart;
    }

    public LiveBroadcastContentDetails setRecordFromStart(Boolean bool) {
        this.recordFromStart = bool;
        return this;
    }

    public Boolean getStartWithSlate() {
        return this.startWithSlate;
    }

    public LiveBroadcastContentDetails setStartWithSlate(Boolean bool) {
        this.startWithSlate = bool;
        return this;
    }

    public String getStereoLayout() {
        return this.stereoLayout;
    }

    public LiveBroadcastContentDetails setStereoLayout(String str) {
        this.stereoLayout = str;
        return this;
    }

    public LiveBroadcastContentDetails set(String str, Object obj) {
        return (LiveBroadcastContentDetails) super.set(str, obj);
    }

    public LiveBroadcastContentDetails clone() {
        return (LiveBroadcastContentDetails) super.clone();
    }
}
