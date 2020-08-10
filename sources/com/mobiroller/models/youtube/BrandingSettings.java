package com.mobiroller.models.youtube;

import com.google.api.services.youtube.model.Channel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class BrandingSettings implements Serializable {
    @SerializedName("channel")
    @Expose
    private Channel channel;
    @SerializedName("hints")
    @Expose
    private List<Hint> hints = null;
    @SerializedName("image")
    @Expose
    private Image image;

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel2) {
        this.channel = channel2;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image2) {
        this.image = image2;
    }

    public List<Hint> getHints() {
        return this.hints;
    }

    public void setHints(List<Hint> list) {
        this.hints = list;
    }
}
