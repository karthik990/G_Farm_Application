package com.mobiroller.models.events;

import android.net.Uri;

public class FormImageEvent {

    /* renamed from: id */
    int f2185id;
    Uri imagePath;

    public FormImageEvent(int i, Uri uri) {
        this.f2185id = i;
        this.imagePath = uri;
    }

    public int getId() {
        return this.f2185id;
    }

    public Uri getImagePath() {
        return this.imagePath;
    }
}
