package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzhc implements zzlh {
    private final zzha zzwc;

    public static zzhc zza(zzha zzha) {
        if (zzha.zzwx != null) {
            return zzha.zzwx;
        }
        return new zzhc(zzha);
    }

    private zzhc(zzha zzha) {
        this.zzwc = (zzha) zzht.zza(zzha, "output");
    }

    public final int zzhl() {
        return zzd.zzaay;
    }

    public final void zzp(int i, int i2) throws IOException {
        this.zzwc.zzi(i, i2);
    }

    public final void zzi(int i, long j) throws IOException {
        this.zzwc.zza(i, j);
    }

    public final void zzj(int i, long j) throws IOException {
        this.zzwc.zzc(i, j);
    }

    public final void zza(int i, float f) throws IOException {
        this.zzwc.zza(i, f);
    }

    public final void zza(int i, double d) throws IOException {
        this.zzwc.zza(i, d);
    }

    public final void zzq(int i, int i2) throws IOException {
        this.zzwc.zzf(i, i2);
    }

    public final void zza(int i, long j) throws IOException {
        this.zzwc.zza(i, j);
    }

    public final void zzf(int i, int i2) throws IOException {
        this.zzwc.zzf(i, i2);
    }

    public final void zzc(int i, long j) throws IOException {
        this.zzwc.zzc(i, j);
    }

    public final void zzi(int i, int i2) throws IOException {
        this.zzwc.zzi(i, i2);
    }

    public final void zzc(int i, boolean z) throws IOException {
        this.zzwc.zzc(i, z);
    }

    public final void zza(int i, String str) throws IOException {
        this.zzwc.zza(i, str);
    }

    public final void zza(int i, zzgf zzgf) throws IOException {
        this.zzwc.zza(i, zzgf);
    }

    public final void zzg(int i, int i2) throws IOException {
        this.zzwc.zzg(i, i2);
    }

    public final void zzh(int i, int i2) throws IOException {
        this.zzwc.zzh(i, i2);
    }

    public final void zzb(int i, long j) throws IOException {
        this.zzwc.zzb(i, j);
    }

    public final void zza(int i, Object obj, zzjs zzjs) throws IOException {
        this.zzwc.zza(i, (zzjc) obj, zzjs);
    }

    public final void zzb(int i, Object obj, zzjs zzjs) throws IOException {
        zzha zzha = this.zzwc;
        zzjc zzjc = (zzjc) obj;
        zzha.zze(i, 3);
        zzjs.zza(zzjc, zzha.zzwx);
        zzha.zze(i, 4);
    }

    public final void zzat(int i) throws IOException {
        this.zzwc.zze(i, 3);
    }

    public final void zzau(int i) throws IOException {
        this.zzwc.zze(i, 4);
    }

    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzgf) {
            this.zzwc.zzb(i, (zzgf) obj);
        } else {
            this.zzwc.zza(i, (zzjc) obj);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzal(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzag(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzao(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzaj(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzi(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zze(((Long) list.get(i4)).longValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzb(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzf(((Long) list.get(i4)).longValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzb(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zza(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzh(((Long) list.get(i4)).longValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzd(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzb(((Float) list.get(i4)).floatValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zza(((Float) list.get(i2)).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zza(i, ((Float) list.get(i2)).floatValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzb(((Double) list.get(i4)).doubleValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zza(((Double) list.get(i2)).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zza(i, ((Double) list.get(i2)).doubleValue());
            i2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzaq(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzag(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzf(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzu(((Boolean) list.get(i4)).booleanValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzt(((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzc(i, ((Boolean) list.get(i2)).booleanValue());
            i2++;
        }
    }

    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            while (i2 < list.size()) {
                Object zzax = zzij.zzax(i2);
                if (zzax instanceof String) {
                    this.zzwc.zza(i, (String) zzax);
                } else {
                    this.zzwc.zza(i, (zzgf) zzax);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zza(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zzb(int i, List<zzgf> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzwc.zza(i, (zzgf) list.get(i2));
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzam(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzah(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzg(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzap(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzaj(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzi(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzi(((Long) list.get(i4)).longValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzd(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzc(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzan(((Integer) list.get(i4)).intValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzai(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzh(i, ((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zzwc.zze(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += zzha.zzg(((Long) list.get(i4)).longValue());
            }
            this.zzwc.zzah(i3);
            while (i2 < list.size()) {
                this.zzwc.zzc(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzwc.zzb(i, ((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public final void zza(int i, List<?> list, zzjs zzjs) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzjs);
        }
    }

    public final void zzb(int i, List<?> list, zzjs zzjs) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzjs);
        }
    }

    public final <K, V> void zza(int i, zzit<K, V> zzit, Map<K, V> map) throws IOException {
        for (Entry entry : map.entrySet()) {
            this.zzwc.zze(i, 2);
            this.zzwc.zzah(zziu.zza(zzit, entry.getKey(), entry.getValue()));
            zziu.zza(this.zzwc, zzit, entry.getKey(), entry.getValue());
        }
    }
}
