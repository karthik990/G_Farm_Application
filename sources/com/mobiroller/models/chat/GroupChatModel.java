package com.mobiroller.models.chat;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupChatModel implements Serializable {
    private String adminUid;
    private String chatId;
    private String groupImageUrl;
    private String groupName;
    private String lastMessage;
    private Object timestamp;
    private ArrayList<GroupChatUserModel> userList;

    public String getAdminUid() {
        return this.adminUid;
    }

    public void setAdminUid(String str) {
        this.adminUid = str;
    }

    public GroupChatModel(String str, ArrayList<GroupChatUserModel> arrayList, String str2, Object obj) {
        this.chatId = str;
        this.userList = arrayList;
        this.lastMessage = str2;
        this.timestamp = obj;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getGroupImageUrl() {
        return this.groupImageUrl;
    }

    public void setGroupImageUrl(String str) {
        this.groupImageUrl = str;
    }

    public String getChatId() {
        return this.chatId;
    }

    public void setChatId(String str) {
        this.chatId = str;
    }

    public ArrayList<GroupChatUserModel> getUserList() {
        return this.userList;
    }

    public void setUserList(ArrayList<GroupChatUserModel> arrayList) {
        this.userList = arrayList;
    }

    public String getLastMessage() {
        return this.lastMessage;
    }

    public void setLastMessage(String str) {
        this.lastMessage = str;
    }

    public Object getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Object obj) {
        this.timestamp = obj;
    }
}
