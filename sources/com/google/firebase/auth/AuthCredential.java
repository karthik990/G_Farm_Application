package com.google.firebase.auth;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public abstract class AuthCredential extends AbstractSafeParcelable {
    AuthCredential() {
    }

    public abstract String getProvider();

    public abstract String getSignInMethod();
}
