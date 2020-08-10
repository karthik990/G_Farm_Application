package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class VideoStatus extends GenericJson {
    @Key
    private Boolean embeddable;
    @Key
    private String failureReason;
    @Key
    private String license;
    @Key
    private String privacyStatus;
    @Key
    private Boolean publicStatsViewable;
    @Key
    private DateTime publishAt;
    @Key
    private String rejectionReason;
    @Key
    private String uploadStatus;

    public Boolean getEmbeddable() {
        return this.embeddable;
    }

    public VideoStatus setEmbeddable(Boolean bool) {
        this.embeddable = bool;
        return this;
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public VideoStatus setFailureReason(String str) {
        this.failureReason = str;
        return this;
    }

    public String getLicense() {
        return this.license;
    }

    public VideoStatus setLicense(String str) {
        this.license = str;
        return this;
    }

    public String getPrivacyStatus() {
        return this.privacyStatus;
    }

    public VideoStatus setPrivacyStatus(String str) {
        this.privacyStatus = str;
        return this;
    }

    public Boolean getPublicStatsViewable() {
        return this.publicStatsViewable;
    }

    public VideoStatus setPublicStatsViewable(Boolean bool) {
        this.publicStatsViewable = bool;
        return this;
    }

    public DateTime getPublishAt() {
        return this.publishAt;
    }

    public VideoStatus setPublishAt(DateTime dateTime) {
        this.publishAt = dateTime;
        return this;
    }

    public String getRejectionReason() {
        return this.rejectionReason;
    }

    public VideoStatus setRejectionReason(String str) {
        this.rejectionReason = str;
        return this;
    }

    public String getUploadStatus() {
        return this.uploadStatus;
    }

    public VideoStatus setUploadStatus(String str) {
        this.uploadStatus = str;
        return this;
    }

    public VideoStatus set(String str, Object obj) {
        return (VideoStatus) super.set(str, obj);
    }

    public VideoStatus clone() {
        return (VideoStatus) super.clone();
    }
}
