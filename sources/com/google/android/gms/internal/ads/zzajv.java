package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.ads.internal.zzbv;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@zzadh
public final class zzajv implements zzgj {
    private final Object lock;
    private final zzajr zzcqn;
    private final HashSet<zzajj> zzcqo;
    private final HashSet<zzaju> zzcqp;

    public zzajv() {
        this(zzkb.zzih());
    }

    private zzajv(String str) {
        this.lock = new Object();
        this.zzcqo = new HashSet<>();
        this.zzcqp = new HashSet<>();
        this.zzcqn = new zzajr(str);
    }

    public final Bundle zza(Context context, zzajs zzajs, String str) {
        Bundle bundle;
        synchronized (this.lock) {
            bundle = new Bundle();
            bundle.putBundle(SettingsJsonConstants.APP_KEY, this.zzcqn.zzk(context, str));
            Bundle bundle2 = new Bundle();
            Iterator it = this.zzcqp.iterator();
            while (it.hasNext()) {
                zzaju zzaju = (zzaju) it.next();
                bundle2.putBundle(zzaju.zzqm(), zzaju.toBundle());
            }
            bundle.putBundle("slots", bundle2);
            ArrayList arrayList = new ArrayList();
            Iterator it2 = this.zzcqo.iterator();
            while (it2.hasNext()) {
                arrayList.add(((zzajj) it2.next()).toBundle());
            }
            bundle.putParcelableArrayList("ads", arrayList);
            zzajs.zza(this.zzcqo);
            this.zzcqo.clear();
        }
        return bundle;
    }

    public final void zza(zzajj zzajj) {
        synchronized (this.lock) {
            this.zzcqo.add(zzajj);
        }
    }

    public final void zza(zzaju zzaju) {
        synchronized (this.lock) {
            this.zzcqp.add(zzaju);
        }
    }

    public final void zzb(zzjj zzjj, long j) {
        synchronized (this.lock) {
            this.zzcqn.zzb(zzjj, j);
        }
    }

    public final void zzb(HashSet<zzajj> hashSet) {
        synchronized (this.lock) {
            this.zzcqo.addAll(hashSet);
        }
    }

    public final void zzh(boolean z) {
        long currentTimeMillis = zzbv.zzer().currentTimeMillis();
        if (z) {
            if (currentTimeMillis - zzbv.zzeo().zzqh().zzrb() > ((Long) zzkb.zzik().zzd(zznk.zzayi)).longValue()) {
                this.zzcqn.zzcqg = -1;
                return;
            }
            this.zzcqn.zzcqg = zzbv.zzeo().zzqh().zzrc();
            return;
        }
        zzbv.zzeo().zzqh().zzj(currentTimeMillis);
        zzbv.zzeo().zzqh().zzaf(this.zzcqn.zzcqg);
    }

    public final void zzpm() {
        synchronized (this.lock) {
            this.zzcqn.zzpm();
        }
    }

    public final void zzpn() {
        synchronized (this.lock) {
            this.zzcqn.zzpn();
        }
    }
}
