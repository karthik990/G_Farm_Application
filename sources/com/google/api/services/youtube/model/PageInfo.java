package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class PageInfo extends GenericJson {
    @Key
    private Integer resultsPerPage;
    @Key
    private Integer totalResults;

    public Integer getResultsPerPage() {
        return this.resultsPerPage;
    }

    public PageInfo setResultsPerPage(Integer num) {
        this.resultsPerPage = num;
        return this;
    }

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public PageInfo setTotalResults(Integer num) {
        this.totalResults = num;
        return this;
    }

    public PageInfo set(String str, Object obj) {
        return (PageInfo) super.set(str, obj);
    }

    public PageInfo clone() {
        return (PageInfo) super.clone();
    }
}
