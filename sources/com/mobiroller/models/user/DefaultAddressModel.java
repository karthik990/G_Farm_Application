package com.mobiroller.models.user;

public class DefaultAddressModel {
    public String apiKey;
    public String appKey;
    public boolean isDefault = true;

    public DefaultAddressModel(String str, String str2) {
        this.appKey = str;
        this.apiKey = str2;
    }
}
