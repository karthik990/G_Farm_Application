package com.stfalcon.chatkit.commons.models;

public interface MessageContentType extends IMessage {

    public interface Image extends IMessage {
        String getImageUrl();
    }
}
