package com.stfalcon.chatkit.commons.models;

import com.stfalcon.chatkit.commons.models.IMessage;
import java.util.List;

public interface IDialog<MESSAGE extends IMessage> {
    String getDialogName();

    String getDialogPhoto();

    String getId();

    MESSAGE getLastMessage();

    int getUnreadCount();

    List<? extends IUser> getUsers();

    void setLastMessage(MESSAGE message);
}
