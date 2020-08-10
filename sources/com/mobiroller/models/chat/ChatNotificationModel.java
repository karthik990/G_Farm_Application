package com.mobiroller.models.chat;

import java.io.Serializable;

public class ChatNotificationModel implements Serializable {
    private String firebaseToken;
    private String receiver;
    private String receiverUid;
    private String screenId;
    private String senderUid;

    public String getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String str) {
        this.receiver = str;
    }

    public String getReceiverUid() {
        return this.receiverUid;
    }

    public void setReceiverUid(String str) {
        this.receiverUid = str;
    }

    public String getSenderUid() {
        return this.senderUid;
    }

    public void setSenderUid(String str) {
        this.senderUid = str;
    }

    public String getFirebaseToken() {
        return this.firebaseToken;
    }

    public void setFirebaseToken(String str) {
        this.firebaseToken = str;
    }

    public String getScreenId() {
        return this.screenId;
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }
}
