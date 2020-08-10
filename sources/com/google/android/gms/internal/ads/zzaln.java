package com.google.android.gms.internal.ads;

import android.content.Context;
import java.io.File;
import java.util.regex.Pattern;

@zzadh
public final class zzaln extends zzaj {
    private final Context mContext;

    private zzaln(Context context, zzar zzar) {
        super(zzar);
        this.mContext = context;
    }

    public static zzv zzba(Context context) {
        zzv zzv = new zzv(new zzam(new File(context.getCacheDir(), "admob_volley")), new zzaln(context, new zzas()));
        zzv.start();
        return zzv;
    }

    public final zzp zzc(zzr<?> zzr) throws zzae {
        if (zzr.zzh() && zzr.getMethod() == 0) {
            if (Pattern.matches((String) zzkb.zzik().zzd(zznk.zzbdw), zzr.getUrl())) {
                zzkb.zzif();
                if (zzamu.zzbe(this.mContext)) {
                    zzp zzc = new zzsm(this.mContext).zzc(zzr);
                    if (zzc != null) {
                        String str = "Got gmscore asset response: ";
                        String valueOf = String.valueOf(zzr.getUrl());
                        zzakb.m1419v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                        return zzc;
                    }
                    String str2 = "Failed to get gmscore asset response: ";
                    String valueOf2 = String.valueOf(zzr.getUrl());
                    zzakb.m1419v(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
            }
        }
        return super.zzc(zzr);
    }
}
