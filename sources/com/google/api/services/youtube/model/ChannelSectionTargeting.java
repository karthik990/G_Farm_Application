package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelSectionTargeting extends GenericJson {
    @Key
    private List<String> countries;
    @Key
    private List<String> languages;
    @Key
    private List<String> regions;

    public List<String> getCountries() {
        return this.countries;
    }

    public ChannelSectionTargeting setCountries(List<String> list) {
        this.countries = list;
        return this;
    }

    public List<String> getLanguages() {
        return this.languages;
    }

    public ChannelSectionTargeting setLanguages(List<String> list) {
        this.languages = list;
        return this;
    }

    public List<String> getRegions() {
        return this.regions;
    }

    public ChannelSectionTargeting setRegions(List<String> list) {
        this.regions = list;
        return this;
    }

    public ChannelSectionTargeting set(String str, Object obj) {
        return (ChannelSectionTargeting) super.set(str, obj);
    }

    public ChannelSectionTargeting clone() {
        return (ChannelSectionTargeting) super.clone();
    }
}
