package com.mobiroller.models.chat;

public class ChatUserProfileEvent {
    private ChatUserDetails userModel;

    public ChatUserProfileEvent(ChatUserDetails chatUserDetails) {
        this.userModel = chatUserDetails;
    }

    public ChatUserDetails getUserModel() {
        return this.userModel;
    }

    public void setUserModel(ChatUserDetails chatUserDetails) {
        this.userModel = chatUserDetails;
    }
}
