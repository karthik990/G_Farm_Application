package com.twitter.sdk.android.core.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("h")

    /* renamed from: h */
    public final int f3655h;
    @SerializedName("image_type")
    public final String imageType;
    @SerializedName("w")

    /* renamed from: w */
    public final int f3656w;

    public Image(int i, int i2, String str) {
        this.f3656w = i;
        this.f3655h = i2;
        this.imageType = str;
    }
}
