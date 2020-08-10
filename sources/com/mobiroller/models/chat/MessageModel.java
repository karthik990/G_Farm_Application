package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import java.util.Date;

public class MessageModel implements IMessage {
    @Exclude
    public AuthorModel author;
    @PropertyName("im")
    public boolean isImage;
    @PropertyName("it")
    public boolean isText;
    @Exclude
    public String key;
    @PropertyName("m")
    public String message;
    @PropertyName("ru")
    public String receiverUid;
    @PropertyName("su")
    public String senderUid;
    @PropertyName("t")
    public Object timestamp;

    @Exclude
    public boolean isText() {
        return this.isText;
    }

    @Exclude
    public void setText(boolean z) {
        this.isText = z;
    }

    @Exclude
    public AuthorModel getAuthor() {
        return this.author;
    }

    public MessageModel() {
    }

    public MessageModel(String str, String str2, String str3, Object obj, boolean z) {
        this.senderUid = str;
        this.receiverUid = str2;
        this.message = str3;
        this.timestamp = obj;
        this.isImage = z;
    }

    @Exclude
    public void setAuthor(AuthorModel authorModel) {
        this.author = authorModel;
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
    public String getMessage() {
        return this.message;
    }

    @Exclude
    public void setMessage(String str) {
        this.message = str;
    }

    @Exclude
    public Object getTimestamp() {
        return this.timestamp;
    }

    @Exclude
    public void setTimestamp(Object obj) {
        this.timestamp = obj;
    }

    @Exclude
    public boolean isImage() {
        return this.isImage;
    }

    @Exclude
    public void setImage(boolean z) {
        this.isImage = z;
    }

    @Exclude
    public String getId() {
        return this.key;
    }

    @Exclude
    public String getText() {
        return this.message;
    }

    @Exclude
    public IUser getUser() {
        return this.author;
    }

    @Exclude
    public Date getCreatedAt() {
        return new Date(((Long) this.timestamp).longValue());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MessageModel{senderUid='");
        sb.append(this.senderUid);
        sb.append('\'');
        sb.append(", receiverUid='");
        sb.append(this.receiverUid);
        sb.append('\'');
        sb.append(", message='");
        sb.append(this.message);
        sb.append('\'');
        sb.append(", timestamp=");
        sb.append(this.timestamp);
        sb.append(", isImage=");
        sb.append(this.isImage);
        sb.append(", isText=");
        sb.append(this.isText);
        sb.append(", key='");
        sb.append(this.key);
        sb.append('\'');
        sb.append(", author=");
        sb.append(this.author);
        sb.append('}');
        return sb.toString();
    }
}
