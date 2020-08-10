package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzju {
    private static final Class<?> zzadw = zzkg();
    private static final zzkk<?, ?> zzadx = zzw(false);
    private static final zzkk<?, ?> zzady = zzw(true);
    private static final zzkk<?, ?> zzadz = new zzkm();

    public static void zzg(Class<?> cls) {
        if (!zzhs.class.isAssignableFrom(cls)) {
            Class<?> cls2 = zzadw;
            if (cls2 != null && !cls2.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
            }
        }
    }

    public static void zza(int i, List<Double> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zza(i, list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzb(i, list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzlh zzlh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzlh zzlh) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zza(i, list);
        }
    }

    public static void zzb(int i, List<zzgf> list, zzlh zzlh) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzb(i, list);
        }
    }

    public static void zza(int i, List<?> list, zzlh zzlh, zzjs zzjs) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zza(i, list, zzjs);
        }
    }

    public static void zzb(int i, List<?> list, zzlh zzlh, zzjs zzjs) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzlh.zzb(i, list, zzjs);
        }
    }

    static int zzx(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zze(zziq.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zze(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzx(list) + (list.size() * zzha.zzak(i));
    }

    static int zzy(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzf(zziq.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzf(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzy(list) + (size * zzha.zzak(i));
    }

    static int zzz(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zziq) {
            zziq zziq = (zziq) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzg(zziq.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzg(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzz(list) + (size * zzha.zzak(i));
    }

    static int zzaa(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzaq(zzhu.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzaq(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzaa(list) + (size * zzha.zzak(i));
    }

    static int zzab(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzal(zzhu.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzal(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzab(list) + (size * zzha.zzak(i));
    }

    static int zzac(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzam(zzhu.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzam(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzac(list) + (size * zzha.zzak(i));
    }

    static int zzad(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhu) {
            zzhu zzhu = (zzhu) list;
            i = 0;
            while (i2 < size) {
                i += zzha.zzan(zzhu.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzha.zzan(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzad(list) + (size * zzha.zzak(i));
    }

    static int zzae(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzha.zzm(i, 0);
    }

    static int zzaf(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzha.zzg(i, 0);
    }

    static int zzag(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzha.zzd(i, true);
    }

    static int zzc(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int zzak = zzha.zzak(i) * size;
        if (list instanceof zzij) {
            zzij zzij = (zzij) list;
            while (i4 < size) {
                Object zzax = zzij.zzax(i4);
                if (zzax instanceof zzgf) {
                    i3 = zzha.zzb((zzgf) zzax);
                } else {
                    i3 = zzha.zzdj((String) zzax);
                }
                zzak += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof zzgf) {
                    i2 = zzha.zzb((zzgf) obj);
                } else {
                    i2 = zzha.zzdj((String) obj);
                }
                zzak += i2;
                i4++;
            }
        }
        return zzak;
    }

    static int zzc(int i, Object obj, zzjs zzjs) {
        if (obj instanceof zzih) {
            return zzha.zza(i, (zzih) obj);
        }
        return zzha.zzb(i, (zzjc) obj, zzjs);
    }

    static int zzc(int i, List<?> list, zzjs zzjs) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzak = zzha.zzak(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zzih) {
                i2 = zzha.zza((zzih) obj);
            } else {
                i2 = zzha.zza((zzjc) obj, zzjs);
            }
            zzak += i2;
        }
        return zzak;
    }

    static int zzd(int i, List<zzgf> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzak = size * zzha.zzak(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzak += zzha.zzb((zzgf) list.get(i2));
        }
        return zzak;
    }

    static int zzd(int i, List<zzjc> list, zzjs zzjs) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzha.zzc(i, (zzjc) list.get(i3), zzjs);
        }
        return i2;
    }

    public static zzkk<?, ?> zzkd() {
        return zzadx;
    }

    public static zzkk<?, ?> zzke() {
        return zzady;
    }

    public static zzkk<?, ?> zzkf() {
        return zzadz;
    }

    private static zzkk<?, ?> zzw(boolean z) {
        try {
            Class zzkh = zzkh();
            if (zzkh == null) {
                return null;
            }
            return (zzkk) zzkh.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzkg() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzkh() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zze(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static <T> void zza(zziv zziv, T t, T t2, long j) {
        zzkq.zza((Object) t, j, zziv.zzc(zzkq.zzp(t, j), zzkq.zzp(t2, j)));
    }

    static <T, FT extends zzhk<FT>> void zza(zzhh<FT> zzhh, T t, T t2) {
        zzhi zzd = zzhh.zzd(t2);
        if (!zzd.zzxh.isEmpty()) {
            zzhh.zze(t).zza(zzd);
        }
    }

    static <T, UT, UB> void zza(zzkk<UT, UB> zzkk, T t, T t2) {
        zzkk.zzf(t, zzkk.zzh(zzkk.zzs(t), zzkk.zzs(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzhy zzhy, UB ub, zzkk<UT, UB> zzkk) {
        UB ub2;
        if (zzhy == null) {
            return ub;
        }
        if (!(list instanceof RandomAccess)) {
            Iterator it = list.iterator();
            loop1:
            while (true) {
                ub2 = ub;
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (!zzhy.zzd(intValue)) {
                        ub = zza(i, intValue, ub2, zzkk);
                        it.remove();
                    }
                }
                break loop1;
            }
        } else {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue2 = ((Integer) list.get(i3)).intValue();
                if (zzhy.zzd(intValue2)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue2));
                    }
                    i2++;
                } else {
                    ub2 = zza(i, intValue2, ub2, zzkk);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        }
        return ub2;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzkk<UT, UB> zzkk) {
        if (ub == null) {
            ub = zzkk.zzkn();
        }
        zzkk.zza(ub, i, (long) i2);
        return ub;
    }
}
