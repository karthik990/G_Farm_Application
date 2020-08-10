package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class LiveChatFanFundingEventDetails extends GenericJson {
    @Key
    private String amountDisplayString;
    @JsonString
    @Key
    private BigInteger amountMicros;
    @Key
    private String currency;
    @Key
    private String userComment;

    public String getAmountDisplayString() {
        return this.amountDisplayString;
    }

    public LiveChatFanFundingEventDetails setAmountDisplayString(String str) {
        this.amountDisplayString = str;
        return this;
    }

    public BigInteger getAmountMicros() {
        return this.amountMicros;
    }

    public LiveChatFanFundingEventDetails setAmountMicros(BigInteger bigInteger) {
        this.amountMicros = bigInteger;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public LiveChatFanFundingEventDetails setCurrency(String str) {
        this.currency = str;
        return this;
    }

    public String getUserComment() {
        return this.userComment;
    }

    public LiveChatFanFundingEventDetails setUserComment(String str) {
        this.userComment = str;
        return this;
    }

    public LiveChatFanFundingEventDetails set(String str, Object obj) {
        return (LiveChatFanFundingEventDetails) super.set(str, obj);
    }

    public LiveChatFanFundingEventDetails clone() {
        return (LiveChatFanFundingEventDetails) super.clone();
    }
}
