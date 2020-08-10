package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public abstract class zzah {
    static final Logger zzbz = Logger.getLogger(zzah.class.getName());
    private static final String[] zzca;

    /* access modifiers changed from: protected */
    public abstract zzai zzc(String str, String str2) throws IOException;

    public final zzab zza(zzac zzac) {
        return new zzab(this, zzac);
    }

    public boolean zzz(String str) throws IOException {
        return Arrays.binarySearch(zzca, str) >= 0;
    }

    static {
        String[] strArr = {"DELETE", "GET", "POST", "PUT"};
        zzca = strArr;
        Arrays.sort(strArr);
    }
}
