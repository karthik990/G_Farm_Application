package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class RelatedPlaylists implements Serializable {
    @SerializedName("favorites")
    @Expose
    private String favorites;
    @SerializedName("uploads")
    @Expose
    private String uploads;
    @SerializedName("watchHistory")
    @Expose
    private String watchHistory;
    @SerializedName("watchLater")
    @Expose
    private String watchLater;

    public String getFavorites() {
        return this.favorites;
    }

    public void setFavorites(String str) {
        this.favorites = str;
    }

    public String getUploads() {
        return this.uploads;
    }

    public void setUploads(String str) {
        this.uploads = str;
    }

    public String getWatchHistory() {
        return this.watchHistory;
    }

    public void setWatchHistory(String str) {
        this.watchHistory = str;
    }

    public String getWatchLater() {
        return this.watchLater;
    }

    public void setWatchLater(String str) {
        this.watchLater = str;
    }
}
