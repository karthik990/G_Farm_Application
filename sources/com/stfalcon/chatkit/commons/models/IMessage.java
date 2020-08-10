package com.stfalcon.chatkit.commons.models;

import java.util.Date;

public interface IMessage {
    Date getCreatedAt();

    String getId();

    String getText();

    IUser getUser();
}
