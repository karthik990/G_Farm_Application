package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.math.BigInteger;

public final class LiveBroadcastStatistics extends GenericJson {
    @JsonString
    @Key
    private BigInteger concurrentViewers;
    @JsonString
    @Key
    private BigInteger totalChatCount;

    public BigInteger getConcurrentViewers() {
        return this.concurrentViewers;
    }

    public LiveBroadcastStatistics setConcurrentViewers(BigInteger bigInteger) {
        this.concurrentViewers = bigInteger;
        return this;
    }

    public BigInteger getTotalChatCount() {
        return this.totalChatCount;
    }

    public LiveBroadcastStatistics setTotalChatCount(BigInteger bigInteger) {
        this.totalChatCount = bigInteger;
        return this;
    }

    public LiveBroadcastStatistics set(String str, Object obj) {
        return (LiveBroadcastStatistics) super.set(str, obj);
    }

    public LiveBroadcastStatistics clone() {
        return (LiveBroadcastStatistics) super.clone();
    }
}
