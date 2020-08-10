package com.mobiroller.models.chat;

import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class ChatInfoModel implements Serializable {
    @PropertyName("e")
    public String email;
    @PropertyName("i")
    public String imageUrl;
    @PropertyName("ib")
    public boolean isBanned;
    @PropertyName("o")
    public String isOnline;
    @PropertyName("n")
    public String name;
    @PropertyName("s")
    public String status;
    @PropertyName("uid")
    public String uid;
    @PropertyName("un")
    public String username;

    @PropertyName("un")
    public String getUsername() {
        return this.username;
    }

    @PropertyName("un")
    public void setUsername(String str) {
        this.username = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChatInfoModel{isOnline=");
        sb.append(this.isOnline);
        sb.append(", isBanned=");
        sb.append(this.isBanned);
        sb.append(", username='");
        sb.append(getUsername());
        sb.append('\'');
        sb.append(", uid='");
        sb.append(this.uid);
        sb.append('\'');
        sb.append(", email='");
        sb.append(this.email);
        sb.append('\'');
        sb.append(", status='");
        sb.append(this.status);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", imageUrl='");
        sb.append(this.imageUrl);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
