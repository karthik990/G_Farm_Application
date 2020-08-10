package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbv;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import p043io.netty.util.internal.StringUtil;

@zzadh
@ParametersAreNonnullByDefault
public final class zznx {
    private final Object mLock = new Object();
    private boolean zzbgn;
    private final List<zznv> zzbgo = new LinkedList();
    private final Map<String, String> zzbgp = new LinkedHashMap();
    private String zzbgq;
    private zznx zzbgr;

    public zznx(boolean z, String str, String str2) {
        this.zzbgn = z;
        this.zzbgp.put("action", str);
        this.zzbgp.put("ad_format", str2);
    }

    public final boolean zza(zznv zznv, long j, String... strArr) {
        synchronized (this.mLock) {
            for (String zznv2 : strArr) {
                this.zzbgo.add(new zznv(j, zznv2, zznv));
            }
        }
        return true;
    }

    public final boolean zza(zznv zznv, String... strArr) {
        if (!this.zzbgn || zznv == null) {
            return false;
        }
        return zza(zznv, zzbv.zzer().elapsedRealtime(), strArr);
    }

    public final void zzan(String str) {
        if (this.zzbgn) {
            synchronized (this.mLock) {
                this.zzbgq = str;
            }
        }
    }

    public final void zzc(zznx zznx) {
        synchronized (this.mLock) {
            this.zzbgr = zznx;
        }
    }

    public final zznv zzd(long j) {
        if (!this.zzbgn) {
            return null;
        }
        return new zznv(j, null, null);
    }

    public final void zze(String str, String str2) {
        if (this.zzbgn && !TextUtils.isEmpty(str2)) {
            zznn zzpy = zzbv.zzeo().zzpy();
            if (zzpy != null) {
                synchronized (this.mLock) {
                    zznr zzal = zzpy.zzal(str);
                    Map<String, String> map = this.zzbgp;
                    map.put(str, zzal.zzd((String) map.get(str), str2));
                }
            }
        }
    }

    public final zznv zzjj() {
        return zzd(zzbv.zzer().elapsedRealtime());
    }

    public final String zzjk() {
        String sb;
        StringBuilder sb2 = new StringBuilder();
        synchronized (this.mLock) {
            for (zznv zznv : this.zzbgo) {
                long time = zznv.getTime();
                String zzjg = zznv.zzjg();
                zznv zzjh = zznv.zzjh();
                if (zzjh != null && time > 0) {
                    long time2 = time - zzjh.getTime();
                    sb2.append(zzjg);
                    sb2.append('.');
                    sb2.append(time2);
                    sb2.append(StringUtil.COMMA);
                }
            }
            this.zzbgo.clear();
            if (!TextUtils.isEmpty(this.zzbgq)) {
                sb2.append(this.zzbgq);
            } else if (sb2.length() > 0) {
                sb2.setLength(sb2.length() - 1);
            }
            sb = sb2.toString();
        }
        return sb;
    }

    /* access modifiers changed from: 0000 */
    public final Map<String, String> zzjl() {
        synchronized (this.mLock) {
            zznn zzpy = zzbv.zzeo().zzpy();
            if (zzpy != null) {
                if (this.zzbgr != null) {
                    Map<String, String> zza = zzpy.zza(this.zzbgp, this.zzbgr.zzjl());
                    return zza;
                }
            }
            Map<String, String> map = this.zzbgp;
            return map;
        }
    }

    public final zznv zzjm() {
        synchronized (this.mLock) {
        }
        return null;
    }
}
