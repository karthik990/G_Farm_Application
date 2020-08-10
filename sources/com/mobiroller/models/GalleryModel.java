package com.mobiroller.models;

import java.io.File;
import java.io.Serializable;

public class GalleryModel implements Serializable {
    private String URL;
    private String caption;
    private File file;

    public GalleryModel(File file2) {
        this.file = file2;
    }

    public GalleryModel(String str, String str2) {
        this.URL = str;
        this.caption = str2;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public String getURL() {
        return this.URL;
    }

    public void setURL(String str) {
        this.URL = str;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String str) {
        this.caption = str;
    }
}
