package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.ads.internal.zzbv;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import p043io.fabric.sdk.android.services.common.CommonUtils;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@zzadh
public final class zznm {
    private Context mContext = null;
    private String zzaej = null;
    private String zzbfx;
    private Map<String, String> zzbfy;

    public zznm(Context context, String str) {
        this.mContext = context;
        this.zzaej = str;
        this.zzbfx = (String) zzkb.zzik().zzd(zznk.zzawi);
        this.zzbfy = new LinkedHashMap();
        this.zzbfy.put("s", "gmob_sdk");
        this.zzbfy.put("v", ExifInterface.GPS_MEASUREMENT_3D);
        this.zzbfy.put("os", VERSION.RELEASE);
        this.zzbfy.put(CommonUtils.SDK, VERSION.SDK);
        Map<String, String> map = this.zzbfy;
        zzbv.zzek();
        map.put("device", zzakk.zzri());
        this.zzbfy.put(SettingsJsonConstants.APP_KEY, context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        Map<String, String> map2 = this.zzbfy;
        zzbv.zzek();
        map2.put("is_lite_sdk", zzakk.zzav(context) ? IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE : "0");
        Future zzq = zzbv.zzev().zzq(this.mContext);
        try {
            zzq.get();
            this.zzbfy.put("network_coarse", Integer.toString(((zzaga) zzq.get()).zzcjx));
            this.zzbfy.put("network_fine", Integer.toString(((zzaga) zzq.get()).zzcjy));
        } catch (Exception e) {
            zzbv.zzeo().zza(e, "CsiConfiguration.CsiConfiguration");
        }
    }

    /* access modifiers changed from: 0000 */
    public final Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: 0000 */
    public final String zzfw() {
        return this.zzaej;
    }

    /* access modifiers changed from: 0000 */
    public final String zzjd() {
        return this.zzbfx;
    }

    /* access modifiers changed from: 0000 */
    public final Map<String, String> zzje() {
        return this.zzbfy;
    }
}
