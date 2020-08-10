package com.google.firebase.auth;

public class GoogleAuthProvider {
    public static final String GOOGLE_SIGN_IN_METHOD = "google.com";
    public static final String PROVIDER_ID = "google.com";

    private GoogleAuthProvider() {
    }

    public static AuthCredential getCredential(String str, String str2) {
        return new GoogleAuthCredential(str, str2);
    }
}
