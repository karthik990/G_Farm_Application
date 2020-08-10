package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoGetRatingResponse extends GenericJson {
    @Key
    private String etag;
    @Key
    private String eventId;
    @Key
    private List<VideoRating> items;
    @Key
    private String kind;
    @Key
    private String visitorId;

    public String getEtag() {
        return this.etag;
    }

    public VideoGetRatingResponse setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getEventId() {
        return this.eventId;
    }

    public VideoGetRatingResponse setEventId(String str) {
        this.eventId = str;
        return this;
    }

    public List<VideoRating> getItems() {
        return this.items;
    }

    public VideoGetRatingResponse setItems(List<VideoRating> list) {
        this.items = list;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public VideoGetRatingResponse setKind(String str) {
        this.kind = str;
        return this;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public VideoGetRatingResponse setVisitorId(String str) {
        this.visitorId = str;
        return this;
    }

    public VideoGetRatingResponse set(String str, Object obj) {
        return (VideoGetRatingResponse) super.set(str, obj);
    }

    public VideoGetRatingResponse clone() {
        return (VideoGetRatingResponse) super.clone();
    }
}
