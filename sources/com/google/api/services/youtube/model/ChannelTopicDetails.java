package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelTopicDetails extends GenericJson {
    @Key
    private List<String> topicCategories;
    @Key
    private List<String> topicIds;

    public List<String> getTopicCategories() {
        return this.topicCategories;
    }

    public ChannelTopicDetails setTopicCategories(List<String> list) {
        this.topicCategories = list;
        return this;
    }

    public List<String> getTopicIds() {
        return this.topicIds;
    }

    public ChannelTopicDetails setTopicIds(List<String> list) {
        this.topicIds = list;
        return this;
    }

    public ChannelTopicDetails set(String str, Object obj) {
        return (ChannelTopicDetails) super.set(str, obj);
    }

    public ChannelTopicDetails clone() {
        return (ChannelTopicDetails) super.clone();
    }
}
