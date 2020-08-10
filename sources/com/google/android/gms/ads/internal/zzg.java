package com.google.android.gms.ads.internal;

import android.webkit.CookieManager;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.concurrent.Callable;

final class zzg implements Callable<String> {
    private final /* synthetic */ zzd zzwk;

    zzg(zzd zzd) {
        this.zzwk = zzd;
    }

    public final /* synthetic */ Object call() throws Exception {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbdj)).booleanValue()) {
            CookieManager zzax = zzbv.zzem().zzax(this.zzwk.zzvw.zzrt);
            if (zzax != null) {
                return zzax.getCookie("googleads.g.doubleclick.net");
            }
        }
        return "";
    }
}
