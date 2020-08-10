package com.mobiroller.models.events;

import com.mobiroller.models.chat.ChatUserDetails;

public class ChatAccountEvent {
    private ChatUserDetails userModel;

    public ChatAccountEvent(ChatUserDetails chatUserDetails) {
        this.userModel = chatUserDetails;
    }

    public ChatUserDetails getUserModel() {
        return this.userModel;
    }

    public void setUserModel(ChatUserDetails chatUserDetails) {
        this.userModel = chatUserDetails;
    }

    public boolean isBanned() {
        return this.userModel.getUserInfo().isBanned;
    }
}
