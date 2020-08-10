package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Thumbnails implements Serializable {
    @SerializedName("default")
    @Expose
    private YoutubeImage _default;
    @SerializedName("high")
    @Expose
    private YoutubeImage high;
    @SerializedName("maxres")
    @Expose
    private YoutubeImage maxres;
    @SerializedName("medium")
    @Expose
    private YoutubeImage medium;
    @SerializedName("standard")
    @Expose
    private YoutubeImage standard;

    public YoutubeImage get_default() {
        return this._default;
    }

    public void set_default(YoutubeImage youtubeImage) {
        this._default = youtubeImage;
    }

    public YoutubeImage getMedium() {
        return this.medium;
    }

    public void setMedium(YoutubeImage youtubeImage) {
        this.medium = youtubeImage;
    }

    public YoutubeImage getHigh() {
        return this.high;
    }

    public void setHigh(YoutubeImage youtubeImage) {
        this.high = youtubeImage;
    }

    public YoutubeImage getStandard() {
        return this.standard;
    }

    public void setStandard(YoutubeImage youtubeImage) {
        this.standard = youtubeImage;
    }

    public YoutubeImage getMaxres() {
        return this.maxres;
    }

    public void setMaxres(YoutubeImage youtubeImage) {
        this.maxres = youtubeImage;
    }

    public YoutubeImage getAvailableImage() {
        YoutubeImage youtubeImage = this.high;
        if (youtubeImage != null) {
            return youtubeImage;
        }
        YoutubeImage youtubeImage2 = this.medium;
        if (youtubeImage2 != null) {
            return youtubeImage2;
        }
        return this._default;
    }
}
