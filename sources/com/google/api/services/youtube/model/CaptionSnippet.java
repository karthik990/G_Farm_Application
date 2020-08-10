package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class CaptionSnippet extends GenericJson {
    @Key
    private String audioTrackType;
    @Key
    private String failureReason;
    @Key
    private Boolean isAutoSynced;
    @Key
    private Boolean isCC;
    @Key
    private Boolean isDraft;
    @Key
    private Boolean isEasyReader;
    @Key
    private Boolean isLarge;
    @Key
    private String language;
    @Key
    private DateTime lastUpdated;
    @Key
    private String name;
    @Key
    private String status;
    @Key
    private String trackKind;
    @Key
    private String videoId;

    public String getAudioTrackType() {
        return this.audioTrackType;
    }

    public CaptionSnippet setAudioTrackType(String str) {
        this.audioTrackType = str;
        return this;
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public CaptionSnippet setFailureReason(String str) {
        this.failureReason = str;
        return this;
    }

    public Boolean getIsAutoSynced() {
        return this.isAutoSynced;
    }

    public CaptionSnippet setIsAutoSynced(Boolean bool) {
        this.isAutoSynced = bool;
        return this;
    }

    public Boolean getIsCC() {
        return this.isCC;
    }

    public CaptionSnippet setIsCC(Boolean bool) {
        this.isCC = bool;
        return this;
    }

    public Boolean getIsDraft() {
        return this.isDraft;
    }

    public CaptionSnippet setIsDraft(Boolean bool) {
        this.isDraft = bool;
        return this;
    }

    public Boolean getIsEasyReader() {
        return this.isEasyReader;
    }

    public CaptionSnippet setIsEasyReader(Boolean bool) {
        this.isEasyReader = bool;
        return this;
    }

    public Boolean getIsLarge() {
        return this.isLarge;
    }

    public CaptionSnippet setIsLarge(Boolean bool) {
        this.isLarge = bool;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public CaptionSnippet setLanguage(String str) {
        this.language = str;
        return this;
    }

    public DateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public CaptionSnippet setLastUpdated(DateTime dateTime) {
        this.lastUpdated = dateTime;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public CaptionSnippet setName(String str) {
        this.name = str;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public CaptionSnippet setStatus(String str) {
        this.status = str;
        return this;
    }

    public String getTrackKind() {
        return this.trackKind;
    }

    public CaptionSnippet setTrackKind(String str) {
        this.trackKind = str;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public CaptionSnippet setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public CaptionSnippet set(String str, Object obj) {
        return (CaptionSnippet) super.set(str, obj);
    }

    public CaptionSnippet clone() {
        return (CaptionSnippet) super.clone();
    }
}
