package com.google.firebase.remoteconfig;

import com.google.firebase.FirebaseException;

public class FirebaseRemoteConfigException extends FirebaseException {
    public FirebaseRemoteConfigException() {
        super("There was an error in the FRC SDK.");
    }

    FirebaseRemoteConfigException(String str) {
        super(str);
    }

    FirebaseRemoteConfigException(String str, Throwable th) {
        super(str, th);
    }
}
