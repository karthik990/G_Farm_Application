package com.mobiroller.models.chat;

import java.io.Serializable;

public class GroupChatUserModel implements Serializable {
    private String firebaseToken;
    private String uid;

    public GroupChatUserModel(String str, String str2) {
        this.firebaseToken = str;
        this.uid = str2;
    }

    public String getFirebaseToken() {
        return this.firebaseToken;
    }

    public void setFirebaseToken(String str) {
        this.firebaseToken = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }
}
