package com.mobiroller.jcplayer.JcPlayerExceptions;

public class AudioAssetsInvalidException extends Exception {
    public AudioAssetsInvalidException(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The file name is not a valid Assets file: ");
        sb.append(str);
        super(sb.toString());
    }
}
