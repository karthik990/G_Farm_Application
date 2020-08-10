package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.internal.Preconditions;

public final class zzed {
    private String zzht;

    public zzed(String str) {
        this.zzht = Preconditions.checkNotEmpty(str);
    }

    public final zzee zzef() {
        return new zzee(this.zzht, null);
    }
}
