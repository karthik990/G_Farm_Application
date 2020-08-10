package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class I18nLanguage extends GenericJson {
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1840id;
    @Key
    private String kind;
    @Key
    private I18nLanguageSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public I18nLanguage setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1840id;
    }

    public I18nLanguage setId(String str) {
        this.f1840id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public I18nLanguage setKind(String str) {
        this.kind = str;
        return this;
    }

    public I18nLanguageSnippet getSnippet() {
        return this.snippet;
    }

    public I18nLanguage setSnippet(I18nLanguageSnippet i18nLanguageSnippet) {
        this.snippet = i18nLanguageSnippet;
        return this;
    }

    public I18nLanguage set(String str, Object obj) {
        return (I18nLanguage) super.set(str, obj);
    }

    public I18nLanguage clone() {
        return (I18nLanguage) super.clone();
    }
}
