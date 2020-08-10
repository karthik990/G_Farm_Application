package com.braintreepayments.api.exceptions;

public class GoogleApiClientException extends Exception {
    private int mErrorCode;
    private ErrorType mErrorType;

    public enum ErrorType {
        NotAttachedToActivity,
        ConnectionSuspended,
        ConnectionFailed
    }

    public GoogleApiClientException(ErrorType errorType, int i) {
        this.mErrorType = errorType;
        this.mErrorCode = i;
    }

    public ErrorType getErrorType() {
        return this.mErrorType;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getMessage() {
        return toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getErrorType().name());
        sb.append(": ");
        sb.append(getErrorCode());
        return sb.toString();
    }
}
