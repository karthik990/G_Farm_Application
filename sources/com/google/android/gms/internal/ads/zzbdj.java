package com.google.android.gms.internal.ads;

import java.lang.reflect.Field;
import java.util.Arrays;

final class zzbdj {
    /* access modifiers changed from: private */
    public final int flags;
    private final Object[] zzdwh;
    /* access modifiers changed from: private */
    public final int zzdwi;
    /* access modifiers changed from: private */
    public final int zzdwj;
    /* access modifiers changed from: private */
    public final int zzdwk;
    /* access modifiers changed from: private */
    public final int[] zzdwq;
    private final zzbdk zzdxf;
    private Class<?> zzdxg;
    /* access modifiers changed from: private */
    public final int zzdxh;
    private final int zzdxi;
    private final int zzdxj;
    /* access modifiers changed from: private */
    public final int zzdxk;
    /* access modifiers changed from: private */
    public final int zzdxl;
    /* access modifiers changed from: private */
    public final int zzdxm;
    private int zzdxn;
    private int zzdxo;
    private int zzdxp = Integer.MAX_VALUE;
    private int zzdxq = Integer.MIN_VALUE;
    private int zzdxr = 0;
    private int zzdxs = 0;
    private int zzdxt = 0;
    private int zzdxu = 0;
    private int zzdxv = 0;
    private int zzdxw;
    private int zzdxx;
    private int zzdxy;
    private int zzdxz;
    private int zzdya;
    private Field zzdyb;
    private Object zzdyc;
    private Object zzdyd;
    private Object zzdye;

    zzbdj(Class<?> cls, String str, Object[] objArr) {
        this.zzdxg = cls;
        this.zzdxf = new zzbdk(str);
        this.zzdwh = objArr;
        this.flags = this.zzdxf.next();
        this.zzdxh = this.zzdxf.next();
        int[] iArr = null;
        if (this.zzdxh == 0) {
            this.zzdxi = 0;
            this.zzdxj = 0;
            this.zzdwi = 0;
            this.zzdwj = 0;
            this.zzdxk = 0;
            this.zzdxl = 0;
            this.zzdwk = 0;
            this.zzdxm = 0;
            this.zzdwq = null;
            return;
        }
        this.zzdxi = this.zzdxf.next();
        this.zzdxj = this.zzdxf.next();
        this.zzdwi = this.zzdxf.next();
        this.zzdwj = this.zzdxf.next();
        this.zzdxl = this.zzdxf.next();
        this.zzdwk = this.zzdxf.next();
        this.zzdxk = this.zzdxf.next();
        this.zzdxm = this.zzdxf.next();
        int next = this.zzdxf.next();
        if (next != 0) {
            iArr = new int[next];
        }
        this.zzdwq = iArr;
        this.zzdxn = (this.zzdxi << 1) + this.zzdxj;
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    private final Object zzaey() {
        Object[] objArr = this.zzdwh;
        int i = this.zzdxn;
        this.zzdxn = i + 1;
        return objArr[i];
    }

    private final boolean zzafa() {
        return (this.flags & 1) == 1;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d0, code lost:
        if (zzafa() != false) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x015e, code lost:
        if (r1 != false) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0165, code lost:
        if (zzafa() != false) goto L_0x00d2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean next() {
        /*
            r5 = this;
            com.google.android.gms.internal.ads.zzbdk r0 = r5.zzdxf
            boolean r0 = r0.hasNext()
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            com.google.android.gms.internal.ads.zzbdk r0 = r5.zzdxf
            int r0 = r0.next()
            r5.zzdxw = r0
            com.google.android.gms.internal.ads.zzbdk r0 = r5.zzdxf
            int r0 = r0.next()
            r5.zzdxx = r0
            int r0 = r5.zzdxx
            r0 = r0 & 255(0xff, float:3.57E-43)
            r5.zzdxy = r0
            int r0 = r5.zzdxw
            int r2 = r5.zzdxp
            if (r0 >= r2) goto L_0x0028
            r5.zzdxp = r0
        L_0x0028:
            int r0 = r5.zzdxw
            int r2 = r5.zzdxq
            if (r0 <= r2) goto L_0x0030
            r5.zzdxq = r0
        L_0x0030:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.MAP
            int r2 = r2.mo28365id()
            r3 = 1
            if (r0 != r2) goto L_0x0041
            int r0 = r5.zzdxr
            int r0 = r0 + r3
            r5.zzdxr = r0
            goto L_0x005a
        L_0x0041:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.DOUBLE_LIST
            int r2 = r2.mo28365id()
            if (r0 < r2) goto L_0x005a
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.GROUP_LIST
            int r2 = r2.mo28365id()
            if (r0 > r2) goto L_0x005a
            int r0 = r5.zzdxs
            int r0 = r0 + r3
            r5.zzdxs = r0
        L_0x005a:
            int r0 = r5.zzdxv
            int r0 = r0 + r3
            r5.zzdxv = r0
            int r0 = r5.zzdxp
            int r2 = r5.zzdxw
            int r4 = r5.zzdxv
            boolean r0 = com.google.android.gms.internal.ads.zzbdo.zze(r0, r2, r4)
            if (r0 == 0) goto L_0x0076
            int r0 = r5.zzdxw
            int r0 = r0 + r3
            r5.zzdxu = r0
            int r0 = r5.zzdxu
            int r2 = r5.zzdxp
            int r0 = r0 - r2
            goto L_0x0079
        L_0x0076:
            int r0 = r5.zzdxt
            int r0 = r0 + r3
        L_0x0079:
            r5.zzdxt = r0
            int r0 = r5.zzdxx
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L_0x0083
            r0 = 1
            goto L_0x0084
        L_0x0083:
            r0 = 0
        L_0x0084:
            if (r0 == 0) goto L_0x0092
            int[] r0 = r5.zzdwq
            int r2 = r5.zzdxo
            int r4 = r2 + 1
            r5.zzdxo = r4
            int r4 = r5.zzdxw
            r0[r2] = r4
        L_0x0092:
            r0 = 0
            r5.zzdyc = r0
            r5.zzdyd = r0
            r5.zzdye = r0
            boolean r0 = r5.zzafb()
            if (r0 == 0) goto L_0x00e2
            com.google.android.gms.internal.ads.zzbdk r0 = r5.zzdxf
            int r0 = r0.next()
            r5.zzdxz = r0
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r1 = com.google.android.gms.internal.ads.zzbbj.MESSAGE
            int r1 = r1.mo28365id()
            int r1 = r1 + 51
            if (r0 == r1) goto L_0x00da
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r1 = com.google.android.gms.internal.ads.zzbbj.GROUP
            int r1 = r1.mo28365id()
            int r1 = r1 + 51
            if (r0 != r1) goto L_0x00c0
            goto L_0x00da
        L_0x00c0:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r1 = com.google.android.gms.internal.ads.zzbbj.ENUM
            int r1 = r1.mo28365id()
            int r1 = r1 + 51
            if (r0 != r1) goto L_0x0171
            boolean r0 = r5.zzafa()
            if (r0 == 0) goto L_0x0171
        L_0x00d2:
            java.lang.Object r0 = r5.zzaey()
            r5.zzdyd = r0
            goto L_0x0171
        L_0x00da:
            java.lang.Object r0 = r5.zzaey()
        L_0x00de:
            r5.zzdyc = r0
            goto L_0x0171
        L_0x00e2:
            java.lang.Class<?> r0 = r5.zzdxg
            java.lang.Object r2 = r5.zzaey()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.reflect.Field r0 = zza(r0, r2)
            r5.zzdyb = r0
            boolean r0 = r5.zzaff()
            if (r0 == 0) goto L_0x00fe
            com.google.android.gms.internal.ads.zzbdk r0 = r5.zzdxf
            int r0 = r0.next()
            r5.zzdya = r0
        L_0x00fe:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.MESSAGE
            int r2 = r2.mo28365id()
            if (r0 == r2) goto L_0x0169
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.GROUP
            int r2 = r2.mo28365id()
            if (r0 != r2) goto L_0x0113
            goto L_0x0169
        L_0x0113:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.MESSAGE_LIST
            int r2 = r2.mo28365id()
            if (r0 == r2) goto L_0x00da
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.GROUP_LIST
            int r2 = r2.mo28365id()
            if (r0 != r2) goto L_0x0128
            goto L_0x00da
        L_0x0128:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.ENUM
            int r2 = r2.mo28365id()
            if (r0 == r2) goto L_0x0161
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.ENUM_LIST
            int r2 = r2.mo28365id()
            if (r0 == r2) goto L_0x0161
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.ENUM_LIST_PACKED
            int r2 = r2.mo28365id()
            if (r0 != r2) goto L_0x0147
            goto L_0x0161
        L_0x0147:
            int r0 = r5.zzdxy
            com.google.android.gms.internal.ads.zzbbj r2 = com.google.android.gms.internal.ads.zzbbj.MAP
            int r2 = r2.mo28365id()
            if (r0 != r2) goto L_0x0171
            java.lang.Object r0 = r5.zzaey()
            r5.zzdye = r0
            int r0 = r5.zzdxx
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L_0x015e
            r1 = 1
        L_0x015e:
            if (r1 == 0) goto L_0x0171
            goto L_0x0167
        L_0x0161:
            boolean r0 = r5.zzafa()
            if (r0 == 0) goto L_0x0171
        L_0x0167:
            goto L_0x00d2
        L_0x0169:
            java.lang.reflect.Field r0 = r5.zzdyb
            java.lang.Class r0 = r0.getType()
            goto L_0x00de
        L_0x0171:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzbdj.next():boolean");
    }

    /* access modifiers changed from: 0000 */
    public final int zzaci() {
        return this.zzdxw;
    }

    /* access modifiers changed from: 0000 */
    public final int zzaez() {
        return this.zzdxy;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzafb() {
        return this.zzdxy > zzbbj.MAP.mo28365id();
    }

    /* access modifiers changed from: 0000 */
    public final Field zzafc() {
        int i = this.zzdxz << 1;
        Object obj = this.zzdwh[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzdxg, (String) obj);
        this.zzdwh[i] = zza;
        return zza;
    }

    /* access modifiers changed from: 0000 */
    public final Field zzafd() {
        int i = (this.zzdxz << 1) + 1;
        Object obj = this.zzdwh[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzdxg, (String) obj);
        this.zzdwh[i] = zza;
        return zza;
    }

    /* access modifiers changed from: 0000 */
    public final Field zzafe() {
        return this.zzdyb;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzaff() {
        return zzafa() && this.zzdxy <= zzbbj.GROUP.mo28365id();
    }

    /* access modifiers changed from: 0000 */
    public final Field zzafg() {
        int i = (this.zzdxi << 1) + (this.zzdya / 32);
        Object obj = this.zzdwh[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzdxg, (String) obj);
        this.zzdwh[i] = zza;
        return zza;
    }

    /* access modifiers changed from: 0000 */
    public final int zzafh() {
        return this.zzdya % 32;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzafi() {
        return (this.zzdxx & 256) != 0;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzafj() {
        return (this.zzdxx & 512) != 0;
    }

    /* access modifiers changed from: 0000 */
    public final Object zzafk() {
        return this.zzdyc;
    }

    /* access modifiers changed from: 0000 */
    public final Object zzafl() {
        return this.zzdyd;
    }

    /* access modifiers changed from: 0000 */
    public final Object zzafm() {
        return this.zzdye;
    }
}
