package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.util.Log;
import androidx.core.content.PermissionChecker;

final class zzse implements zzsb {
    static zzse zzbrl;
    private final Context zzri;

    static zzse zzad(Context context) {
        zzse zzse;
        synchronized (zzse.class) {
            if (zzbrl == null) {
                zzbrl = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzse(context) : new zzse();
            }
            zzse = zzbrl;
        }
        return zzse;
    }

    private zzse(Context context) {
        this.zzri = context;
        this.zzri.getContentResolver().registerContentObserver(zzru.CONTENT_URI, true, new zzsg(this, null));
    }

    private zzse() {
        this.zzri = null;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzfo */
    public final String zzfn(String str) {
        if (this.zzri == null) {
            return null;
        }
        try {
            return (String) zzsc.zza(new zzsf(this, str));
        } catch (SecurityException e) {
            String str2 = "Unable to read GServices for: ";
            String valueOf = String.valueOf(str);
            Log.e("GservicesLoader", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ String zzfp(String str) {
        return zzru.zza(this.zzri.getContentResolver(), str, (String) null);
    }
}
