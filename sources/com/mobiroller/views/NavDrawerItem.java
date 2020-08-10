package com.mobiroller.views;

import android.graphics.drawable.Drawable;

public class NavDrawerItem {
    private String count = "0";
    private int drawable;
    private Drawable icon;
    private String imageUrl;
    private boolean isCounterVisible = false;
    public int textColor;
    private String title;

    public NavDrawerItem() {
    }

    public NavDrawerItem(String str, Drawable drawable2) {
        this.title = str;
        this.icon = drawable2;
    }

    public NavDrawerItem(String str, String str2) {
        this.title = str;
        this.imageUrl = str2;
    }

    public NavDrawerItem(String str, String str2, int i) {
        this.title = str;
        this.imageUrl = str2;
        this.textColor = i;
    }

    public NavDrawerItem(String str, int i) {
        this.title = str;
        this.drawable = i;
    }

    public NavDrawerItem(String str, Drawable drawable2, boolean z, String str2) {
        this.title = str;
        this.icon = drawable2;
        this.isCounterVisible = z;
        this.count = str2;
    }

    public int getDrawable() {
        return this.drawable;
    }

    public void setDrawable(int i) {
        this.drawable = i;
    }

    public boolean isCounterVisible() {
        return this.isCounterVisible;
    }

    public void setCounterVisible(boolean z) {
        this.isCounterVisible = z;
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getCount() {
        return this.count;
    }

    public boolean getCounterVisibility() {
        return this.isCounterVisible;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setIcon(Drawable drawable2) {
        this.icon = drawable2;
    }

    public void setCount(String str) {
        this.count = str;
    }

    public void setCounterVisibility(boolean z) {
        this.isCounterVisible = z;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }
}
