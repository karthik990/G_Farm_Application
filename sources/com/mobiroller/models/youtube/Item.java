package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Item implements Serializable {
    @SerializedName("brandingSettings")
    @Expose
    private BrandingSettings brandingSettings;
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose

    /* renamed from: id */
    private String f2193id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

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

    public String getId() {
        return this.f2193id;
    }

    public void setId(String str) {
        this.f2193id = str;
    }

    public Snippet getSnippet() {
        return this.snippet;
    }

    public void setSnippet(Snippet snippet2) {
        this.snippet = snippet2;
    }

    public ContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails2) {
        this.contentDetails = contentDetails2;
    }

    public BrandingSettings getBrandingSettings() {
        return this.brandingSettings;
    }

    public void setBrandingSettings(BrandingSettings brandingSettings2) {
        this.brandingSettings = brandingSettings2;
    }
}
