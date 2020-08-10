package com.google.firebase.auth.api.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;

public final class zzey {
    private final String packageName;
    private final String zzqu;

    public zzey(Context context) {
        this(context, context.getPackageName());
    }

    private zzey(Context context, String str) {
        String str2 = "FBA-PackageInfo";
        Preconditions.checkNotNull(context);
        this.packageName = Preconditions.checkNotEmpty(str);
        try {
            byte[] packageCertificateHashBytes = AndroidUtilsLight.getPackageCertificateHashBytes(context, this.packageName);
            if (packageCertificateHashBytes == null) {
                String str3 = "single cert required: ";
                String valueOf = String.valueOf(str);
                Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                this.zzqu = null;
                return;
            }
            this.zzqu = Hex.bytesToStringUppercase(packageCertificateHashBytes, false);
        } catch (NameNotFoundException unused) {
            String str4 = "no pkg: ";
            String valueOf2 = String.valueOf(str);
            Log.e(str2, valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4));
            this.zzqu = null;
        }
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String zzeo() {
        return this.zzqu;
    }
}
