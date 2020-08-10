package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoProcessingDetails extends GenericJson {
    @Key
    private String editorSuggestionsAvailability;
    @Key
    private String fileDetailsAvailability;
    @Key
    private String processingFailureReason;
    @Key
    private String processingIssuesAvailability;
    @Key
    private VideoProcessingDetailsProcessingProgress processingProgress;
    @Key
    private String processingStatus;
    @Key
    private String tagSuggestionsAvailability;
    @Key
    private String thumbnailsAvailability;

    public String getEditorSuggestionsAvailability() {
        return this.editorSuggestionsAvailability;
    }

    public VideoProcessingDetails setEditorSuggestionsAvailability(String str) {
        this.editorSuggestionsAvailability = str;
        return this;
    }

    public String getFileDetailsAvailability() {
        return this.fileDetailsAvailability;
    }

    public VideoProcessingDetails setFileDetailsAvailability(String str) {
        this.fileDetailsAvailability = str;
        return this;
    }

    public String getProcessingFailureReason() {
        return this.processingFailureReason;
    }

    public VideoProcessingDetails setProcessingFailureReason(String str) {
        this.processingFailureReason = str;
        return this;
    }

    public String getProcessingIssuesAvailability() {
        return this.processingIssuesAvailability;
    }

    public VideoProcessingDetails setProcessingIssuesAvailability(String str) {
        this.processingIssuesAvailability = str;
        return this;
    }

    public VideoProcessingDetailsProcessingProgress getProcessingProgress() {
        return this.processingProgress;
    }

    public VideoProcessingDetails setProcessingProgress(VideoProcessingDetailsProcessingProgress videoProcessingDetailsProcessingProgress) {
        this.processingProgress = videoProcessingDetailsProcessingProgress;
        return this;
    }

    public String getProcessingStatus() {
        return this.processingStatus;
    }

    public VideoProcessingDetails setProcessingStatus(String str) {
        this.processingStatus = str;
        return this;
    }

    public String getTagSuggestionsAvailability() {
        return this.tagSuggestionsAvailability;
    }

    public VideoProcessingDetails setTagSuggestionsAvailability(String str) {
        this.tagSuggestionsAvailability = str;
        return this;
    }

    public String getThumbnailsAvailability() {
        return this.thumbnailsAvailability;
    }

    public VideoProcessingDetails setThumbnailsAvailability(String str) {
        this.thumbnailsAvailability = str;
        return this;
    }

    public VideoProcessingDetails set(String str, Object obj) {
        return (VideoProcessingDetails) super.set(str, obj);
    }

    public VideoProcessingDetails clone() {
        return (VideoProcessingDetails) super.clone();
    }
}
