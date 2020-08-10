package com.mobiroller.models;

import java.io.Serializable;

public class FlagModel implements Serializable {
    private String detail;
    private String lattitude;
    private String longitude;
    private String title;

    public String getLattitude() {
        return this.lattitude;
    }

    public void setLattitude(String str) {
        this.lattitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String str) {
        this.detail = str;
    }
}
