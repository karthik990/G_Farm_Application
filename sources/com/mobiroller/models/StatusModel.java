package com.mobiroller.models;

public class StatusModel {
    private String profileImageURL;
    private String roleID;
    private boolean status;
    private String statusMessage;

    public String getProfileImageURL() {
        return this.profileImageURL;
    }

    public void setProfileImageURL(String str) {
        this.profileImageURL = str;
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
