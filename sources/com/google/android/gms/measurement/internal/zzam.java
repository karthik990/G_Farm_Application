package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.util.Clock;

public final class zzam extends zzf {
    private String zzafi;
    private String zzafp;
    private long zzafs;
    private String zzafv;
    private int zzagp;
    private int zzalm;
    private long zzaln;
    private String zztr;
    private String zzts;
    private String zztt;

    zzam(zzbw zzbw) {
        super(zzbw);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x019f A[Catch:{ IllegalStateException -> 0x01ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01a0 A[Catch:{ IllegalStateException -> 0x01ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01a9 A[Catch:{ IllegalStateException -> 0x01ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01bc A[Catch:{ IllegalStateException -> 0x01ce }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01f1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzgz() {
        /*
            r13 = this;
            android.content.Context r0 = r13.getContext()
            java.lang.String r0 = r0.getPackageName()
            android.content.Context r1 = r13.getContext()
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            java.lang.String r2 = "Unknown"
            java.lang.String r3 = ""
            r4 = 0
            java.lang.String r5 = "unknown"
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r1 != 0) goto L_0x002f
            com.google.android.gms.measurement.internal.zzas r1 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r0)
            java.lang.String r8 = "PackageManager is null, app identity information might be inaccurate. appId"
            r1.zzg(r8, r7)
        L_0x002c:
            r1 = r2
            goto L_0x0091
        L_0x002f:
            java.lang.String r5 = r1.getInstallerPackageName(r0)     // Catch:{ IllegalArgumentException -> 0x0034 }
            goto L_0x0045
        L_0x0034:
            com.google.android.gms.measurement.internal.zzas r7 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjg()
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r0)
            java.lang.String r9 = "Error retrieving app installer package name. appId"
            r7.zzg(r9, r8)
        L_0x0045:
            if (r5 != 0) goto L_0x004a
            java.lang.String r5 = "manual_install"
            goto L_0x0053
        L_0x004a:
            java.lang.String r7 = "com.android.vending"
            boolean r7 = r7.equals(r5)
            if (r7 == 0) goto L_0x0053
            r5 = r3
        L_0x0053:
            android.content.Context r7 = r13.getContext()     // Catch:{ NameNotFoundException -> 0x007c }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x007c }
            android.content.pm.PackageInfo r7 = r1.getPackageInfo(r7, r4)     // Catch:{ NameNotFoundException -> 0x007c }
            if (r7 == 0) goto L_0x002c
            android.content.pm.ApplicationInfo r8 = r7.applicationInfo     // Catch:{ NameNotFoundException -> 0x007c }
            java.lang.CharSequence r1 = r1.getApplicationLabel(r8)     // Catch:{ NameNotFoundException -> 0x007c }
            boolean r8 = android.text.TextUtils.isEmpty(r1)     // Catch:{ NameNotFoundException -> 0x007c }
            if (r8 != 0) goto L_0x0072
            java.lang.String r1 = r1.toString()     // Catch:{ NameNotFoundException -> 0x007c }
            goto L_0x0073
        L_0x0072:
            r1 = r2
        L_0x0073:
            java.lang.String r2 = r7.versionName     // Catch:{ NameNotFoundException -> 0x0078 }
            int r6 = r7.versionCode     // Catch:{ NameNotFoundException -> 0x0078 }
            goto L_0x0091
        L_0x0078:
            r12 = r2
            r2 = r1
            r1 = r12
            goto L_0x007d
        L_0x007c:
            r1 = r2
        L_0x007d:
            com.google.android.gms.measurement.internal.zzas r7 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjg()
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r0)
            java.lang.String r9 = "Error retrieving package info. appId, appName"
            r7.zze(r9, r8, r2)
            r12 = r2
            r2 = r1
            r1 = r12
        L_0x0091:
            r13.zztt = r0
            r13.zzafp = r5
            r13.zzts = r2
            r13.zzalm = r6
            r13.zztr = r1
            r1 = 0
            r13.zzaln = r1
            r13.zzgw()
            android.content.Context r5 = r13.getContext()
            com.google.android.gms.common.api.Status r5 = com.google.android.gms.common.api.internal.GoogleServices.initialize(r5)
            r6 = 1
            if (r5 == 0) goto L_0x00b5
            boolean r7 = r5.isSuccess()
            if (r7 == 0) goto L_0x00b5
            r7 = 1
            goto L_0x00b6
        L_0x00b5:
            r7 = 0
        L_0x00b6:
            com.google.android.gms.measurement.internal.zzbw r8 = r13.zzada
            java.lang.String r8 = r8.zzko()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            java.lang.String r9 = "am"
            if (r8 != 0) goto L_0x00d2
            com.google.android.gms.measurement.internal.zzbw r8 = r13.zzada
            java.lang.String r8 = r8.zzkp()
            boolean r8 = r9.equals(r8)
            if (r8 == 0) goto L_0x00d2
            r8 = 1
            goto L_0x00d3
        L_0x00d2:
            r8 = 0
        L_0x00d3:
            r7 = r7 | r8
            if (r7 != 0) goto L_0x00ff
            if (r5 != 0) goto L_0x00e6
            com.google.android.gms.measurement.internal.zzas r5 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()
            java.lang.String r8 = "GoogleService failed to initialize (no status)"
            r5.zzby(r8)
            goto L_0x00ff
        L_0x00e6:
            com.google.android.gms.measurement.internal.zzas r8 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjg()
            int r10 = r5.getStatusCode()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            java.lang.String r5 = r5.getStatusMessage()
            java.lang.String r11 = "GoogleService failed to initialize, status"
            r8.zze(r11, r10, r5)
        L_0x00ff:
            if (r7 == 0) goto L_0x016b
            com.google.android.gms.measurement.internal.zzq r5 = r13.zzgv()
            java.lang.Boolean r5 = r5.zzia()
            com.google.android.gms.measurement.internal.zzq r7 = r13.zzgv()
            boolean r7 = r7.zzhz()
            if (r7 == 0) goto L_0x0129
            com.google.android.gms.measurement.internal.zzbw r5 = r13.zzada
            boolean r5 = r5.zzkn()
            if (r5 == 0) goto L_0x016b
            com.google.android.gms.measurement.internal.zzas r5 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjm()
            java.lang.String r6 = "Collection disabled with firebase_analytics_collection_deactivated=1"
            r5.zzby(r6)
            goto L_0x016b
        L_0x0129:
            if (r5 == 0) goto L_0x0147
            boolean r7 = r5.booleanValue()
            if (r7 != 0) goto L_0x0147
            com.google.android.gms.measurement.internal.zzbw r5 = r13.zzada
            boolean r5 = r5.zzkn()
            if (r5 == 0) goto L_0x016b
            com.google.android.gms.measurement.internal.zzas r5 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjm()
            java.lang.String r6 = "Collection disabled with firebase_analytics_collection_enabled=0"
            r5.zzby(r6)
            goto L_0x016b
        L_0x0147:
            if (r5 != 0) goto L_0x015d
            boolean r5 = com.google.android.gms.common.api.internal.GoogleServices.isMeasurementExplicitlyDisabled()
            if (r5 == 0) goto L_0x015d
            com.google.android.gms.measurement.internal.zzas r5 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjm()
            java.lang.String r6 = "Collection disabled with google_app_measurement_enable=0"
            r5.zzby(r6)
            goto L_0x016b
        L_0x015d:
            com.google.android.gms.measurement.internal.zzas r5 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjo()
            java.lang.String r7 = "Collection enabled"
            r5.zzby(r7)
            goto L_0x016c
        L_0x016b:
            r6 = 0
        L_0x016c:
            r13.zzafi = r3
            r13.zzafv = r3
            r13.zzafs = r1
            r13.zzgw()
            com.google.android.gms.measurement.internal.zzbw r1 = r13.zzada
            java.lang.String r1 = r1.zzko()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0195
            com.google.android.gms.measurement.internal.zzbw r1 = r13.zzada
            java.lang.String r1 = r1.zzkp()
            boolean r1 = r9.equals(r1)
            if (r1 == 0) goto L_0x0195
            com.google.android.gms.measurement.internal.zzbw r1 = r13.zzada
            java.lang.String r1 = r1.zzko()
            r13.zzafv = r1
        L_0x0195:
            java.lang.String r1 = com.google.android.gms.common.api.internal.GoogleServices.getGoogleAppId()     // Catch:{ IllegalStateException -> 0x01ce }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ IllegalStateException -> 0x01ce }
            if (r2 == 0) goto L_0x01a0
            goto L_0x01a1
        L_0x01a0:
            r3 = r1
        L_0x01a1:
            r13.zzafi = r3     // Catch:{ IllegalStateException -> 0x01ce }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ IllegalStateException -> 0x01ce }
            if (r1 != 0) goto L_0x01ba
            com.google.android.gms.common.internal.StringResourceValueReader r1 = new com.google.android.gms.common.internal.StringResourceValueReader     // Catch:{ IllegalStateException -> 0x01ce }
            android.content.Context r2 = r13.getContext()     // Catch:{ IllegalStateException -> 0x01ce }
            r1.<init>(r2)     // Catch:{ IllegalStateException -> 0x01ce }
            java.lang.String r2 = "admob_app_id"
            java.lang.String r1 = r1.getString(r2)     // Catch:{ IllegalStateException -> 0x01ce }
            r13.zzafv = r1     // Catch:{ IllegalStateException -> 0x01ce }
        L_0x01ba:
            if (r6 == 0) goto L_0x01e0
            com.google.android.gms.measurement.internal.zzas r1 = r13.zzgt()     // Catch:{ IllegalStateException -> 0x01ce }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()     // Catch:{ IllegalStateException -> 0x01ce }
            java.lang.String r2 = "App package, google app id"
            java.lang.String r3 = r13.zztt     // Catch:{ IllegalStateException -> 0x01ce }
            java.lang.String r5 = r13.zzafi     // Catch:{ IllegalStateException -> 0x01ce }
            r1.zze(r2, r3, r5)     // Catch:{ IllegalStateException -> 0x01ce }
            goto L_0x01e0
        L_0x01ce:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzas r2 = r13.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()
            java.lang.Object r0 = com.google.android.gms.measurement.internal.zzas.zzbw(r0)
            java.lang.String r3 = "getGoogleAppId or isMeasurementEnabled failed with exception. appId"
            r2.zze(r3, r0, r1)
        L_0x01e0:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 16
            if (r0 < r1) goto L_0x01f1
            android.content.Context r0 = r13.getContext()
            boolean r0 = com.google.android.gms.common.wrappers.InstantApps.isInstantApp(r0)
            r13.zzagp = r0
            return
        L_0x01f1:
            r13.zzagp = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzgz():void");
    }

    /* access modifiers changed from: 0000 */
    public final zzk zzbs(String str) {
        String str2;
        zzaf();
        zzgg();
        String zzal = zzal();
        String gmpAppId = getGmpAppId();
        zzcl();
        String str3 = this.zzts;
        long zzjd = (long) zzjd();
        zzcl();
        String str4 = this.zzafp;
        long zzhh = zzgv().zzhh();
        zzcl();
        zzaf();
        if (this.zzaln == 0) {
            this.zzaln = this.zzada.zzgr().zzd(getContext(), getContext().getPackageName());
        }
        long j = this.zzaln;
        boolean isEnabled = this.zzada.isEnabled();
        boolean z = !zzgu().zzans;
        zzaf();
        zzgg();
        if (!zzgv().zzaz(this.zztt) || this.zzada.isEnabled()) {
            str2 = zzjc();
        } else {
            str2 = null;
        }
        String str5 = str2;
        zzcl();
        boolean z2 = z;
        String str6 = str5;
        long j2 = this.zzafs;
        long zzkt = this.zzada.zzkt();
        int zzje = zzje();
        zzq zzgv = zzgv();
        zzgv.zzgg();
        Boolean zzar = zzgv.zzar("google_analytics_adid_collection_enabled");
        boolean booleanValue = Boolean.valueOf(zzar == null || zzar.booleanValue()).booleanValue();
        zzq zzgv2 = zzgv();
        zzgv2.zzgg();
        Boolean zzar2 = zzgv2.zzar("google_analytics_ssaid_collection_enabled");
        zzk zzk = new zzk(zzal, gmpAppId, str3, zzjd, str4, zzhh, j, str, isEnabled, z2, str6, j2, zzkt, zzje, booleanValue, Boolean.valueOf(zzar2 == null || zzar2.booleanValue()).booleanValue(), zzgu().zzkb(), zzhb());
        return zzk;
    }

    private final String zzjc() {
        try {
            Class loadClass = getContext().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
            if (loadClass == null) {
                return null;
            }
            try {
                Object invoke = loadClass.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(null, new Object[]{getContext()});
                if (invoke == null) {
                    return null;
                }
                try {
                    return (String) loadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(invoke, new Object[0]);
                } catch (Exception unused) {
                    zzgt().zzjl().zzby("Failed to retrieve Firebase Instance Id");
                    return null;
                }
            } catch (Exception unused2) {
                zzgt().zzjk().zzby("Failed to obtain Firebase Analytics instance");
                return null;
            }
        } catch (ClassNotFoundException unused3) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final String zzal() {
        zzcl();
        return this.zztt;
    }

    /* access modifiers changed from: 0000 */
    public final String getGmpAppId() {
        zzcl();
        return this.zzafi;
    }

    /* access modifiers changed from: 0000 */
    public final String zzhb() {
        zzcl();
        return this.zzafv;
    }

    /* access modifiers changed from: 0000 */
    public final int zzjd() {
        zzcl();
        return this.zzalm;
    }

    /* access modifiers changed from: 0000 */
    public final int zzje() {
        zzcl();
        return this.zzagp;
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
