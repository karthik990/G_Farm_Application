package com.paypal.android.sdk.onetouch.core.config;

public class ConfigEndpoint {
    public final String certificate;
    public final String name;
    public final String url;

    public ConfigEndpoint(String str, String str2, String str3) {
        this.name = str;
        this.url = str2;
        this.certificate = str3;
    }
}
