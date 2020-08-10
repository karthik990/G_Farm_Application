package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatMessageDeletedDetails extends GenericJson {
    @Key
    private String deletedMessageId;

    public String getDeletedMessageId() {
        return this.deletedMessageId;
    }

    public LiveChatMessageDeletedDetails setDeletedMessageId(String str) {
        this.deletedMessageId = str;
        return this;
    }

    public LiveChatMessageDeletedDetails set(String str, Object obj) {
        return (LiveChatMessageDeletedDetails) super.set(str, obj);
    }

    public LiveChatMessageDeletedDetails clone() {
        return (LiveChatMessageDeletedDetails) super.clone();
    }
}
