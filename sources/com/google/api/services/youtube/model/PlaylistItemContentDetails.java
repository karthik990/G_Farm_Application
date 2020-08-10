package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class PlaylistItemContentDetails extends GenericJson {
    @Key
    private String endAt;
    @Key
    private String note;
    @Key
    private String startAt;
    @Key
    private String videoId;
    @Key
    private DateTime videoPublishedAt;

    public String getEndAt() {
        return this.endAt;
    }

    public PlaylistItemContentDetails setEndAt(String str) {
        this.endAt = str;
        return this;
    }

    public String getNote() {
        return this.note;
    }

    public PlaylistItemContentDetails setNote(String str) {
        this.note = str;
        return this;
    }

    public String getStartAt() {
        return this.startAt;
    }

    public PlaylistItemContentDetails setStartAt(String str) {
        this.startAt = str;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public PlaylistItemContentDetails setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public DateTime getVideoPublishedAt() {
        return this.videoPublishedAt;
    }

    public PlaylistItemContentDetails setVideoPublishedAt(DateTime dateTime) {
        this.videoPublishedAt = dateTime;
        return this;
    }

    public PlaylistItemContentDetails set(String str, Object obj) {
        return (PlaylistItemContentDetails) super.set(str, obj);
    }

    public PlaylistItemContentDetails clone() {
        return (PlaylistItemContentDetails) super.clone();
    }
}
