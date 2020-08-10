package com.google.firebase.auth.internal;

import com.google.firebase.auth.SignInMethodQueryResult;
import java.util.List;

public final class zzw implements SignInMethodQueryResult {
    private final List<String> zzrc;

    public zzw(List<String> list) {
        this.zzrc = list;
    }

    public final List<String> getSignInMethods() {
        return this.zzrc;
    }
}
