package com.mobiroller.models.request;

public class CheckLoginStateDto {
    public String apiKey;
    public String appKey;

    /* renamed from: id */
    public String f2186id;
    public String sessionKey;
    public String udid;

    public CheckLoginStateDto(String str, String str2, String str3, String str4, String str5) {
        this.sessionKey = str;
        this.f2186id = str2;
        this.udid = str3;
        this.apiKey = str4;
        this.appKey = str5;
    }
}
