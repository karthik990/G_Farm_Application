package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class LanguageTag extends GenericJson {
    @Key
    private String value;

    public String getValue() {
        return this.value;
    }

    public LanguageTag setValue(String str) {
        this.value = str;
        return this;
    }

    public LanguageTag set(String str, Object obj) {
        return (LanguageTag) super.set(str, obj);
    }

    public LanguageTag clone() {
        return (LanguageTag) super.clone();
    }
}
