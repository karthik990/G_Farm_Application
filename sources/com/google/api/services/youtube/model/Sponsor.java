package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Sponsor extends GenericJson {
    @Key
    private String etag;
    @Key
    private String kind;
    @Key
    private SponsorSnippet snippet;

    public String getEtag() {
        return this.etag;
    }

    public Sponsor setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Sponsor setKind(String str) {
        this.kind = str;
        return this;
    }

    public SponsorSnippet getSnippet() {
        return this.snippet;
    }

    public Sponsor setSnippet(SponsorSnippet sponsorSnippet) {
        this.snippet = sponsorSnippet;
        return this;
    }

    public Sponsor set(String str, Object obj) {
        return (Sponsor) super.set(str, obj);
    }

    public Sponsor clone() {
        return (Sponsor) super.clone();
    }
}
