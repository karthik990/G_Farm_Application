package com.google.firebase.remoteconfig;

public class FirebaseRemoteConfigFetchException extends FirebaseRemoteConfigClientException {
    public FirebaseRemoteConfigFetchException() {
        super("There was a fetch error in the FRC SDK.");
    }

    public FirebaseRemoteConfigFetchException(String str) {
        super(str);
    }

    public FirebaseRemoteConfigFetchException(String str, Throwable th) {
        super(str, th);
    }
}
