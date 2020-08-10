package com.mobiroller.models.events;

public class ChatIsReadEvent {
    private boolean isRead;
    public String screenId;
    private String uid;

    public ChatIsReadEvent(String str, boolean z, String str2) {
        this.uid = str;
        this.isRead = z;
        this.screenId = str2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public void setRead(boolean z) {
        this.isRead = z;
    }
}
