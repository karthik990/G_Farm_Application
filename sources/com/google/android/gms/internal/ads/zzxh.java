package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.ads.internal.zzbv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@zzadh
public final class zzxh implements zzww {
    private final Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public final long mStartTime;
    private final boolean zzael;
    private final zzwy zzbtj;
    private final boolean zzbtn;
    private final boolean zzbto;
    private final zzaef zzbuc;
    /* access modifiers changed from: private */
    public final long zzbud;
    private final int zzbue;
    /* access modifiers changed from: private */
    public boolean zzbuf = false;
    /* access modifiers changed from: private */
    public final Map<zzanz<zzxe>, zzxb> zzbug = new HashMap();
    private final String zzbuh;
    private List<zzxe> zzbui = new ArrayList();
    private final zzxn zzwh;

    public zzxh(Context context, zzaef zzaef, zzxn zzxn, zzwy zzwy, boolean z, boolean z2, String str, long j, long j2, int i, boolean z3) {
        this.mContext = context;
        this.zzbuc = zzaef;
        this.zzwh = zzxn;
        this.zzbtj = zzwy;
        this.zzael = z;
        this.zzbtn = z2;
        this.zzbuh = str;
        this.mStartTime = j;
        this.zzbud = j2;
        this.zzbue = 2;
        this.zzbto = z3;
    }

    private final void zza(zzanz<zzxe> zzanz) {
        zzakk.zzcrm.post(new zzxj(this, zzanz));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        if (r4.hasNext() == false) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        r0 = (com.google.android.gms.internal.ads.zzanz) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r1 = (com.google.android.gms.internal.ads.zzxe) r0.get();
        r3.zzbui.add(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002b, code lost:
        if (r1 == null) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002f, code lost:
        if (r1.zzbtv != 0) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        zza(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
        com.google.android.gms.internal.ads.zzakb.zzc("Exception while processing an adapter; continuing with other adapters", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003e, code lost:
        zza(null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        return new com.google.android.gms.internal.ads.zzxe(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r4 = r4.iterator();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.ads.zzxe zzi(java.util.List<com.google.android.gms.internal.ads.zzanz<com.google.android.gms.internal.ads.zzxe>> r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.zzbuf     // Catch:{ all -> 0x0049 }
            if (r1 == 0) goto L_0x000f
            com.google.android.gms.internal.ads.zzxe r4 = new com.google.android.gms.internal.ads.zzxe     // Catch:{ all -> 0x0049 }
            r1 = -1
            r4.<init>(r1)     // Catch:{ all -> 0x0049 }
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            return r4
        L_0x000f:
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            java.util.Iterator r4 = r4.iterator()
        L_0x0014:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x003e
            java.lang.Object r0 = r4.next()
            com.google.android.gms.internal.ads.zzanz r0 = (com.google.android.gms.internal.ads.zzanz) r0
            java.lang.Object r1 = r0.get()     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            com.google.android.gms.internal.ads.zzxe r1 = (com.google.android.gms.internal.ads.zzxe) r1     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            java.util.List<com.google.android.gms.internal.ads.zzxe> r2 = r3.zzbui     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            r2.add(r1)     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            if (r1 == 0) goto L_0x0014
            int r2 = r1.zzbtv     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            if (r2 != 0) goto L_0x0014
            r3.zza(r0)     // Catch:{ InterruptedException -> 0x0037, ExecutionException -> 0x0035 }
            return r1
        L_0x0035:
            r0 = move-exception
            goto L_0x0038
        L_0x0037:
            r0 = move-exception
        L_0x0038:
            java.lang.String r1 = "Exception while processing an adapter; continuing with other adapters"
            com.google.android.gms.internal.ads.zzakb.zzc(r1, r0)
            goto L_0x0014
        L_0x003e:
            r4 = 0
            r3.zza(r4)
            com.google.android.gms.internal.ads.zzxe r4 = new com.google.android.gms.internal.ads.zzxe
            r0 = 1
            r4.<init>(r0)
            return r4
        L_0x0049:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            goto L_0x004d
        L_0x004c:
            throw r4
        L_0x004d:
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzxh.zzi(java.util.List):com.google.android.gms.internal.ads.zzxe");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        if (r13.zzbtj.zzbsy == -1) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        r0 = r13.zzbtj.zzbsy;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        r0 = androidx.work.WorkRequest.MIN_BACKOFF_MILLIS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        r14 = r14.iterator();
        r3 = null;
        r1 = r0;
        r0 = null;
        r4 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        if (r14.hasNext() == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        r5 = (com.google.android.gms.internal.ads.zzanz) r14.next();
        r6 = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        if (r1 != 0) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        if (r5.isDone() == false) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        r10 = r5.get();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
        r10 = (com.google.android.gms.internal.ads.zzxe) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0050, code lost:
        r10 = r5.get(r1, java.util.concurrent.TimeUnit.MILLISECONDS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
        r13.zzbui.add(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005c, code lost:
        if (r10 == null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (r10.zzbtv != 0) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
        r11 = r10.zzbua;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        if (r11 == null) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006a, code lost:
        if (r11.zzmm() <= r4) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0070, code lost:
        r4 = r11.zzmm();
        r3 = r5;
        r0 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0074, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0076, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        com.google.android.gms.internal.ads.zzakb.zzc("Exception while processing an adapter; continuing with other adapters", r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0091, code lost:
        java.lang.Math.max(r1 - (com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis() - r6), 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009e, code lost:
        throw r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009f, code lost:
        zza(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a2, code lost:
        if (r0 != null) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00aa, code lost:
        return new com.google.android.gms.internal.ads.zzxe(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ab, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.ads.zzxe zzj(java.util.List<com.google.android.gms.internal.ads.zzanz<com.google.android.gms.internal.ads.zzxe>> r14) {
        /*
            r13 = this;
            java.lang.Object r0 = r13.mLock
            monitor-enter(r0)
            boolean r1 = r13.zzbuf     // Catch:{ all -> 0x00ac }
            r2 = -1
            if (r1 == 0) goto L_0x000f
            com.google.android.gms.internal.ads.zzxe r14 = new com.google.android.gms.internal.ads.zzxe     // Catch:{ all -> 0x00ac }
            r14.<init>(r2)     // Catch:{ all -> 0x00ac }
            monitor-exit(r0)     // Catch:{ all -> 0x00ac }
            return r14
        L_0x000f:
            monitor-exit(r0)     // Catch:{ all -> 0x00ac }
            com.google.android.gms.internal.ads.zzwy r0 = r13.zzbtj
            long r0 = r0.zzbsy
            r3 = -1
            int r5 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x001f
            com.google.android.gms.internal.ads.zzwy r0 = r13.zzbtj
            long r0 = r0.zzbsy
            goto L_0x0021
        L_0x001f:
            r0 = 10000(0x2710, double:4.9407E-320)
        L_0x0021:
            java.util.Iterator r14 = r14.iterator()
            r3 = 0
            r1 = r0
            r0 = r3
            r4 = -1
        L_0x0029:
            boolean r5 = r14.hasNext()
            if (r5 == 0) goto L_0x009f
            java.lang.Object r5 = r14.next()
            com.google.android.gms.internal.ads.zzanz r5 = (com.google.android.gms.internal.ads.zzanz) r5
            com.google.android.gms.common.util.Clock r6 = com.google.android.gms.ads.internal.zzbv.zzer()
            long r6 = r6.currentTimeMillis()
            r8 = 0
            int r10 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r10 != 0) goto L_0x0050
            boolean r10 = r5.isDone()     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            if (r10 == 0) goto L_0x0050
            java.lang.Object r10 = r5.get()     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
        L_0x004d:
            com.google.android.gms.internal.ads.zzxe r10 = (com.google.android.gms.internal.ads.zzxe) r10     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            goto L_0x0057
        L_0x0050:
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            java.lang.Object r10 = r5.get(r1, r10)     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            goto L_0x004d
        L_0x0057:
            java.util.List<com.google.android.gms.internal.ads.zzxe> r11 = r13.zzbui     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            r11.add(r10)     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            if (r10 == 0) goto L_0x0082
            int r11 = r10.zzbtv     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            if (r11 != 0) goto L_0x0082
            com.google.android.gms.internal.ads.zzxw r11 = r10.zzbua     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            if (r11 == 0) goto L_0x0082
            int r12 = r11.zzmm()     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            if (r12 <= r4) goto L_0x0082
            int r0 = r11.zzmm()     // Catch:{ InterruptedException -> 0x007c, ExecutionException -> 0x007a, RemoteException -> 0x0078, TimeoutException -> 0x0076 }
            r4 = r0
            r3 = r5
            r0 = r10
            goto L_0x0082
        L_0x0074:
            r14 = move-exception
            goto L_0x0091
        L_0x0076:
            r5 = move-exception
            goto L_0x007d
        L_0x0078:
            r5 = move-exception
            goto L_0x007d
        L_0x007a:
            r5 = move-exception
            goto L_0x007d
        L_0x007c:
            r5 = move-exception
        L_0x007d:
            java.lang.String r10 = "Exception while processing an adapter; continuing with other adapters"
            com.google.android.gms.internal.ads.zzakb.zzc(r10, r5)     // Catch:{ all -> 0x0074 }
        L_0x0082:
            com.google.android.gms.common.util.Clock r5 = com.google.android.gms.ads.internal.zzbv.zzer()
            long r10 = r5.currentTimeMillis()
            long r10 = r10 - r6
            long r1 = r1 - r10
            long r1 = java.lang.Math.max(r1, r8)
            goto L_0x0029
        L_0x0091:
            com.google.android.gms.common.util.Clock r0 = com.google.android.gms.ads.internal.zzbv.zzer()
            long r3 = r0.currentTimeMillis()
            long r3 = r3 - r6
            long r1 = r1 - r3
            java.lang.Math.max(r1, r8)
            throw r14
        L_0x009f:
            r13.zza(r3)
            if (r0 != 0) goto L_0x00ab
            com.google.android.gms.internal.ads.zzxe r14 = new com.google.android.gms.internal.ads.zzxe
            r0 = 1
            r14.<init>(r0)
            return r14
        L_0x00ab:
            return r0
        L_0x00ac:
            r14 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ac }
            goto L_0x00b0
        L_0x00af:
            throw r14
        L_0x00b0:
            goto L_0x00af
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzxh.zzj(java.util.List):com.google.android.gms.internal.ads.zzxe");
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbuf = true;
            for (zzxb cancel : this.zzbug.values()) {
                cancel.cancel();
            }
        }
    }

    public final zzxe zzh(List<zzwx> list) {
        zzakb.zzck("Starting mediation.");
        ArrayList arrayList = new ArrayList();
        zzjn zzjn = this.zzbuc.zzacv;
        int[] iArr = new int[2];
        if (zzjn.zzard != null) {
            zzbv.zzfd();
            if (zzxg.zza(this.zzbuh, iArr)) {
                int i = 0;
                int i2 = iArr[0];
                int i3 = iArr[1];
                zzjn[] zzjnArr = zzjn.zzard;
                int length = zzjnArr.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    zzjn zzjn2 = zzjnArr[i];
                    if (i2 == zzjn2.width && i3 == zzjn2.height) {
                        zzjn = zzjn2;
                        break;
                    }
                    i++;
                }
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            zzwx zzwx = (zzwx) it.next();
            String str = "Trying mediation network: ";
            String valueOf = String.valueOf(zzwx.zzbrs);
            zzakb.zzdj(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            Iterator it2 = zzwx.zzbrt.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                Context context = this.mContext;
                zzxn zzxn = this.zzwh;
                zzwy zzwy = this.zzbtj;
                zzjj zzjj = this.zzbuc.zzccv;
                zzang zzang = this.zzbuc.zzacr;
                boolean z = this.zzael;
                boolean z2 = this.zzbtn;
                Iterator it3 = it;
                Iterator it4 = it2;
                ArrayList arrayList2 = arrayList;
                boolean z3 = z2;
                boolean z4 = z;
                zzxb zzxb = new zzxb(context, str2, zzxn, zzwy, zzwx, zzjj, zzjn, zzang, z4, z3, this.zzbuc.zzadj, this.zzbuc.zzads, this.zzbuc.zzcdk, this.zzbuc.zzcef, this.zzbto);
                zzanz zza = zzaki.zza(new zzxi(this, zzxb));
                this.zzbug.put(zza, zzxb);
                ArrayList arrayList3 = arrayList2;
                arrayList3.add(zza);
                it = it3;
                arrayList = arrayList3;
                it2 = it4;
            }
        }
        ArrayList arrayList4 = arrayList;
        return this.zzbue != 2 ? zzi(arrayList4) : zzj(arrayList4);
    }

    public final List<zzxe> zzme() {
        return this.zzbui;
    }
}
