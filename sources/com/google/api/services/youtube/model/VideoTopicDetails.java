package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class VideoTopicDetails extends GenericJson {
    @Key
    private List<String> relevantTopicIds;
    @Key
    private List<String> topicCategories;
    @Key
    private List<String> topicIds;

    public List<String> getRelevantTopicIds() {
        return this.relevantTopicIds;
    }

    public VideoTopicDetails setRelevantTopicIds(List<String> list) {
        this.relevantTopicIds = list;
        return this;
    }

    public List<String> getTopicCategories() {
        return this.topicCategories;
    }

    public VideoTopicDetails setTopicCategories(List<String> list) {
        this.topicCategories = list;
        return this;
    }

    public List<String> getTopicIds() {
        return this.topicIds;
    }

    public VideoTopicDetails setTopicIds(List<String> list) {
        this.topicIds = list;
        return this;
    }

    public VideoTopicDetails set(String str, Object obj) {
        return (VideoTopicDetails) super.set(str, obj);
    }

    public VideoTopicDetails clone() {
        return (VideoTopicDetails) super.clone();
    }
}
