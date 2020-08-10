package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzalg;
    private final DataLayer zzaed;
    private final zzal zzajg;
    private final zza zzald;
    private final zzfm zzale;
    private final ConcurrentMap<String, zzv> zzalf;
    private final Context zzrm;

    public interface zza {
        zzy zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzal);
    }

    private TagManager(Context context, zza zza2, DataLayer dataLayer, zzfm zzfm) {
        if (context != null) {
            this.zzrm = context.getApplicationContext();
            this.zzale = zzfm;
            this.zzald = zza2;
            this.zzalf = new ConcurrentHashMap();
            this.zzaed = dataLayer;
            this.zzaed.zza((zzb) new zzga(this));
            this.zzaed.zza((zzb) new zzg(this.zzrm));
            this.zzajg = new zzal();
            this.zzrm.registerComponentCallbacks(new zzgc(this));
            zza.zzf(this.zzrm);
            return;
        }
        throw new NullPointerException("context cannot be null");
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzalg == null) {
                if (context != null) {
                    zzalg = new TagManager(context, new zzgb(), new DataLayer(new zzat(context)), zzfn.zzjq());
                } else {
                    zzdi.zzav("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
            }
            tagManager = zzalg;
        }
        return tagManager;
    }

    public DataLayer getDataLayer() {
        return this.zzaed;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zza2.zzhf();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i, Handler handler) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zza2.zzhf();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zza2.zzhg();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i, Handler handler) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zza2.zzhg();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, null, str, i, this.zzajg);
        zza2.zzhh();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i, Handler handler) {
        zzy zza2 = this.zzald.zza(this.zzrm, this, handler.getLooper(), str, i, this.zzajg);
        zza2.zzhh();
        return zza2;
    }

    public void dispatch() {
        this.zzale.dispatch();
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzdi.setLogLevel(z ? 2 : 5);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0073, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean zza(android.net.Uri r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            com.google.android.gms.tagmanager.zzeh r0 = com.google.android.gms.tagmanager.zzeh.zziy()     // Catch:{ all -> 0x0077 }
            boolean r7 = r0.zza(r7)     // Catch:{ all -> 0x0077 }
            if (r7 == 0) goto L_0x0074
            java.lang.String r7 = r0.getContainerId()     // Catch:{ all -> 0x0077 }
            int[] r1 = com.google.android.gms.tagmanager.zzgd.zzali     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzeh$zza r2 = r0.zziz()     // Catch:{ all -> 0x0077 }
            int r2 = r2.ordinal()     // Catch:{ all -> 0x0077 }
            r1 = r1[r2]     // Catch:{ all -> 0x0077 }
            r2 = 0
            r3 = 1
            if (r1 == r3) goto L_0x0062
            r4 = 2
            if (r1 == r4) goto L_0x0026
            r4 = 3
            if (r1 == r4) goto L_0x0026
            goto L_0x0072
        L_0x0026:
            java.util.concurrent.ConcurrentMap<java.lang.String, com.google.android.gms.tagmanager.zzv> r1 = r6.zzalf     // Catch:{ all -> 0x0077 }
            java.util.Set r1 = r1.keySet()     // Catch:{ all -> 0x0077 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0077 }
        L_0x0030:
            boolean r4 = r1.hasNext()     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x0072
            java.lang.Object r4 = r1.next()     // Catch:{ all -> 0x0077 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x0077 }
            java.util.concurrent.ConcurrentMap<java.lang.String, com.google.android.gms.tagmanager.zzv> r5 = r6.zzalf     // Catch:{ all -> 0x0077 }
            java.lang.Object r5 = r5.get(r4)     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzv r5 = (com.google.android.gms.tagmanager.zzv) r5     // Catch:{ all -> 0x0077 }
            boolean r4 = r4.equals(r7)     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x0055
            java.lang.String r4 = r0.zzja()     // Catch:{ all -> 0x0077 }
            r5.zzao(r4)     // Catch:{ all -> 0x0077 }
            r5.refresh()     // Catch:{ all -> 0x0077 }
            goto L_0x0030
        L_0x0055:
            java.lang.String r4 = r5.zzhc()     // Catch:{ all -> 0x0077 }
            if (r4 == 0) goto L_0x0030
            r5.zzao(r2)     // Catch:{ all -> 0x0077 }
            r5.refresh()     // Catch:{ all -> 0x0077 }
            goto L_0x0030
        L_0x0062:
            java.util.concurrent.ConcurrentMap<java.lang.String, com.google.android.gms.tagmanager.zzv> r0 = r6.zzalf     // Catch:{ all -> 0x0077 }
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzv r7 = (com.google.android.gms.tagmanager.zzv) r7     // Catch:{ all -> 0x0077 }
            if (r7 == 0) goto L_0x0072
            r7.zzao(r2)     // Catch:{ all -> 0x0077 }
            r7.refresh()     // Catch:{ all -> 0x0077 }
        L_0x0072:
            monitor-exit(r6)
            return r3
        L_0x0074:
            r7 = 0
            monitor-exit(r6)
            return r7
        L_0x0077:
            r7 = move-exception
            monitor-exit(r6)
            goto L_0x007b
        L_0x007a:
            throw r7
        L_0x007b:
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.TagManager.zza(android.net.Uri):boolean");
    }

    public final int zza(zzv zzv) {
        this.zzalf.put(zzv.getContainerId(), zzv);
        return this.zzalf.size();
    }

    public final boolean zzb(zzv zzv) {
        return this.zzalf.remove(zzv.getContainerId()) != null;
    }

    /* access modifiers changed from: private */
    public final void zzbl(String str) {
        for (zzv zzan : this.zzalf.values()) {
            zzan.zzan(str);
        }
    }
}
