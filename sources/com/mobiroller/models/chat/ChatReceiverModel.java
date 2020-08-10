package com.mobiroller.models.chat;

public class ChatReceiverModel {
    private String imageUrl;
    private boolean isRead;
    private String name;
    private String uid;

    public ChatReceiverModel(String str, String str2, String str3, boolean z) {
        this.name = str;
        this.uid = str2;
        this.imageUrl = str3;
        this.isRead = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public void setRead(boolean z) {
        this.isRead = z;
    }
}
