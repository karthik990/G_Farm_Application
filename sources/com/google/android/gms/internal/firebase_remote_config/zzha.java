package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzha<FieldDescriptorType extends zzhc<FieldDescriptorType>> {
    private static final zzha zzqd = new zzha(true);
    private final zzjm<FieldDescriptorType, Object> zzqa = zzjm.zzbn(16);
    private boolean zzqb;
    private boolean zzqc = false;

    private zzha() {
    }

    private zzha(boolean z) {
        zzer();
    }

    public static <T extends zzhc<T>> zzha<T> zzgn() {
        return zzqd;
    }

    /* access modifiers changed from: 0000 */
    public final boolean isEmpty() {
        return this.zzqa.isEmpty();
    }

    public final void zzer() {
        if (!this.zzqb) {
            this.zzqa.zzer();
            this.zzqb = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzqb;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzha)) {
            return false;
        }
        return this.zzqa.equals(((zzha) obj).zzqa);
    }

    public final int hashCode() {
        return this.zzqa.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzqc) {
            return new zzhu(this.zzqa.entrySet().iterator());
        }
        return this.zzqa.entrySet().iterator();
    }

    /* access modifiers changed from: 0000 */
    public final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzqc) {
            return new zzhu(this.zzqa.zzix().iterator());
        }
        return this.zzqa.zzix().iterator();
    }

    private final Object zza(FieldDescriptorType fielddescriptortype) {
        Object obj = this.zzqa.get(fielddescriptortype);
        return obj instanceof zzhr ? zzhr.zzhq() : obj;
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (!fielddescriptortype.zzgt()) {
            zza(fielddescriptortype.zzgr(), obj);
            r7 = obj;
        } else if (obj instanceof List) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fielddescriptortype.zzgr(), obj2);
            }
            r7 = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (r7 instanceof zzhr) {
            this.zzqc = true;
        }
        this.zzqa.put(fielddescriptortype, r7);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if ((r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzhr) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        if ((r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzhl) == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(com.google.android.gms.internal.firebase_remote_config.zzkq r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.firebase_remote_config.zzhk.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.firebase_remote_config.zzhb.zzqe
            com.google.android.gms.internal.firebase_remote_config.zzkv r2 = r2.zzjn()
            int r2 = r2.ordinal()
            r2 = r0[r2]
            r0 = 1
            r1 = 0
            switch(r2) {
                case 1: goto L_0x0040;
                case 2: goto L_0x003d;
                case 3: goto L_0x003a;
                case 4: goto L_0x0037;
                case 5: goto L_0x0034;
                case 6: goto L_0x0031;
                case 7: goto L_0x0028;
                case 8: goto L_0x001e;
                case 9: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0043
        L_0x0015:
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzio
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzhr
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x001e:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzhl
            if (r2 == 0) goto L_0x0043
        L_0x0026:
            r1 = 1
            goto L_0x0043
        L_0x0028:
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_remote_config.zzfw
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x0031:
            boolean r0 = r3 instanceof java.lang.String
            goto L_0x0042
        L_0x0034:
            boolean r0 = r3 instanceof java.lang.Boolean
            goto L_0x0042
        L_0x0037:
            boolean r0 = r3 instanceof java.lang.Double
            goto L_0x0042
        L_0x003a:
            boolean r0 = r3 instanceof java.lang.Float
            goto L_0x0042
        L_0x003d:
            boolean r0 = r3 instanceof java.lang.Long
            goto L_0x0042
        L_0x0040:
            boolean r0 = r3 instanceof java.lang.Integer
        L_0x0042:
            r1 = r0
        L_0x0043:
            if (r1 == 0) goto L_0x0046
            return
        L_0x0046:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            goto L_0x004f
        L_0x004e:
            throw r2
        L_0x004f:
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzha.zza(com.google.android.gms.internal.firebase_remote_config.zzkq, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzqa.zziv(); i++) {
            if (!zzb(this.zzqa.zzbo(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzqa.zziw()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzhc zzhc = (zzhc) entry.getKey();
        if (zzhc.zzgs() == zzkv.MESSAGE) {
            if (zzhc.zzgt()) {
                for (zzio isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzio) {
                    if (!((zzio) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof zzhr) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzha<FieldDescriptorType> zzha) {
        for (int i = 0; i < zzha.zzqa.zziv(); i++) {
            zzc(zzha.zzqa.zzbo(i));
        }
        for (Entry zzc : zzha.zzqa.zziw()) {
            zzc(zzc);
        }
    }

    private static Object zzn(Object obj) {
        if (obj instanceof zziv) {
            return ((zziv) obj).zzig();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final void zzc(Entry<FieldDescriptorType, Object> entry) {
        Object obj;
        zzhc zzhc = (zzhc) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzhr) {
            value = zzhr.zzhq();
        }
        if (zzhc.zzgt()) {
            Object zza = zza((FieldDescriptorType) zzhc);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzn : (List) value) {
                ((List) zza).add(zzn(zzn));
            }
            this.zzqa.put(zzhc, zza);
        } else if (zzhc.zzgs() == zzkv.MESSAGE) {
            Object zza2 = zza((FieldDescriptorType) zzhc);
            if (zza2 == null) {
                this.zzqa.put(zzhc, zzn(value));
                return;
            }
            if (zza2 instanceof zziv) {
                obj = zzhc.zza((zziv) zza2, (zziv) value);
            } else {
                obj = zzhc.zza(((zzio) zza2).zzgx(), (zzio) value).zzhd();
            }
            this.zzqa.put(zzhc, obj);
        } else {
            this.zzqa.put(zzhc, zzn(value));
        }
    }

    static void zza(zzgp zzgp, zzkq zzkq, int i, Object obj) throws IOException {
        if (zzkq == zzkq.GROUP) {
            zzio zzio = (zzio) obj;
            zzhk.zzf(zzio);
            zzgp.zzd(i, 3);
            zzio.zzb(zzgp);
            zzgp.zzd(i, 4);
            return;
        }
        zzgp.zzd(i, zzkq.zzjo());
        switch (zzhb.zzpo[zzkq.ordinal()]) {
            case 1:
                zzgp.zzc(((Double) obj).doubleValue());
                break;
            case 2:
                zzgp.zzb(((Float) obj).floatValue());
                return;
            case 3:
                zzgp.zzf(((Long) obj).longValue());
                return;
            case 4:
                zzgp.zzf(((Long) obj).longValue());
                return;
            case 5:
                zzgp.zzan(((Integer) obj).intValue());
                return;
            case 6:
                zzgp.zzh(((Long) obj).longValue());
                return;
            case 7:
                zzgp.zzaq(((Integer) obj).intValue());
                return;
            case 8:
                zzgp.zze(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzio) obj).zzb(zzgp);
                return;
            case 10:
                zzgp.zzb((zzio) obj);
                return;
            case 11:
                if (obj instanceof zzfw) {
                    zzgp.zzb((zzfw) obj);
                    return;
                } else {
                    zzgp.zzbm((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzfw) {
                    zzgp.zzb((zzfw) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzgp.zzd(bArr, 0, bArr.length);
                return;
            case 13:
                zzgp.zzao(((Integer) obj).intValue());
                return;
            case 14:
                zzgp.zzaq(((Integer) obj).intValue());
                return;
            case 15:
                zzgp.zzh(((Long) obj).longValue());
                return;
            case 16:
                zzgp.zzap(((Integer) obj).intValue());
                return;
            case 17:
                zzgp.zzg(((Long) obj).longValue());
                return;
            case 18:
                if (!(obj instanceof zzhl)) {
                    zzgp.zzan(((Integer) obj).intValue());
                    break;
                } else {
                    zzgp.zzan(((zzhl) obj).zzgq());
                    return;
                }
        }
    }

    public final int zzgo() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzqa.zziv(); i2++) {
            Entry zzbo = this.zzqa.zzbo(i2);
            i += zzb((zzhc) zzbo.getKey(), zzbo.getValue());
        }
        for (Entry entry : this.zzqa.zziw()) {
            i += zzb((zzhc) entry.getKey(), entry.getValue());
        }
        return i;
    }

    public final int zzgp() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzqa.zziv(); i2++) {
            i += zzd(this.zzqa.zzbo(i2));
        }
        for (Entry zzd : this.zzqa.zziw()) {
            i += zzd(zzd);
        }
        return i;
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzhc zzhc = (zzhc) entry.getKey();
        Object value = entry.getValue();
        if (zzhc.zzgs() != zzkv.MESSAGE || zzhc.zzgt() || zzhc.zzgu()) {
            return zzb(zzhc, value);
        }
        if (value instanceof zzhr) {
            return zzgp.zzb(((zzhc) entry.getKey()).zzgq(), (zzhv) (zzhr) value);
        }
        return zzgp.zzb(((zzhc) entry.getKey()).zzgq(), (zzio) value);
    }

    static int zza(zzkq zzkq, int i, Object obj) {
        int zzar = zzgp.zzar(i);
        if (zzkq == zzkq.GROUP) {
            zzhk.zzf((zzio) obj);
            zzar <<= 1;
        }
        return zzar + zzb(zzkq, obj);
    }

    private static int zzb(zzkq zzkq, Object obj) {
        switch (zzhb.zzpo[zzkq.ordinal()]) {
            case 1:
                return zzgp.zzd(((Double) obj).doubleValue());
            case 2:
                return zzgp.zzc(((Float) obj).floatValue());
            case 3:
                return zzgp.zzi(((Long) obj).longValue());
            case 4:
                return zzgp.zzj(((Long) obj).longValue());
            case 5:
                return zzgp.zzas(((Integer) obj).intValue());
            case 6:
                return zzgp.zzl(((Long) obj).longValue());
            case 7:
                return zzgp.zzav(((Integer) obj).intValue());
            case 8:
                return zzgp.zzf(((Boolean) obj).booleanValue());
            case 9:
                return zzgp.zzd((zzio) obj);
            case 10:
                if (obj instanceof zzhr) {
                    return zzgp.zza((zzhr) obj);
                }
                return zzgp.zzc((zzio) obj);
            case 11:
                if (obj instanceof zzfw) {
                    return zzgp.zzc((zzfw) obj);
                }
                return zzgp.zzbn((String) obj);
            case 12:
                if (obj instanceof zzfw) {
                    return zzgp.zzc((zzfw) obj);
                }
                return zzgp.zzc((byte[]) obj);
            case 13:
                return zzgp.zzat(((Integer) obj).intValue());
            case 14:
                return zzgp.zzaw(((Integer) obj).intValue());
            case 15:
                return zzgp.zzm(((Long) obj).longValue());
            case 16:
                return zzgp.zzau(((Integer) obj).intValue());
            case 17:
                return zzgp.zzk(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzhl) {
                    return zzgp.zzax(((zzhl) obj).zzgq());
                }
                return zzgp.zzax(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static int zzb(zzhc<?> zzhc, Object obj) {
        zzkq zzgr = zzhc.zzgr();
        int zzgq = zzhc.zzgq();
        if (!zzhc.zzgt()) {
            return zza(zzgr, zzgq, obj);
        }
        int i = 0;
        if (zzhc.zzgu()) {
            for (Object zzb : (List) obj) {
                i += zzb(zzgr, zzb);
            }
            return zzgp.zzar(zzgq) + i + zzgp.zzaz(i);
        }
        for (Object zza : (List) obj) {
            i += zza(zzgr, zzgq, zza);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzha zzha = new zzha();
        for (int i = 0; i < this.zzqa.zziv(); i++) {
            Entry zzbo = this.zzqa.zzbo(i);
            zzha.zza((FieldDescriptorType) (zzhc) zzbo.getKey(), zzbo.getValue());
        }
        for (Entry entry : this.zzqa.zziw()) {
            zzha.zza((FieldDescriptorType) (zzhc) entry.getKey(), entry.getValue());
        }
        zzha.zzqc = this.zzqc;
        return zzha;
    }
}
