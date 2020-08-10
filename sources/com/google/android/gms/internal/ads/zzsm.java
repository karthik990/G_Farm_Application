package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.ads.internal.zzbv;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzadh
public final class zzsm implements zzm {
    private final Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public zzsf zzbnl;
    /* access modifiers changed from: private */
    public boolean zzbnm;

    public zzsm(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public final void disconnect() {
        synchronized (this.mLock) {
            if (this.zzbnl != null) {
                this.zzbnl.disconnect();
                this.zzbnl = null;
                Binder.flushPendingCommands();
            }
        }
    }

    private final Future<ParcelFileDescriptor> zzb(zzsg zzsg) {
        zzsn zzsn = new zzsn(this);
        zzso zzso = new zzso(this, zzsn, zzsg);
        zzsr zzsr = new zzsr(this, zzsn);
        synchronized (this.mLock) {
            this.zzbnl = new zzsf(this.mContext, zzbv.zzez().zzsa(), zzso, zzsr);
            this.zzbnl.checkAvailabilityAndConnect();
        }
        return zzsn;
    }

    /* JADX INFO: finally extract failed */
    public final zzp zzc(zzr<?> zzr) throws zzae {
        zzp zzp;
        String str = "ms";
        String str2 = "Http assets remote cache took ";
        zzsg zzh = zzsg.zzh(zzr);
        long intValue = (long) ((Integer) zzkb.zzik().zzd(zznk.zzbdx)).intValue();
        long elapsedRealtime = zzbv.zzer().elapsedRealtime();
        try {
            zzsi zzsi = (zzsi) new zzaev((ParcelFileDescriptor) zzb(zzh).get(intValue, TimeUnit.MILLISECONDS)).zza(zzsi.CREATOR);
            if (!zzsi.zzbnj) {
                if (zzsi.zzbnh.length != zzsi.zzbni.length) {
                    zzp = null;
                } else {
                    HashMap hashMap = new HashMap();
                    for (int i = 0; i < zzsi.zzbnh.length; i++) {
                        hashMap.put(zzsi.zzbnh[i], zzsi.zzbni[i]);
                    }
                    zzp = new zzp(zzsi.statusCode, zzsi.data, (Map<String, String>) hashMap, zzsi.zzac, zzsi.zzad);
                }
                long elapsedRealtime2 = zzbv.zzer().elapsedRealtime() - elapsedRealtime;
                StringBuilder sb = new StringBuilder(52);
                sb.append(str2);
                sb.append(elapsedRealtime2);
                sb.append(str);
                zzakb.m1419v(sb.toString());
                return zzp;
            }
            throw new zzae(zzsi.zzbnk);
        } catch (InterruptedException | ExecutionException | TimeoutException unused) {
            long elapsedRealtime3 = zzbv.zzer().elapsedRealtime() - elapsedRealtime;
            StringBuilder sb2 = new StringBuilder(52);
            sb2.append(str2);
            sb2.append(elapsedRealtime3);
            sb2.append(str);
            zzakb.m1419v(sb2.toString());
            return null;
        } catch (Throwable th) {
            long elapsedRealtime4 = zzbv.zzer().elapsedRealtime() - elapsedRealtime;
            StringBuilder sb3 = new StringBuilder(52);
            sb3.append(str2);
            sb3.append(elapsedRealtime4);
            sb3.append(str);
            zzakb.m1419v(sb3.toString());
            throw th;
        }
    }
}
