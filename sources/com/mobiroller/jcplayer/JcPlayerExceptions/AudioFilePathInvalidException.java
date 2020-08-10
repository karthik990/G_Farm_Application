package com.mobiroller.jcplayer.JcPlayerExceptions;

public class AudioFilePathInvalidException extends Exception {
    public AudioFilePathInvalidException(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The file path is not a valid path: ");
        sb.append(str);
        sb.append("\nHave you add File Access Permission?");
        super(sb.toString());
    }
}
