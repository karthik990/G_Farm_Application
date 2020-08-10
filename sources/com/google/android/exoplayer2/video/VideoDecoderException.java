package com.google.android.exoplayer2.video;

public class VideoDecoderException extends Exception {
    public VideoDecoderException(String str) {
        super(str);
    }

    public VideoDecoderException(String str, Throwable th) {
        super(str, th);
    }
}
