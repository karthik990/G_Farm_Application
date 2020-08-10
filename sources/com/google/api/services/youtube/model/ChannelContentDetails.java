package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class ChannelContentDetails extends GenericJson {
    @Key
    private RelatedPlaylists relatedPlaylists;

    public static final class RelatedPlaylists extends GenericJson {
        @Key
        private String favorites;
        @Key
        private String likes;
        @Key
        private String uploads;
        @Key
        private String watchHistory;
        @Key
        private String watchLater;

        public String getFavorites() {
            return this.favorites;
        }

        public RelatedPlaylists setFavorites(String str) {
            this.favorites = str;
            return this;
        }

        public String getLikes() {
            return this.likes;
        }

        public RelatedPlaylists setLikes(String str) {
            this.likes = str;
            return this;
        }

        public String getUploads() {
            return this.uploads;
        }

        public RelatedPlaylists setUploads(String str) {
            this.uploads = str;
            return this;
        }

        public String getWatchHistory() {
            return this.watchHistory;
        }

        public RelatedPlaylists setWatchHistory(String str) {
            this.watchHistory = str;
            return this;
        }

        public String getWatchLater() {
            return this.watchLater;
        }

        public RelatedPlaylists setWatchLater(String str) {
            this.watchLater = str;
            return this;
        }

        public RelatedPlaylists set(String str, Object obj) {
            return (RelatedPlaylists) super.set(str, obj);
        }

        public RelatedPlaylists clone() {
            return (RelatedPlaylists) super.clone();
        }
    }

    public RelatedPlaylists getRelatedPlaylists() {
        return this.relatedPlaylists;
    }

    public ChannelContentDetails setRelatedPlaylists(RelatedPlaylists relatedPlaylists2) {
        this.relatedPlaylists = relatedPlaylists2;
        return this;
    }

    public ChannelContentDetails set(String str, Object obj) {
        return (ChannelContentDetails) super.set(str, obj);
    }

    public ChannelContentDetails clone() {
        return (ChannelContentDetails) super.clone();
    }
}
