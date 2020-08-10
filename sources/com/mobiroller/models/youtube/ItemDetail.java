package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemDetail implements Serializable {
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose

    /* renamed from: id */
    private String f2194id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    public ContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails2) {
        this.contentDetails = contentDetails2;
    }

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
        return this.f2194id;
    }

    public void setId(String str) {
        this.f2194id = str;
    }

    public Snippet getSnippet() {
        return this.snippet;
    }

    public void setSnippet(Snippet snippet2) {
        this.snippet = snippet2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ItemDetail{kind='");
        sb.append(this.kind);
        sb.append('\'');
        sb.append(", etag='");
        sb.append(this.etag);
        sb.append('\'');
        sb.append(", id='");
        sb.append(this.f2194id);
        sb.append('\'');
        sb.append(", snippet=");
        sb.append(this.snippet);
        sb.append(", contentDetails=");
        sb.append(this.contentDetails);
        sb.append('}');
        return sb.toString();
    }
}
