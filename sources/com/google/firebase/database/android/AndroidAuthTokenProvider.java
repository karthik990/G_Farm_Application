package com.google.firebase.database.android;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider.GetTokenCompletionListener;
import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class AndroidAuthTokenProvider implements AuthTokenProvider {
    public static AuthTokenProvider forAuthenticatedAccess(final InternalAuthProvider internalAuthProvider) {
        return new AuthTokenProvider() {
            public void removeTokenChangeListener(TokenChangeListener tokenChangeListener) {
            }

            public void getToken(boolean z, GetTokenCompletionListener getTokenCompletionListener) {
                InternalAuthProvider.this.getAccessToken(z).addOnSuccessListener(AndroidAuthTokenProvider$1$$Lambda$1.lambdaFactory$(getTokenCompletionListener)).addOnFailureListener(AndroidAuthTokenProvider$1$$Lambda$2.lambdaFactory$(getTokenCompletionListener));
            }

            static /* synthetic */ void lambda$getToken$1(GetTokenCompletionListener getTokenCompletionListener, Exception exc) {
                if (AndroidAuthTokenProvider.isUnauthenticatedUsage(exc)) {
                    getTokenCompletionListener.onSuccess(null);
                } else {
                    getTokenCompletionListener.onError(exc.getMessage());
                }
            }

            public void addTokenChangeListener(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
                InternalAuthProvider.this.addIdTokenListener(AndroidAuthTokenProvider$1$$Lambda$3.lambdaFactory$(executorService, tokenChangeListener));
            }
        };
    }

    public static AuthTokenProvider forUnauthenticatedAccess() {
        return new AuthTokenProvider() {
            public void removeTokenChangeListener(TokenChangeListener tokenChangeListener) {
            }

            public void getToken(boolean z, GetTokenCompletionListener getTokenCompletionListener) {
                getTokenCompletionListener.onSuccess(null);
            }

            public void addTokenChangeListener(ExecutorService executorService, TokenChangeListener tokenChangeListener) {
                executorService.execute(AndroidAuthTokenProvider$2$$Lambda$1.lambdaFactory$(tokenChangeListener));
            }
        };
    }

    /* access modifiers changed from: private */
    public static boolean isUnauthenticatedUsage(Exception exc) {
        return (exc instanceof FirebaseApiNotAvailableException) || (exc instanceof FirebaseNoSignedInUserException);
    }
}
