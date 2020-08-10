package com.google.firebase.auth.internal;

import android.content.Context;
import com.google.android.gms.flags.Flag;
import com.google.android.gms.flags.FlagRegistry;
import com.google.android.gms.flags.Singletons;

public final class zzal {
    public static final Flag<Boolean> zzup = Flag.define(0, "firebase_auth_proactive_token_refresh_enabled", Boolean.valueOf(true));

    public static final void initialize(Context context) {
        Singletons.flagRegistry();
        FlagRegistry.initialize(context);
    }
}
