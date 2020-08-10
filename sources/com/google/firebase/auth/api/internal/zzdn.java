package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.firebase.FirebaseExceptionMapper;
import java.util.Collections;
import java.util.concurrent.Callable;

final class zzdn implements Callable<zzaj<zzee>> {
    private final Context zzml;
    private final zzee zzmm;

    public zzdn(zzee zzee, Context context) {
        this.zzmm = zzee;
        this.zzml = context;
    }

    private final GoogleApi<zzee> zza(boolean z, Context context) {
        zzee zzee = (zzee) this.zzmm.clone();
        zzee.zzmc = z;
        return new zzak(context, zzec.zzoz, zzee, new FirebaseExceptionMapper());
    }

    public final /* synthetic */ Object call() throws Exception {
        int localVersion = DynamiteModule.getLocalVersion(this.zzml, "com.google.firebase.auth");
        int i = 1;
        GoogleApi googleApi = null;
        GoogleApi zza = localVersion != 0 ? zza(true, this.zzml) : null;
        if (localVersion != 0) {
            int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.zzml, GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                i = DynamiteModule.getRemoteVersion(this.zzml, "com.google.android.gms.firebase_auth");
            } else {
                i = 0;
            }
        }
        if (i != 0) {
            googleApi = zza(false, this.zzml);
        }
        return new zzaj(googleApi, zza, new zzal(i, localVersion, Collections.emptyMap()));
    }
}
