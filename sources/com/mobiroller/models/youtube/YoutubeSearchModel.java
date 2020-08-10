package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class YoutubeSearchModel implements Serializable {
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("items")
    @Expose
    private List<ItemSearch> items = null;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo;
    @SerializedName("prevPageToken")
    @Expose
    private String prevPageToken;
    @SerializedName("regionCode")
    @Expose
    private String regionCode;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String str) {
        this.kind = str;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public String getNextPageToken() {
        return this.nextPageToken;
    }

    public void setNextPageToken(String str) {
        this.nextPageToken = str;
    }

    public String getPrevPageToken() {
        return this.prevPageToken;
    }

    public void setPrevPageToken(String str) {
        this.prevPageToken = str;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String str) {
        this.regionCode = str;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo2) {
        this.pageInfo = pageInfo2;
    }

    public List<ItemSearch> getItems() {
        return this.items;
    }

    public void setItems(List<ItemSearch> list) {
        this.items = list;
    }
}
