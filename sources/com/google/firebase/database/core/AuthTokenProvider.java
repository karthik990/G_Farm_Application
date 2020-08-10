package com.google.firebase.database.core;

import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface AuthTokenProvider {

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface GetTokenCompletionListener {
        void onError(String str);

        void onSuccess(String str);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TokenChangeListener {
        void onTokenChange();

        void onTokenChange(String str);
    }

    void addTokenChangeListener(ExecutorService executorService, TokenChangeListener tokenChangeListener);

    void getToken(boolean z, GetTokenCompletionListener getTokenCompletionListener);

    void removeTokenChangeListener(TokenChangeListener tokenChangeListener);
}
