package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.internal.gmsg.zzv;
import java.util.Map;

final class zzafv implements zzv<Object> {
    private final /* synthetic */ zzaft zzchv;

    zzafv(zzaft zzaft) {
        this.zzchv = zzaft;
    }

    public final void zza(Object obj, Map<String, String> map) {
        synchronized (this.zzchv.mLock) {
            if (!this.zzchv.zzchr.isDone()) {
                zzafz zzafz = new zzafz(-2, map);
                if (this.zzchv.zzchp.equals(zzafz.zzol())) {
                    String url = zzafz.getUrl();
                    if (url == null) {
                        zzakb.zzdk("URL missing in loadAdUrl GMSG.");
                        return;
                    }
                    if (url.contains("%40mediation_adapters%40")) {
                        String replaceAll = url.replaceAll("%40mediation_adapters%40", zzajw.zzc(this.zzchv.mContext, (String) map.get("check_adapters"), this.zzchv.zzchq));
                        zzafz.setUrl(replaceAll);
                        String str = "Ad request URL modified to ";
                        String valueOf = String.valueOf(replaceAll);
                        zzakb.m1419v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    }
                    this.zzchv.zzchr.set(zzafz);
                }
            }
        }
    }
}
