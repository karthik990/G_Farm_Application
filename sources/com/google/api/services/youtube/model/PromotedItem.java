package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class PromotedItem extends GenericJson {
    @Key
    private String customMessage;
    @Key

    /* renamed from: id */
    private PromotedItemId f1853id;
    @Key
    private Boolean promotedByContentOwner;
    @Key
    private InvideoTiming timing;

    public String getCustomMessage() {
        return this.customMessage;
    }

    public PromotedItem setCustomMessage(String str) {
        this.customMessage = str;
        return this;
    }

    public PromotedItemId getId() {
        return this.f1853id;
    }

    public PromotedItem setId(PromotedItemId promotedItemId) {
        this.f1853id = promotedItemId;
        return this;
    }

    public Boolean getPromotedByContentOwner() {
        return this.promotedByContentOwner;
    }

    public PromotedItem setPromotedByContentOwner(Boolean bool) {
        this.promotedByContentOwner = bool;
        return this;
    }

    public InvideoTiming getTiming() {
        return this.timing;
    }

    public PromotedItem setTiming(InvideoTiming invideoTiming) {
        this.timing = invideoTiming;
        return this;
    }

    public PromotedItem set(String str, Object obj) {
        return (PromotedItem) super.set(str, obj);
    }

    public PromotedItem clone() {
        return (PromotedItem) super.clone();
    }
}
