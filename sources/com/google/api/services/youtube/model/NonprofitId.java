package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class NonprofitId extends GenericJson {
    @Key
    private String value;

    public String getValue() {
        return this.value;
    }

    public NonprofitId setValue(String str) {
        this.value = str;
        return this;
    }

    public NonprofitId set(String str, Object obj) {
        return (NonprofitId) super.set(str, obj);
    }

    public NonprofitId clone() {
        return (NonprofitId) super.clone();
    }
}
