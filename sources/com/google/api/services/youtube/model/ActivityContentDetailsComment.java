package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsComment extends GenericJson {
    @Key
    private ResourceId resourceId;

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public ActivityContentDetailsComment setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public ActivityContentDetailsComment set(String str, Object obj) {
        return (ActivityContentDetailsComment) super.set(str, obj);
    }

    public ActivityContentDetailsComment clone() {
        return (ActivityContentDetailsComment) super.clone();
    }
}
