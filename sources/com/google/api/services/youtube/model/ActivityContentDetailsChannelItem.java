package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetailsChannelItem extends GenericJson {
    @Key
    private ResourceId resourceId;

    public ResourceId getResourceId() {
        return this.resourceId;
    }

    public ActivityContentDetailsChannelItem setResourceId(ResourceId resourceId2) {
        this.resourceId = resourceId2;
        return this;
    }

    public ActivityContentDetailsChannelItem set(String str, Object obj) {
        return (ActivityContentDetailsChannelItem) super.set(str, obj);
    }

    public ActivityContentDetailsChannelItem clone() {
        return (ActivityContentDetailsChannelItem) super.clone();
    }
}
