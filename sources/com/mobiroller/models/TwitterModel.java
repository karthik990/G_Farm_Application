package com.mobiroller.models;

import java.io.Serializable;

public class TwitterModel implements Serializable {

    /* renamed from: id */
    String f2171id;
    String text;
    TwitterUserModel user;

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getId() {
        return this.f2171id;
    }

    public void setId(String str) {
        this.f2171id = str;
    }

    public TwitterUserModel getUser() {
        return this.user;
    }

    public void setUser(TwitterUserModel twitterUserModel) {
        this.user = twitterUserModel;
    }
}
