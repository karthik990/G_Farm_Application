package com.mobiroller.jcplayer.JcPlayerExceptions;

public class AudioListNullPointerException extends NullPointerException {
    public AudioListNullPointerException() {
        super("The playlist is empty or null");
    }
}
