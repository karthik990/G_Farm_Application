package com.mobiroller.models.events;

public class ProfileUpdateEvent {
    private String profileImageURL;
    private String userName;

    public ProfileUpdateEvent(String str) {
        this.userName = str;
    }

    public ProfileUpdateEvent(String str, String str2) {
        this.profileImageURL = str;
        this.userName = str2;
    }

    public String getProfileImageURL() {
        return this.profileImageURL;
    }

    public void setProfileImageURL(String str) {
        this.profileImageURL = str;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String str) {
        this.userName = str;
    }
}
