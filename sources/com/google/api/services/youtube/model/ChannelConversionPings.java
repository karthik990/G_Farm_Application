package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelConversionPings extends GenericJson {
    @Key
    private List<ChannelConversionPing> pings;

    static {
        Data.nullOf(ChannelConversionPing.class);
    }

    public List<ChannelConversionPing> getPings() {
        return this.pings;
    }

    public ChannelConversionPings setPings(List<ChannelConversionPing> list) {
        this.pings = list;
        return this;
    }

    public ChannelConversionPings set(String str, Object obj) {
        return (ChannelConversionPings) super.set(str, obj);
    }

    public ChannelConversionPings clone() {
        return (ChannelConversionPings) super.clone();
    }
}
