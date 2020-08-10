package com.firebase.p037ui.auth.util;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.CredentialsOptions.Builder;

/* renamed from: com.firebase.ui.auth.util.GoogleApiUtils */
public final class GoogleApiUtils {
    private GoogleApiUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static CredentialsClient getCredentialsClient(Context context) {
        CredentialsOptions build = new Builder().forceEnableSaveDialog().zzc();
        if (context instanceof Activity) {
            return Credentials.getClient((Activity) context, build);
        }
        return Credentials.getClient(context, build);
    }
}
