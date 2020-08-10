package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.google.android.gms.common.util.PlatformVersion;

@zzadh
public final class zzapo extends zzaph {
    public final zzapg zza(Context context, zzapw zzapw, int i, boolean z, zznx zznx, zzapv zzapv) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (!(PlatformVersion.isAtLeastIceCreamSandwich() && (applicationInfo == null || applicationInfo.targetSdkVersion >= 11))) {
            return null;
        }
        boolean zzvs = zzapw.zzud().zzvs();
        zzapx zzapx = new zzapx(context, zzapw.zztq(), zzapw.zzol(), zznx, zzapw.zztn());
        zzaov zzaov = new zzaov(context, z, zzvs, zzapv, zzapx);
        return zzaov;
    }
}
