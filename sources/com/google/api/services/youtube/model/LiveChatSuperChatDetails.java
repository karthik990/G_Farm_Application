package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class LiveChatSuperChatDetails extends GenericJson {
    @Key
    private String amountDisplayString;
    @JsonString
    @Key
    private BigInteger amountMicros;
    @Key
    private String currency;
    @Key
    private Long tier;
    @Key
    private String userComment;

    public String getAmountDisplayString() {
        return this.amountDisplayString;
    }

    public LiveChatSuperChatDetails setAmountDisplayString(String str) {
        this.amountDisplayString = str;
        return this;
    }

    public BigInteger getAmountMicros() {
        return this.amountMicros;
    }

    public LiveChatSuperChatDetails setAmountMicros(BigInteger bigInteger) {
        this.amountMicros = bigInteger;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public LiveChatSuperChatDetails setCurrency(String str) {
        this.currency = str;
        return this;
    }

    public Long getTier() {
        return this.tier;
    }

    public LiveChatSuperChatDetails setTier(Long l) {
        this.tier = l;
        return this;
    }

    public String getUserComment() {
        return this.userComment;
    }

    public LiveChatSuperChatDetails setUserComment(String str) {
        this.userComment = str;
        return this;
    }

    public LiveChatSuperChatDetails set(String str, Object obj) {
        return (LiveChatSuperChatDetails) super.set(str, obj);
    }

    public LiveChatSuperChatDetails clone() {
        return (LiveChatSuperChatDetails) super.clone();
    }
}
