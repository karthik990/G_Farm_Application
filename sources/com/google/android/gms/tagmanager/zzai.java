package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Random;

public final class zzai {
    private final String zzaec;
    private final Random zzafj;
    private final Context zzrm;

    public zzai(Context context, String str) {
        this(context, str, new Random());
    }

    private zzai(Context context, String str, Random random) {
        this.zzrm = (Context) Preconditions.checkNotNull(context);
        this.zzaec = (String) Preconditions.checkNotNull(str);
        this.zzafj = random;
    }

    public final long zzhl() {
        return zza(7200000, 259200000) + 43200000;
    }

    public final long zzhm() {
        return zza(600000, 86400000) + 3600000;
    }

    private final long zza(long j, long j2) {
        SharedPreferences zzhp = zzhp();
        long max = Math.max(0, zzhp.getLong("FORBIDDEN_COUNT", 0));
        return (long) (this.zzafj.nextFloat() * ((float) (j + ((long) ((((float) max) / ((float) ((max + Math.max(0, zzhp.getLong("SUCCESSFUL_COUNT", 0))) + 1))) * ((float) (j2 - j)))))));
    }

    public final void zzhn() {
        long j;
        SharedPreferences zzhp = zzhp();
        String str = "FORBIDDEN_COUNT";
        long j2 = zzhp.getLong(str, 0);
        String str2 = "SUCCESSFUL_COUNT";
        long j3 = zzhp.getLong(str2, 0);
        Editor edit = zzhp.edit();
        if (j2 == 0) {
            j = 3;
        } else {
            j = Math.min(10, j2 + 1);
        }
        long max = Math.max(0, Math.min(j3, 10 - j));
        edit.putLong(str, j);
        edit.putLong(str2, max);
        edit.apply();
    }

    public final void zzho() {
        SharedPreferences zzhp = zzhp();
        String str = "SUCCESSFUL_COUNT";
        long j = zzhp.getLong(str, 0);
        String str2 = "FORBIDDEN_COUNT";
        long j2 = zzhp.getLong(str2, 0);
        long min = Math.min(10, j + 1);
        long max = Math.max(0, Math.min(j2, 10 - min));
        Editor edit = zzhp.edit();
        edit.putLong(str, min);
        edit.putLong(str2, max);
        edit.apply();
    }

    private final SharedPreferences zzhp() {
        Context context = this.zzrm;
        String valueOf = String.valueOf(this.zzaec);
        String str = "_gtmContainerRefreshPolicy_";
        return context.getSharedPreferences(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), 0);
    }
}
