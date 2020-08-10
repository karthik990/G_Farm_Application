package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LocalizedString extends GenericJson {
    @Key
    private String language;
    @Key
    private String value;

    public String getLanguage() {
        return this.language;
    }

    public LocalizedString setLanguage(String str) {
        this.language = str;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public LocalizedString setValue(String str) {
        this.value = str;
        return this;
    }

    public LocalizedString set(String str, Object obj) {
        return (LocalizedString) super.set(str, obj);
    }

    public LocalizedString clone() {
        return (LocalizedString) super.clone();
    }
}
