package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ChannelAuditDetails extends GenericJson {
    @Key
    private Boolean communityGuidelinesGoodStanding;
    @Key
    private Boolean contentIdClaimsGoodStanding;
    @Key
    private Boolean copyrightStrikesGoodStanding;

    public Boolean getCommunityGuidelinesGoodStanding() {
        return this.communityGuidelinesGoodStanding;
    }

    public ChannelAuditDetails setCommunityGuidelinesGoodStanding(Boolean bool) {
        this.communityGuidelinesGoodStanding = bool;
        return this;
    }

    public Boolean getContentIdClaimsGoodStanding() {
        return this.contentIdClaimsGoodStanding;
    }

    public ChannelAuditDetails setContentIdClaimsGoodStanding(Boolean bool) {
        this.contentIdClaimsGoodStanding = bool;
        return this;
    }

    public Boolean getCopyrightStrikesGoodStanding() {
        return this.copyrightStrikesGoodStanding;
    }

    public ChannelAuditDetails setCopyrightStrikesGoodStanding(Boolean bool) {
        this.copyrightStrikesGoodStanding = bool;
        return this;
    }

    public ChannelAuditDetails set(String str, Object obj) {
        return (ChannelAuditDetails) super.set(str, obj);
    }

    public ChannelAuditDetails clone() {
        return (ChannelAuditDetails) super.clone();
    }
}
