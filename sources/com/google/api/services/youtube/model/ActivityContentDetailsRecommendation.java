package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsRecommendation extends GenericJson {
    @Key
    private String reason;
    @Key
    private ResourceId resourceId;
    @Key
    private ResourceId seedResourceId;

    public String getReason() {
        return this.reason;
    }

    public ActivityContentDetailsRecommendation setReason(String str) {
        this.reason = str;
        return this;
    }

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public ActivityContentDetailsRecommendation setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public ResourceId getSeedResourceId() {
        return this.seedResourceId;
    }

    public ActivityContentDetailsRecommendation setSeedResourceId(ResourceId resourceId2) {
        this.seedResourceId = resourceId2;
        return this;
    }

    public ActivityContentDetailsRecommendation set(String str, Object obj) {
        return (ActivityContentDetailsRecommendation) super.set(str, obj);
    }

    public ActivityContentDetailsRecommendation clone() {
        return (ActivityContentDetailsRecommendation) super.clone();
    }
}
