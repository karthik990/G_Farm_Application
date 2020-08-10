package com.google.firebase.database.android;

import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.internal.InternalTokenResult;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class AndroidAuthTokenProvider$1$$Lambda$4 implements Runnable {
    private final TokenChangeListener arg$1;
    private final InternalTokenResult arg$2;

    private AndroidAuthTokenProvider$1$$Lambda$4(TokenChangeListener tokenChangeListener, InternalTokenResult internalTokenResult) {
        this.arg$1 = tokenChangeListener;
        this.arg$2 = internalTokenResult;
    }

    public static Runnable lambdaFactory$(TokenChangeListener tokenChangeListener, InternalTokenResult internalTokenResult) {
        return new AndroidAuthTokenProvider$1$$Lambda$4(tokenChangeListener, internalTokenResult);
    }

    public void run() {
        this.arg$1.onTokenChange(this.arg$2.getToken());
    }
}
