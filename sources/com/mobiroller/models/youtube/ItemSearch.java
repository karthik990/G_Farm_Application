package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemSearch implements Serializable {
    @SerializedName("contentDetails")
    @Expose
    private ContentDetails contentDetails;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("id")
    @Expose

    /* renamed from: id */
    private C4289Id f2195id;
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

    public C4289Id getId() {
        return this.f2195id;
    }

    public void setId(C4289Id id) {
        this.f2195id = id;
    }

    public Snippet getSnippet() {
        return this.snippet;
    }

    public void setSnippet(Snippet snippet2) {
        this.snippet = snippet2;
    }
}
