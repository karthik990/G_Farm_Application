package com.mobiroller.jcplayer.JcPlayerExceptions;

public class AudioUrlInvalidException extends IllegalStateException {
    public AudioUrlInvalidException(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The url does not appear valid: ");
        sb.append(str);
        super(sb.toString());
    }
}
