package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class YoutubeDetailModel implements Serializable {
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("items")
    @Expose
    private List<ItemDetail> items = null;
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
    private String screenId;

    public String getScreenId() {
        return this.screenId;
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String str) {
        this.kind = str;
    }

    public List<ItemDetail> getItems() {
        return this.items;
    }

    public void setItems(List<ItemDetail> list) {
        this.items = list;
    }
}
