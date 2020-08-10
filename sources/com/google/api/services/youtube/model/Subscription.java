package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class Subscription extends GenericJson {
    @Key
    private SubscriptionContentDetails contentDetails;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1855id;
    @Key
    private String kind;
    @Key
    private SubscriptionSnippet snippet;
    @Key
    private SubscriptionSubscriberSnippet subscriberSnippet;

    public SubscriptionContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public Subscription setContentDetails(SubscriptionContentDetails subscriptionContentDetails) {
        this.contentDetails = subscriptionContentDetails;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Subscription setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1855id;
    }

    public Subscription setId(String str) {
        this.f1855id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Subscription setKind(String str) {
        this.kind = str;
        return this;
    }

    public SubscriptionSnippet getSnippet() {
        return this.snippet;
    }

    public Subscription setSnippet(SubscriptionSnippet subscriptionSnippet) {
        this.snippet = subscriptionSnippet;
        return this;
    }

    public SubscriptionSubscriberSnippet getSubscriberSnippet() {
        return this.subscriberSnippet;
    }

    public Subscription setSubscriberSnippet(SubscriptionSubscriberSnippet subscriptionSubscriberSnippet) {
        this.subscriberSnippet = subscriptionSubscriberSnippet;
        return this;
    }

    public Subscription set(String str, Object obj) {
        return (Subscription) super.set(str, obj);
    }

    public Subscription clone() {
        return (Subscription) super.clone();
    }
}
