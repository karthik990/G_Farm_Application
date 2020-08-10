package com.google.firebase.auth;

public abstract class OAuthCredential extends AuthCredential {
    public abstract String getAccessToken();

    public abstract String getIdToken();
}
