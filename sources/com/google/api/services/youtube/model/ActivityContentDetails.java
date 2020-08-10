package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ActivityContentDetails extends GenericJson {
    @Key
    private ActivityContentDetailsBulletin bulletin;
    @Key
    private ActivityContentDetailsChannelItem channelItem;
    @Key
    private ActivityContentDetailsComment comment;
    @Key
    private ActivityContentDetailsFavorite favorite;
    @Key
    private ActivityContentDetailsLike like;
    @Key
    private ActivityContentDetailsPlaylistItem playlistItem;
    @Key
    private ActivityContentDetailsPromotedItem promotedItem;
    @Key
    private ActivityContentDetailsRecommendation recommendation;
    @Key
    private ActivityContentDetailsSocial social;
    @Key
    private ActivityContentDetailsSubscription subscription;
    @Key
    private ActivityContentDetailsUpload upload;

    public ActivityContentDetailsBulletin getBulletin() {
        return this.bulletin;
    }

    public ActivityContentDetails setBulletin(ActivityContentDetailsBulletin activityContentDetailsBulletin) {
        this.bulletin = activityContentDetailsBulletin;
        return this;
    }

    public ActivityContentDetailsChannelItem getChannelItem() {
        return this.channelItem;
    }

    public ActivityContentDetails setChannelItem(ActivityContentDetailsChannelItem activityContentDetailsChannelItem) {
        this.channelItem = activityContentDetailsChannelItem;
        return this;
    }

    public ActivityContentDetailsComment getComment() {
        return this.comment;
    }

    public ActivityContentDetails setComment(ActivityContentDetailsComment activityContentDetailsComment) {
        this.comment = activityContentDetailsComment;
        return this;
    }

    public ActivityContentDetailsFavorite getFavorite() {
        return this.favorite;
    }

    public ActivityContentDetails setFavorite(ActivityContentDetailsFavorite activityContentDetailsFavorite) {
        this.favorite = activityContentDetailsFavorite;
        return this;
    }

    public ActivityContentDetailsLike getLike() {
        return this.like;
    }

    public ActivityContentDetails setLike(ActivityContentDetailsLike activityContentDetailsLike) {
        this.like = activityContentDetailsLike;
        return this;
    }

    public ActivityContentDetailsPlaylistItem getPlaylistItem() {
        return this.playlistItem;
    }

    public ActivityContentDetails setPlaylistItem(ActivityContentDetailsPlaylistItem activityContentDetailsPlaylistItem) {
        this.playlistItem = activityContentDetailsPlaylistItem;
        return this;
    }

    public ActivityContentDetailsPromotedItem getPromotedItem() {
        return this.promotedItem;
    }

    public ActivityContentDetails setPromotedItem(ActivityContentDetailsPromotedItem activityContentDetailsPromotedItem) {
        this.promotedItem = activityContentDetailsPromotedItem;
        return this;
    }

    public ActivityContentDetailsRecommendation getRecommendation() {
        return this.recommendation;
    }

    public ActivityContentDetails setRecommendation(ActivityContentDetailsRecommendation activityContentDetailsRecommendation) {
        this.recommendation = activityContentDetailsRecommendation;
        return this;
    }

    public ActivityContentDetailsSocial getSocial() {
        return this.social;
    }

    public ActivityContentDetails setSocial(ActivityContentDetailsSocial activityContentDetailsSocial) {
        this.social = activityContentDetailsSocial;
        return this;
    }

    public ActivityContentDetailsSubscription getSubscription() {
        return this.subscription;
    }

    public ActivityContentDetails setSubscription(ActivityContentDetailsSubscription activityContentDetailsSubscription) {
        this.subscription = activityContentDetailsSubscription;
        return this;
    }

    public ActivityContentDetailsUpload getUpload() {
        return this.upload;
    }

    public ActivityContentDetails setUpload(ActivityContentDetailsUpload activityContentDetailsUpload) {
        this.upload = activityContentDetailsUpload;
        return this;
    }

    public ActivityContentDetails set(String str, Object obj) {
        return (ActivityContentDetails) super.set(str, obj);
    }

    public ActivityContentDetails clone() {
        return (ActivityContentDetails) super.clone();
    }
}
