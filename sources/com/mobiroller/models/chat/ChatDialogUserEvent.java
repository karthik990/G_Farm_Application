package com.mobiroller.models.chat;

public class ChatDialogUserEvent {
    private ChatUserModel userModel;

    public ChatDialogUserEvent(ChatUserModel chatUserModel) {
        this.userModel = chatUserModel;
    }

    public ChatUserModel getUserModel() {
        return this.userModel;
    }

    public void setUserModel(ChatUserModel chatUserModel) {
        this.userModel = chatUserModel;
    }
}
