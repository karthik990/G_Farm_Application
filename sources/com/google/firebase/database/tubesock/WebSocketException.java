package com.google.firebase.database.tubesock;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class WebSocketException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public WebSocketException(String str) {
        super(str);
    }

    public WebSocketException(String str, Throwable th) {
        super(str, th);
    }
}
