package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChannelSettings extends GenericJson {
    @Key
    private String country;
    @Key
    private String defaultLanguage;
    @Key
    private String defaultTab;
    @Key
    private String description;
    @Key
    private String featuredChannelsTitle;
    @Key
    private List<String> featuredChannelsUrls;
    @Key
    private String keywords;
    @Key
    private Boolean moderateComments;
    @Key
    private String profileColor;
    @Key
    private Boolean showBrowseView;
    @Key
    private Boolean showRelatedChannels;
    @Key
    private String title;
    @Key
    private String trackingAnalyticsAccountId;
    @Key
    private String unsubscribedTrailer;

    public String getCountry() {
        return this.country;
    }

    public ChannelSettings setCountry(String str) {
        this.country = str;
        return this;
    }

    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }

    public ChannelSettings setDefaultLanguage(String str) {
        this.defaultLanguage = str;
        return this;
    }

    public String getDefaultTab() {
        return this.defaultTab;
    }

    public ChannelSettings setDefaultTab(String str) {
        this.defaultTab = str;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public ChannelSettings setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getFeaturedChannelsTitle() {
        return this.featuredChannelsTitle;
    }

    public ChannelSettings setFeaturedChannelsTitle(String str) {
        this.featuredChannelsTitle = str;
        return this;
    }

    public List<String> getFeaturedChannelsUrls() {
        return this.featuredChannelsUrls;
    }

    public ChannelSettings setFeaturedChannelsUrls(List<String> list) {
        this.featuredChannelsUrls = list;
        return this;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public ChannelSettings setKeywords(String str) {
        this.keywords = str;
        return this;
    }

    public Boolean getModerateComments() {
        return this.moderateComments;
    }

    public ChannelSettings setModerateComments(Boolean bool) {
        this.moderateComments = bool;
        return this;
    }

    public String getProfileColor() {
        return this.profileColor;
    }

    public ChannelSettings setProfileColor(String str) {
        this.profileColor = str;
        return this;
    }

    public Boolean getShowBrowseView() {
        return this.showBrowseView;
    }

    public ChannelSettings setShowBrowseView(Boolean bool) {
        this.showBrowseView = bool;
        return this;
    }

    public Boolean getShowRelatedChannels() {
        return this.showRelatedChannels;
    }

    public ChannelSettings setShowRelatedChannels(Boolean bool) {
        this.showRelatedChannels = bool;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ChannelSettings setTitle(String str) {
        this.title = str;
        return this;
    }

    public String getTrackingAnalyticsAccountId() {
        return this.trackingAnalyticsAccountId;
    }

    public ChannelSettings setTrackingAnalyticsAccountId(String str) {
        this.trackingAnalyticsAccountId = str;
        return this;
    }

    public String getUnsubscribedTrailer() {
        return this.unsubscribedTrailer;
    }

    public ChannelSettings setUnsubscribedTrailer(String str) {
        this.unsubscribedTrailer = str;
        return this;
    }

    public ChannelSettings set(String str, Object obj) {
        return (ChannelSettings) super.set(str, obj);
    }

    public ChannelSettings clone() {
        return (ChannelSettings) super.clone();
    }
}
