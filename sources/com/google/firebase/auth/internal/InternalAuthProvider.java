package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.internal.InternalTokenProvider;

public interface InternalAuthProvider extends InternalTokenProvider {
    void addIdTokenListener(IdTokenListener idTokenListener);

    Task<GetTokenResult> getAccessToken(boolean z);

    String getUid();

    void removeIdTokenListener(IdTokenListener idTokenListener);
}
