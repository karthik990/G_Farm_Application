package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelSectionContentDetails extends GenericJson {
    @Key
    private List<String> channels;
    @Key
    private List<String> playlists;

    public List<String> getChannels() {
        return this.channels;
    }

    public ChannelSectionContentDetails setChannels(List<String> list) {
        this.channels = list;
        return this;
    }

    public List<String> getPlaylists() {
        return this.playlists;
    }

    public ChannelSectionContentDetails setPlaylists(List<String> list) {
        this.playlists = list;
        return this;
    }

    public ChannelSectionContentDetails set(String str, Object obj) {
        return (ChannelSectionContentDetails) super.set(str, obj);
    }

    public ChannelSectionContentDetails clone() {
        return (ChannelSectionContentDetails) super.clone();
    }
}
