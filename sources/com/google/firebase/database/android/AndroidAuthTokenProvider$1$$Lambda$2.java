package com.google.firebase.database.android;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.core.AuthTokenProvider.GetTokenCompletionListener;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class AndroidAuthTokenProvider$1$$Lambda$2 implements OnFailureListener {
    private final GetTokenCompletionListener arg$1;

    private AndroidAuthTokenProvider$1$$Lambda$2(GetTokenCompletionListener getTokenCompletionListener) {
        this.arg$1 = getTokenCompletionListener;
    }

    public static OnFailureListener lambdaFactory$(GetTokenCompletionListener getTokenCompletionListener) {
        return new AndroidAuthTokenProvider$1$$Lambda$2(getTokenCompletionListener);
    }

    public void onFailure(Exception exc) {
        C35141.lambda$getToken$1(this.arg$1, exc);
    }
}
