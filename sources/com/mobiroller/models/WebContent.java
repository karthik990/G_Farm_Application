package com.mobiroller.models;

import java.io.Serializable;

public class WebContent implements Serializable {
    public boolean shareable;
    public String title;
    public String url;

    public WebContent(String str, String str2, boolean z) {
        this.title = str;
        this.url = str2;
        this.shareable = z;
    }
}
