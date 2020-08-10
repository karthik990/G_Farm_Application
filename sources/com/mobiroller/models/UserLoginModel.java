package com.mobiroller.models;

import java.util.List;

public class UserLoginModel {
    private int chatPermissionID;
    private int chatRoleID;
    private String profileImageURL;
    private List<UserProfileItem> profileValues;
    private String roleID;
    private boolean status;
    private String statusMessage;

    public List<UserProfileItem> getProfileValues() {
        return this.profileValues;
    }

    public void setProfileValues(List<UserProfileItem> list) {
        this.profileValues = list;
    }

    public String getProfileImageURL() {
        String str = this.profileImageURL;
        return str == null ? "" : str;
    }

    public int getChatRoleID() {
        return this.chatRoleID;
    }

    public void setChatRoleID(int i) {
        this.chatRoleID = i;
    }

    public int getChatPermissionID() {
        return this.chatPermissionID;
    }

    public void setChatPermissionID(int i) {
        this.chatPermissionID = i;
    }

    public void setProfileImageURL(String str) {
        this.profileImageURL = str;
    }

    public List<UserProfileItem> getUserProfileItems() {
        return this.profileValues;
    }

    public void setUserProfileItems(List<UserProfileItem> list) {
        this.profileValues = list;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean z) {
        this.status = z;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String str) {
        this.statusMessage = str;
    }

    public String getRoleID() {
        return this.roleID;
    }

    public void setRoleID(String str) {
        this.roleID = str;
    }
}
