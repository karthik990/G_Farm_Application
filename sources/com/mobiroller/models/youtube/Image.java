package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Image implements Serializable {
    private static final long serialVersionUID = -2677435631774896709L;
    @SerializedName("bannerImageUrl")
    @Expose
    private String bannerImageUrl;
    @SerializedName("bannerMobileExtraHdImageUrl")
    @Expose
    private String bannerMobileExtraHdImageUrl;
    @SerializedName("bannerMobileHdImageUrl")
    @Expose
    private String bannerMobileHdImageUrl;
    @SerializedName("bannerMobileImageUrl")
    @Expose
    private String bannerMobileImageUrl;
    @SerializedName("bannerMobileLowImageUrl")
    @Expose
    private String bannerMobileLowImageUrl;
    @SerializedName("bannerMobileMediumHdImageUrl")
    @Expose
    private String bannerMobileMediumHdImageUrl;
    @SerializedName("bannerTabletExtraHdImageUrl")
    @Expose
    private String bannerTabletExtraHdImageUrl;
    @SerializedName("bannerTabletHdImageUrl")
    @Expose
    private String bannerTabletHdImageUrl;
    @SerializedName("bannerTabletImageUrl")
    @Expose
    private String bannerTabletImageUrl;
    @SerializedName("bannerTabletLowImageUrl")
    @Expose
    private String bannerTabletLowImageUrl;
    @SerializedName("bannerTvHighImageUrl")
    @Expose
    private String bannerTvHighImageUrl;
    @SerializedName("bannerTvImageUrl")
    @Expose
    private String bannerTvImageUrl;
    @SerializedName("bannerTvLowImageUrl")
    @Expose
    private String bannerTvLowImageUrl;
    @SerializedName("bannerTvMediumImageUrl")
    @Expose
    private String bannerTvMediumImageUrl;

    public String getBannerImageUrl() {
        return this.bannerImageUrl;
    }

    public void setBannerImageUrl(String str) {
        this.bannerImageUrl = str;
    }

    public String getBannerMobileImageUrl() {
        return this.bannerMobileImageUrl;
    }

    public void setBannerMobileImageUrl(String str) {
        this.bannerMobileImageUrl = str;
    }

    public String getBannerTabletLowImageUrl() {
        return this.bannerTabletLowImageUrl;
    }

    public void setBannerTabletLowImageUrl(String str) {
        this.bannerTabletLowImageUrl = str;
    }

    public String getBannerTabletImageUrl() {
        return this.bannerTabletImageUrl;
    }

    public void setBannerTabletImageUrl(String str) {
        this.bannerTabletImageUrl = str;
    }

    public String getBannerTabletHdImageUrl() {
        return this.bannerTabletHdImageUrl;
    }

    public void setBannerTabletHdImageUrl(String str) {
        this.bannerTabletHdImageUrl = str;
    }

    public String getBannerTabletExtraHdImageUrl() {
        return this.bannerTabletExtraHdImageUrl;
    }

    public void setBannerTabletExtraHdImageUrl(String str) {
        this.bannerTabletExtraHdImageUrl = str;
    }

    public String getBannerMobileLowImageUrl() {
        return this.bannerMobileLowImageUrl;
    }

    public void setBannerMobileLowImageUrl(String str) {
        this.bannerMobileLowImageUrl = str;
    }

    public String getBannerMobileMediumHdImageUrl() {
        return this.bannerMobileMediumHdImageUrl;
    }

    public void setBannerMobileMediumHdImageUrl(String str) {
        this.bannerMobileMediumHdImageUrl = str;
    }

    public String getBannerMobileHdImageUrl() {
        return this.bannerMobileHdImageUrl;
    }

    public void setBannerMobileHdImageUrl(String str) {
        this.bannerMobileHdImageUrl = str;
    }

    public String getBannerMobileExtraHdImageUrl() {
        return this.bannerMobileExtraHdImageUrl;
    }

    public void setBannerMobileExtraHdImageUrl(String str) {
        this.bannerMobileExtraHdImageUrl = str;
    }

    public String getBannerTvImageUrl() {
        return this.bannerTvImageUrl;
    }

    public void setBannerTvImageUrl(String str) {
        this.bannerTvImageUrl = str;
    }

    public String getBannerTvLowImageUrl() {
        return this.bannerTvLowImageUrl;
    }

    public void setBannerTvLowImageUrl(String str) {
        this.bannerTvLowImageUrl = str;
    }

    public String getBannerTvMediumImageUrl() {
        return this.bannerTvMediumImageUrl;
    }

    public void setBannerTvMediumImageUrl(String str) {
        this.bannerTvMediumImageUrl = str;
    }

    public String getBannerTvHighImageUrl() {
        return this.bannerTvHighImageUrl;
    }

    public void setBannerTvHighImageUrl(String str) {
        this.bannerTvHighImageUrl = str;
    }
}
