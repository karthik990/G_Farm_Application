package com.mobiroller.models.chat;

public class MessagingModel {
    String receiverUid;

    public MessagingModel(String str) {
        this.receiverUid = str;
    }

    public String getReceiverUid() {
        return this.receiverUid;
    }

    public void setReceiverUid(String str) {
        this.receiverUid = str;
    }
}
