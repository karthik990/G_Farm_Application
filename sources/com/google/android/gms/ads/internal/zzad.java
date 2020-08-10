package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajl;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzanm;
import com.google.android.gms.internal.ads.zzano;
import com.google.android.gms.internal.ads.zzanz;
import com.google.android.gms.internal.ads.zzaoe;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzwf;
import com.google.android.gms.internal.ads.zzwk;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzad {
    private Context mContext;
    private final Object mLock = new Object();
    private long zzxm = 0;

    public final void zza(Context context, zzang zzang, String str, Runnable runnable) {
        zza(context, zzang, true, null, str, null, runnable);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(Context context, zzang zzang, boolean z, zzajl zzajl, String str, String str2, Runnable runnable) {
        if (zzbv.zzer().elapsedRealtime() - this.zzxm < DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
            zzakb.zzdk("Not retrying to fetch app settings");
            return;
        }
        this.zzxm = zzbv.zzer().elapsedRealtime();
        boolean z2 = true;
        if (zzajl != null) {
            if (!(zzbv.zzer().currentTimeMillis() - zzajl.zzps() > ((Long) zzkb.zzik().zzd(zznk.zzbcu)).longValue()) && zzajl.zzpt()) {
                z2 = false;
            }
        }
        if (z2) {
            if (context == null) {
                zzakb.zzdk("Context not provided to fetch application settings");
            } else if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext == null) {
                    applicationContext = context;
                }
                this.mContext = applicationContext;
                zzwf zza = zzbv.zzey().zzb(this.mContext, zzang).zza("google.afma.config.fetchAppSettings", zzwk.zzbrc, zzwk.zzbrc);
                try {
                    JSONObject jSONObject = new JSONObject();
                    if (!TextUtils.isEmpty(str)) {
                        jSONObject.put("app_id", str);
                    } else if (!TextUtils.isEmpty(str2)) {
                        jSONObject.put("ad_unit_id", str2);
                    }
                    jSONObject.put("is_init", z);
                    jSONObject.put("pn", context.getPackageName());
                    zzanz zzf = zza.zzf(jSONObject);
                    zzanz zza2 = zzano.zza(zzf, zzae.zzxn, zzaoe.zzcvz);
                    if (runnable != null) {
                        zzf.zza(runnable, zzaoe.zzcvz);
                    }
                    zzanm.zza(zza2, "ConfigLoader.maybeFetchNewAppSettings");
                } catch (Exception e) {
                    zzakb.zzb("Error requesting application settings", e);
                }
            } else {
                zzakb.zzdk("App settings could not be fetched. Required parameters missing");
            }
        }
    }
}
