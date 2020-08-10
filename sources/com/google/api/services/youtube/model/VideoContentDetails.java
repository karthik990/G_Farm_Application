package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class VideoContentDetails extends GenericJson {
    @Key
    private String caption;
    @Key
    private ContentRating contentRating;
    @Key
    private AccessPolicy countryRestriction;
    @Key
    private String definition;
    @Key
    private String dimension;
    @Key
    private String duration;
    @Key
    private Boolean hasCustomThumbnail;
    @Key
    private Boolean licensedContent;
    @Key
    private String projection;
    @Key
    private VideoContentDetailsRegionRestriction regionRestriction;

    public String getCaption() {
        return this.caption;
    }

    public VideoContentDetails setCaption(String str) {
        this.caption = str;
        return this;
    }

    public ContentRating getContentRating() {
        return this.contentRating;
    }

    public VideoContentDetails setContentRating(ContentRating contentRating2) {
        this.contentRating = contentRating2;
        return this;
    }

    public AccessPolicy getCountryRestriction() {
        return this.countryRestriction;
    }

    public VideoContentDetails setCountryRestriction(AccessPolicy accessPolicy) {
        this.countryRestriction = accessPolicy;
        return this;
    }

    public String getDefinition() {
        return this.definition;
    }

    public VideoContentDetails setDefinition(String str) {
        this.definition = str;
        return this;
    }

    public String getDimension() {
        return this.dimension;
    }

    public VideoContentDetails setDimension(String str) {
        this.dimension = str;
        return this;
    }

    public String getDuration() {
        return this.duration;
    }

    public VideoContentDetails setDuration(String str) {
        this.duration = str;
        return this;
    }

    public Boolean getHasCustomThumbnail() {
        return this.hasCustomThumbnail;
    }

    public VideoContentDetails setHasCustomThumbnail(Boolean bool) {
        this.hasCustomThumbnail = bool;
        return this;
    }

    public Boolean getLicensedContent() {
        return this.licensedContent;
    }

    public VideoContentDetails setLicensedContent(Boolean bool) {
        this.licensedContent = bool;
        return this;
    }

    public String getProjection() {
        return this.projection;
    }

    public VideoContentDetails setProjection(String str) {
        this.projection = str;
        return this;
    }

    public VideoContentDetailsRegionRestriction getRegionRestriction() {
        return this.regionRestriction;
    }

    public VideoContentDetails setRegionRestriction(VideoContentDetailsRegionRestriction videoContentDetailsRegionRestriction) {
        this.regionRestriction = videoContentDetailsRegionRestriction;
        return this;
    }

    public VideoContentDetails set(String str, Object obj) {
        return (VideoContentDetails) super.set(str, obj);
    }

    public VideoContentDetails clone() {
        return (VideoContentDetails) super.clone();
    }
}
