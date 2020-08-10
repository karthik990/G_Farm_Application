package com.mobiroller.models;

import java.io.Serializable;

public class Audio implements Serializable {
    private String data;
    private boolean isSelected = false;
    private String title;

    public Audio(String str, String str2) {
        this.data = str;
        this.title = str2;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
