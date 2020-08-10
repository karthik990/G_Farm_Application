package com.mobiroller.models;

import java.io.Serializable;

public class ImageModel implements Serializable {
    private String imageURL;

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String str) {
        this.imageURL = str;
    }
}
