package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import androidx.work.PeriodicWorkRequest;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzi;
import com.google.android.gms.internal.gtm.zzk;
import com.google.android.gms.internal.gtm.zzop;
import com.google.android.gms.internal.gtm.zzoq;
import com.google.android.gms.internal.gtm.zzov;

public final class zzy extends BasePendingResult<ContainerHolder> {
    private final String zzaec;
    /* access modifiers changed from: private */
    public long zzaeh;
    private final Looper zzaek;
    private final TagManager zzaer;
    private final zzaf zzaeu;
    /* access modifiers changed from: private */
    public final zzej zzaev;
    private final int zzaew;
    /* access modifiers changed from: private */
    public final zzai zzaex;
    private zzah zzaey;
    private zzoq zzaez;
    /* access modifiers changed from: private */
    public volatile zzv zzafa;
    /* access modifiers changed from: private */
    public volatile boolean zzafb;
    /* access modifiers changed from: private */
    public zzk zzafc;
    private String zzafd;
    private zzag zzafe;
    private zzac zzaff;
    private final Context zzrm;
    /* access modifiers changed from: private */
    public final Clock zzsd;

    public zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzal) {
        Context context2 = context;
        String str2 = str;
        zzex zzex = new zzex(context2, str2);
        zzes zzes = new zzes(context2, str2, zzal);
        zzoq zzoq = new zzoq(context2);
        Clock instance = DefaultClock.getInstance();
        zzdg zzdg = new zzdg(1, 5, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, "refreshing", DefaultClock.getInstance());
        this(context2, tagManager, looper, str2, i, zzex, zzes, zzoq, instance, zzdg, new zzai(context2, str2));
        this.zzaez.zzcr(zzal.zzhq());
    }

    private zzy(Context context, TagManager tagManager, Looper looper, String str, int i, zzah zzah, zzag zzag, zzoq zzoq, Clock clock, zzej zzej, zzai zzai) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.zzrm = context;
        this.zzaer = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzaek = looper;
        this.zzaec = str;
        this.zzaew = i;
        this.zzaey = zzah;
        this.zzafe = zzag;
        this.zzaez = zzoq;
        this.zzaeu = new zzaf(this, null);
        this.zzafc = new zzk();
        this.zzsd = clock;
        this.zzaev = zzej;
        this.zzaex = zzai;
        if (zzhi()) {
            zzao(zzeh.zziy().zzja());
        }
    }

    public final void zzhf() {
        zzov zzt = this.zzaey.zzt(this.zzaew);
        if (zzt != null) {
            Container container = new Container(this.zzrm, this.zzaer.getDataLayer(), this.zzaec, 0, zzt);
            setResult(new zzv(this.zzaer, this.zzaek, container, new zzaa(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            zzdi.zzav(str);
            setResult(createFailedResult(new Status(10, str, null)));
        }
        this.zzafe = null;
        this.zzaey = null;
    }

    public final void zzhg() {
        zzd(false);
    }

    public final void zzhh() {
        zzd(true);
    }

    private final void zzd(boolean z) {
        this.zzaey.zza((zzdh<zzop>) new zzad<zzop>(this, null));
        this.zzafe.zza(new zzae(this, null));
        zzov zzt = this.zzaey.zzt(this.zzaew);
        if (zzt != null) {
            TagManager tagManager = this.zzaer;
            Looper looper = this.zzaek;
            Container container = new Container(this.zzrm, tagManager.getDataLayer(), this.zzaec, 0, zzt);
            this.zzafa = new zzv(tagManager, looper, container, this.zzaeu);
        }
        this.zzaff = new zzab(this, z);
        if (zzhi()) {
            this.zzafe.zza(0, "");
        } else {
            this.zzaey.zzhk();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void zza(com.google.android.gms.internal.gtm.zzk r10, long r11, boolean r13) {
        /*
            r9 = this;
            monitor-enter(r9)
            if (r13 == 0) goto L_0x0005
            boolean r13 = r9.zzafb     // Catch:{ all -> 0x0070 }
        L_0x0005:
            boolean r13 = r9.isReady()     // Catch:{ all -> 0x0070 }
            if (r13 == 0) goto L_0x0011
            com.google.android.gms.tagmanager.zzv r13 = r9.zzafa     // Catch:{ all -> 0x0070 }
            if (r13 != 0) goto L_0x0011
            monitor-exit(r9)
            return
        L_0x0011:
            r9.zzafc = r10     // Catch:{ all -> 0x0070 }
            r9.zzaeh = r11     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.zzai r13 = r9.zzaex     // Catch:{ all -> 0x0070 }
            long r0 = r13.zzhl()     // Catch:{ all -> 0x0070 }
            r2 = 0
            long r4 = r9.zzaeh     // Catch:{ all -> 0x0070 }
            long r4 = r4 + r0
            com.google.android.gms.common.util.Clock r13 = r9.zzsd     // Catch:{ all -> 0x0070 }
            long r6 = r13.currentTimeMillis()     // Catch:{ all -> 0x0070 }
            long r4 = r4 - r6
            long r0 = java.lang.Math.min(r0, r4)     // Catch:{ all -> 0x0070 }
            long r0 = java.lang.Math.max(r2, r0)     // Catch:{ all -> 0x0070 }
            r9.zzk(r0)     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.Container r13 = new com.google.android.gms.tagmanager.Container     // Catch:{ all -> 0x0070 }
            android.content.Context r3 = r9.zzrm     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.TagManager r0 = r9.zzaer     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.DataLayer r4 = r0.getDataLayer()     // Catch:{ all -> 0x0070 }
            java.lang.String r5 = r9.zzaec     // Catch:{ all -> 0x0070 }
            r2 = r13
            r6 = r11
            r8 = r10
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.zzv r10 = r9.zzafa     // Catch:{ all -> 0x0070 }
            if (r10 != 0) goto L_0x0056
            com.google.android.gms.tagmanager.zzv r10 = new com.google.android.gms.tagmanager.zzv     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.TagManager r11 = r9.zzaer     // Catch:{ all -> 0x0070 }
            android.os.Looper r12 = r9.zzaek     // Catch:{ all -> 0x0070 }
            com.google.android.gms.tagmanager.zzaf r0 = r9.zzaeu     // Catch:{ all -> 0x0070 }
            r10.<init>(r11, r12, r13, r0)     // Catch:{ all -> 0x0070 }
            r9.zzafa = r10     // Catch:{ all -> 0x0070 }
            goto L_0x005b
        L_0x0056:
            com.google.android.gms.tagmanager.zzv r10 = r9.zzafa     // Catch:{ all -> 0x0070 }
            r10.zza(r13)     // Catch:{ all -> 0x0070 }
        L_0x005b:
            boolean r10 = r9.isReady()     // Catch:{ all -> 0x0070 }
            if (r10 != 0) goto L_0x006e
            com.google.android.gms.tagmanager.zzac r10 = r9.zzaff     // Catch:{ all -> 0x0070 }
            boolean r10 = r10.zzb(r13)     // Catch:{ all -> 0x0070 }
            if (r10 == 0) goto L_0x006e
            com.google.android.gms.tagmanager.zzv r10 = r9.zzafa     // Catch:{ all -> 0x0070 }
            r9.setResult(r10)     // Catch:{ all -> 0x0070 }
        L_0x006e:
            monitor-exit(r9)
            return
        L_0x0070:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzy.zza(com.google.android.gms.internal.gtm.zzk, long, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void zzao(String str) {
        this.zzafd = str;
        if (this.zzafe != null) {
            this.zzafe.zzap(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String zzhc() {
        return this.zzafd;
    }

    /* access modifiers changed from: private */
    public final synchronized void zzk(long j) {
        if (this.zzafe == null) {
            zzdi.zzac("Refresh requested, but no network load scheduler.");
        } else {
            this.zzafe.zza(j, this.zzafc.zzql);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: zza */
    public final ContainerHolder createFailedResult(Status status) {
        if (this.zzafa != null) {
            return this.zzafa;
        }
        if (status == Status.RESULT_TIMEOUT) {
            zzdi.zzav("timer expired: setting result to failure");
        }
        return new zzv(status);
    }

    /* access modifiers changed from: private */
    public final boolean zzhi() {
        zzeh zziy = zzeh.zziy();
        return (zziy.zziz() == zza.CONTAINER || zziy.zziz() == zza.CONTAINER_DEBUG) && this.zzaec.equals(zziy.getContainerId());
    }

    /* access modifiers changed from: private */
    public final synchronized void zza(zzk zzk) {
        if (this.zzaey != null) {
            zzop zzop = new zzop();
            zzop.zzaux = this.zzaeh;
            zzop.zzqk = new zzi();
            zzop.zzauy = zzk;
            this.zzaey.zza(zzop);
        }
    }
}
