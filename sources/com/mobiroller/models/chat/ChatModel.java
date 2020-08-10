package com.mobiroller.models.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class ChatModel implements Serializable, Comparable<Object> {
    @PropertyName("a")
    public boolean archived;
    @PropertyName("im")
    public boolean isMessage;
    @Exclude
    private String isOnline;
    @Exclude
    public boolean isRead;
    @PropertyName("irr")
    public boolean isReceiverRead;
    @PropertyName("isr")
    public boolean isSenderRead;
    @PropertyName("it")
    public boolean isText;
    @PropertyName("lm")
    public String lastMessage;
    @Exclude
    public String receiverChatRoleId;
    @Exclude
    public String receiverImageUrl;
    @Exclude
    public String receiverName;
    @PropertyName("ru")
    public String receiverUid;
    @Exclude
    public String screenId;
    @Exclude
    public String senderChatRoleId;
    @Exclude
    public String senderImageUrl;
    @Exclude
    public String senderName;
    @PropertyName("su")
    public String senderUid;
    @PropertyName("tm")
    public Object timeStamp;

    @Exclude
    public boolean isSenderRead() {
        return true;
    }

    public boolean getIsText() {
        return this.isText;
    }

    public void setIsText(boolean z) {
        this.isText = z;
    }

    @Exclude
    public boolean isArchived() {
        return this.archived;
    }

    @Exclude
    public void setArchived(boolean z) {
        this.archived = z;
    }

    @Exclude
    public boolean isOnline() {
        String str = this.isOnline;
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase("n");
    }

    @Exclude
    public void setOnline(String str) {
        this.isOnline = str;
    }

    @Exclude
    public void setOnline(boolean z) {
        if (z) {
            this.isOnline = "n";
        } else {
            this.isOnline = "f";
        }
    }

    @Exclude
    public boolean isReceiverRead() {
        return this.isReceiverRead;
    }

    @Exclude
    public void setReceiverRead(boolean z) {
        this.isReceiverRead = z;
    }

    @Exclude
    public void setSenderRead(boolean z) {
        this.isSenderRead = z;
    }

    public ChatModel() {
    }

    public ChatModel(MessageModel messageModel) {
        this.senderUid = messageModel.getSenderUid();
        this.receiverUid = messageModel.getReceiverUid();
        this.lastMessage = messageModel.getMessage();
        this.isMessage = !messageModel.isImage();
        this.isText = messageModel.isText();
        this.timeStamp = messageModel.getTimestamp();
    }

    @Exclude
    public String getSenderUid() {
        return this.senderUid;
    }

    @Exclude
    public void setSenderUid(String str) {
        this.senderUid = str;
    }

    @Exclude
    public String getReceiverUid() {
        return this.receiverUid;
    }

    @Exclude
    public void setReceiverUid(String str) {
        this.receiverUid = str;
    }

    @Exclude
    public String getSenderName() {
        return this.senderName;
    }

    @Exclude
    public void setSenderName(String str) {
        this.senderName = str;
    }

    @Exclude
    public String getReceiverName() {
        return this.receiverName;
    }

    @Exclude
    public void setReceiverName(String str) {
        this.receiverName = str;
    }

    @Exclude
    public String getSenderImageUrl() {
        return this.senderImageUrl;
    }

    @Exclude
    public void setSenderImageUrl(String str) {
        this.senderImageUrl = str;
    }

    @Exclude
    public String getReceiverImageUrl() {
        return this.receiverImageUrl;
    }

    @Exclude
    public void setReceiverImageUrl(String str) {
        this.receiverImageUrl = str;
    }

    @Exclude
    public String getLastMessage() {
        return this.lastMessage;
    }

    @Exclude
    public void setLastMessage(String str) {
        this.lastMessage = str;
    }

    @Exclude
    public boolean isMessage() {
        return this.isMessage;
    }

    @Exclude
    public void setMessage(boolean z) {
        this.isMessage = z;
    }

    @Exclude
    public Object getTimeStamp() {
        return this.timeStamp;
    }

    @Exclude
    public void setTimeStamp(Object obj) {
        this.timeStamp = obj;
    }

    @Exclude
    public boolean isSender() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(this.senderUid);
    }

    @Exclude
    public String getRealReceiverUid() {
        if (isSender()) {
            return this.receiverUid;
        }
        return this.senderUid;
    }

    @Exclude
    public void setRealRecevierNameAndImage(String str, String str2) {
        if (isSender()) {
            this.receiverName = str;
            this.receiverImageUrl = str2;
            return;
        }
        this.senderName = str;
        this.senderImageUrl = str2;
    }

    @Exclude
    public String getRealRecevierName() {
        if (isSender()) {
            return this.receiverName;
        }
        return this.senderName;
    }

    @Exclude
    public String getRealRecevierImageUrl() {
        if (isSender()) {
            return this.receiverImageUrl;
        }
        return this.senderImageUrl;
    }

    @Exclude
    public int compareTo(Object obj) {
        if (!(obj instanceof ChatModel) || ((Long) getTimeStamp()).longValue() > ((Long) ((ChatModel) obj).getTimeStamp()).longValue()) {
            return -1;
        }
        return 1;
    }

    @Exclude
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChatModel{ senderUid='");
        sb.append(this.senderUid);
        sb.append('\'');
        sb.append(", receiverUid='");
        sb.append(this.receiverUid);
        sb.append('\'');
        sb.append(", senderName='");
        sb.append(this.senderName);
        sb.append('\'');
        sb.append(", receiverName='");
        sb.append(this.receiverName);
        sb.append('\'');
        sb.append(", senderImageUrl='");
        sb.append(this.senderImageUrl);
        sb.append('\'');
        sb.append(", receiverImageUrl='");
        sb.append(this.receiverImageUrl);
        sb.append('\'');
        sb.append(", isReceiverRead=");
        sb.append(this.isReceiverRead);
        sb.append(", isSenderRead=");
        sb.append(this.isSenderRead);
        sb.append(", lastMessage='");
        sb.append(this.lastMessage);
        sb.append('\'');
        sb.append(", isMessage=");
        sb.append(this.isMessage);
        sb.append(", timeStamp=");
        sb.append(this.timeStamp);
        sb.append(", archived=");
        sb.append(this.archived);
        sb.append(", isOnline=");
        sb.append(this.isOnline);
        sb.append(", isRead=");
        sb.append(this.isRead);
        sb.append('}');
        return sb.toString();
    }
}
