package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class SuperChatEventSnippet extends GenericJson {
    @JsonString
    @Key
    private BigInteger amountMicros;
    @Key
    private String channelId;
    @Key
    private String commentText;
    @Key
    private DateTime createdAt;
    @Key
    private String currency;
    @Key
    private String displayString;
    @Key
    private Boolean isSuperChatForGood;
    @Key
    private Boolean isSuperStickerEvent;
    @Key
    private Long messageType;
    @Key
    private Nonprofit nonprofit;
    @Key
    private SuperStickerMetadata superStickerMetadata;
    @Key
    private ChannelProfileDetails supporterDetails;

    public BigInteger getAmountMicros() {
        return this.amountMicros;
    }

    public SuperChatEventSnippet setAmountMicros(BigInteger bigInteger) {
        this.amountMicros = bigInteger;
        return this;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public SuperChatEventSnippet setChannelId(String str) {
        this.channelId = str;
        return this;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public SuperChatEventSnippet setCommentText(String str) {
        this.commentText = str;
        return this;
    }

    public DateTime getCreatedAt() {
        return this.createdAt;
    }

    public SuperChatEventSnippet setCreatedAt(DateTime dateTime) {
        this.createdAt = dateTime;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public SuperChatEventSnippet setCurrency(String str) {
        this.currency = str;
        return this;
    }

    public String getDisplayString() {
        return this.displayString;
    }

    public SuperChatEventSnippet setDisplayString(String str) {
        this.displayString = str;
        return this;
    }

    public Boolean getIsSuperChatForGood() {
        return this.isSuperChatForGood;
    }

    public SuperChatEventSnippet setIsSuperChatForGood(Boolean bool) {
        this.isSuperChatForGood = bool;
        return this;
    }

    public Boolean getIsSuperStickerEvent() {
        return this.isSuperStickerEvent;
    }

    public SuperChatEventSnippet setIsSuperStickerEvent(Boolean bool) {
        this.isSuperStickerEvent = bool;
        return this;
    }

    public Long getMessageType() {
        return this.messageType;
    }

    public SuperChatEventSnippet setMessageType(Long l) {
        this.messageType = l;
        return this;
    }

    public Nonprofit getNonprofit() {
        return this.nonprofit;
    }

    public SuperChatEventSnippet setNonprofit(Nonprofit nonprofit2) {
        this.nonprofit = nonprofit2;
        return this;
    }

    public SuperStickerMetadata getSuperStickerMetadata() {
        return this.superStickerMetadata;
    }

    public SuperChatEventSnippet setSuperStickerMetadata(SuperStickerMetadata superStickerMetadata2) {
        this.superStickerMetadata = superStickerMetadata2;
        return this;
    }

    public ChannelProfileDetails getSupporterDetails() {
        return this.supporterDetails;
    }

    public SuperChatEventSnippet setSupporterDetails(ChannelProfileDetails channelProfileDetails) {
        this.supporterDetails = channelProfileDetails;
        return this;
    }

    public SuperChatEventSnippet set(String str, Object obj) {
        return (SuperChatEventSnippet) super.set(str, obj);
    }

    public SuperChatEventSnippet clone() {
        return (SuperChatEventSnippet) super.clone();
    }
}
