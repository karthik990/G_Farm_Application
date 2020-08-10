package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class LiveChatPollEditedDetails extends GenericJson {
    @Key

    /* renamed from: id */
    private String f1848id;
    @Key
    private List<LiveChatPollItem> items;
    @Key
    private String prompt;

    public String getId() {
        return this.f1848id;
    }

    public LiveChatPollEditedDetails setId(String str) {
        this.f1848id = str;
        return this;
    }

    public List<LiveChatPollItem> getItems() {
        return this.items;
    }

    public LiveChatPollEditedDetails setItems(List<LiveChatPollItem> list) {
        this.items = list;
        return this;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public LiveChatPollEditedDetails setPrompt(String str) {
        this.prompt = str;
        return this;
    }

    public LiveChatPollEditedDetails set(String str, Object obj) {
        return (LiveChatPollEditedDetails) super.set(str, obj);
    }

    public LiveChatPollEditedDetails clone() {
        return (LiveChatPollEditedDetails) super.clone();
    }
}
