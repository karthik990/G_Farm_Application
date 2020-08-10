package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.internal.firebase_auth.zze;

public final class zzdo extends GmsClient<zzdz> implements zzdp {
    private static Logger zzjt = new Logger("FirebaseAuth", "FirebaseAuth:");
    private final Context zzml;
    private final zzee zzos;

    public zzdo(Context context, Looper looper, ClientSettings clientSettings, zzee zzee, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 112, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzml = (Context) Preconditions.checkNotNull(context);
        this.zzos = zzee;
    }

    public final int getMinApkVersion() {
        return GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.firebase.auth.api.internal.IFirebaseAuthService";
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.firebase.auth.api.gms.service.START";
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String getStartServicePackage() {
        /*
            r9 = this;
            java.lang.String r0 = "firebear.preference"
            java.lang.String r0 = com.google.firebase.auth.api.internal.zzfe.getProperty(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = "default"
            if (r1 == 0) goto L_0x000f
            r0 = r2
        L_0x000f:
            int r1 = r0.hashCode()
            java.lang.String r3 = "local"
            r4 = 1
            r5 = 103145323(0x625df6b, float:3.1197192E-35)
            r6 = -1
            r7 = 0
            if (r1 == r5) goto L_0x002b
            r8 = 1544803905(0x5c13d641, float:1.66449585E17)
            if (r1 == r8) goto L_0x0023
            goto L_0x0033
        L_0x0023:
            boolean r1 = r0.equals(r2)
            if (r1 == 0) goto L_0x0033
            r1 = 1
            goto L_0x0034
        L_0x002b:
            boolean r1 = r0.equals(r3)
            if (r1 == 0) goto L_0x0033
            r1 = 0
            goto L_0x0034
        L_0x0033:
            r1 = -1
        L_0x0034:
            if (r1 == 0) goto L_0x0039
            if (r1 == r4) goto L_0x0039
            r0 = r2
        L_0x0039:
            int r1 = r0.hashCode()
            if (r1 == r5) goto L_0x0040
            goto L_0x0047
        L_0x0040:
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0047
            r6 = 0
        L_0x0047:
            if (r6 == 0) goto L_0x0074
            com.google.android.gms.common.logging.Logger r0 = zzjt
            java.lang.Object[] r1 = new java.lang.Object[r7]
            java.lang.String r2 = "Loading module via FirebaseOptions."
            r0.mo26593i(r2, r1)
            com.google.firebase.auth.api.internal.zzee r0 = r9.zzos
            boolean r0 = r0.zzmc
            if (r0 == 0) goto L_0x0068
            com.google.android.gms.common.logging.Logger r0 = zzjt
            java.lang.Object[] r1 = new java.lang.Object[r7]
            java.lang.String r2 = "Preparing to create service connection to fallback implementation"
            r0.mo26593i(r2, r1)
            android.content.Context r0 = r9.zzml
            java.lang.String r0 = r0.getPackageName()
            return r0
        L_0x0068:
            com.google.android.gms.common.logging.Logger r0 = zzjt
            java.lang.Object[] r1 = new java.lang.Object[r7]
            java.lang.String r2 = "Preparing to create service connection to gms implementation"
            r0.mo26593i(r2, r1)
            java.lang.String r0 = "com.google.android.gms"
            return r0
        L_0x0074:
            com.google.android.gms.common.logging.Logger r0 = zzjt
            java.lang.Object[] r1 = new java.lang.Object[r7]
            java.lang.String r2 = "Loading fallback module override."
            r0.mo26593i(r2, r1)
            android.content.Context r0 = r9.zzml
            java.lang.String r0 = r0.getPackageName()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.api.internal.zzdo.getStartServicePackage():java.lang.String");
    }

    public final boolean requiresGooglePlayServices() {
        return DynamiteModule.getLocalVersion(this.zzml, "com.google.firebase.auth") == 0;
    }

    /* access modifiers changed from: protected */
    public final Bundle getGetServiceRequestExtraArgs() {
        Bundle getServiceRequestExtraArgs = super.getGetServiceRequestExtraArgs();
        if (getServiceRequestExtraArgs == null) {
            getServiceRequestExtraArgs = new Bundle();
        }
        zzee zzee = this.zzos;
        if (zzee != null) {
            getServiceRequestExtraArgs.putString("com.google.firebase.auth.API_KEY", zzee.getApiKey());
        }
        getServiceRequestExtraArgs.putString("com.google.firebase.auth.LIBRARY_VERSION", zzeg.zzek());
        return getServiceRequestExtraArgs;
    }

    public final Feature[] getApiFeatures() {
        return zze.zzh;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.auth.api.internal.IFirebaseAuthService");
        if (queryLocalInterface instanceof zzdz) {
            return (zzdz) queryLocalInterface;
        }
        return new zzea(iBinder);
    }

    public final /* synthetic */ zzdz zzeb() throws DeadObjectException {
        return (zzdz) super.getService();
    }
}
