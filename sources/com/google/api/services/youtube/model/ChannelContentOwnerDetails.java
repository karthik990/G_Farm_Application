package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class ChannelContentOwnerDetails extends GenericJson {
    @Key
    private String contentOwner;
    @Key
    private DateTime timeLinked;

    public String getContentOwner() {
        return this.contentOwner;
    }

    public ChannelContentOwnerDetails setContentOwner(String str) {
        this.contentOwner = str;
        return this;
    }

    public DateTime getTimeLinked() {
        return this.timeLinked;
    }

    public ChannelContentOwnerDetails setTimeLinked(DateTime dateTime) {
        this.timeLinked = dateTime;
        return this;
    }

    public ChannelContentOwnerDetails set(String str, Object obj) {
        return (ChannelContentOwnerDetails) super.set(str, obj);
    }

    public ChannelContentOwnerDetails clone() {
        return (ChannelContentOwnerDetails) super.clone();
    }
}
