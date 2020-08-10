package com.mobiroller.models.chat;

import com.stfalcon.chatkit.commons.models.IUser;

public class AuthorModel implements IUser {
    String avatarUrl;

    /* renamed from: id */
    String f2173id;
    String name;

    public String getAvatar() {
        return null;
    }

    public AuthorModel(String str, String str2, String str3) {
        this.f2173id = str;
        this.name = str2;
        this.avatarUrl = str3;
    }

    public String getId() {
        return this.f2173id;
    }

    public String getName() {
        return this.name;
    }
}
