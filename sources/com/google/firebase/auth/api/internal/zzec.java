package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ClientKey;

public final class zzec {
    private static final ClientKey<zzdp> CLIENT_KEY = new ClientKey<>();
    private static final AbstractClientBuilder<zzdp, zzee> zzoy = new zzeb();
    public static final Api<zzee> zzoz = new Api<>("InternalFirebaseAuth.FIREBASE_AUTH_API", zzoy, CLIENT_KEY);

    public static zzap zza(Context context, zzee zzee) {
        return new zzap(context, zzee);
    }
}
