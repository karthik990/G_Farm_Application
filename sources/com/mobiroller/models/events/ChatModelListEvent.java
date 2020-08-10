package com.mobiroller.models.events;

import com.mobiroller.models.chat.ChatModel;
import java.util.List;

public class ChatModelListEvent {
    public List<ChatModel> chatModels;

    public ChatModelListEvent(List<ChatModel> list) {
        this.chatModels = list;
    }

    public List<ChatModel> getChatModels() {
        return this.chatModels;
    }

    public void setChatModels(List<ChatModel> list) {
        this.chatModels = list;
    }
}
