package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zze;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzgr implements zzkw {
    private final zzgp zzov;

    public static zzgr zza(zzgp zzgp) {
        if (zzgp.zzpq != null) {
            return zzgp.zzpq;
        }
        return new zzgr(zzgp);
    }

    private zzgr(zzgp zzgp) {
        this.zzov = (zzgp) zzhk.zza(zzgp, "output");
    }

    public final int zzgf() {
        return zze.zzto;
    }

    public final void zzo(int i, int i2) throws IOException {
        this.zzov.zzh(i, i2);
    }

    public final void zzi(int i, long j) throws IOException {
        this.zzov.zza(i, j);
    }

    public final void zzj(int i, long j) throws IOException {
        this.zzov.zzc(i, j);
    }

    public final void zza(int i, float f) throws IOException {
        this.zzov.zza(i, f);
    }

    public final void zza(int i, double d) throws IOException {
        this.zzov.zza(i, d);
    }

    public final void zzp(int i, int i2) throws IOException {
        this.zzov.zze(i, i2);
    }

    public final void zza(int i, long j) throws IOException {
        this.zzov.zza(i, j);
    }

    public final void zze(int i, int i2) throws IOException {
        this.zzov.zze(i, i2);
    }

    public final void zzc(int i, long j) throws IOException {
        this.zzov.zzc(i, j);
    }

    public final void zzh(int i, int i2) throws IOException {
        this.zzov.zzh(i, i2);
    }

    public final void zzb(int i, boolean z) throws IOException {
        this.zzov.zzb(i, z);
    }

    public final void zzb(int i, String str) throws IOException {
        this.zzov.zzb(i, str);
    }

    public final void zza(int i, zzfw zzfw) throws IOException {
        this.zzov.zza(i, zzfw);
    }

    public final void zzf(int i, int i2) throws IOException {
        this.zzov.zzf(i, i2);
    }

    public final void zzg(int i, int i2) throws IOException {
        this.zzov.zzg(i, i2);
    }

    public final void zzb(int i, long j) throws IOException {
        this.zzov.zzb(i, j);
    }

    public final void zza(int i, Object obj, zzjj zzjj) throws IOException {
        this.zzov.zza(i, (zzio) obj, zzjj);
    }

    public final void zzb(int i, Object obj, zzjj zzjj) throws IOException {
        zzgp zzgp = this.zzov;
        zzio zzio = (zzio) obj;
        zzgp.zzd(i, 3);
        zzjj.zza(zzio, zzgp.zzpq);
        zzgp.zzd(i, 4);
    }

    public final void zzba(int i) throws IOException {
        this.zzov.zzd(i, 3);
    }

    public final void zzbb(int i) throws IOException {
        this.zzov.zzd(i, 4);
    }

    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzfw) {
            this.zzov.zzb(i, (zzfw) obj);
        } else {
            this.zzov.zza(i, (zzio) obj);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzas(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzan(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zze(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzav(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzaq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzh(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzi(((Long) list.get(i4)).longValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzf(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzj(((Long) list.get(i4)).longValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzf(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzl(((Long) list.get(i4)).longValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzh(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzc(((Float) list.get(i4)).floatValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzb(((Float) list.get(i2)).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zza(i, ((Float) list.get(i2)).floatValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzd(((Double) list.get(i4)).doubleValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzc(((Double) list.get(i2)).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zza(i, ((Double) list.get(i2)).doubleValue());
            i2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzax(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzan(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zze(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzf(((Boolean) list.get(i4)).booleanValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zze(((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzb(i, ((Boolean) list.get(i2)).booleanValue());
            i2++;
        }
    }

    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzhx) {
            zzhx zzhx = (zzhx) list;
            while (i2 < list.size()) {
                Object zzbe = zzhx.zzbe(i2);
                if (zzbe instanceof String) {
                    this.zzov.zzb(i, (String) zzbe);
                } else {
                    this.zzov.zza(i, (zzfw) zzbe);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzb(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zzb(int i, List<zzfw> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzov.zza(i, (zzfw) list.get(i2));
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzat(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzao(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzaw(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzaq(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzh(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzm(((Long) list.get(i4)).longValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzh(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzau(((Integer) list.get(i4)).intValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzap(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzg(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzov.zzd(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzgp.zzk(((Long) list.get(i4)).longValue());
            }
            this.zzov.zzao(i3);
            while (i2 < list.size()) {
                this.zzov.zzg(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzov.zzb(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zza(int i, List<?> list, zzjj zzjj) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzjj);
        }
    }

    public final void zzb(int i, List<?> list, zzjj zzjj) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzjj);
        }
    }

    public final <K, V> void zza(int i, zzih<K, V> zzih, Map<K, V> map) throws IOException {
        for (Entry entry : map.entrySet()) {
            this.zzov.zzd(i, 2);
            this.zzov.zzao(zzig.zza(zzih, entry.getKey(), entry.getValue()));
            zzig.zza(this.zzov, zzih, entry.getKey(), entry.getValue());
        }
    }
}
