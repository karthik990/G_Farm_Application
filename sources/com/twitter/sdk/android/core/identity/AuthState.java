package com.twitter.sdk.android.core.identity;

import android.app.Activity;
import com.twitter.sdk.android.core.Twitter;
import java.util.concurrent.atomic.AtomicReference;

class AuthState {
    final AtomicReference<AuthHandler> authHandlerRef = new AtomicReference<>(null);

    AuthState() {
    }

    public boolean beginAuthorize(Activity activity, AuthHandler authHandler) {
        String str = "Twitter";
        if (isAuthorizeInProgress()) {
            Twitter.getLogger().mo20830w(str, "Authorize already in progress");
        } else if (authHandler.authorize(activity)) {
            boolean compareAndSet = this.authHandlerRef.compareAndSet(null, authHandler);
            if (compareAndSet) {
                return compareAndSet;
            }
            Twitter.getLogger().mo20830w(str, "Failed to update authHandler, authorize already in progress.");
            return compareAndSet;
        }
        return false;
    }

    public void endAuthorize() {
        this.authHandlerRef.set(null);
    }

    public boolean isAuthorizeInProgress() {
        return this.authHandlerRef.get() != null;
    }

    public AuthHandler getAuthHandler() {
        return (AuthHandler) this.authHandlerRef.get();
    }
}
