package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatPollItem extends GenericJson {
    @Key
    private String description;
    @Key
    private String itemId;

    public String getDescription() {
        return this.description;
    }

    public LiveChatPollItem setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getItemId() {
        return this.itemId;
    }

    public LiveChatPollItem setItemId(String str) {
        this.itemId = str;
        return this;
    }

    public LiveChatPollItem set(String str, Object obj) {
        return (LiveChatPollItem) super.set(str, obj);
    }

    public LiveChatPollItem clone() {
        return (LiveChatPollItem) super.clone();
    }
}
