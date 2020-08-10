package com.firebase.p037ui.auth.data.remote;

import com.firebase.p037ui.auth.AuthUI.IdpConfig;
import com.firebase.p037ui.auth.viewmodel.ProviderSignInBase;

/* renamed from: com.firebase.ui.auth.data.remote.GitHubSignInHandlerBridge */
public final class GitHubSignInHandlerBridge {
    public static final Class<ProviderSignInBase<IdpConfig>> HANDLER_CLASS;

    static {
        try {
            HANDLER_CLASS = Class.forName("com.firebase.ui.auth.data.remote.GitHubSignInHandler");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Check for availability with ProviderAvailability first.", e);
        }
    }

    private GitHubSignInHandlerBridge() {
        throw new AssertionError("No instance for you!");
    }
}
