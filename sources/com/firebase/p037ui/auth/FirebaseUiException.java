package com.firebase.p037ui.auth;

/* renamed from: com.firebase.ui.auth.FirebaseUiException */
public class FirebaseUiException extends Exception {
    private final int mErrorCode;

    public FirebaseUiException(int i) {
        this(i, ErrorCodes.toFriendlyMessage(i));
    }

    public FirebaseUiException(int i, String str) {
        super(str);
        this.mErrorCode = i;
    }

    public FirebaseUiException(int i, Throwable th) {
        this(i, ErrorCodes.toFriendlyMessage(i), th);
    }

    public FirebaseUiException(int i, String str, Throwable th) {
        super(str, th);
        this.mErrorCode = i;
    }

    public final int getErrorCode() {
        return this.mErrorCode;
    }
}
