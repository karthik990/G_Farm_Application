package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Activity extends GenericJson {
    @Key
    private ActivityContentDetails contentDetails;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1833id;
    @Key
    private String kind;
    @Key
    private ActivitySnippet snippet;

    public ActivityContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public Activity setContentDetails(ActivityContentDetails activityContentDetails) {
        this.contentDetails = activityContentDetails;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Activity setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1833id;
    }

    public Activity setId(String str) {
        this.f1833id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Activity setKind(String str) {
        this.kind = str;
        return this;
    }

    public ActivitySnippet getSnippet() {
        return this.snippet;
    }

    public Activity setSnippet(ActivitySnippet activitySnippet) {
        this.snippet = activitySnippet;
        return this;
    }

    public Activity set(String str, Object obj) {
        return (Activity) super.set(str, obj);
    }

    public Activity clone() {
        return (Activity) super.clone();
    }
}
