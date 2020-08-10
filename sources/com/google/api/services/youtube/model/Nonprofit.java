package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Nonprofit extends GenericJson {
    @Key
    private NonprofitId nonprofitId;
    @Key
    private String nonprofitLegalName;

    public NonprofitId getNonprofitId() {
        return this.nonprofitId;
    }

    public Nonprofit setNonprofitId(NonprofitId nonprofitId2) {
        this.nonprofitId = nonprofitId2;
        return this;
    }

    public String getNonprofitLegalName() {
        return this.nonprofitLegalName;
    }

    public Nonprofit setNonprofitLegalName(String str) {
        this.nonprofitLegalName = str;
        return this;
    }

    public Nonprofit set(String str, Object obj) {
        return (Nonprofit) super.set(str, obj);
    }

    public Nonprofit clone() {
        return (Nonprofit) super.clone();
    }
}
