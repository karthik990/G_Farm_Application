package com.mobiroller.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class UserAgreementModel implements Serializable {
    public Boolean isActive;
    @SerializedName("URL")
    public String url;
}
