package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class VideoLiveStreamingDetails extends GenericJson {
    @Key
    private String activeLiveChatId;
    @Key
    private DateTime actualEndTime;
    @Key
    private DateTime actualStartTime;
    @JsonString
    @Key
    private BigInteger concurrentViewers;
    @Key
    private DateTime scheduledEndTime;
    @Key
    private DateTime scheduledStartTime;

    public String getActiveLiveChatId() {
        return this.activeLiveChatId;
    }

    public VideoLiveStreamingDetails setActiveLiveChatId(String str) {
        this.activeLiveChatId = str;
        return this;
    }

    public DateTime getActualEndTime() {
        return this.actualEndTime;
    }

    public VideoLiveStreamingDetails setActualEndTime(DateTime dateTime) {
        this.actualEndTime = dateTime;
        return this;
    }

    public DateTime getActualStartTime() {
        return this.actualStartTime;
    }

    public VideoLiveStreamingDetails setActualStartTime(DateTime dateTime) {
        this.actualStartTime = dateTime;
        return this;
    }

    public BigInteger getConcurrentViewers() {
        return this.concurrentViewers;
    }

    public VideoLiveStreamingDetails setConcurrentViewers(BigInteger bigInteger) {
        this.concurrentViewers = bigInteger;
        return this;
    }

    public DateTime getScheduledEndTime() {
        return this.scheduledEndTime;
    }

    public VideoLiveStreamingDetails setScheduledEndTime(DateTime dateTime) {
        this.scheduledEndTime = dateTime;
        return this;
    }

    public DateTime getScheduledStartTime() {
        return this.scheduledStartTime;
    }

    public VideoLiveStreamingDetails setScheduledStartTime(DateTime dateTime) {
        this.scheduledStartTime = dateTime;
        return this;
    }

    public VideoLiveStreamingDetails set(String str, Object obj) {
        return (VideoLiveStreamingDetails) super.set(str, obj);
    }

    public VideoLiveStreamingDetails clone() {
        return (VideoLiveStreamingDetails) super.clone();
    }
}
