package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ContentDetails implements Serializable {
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("dimension")
    @Expose
    private String dimension;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("licensedContent")
    @Expose
    private Boolean licensedContent;
    @SerializedName("projection")
    @Expose
    private String projection;

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public String getDimension() {
        return this.dimension;
    }

    public void setDimension(String str) {
        this.dimension = str;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String str) {
        this.definition = str;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String str) {
        this.caption = str;
    }

    public Boolean getLicensedContent() {
        return this.licensedContent;
    }

    public void setLicensedContent(Boolean bool) {
        this.licensedContent = bool;
    }

    public String getProjection() {
        return this.projection;
    }

    public void setProjection(String str) {
        this.projection = str;
    }
}
