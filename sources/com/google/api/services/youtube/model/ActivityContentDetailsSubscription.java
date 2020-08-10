package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsSubscription extends GenericJson {
    @Key
    private ResourceId resourceId;

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public ActivityContentDetailsSubscription setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public ActivityContentDetailsSubscription set(String str, Object obj) {
        return (ActivityContentDetailsSubscription) super.set(str, obj);
    }

    public ActivityContentDetailsSubscription clone() {
        return (ActivityContentDetailsSubscription) super.clone();
    }
}
