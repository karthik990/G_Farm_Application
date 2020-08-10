package com.google.firebase.auth.internal;

import com.google.firebase.internal.InternalTokenResult;

public interface IdTokenListener {
    void onIdTokenChanged(InternalTokenResult internalTokenResult);
}
