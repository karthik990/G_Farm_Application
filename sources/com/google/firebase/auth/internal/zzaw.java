package com.google.firebase.auth.internal;

import android.content.Intent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.HashMap;
import java.util.Map;

public final class zzaw {
    private static final Map<String, String> zzvc;

    public static void zza(Intent intent, Status status) {
        SafeParcelableSerializer.serializeToIntentExtra(status, intent, "com.google.firebase.auth.internal.STATUS");
    }

    public static boolean zzb(Intent intent) {
        Preconditions.checkNotNull(intent);
        return intent.hasExtra("com.google.firebase.auth.internal.STATUS");
    }

    public static Status zzc(Intent intent) {
        Preconditions.checkNotNull(intent);
        Preconditions.checkArgument(zzb(intent));
        return (Status) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.auth.internal.STATUS", Status.CREATOR);
    }

    static {
        HashMap hashMap = new HashMap();
        zzvc = hashMap;
        hashMap.put("auth/invalid-provider-id", "INVALID_PROVIDER_ID");
        zzvc.put("auth/invalid-cert-hash", "INVALID_CERT_HASH");
        zzvc.put("auth/network-request-failed", "WEB_NETWORK_REQUEST_FAILED");
        zzvc.put("auth/web-storage-unsupported", "WEB_STORAGE_UNSUPPORTED");
        zzvc.put("auth/operation-not-allowed", "OPERATION_NOT_ALLOWED");
    }
}
