package com.paypal.android.sdk.onetouch.core.exception;

public class ResponseParsingException extends Exception {
    public ResponseParsingException(String str) {
        super(str);
    }

    public ResponseParsingException(Throwable th) {
        super(th);
    }
}
