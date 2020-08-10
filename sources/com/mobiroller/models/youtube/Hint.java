package com.mobiroller.models.youtube;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Hint implements Serializable {
    @SerializedName("property")
    @Expose
    private String property;
    @SerializedName("value")
    @Expose
    private String value;

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String str) {
        this.property = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
