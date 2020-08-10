package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.internal.gmsg.zzv;
import java.lang.ref.WeakReference;

final class zzpf {
    /* access modifiers changed from: private */
    public final WeakReference<zzaqw> zzbjg;
    /* access modifiers changed from: private */
    public String zzbjh;

    public zzpf(zzaqw zzaqw) {
        this.zzbjg = new WeakReference<>(zzaqw);
    }

    public final void zza(zzacm zzacm) {
        zzacm.zza("/loadHtml", (zzv<? super T>) new zzpg<Object>(this, zzacm));
        zzacm.zza("/showOverlay", (zzv<? super T>) new zzpi<Object>(this, zzacm));
        zzacm.zza("/hideOverlay", (zzv<? super T>) new zzpj<Object>(this, zzacm));
        zzaqw zzaqw = (zzaqw) this.zzbjg.get();
        if (zzaqw != null) {
            zzaqw.zza("/sendMessageToSdk", (zzv<? super zzaqw>) new zzpk<Object>(this, zzacm));
        }
    }
}
