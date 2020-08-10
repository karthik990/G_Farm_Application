package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import java.io.Serializable;

public class LogModel implements Serializable, Comparable {
    String authUid;
    @Exclude
    public ChatUserModel authUser;
    public String key;
    String message;
    String receiverUid;
    @Exclude
    public ChatUserModel receiverUser;
    private Object timeStamp;
    int type;
    String version = "v2";

    public LogModel() {
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public LogModel(String str, String str2, int i) {
        this.authUid = str;
        this.receiverUid = str2;
        this.type = i;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public LogModel(String str, String str2, int i, String str3) {
        this.authUid = str;
        this.receiverUid = str2;
        this.type = i;
        this.message = str3;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getAuthUid() {
        return this.authUid;
    }

    public void setAuthUid(String str) {
        this.authUid = str;
    }

    public String getReceiverUid() {
        return this.receiverUid;
    }

    public void setReceiverUid(String str) {
        this.receiverUid = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public Object getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Object obj) {
        this.timeStamp = obj;
    }

    public int compareTo(Object obj) {
        return ((Long) this.timeStamp).longValue() == ((Long) ((LogModel) obj).getTimeStamp()).longValue() ? -1 : 1;
    }
}
