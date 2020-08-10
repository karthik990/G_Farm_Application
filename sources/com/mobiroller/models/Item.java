package com.mobiroller.models;

import android.graphics.drawable.Drawable;
import java.io.Serializable;

public class Item implements Serializable {
    private Drawable drawable;

    /* renamed from: id */
    private long f2169id;
    private String imageUrl;
    private String title;

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public long getId() {
        return this.f2169id;
    }

    public void setId(long j) {
        this.f2169id = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public void setDrawable(Drawable drawable2) {
        this.drawable = drawable2;
    }

    public Item(long j, String str, Drawable drawable2, String str2) {
        this.f2169id = j;
        this.title = str;
        this.drawable = drawable2;
        this.imageUrl = str2;
    }

    public Item(long j, String str, String str2) {
        this.f2169id = j;
        this.title = str;
        this.imageUrl = str2;
    }
}
