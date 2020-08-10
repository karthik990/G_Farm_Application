package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ActivityContentDetailsPromotedItem extends GenericJson {
    @Key
    private String adTag;
    @Key
    private String clickTrackingUrl;
    @Key
    private String creativeViewUrl;
    @Key
    private String ctaType;
    @Key
    private String customCtaButtonText;
    @Key
    private String descriptionText;
    @Key
    private String destinationUrl;
    @Key
    private List<String> forecastingUrl;
    @Key
    private List<String> impressionUrl;
    @Key
    private String videoId;

    public String getAdTag() {
        return this.adTag;
    }

    public ActivityContentDetailsPromotedItem setAdTag(String str) {
        this.adTag = str;
        return this;
    }

    public String getClickTrackingUrl() {
        return this.clickTrackingUrl;
    }

    public ActivityContentDetailsPromotedItem setClickTrackingUrl(String str) {
        this.clickTrackingUrl = str;
        return this;
    }

    public String getCreativeViewUrl() {
        return this.creativeViewUrl;
    }

    public ActivityContentDetailsPromotedItem setCreativeViewUrl(String str) {
        this.creativeViewUrl = str;
        return this;
    }

    public String getCtaType() {
        return this.ctaType;
    }

    public ActivityContentDetailsPromotedItem setCtaType(String str) {
        this.ctaType = str;
        return this;
    }

    public String getCustomCtaButtonText() {
        return this.customCtaButtonText;
    }

    public ActivityContentDetailsPromotedItem setCustomCtaButtonText(String str) {
        this.customCtaButtonText = str;
        return this;
    }

    public String getDescriptionText() {
        return this.descriptionText;
    }

    public ActivityContentDetailsPromotedItem setDescriptionText(String str) {
        this.descriptionText = str;
        return this;
    }

    public String getDestinationUrl() {
        return this.destinationUrl;
    }

    public ActivityContentDetailsPromotedItem setDestinationUrl(String str) {
        this.destinationUrl = str;
        return this;
    }

    public List<String> getForecastingUrl() {
        return this.forecastingUrl;
    }

    public ActivityContentDetailsPromotedItem setForecastingUrl(List<String> list) {
        this.forecastingUrl = list;
        return this;
    }

    public List<String> getImpressionUrl() {
        return this.impressionUrl;
    }

    public ActivityContentDetailsPromotedItem setImpressionUrl(List<String> list) {
        this.impressionUrl = list;
        return this;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public ActivityContentDetailsPromotedItem setVideoId(String str) {
        this.videoId = str;
        return this;
    }

    public ActivityContentDetailsPromotedItem set(String str, Object obj) {
        return (ActivityContentDetailsPromotedItem) super.set(str, obj);
    }

    public ActivityContentDetailsPromotedItem clone() {
        return (ActivityContentDetailsPromotedItem) super.clone();
    }
}
