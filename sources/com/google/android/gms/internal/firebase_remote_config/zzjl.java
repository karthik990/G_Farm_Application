package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

final class zzjl {
    private static final Class<?> zzwt = zzit();
    private static final zzkb<?, ?> zzwu = zzh(false);
    private static final zzkb<?, ?> zzwv = zzh(true);
    private static final zzkb<?, ?> zzww = new zzkd();

    public static void zzl(Class<?> cls) {
        if (!zzhi.class.isAssignableFrom(cls)) {
            Class<?> cls2 = zzwt;
            if (cls2 != null && !cls2.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
            }
        }
    }

    public static void zza(int i, List<Double> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zza(i, list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzb(i, list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzkw zzkw, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzkw zzkw) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zza(i, list);
        }
    }

    public static void zzb(int i, List<zzfw> list, zzkw zzkw) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzb(i, list);
        }
    }

    public static void zza(int i, List<?> list, zzkw zzkw, zzjj zzjj) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zza(i, list, zzjj);
        }
    }

    public static void zzb(int i, List<?> list, zzkw zzkw, zzjj zzjj) throws IOException {
        if (list != null && !list.isEmpty()) {
            zzkw.zzb(i, list, zzjj);
        }
    }

    static int zzs(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzi(zzic.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzi(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzs(list) + (list.size() * zzgp.zzar(i));
    }

    static int zzt(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzj(zzic.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzj(((Long) list.get(i2)).longValue());
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
        return zzt(list) + (size * zzgp.zzar(i));
    }

    static int zzu(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzic) {
            zzic zzic = (zzic) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzk(zzic.getLong(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzk(((Long) list.get(i2)).longValue());
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
        return zzu(list) + (size * zzgp.zzar(i));
    }

    static int zzv(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzax(zzhj.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzax(((Integer) list.get(i2)).intValue());
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
        return zzv(list) + (size * zzgp.zzar(i));
    }

    static int zzw(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzas(zzhj.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzas(((Integer) list.get(i2)).intValue());
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
        return zzw(list) + (size * zzgp.zzar(i));
    }

    static int zzx(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzat(zzhj.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzat(((Integer) list.get(i2)).intValue());
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
        return zzx(list) + (size * zzgp.zzar(i));
    }

    static int zzy(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzhj) {
            zzhj zzhj = (zzhj) list;
            i = 0;
            while (i2 < size) {
                i += zzgp.zzau(zzhj.getInt(i2));
                i2++;
            }
        } else {
            int i3 = 0;
            while (i2 < size) {
                i3 = i + zzgp.zzau(((Integer) list.get(i2)).intValue());
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
        return zzy(list) + (size * zzgp.zzar(i));
    }

    static int zzz(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzgp.zzl(i, 0);
    }

    static int zzaa(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzgp.zzg(i, 0);
    }

    static int zzab(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzgp.zzc(i, true);
    }

    static int zzc(int i, List<?> list) {
        int i2;
        int i3;
        int size = list.size();
        int i4 = 0;
        if (size == 0) {
            return 0;
        }
        int zzar = zzgp.zzar(i) * size;
        if (list instanceof zzhx) {
            zzhx zzhx = (zzhx) list;
            while (i4 < size) {
                Object zzbe = zzhx.zzbe(i4);
                if (zzbe instanceof zzfw) {
                    i3 = zzgp.zzc((zzfw) zzbe);
                } else {
                    i3 = zzgp.zzbn((String) zzbe);
                }
                zzar += i3;
                i4++;
            }
        } else {
            while (i4 < size) {
                Object obj = list.get(i4);
                if (obj instanceof zzfw) {
                    i2 = zzgp.zzc((zzfw) obj);
                } else {
                    i2 = zzgp.zzbn((String) obj);
                }
                zzar += i2;
                i4++;
            }
        }
        return zzar;
    }

    static int zzc(int i, Object obj, zzjj zzjj) {
        if (obj instanceof zzhv) {
            return zzgp.zza(i, (zzhv) obj);
        }
        return zzgp.zzb(i, (zzio) obj, zzjj);
    }

    static int zzc(int i, List<?> list, zzjj zzjj) {
        int i2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzar = zzgp.zzar(i) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zzhv) {
                i2 = zzgp.zza((zzhv) obj);
            } else {
                i2 = zzgp.zza((zzio) obj, zzjj);
            }
            zzar += i2;
        }
        return zzar;
    }

    static int zzd(int i, List<zzfw> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzar = size * zzgp.zzar(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzar += zzgp.zzc((zzfw) list.get(i2));
        }
        return zzar;
    }

    static int zzd(int i, List<zzio> list, zzjj zzjj) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzgp.zzc(i, (zzio) list.get(i3), zzjj);
        }
        return i2;
    }

    public static zzkb<?, ?> zziq() {
        return zzwu;
    }

    public static zzkb<?, ?> zzir() {
        return zzwv;
    }

    public static zzkb<?, ?> zzis() {
        return zzww;
    }

    private static zzkb<?, ?> zzh(boolean z) {
        try {
            Class zziu = zziu();
            if (zziu == null) {
                return null;
            }
            return (zzkb) zziu.getConstructor(new Class[]{Boolean.TYPE}).newInstance(new Object[]{Boolean.valueOf(z)});
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzit() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zziu() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzf(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static <T> void zza(zzij zzij, T t, T t2, long j) {
        zzkh.zza((Object) t, j, zzij.zzd(zzkh.zzp(t, j), zzkh.zzp(t2, j)));
    }

    static <T, FT extends zzhc<FT>> void zza(zzgx<FT> zzgx, T t, T t2) {
        zzha zzk = zzgx.zzk(t2);
        if (!zzk.isEmpty()) {
            zzgx.zzl(t).zza(zzk);
        }
    }

    static <T, UT, UB> void zza(zzkb<UT, UB> zzkb, T t, T t2) {
        zzkb.zzg(t, zzkb.zzi(zzkb.zzaa(t), zzkb.zzaa(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzhm zzhm, UB ub, zzkb<UT, UB> zzkb) {
        UB ub2;
        if (zzhm == null) {
            return ub;
        }
        if (!(list instanceof RandomAccess)) {
            Iterator it = list.iterator();
            loop1:
            while (true) {
                ub2 = ub;
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (!zzhm.zzbd(intValue)) {
                        ub = zza(i, intValue, ub2, zzkb);
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
                if (zzhm.zzbd(intValue2)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue2));
                    }
                    i2++;
                } else {
                    ub2 = zza(i, intValue2, ub2, zzkb);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        }
        return ub2;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzkb<UT, UB> zzkb) {
        if (ub == null) {
            ub = zzkb.zzjd();
        }
        zzkb.zza(ub, i, (long) i2);
        return ub;
    }
}
