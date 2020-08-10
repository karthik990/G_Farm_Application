package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class SponsorSnippet extends GenericJson {
    @Key
    private String channelId;
    @Key
    private Integer cumulativeDurationMonths;
    @Key
    private ChannelProfileDetails sponsorDetails;
    @Key
    private DateTime sponsorSince;

    public String getChannelId() {
        return this.channelId;
    }

    public SponsorSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public Integer getCumulativeDurationMonths() {
        return this.cumulativeDurationMonths;
    }

    public SponsorSnippet setCumulativeDurationMonths(Integer num) {
        this.cumulativeDurationMonths = num;
        return this;
    }

    public ChannelProfileDetails getSponsorDetails() {
        return this.sponsorDetails;
    }

    public SponsorSnippet setSponsorDetails(ChannelProfileDetails channelProfileDetails) {
        this.sponsorDetails = channelProfileDetails;
        return this;
    }

    public DateTime getSponsorSince() {
        return this.sponsorSince;
    }

    public SponsorSnippet setSponsorSince(DateTime dateTime) {
        this.sponsorSince = dateTime;
        return this;
    }

    public SponsorSnippet set(String str, Object obj) {
        return (SponsorSnippet) super.set(str, obj);
    }

    public SponsorSnippet clone() {
        return (SponsorSnippet) super.clone();
    }
}
