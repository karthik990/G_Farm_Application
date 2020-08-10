package com.google.firebase.remoteconfig;

public class FirebaseRemoteConfigClientException extends FirebaseRemoteConfigException {
    FirebaseRemoteConfigClientException(String str) {
        super(str);
    }

    FirebaseRemoteConfigClientException(String str, Throwable th) {
        super(str, th);
    }
}
