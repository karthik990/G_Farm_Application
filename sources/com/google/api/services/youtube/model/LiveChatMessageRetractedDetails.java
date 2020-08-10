package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatMessageRetractedDetails extends GenericJson {
    @Key
    private String retractedMessageId;

    public String getRetractedMessageId() {
        return this.retractedMessageId;
    }

    public LiveChatMessageRetractedDetails setRetractedMessageId(String str) {
        this.retractedMessageId = str;
        return this;
    }

    public LiveChatMessageRetractedDetails set(String str, Object obj) {
        return (LiveChatMessageRetractedDetails) super.set(str, obj);
    }

    public LiveChatMessageRetractedDetails clone() {
        return (LiveChatMessageRetractedDetails) super.clone();
    }
}
