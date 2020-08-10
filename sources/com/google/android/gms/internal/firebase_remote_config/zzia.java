package com.google.android.gms.internal.firebase_remote_config;

import java.util.Collections;
import java.util.List;

final class zzia extends zzhy {
    private static final Class<?> zzuv = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzia() {
        super();
    }

    /* access modifiers changed from: 0000 */
    public final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(Object obj, long j) {
        Object obj2;
        List list = (List) zzkh.zzp(obj, j);
        if (list instanceof zzhx) {
            obj2 = ((zzhx) list).zzht();
        } else if (!zzuv.isAssignableFrom(list.getClass())) {
            if (!(list instanceof zzja) || !(list instanceof zzhn)) {
                obj2 = Collections.unmodifiableList(list);
            } else {
                zzhn zzhn = (zzhn) list;
                if (zzhn.zzeq()) {
                    zzhn.zzer();
                }
                return;
            }
        } else {
            return;
        }
        zzkh.zza(obj, j, obj2);
    }

    /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.firebase_remote_config.zzfq, java.lang.Object, com.google.android.gms.internal.firebase_remote_config.zzhw] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.util.List<L>] */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <L> java.util.List<L> zza(java.lang.Object r3, long r4, int r6) {
        /*
            java.util.List r0 = zzc(r3, r4)
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x002d
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzhx
            if (r1 == 0) goto L_0x0014
            com.google.android.gms.internal.firebase_remote_config.zzhw r0 = new com.google.android.gms.internal.firebase_remote_config.zzhw
            r0.<init>(r6)
            goto L_0x0029
        L_0x0014:
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzja
            if (r1 == 0) goto L_0x0024
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzhn
            if (r1 == 0) goto L_0x0024
            com.google.android.gms.internal.firebase_remote_config.zzhn r0 = (com.google.android.gms.internal.firebase_remote_config.zzhn) r0
            com.google.android.gms.internal.firebase_remote_config.zzhn r6 = r0.zzu(r6)
            r0 = r6
            goto L_0x0029
        L_0x0024:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r6)
        L_0x0029:
            com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r3, r4, r0)
            goto L_0x007f
        L_0x002d:
            java.lang.Class<?> r1 = zzuv
            java.lang.Class r2 = r0.getClass()
            boolean r1 = r1.isAssignableFrom(r2)
            if (r1 == 0) goto L_0x004b
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.size()
            int r2 = r2 + r6
            r1.<init>(r2)
            r1.addAll(r0)
            com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r3, r4, r1)
        L_0x0049:
            r0 = r1
            goto L_0x007f
        L_0x004b:
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzke
            if (r1 == 0) goto L_0x0062
            com.google.android.gms.internal.firebase_remote_config.zzhw r1 = new com.google.android.gms.internal.firebase_remote_config.zzhw
            int r2 = r0.size()
            int r2 = r2 + r6
            r1.<init>(r2)
            com.google.android.gms.internal.firebase_remote_config.zzke r0 = (com.google.android.gms.internal.firebase_remote_config.zzke) r0
            r1.addAll(r0)
            com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r3, r4, r1)
            goto L_0x0049
        L_0x0062:
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzja
            if (r1 == 0) goto L_0x007f
            boolean r1 = r0 instanceof com.google.android.gms.internal.firebase_remote_config.zzhn
            if (r1 == 0) goto L_0x007f
            r1 = r0
            com.google.android.gms.internal.firebase_remote_config.zzhn r1 = (com.google.android.gms.internal.firebase_remote_config.zzhn) r1
            boolean r2 = r1.zzeq()
            if (r2 != 0) goto L_0x007f
            int r0 = r0.size()
            int r0 = r0 + r6
            com.google.android.gms.internal.firebase_remote_config.zzhn r0 = r1.zzu(r0)
            com.google.android.gms.internal.firebase_remote_config.zzkh.zza(r3, r4, r0)
        L_0x007f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzia.zza(java.lang.Object, long, int):java.util.List");
    }

    /* access modifiers changed from: 0000 */
    public final <E> void zza(Object obj, Object obj2, long j) {
        List zzc = zzc(obj2, j);
        List zza = zza(obj, j, zzc.size());
        int size = zza.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza.addAll(zzc);
        }
        if (size > 0) {
            zzc = zza;
        }
        zzkh.zza(obj, j, (Object) zzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzkh.zzp(obj, j);
    }
}
