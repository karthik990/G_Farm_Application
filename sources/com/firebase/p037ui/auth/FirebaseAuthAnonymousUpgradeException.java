package com.firebase.p037ui.auth;

/* renamed from: com.firebase.ui.auth.FirebaseAuthAnonymousUpgradeException */
public class FirebaseAuthAnonymousUpgradeException extends Exception {
    private IdpResponse mResponse;

    public FirebaseAuthAnonymousUpgradeException(int i, IdpResponse idpResponse) {
        super(ErrorCodes.toFriendlyMessage(i));
        this.mResponse = idpResponse;
    }

    public IdpResponse getResponse() {
        return this.mResponse;
    }
}
