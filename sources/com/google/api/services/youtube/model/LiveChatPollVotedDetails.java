package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LiveChatPollVotedDetails extends GenericJson {
    @Key
    private String itemId;
    @Key
    private String pollId;

    public String getItemId() {
        return this.itemId;
    }

    public LiveChatPollVotedDetails setItemId(String str) {
        this.itemId = str;
        return this;
    }

    public String getPollId() {
        return this.pollId;
    }

    public LiveChatPollVotedDetails setPollId(String str) {
        this.pollId = str;
        return this;
    }

    public LiveChatPollVotedDetails set(String str, Object obj) {
        return (LiveChatPollVotedDetails) super.set(str, obj);
    }

    public LiveChatPollVotedDetails clone() {
        return (LiveChatPollVotedDetails) super.clone();
    }
}
