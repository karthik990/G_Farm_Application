package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class LiveChatSuperStickerDetails extends GenericJson {
    @Key
    private String amountDisplayString;
    @JsonString
    @Key
    private BigInteger amountMicros;
    @Key
    private String currency;
    @Key
    private SuperStickerMetadata superStickerMetadata;
    @Key
    private Long tier;

    public String getAmountDisplayString() {
        return this.amountDisplayString;
    }

    public LiveChatSuperStickerDetails setAmountDisplayString(String str) {
        this.amountDisplayString = str;
        return this;
    }

    public BigInteger getAmountMicros() {
        return this.amountMicros;
    }

    public LiveChatSuperStickerDetails setAmountMicros(BigInteger bigInteger) {
        this.amountMicros = bigInteger;
        return this;
    }

    public String getCurrency() {
        return this.currency;
    }

    public LiveChatSuperStickerDetails setCurrency(String str) {
        this.currency = str;
        return this;
    }

    public SuperStickerMetadata getSuperStickerMetadata() {
        return this.superStickerMetadata;
    }

    public LiveChatSuperStickerDetails setSuperStickerMetadata(SuperStickerMetadata superStickerMetadata2) {
        this.superStickerMetadata = superStickerMetadata2;
        return this;
    }

    public Long getTier() {
        return this.tier;
    }

    public LiveChatSuperStickerDetails setTier(Long l) {
        this.tier = l;
        return this;
    }

    public LiveChatSuperStickerDetails set(String str, Object obj) {
        return (LiveChatSuperStickerDetails) super.set(str, obj);
    }

    public LiveChatSuperStickerDetails clone() {
        return (LiveChatSuperStickerDetails) super.clone();
    }
}
