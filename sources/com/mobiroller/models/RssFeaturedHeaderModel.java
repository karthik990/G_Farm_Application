package com.mobiroller.models;

public class RssFeaturedHeaderModel {
    private RssModel featuredHeader;

    public RssFeaturedHeaderModel(RssModel rssModel) {
        this.featuredHeader = rssModel;
    }

    public RssModel getFeaturedHeader() {
        return this.featuredHeader;
    }

    public void setFeaturedHeader(RssModel rssModel) {
        this.featuredHeader = rssModel;
    }
}
