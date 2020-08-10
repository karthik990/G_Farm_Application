package com.mobiroller.jcplayer.JcPlayerExceptions;

public class AudioRawInvalidException extends Exception {
    public AudioRawInvalidException(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Not a valid raw file id: ");
        sb.append(str);
        super(sb.toString());
    }
}
