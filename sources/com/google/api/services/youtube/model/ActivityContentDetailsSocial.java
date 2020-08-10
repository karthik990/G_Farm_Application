package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsSocial extends GenericJson {
    @Key
    private String author;
    @Key
    private String imageUrl;
    @Key
    private String referenceUrl;
    @Key
    private ResourceId resourceId;
    @Key
    private String type;

    public String getAuthor() {
        return this.author;
    }

    public ActivityContentDetailsSocial setAuthor(String str) {
        this.author = str;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public ActivityContentDetailsSocial setImageUrl(String str) {
        this.imageUrl = str;
        return this;
    }

    public String getReferenceUrl() {
        return this.referenceUrl;
    }

    public ActivityContentDetailsSocial setReferenceUrl(String str) {
        this.referenceUrl = str;
        return this;
    }

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public ActivityContentDetailsSocial setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public ActivityContentDetailsSocial setType(String str) {
        this.type = str;
        return this;
    }

    public ActivityContentDetailsSocial set(String str, Object obj) {
        return (ActivityContentDetailsSocial) super.set(str, obj);
    }

    public ActivityContentDetailsSocial clone() {
        return (ActivityContentDetailsSocial) super.clone();
    }
}
