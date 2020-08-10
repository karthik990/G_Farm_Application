package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class I18nLanguageSnippet extends GenericJson {
    @Key

    /* renamed from: hl */
    private String f1841hl;
    @Key
    private String name;

    public String getHl() {
        return this.f1841hl;
    }

    public I18nLanguageSnippet setHl(String str) {
        this.f1841hl = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public I18nLanguageSnippet setName(String str) {
        this.name = str;
        return this;
    }

    public I18nLanguageSnippet set(String str, Object obj) {
        return (I18nLanguageSnippet) super.set(str, obj);
    }

    public I18nLanguageSnippet clone() {
        return (I18nLanguageSnippet) super.clone();
    }
}
