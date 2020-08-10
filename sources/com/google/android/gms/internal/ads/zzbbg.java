package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzbbg<FieldDescriptorType extends zzbbi<FieldDescriptorType>> {
    private static final zzbbg zzdra = new zzbbg(true);
    private final zzbdp<FieldDescriptorType, Object> zzdqx = zzbdp.zzcx(16);
    private boolean zzdqy;
    private boolean zzdqz = false;

    private zzbbg() {
    }

    private zzbbg(boolean z) {
        zzaaz();
    }

    static int zza(zzbes zzbes, int i, Object obj) {
        int zzcd = zzbav.zzcd(i);
        if (zzbes == zzbes.GROUP) {
            zzbbq.zzi((zzbcu) obj);
            zzcd <<= 1;
        }
        return zzcd + zzb(zzbes, obj);
    }

    private final Object zza(FieldDescriptorType fielddescriptortype) {
        Object obj = this.zzdqx.get(fielddescriptortype);
        return obj instanceof zzbbx ? zzbbx.zzadu() : obj;
    }

    static void zza(zzbav zzbav, zzbes zzbes, int i, Object obj) throws IOException {
        if (zzbes == zzbes.GROUP) {
            zzbcu zzbcu = (zzbcu) obj;
            zzbbq.zzi(zzbcu);
            zzbav.zzl(i, 3);
            zzbcu.zzb(zzbav);
            zzbav.zzl(i, 4);
            return;
        }
        zzbav.zzl(i, zzbes.zzagm());
        switch (zzbes) {
            case DOUBLE:
                zzbav.zzb(((Double) obj).doubleValue());
                break;
            case FLOAT:
                zzbav.zzb(((Float) obj).floatValue());
                return;
            case INT64:
                zzbav.zzm(((Long) obj).longValue());
                return;
            case UINT64:
                zzbav.zzm(((Long) obj).longValue());
                return;
            case INT32:
                zzbav.zzbz(((Integer) obj).intValue());
                return;
            case FIXED64:
                zzbav.zzo(((Long) obj).longValue());
                return;
            case FIXED32:
                zzbav.zzcc(((Integer) obj).intValue());
                return;
            case BOOL:
                zzbav.zzap(((Boolean) obj).booleanValue());
                return;
            case GROUP:
                ((zzbcu) obj).zzb(zzbav);
                return;
            case MESSAGE:
                zzbav.zze((zzbcu) obj);
                return;
            case STRING:
                if (obj instanceof zzbah) {
                    zzbav.zzan((zzbah) obj);
                    return;
                } else {
                    zzbav.zzen((String) obj);
                    return;
                }
            case BYTES:
                if (obj instanceof zzbah) {
                    zzbav.zzan((zzbah) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzbav.zze(bArr, 0, bArr.length);
                return;
            case UINT32:
                zzbav.zzca(((Integer) obj).intValue());
                return;
            case SFIXED32:
                zzbav.zzcc(((Integer) obj).intValue());
                return;
            case SFIXED64:
                zzbav.zzo(((Long) obj).longValue());
                return;
            case SINT32:
                zzbav.zzcb(((Integer) obj).intValue());
                return;
            case SINT64:
                zzbav.zzn(((Long) obj).longValue());
                return;
            case ENUM:
                if (!(obj instanceof zzbbr)) {
                    zzbav.zzbz(((Integer) obj).intValue());
                    break;
                } else {
                    zzbav.zzbz(((zzbbr) obj).zzhq());
                    return;
                }
        }
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (!fielddescriptortype.zzada()) {
            zza(fielddescriptortype.zzacy(), obj);
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
                zza(fielddescriptortype.zzacy(), obj2);
            }
            r7 = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (r7 instanceof zzbbx) {
            this.zzdqz = true;
        }
        this.zzdqx.put(fielddescriptortype, r7);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if ((r3 instanceof byte[]) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if ((r3 instanceof com.google.android.gms.internal.ads.zzbbx) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        if ((r3 instanceof com.google.android.gms.internal.ads.zzbbr) == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(com.google.android.gms.internal.ads.zzbes r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.ads.zzbbq.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.ads.zzbbh.zzdrb
            com.google.android.gms.internal.ads.zzbex r2 = r2.zzagl()
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
            boolean r2 = r3 instanceof com.google.android.gms.internal.ads.zzbcu
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.ads.zzbbx
            if (r2 == 0) goto L_0x0043
            goto L_0x0026
        L_0x001e:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L_0x0026
            boolean r2 = r3 instanceof com.google.android.gms.internal.ads.zzbbr
            if (r2 == 0) goto L_0x0043
        L_0x0026:
            r1 = 1
            goto L_0x0043
        L_0x0028:
            boolean r2 = r3 instanceof com.google.android.gms.internal.ads.zzbah
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzbbg.zza(com.google.android.gms.internal.ads.zzbes, java.lang.Object):void");
    }

    public static <T extends zzbbi<T>> zzbbg<T> zzacv() {
        return zzdra;
    }

    private static int zzb(zzbbi<?> zzbbi, Object obj) {
        zzbes zzacy = zzbbi.zzacy();
        int zzhq = zzbbi.zzhq();
        if (!zzbbi.zzada()) {
            return zza(zzacy, zzhq, obj);
        }
        int i = 0;
        List<Object> list = (List) obj;
        if (zzbbi.zzadb()) {
            for (Object zzb : list) {
                i += zzb(zzacy, zzb);
            }
            return zzbav.zzcd(zzhq) + i + zzbav.zzcl(i);
        }
        for (Object zza : list) {
            i += zza(zzacy, zzhq, zza);
        }
        return i;
    }

    private static int zzb(zzbes zzbes, Object obj) {
        switch (zzbes) {
            case DOUBLE:
                return zzbav.zzc(((Double) obj).doubleValue());
            case FLOAT:
                return zzbav.zzc(((Float) obj).floatValue());
            case INT64:
                return zzbav.zzp(((Long) obj).longValue());
            case UINT64:
                return zzbav.zzq(((Long) obj).longValue());
            case INT32:
                return zzbav.zzce(((Integer) obj).intValue());
            case FIXED64:
                return zzbav.zzs(((Long) obj).longValue());
            case FIXED32:
                return zzbav.zzch(((Integer) obj).intValue());
            case BOOL:
                return zzbav.zzaq(((Boolean) obj).booleanValue());
            case GROUP:
                return zzbav.zzg((zzbcu) obj);
            case MESSAGE:
                return obj instanceof zzbbx ? zzbav.zza((zzbcb) (zzbbx) obj) : zzbav.zzf((zzbcu) obj);
            case STRING:
                return obj instanceof zzbah ? zzbav.zzao((zzbah) obj) : zzbav.zzeo((String) obj);
            case BYTES:
                return obj instanceof zzbah ? zzbav.zzao((zzbah) obj) : zzbav.zzr((byte[]) obj);
            case UINT32:
                return zzbav.zzcf(((Integer) obj).intValue());
            case SFIXED32:
                return zzbav.zzci(((Integer) obj).intValue());
            case SFIXED64:
                return zzbav.zzt(((Long) obj).longValue());
            case SINT32:
                return zzbav.zzcg(((Integer) obj).intValue());
            case SINT64:
                return zzbav.zzr(((Long) obj).longValue());
            case ENUM:
                return obj instanceof zzbbr ? zzbav.zzcj(((zzbbr) obj).zzhq()) : zzbav.zzcj(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static boolean zzb(Entry<FieldDescriptorType, Object> entry) {
        zzbbi zzbbi = (zzbbi) entry.getKey();
        if (zzbbi.zzacz() == zzbex.MESSAGE) {
            boolean zzada = zzbbi.zzada();
            Object value = entry.getValue();
            if (zzada) {
                for (zzbcu isInitialized : (List) value) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            } else if (value instanceof zzbcu) {
                if (!((zzbcu) value).isInitialized()) {
                    return false;
                }
            } else if (value instanceof zzbbx) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    private final void zzc(Entry<FieldDescriptorType, Object> entry) {
        zzbbi zzbbi = (zzbbi) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzbbx) {
            value = zzbbx.zzadu();
        }
        if (zzbbi.zzada()) {
            Object zza = zza((FieldDescriptorType) zzbbi);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzp : (List) value) {
                ((List) zza).add(zzp(zzp));
            }
            this.zzdqx.put(zzbbi, zza);
        } else if (zzbbi.zzacz() == zzbex.MESSAGE) {
            Object zza2 = zza((FieldDescriptorType) zzbbi);
            if (zza2 == null) {
                this.zzdqx.put(zzbbi, zzp(value));
            } else {
                this.zzdqx.put(zzbbi, zza2 instanceof zzbdb ? zzbbi.zza((zzbdb) zza2, (zzbdb) value) : zzbbi.zza(((zzbcu) zza2).zzade(), (zzbcu) value).zzadk());
            }
        } else {
            this.zzdqx.put(zzbbi, zzp(value));
        }
    }

    private static int zzd(Entry<FieldDescriptorType, Object> entry) {
        zzbbi zzbbi = (zzbbi) entry.getKey();
        Object value = entry.getValue();
        if (zzbbi.zzacz() != zzbex.MESSAGE || zzbbi.zzada() || zzbbi.zzadb()) {
            return zzb(zzbbi, value);
        }
        boolean z = value instanceof zzbbx;
        int zzhq = ((zzbbi) entry.getKey()).zzhq();
        return z ? zzbav.zzb(zzhq, (zzbcb) (zzbbx) value) : zzbav.zzb(zzhq, (zzbcu) value);
    }

    private static Object zzp(Object obj) {
        if (obj instanceof zzbdb) {
            return ((zzbdb) obj).zzaek();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzbbg zzbbg = new zzbbg();
        for (int i = 0; i < this.zzdqx.zzafs(); i++) {
            Entry zzcy = this.zzdqx.zzcy(i);
            zzbbg.zza((FieldDescriptorType) (zzbbi) zzcy.getKey(), zzcy.getValue());
        }
        for (Entry entry : this.zzdqx.zzaft()) {
            zzbbg.zza((FieldDescriptorType) (zzbbi) entry.getKey(), entry.getValue());
        }
        zzbbg.zzdqz = this.zzdqz;
        return zzbbg;
    }

    /* access modifiers changed from: 0000 */
    public final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        return this.zzdqz ? new zzbca(this.zzdqx.zzafu().iterator()) : this.zzdqx.zzafu().iterator();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbbg)) {
            return false;
        }
        return this.zzdqx.equals(((zzbbg) obj).zzdqx);
    }

    public final int hashCode() {
        return this.zzdqx.hashCode();
    }

    /* access modifiers changed from: 0000 */
    public final boolean isEmpty() {
        return this.zzdqx.isEmpty();
    }

    public final boolean isImmutable() {
        return this.zzdqy;
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzdqx.zzafs(); i++) {
            if (!zzb(this.zzdqx.zzcy(i))) {
                return false;
            }
        }
        for (Entry zzb : this.zzdqx.zzaft()) {
            if (!zzb(zzb)) {
                return false;
            }
        }
        return true;
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzdqz ? new zzbca(this.zzdqx.entrySet().iterator()) : this.zzdqx.entrySet().iterator();
    }

    public final void zza(zzbbg<FieldDescriptorType> zzbbg) {
        for (int i = 0; i < zzbbg.zzdqx.zzafs(); i++) {
            zzc(zzbbg.zzdqx.zzcy(i));
        }
        for (Entry zzc : zzbbg.zzdqx.zzaft()) {
            zzc(zzc);
        }
    }

    public final void zzaaz() {
        if (!this.zzdqy) {
            this.zzdqx.zzaaz();
            this.zzdqy = true;
        }
    }

    public final int zzacw() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzdqx.zzafs(); i2++) {
            Entry zzcy = this.zzdqx.zzcy(i2);
            i += zzb((zzbbi) zzcy.getKey(), zzcy.getValue());
        }
        for (Entry entry : this.zzdqx.zzaft()) {
            i += zzb((zzbbi) entry.getKey(), entry.getValue());
        }
        return i;
    }

    public final int zzacx() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzdqx.zzafs(); i2++) {
            i += zzd(this.zzdqx.zzcy(i2));
        }
        for (Entry zzd : this.zzdqx.zzaft()) {
            i += zzd(zzd);
        }
        return i;
    }
}
