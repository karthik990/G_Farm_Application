package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzhi<FieldDescriptorType extends zzhk<FieldDescriptorType>> {
    private static final zzhi zzxk = new zzhi(true);
    final zzjt<FieldDescriptorType, Object> zzxh = zzjt.zzbe(16);
    private boolean zzxi;
    private boolean zzxj = false;

    private zzhi() {
    }

    private zzhi(boolean z) {
        zzfy();
    }

    public static <T extends zzhk<T>> zzhi<T> zzhs() {
        return zzxk;
    }

    public final void zzfy() {
        if (!this.zzxi) {
            this.zzxh.zzfy();
            this.zzxi = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzxi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhi)) {
            return false;
        }
        return this.zzxh.equals(((zzhi) obj).zzxh);
    }

    public final int hashCode() {
        return this.zzxh.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzxj) {
            return new zzii(this.zzxh.entrySet().iterator());
        }
        return this.zzxh.entrySet().iterator();
    }

    /* access modifiers changed from: 0000 */
    public final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzxj) {
            return new zzii(this.zzxh.zzka().iterator());
        }
        return this.zzxh.zzka().iterator();
    }

    private final Object zza(FieldDescriptorType fielddescriptortype) {
        Object obj = this.zzxh.get(fielddescriptortype);
        return obj instanceof zzid ? zzid.zzja() : obj;
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (!fielddescriptortype.zzhz()) {
            zza(fielddescriptortype.zzhx(), obj);
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
                zza(fielddescriptortype.zzhx(), obj2);
            }
            r7 = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (r7 instanceof zzid) {
            this.zzxj = true;
        }
        this.zzxh.put(fielddescriptortype, r7);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if ((r3 instanceof com.google.android.gms.internal.firebase_auth.zzid) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        if ((r3 instanceof com.google.android.gms.internal.firebase_auth.zzhw) == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(com.google.android.gms.internal.firebase_auth.zzlb r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.firebase_auth.zzht.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.firebase_auth.zzhl.zzxn
            com.google.android.gms.internal.firebase_auth.zzle r2 = r2.zzkx()
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
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzjc
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzid
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x001e:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzhw
            if (r2 == 0) goto L_0x0043
        L_0x0026:
            r1 = 1
            goto L_0x0043
        L_0x0028:
            boolean r2 = r3 instanceof com.google.android.gms.internal.firebase_auth.zzgf
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzhi.zza(com.google.android.gms.internal.firebase_auth.zzlb, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzxh.zzjy(); i++) {
            if (!zzb(this.zzxh.zzbf(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzxh.zzjz()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzhk zzhk = (zzhk) entry.getKey();
        if (zzhk.zzhy() == zzle.MESSAGE) {
            if (zzhk.zzhz()) {
                for (zzjc isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzjc) {
                    if (!((zzjc) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof zzid) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzhi<FieldDescriptorType> zzhi) {
        for (int i = 0; i < zzhi.zzxh.zzjy(); i++) {
            zzc(zzhi.zzxh.zzbf(i));
        }
        for (Entry zzc : zzhi.zzxh.zzjz()) {
            zzc(zzc);
        }
    }

    private static Object zzg(Object obj) {
        if (obj instanceof zzji) {
            return ((zzji) obj).zzfv();
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
        zzhk zzhk = (zzhk) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzid) {
            value = zzid.zzja();
        }
        if (zzhk.zzhz()) {
            Object zza = zza((FieldDescriptorType) zzhk);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzg : (List) value) {
                ((List) zza).add(zzg(zzg));
            }
            this.zzxh.put(zzhk, zza);
        } else if (zzhk.zzhy() == zzle.MESSAGE) {
            Object zza2 = zza((FieldDescriptorType) zzhk);
            if (zza2 == null) {
                this.zzxh.put(zzhk, zzg(value));
                return;
            }
            if (zza2 instanceof zzji) {
                obj = zzhk.zza((zzji) zza2, (zzji) value);
            } else {
                obj = zzhk.zza(((zzjc) zza2).zzin(), (zzjc) value).zzih();
            }
            this.zzxh.put(zzhk, obj);
        } else {
            this.zzxh.put(zzhk, zzg(value));
        }
    }

    static void zza(zzha zzha, zzlb zzlb, int i, Object obj) throws IOException {
        if (zzlb == zzlb.GROUP) {
            zzjc zzjc = (zzjc) obj;
            zzht.zzg(zzjc);
            zzha.zze(i, 3);
            zzjc.zzb(zzha);
            zzha.zze(i, 4);
            return;
        }
        zzha.zze(i, zzlb.zzky());
        switch (zzhl.zzws[zzlb.ordinal()]) {
            case 1:
                zzha.zza(((Double) obj).doubleValue());
                break;
            case 2:
                zzha.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzha.zzb(((Long) obj).longValue());
                return;
            case 4:
                zzha.zzb(((Long) obj).longValue());
                return;
            case 5:
                zzha.zzag(((Integer) obj).intValue());
                return;
            case 6:
                zzha.zzd(((Long) obj).longValue());
                return;
            case 7:
                zzha.zzaj(((Integer) obj).intValue());
                return;
            case 8:
                zzha.zzt(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzjc) obj).zzb(zzha);
                return;
            case 10:
                zzha.zzc((zzjc) obj);
                return;
            case 11:
                if (obj instanceof zzgf) {
                    zzha.zza((zzgf) obj);
                    return;
                } else {
                    zzha.zzdi((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzgf) {
                    zzha.zza((zzgf) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzha.zzd(bArr, 0, bArr.length);
                return;
            case 13:
                zzha.zzah(((Integer) obj).intValue());
                return;
            case 14:
                zzha.zzaj(((Integer) obj).intValue());
                return;
            case 15:
                zzha.zzd(((Long) obj).longValue());
                return;
            case 16:
                zzha.zzai(((Integer) obj).intValue());
                return;
            case 17:
                zzha.zzc(((Long) obj).longValue());
                return;
            case 18:
                if (!(obj instanceof zzhw)) {
                    zzha.zzag(((Integer) obj).intValue());
                    break;
                } else {
                    zzha.zzag(((zzhw) obj).zzbq());
                    return;
                }
        }
    }

    public final int zzht() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzxh.zzjy(); i2++) {
            i += zzd(this.zzxh.zzbf(i2));
        }
        for (Entry zzd : this.zzxh.zzjz()) {
            i += zzd(zzd);
        }
        return i;
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzhk zzhk = (zzhk) entry.getKey();
        Object value = entry.getValue();
        if (zzhk.zzhy() != zzle.MESSAGE || zzhk.zzhz() || zzhk.zzia()) {
            return zzb(zzhk, value);
        }
        if (value instanceof zzid) {
            return zzha.zzb(((zzhk) entry.getKey()).zzbq(), (zzih) (zzid) value);
        }
        return zzha.zzb(((zzhk) entry.getKey()).zzbq(), (zzjc) value);
    }

    static int zza(zzlb zzlb, int i, Object obj) {
        int zzak = zzha.zzak(i);
        if (zzlb == zzlb.GROUP) {
            zzht.zzg((zzjc) obj);
            zzak <<= 1;
        }
        return zzak + zzb(zzlb, obj);
    }

    private static int zzb(zzlb zzlb, Object obj) {
        switch (zzhl.zzws[zzlb.ordinal()]) {
            case 1:
                return zzha.zzb(((Double) obj).doubleValue());
            case 2:
                return zzha.zzb(((Float) obj).floatValue());
            case 3:
                return zzha.zze(((Long) obj).longValue());
            case 4:
                return zzha.zzf(((Long) obj).longValue());
            case 5:
                return zzha.zzal(((Integer) obj).intValue());
            case 6:
                return zzha.zzh(((Long) obj).longValue());
            case 7:
                return zzha.zzao(((Integer) obj).intValue());
            case 8:
                return zzha.zzu(((Boolean) obj).booleanValue());
            case 9:
                return zzha.zze((zzjc) obj);
            case 10:
                if (obj instanceof zzid) {
                    return zzha.zza((zzih) (zzid) obj);
                }
                return zzha.zzd((zzjc) obj);
            case 11:
                if (obj instanceof zzgf) {
                    return zzha.zzb((zzgf) obj);
                }
                return zzha.zzdj((String) obj);
            case 12:
                if (obj instanceof zzgf) {
                    return zzha.zzb((zzgf) obj);
                }
                return zzha.zzd((byte[]) obj);
            case 13:
                return zzha.zzam(((Integer) obj).intValue());
            case 14:
                return zzha.zzap(((Integer) obj).intValue());
            case 15:
                return zzha.zzi(((Long) obj).longValue());
            case 16:
                return zzha.zzan(((Integer) obj).intValue());
            case 17:
                return zzha.zzg(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzhw) {
                    return zzha.zzaq(((zzhw) obj).zzbq());
                }
                return zzha.zzaq(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzhk<?> zzhk, Object obj) {
        zzlb zzhx = zzhk.zzhx();
        int zzbq = zzhk.zzbq();
        if (!zzhk.zzhz()) {
            return zza(zzhx, zzbq, obj);
        }
        int i = 0;
        if (zzhk.zzia()) {
            for (Object zzb : (List) obj) {
                i += zzb(zzhx, zzb);
            }
            return zzha.zzak(zzbq) + i + zzha.zzas(i);
        }
        for (Object zza : (List) obj) {
            i += zza(zzhx, zzbq, zza);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzhi zzhi = new zzhi();
        for (int i = 0; i < this.zzxh.zzjy(); i++) {
            Entry zzbf = this.zzxh.zzbf(i);
            zzhi.zza((FieldDescriptorType) (zzhk) zzbf.getKey(), zzbf.getValue());
        }
        for (Entry entry : this.zzxh.zzjz()) {
            zzhi.zza((FieldDescriptorType) (zzhk) entry.getKey(), entry.getValue());
        }
        zzhi.zzxj = this.zzxj;
        return zzhi;
    }
}
