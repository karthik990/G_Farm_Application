package com.google.android.gms.internal.ads;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.google.android.gms.ads.AdActivity;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;

@zzadh
public final class zzajr {
    private final Object mLock = new Object();
    private final String zzasc;
    private long zzcqd = -1;
    private long zzcqe = -1;
    private int zzcqf = -1;
    int zzcqg = -1;
    private long zzcqh = 0;
    private int zzcqi = 0;
    private int zzcqj = 0;

    public zzajr(String str) {
        this.zzasc = str;
    }

    private static boolean zzah(Context context) {
        int identifier = context.getResources().getIdentifier("Theme.Translucent", TtmlNode.TAG_STYLE, AbstractSpiCall.ANDROID_CLIENT_TYPE);
        String str = "Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.";
        if (identifier == 0) {
            zzakb.zzdj(str);
            return false;
        }
        try {
            if (identifier == context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), AdActivity.CLASS_NAME), 0).theme) {
                return true;
            }
            zzakb.zzdj(str);
            return false;
        } catch (NameNotFoundException unused) {
            zzakb.zzdk("Fail to fetch AdActivity theme");
            zzakb.zzdj(str);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(com.google.android.gms.internal.ads.zzjj r11, long r12) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            com.google.android.gms.internal.ads.zzajm r1 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzakd r1 = r1.zzqh()     // Catch:{ all -> 0x0090 }
            long r1 = r1.zzrb()     // Catch:{ all -> 0x0090 }
            com.google.android.gms.common.util.Clock r3 = com.google.android.gms.ads.internal.zzbv.zzer()     // Catch:{ all -> 0x0090 }
            long r3 = r3.currentTimeMillis()     // Catch:{ all -> 0x0090 }
            long r5 = r10.zzcqe     // Catch:{ all -> 0x0090 }
            r7 = -1
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x004b
            long r1 = r3 - r1
            com.google.android.gms.internal.ads.zzna<java.lang.Long> r5 = com.google.android.gms.internal.ads.zznk.zzayi     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzni r6 = com.google.android.gms.internal.ads.zzkb.zzik()     // Catch:{ all -> 0x0090 }
            java.lang.Object r5 = r6.zzd(r5)     // Catch:{ all -> 0x0090 }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ all -> 0x0090 }
            long r5 = r5.longValue()     // Catch:{ all -> 0x0090 }
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0039
            r1 = -1
            r10.zzcqg = r1     // Catch:{ all -> 0x0090 }
            goto L_0x0047
        L_0x0039:
            com.google.android.gms.internal.ads.zzajm r1 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzakd r1 = r1.zzqh()     // Catch:{ all -> 0x0090 }
            int r1 = r1.zzrc()     // Catch:{ all -> 0x0090 }
            r10.zzcqg = r1     // Catch:{ all -> 0x0090 }
        L_0x0047:
            r10.zzcqe = r12     // Catch:{ all -> 0x0090 }
            long r12 = r10.zzcqe     // Catch:{ all -> 0x0090 }
        L_0x004b:
            r10.zzcqd = r12     // Catch:{ all -> 0x0090 }
            r12 = 1
            if (r11 == 0) goto L_0x0061
            android.os.Bundle r13 = r11.extras     // Catch:{ all -> 0x0090 }
            if (r13 == 0) goto L_0x0061
            android.os.Bundle r11 = r11.extras     // Catch:{ all -> 0x0090 }
            java.lang.String r13 = "gw"
            r1 = 2
            int r11 = r11.getInt(r13, r1)     // Catch:{ all -> 0x0090 }
            if (r11 != r12) goto L_0x0061
            monitor-exit(r0)     // Catch:{ all -> 0x0090 }
            return
        L_0x0061:
            int r11 = r10.zzcqf     // Catch:{ all -> 0x0090 }
            int r11 = r11 + r12
            r10.zzcqf = r11     // Catch:{ all -> 0x0090 }
            int r11 = r10.zzcqg     // Catch:{ all -> 0x0090 }
            int r11 = r11 + r12
            r10.zzcqg = r11     // Catch:{ all -> 0x0090 }
            int r11 = r10.zzcqg     // Catch:{ all -> 0x0090 }
            if (r11 != 0) goto L_0x007f
            r11 = 0
            r10.zzcqh = r11     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzajm r11 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzakd r11 = r11.zzqh()     // Catch:{ all -> 0x0090 }
            r11.zzk(r3)     // Catch:{ all -> 0x0090 }
            goto L_0x008e
        L_0x007f:
            com.google.android.gms.internal.ads.zzajm r11 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch:{ all -> 0x0090 }
            com.google.android.gms.internal.ads.zzakd r11 = r11.zzqh()     // Catch:{ all -> 0x0090 }
            long r11 = r11.zzrd()     // Catch:{ all -> 0x0090 }
            long r3 = r3 - r11
            r10.zzcqh = r3     // Catch:{ all -> 0x0090 }
        L_0x008e:
            monitor-exit(r0)     // Catch:{ all -> 0x0090 }
            return
        L_0x0090:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0090 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzajr.zzb(com.google.android.gms.internal.ads.zzjj, long):void");
    }

    public final Bundle zzk(Context context, String str) {
        Bundle bundle;
        synchronized (this.mLock) {
            bundle = new Bundle();
            bundle.putString("session_id", this.zzasc);
            bundle.putLong("basets", this.zzcqe);
            bundle.putLong("currts", this.zzcqd);
            bundle.putString("seq_num", str);
            bundle.putInt("preqs", this.zzcqf);
            bundle.putInt("preqs_in_session", this.zzcqg);
            bundle.putLong("time_in_session", this.zzcqh);
            bundle.putInt("pclick", this.zzcqi);
            bundle.putInt("pimp", this.zzcqj);
            bundle.putBoolean("support_transparent_background", zzah(context));
        }
        return bundle;
    }

    public final void zzpm() {
        synchronized (this.mLock) {
            this.zzcqj++;
        }
    }

    public final void zzpn() {
        synchronized (this.mLock) {
            this.zzcqi++;
        }
    }
}
