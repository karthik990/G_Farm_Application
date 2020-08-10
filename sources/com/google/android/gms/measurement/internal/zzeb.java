package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzdq;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class zzeb extends zzf {
    /* access modifiers changed from: private */
    public final zzes zzase;
    /* access modifiers changed from: private */
    public zzaj zzasf;
    private volatile Boolean zzasg;
    private final zzy zzash;
    private final zzfi zzasi;
    private final List<Runnable> zzasj = new ArrayList();
    private final zzy zzask;

    protected zzeb(zzbw zzbw) {
        super(zzbw);
        this.zzasi = new zzfi(zzbw.zzbx());
        this.zzase = new zzes(this);
        this.zzash = new zzec(this, zzbw);
        this.zzask = new zzek(this, zzbw);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    public final boolean isConnected() {
        zzaf();
        zzcl();
        return this.zzasf != null;
    }

    /* access modifiers changed from: protected */
    public final void zzlg() {
        zzaf();
        zzcl();
        zzf(new zzel(this, zzl(true)));
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.measurement.internal.zzaj r12, com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable r13, com.google.android.gms.measurement.internal.zzk r14) {
        /*
            r11 = this;
            r11.zzaf()
            r11.zzgg()
            r11.zzcl()
            boolean r0 = r11.zzlh()
            r1 = 0
            r2 = 100
            r3 = 0
            r4 = 100
        L_0x0013:
            r5 = 1001(0x3e9, float:1.403E-42)
            if (r3 >= r5) goto L_0x00a9
            if (r4 != r2) goto L_0x00a9
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            if (r0 == 0) goto L_0x0032
            com.google.android.gms.measurement.internal.zzao r5 = r11.zzgn()
            java.util.List r5 = r5.zzr(r2)
            if (r5 == 0) goto L_0x0032
            r4.addAll(r5)
            int r5 = r5.size()
            goto L_0x0033
        L_0x0032:
            r5 = 0
        L_0x0033:
            if (r13 == 0) goto L_0x003a
            if (r5 >= r2) goto L_0x003a
            r4.add(r13)
        L_0x003a:
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            int r6 = r4.size()
            r7 = 0
        L_0x0041:
            if (r7 >= r6) goto L_0x00a4
            java.lang.Object r8 = r4.get(r7)
            int r7 = r7 + 1
            com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable r8 = (com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable) r8
            boolean r9 = r8 instanceof com.google.android.gms.measurement.internal.zzag
            if (r9 == 0) goto L_0x0064
            com.google.android.gms.measurement.internal.zzag r8 = (com.google.android.gms.measurement.internal.zzag) r8     // Catch:{ RemoteException -> 0x0055 }
            r12.zza(r8, r14)     // Catch:{ RemoteException -> 0x0055 }
            goto L_0x0041
        L_0x0055:
            r8 = move-exception
            com.google.android.gms.measurement.internal.zzas r9 = r11.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()
            java.lang.String r10 = "Failed to send event to the service"
            r9.zzg(r10, r8)
            goto L_0x0041
        L_0x0064:
            boolean r9 = r8 instanceof com.google.android.gms.measurement.internal.zzfu
            if (r9 == 0) goto L_0x007d
            com.google.android.gms.measurement.internal.zzfu r8 = (com.google.android.gms.measurement.internal.zzfu) r8     // Catch:{ RemoteException -> 0x006e }
            r12.zza(r8, r14)     // Catch:{ RemoteException -> 0x006e }
            goto L_0x0041
        L_0x006e:
            r8 = move-exception
            com.google.android.gms.measurement.internal.zzas r9 = r11.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()
            java.lang.String r10 = "Failed to send attribute to the service"
            r9.zzg(r10, r8)
            goto L_0x0041
        L_0x007d:
            boolean r9 = r8 instanceof com.google.android.gms.measurement.internal.zzo
            if (r9 == 0) goto L_0x0096
            com.google.android.gms.measurement.internal.zzo r8 = (com.google.android.gms.measurement.internal.zzo) r8     // Catch:{ RemoteException -> 0x0087 }
            r12.zza(r8, r14)     // Catch:{ RemoteException -> 0x0087 }
            goto L_0x0041
        L_0x0087:
            r8 = move-exception
            com.google.android.gms.measurement.internal.zzas r9 = r11.zzgt()
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()
            java.lang.String r10 = "Failed to send conditional property to the service"
            r9.zzg(r10, r8)
            goto L_0x0041
        L_0x0096:
            com.google.android.gms.measurement.internal.zzas r8 = r11.zzgt()
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjg()
            java.lang.String r9 = "Discarding data. Unrecognized parcel type."
            r8.zzby(r9)
            goto L_0x0041
        L_0x00a4:
            int r3 = r3 + 1
            r4 = r5
            goto L_0x0013
        L_0x00a9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeb.zza(com.google.android.gms.measurement.internal.zzaj, com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable, com.google.android.gms.measurement.internal.zzk):void");
    }

    /* access modifiers changed from: protected */
    public final void zzc(zzag zzag, String str) {
        Preconditions.checkNotNull(zzag);
        zzaf();
        zzcl();
        boolean zzlh = zzlh();
        zzem zzem = new zzem(this, zzlh, zzlh && zzgn().zza(zzag), zzag, zzl(true), str);
        zzf(zzem);
    }

    /* access modifiers changed from: protected */
    public final void zzd(zzo zzo) {
        Preconditions.checkNotNull(zzo);
        zzaf();
        zzcl();
        zzgw();
        zzen zzen = new zzen(this, true, zzgn().zzc(zzo), new zzo(zzo), zzl(true), zzo);
        zzf(zzen);
    }

    /* access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzo>> atomicReference, String str, String str2, String str3) {
        zzaf();
        zzcl();
        zzeo zzeo = new zzeo(this, atomicReference, str, str2, str3, zzl(false));
        zzf(zzeo);
    }

    /* access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzfu>> atomicReference, String str, String str2, String str3, boolean z) {
        zzaf();
        zzcl();
        zzep zzep = new zzep(this, atomicReference, str, str2, str3, z, zzl(false));
        zzf(zzep);
    }

    /* access modifiers changed from: protected */
    public final void zza(zzdq zzdq, String str, String str2, boolean z) {
        zzaf();
        zzcl();
        zzeq zzeq = new zzeq(this, str, str2, z, zzl(false), zzdq);
        zzf(zzeq);
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzfu zzfu) {
        zzaf();
        zzcl();
        zzf(new zzer(this, zzlh() && zzgn().zza(zzfu), zzfu, zzl(true)));
    }

    /* access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzfu>> atomicReference, boolean z) {
        zzaf();
        zzcl();
        zzf(new zzed(this, atomicReference, zzl(false), z));
    }

    /* access modifiers changed from: protected */
    public final void resetAnalyticsData() {
        zzaf();
        zzgg();
        zzcl();
        zzk zzl = zzl(false);
        if (zzlh()) {
            zzgn().resetAnalyticsData();
        }
        zzf(new zzee(this, zzl));
    }

    private final boolean zzlh() {
        zzgw();
        return true;
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzaf();
        zzcl();
        zzf(new zzef(this, atomicReference, zzl(false)));
    }

    public final void getAppInstanceId(zzdq zzdq) {
        zzaf();
        zzcl();
        zzf(new zzeg(this, zzl(false), zzdq));
    }

    /* access modifiers changed from: protected */
    public final void zzld() {
        zzaf();
        zzcl();
        zzf(new zzeh(this, zzl(true)));
    }

    /* access modifiers changed from: protected */
    public final void zza(zzdx zzdx) {
        zzaf();
        zzcl();
        zzf(new zzei(this, zzdx));
    }

    /* access modifiers changed from: private */
    public final void zzcy() {
        zzaf();
        this.zzasi.start();
        this.zzash.zzh(((Long) zzai.zzaka.get()).longValue());
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzdj() {
        /*
            r6 = this;
            r6.zzaf()
            r6.zzcl()
            boolean r0 = r6.isConnected()
            if (r0 == 0) goto L_0x000d
            return
        L_0x000d:
            java.lang.Boolean r0 = r6.zzasg
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x011a
            r6.zzaf()
            r6.zzcl()
            com.google.android.gms.measurement.internal.zzbd r0 = r6.zzgu()
            java.lang.Boolean r0 = r0.zzjx()
            if (r0 == 0) goto L_0x002c
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x002c
            r0 = 1
            goto L_0x0114
        L_0x002c:
            r6.zzgw()
            com.google.android.gms.measurement.internal.zzam r0 = r6.zzgk()
            int r0 = r0.zzje()
            if (r0 != r2) goto L_0x003d
        L_0x0039:
            r0 = 1
        L_0x003a:
            r3 = 1
            goto L_0x00f1
        L_0x003d:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            java.lang.String r3 = "Checking service availability"
            r0.zzby(r3)
            com.google.android.gms.measurement.internal.zzfx r0 = r6.zzgr()
            r3 = 12451000(0xbdfcb8, float:1.7447567E-38)
            int r0 = r0.zzs(r3)
            if (r0 == 0) goto L_0x00e2
            if (r0 == r2) goto L_0x00d2
            r3 = 2
            if (r0 == r3) goto L_0x00a6
            r3 = 3
            if (r0 == r3) goto L_0x0098
            r3 = 9
            if (r0 == r3) goto L_0x008a
            r3 = 18
            if (r0 == r3) goto L_0x007c
            com.google.android.gms.measurement.internal.zzas r3 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjj()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.String r4 = "Unexpected service status"
            r3.zzg(r4, r0)
        L_0x0078:
            r0 = 0
        L_0x0079:
            r3 = 0
            goto L_0x00f1
        L_0x007c:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r3 = "Service updating"
            r0.zzby(r3)
            goto L_0x0039
        L_0x008a:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r3 = "Service invalid"
            r0.zzby(r3)
            goto L_0x0078
        L_0x0098:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r3 = "Service disabled"
            r0.zzby(r3)
            goto L_0x0078
        L_0x00a6:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjn()
            java.lang.String r3 = "Service container out of date"
            r0.zzby(r3)
            com.google.android.gms.measurement.internal.zzfx r0 = r6.zzgr()
            int r0 = r0.zzml()
            r3 = 14500(0x38a4, float:2.0319E-41)
            if (r0 >= r3) goto L_0x00c0
            goto L_0x00df
        L_0x00c0:
            com.google.android.gms.measurement.internal.zzbd r0 = r6.zzgu()
            java.lang.Boolean r0 = r0.zzjx()
            if (r0 == 0) goto L_0x00d0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0078
        L_0x00d0:
            r0 = 1
            goto L_0x0079
        L_0x00d2:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            java.lang.String r3 = "Service missing"
            r0.zzby(r3)
        L_0x00df:
            r0 = 0
            goto L_0x003a
        L_0x00e2:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            java.lang.String r3 = "Service available"
            r0.zzby(r3)
            goto L_0x0039
        L_0x00f1:
            if (r0 != 0) goto L_0x010b
            com.google.android.gms.measurement.internal.zzq r4 = r6.zzgv()
            boolean r4 = r4.zzif()
            if (r4 == 0) goto L_0x010b
            com.google.android.gms.measurement.internal.zzas r3 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()
            java.lang.String r4 = "No way to upload. Consider using the full version of Analytics"
            r3.zzby(r4)
            r3 = 0
        L_0x010b:
            if (r3 == 0) goto L_0x0114
            com.google.android.gms.measurement.internal.zzbd r3 = r6.zzgu()
            r3.zzg(r0)
        L_0x0114:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r6.zzasg = r0
        L_0x011a:
            java.lang.Boolean r0 = r6.zzasg
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0128
            com.google.android.gms.measurement.internal.zzes r0 = r6.zzase
            r0.zzll()
            return
        L_0x0128:
            com.google.android.gms.measurement.internal.zzq r0 = r6.zzgv()
            boolean r0 = r0.zzif()
            if (r0 != 0) goto L_0x0186
            r6.zzgw()
            android.content.Context r0 = r6.getContext()
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.Intent r3 = new android.content.Intent
            r3.<init>()
            android.content.Context r4 = r6.getContext()
            java.lang.String r5 = "com.google.android.gms.measurement.AppMeasurementService"
            android.content.Intent r3 = r3.setClassName(r4, r5)
            r4 = 65536(0x10000, float:9.18355E-41)
            java.util.List r0 = r0.queryIntentServices(r3, r4)
            if (r0 == 0) goto L_0x015b
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x015b
            r1 = 1
        L_0x015b:
            if (r1 == 0) goto L_0x0179
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "com.google.android.gms.measurement.START"
            r0.<init>(r1)
            android.content.ComponentName r1 = new android.content.ComponentName
            android.content.Context r2 = r6.getContext()
            r6.zzgw()
            r1.<init>(r2, r5)
            r0.setComponent(r1)
            com.google.android.gms.measurement.internal.zzes r1 = r6.zzase
            r1.zzb(r0)
            return
        L_0x0179:
            com.google.android.gms.measurement.internal.zzas r0 = r6.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()
            java.lang.String r1 = "Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest"
            r0.zzby(r1)
        L_0x0186:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzeb.zzdj():void");
    }

    /* access modifiers changed from: 0000 */
    public final Boolean zzli() {
        return this.zzasg;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaj zzaj) {
        zzaf();
        Preconditions.checkNotNull(zzaj);
        this.zzasf = zzaj;
        zzcy();
        zzlj();
    }

    public final void disconnect() {
        zzaf();
        zzcl();
        this.zzase.zzlk();
        try {
            ConnectionTracker.getInstance().unbindService(getContext(), this.zzase);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzasf = null;
    }

    /* access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        zzaf();
        if (this.zzasf != null) {
            this.zzasf = null;
            zzgt().zzjo().zzg("Disconnected from device MeasurementService", componentName);
            zzaf();
            zzdj();
        }
    }

    /* access modifiers changed from: private */
    public final void zzcz() {
        zzaf();
        if (isConnected()) {
            zzgt().zzjo().zzby("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    private final void zzf(Runnable runnable) throws IllegalStateException {
        zzaf();
        if (isConnected()) {
            runnable.run();
        } else if (((long) this.zzasj.size()) >= 1000) {
            zzgt().zzjg().zzby("Discarding data. Max runnable queue size reached");
        } else {
            this.zzasj.add(runnable);
            this.zzask.zzh(DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
            zzdj();
        }
    }

    /* access modifiers changed from: private */
    public final void zzlj() {
        zzaf();
        zzgt().zzjo().zzg("Processing queued up service tasks", Integer.valueOf(this.zzasj.size()));
        for (Runnable run : this.zzasj) {
            try {
                run.run();
            } catch (Exception e) {
                zzgt().zzjg().zzg("Task exception while flushing queue", e);
            }
        }
        this.zzasj.clear();
        this.zzask.cancel();
    }

    private final zzk zzl(boolean z) {
        zzgw();
        return zzgk().zzbs(z ? zzgt().zzjq() : null);
    }

    public final void zza(zzdq zzdq, zzag zzag, String str) {
        zzaf();
        zzcl();
        if (zzgr().zzs(GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE) != 0) {
            zzgt().zzjj().zzby("Not bundling data. Service unavailable or out of date");
            zzgr().zza(zzdq, new byte[0]);
            return;
        }
        zzf(new zzej(this, zzag, str, zzdq));
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zza zzgi() {
        return super.zzgi();
    }

    public final /* bridge */ /* synthetic */ zzda zzgj() {
        return super.zzgj();
    }

    public final /* bridge */ /* synthetic */ zzam zzgk() {
        return super.zzgk();
    }

    public final /* bridge */ /* synthetic */ zzeb zzgl() {
        return super.zzgl();
    }

    public final /* bridge */ /* synthetic */ zzdy zzgm() {
        return super.zzgm();
    }

    public final /* bridge */ /* synthetic */ zzao zzgn() {
        return super.zzgn();
    }

    public final /* bridge */ /* synthetic */ zzfd zzgo() {
        return super.zzgo();
    }

    public final /* bridge */ /* synthetic */ zzaa zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfx zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbr zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzas zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbd zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzq zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzn zzgw() {
        return super.zzgw();
    }
}
