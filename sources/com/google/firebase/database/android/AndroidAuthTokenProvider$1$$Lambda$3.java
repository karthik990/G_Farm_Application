package com.google.firebase.database.android;

import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.internal.InternalTokenResult;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class AndroidAuthTokenProvider$1$$Lambda$3 implements IdTokenListener {
    private final ExecutorService arg$1;
    private final TokenChangeListener arg$2;

    private AndroidAuthTokenProvider$1$$Lambda$3(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
        this.arg$1 = executorService;
        this.arg$2 = tokenChangeListener;
    }

    public static IdTokenListener lambdaFactory$(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
        return new AndroidAuthTokenProvider$1$$Lambda$3(executorService, tokenChangeListener);
    }

    public void onIdTokenChanged(InternalTokenResult internalTokenResult) {
        this.arg$1.execute(AndroidAuthTokenProvider$1$$Lambda$4.lambdaFactory$(this.arg$2, internalTokenResult));
    }
}
