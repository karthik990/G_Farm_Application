package com.google.firebase.database.android;

import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class AndroidAuthTokenProvider$2$$Lambda$1 implements Runnable {
    private final TokenChangeListener arg$1;

    private AndroidAuthTokenProvider$2$$Lambda$1(TokenChangeListener tokenChangeListener) {
        this.arg$1 = tokenChangeListener;
    }

    public static Runnable lambdaFactory$(TokenChangeListener tokenChangeListener) {
        return new AndroidAuthTokenProvider$2$$Lambda$1(tokenChangeListener);
    }

    public void run() {
        this.arg$1.onTokenChange(null);
    }
}
