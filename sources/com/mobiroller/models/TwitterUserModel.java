package com.mobiroller.models;

import java.io.Serializable;

public class TwitterUserModel implements Serializable {
    private String profile_image_url;

    public String getProfile_image_url() {
        return this.profile_image_url;
    }

    public void setProfile_image_url(String str) {
        this.profile_image_url = str;
    }
}
