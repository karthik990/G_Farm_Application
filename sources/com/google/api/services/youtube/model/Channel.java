package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Channel extends GenericJson {
    @Key
    private ChannelAuditDetails auditDetails;
    @Key
    private ChannelBrandingSettings brandingSettings;
    @Key
    private ChannelContentDetails contentDetails;
    @Key
    private ChannelContentOwnerDetails contentOwnerDetails;
    @Key
    private ChannelConversionPings conversionPings;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1835id;
    @Key
    private InvideoPromotion invideoPromotion;
    @Key
    private String kind;
    @Key
    private Map<String, ChannelLocalization> localizations;
    @Key
    private ChannelSnippet snippet;
    @Key
    private ChannelStatistics statistics;
    @Key
    private ChannelStatus status;
    @Key
    private ChannelTopicDetails topicDetails;

    public ChannelAuditDetails getAuditDetails() {
        return this.auditDetails;
    }

    public Channel setAuditDetails(ChannelAuditDetails channelAuditDetails) {
        this.auditDetails = channelAuditDetails;
        return this;
    }

    public ChannelBrandingSettings getBrandingSettings() {
        return this.brandingSettings;
    }

    public Channel setBrandingSettings(ChannelBrandingSettings channelBrandingSettings) {
        this.brandingSettings = channelBrandingSettings;
        return this;
    }

    public ChannelContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public Channel setContentDetails(ChannelContentDetails channelContentDetails) {
        this.contentDetails = channelContentDetails;
        return this;
    }

    public ChannelContentOwnerDetails getContentOwnerDetails() {
        return this.contentOwnerDetails;
    }

    public Channel setContentOwnerDetails(ChannelContentOwnerDetails channelContentOwnerDetails) {
        this.contentOwnerDetails = channelContentOwnerDetails;
        return this;
    }

    public ChannelConversionPings getConversionPings() {
        return this.conversionPings;
    }

    public Channel setConversionPings(ChannelConversionPings channelConversionPings) {
        this.conversionPings = channelConversionPings;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Channel setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1835id;
    }

    public Channel setId(String str) {
        this.f1835id = str;
        return this;
    }

    public InvideoPromotion getInvideoPromotion() {
        return this.invideoPromotion;
    }

    public Channel setInvideoPromotion(InvideoPromotion invideoPromotion2) {
        this.invideoPromotion = invideoPromotion2;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Channel setKind(String str) {
        this.kind = str;
        return this;
    }

    public Map<String, ChannelLocalization> getLocalizations() {
        return this.localizations;
    }

    public Channel setLocalizations(Map<String, ChannelLocalization> map) {
        this.localizations = map;
        return this;
    }

    public ChannelSnippet getSnippet() {
        return this.snippet;
    }

    public Channel setSnippet(ChannelSnippet channelSnippet) {
        this.snippet = channelSnippet;
        return this;
    }

    public ChannelStatistics getStatistics() {
        return this.statistics;
    }

    public Channel setStatistics(ChannelStatistics channelStatistics) {
        this.statistics = channelStatistics;
        return this;
    }

    public ChannelStatus getStatus() {
        return this.status;
    }

    public Channel setStatus(ChannelStatus channelStatus) {
        this.status = channelStatus;
        return this;
    }

    public ChannelTopicDetails getTopicDetails() {
        return this.topicDetails;
    }

    public Channel setTopicDetails(ChannelTopicDetails channelTopicDetails) {
        this.topicDetails = channelTopicDetails;
        return this;
    }

    public Channel set(String str, Object obj) {
        return (Channel) super.set(str, obj);
    }

    public Channel clone() {
        return (Channel) super.clone();
    }
}
