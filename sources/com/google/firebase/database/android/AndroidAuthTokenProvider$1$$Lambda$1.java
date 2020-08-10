package com.google.firebase.database.android;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.core.AuthTokenProvider.GetTokenCompletionListener;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
final /* synthetic */ class AndroidAuthTokenProvider$1$$Lambda$1 implements OnSuccessListener {
    private final GetTokenCompletionListener arg$1;

    private AndroidAuthTokenProvider$1$$Lambda$1(GetTokenCompletionListener getTokenCompletionListener) {
        this.arg$1 = getTokenCompletionListener;
    }

    public static OnSuccessListener lambdaFactory$(GetTokenCompletionListener getTokenCompletionListener) {
        return new AndroidAuthTokenProvider$1$$Lambda$1(getTokenCompletionListener);
    }

    public void onSuccess(Object obj) {
        this.arg$1.onSuccess(((GetTokenResult) obj).getToken());
    }
}
