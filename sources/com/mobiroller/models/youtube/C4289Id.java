package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* renamed from: com.mobiroller.models.youtube.Id */
public class C4289Id implements Serializable {
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String str) {
        this.kind = str;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String str) {
        this.videoId = str;
    }
}
