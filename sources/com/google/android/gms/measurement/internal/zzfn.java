package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.braintreepayments.api.models.BinData;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzfp;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzfu;
import com.google.android.gms.internal.measurement.zzfw;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;

public class zzfn implements zzct {
    private static volatile zzfn zzati;
    private final zzbw zzada;
    private zzbq zzatj;
    private zzaw zzatk;
    private zzt zzatl;
    private zzbb zzatm;
    private zzfj zzatn;
    private zzm zzato;
    private final zzft zzatp;
    private zzdv zzatq;
    private boolean zzatr;
    private boolean zzats;
    private long zzatt;
    private List<Runnable> zzatu;
    private int zzatv;
    private int zzatw;
    private boolean zzatx;
    private boolean zzaty;
    private boolean zzatz;
    private FileLock zzaua;
    private FileChannel zzaub;
    private List<Long> zzauc;
    private List<Long> zzaud;
    private long zzaue;
    private boolean zzvz;

    class zza implements zzv {
        zzfw zzaui;
        List<Long> zzauj;
        List<zzft> zzauk;
        private long zzaul;

        private zza() {
        }

        public final void zzb(zzfw zzfw) {
            Preconditions.checkNotNull(zzfw);
            this.zzaui = zzfw;
        }

        public final boolean zza(long j, zzft zzft) {
            Preconditions.checkNotNull(zzft);
            if (this.zzauk == null) {
                this.zzauk = new ArrayList();
            }
            if (this.zzauj == null) {
                this.zzauj = new ArrayList();
            }
            if (this.zzauk.size() > 0 && zza((zzft) this.zzauk.get(0)) != zza(zzft)) {
                return false;
            }
            long zzvx = this.zzaul + ((long) zzft.zzvx());
            if (zzvx >= ((long) Math.max(0, ((Integer) zzai.zzajc.get()).intValue()))) {
                return false;
            }
            this.zzaul = zzvx;
            this.zzauk.add(zzft);
            this.zzauj.add(Long.valueOf(j));
            if (this.zzauk.size() >= Math.max(1, ((Integer) zzai.zzajd.get()).intValue())) {
                return false;
            }
            return true;
        }

        private static long zza(zzft zzft) {
            return ((zzft.zzaxd.longValue() / 1000) / 60) / 60;
        }

        /* synthetic */ zza(zzfn zzfn, zzfo zzfo) {
            this();
        }
    }

    public static zzfn zzn(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzati == null) {
            synchronized (zzfn.class) {
                if (zzati == null) {
                    zzati = new zzfn(new zzfs(context));
                }
            }
        }
        return zzati;
    }

    private zzfn(zzfs zzfs) {
        this(zzfs, null);
    }

    private zzfn(zzfs zzfs, zzbw zzbw) {
        this.zzvz = false;
        Preconditions.checkNotNull(zzfs);
        this.zzada = zzbw.zza(zzfs.zzri, (zzan) null);
        this.zzaue = -1;
        zzft zzft = new zzft(this);
        zzft.zzq();
        this.zzatp = zzft;
        zzaw zzaw = new zzaw(this);
        zzaw.zzq();
        this.zzatk = zzaw;
        zzbq zzbq = new zzbq(this);
        zzbq.zzq();
        this.zzatj = zzbq;
        this.zzada.zzgs().zzc((Runnable) new zzfo(this, zzfs));
    }

    /* access modifiers changed from: private */
    public final void zza(zzfs zzfs) {
        this.zzada.zzgs().zzaf();
        zzt zzt = new zzt(this);
        zzt.zzq();
        this.zzatl = zzt;
        this.zzada.zzgv().zza((zzs) this.zzatj);
        zzm zzm = new zzm(this);
        zzm.zzq();
        this.zzato = zzm;
        zzdv zzdv = new zzdv(this);
        zzdv.zzq();
        this.zzatq = zzdv;
        zzfj zzfj = new zzfj(this);
        zzfj.zzq();
        this.zzatn = zzfj;
        this.zzatm = new zzbb(this);
        if (this.zzatv != this.zzatw) {
            this.zzada.zzgt().zzjg().zze("Not all upload components initialized", Integer.valueOf(this.zzatv), Integer.valueOf(this.zzatw));
        }
        this.zzvz = true;
    }

    /* access modifiers changed from: protected */
    public final void start() {
        this.zzada.zzgs().zzaf();
        zzjt().zzij();
        if (this.zzada.zzgu().zzanc.get() == 0) {
            this.zzada.zzgu().zzanc.set(this.zzada.zzbx().currentTimeMillis());
        }
        zzmb();
    }

    public final zzn zzgw() {
        return this.zzada.zzgw();
    }

    public final zzq zzgv() {
        return this.zzada.zzgv();
    }

    public final zzas zzgt() {
        return this.zzada.zzgt();
    }

    public final zzbr zzgs() {
        return this.zzada.zzgs();
    }

    private final zzbq zzls() {
        zza((zzfm) this.zzatj);
        return this.zzatj;
    }

    public final zzaw zzlt() {
        zza((zzfm) this.zzatk);
        return this.zzatk;
    }

    public final zzt zzjt() {
        zza((zzfm) this.zzatl);
        return this.zzatl;
    }

    private final zzbb zzlu() {
        zzbb zzbb = this.zzatm;
        if (zzbb != null) {
            return zzbb;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzfj zzlv() {
        zza((zzfm) this.zzatn);
        return this.zzatn;
    }

    public final zzm zzjs() {
        zza((zzfm) this.zzato);
        return this.zzato;
    }

    public final zzdv zzlw() {
        zza((zzfm) this.zzatq);
        return this.zzatq;
    }

    public final zzft zzjr() {
        zza((zzfm) this.zzatp);
        return this.zzatp;
    }

    public final zzaq zzgq() {
        return this.zzada.zzgq();
    }

    public final Context getContext() {
        return this.zzada.getContext();
    }

    public final Clock zzbx() {
        return this.zzada.zzbx();
    }

    public final zzfx zzgr() {
        return this.zzada.zzgr();
    }

    private final void zzaf() {
        this.zzada.zzgs().zzaf();
    }

    /* access modifiers changed from: 0000 */
    public final void zzlx() {
        if (!this.zzvz) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    private static void zza(zzfm zzfm) {
        if (zzfm == null) {
            throw new IllegalStateException("Upload Component not created");
        } else if (!zzfm.isInitialized()) {
            String valueOf = String.valueOf(zzfm.getClass());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("Component not initialized: ");
            sb.append(valueOf);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zze(zzk zzk) {
        zzaf();
        zzlx();
        Preconditions.checkNotEmpty(zzk.packageName);
        zzg(zzk);
    }

    private final long zzly() {
        long currentTimeMillis = this.zzada.zzbx().currentTimeMillis();
        zzbd zzgu = this.zzada.zzgu();
        zzgu.zzcl();
        zzgu.zzaf();
        long j = zzgu.zzang.get();
        if (j == 0) {
            j = 1 + ((long) zzgu.zzgr().zzmk().nextInt(86400000));
            zzgu.zzang.set(j);
        }
        return ((((currentTimeMillis + j) / 1000) / 60) / 60) / 24;
    }

    /* access modifiers changed from: 0000 */
    public final void zzd(zzag zzag, String str) {
        zzag zzag2 = zzag;
        String str2 = str;
        zzg zzbm = zzjt().zzbm(str2);
        if (zzbm == null || TextUtils.isEmpty(zzbm.zzak())) {
            this.zzada.zzgt().zzjn().zzg("No app data available; dropping event", str2);
            return;
        }
        Boolean zzc = zzc(zzbm);
        if (zzc == null) {
            if (!"_ui".equals(zzag2.name)) {
                this.zzada.zzgt().zzjj().zzg("Could not find package. appId", zzas.zzbw(str));
            }
        } else if (!zzc.booleanValue()) {
            this.zzada.zzgt().zzjg().zzg("App version does not match; dropping event. appId", zzas.zzbw(str));
            return;
        }
        zzk zzk = r2;
        zzg zzg = zzbm;
        zzk zzk2 = new zzk(str, zzbm.getGmpAppId(), zzbm.zzak(), zzbm.zzhf(), zzbm.zzhg(), zzbm.zzhh(), zzbm.zzhi(), (String) null, zzbm.isMeasurementEnabled(), false, zzg.getFirebaseInstanceId(), zzg.zzhv(), 0, 0, zzg.zzhw(), zzg.zzhx(), false, zzg.zzhb());
        zzc(zzag2, zzk);
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(zzag zzag, zzk zzk) {
        List<zzo> list;
        List<zzo> list2;
        List<zzo> list3;
        zzag zzag2 = zzag;
        zzk zzk2 = zzk;
        Preconditions.checkNotNull(zzk);
        Preconditions.checkNotEmpty(zzk2.packageName);
        zzaf();
        zzlx();
        String str = zzk2.packageName;
        long j = zzag2.zzaig;
        if (zzjr().zze(zzag2, zzk2)) {
            if (!zzk2.zzafr) {
                zzg(zzk2);
                return;
            }
            zzjt().beginTransaction();
            try {
                zzt zzjt = zzjt();
                Preconditions.checkNotEmpty(str);
                zzjt.zzaf();
                zzjt.zzcl();
                if (j < 0) {
                    zzjt.zzgt().zzjj().zze("Invalid time querying timed out conditional properties", zzas.zzbw(str), Long.valueOf(j));
                    list = Collections.emptyList();
                } else {
                    list = zzjt.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str, String.valueOf(j)});
                }
                for (zzo zzo : list) {
                    if (zzo != null) {
                        this.zzada.zzgt().zzjn().zzd("User property timed out", zzo.packageName, this.zzada.zzgq().zzbv(zzo.zzags.name), zzo.zzags.getValue());
                        if (zzo.zzagt != null) {
                            zzd(new zzag(zzo.zzagt, j), zzk2);
                        }
                        zzjt().zzk(str, zzo.zzags.name);
                    }
                }
                zzt zzjt2 = zzjt();
                Preconditions.checkNotEmpty(str);
                zzjt2.zzaf();
                zzjt2.zzcl();
                if (j < 0) {
                    zzjt2.zzgt().zzjj().zze("Invalid time querying expired conditional properties", zzas.zzbw(str), Long.valueOf(j));
                    list2 = Collections.emptyList();
                } else {
                    list2 = zzjt2.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(list2.size());
                for (zzo zzo2 : list2) {
                    if (zzo2 != null) {
                        this.zzada.zzgt().zzjn().zzd("User property expired", zzo2.packageName, this.zzada.zzgq().zzbv(zzo2.zzags.name), zzo2.zzags.getValue());
                        zzjt().zzh(str, zzo2.zzags.name);
                        if (zzo2.zzagv != null) {
                            arrayList.add(zzo2.zzagv);
                        }
                        zzjt().zzk(str, zzo2.zzags.name);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    zzd(new zzag((zzag) obj, j), zzk2);
                }
                zzt zzjt3 = zzjt();
                String str2 = zzag2.name;
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotEmpty(str2);
                zzjt3.zzaf();
                zzjt3.zzcl();
                if (j < 0) {
                    zzjt3.zzgt().zzjj().zzd("Invalid time querying triggered conditional properties", zzas.zzbw(str), zzjt3.zzgq().zzbt(str2), Long.valueOf(j));
                    list3 = Collections.emptyList();
                } else {
                    list3 = zzjt3.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str, str2, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(list3.size());
                for (zzo zzo3 : list3) {
                    if (zzo3 != null) {
                        zzfu zzfu = zzo3.zzags;
                        zzfw zzfw = r4;
                        zzfw zzfw2 = new zzfw(zzo3.packageName, zzo3.origin, zzfu.name, j, zzfu.getValue());
                        if (zzjt().zza(zzfw)) {
                            this.zzada.zzgt().zzjn().zzd("User property triggered", zzo3.packageName, this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                        } else {
                            this.zzada.zzgt().zzjg().zzd("Too many active user properties, ignoring", zzas.zzbw(zzo3.packageName), this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                        }
                        if (zzo3.zzagu != null) {
                            arrayList3.add(zzo3.zzagu);
                        }
                        zzo3.zzags = new zzfu(zzfw);
                        zzo3.active = true;
                        zzjt().zza(zzo3);
                    }
                }
                zzd(zzag, zzk);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList4.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList4.get(i2);
                    i2++;
                    zzd(new zzag((zzag) obj2, j), zzk2);
                }
                zzjt().setTransactionSuccessful();
            } finally {
                zzjt().endTransaction();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:206:0x0791 A[Catch:{ SQLiteException -> 0x0231, all -> 0x0803 }] */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x07be A[Catch:{ SQLiteException -> 0x0231, all -> 0x0803 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0264 A[Catch:{ SQLiteException -> 0x0231, all -> 0x0803 }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x029c A[Catch:{ SQLiteException -> 0x0231, all -> 0x0803 }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02e9 A[Catch:{ SQLiteException -> 0x0231, all -> 0x0803 }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0316  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzd(com.google.android.gms.measurement.internal.zzag r27, com.google.android.gms.measurement.internal.zzk r28) {
        /*
            r26 = this;
            r1 = r26
            r2 = r27
            r3 = r28
            java.lang.String r4 = "_sno"
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r28)
            java.lang.String r0 = r3.packageName
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r0)
            long r5 = java.lang.System.nanoTime()
            r26.zzaf()
            r26.zzlx()
            java.lang.String r15 = r3.packageName
            com.google.android.gms.measurement.internal.zzft r0 = r26.zzjr()
            boolean r0 = r0.zze(r2, r3)
            if (r0 != 0) goto L_0x0027
            return
        L_0x0027:
            boolean r0 = r3.zzafr
            if (r0 != 0) goto L_0x002f
            r1.zzg(r3)
            return
        L_0x002f:
            com.google.android.gms.measurement.internal.zzbq r0 = r26.zzls()
            java.lang.String r7 = r2.name
            boolean r0 = r0.zzo(r15, r7)
            java.lang.String r14 = "_err"
            r13 = 0
            r22 = 1
            if (r0 == 0) goto L_0x00d9
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r15)
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada
            com.google.android.gms.measurement.internal.zzaq r4 = r4.zzgq()
            java.lang.String r5 = r2.name
            java.lang.String r4 = r4.zzbt(r5)
            java.lang.String r5 = "Dropping blacklisted event. appId"
            r0.zze(r5, r3, r4)
            com.google.android.gms.measurement.internal.zzbq r0 = r26.zzls()
            boolean r0 = r0.zzcl(r15)
            if (r0 != 0) goto L_0x0073
            com.google.android.gms.measurement.internal.zzbq r0 = r26.zzls()
            boolean r0 = r0.zzcm(r15)
            if (r0 == 0) goto L_0x0074
        L_0x0073:
            r13 = 1
        L_0x0074:
            if (r13 != 0) goto L_0x008f
            java.lang.String r0 = r2.name
            boolean r0 = r14.equals(r0)
            if (r0 != 0) goto L_0x008f
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada
            com.google.android.gms.measurement.internal.zzfx r7 = r0.zzgr()
            r9 = 11
            java.lang.String r11 = r2.name
            r12 = 0
            java.lang.String r10 = "_ev"
            r8 = r15
            r7.zza(r8, r9, r10, r11, r12)
        L_0x008f:
            if (r13 == 0) goto L_0x00d8
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            com.google.android.gms.measurement.internal.zzg r0 = r0.zzbm(r15)
            if (r0 == 0) goto L_0x00d8
            long r2 = r0.zzhl()
            long r4 = r0.zzhk()
            long r2 = java.lang.Math.max(r2, r4)
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada
            com.google.android.gms.common.util.Clock r4 = r4.zzbx()
            long r4 = r4.currentTimeMillis()
            long r4 = r4 - r2
            long r2 = java.lang.Math.abs(r4)
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r4 = com.google.android.gms.measurement.internal.zzai.zzajt
            java.lang.Object r4 = r4.get()
            java.lang.Long r4 = (java.lang.Long) r4
            long r4 = r4.longValue()
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 <= 0) goto L_0x00d8
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjn()
            java.lang.String r3 = "Fetching config for blacklisted app"
            r2.zzby(r3)
            r1.zzb(r0)
        L_0x00d8:
            return
        L_0x00d9:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()
            r11 = 2
            boolean r0 = r0.isLoggable(r11)
            if (r0 == 0) goto L_0x00ff
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada
            com.google.android.gms.measurement.internal.zzaq r7 = r7.zzgq()
            java.lang.String r7 = r7.zzb(r2)
            java.lang.String r8 = "Logging event"
            r0.zzg(r8, r7)
        L_0x00ff:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.beginTransaction()
            r1.zzg(r3)     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = "_iap"
            java.lang.String r7 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r0 = r0.equals(r7)     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "ecommerce_purchase"
            if (r0 != 0) goto L_0x0124
            java.lang.String r0 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r0 = r7.equals(r0)     // Catch:{ all -> 0x0803 }
            if (r0 == 0) goto L_0x011e
            goto L_0x0124
        L_0x011e:
            r23 = r5
            r5 = 2
            r6 = 0
            goto L_0x02ab
        L_0x0124:
            com.google.android.gms.measurement.internal.zzad r0 = r2.zzahu     // Catch:{ all -> 0x0803 }
            java.lang.String r8 = "currency"
            java.lang.String r0 = r0.getString(r8)     // Catch:{ all -> 0x0803 }
            java.lang.String r8 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r7 = r7.equals(r8)     // Catch:{ all -> 0x0803 }
            java.lang.String r8 = "value"
            if (r7 == 0) goto L_0x018b
            com.google.android.gms.measurement.internal.zzad r7 = r2.zzahu     // Catch:{ all -> 0x0803 }
            java.lang.Double r7 = r7.zzbr(r8)     // Catch:{ all -> 0x0803 }
            double r9 = r7.doubleValue()     // Catch:{ all -> 0x0803 }
            r16 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r9 = r9 * r16
            r18 = 0
            int r7 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            if (r7 != 0) goto L_0x015d
            com.google.android.gms.measurement.internal.zzad r7 = r2.zzahu     // Catch:{ all -> 0x0803 }
            java.lang.Long r7 = r7.getLong(r8)     // Catch:{ all -> 0x0803 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0803 }
            double r7 = (double) r7
            java.lang.Double.isNaN(r7)
            double r9 = r7 * r16
        L_0x015d:
            r7 = 4890909195324358656(0x43e0000000000000, double:9.223372036854776E18)
            int r12 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r12 > 0) goto L_0x016e
            r7 = -4332462841530417152(0xc3e0000000000000, double:-9.223372036854776E18)
            int r12 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r12 < 0) goto L_0x016e
            long r7 = java.lang.Math.round(r9)     // Catch:{ all -> 0x0803 }
            goto L_0x0195
        L_0x016e:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "Data lost. Currency value is too big. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r15)     // Catch:{ all -> 0x0803 }
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ all -> 0x0803 }
            r0.zze(r7, r8, r9)     // Catch:{ all -> 0x0803 }
            r23 = r5
            r5 = 2
            r6 = 0
            goto L_0x029a
        L_0x018b:
            com.google.android.gms.measurement.internal.zzad r7 = r2.zzahu     // Catch:{ all -> 0x0803 }
            java.lang.Long r7 = r7.getLong(r8)     // Catch:{ all -> 0x0803 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0803 }
        L_0x0195:
            boolean r9 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0803 }
            if (r9 != 0) goto L_0x0295
            java.util.Locale r9 = java.util.Locale.US     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r0.toUpperCase(r9)     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = "[A-Z]{3}"
            boolean r9 = r0.matches(r9)     // Catch:{ all -> 0x0803 }
            if (r9 == 0) goto L_0x0295
            java.lang.String r9 = "_ltv_"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0803 }
            int r10 = r0.length()     // Catch:{ all -> 0x0803 }
            if (r10 == 0) goto L_0x01ba
            java.lang.String r0 = r9.concat(r0)     // Catch:{ all -> 0x0803 }
            goto L_0x01bf
        L_0x01ba:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x0803 }
            r0.<init>(r9)     // Catch:{ all -> 0x0803 }
        L_0x01bf:
            r10 = r0
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r0 = r0.zzi(r15, r10)     // Catch:{ all -> 0x0803 }
            if (r0 == 0) goto L_0x01fb
            java.lang.Object r9 = r0.value     // Catch:{ all -> 0x0803 }
            boolean r9 = r9 instanceof java.lang.Long     // Catch:{ all -> 0x0803 }
            if (r9 != 0) goto L_0x01d1
            goto L_0x01fb
        L_0x01d1:
            java.lang.Object r0 = r0.value     // Catch:{ all -> 0x0803 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ all -> 0x0803 }
            long r16 = r0.longValue()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r0 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = r2.origin     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r12 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.util.Clock r12 = r12.zzbx()     // Catch:{ all -> 0x0803 }
            long r18 = r12.currentTimeMillis()     // Catch:{ all -> 0x0803 }
            long r16 = r16 + r7
            java.lang.Long r16 = java.lang.Long.valueOf(r16)     // Catch:{ all -> 0x0803 }
            r7 = r0
            r8 = r15
            r23 = r5
            r5 = 2
            r11 = r18
            r6 = 0
            r13 = r16
            r7.<init>(r8, r9, r10, r11, r13)     // Catch:{ all -> 0x0803 }
            goto L_0x025a
        L_0x01fb:
            r23 = r5
            r5 = 2
            r6 = 0
            com.google.android.gms.measurement.internal.zzt r9 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r11 = com.google.android.gms.measurement.internal.zzai.zzajy     // Catch:{ all -> 0x0803 }
            int r0 = r0.zzb(r15, r11)     // Catch:{ all -> 0x0803 }
            int r0 = r0 + -1
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r15)     // Catch:{ all -> 0x0803 }
            r9.zzaf()     // Catch:{ all -> 0x0803 }
            r9.zzcl()     // Catch:{ all -> 0x0803 }
            android.database.sqlite.SQLiteDatabase r11 = r9.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0231 }
            java.lang.String r12 = "delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);"
            r13 = 3
            java.lang.String[] r13 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x0231 }
            r13[r6] = r15     // Catch:{ SQLiteException -> 0x0231 }
            r13[r22] = r15     // Catch:{ SQLiteException -> 0x0231 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ SQLiteException -> 0x0231 }
            r13[r5] = r0     // Catch:{ SQLiteException -> 0x0231 }
            r11.execSQL(r12, r13)     // Catch:{ SQLiteException -> 0x0231 }
            goto L_0x0243
        L_0x0231:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzas r9 = r9.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r11 = "Error pruning currencies. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzas.zzbw(r15)     // Catch:{ all -> 0x0803 }
            r9.zze(r11, r12, r0)     // Catch:{ all -> 0x0803 }
        L_0x0243:
            com.google.android.gms.measurement.internal.zzfw r0 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = r2.origin     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r11 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.util.Clock r11 = r11.zzbx()     // Catch:{ all -> 0x0803 }
            long r11 = r11.currentTimeMillis()     // Catch:{ all -> 0x0803 }
            java.lang.Long r13 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0803 }
            r7 = r0
            r8 = r15
            r7.<init>(r8, r9, r10, r11, r13)     // Catch:{ all -> 0x0803 }
        L_0x025a:
            com.google.android.gms.measurement.internal.zzt r7 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            boolean r7 = r7.zza(r0)     // Catch:{ all -> 0x0803 }
            if (r7 != 0) goto L_0x0299
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r7 = r7.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r8 = "Too many unique user properties are set. Ignoring user property. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzas.zzbw(r15)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r10 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaq r10 = r10.zzgq()     // Catch:{ all -> 0x0803 }
            java.lang.String r11 = r0.name     // Catch:{ all -> 0x0803 }
            java.lang.String r10 = r10.zzbv(r11)     // Catch:{ all -> 0x0803 }
            java.lang.Object r0 = r0.value     // Catch:{ all -> 0x0803 }
            r7.zzd(r8, r9, r10, r0)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r7 = r0.zzgr()     // Catch:{ all -> 0x0803 }
            r9 = 9
            r10 = 0
            r11 = 0
            r12 = 0
            r8 = r15
            r7.zza(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0803 }
            goto L_0x0299
        L_0x0295:
            r23 = r5
            r5 = 2
            r6 = 0
        L_0x0299:
            r13 = 1
        L_0x029a:
            if (r13 != 0) goto L_0x02ab
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            return
        L_0x02ab:
            java.lang.String r0 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r0 = com.google.android.gms.measurement.internal.zzfx.zzct(r0)     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r16 = r14.equals(r7)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r7 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            long r8 = r26.zzly()     // Catch:{ all -> 0x0803 }
            r11 = 1
            r13 = 0
            r17 = 0
            r10 = r15
            r12 = r0
            r14 = r16
            r18 = r15
            r15 = r17
            com.google.android.gms.measurement.internal.zzu r7 = r7.zza(r8, r10, r11, r12, r13, r14, r15)     // Catch:{ all -> 0x0803 }
            long r8 = r7.zzahi     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r10 = com.google.android.gms.measurement.internal.zzai.zzaje     // Catch:{ all -> 0x0803 }
            java.lang.Object r10 = r10.get()     // Catch:{ all -> 0x0803 }
            java.lang.Integer r10 = (java.lang.Integer) r10     // Catch:{ all -> 0x0803 }
            int r10 = r10.intValue()     // Catch:{ all -> 0x0803 }
            long r10 = (long) r10     // Catch:{ all -> 0x0803 }
            long r8 = r8 - r10
            r10 = 1000(0x3e8, double:4.94E-321)
            r12 = 1
            r14 = 0
            int r17 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r17 <= 0) goto L_0x0316
            long r8 = r8 % r10
            int r0 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x0307
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r2 = "Data loss. Too many events logged. appId, count"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r18)     // Catch:{ all -> 0x0803 }
            long r4 = r7.zzahi     // Catch:{ all -> 0x0803 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0803 }
            r0.zze(r2, r3, r4)     // Catch:{ all -> 0x0803 }
        L_0x0307:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            return
        L_0x0316:
            if (r0 == 0) goto L_0x0372
            long r8 = r7.zzahh     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r17 = com.google.android.gms.measurement.internal.zzai.zzajg     // Catch:{ all -> 0x0803 }
            java.lang.Object r17 = r17.get()     // Catch:{ all -> 0x0803 }
            java.lang.Integer r17 = (java.lang.Integer) r17     // Catch:{ all -> 0x0803 }
            int r5 = r17.intValue()     // Catch:{ all -> 0x0803 }
            r17 = r7
            long r6 = (long) r5     // Catch:{ all -> 0x0803 }
            long r8 = r8 - r6
            int r5 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r5 <= 0) goto L_0x036f
            long r8 = r8 % r10
            int r0 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x034e
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r3 = "Data loss. Too many public events logged. appId, count"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r18)     // Catch:{ all -> 0x0803 }
            r5 = r17
            long r5 = r5.zzahh     // Catch:{ all -> 0x0803 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0803 }
            r0.zze(r3, r4, r5)     // Catch:{ all -> 0x0803 }
        L_0x034e:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r7 = r0.zzgr()     // Catch:{ all -> 0x0803 }
            r9 = 16
            java.lang.String r10 = "_ev"
            java.lang.String r11 = r2.name     // Catch:{ all -> 0x0803 }
            r12 = 0
            r8 = r18
            r7.zza(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            return
        L_0x036f:
            r5 = r17
            goto L_0x0373
        L_0x0372:
            r5 = r7
        L_0x0373:
            if (r16 == 0) goto L_0x03c3
            long r6 = r5.zzahk     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r8 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzq r8 = r8.zzgv()     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = r3.packageName     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r10 = com.google.android.gms.measurement.internal.zzai.zzajf     // Catch:{ all -> 0x0803 }
            int r8 = r8.zzb(r9, r10)     // Catch:{ all -> 0x0803 }
            r9 = 1000000(0xf4240, float:1.401298E-39)
            int r8 = java.lang.Math.min(r9, r8)     // Catch:{ all -> 0x0803 }
            r11 = 0
            int r8 = java.lang.Math.max(r11, r8)     // Catch:{ all -> 0x0803 }
            long r8 = (long) r8     // Catch:{ all -> 0x0803 }
            long r6 = r6 - r8
            int r8 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r8 <= 0) goto L_0x03c4
            int r0 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x03b4
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r2 = "Too many error events logged. appId, count"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r18)     // Catch:{ all -> 0x0803 }
            long r4 = r5.zzahk     // Catch:{ all -> 0x0803 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0803 }
            r0.zze(r2, r3, r4)     // Catch:{ all -> 0x0803 }
        L_0x03b4:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            return
        L_0x03c3:
            r11 = 0
        L_0x03c4:
            com.google.android.gms.measurement.internal.zzad r5 = r2.zzahu     // Catch:{ all -> 0x0803 }
            android.os.Bundle r5 = r5.zziy()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "_o"
            java.lang.String r8 = r2.origin     // Catch:{ all -> 0x0803 }
            r6.zza(r5, r7, r8)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0803 }
            r10 = r18
            boolean r6 = r6.zzcz(r10)     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = "_r"
            if (r6 == 0) goto L_0x0403
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "_dbg"
            java.lang.Long r8 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0803 }
            r6.zza(r5, r7, r8)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0803 }
            java.lang.Long r7 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0803 }
            r6.zza(r5, r9, r7)     // Catch:{ all -> 0x0803 }
        L_0x0403:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzq r6 = r6.zzgv()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            boolean r6 = r6.zzbh(r7)     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0438
            java.lang.String r6 = "_s"
            java.lang.String r7 = r2.name     // Catch:{ all -> 0x0803 }
            boolean r6 = r6.equals(r7)     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0438
            com.google.android.gms.measurement.internal.zzt r6 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r6 = r6.zzi(r7, r4)     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0438
            java.lang.Object r7 = r6.value     // Catch:{ all -> 0x0803 }
            boolean r7 = r7 instanceof java.lang.Long     // Catch:{ all -> 0x0803 }
            if (r7 == 0) goto L_0x0438
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r7 = r7.zzgr()     // Catch:{ all -> 0x0803 }
            java.lang.Object r6 = r6.value     // Catch:{ all -> 0x0803 }
            r7.zza(r5, r4, r6)     // Catch:{ all -> 0x0803 }
        L_0x0438:
            com.google.android.gms.measurement.internal.zzt r4 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            long r6 = r4.zzbn(r10)     // Catch:{ all -> 0x0803 }
            int r4 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r4 <= 0) goto L_0x045b
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjj()     // Catch:{ all -> 0x0803 }
            java.lang.String r8 = "Data lost. Too many events stored on disk, deleted. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzas.zzbw(r10)     // Catch:{ all -> 0x0803 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0803 }
            r4.zze(r8, r12, r6)     // Catch:{ all -> 0x0803 }
        L_0x045b:
            com.google.android.gms.measurement.internal.zzab r4 = new com.google.android.gms.measurement.internal.zzab     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r8 = r1.zzada     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r2.origin     // Catch:{ all -> 0x0803 }
            java.lang.String r12 = r2.name     // Catch:{ all -> 0x0803 }
            long r14 = r2.zzaig     // Catch:{ all -> 0x0803 }
            r18 = 0
            r7 = r4
            r2 = r9
            r9 = r6
            r6 = r10
            r25 = 0
            r11 = r12
            r12 = r14
            r14 = r18
            r16 = r5
            r7.<init>(r8, r9, r10, r11, r12, r14, r16)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r5 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r4.name     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzac r5 = r5.zzg(r6, r7)     // Catch:{ all -> 0x0803 }
            if (r5 != 0) goto L_0x04e8
            com.google.android.gms.measurement.internal.zzt r5 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            long r7 = r5.zzbq(r6)     // Catch:{ all -> 0x0803 }
            r9 = 500(0x1f4, double:2.47E-321)
            int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r5 < 0) goto L_0x04ce
            if (r0 == 0) goto L_0x04ce
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r2 = "Too many event names used, ignoring event. appId, name, supported count"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r6)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaq r5 = r5.zzgq()     // Catch:{ all -> 0x0803 }
            java.lang.String r4 = r4.name     // Catch:{ all -> 0x0803 }
            java.lang.String r4 = r5.zzbt(r4)     // Catch:{ all -> 0x0803 }
            r5 = 500(0x1f4, float:7.0E-43)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0803 }
            r0.zzd(r2, r3, r4, r5)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r7 = r0.zzgr()     // Catch:{ all -> 0x0803 }
            r9 = 8
            r10 = 0
            r11 = 0
            r12 = 0
            r8 = r6
            r7.zza(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            return
        L_0x04ce:
            com.google.android.gms.measurement.internal.zzac r0 = new com.google.android.gms.measurement.internal.zzac     // Catch:{ all -> 0x0803 }
            java.lang.String r9 = r4.name     // Catch:{ all -> 0x0803 }
            r10 = 0
            r12 = 0
            long r14 = r4.timestamp     // Catch:{ all -> 0x0803 }
            r16 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r7 = r0
            r8 = r6
            r7.<init>(r8, r9, r10, r12, r14, r16, r18, r19, r20, r21)     // Catch:{ all -> 0x0803 }
            goto L_0x04f6
        L_0x04e8:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            long r6 = r5.zzahx     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzab r4 = r4.zza(r0, r6)     // Catch:{ all -> 0x0803 }
            long r6 = r4.timestamp     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzac r0 = r5.zzae(r6)     // Catch:{ all -> 0x0803 }
        L_0x04f6:
            com.google.android.gms.measurement.internal.zzt r5 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r5.zza(r0)     // Catch:{ all -> 0x0803 }
            r26.zzaf()     // Catch:{ all -> 0x0803 }
            r26.zzlx()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r4)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r28)     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r4.zztt     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r0)     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r4.zztt     // Catch:{ all -> 0x0803 }
            java.lang.String r5 = r3.packageName     // Catch:{ all -> 0x0803 }
            boolean r0 = r0.equals(r5)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.common.internal.Preconditions.checkArgument(r0)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.internal.measurement.zzfw r5 = new com.google.android.gms.internal.measurement.zzfw     // Catch:{ all -> 0x0803 }
            r5.<init>()     // Catch:{ all -> 0x0803 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r22)     // Catch:{ all -> 0x0803 }
            r5.zzaxj = r0     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = "android"
            r5.zzaxr = r0     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r3.packageName     // Catch:{ all -> 0x0803 }
            r5.zztt = r0     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r3.zzafp     // Catch:{ all -> 0x0803 }
            r5.zzafp = r0     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r3.zzts     // Catch:{ all -> 0x0803 }
            r5.zzts = r0     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafo     // Catch:{ all -> 0x0803 }
            r8 = -2147483648(0xffffffff80000000, double:NaN)
            r0 = 0
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 != 0) goto L_0x0540
            r6 = r0
            goto L_0x0547
        L_0x0540:
            long r6 = r3.zzafo     // Catch:{ all -> 0x0803 }
            int r7 = (int) r6     // Catch:{ all -> 0x0803 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0803 }
        L_0x0547:
            r5.zzayd = r6     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzade     // Catch:{ all -> 0x0803 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0803 }
            r5.zzaxv = r6     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzafi     // Catch:{ all -> 0x0803 }
            r5.zzafi = r6     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzafv     // Catch:{ all -> 0x0803 }
            r5.zzawr = r6     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafq     // Catch:{ all -> 0x0803 }
            r8 = 0
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 != 0) goto L_0x0563
            r6 = r0
            goto L_0x0569
        L_0x0563:
            long r6 = r3.zzafq     // Catch:{ all -> 0x0803 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0803 }
        L_0x0569:
            r5.zzaxz = r6     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzq r6 = r6.zzgv()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r10 = com.google.android.gms.measurement.internal.zzai.zzalg     // Catch:{ all -> 0x0803 }
            boolean r6 = r6.zze(r7, r10)     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0585
            com.google.android.gms.measurement.internal.zzft r6 = r26.zzjr()     // Catch:{ all -> 0x0803 }
            int[] r6 = r6.zzmi()     // Catch:{ all -> 0x0803 }
            r5.zzayn = r6     // Catch:{ all -> 0x0803 }
        L_0x0585:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbd r6 = r6.zzgu()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            android.util.Pair r6 = r6.zzbz(r7)     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x05ae
            java.lang.Object r7 = r6.first     // Catch:{ all -> 0x0803 }
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch:{ all -> 0x0803 }
            boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0803 }
            if (r7 != 0) goto L_0x05ae
            boolean r7 = r3.zzaft     // Catch:{ all -> 0x0803 }
            if (r7 == 0) goto L_0x060b
            java.lang.Object r7 = r6.first     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x0803 }
            r5.zzaxx = r7     // Catch:{ all -> 0x0803 }
            java.lang.Object r6 = r6.second     // Catch:{ all -> 0x0803 }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ all -> 0x0803 }
            r5.zzaxy = r6     // Catch:{ all -> 0x0803 }
            goto L_0x060b
        L_0x05ae:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaa r6 = r6.zzgp()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0803 }
            android.content.Context r7 = r7.getContext()     // Catch:{ all -> 0x0803 }
            boolean r6 = r6.zzl(r7)     // Catch:{ all -> 0x0803 }
            if (r6 != 0) goto L_0x060b
            boolean r6 = r3.zzafu     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x060b
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            android.content.Context r6 = r6.getContext()     // Catch:{ all -> 0x0803 }
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "android_id"
            java.lang.String r6 = android.provider.Settings.Secure.getString(r6, r7)     // Catch:{ all -> 0x0803 }
            if (r6 != 0) goto L_0x05ee
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r6 = r6.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r6 = r6.zzjj()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = "null secure ID. appId"
            java.lang.String r10 = r5.zztt     // Catch:{ all -> 0x0803 }
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzas.zzbw(r10)     // Catch:{ all -> 0x0803 }
            r6.zzg(r7, r10)     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = "null"
            goto L_0x0609
        L_0x05ee:
            boolean r7 = r6.isEmpty()     // Catch:{ all -> 0x0803 }
            if (r7 == 0) goto L_0x0609
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r7 = r7.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjj()     // Catch:{ all -> 0x0803 }
            java.lang.String r10 = "empty secure ID. appId"
            java.lang.String r11 = r5.zztt     // Catch:{ all -> 0x0803 }
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzas.zzbw(r11)     // Catch:{ all -> 0x0803 }
            r7.zzg(r10, r11)     // Catch:{ all -> 0x0803 }
        L_0x0609:
            r5.zzayg = r6     // Catch:{ all -> 0x0803 }
        L_0x060b:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaa r6 = r6.zzgp()     // Catch:{ all -> 0x0803 }
            r6.zzcl()     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = android.os.Build.MODEL     // Catch:{ all -> 0x0803 }
            r5.zzaxt = r6     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaa r6 = r6.zzgp()     // Catch:{ all -> 0x0803 }
            r6.zzcl()     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = android.os.Build.VERSION.RELEASE     // Catch:{ all -> 0x0803 }
            r5.zzaxs = r6     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaa r6 = r6.zzgp()     // Catch:{ all -> 0x0803 }
            long r6 = r6.zziw()     // Catch:{ all -> 0x0803 }
            int r7 = (int) r6     // Catch:{ all -> 0x0803 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0803 }
            r5.zzaxu = r6     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaa r6 = r6.zzgp()     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r6.zzix()     // Catch:{ all -> 0x0803 }
            r5.zzahr = r6     // Catch:{ all -> 0x0803 }
            r5.zzaxw = r0     // Catch:{ all -> 0x0803 }
            r5.zzaxm = r0     // Catch:{ all -> 0x0803 }
            r5.zzaxn = r0     // Catch:{ all -> 0x0803 }
            r5.zzaxo = r0     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafs     // Catch:{ all -> 0x0803 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0803 }
            r5.zzayi = r6     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            boolean r6 = r6.isEnabled()     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0662
            boolean r6 = com.google.android.gms.measurement.internal.zzq.zzie()     // Catch:{ all -> 0x0803 }
            if (r6 == 0) goto L_0x0662
            r5.zzayj = r0     // Catch:{ all -> 0x0803 }
        L_0x0662:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.packageName     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzg r0 = r0.zzbm(r6)     // Catch:{ all -> 0x0803 }
            if (r0 != 0) goto L_0x06d0
            com.google.android.gms.measurement.internal.zzg r0 = new com.google.android.gms.measurement.internal.zzg     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            r0.<init>(r6, r7)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r6.zzmm()     // Catch:{ all -> 0x0803 }
            r0.zzaj(r6)     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzafk     // Catch:{ all -> 0x0803 }
            r0.zzan(r6)     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzafi     // Catch:{ all -> 0x0803 }
            r0.zzak(r6)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbd r6 = r6.zzgu()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r3.packageName     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r6.zzca(r7)     // Catch:{ all -> 0x0803 }
            r0.zzam(r6)     // Catch:{ all -> 0x0803 }
            r0.zzt(r8)     // Catch:{ all -> 0x0803 }
            r0.zzo(r8)     // Catch:{ all -> 0x0803 }
            r0.zzp(r8)     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzts     // Catch:{ all -> 0x0803 }
            r0.setAppVersion(r6)     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafo     // Catch:{ all -> 0x0803 }
            r0.zzq(r6)     // Catch:{ all -> 0x0803 }
            java.lang.String r6 = r3.zzafp     // Catch:{ all -> 0x0803 }
            r0.zzao(r6)     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzade     // Catch:{ all -> 0x0803 }
            r0.zzr(r6)     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafq     // Catch:{ all -> 0x0803 }
            r0.zzs(r6)     // Catch:{ all -> 0x0803 }
            boolean r6 = r3.zzafr     // Catch:{ all -> 0x0803 }
            r0.setMeasurementEnabled(r6)     // Catch:{ all -> 0x0803 }
            long r6 = r3.zzafs     // Catch:{ all -> 0x0803 }
            r0.zzac(r6)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r6 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r6.zza(r0)     // Catch:{ all -> 0x0803 }
        L_0x06d0:
            java.lang.String r6 = r0.getAppInstanceId()     // Catch:{ all -> 0x0803 }
            r5.zzafh = r6     // Catch:{ all -> 0x0803 }
            java.lang.String r0 = r0.getFirebaseInstanceId()     // Catch:{ all -> 0x0803 }
            r5.zzafk = r0     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            java.lang.String r3 = r3.packageName     // Catch:{ all -> 0x0803 }
            java.util.List r0 = r0.zzbl(r3)     // Catch:{ all -> 0x0803 }
            int r3 = r0.size()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.internal.measurement.zzfz[] r3 = new com.google.android.gms.internal.measurement.zzfz[r3]     // Catch:{ all -> 0x0803 }
            r5.zzaxl = r3     // Catch:{ all -> 0x0803 }
            r3 = 0
        L_0x06ef:
            int r6 = r0.size()     // Catch:{ all -> 0x0803 }
            if (r3 >= r6) goto L_0x0728
            com.google.android.gms.internal.measurement.zzfz r6 = new com.google.android.gms.internal.measurement.zzfz     // Catch:{ all -> 0x0803 }
            r6.<init>()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.internal.measurement.zzfz[] r7 = r5.zzaxl     // Catch:{ all -> 0x0803 }
            r7[r3] = r6     // Catch:{ all -> 0x0803 }
            java.lang.Object r7 = r0.get(r3)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r7 = (com.google.android.gms.measurement.internal.zzfw) r7     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r7.name     // Catch:{ all -> 0x0803 }
            r6.name = r7     // Catch:{ all -> 0x0803 }
            java.lang.Object r7 = r0.get(r3)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r7 = (com.google.android.gms.measurement.internal.zzfw) r7     // Catch:{ all -> 0x0803 }
            long r10 = r7.zzaum     // Catch:{ all -> 0x0803 }
            java.lang.Long r7 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0803 }
            r6.zzayw = r7     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzft r7 = r26.zzjr()     // Catch:{ all -> 0x0803 }
            java.lang.Object r10 = r0.get(r3)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzfw r10 = (com.google.android.gms.measurement.internal.zzfw) r10     // Catch:{ all -> 0x0803 }
            java.lang.Object r10 = r10.value     // Catch:{ all -> 0x0803 }
            r7.zza(r6, r10)     // Catch:{ all -> 0x0803 }
            int r3 = r3 + 1
            goto L_0x06ef
        L_0x0728:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ IOException -> 0x0794 }
            long r5 = r0.zza(r5)     // Catch:{ IOException -> 0x0794 }
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzad r3 = r4.zzahu     // Catch:{ all -> 0x0803 }
            if (r3 == 0) goto L_0x078a
            com.google.android.gms.measurement.internal.zzad r3 = r4.zzahu     // Catch:{ all -> 0x0803 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0803 }
        L_0x073e:
            boolean r7 = r3.hasNext()     // Catch:{ all -> 0x0803 }
            if (r7 == 0) goto L_0x0752
            java.lang.Object r7 = r3.next()     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x0803 }
            boolean r7 = r2.equals(r7)     // Catch:{ all -> 0x0803 }
            if (r7 == 0) goto L_0x073e
        L_0x0750:
            r2 = 1
            goto L_0x078b
        L_0x0752:
            com.google.android.gms.measurement.internal.zzbq r2 = r26.zzls()     // Catch:{ all -> 0x0803 }
            java.lang.String r3 = r4.zztt     // Catch:{ all -> 0x0803 }
            java.lang.String r7 = r4.name     // Catch:{ all -> 0x0803 }
            boolean r2 = r2.zzp(r3, r7)     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzt r10 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            long r11 = r26.zzly()     // Catch:{ all -> 0x0803 }
            java.lang.String r13 = r4.zztt     // Catch:{ all -> 0x0803 }
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            com.google.android.gms.measurement.internal.zzu r3 = r10.zza(r11, r13, r14, r15, r16, r17, r18)     // Catch:{ all -> 0x0803 }
            if (r2 == 0) goto L_0x078a
            long r2 = r3.zzahl     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzq r7 = r7.zzgv()     // Catch:{ all -> 0x0803 }
            java.lang.String r10 = r4.zztt     // Catch:{ all -> 0x0803 }
            int r7 = r7.zzaq(r10)     // Catch:{ all -> 0x0803 }
            long r10 = (long) r7     // Catch:{ all -> 0x0803 }
            int r7 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r7 >= 0) goto L_0x078a
            goto L_0x0750
        L_0x078a:
            r2 = 0
        L_0x078b:
            boolean r0 = r0.zza(r4, r5, r2)     // Catch:{ all -> 0x0803 }
            if (r0 == 0) goto L_0x07aa
            r1.zzatt = r8     // Catch:{ all -> 0x0803 }
            goto L_0x07aa
        L_0x0794:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()     // Catch:{ all -> 0x0803 }
            java.lang.String r3 = "Data loss. Failed to insert raw event metadata. appId"
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0803 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzas.zzbw(r5)     // Catch:{ all -> 0x0803 }
            r2.zze(r3, r5, r0)     // Catch:{ all -> 0x0803 }
        L_0x07aa:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()     // Catch:{ all -> 0x0803 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            r2 = 2
            boolean r0 = r0.isLoggable(r2)     // Catch:{ all -> 0x0803 }
            if (r0 == 0) goto L_0x07d7
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()     // Catch:{ all -> 0x0803 }
            java.lang.String r2 = "Event recorded"
            com.google.android.gms.measurement.internal.zzbw r3 = r1.zzada     // Catch:{ all -> 0x0803 }
            com.google.android.gms.measurement.internal.zzaq r3 = r3.zzgq()     // Catch:{ all -> 0x0803 }
            java.lang.String r3 = r3.zza(r4)     // Catch:{ all -> 0x0803 }
            r0.zzg(r2, r3)     // Catch:{ all -> 0x0803 }
        L_0x07d7:
            com.google.android.gms.measurement.internal.zzt r0 = r26.zzjt()
            r0.endTransaction()
            r26.zzmb()
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()
            long r2 = java.lang.System.nanoTime()
            long r2 = r2 - r23
            r4 = 500000(0x7a120, double:2.47033E-318)
            long r2 = r2 + r4
            r4 = 1000000(0xf4240, double:4.940656E-318)
            long r2 = r2 / r4
            java.lang.Long r2 = java.lang.Long.valueOf(r2)
            java.lang.String r3 = "Background event processing time, ms"
            r0.zzg(r3, r2)
            return
        L_0x0803:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzt r2 = r26.zzjt()
            r2.endTransaction()
            goto L_0x080d
        L_0x080c:
            throw r0
        L_0x080d:
            goto L_0x080c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzd(com.google.android.gms.measurement.internal.zzag, com.google.android.gms.measurement.internal.zzk):void");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:92|93) */
    /* JADX WARNING: Code restructure failed: missing block: B:93:?, code lost:
        r1.zzada.zzgt().zzjg().zze("Failed to parse upload URL. Not uploading. appId", com.google.android.gms.measurement.internal.zzas.zzbw(r5), r6);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:92:0x02a0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzlz() {
        /*
            r17 = this;
            r1 = r17
            r17.zzaf()
            r17.zzlx()
            r0 = 1
            r1.zzatz = r0
            r2 = 0
            com.google.android.gms.measurement.internal.zzbw r3 = r1.zzada     // Catch:{ all -> 0x02dc }
            r3.zzgw()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbw r3 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzeb r3 = r3.zzgl()     // Catch:{ all -> 0x02dc }
            java.lang.Boolean r3 = r3.zzli()     // Catch:{ all -> 0x02dc }
            if (r3 != 0) goto L_0x0032
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = "Upload data called on the client side before use of service was decided"
            r0.zzby(r3)     // Catch:{ all -> 0x02dc }
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x0032:
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x02dc }
            if (r3 == 0) goto L_0x004d
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = "Upload called in the client side when service should be used"
            r0.zzby(r3)     // Catch:{ all -> 0x02dc }
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x004d:
            long r3 = r1.zzatt     // Catch:{ all -> 0x02dc }
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x005e
            r17.zzmb()     // Catch:{ all -> 0x02dc }
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x005e:
            r17.zzaf()     // Catch:{ all -> 0x02dc }
            java.util.List<java.lang.Long> r3 = r1.zzauc     // Catch:{ all -> 0x02dc }
            if (r3 == 0) goto L_0x0067
            r3 = 1
            goto L_0x0068
        L_0x0067:
            r3 = 0
        L_0x0068:
            if (r3 == 0) goto L_0x007f
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = "Uploading requested multiple times"
            r0.zzby(r3)     // Catch:{ all -> 0x02dc }
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x007f:
            com.google.android.gms.measurement.internal.zzaw r3 = r17.zzlt()     // Catch:{ all -> 0x02dc }
            boolean r3 = r3.zzfb()     // Catch:{ all -> 0x02dc }
            if (r3 != 0) goto L_0x00a1
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = "Network not connected, ignoring upload request"
            r0.zzby(r3)     // Catch:{ all -> 0x02dc }
            r17.zzmb()     // Catch:{ all -> 0x02dc }
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x00a1:
            com.google.android.gms.measurement.internal.zzbw r3 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.common.util.Clock r3 = r3.zzbx()     // Catch:{ all -> 0x02dc }
            long r3 = r3.currentTimeMillis()     // Catch:{ all -> 0x02dc }
            long r7 = com.google.android.gms.measurement.internal.zzq.zzic()     // Catch:{ all -> 0x02dc }
            long r7 = r3 - r7
            r9 = 0
            r1.zzd(r9, r7)     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbd r7 = r7.zzgu()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbg r7 = r7.zzanc     // Catch:{ all -> 0x02dc }
            long r7 = r7.get()     // Catch:{ all -> 0x02dc }
            int r10 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r10 == 0) goto L_0x00de
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjn()     // Catch:{ all -> 0x02dc }
            java.lang.String r6 = "Uploading events. Elapsed time since last upload attempt (ms)"
            long r7 = r3 - r7
            long r7 = java.lang.Math.abs(r7)     // Catch:{ all -> 0x02dc }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x02dc }
            r5.zzg(r6, r7)     // Catch:{ all -> 0x02dc }
        L_0x00de:
            com.google.android.gms.measurement.internal.zzt r5 = r17.zzjt()     // Catch:{ all -> 0x02dc }
            java.lang.String r5 = r5.zzih()     // Catch:{ all -> 0x02dc }
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x02dc }
            r7 = -1
            if (r6 != 0) goto L_0x02b4
            long r10 = r1.zzaue     // Catch:{ all -> 0x02dc }
            int r6 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r6 != 0) goto L_0x00fe
            com.google.android.gms.measurement.internal.zzt r6 = r17.zzjt()     // Catch:{ all -> 0x02dc }
            long r6 = r6.zzio()     // Catch:{ all -> 0x02dc }
            r1.zzaue = r6     // Catch:{ all -> 0x02dc }
        L_0x00fe:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzq r6 = r6.zzgv()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r7 = com.google.android.gms.measurement.internal.zzai.zzaja     // Catch:{ all -> 0x02dc }
            int r6 = r6.zzb(r5, r7)     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzq r7 = r7.zzgv()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r8 = com.google.android.gms.measurement.internal.zzai.zzajb     // Catch:{ all -> 0x02dc }
            int r7 = r7.zzb(r5, r8)     // Catch:{ all -> 0x02dc }
            int r7 = java.lang.Math.max(r2, r7)     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzt r8 = r17.zzjt()     // Catch:{ all -> 0x02dc }
            java.util.List r6 = r8.zzb(r5, r6, r7)     // Catch:{ all -> 0x02dc }
            boolean r7 = r6.isEmpty()     // Catch:{ all -> 0x02dc }
            if (r7 != 0) goto L_0x02d6
            java.util.Iterator r7 = r6.iterator()     // Catch:{ all -> 0x02dc }
        L_0x012c:
            boolean r8 = r7.hasNext()     // Catch:{ all -> 0x02dc }
            if (r8 == 0) goto L_0x0147
            java.lang.Object r8 = r7.next()     // Catch:{ all -> 0x02dc }
            android.util.Pair r8 = (android.util.Pair) r8     // Catch:{ all -> 0x02dc }
            java.lang.Object r8 = r8.first     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw r8 = (com.google.android.gms.internal.measurement.zzfw) r8     // Catch:{ all -> 0x02dc }
            java.lang.String r10 = r8.zzaxx     // Catch:{ all -> 0x02dc }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ all -> 0x02dc }
            if (r10 != 0) goto L_0x012c
            java.lang.String r7 = r8.zzaxx     // Catch:{ all -> 0x02dc }
            goto L_0x0148
        L_0x0147:
            r7 = r9
        L_0x0148:
            if (r7 == 0) goto L_0x0173
            r8 = 0
        L_0x014b:
            int r10 = r6.size()     // Catch:{ all -> 0x02dc }
            if (r8 >= r10) goto L_0x0173
            java.lang.Object r10 = r6.get(r8)     // Catch:{ all -> 0x02dc }
            android.util.Pair r10 = (android.util.Pair) r10     // Catch:{ all -> 0x02dc }
            java.lang.Object r10 = r10.first     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw r10 = (com.google.android.gms.internal.measurement.zzfw) r10     // Catch:{ all -> 0x02dc }
            java.lang.String r11 = r10.zzaxx     // Catch:{ all -> 0x02dc }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x02dc }
            if (r11 != 0) goto L_0x0170
            java.lang.String r10 = r10.zzaxx     // Catch:{ all -> 0x02dc }
            boolean r10 = r10.equals(r7)     // Catch:{ all -> 0x02dc }
            if (r10 != 0) goto L_0x0170
            java.util.List r6 = r6.subList(r2, r8)     // Catch:{ all -> 0x02dc }
            goto L_0x0173
        L_0x0170:
            int r8 = r8 + 1
            goto L_0x014b
        L_0x0173:
            com.google.android.gms.internal.measurement.zzfv r7 = new com.google.android.gms.internal.measurement.zzfv     // Catch:{ all -> 0x02dc }
            r7.<init>()     // Catch:{ all -> 0x02dc }
            int r8 = r6.size()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw[] r8 = new com.google.android.gms.internal.measurement.zzfw[r8]     // Catch:{ all -> 0x02dc }
            r7.zzaxh = r8     // Catch:{ all -> 0x02dc }
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ all -> 0x02dc }
            int r10 = r6.size()     // Catch:{ all -> 0x02dc }
            r8.<init>(r10)     // Catch:{ all -> 0x02dc }
            boolean r10 = com.google.android.gms.measurement.internal.zzq.zzie()     // Catch:{ all -> 0x02dc }
            if (r10 == 0) goto L_0x019d
            com.google.android.gms.measurement.internal.zzbw r10 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzq r10 = r10.zzgv()     // Catch:{ all -> 0x02dc }
            boolean r10 = r10.zzas(r5)     // Catch:{ all -> 0x02dc }
            if (r10 == 0) goto L_0x019d
            r10 = 1
            goto L_0x019e
        L_0x019d:
            r10 = 0
        L_0x019e:
            r11 = 0
        L_0x019f:
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            int r12 = r12.length     // Catch:{ all -> 0x02dc }
            if (r11 >= r12) goto L_0x01f7
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            java.lang.Object r13 = r6.get(r11)     // Catch:{ all -> 0x02dc }
            android.util.Pair r13 = (android.util.Pair) r13     // Catch:{ all -> 0x02dc }
            java.lang.Object r13 = r13.first     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw r13 = (com.google.android.gms.internal.measurement.zzfw) r13     // Catch:{ all -> 0x02dc }
            r12[r11] = r13     // Catch:{ all -> 0x02dc }
            java.lang.Object r12 = r6.get(r11)     // Catch:{ all -> 0x02dc }
            android.util.Pair r12 = (android.util.Pair) r12     // Catch:{ all -> 0x02dc }
            java.lang.Object r12 = r12.second     // Catch:{ all -> 0x02dc }
            java.lang.Long r12 = (java.lang.Long) r12     // Catch:{ all -> 0x02dc }
            r8.add(r12)     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            r12 = r12[r11]     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbw r13 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzq r13 = r13.zzgv()     // Catch:{ all -> 0x02dc }
            long r13 = r13.zzhh()     // Catch:{ all -> 0x02dc }
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch:{ all -> 0x02dc }
            r12.zzaxw = r13     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            r12 = r12[r11]     // Catch:{ all -> 0x02dc }
            java.lang.Long r13 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x02dc }
            r12.zzaxm = r13     // Catch:{ all -> 0x02dc }
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            r12 = r12[r11]     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzbw r13 = r1.zzada     // Catch:{ all -> 0x02dc }
            r13.zzgw()     // Catch:{ all -> 0x02dc }
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x02dc }
            r12.zzayb = r13     // Catch:{ all -> 0x02dc }
            if (r10 != 0) goto L_0x01f4
            com.google.android.gms.internal.measurement.zzfw[] r12 = r7.zzaxh     // Catch:{ all -> 0x02dc }
            r12 = r12[r11]     // Catch:{ all -> 0x02dc }
            r12.zzayj = r9     // Catch:{ all -> 0x02dc }
        L_0x01f4:
            int r11 = r11 + 1
            goto L_0x019f
        L_0x01f7:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r6 = r6.zzgt()     // Catch:{ all -> 0x02dc }
            r10 = 2
            boolean r6 = r6.isLoggable(r10)     // Catch:{ all -> 0x02dc }
            if (r6 == 0) goto L_0x020c
            com.google.android.gms.measurement.internal.zzft r6 = r17.zzjr()     // Catch:{ all -> 0x02dc }
            java.lang.String r9 = r6.zzb(r7)     // Catch:{ all -> 0x02dc }
        L_0x020c:
            com.google.android.gms.measurement.internal.zzft r6 = r17.zzjr()     // Catch:{ all -> 0x02dc }
            byte[] r14 = r6.zza(r7)     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.String> r6 = com.google.android.gms.measurement.internal.zzai.zzajk     // Catch:{ all -> 0x02dc }
            java.lang.Object r6 = r6.get()     // Catch:{ all -> 0x02dc }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x02dc }
            java.net.URL r13 = new java.net.URL     // Catch:{ MalformedURLException -> 0x02a0 }
            r13.<init>(r6)     // Catch:{ MalformedURLException -> 0x02a0 }
            boolean r10 = r8.isEmpty()     // Catch:{ MalformedURLException -> 0x02a0 }
            if (r10 != 0) goto L_0x0229
            r10 = 1
            goto L_0x022a
        L_0x0229:
            r10 = 0
        L_0x022a:
            com.google.android.gms.common.internal.Preconditions.checkArgument(r10)     // Catch:{ MalformedURLException -> 0x02a0 }
            java.util.List<java.lang.Long> r10 = r1.zzauc     // Catch:{ MalformedURLException -> 0x02a0 }
            if (r10 == 0) goto L_0x0241
            com.google.android.gms.measurement.internal.zzbw r8 = r1.zzada     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzas r8 = r8.zzgt()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjg()     // Catch:{ MalformedURLException -> 0x02a0 }
            java.lang.String r10 = "Set uploading progress before finishing the previous upload"
            r8.zzby(r10)     // Catch:{ MalformedURLException -> 0x02a0 }
            goto L_0x0248
        L_0x0241:
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ MalformedURLException -> 0x02a0 }
            r10.<init>(r8)     // Catch:{ MalformedURLException -> 0x02a0 }
            r1.zzauc = r10     // Catch:{ MalformedURLException -> 0x02a0 }
        L_0x0248:
            com.google.android.gms.measurement.internal.zzbw r8 = r1.zzada     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzbd r8 = r8.zzgu()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzbg r8 = r8.zzand     // Catch:{ MalformedURLException -> 0x02a0 }
            r8.set(r3)     // Catch:{ MalformedURLException -> 0x02a0 }
            java.lang.String r3 = "?"
            com.google.android.gms.internal.measurement.zzfw[] r4 = r7.zzaxh     // Catch:{ MalformedURLException -> 0x02a0 }
            int r4 = r4.length     // Catch:{ MalformedURLException -> 0x02a0 }
            if (r4 <= 0) goto L_0x0260
            com.google.android.gms.internal.measurement.zzfw[] r3 = r7.zzaxh     // Catch:{ MalformedURLException -> 0x02a0 }
            r3 = r3[r2]     // Catch:{ MalformedURLException -> 0x02a0 }
            java.lang.String r3 = r3.zztt     // Catch:{ MalformedURLException -> 0x02a0 }
        L_0x0260:
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjo()     // Catch:{ MalformedURLException -> 0x02a0 }
            java.lang.String r7 = "Uploading data. app, uncompressed size, data"
            int r8 = r14.length     // Catch:{ MalformedURLException -> 0x02a0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ MalformedURLException -> 0x02a0 }
            r4.zzd(r7, r3, r8, r9)     // Catch:{ MalformedURLException -> 0x02a0 }
            r1.zzaty = r0     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzaw r11 = r17.zzlt()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzfp r0 = new com.google.android.gms.measurement.internal.zzfp     // Catch:{ MalformedURLException -> 0x02a0 }
            r0.<init>(r1, r5)     // Catch:{ MalformedURLException -> 0x02a0 }
            r11.zzaf()     // Catch:{ MalformedURLException -> 0x02a0 }
            r11.zzcl()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r13)     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r14)     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzbr r3 = r11.zzgs()     // Catch:{ MalformedURLException -> 0x02a0 }
            com.google.android.gms.measurement.internal.zzba r4 = new com.google.android.gms.measurement.internal.zzba     // Catch:{ MalformedURLException -> 0x02a0 }
            r15 = 0
            r10 = r4
            r12 = r5
            r16 = r0
            r10.<init>(r11, r12, r13, r14, r15, r16)     // Catch:{ MalformedURLException -> 0x02a0 }
            r3.zzd(r4)     // Catch:{ MalformedURLException -> 0x02a0 }
            goto L_0x02d6
        L_0x02a0:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = "Failed to parse upload URL. Not uploading. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r5)     // Catch:{ all -> 0x02dc }
            r0.zze(r3, r4, r6)     // Catch:{ all -> 0x02dc }
            goto L_0x02d6
        L_0x02b4:
            r1.zzaue = r7     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzt r0 = r17.zzjt()     // Catch:{ all -> 0x02dc }
            long r5 = com.google.android.gms.measurement.internal.zzq.zzic()     // Catch:{ all -> 0x02dc }
            long r3 = r3 - r5
            java.lang.String r0 = r0.zzad(r3)     // Catch:{ all -> 0x02dc }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x02dc }
            if (r3 != 0) goto L_0x02d6
            com.google.android.gms.measurement.internal.zzt r3 = r17.zzjt()     // Catch:{ all -> 0x02dc }
            com.google.android.gms.measurement.internal.zzg r0 = r3.zzbm(r0)     // Catch:{ all -> 0x02dc }
            if (r0 == 0) goto L_0x02d6
            r1.zzb(r0)     // Catch:{ all -> 0x02dc }
        L_0x02d6:
            r1.zzatz = r2
            r17.zzmc()
            return
        L_0x02dc:
            r0 = move-exception
            r1.zzatz = r2
            r17.zzmc()
            goto L_0x02e4
        L_0x02e3:
            throw r0
        L_0x02e4:
            goto L_0x02e3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzlz():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:118:0x024e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x024f, code lost:
        r9 = r4;
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0043, code lost:
        r5 = r1;
        r9 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0047, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0048, code lost:
        r8 = null;
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:482:0x0aff, code lost:
        if (r26 != r14) goto L_0x0b01;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:579:?, code lost:
        r9.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0042 A[ExcHandler: all (th java.lang.Throwable), PHI: r4 
      PHI: (r4v134 android.database.Cursor) = (r4v129 android.database.Cursor), (r4v129 android.database.Cursor), (r4v129 android.database.Cursor), (r4v137 android.database.Cursor), (r4v137 android.database.Cursor), (r4v137 android.database.Cursor), (r4v137 android.database.Cursor), (r4v0 android.database.Cursor), (r4v0 android.database.Cursor) binds: [B:48:0x00df, B:54:0x00ec, B:55:?, B:25:0x0082, B:31:0x008f, B:33:0x0093, B:34:?, B:9:0x0033, B:10:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0270 A[SYNTHETIC, Splitter:B:128:0x0270] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0277 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0285 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x03ac A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x03b7 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x03b8 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x0672 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x06f3 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:367:0x083f A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:373:0x0857 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:376:0x0877 A[Catch:{ IOException -> 0x0231, all -> 0x0da2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:489:0x0b1e A[Catch:{ all -> 0x0d81 }] */
    /* JADX WARNING: Removed duplicated region for block: B:493:0x0b6b A[Catch:{ all -> 0x0d81 }] */
    /* JADX WARNING: Removed duplicated region for block: B:570:0x0d85  */
    /* JADX WARNING: Removed duplicated region for block: B:578:0x0d9c A[SYNTHETIC, Splitter:B:578:0x0d9c] */
    /* JADX WARNING: Removed duplicated region for block: B:615:0x0854 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zzd(java.lang.String r44, long r45) {
        /*
            r43 = this;
            r1 = r43
            java.lang.String r2 = "_lte"
            com.google.android.gms.measurement.internal.zzt r3 = r43.zzjt()
            r3.beginTransaction()
            com.google.android.gms.measurement.internal.zzfn$zza r3 = new com.google.android.gms.measurement.internal.zzfn$zza     // Catch:{ all -> 0x0da2 }
            r4 = 0
            r3.<init>(r1, r4)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzt r5 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            long r6 = r1.zzaue     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)     // Catch:{ all -> 0x0da2 }
            r5.zzaf()     // Catch:{ all -> 0x0da2 }
            r5.zzcl()     // Catch:{ all -> 0x0da2 }
            r9 = -1
            r11 = 2
            r12 = 0
            r13 = 1
            android.database.sqlite.SQLiteDatabase r15 = r5.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            boolean r14 = android.text.TextUtils.isEmpty(r4)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            if (r14 == 0) goto L_0x00a1
            int r14 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r14 == 0) goto L_0x004d
            java.lang.String[] r14 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x0047, all -> 0x0042 }
            java.lang.String r16 = java.lang.String.valueOf(r6)     // Catch:{ SQLiteException -> 0x0047, all -> 0x0042 }
            r14[r12] = r16     // Catch:{ SQLiteException -> 0x0047, all -> 0x0042 }
            java.lang.String r16 = java.lang.String.valueOf(r45)     // Catch:{ SQLiteException -> 0x0047, all -> 0x0042 }
            r14[r13] = r16     // Catch:{ SQLiteException -> 0x0047, all -> 0x0042 }
            goto L_0x0055
        L_0x0042:
            r0 = move-exception
            r5 = r1
            r9 = r4
            goto L_0x0256
        L_0x0047:
            r0 = move-exception
            r8 = r4
            r9 = r8
        L_0x004a:
            r4 = r0
            goto L_0x025d
        L_0x004d:
            java.lang.String[] r14 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r16 = java.lang.String.valueOf(r45)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r14[r12] = r16     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
        L_0x0055:
            int r16 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r16 == 0) goto L_0x005c
            java.lang.String r16 = "rowid <= ? and "
            goto L_0x005e
        L_0x005c:
            java.lang.String r16 = ""
        L_0x005e:
            r44 = r16
            int r8 = r44.length()     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            int r8 = r8 + 148
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r4.<init>(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r8 = "select app_id, metadata_fingerprint from raw_events where "
            r4.append(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r8 = r44
            r4.append(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r8 = "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;"
            r4.append(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r4 = r4.toString()     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            android.database.Cursor r4 = r15.rawQuery(r4, r14)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            boolean r8 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0042 }
            if (r8 != 0) goto L_0x008f
            if (r4 == 0) goto L_0x0273
            r4.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x008f:
            java.lang.String r8 = r4.getString(r12)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0042 }
            java.lang.String r14 = r4.getString(r13)     // Catch:{ SQLiteException -> 0x009e, all -> 0x0042 }
            r4.close()     // Catch:{ SQLiteException -> 0x009e, all -> 0x0042 }
            r9 = r4
            r4 = r8
            r8 = r14
            goto L_0x00f6
        L_0x009e:
            r0 = move-exception
            r9 = r4
            goto L_0x004a
        L_0x00a1:
            int r4 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r4 == 0) goto L_0x00b1
            java.lang.String[] r4 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r8 = 0
            r4[r12] = r8     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r4[r13] = r8     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            goto L_0x00b6
        L_0x00b1:
            java.lang.String[] r4 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r8 = 0
            r4[r12] = r8     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
        L_0x00b6:
            int r8 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r8 == 0) goto L_0x00bd
            java.lang.String r8 = " and rowid <= ?"
            goto L_0x00bf
        L_0x00bd:
            java.lang.String r8 = ""
        L_0x00bf:
            int r14 = r8.length()     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            int r14 = r14 + 84
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r9.<init>(r14)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r10 = "select metadata_fingerprint from raw_events where app_id = ?"
            r9.append(r10)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            r9.append(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r8 = " order by rowid limit 1;"
            r9.append(r8)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            java.lang.String r8 = r9.toString()     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            android.database.Cursor r4 = r15.rawQuery(r8, r4)     // Catch:{ SQLiteException -> 0x0259, all -> 0x0253 }
            boolean r8 = r4.moveToFirst()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0042 }
            if (r8 != 0) goto L_0x00ec
            if (r4 == 0) goto L_0x0273
            r4.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x00ec:
            java.lang.String r14 = r4.getString(r12)     // Catch:{ SQLiteException -> 0x024e, all -> 0x0042 }
            r4.close()     // Catch:{ SQLiteException -> 0x024e, all -> 0x0042 }
            r9 = r4
            r8 = r14
            r4 = 0
        L_0x00f6:
            java.lang.String r10 = "raw_events_metadata"
            java.lang.String[] r14 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r16 = "metadata"
            r14[r12] = r16     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r17 = "app_id = ? and metadata_fingerprint = ?"
            java.lang.String[] r13 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x024a }
            r13[r12] = r4     // Catch:{ SQLiteException -> 0x024a }
            r16 = 1
            r13[r16] = r8     // Catch:{ SQLiteException -> 0x024a }
            r19 = 0
            r20 = 0
            java.lang.String r21 = "rowid"
            java.lang.String r22 = "2"
            r16 = r14
            r14 = r15
            r24 = r15
            r15 = r10
            r18 = r13
            android.database.Cursor r9 = r14.query(r15, r16, r17, r18, r19, r20, r21, r22)     // Catch:{ SQLiteException -> 0x024a }
            boolean r10 = r9.moveToFirst()     // Catch:{ SQLiteException -> 0x024a }
            if (r10 != 0) goto L_0x013a
            com.google.android.gms.measurement.internal.zzas r6 = r5.zzgt()     // Catch:{ SQLiteException -> 0x024a }
            com.google.android.gms.measurement.internal.zzau r6 = r6.zzjg()     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r7 = "Raw event metadata record is missing. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ SQLiteException -> 0x024a }
            r6.zzg(r7, r8)     // Catch:{ SQLiteException -> 0x024a }
            if (r9 == 0) goto L_0x0273
            r9.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x013a:
            byte[] r10 = r9.getBlob(r12)     // Catch:{ SQLiteException -> 0x024a }
            int r13 = r10.length     // Catch:{ SQLiteException -> 0x024a }
            com.google.android.gms.internal.measurement.zzxz r10 = com.google.android.gms.internal.measurement.zzxz.zzj(r10, r12, r13)     // Catch:{ SQLiteException -> 0x024a }
            com.google.android.gms.internal.measurement.zzfw r13 = new com.google.android.gms.internal.measurement.zzfw     // Catch:{ SQLiteException -> 0x024a }
            r13.<init>()     // Catch:{ SQLiteException -> 0x024a }
            r13.zza(r10)     // Catch:{ IOException -> 0x0231 }
            boolean r10 = r9.moveToNext()     // Catch:{ SQLiteException -> 0x024a }
            if (r10 == 0) goto L_0x0162
            com.google.android.gms.measurement.internal.zzas r10 = r5.zzgt()     // Catch:{ SQLiteException -> 0x024a }
            com.google.android.gms.measurement.internal.zzau r10 = r10.zzjj()     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r14 = "Get multiple raw event metadata records, expected one. appId"
            java.lang.Object r15 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ SQLiteException -> 0x024a }
            r10.zzg(r14, r15)     // Catch:{ SQLiteException -> 0x024a }
        L_0x0162:
            r9.close()     // Catch:{ SQLiteException -> 0x024a }
            r3.zzb(r13)     // Catch:{ SQLiteException -> 0x024a }
            r13 = -1
            int r10 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r10 == 0) goto L_0x0183
            java.lang.String r10 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?"
            r13 = 3
            java.lang.String[] r14 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x024a }
            r14[r12] = r4     // Catch:{ SQLiteException -> 0x024a }
            r13 = 1
            r14[r13] = r8     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ SQLiteException -> 0x024a }
            r14[r11] = r6     // Catch:{ SQLiteException -> 0x024a }
            r17 = r10
            r18 = r14
            goto L_0x0190
        L_0x0183:
            java.lang.String r6 = "app_id = ? and metadata_fingerprint = ?"
            java.lang.String[] r7 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x024a }
            r7[r12] = r4     // Catch:{ SQLiteException -> 0x024a }
            r10 = 1
            r7[r10] = r8     // Catch:{ SQLiteException -> 0x024a }
            r17 = r6
            r18 = r7
        L_0x0190:
            java.lang.String r15 = "raw_events"
            r6 = 4
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r7 = "rowid"
            r6[r12] = r7     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r7 = "name"
            r8 = 1
            r6[r8] = r7     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r7 = "timestamp"
            r6[r11] = r7     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r7 = "data"
            r8 = 3
            r6[r8] = r7     // Catch:{ SQLiteException -> 0x024a }
            r19 = 0
            r20 = 0
            java.lang.String r21 = "rowid"
            r22 = 0
            r14 = r24
            r16 = r6
            android.database.Cursor r6 = r14.query(r15, r16, r17, r18, r19, r20, r21, r22)     // Catch:{ SQLiteException -> 0x024a }
            boolean r7 = r6.moveToFirst()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            if (r7 != 0) goto L_0x01d5
            com.google.android.gms.measurement.internal.zzas r7 = r5.zzgt()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjj()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            java.lang.String r8 = "Raw event data disappeared while in transaction. appId"
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r7.zzg(r8, r9)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            if (r6 == 0) goto L_0x0273
            r6.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x01d5:
            long r7 = r6.getLong(r12)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r9 = 3
            byte[] r10 = r6.getBlob(r9)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            int r9 = r10.length     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            com.google.android.gms.internal.measurement.zzxz r9 = com.google.android.gms.internal.measurement.zzxz.zzj(r10, r12, r9)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            com.google.android.gms.internal.measurement.zzft r10 = new com.google.android.gms.internal.measurement.zzft     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r10.<init>()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r10.zza(r9)     // Catch:{ IOException -> 0x0209 }
            r9 = 1
            java.lang.String r13 = r6.getString(r9)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r10.name = r13     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            long r13 = r6.getLong(r11)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            java.lang.Long r9 = java.lang.Long.valueOf(r13)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r10.zzaxd = r9     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            boolean r7 = r3.zza(r7, r10)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            if (r7 != 0) goto L_0x021c
            if (r6 == 0) goto L_0x0273
            r6.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x0209:
            r0 = move-exception
            r7 = r0
            com.google.android.gms.measurement.internal.zzas r8 = r5.zzgt()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjg()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            java.lang.String r9 = "Data loss. Failed to merge raw event. appId"
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            r8.zze(r9, r10, r7)     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
        L_0x021c:
            boolean r7 = r6.moveToNext()     // Catch:{ SQLiteException -> 0x022c, all -> 0x0228 }
            if (r7 != 0) goto L_0x01d5
            if (r6 == 0) goto L_0x0273
            r6.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x0228:
            r0 = move-exception
            r5 = r1
            r9 = r6
            goto L_0x0256
        L_0x022c:
            r0 = move-exception
            r8 = r4
            r9 = r6
            goto L_0x004a
        L_0x0231:
            r0 = move-exception
            r6 = r0
            com.google.android.gms.measurement.internal.zzas r7 = r5.zzgt()     // Catch:{ SQLiteException -> 0x024a }
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjg()     // Catch:{ SQLiteException -> 0x024a }
            java.lang.String r8 = "Data loss. Failed to merge raw event metadata. appId"
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ SQLiteException -> 0x024a }
            r7.zze(r8, r10, r6)     // Catch:{ SQLiteException -> 0x024a }
            if (r9 == 0) goto L_0x0273
            r9.close()     // Catch:{ all -> 0x0da2 }
            goto L_0x0273
        L_0x024a:
            r0 = move-exception
            r8 = r4
            goto L_0x004a
        L_0x024e:
            r0 = move-exception
            r9 = r4
            r8 = 0
            goto L_0x004a
        L_0x0253:
            r0 = move-exception
            r5 = r1
            r9 = 0
        L_0x0256:
            r1 = r0
            goto L_0x0d9a
        L_0x0259:
            r0 = move-exception
            r4 = r0
            r8 = 0
            r9 = 0
        L_0x025d:
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0d96 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ all -> 0x0d96 }
            java.lang.String r6 = "Data loss. Error selecting raw event. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r8)     // Catch:{ all -> 0x0d96 }
            r5.zze(r6, r7, r4)     // Catch:{ all -> 0x0d96 }
            if (r9 == 0) goto L_0x0273
            r9.close()     // Catch:{ all -> 0x0da2 }
        L_0x0273:
            java.util.List<com.google.android.gms.internal.measurement.zzft> r4 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x0282
            java.util.List<com.google.android.gms.internal.measurement.zzft> r4 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x0280
            goto L_0x0282
        L_0x0280:
            r4 = 0
            goto L_0x0283
        L_0x0282:
            r4 = 1
        L_0x0283:
            if (r4 != 0) goto L_0x0d85
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.util.List<com.google.android.gms.internal.measurement.zzft> r5 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            int r5 = r5.size()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzft[] r5 = new com.google.android.gms.internal.measurement.zzft[r5]     // Catch:{ all -> 0x0da2 }
            r4.zzaxk = r5     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r5 = r5.zzgv()     // Catch:{ all -> 0x0da2 }
            java.lang.String r6 = r4.zztt     // Catch:{ all -> 0x0da2 }
            boolean r5 = r5.zzau(r6)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r6 = r6.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r8 = com.google.android.gms.measurement.internal.zzai.zzalc     // Catch:{ all -> 0x0da2 }
            boolean r6 = r6.zze(r7, r8)     // Catch:{ all -> 0x0da2 }
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r13 = 0
            r14 = 0
        L_0x02b4:
            java.util.List<com.google.android.gms.internal.measurement.zzft> r12 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            int r12 = r12.size()     // Catch:{ all -> 0x0da2 }
            java.lang.String r11 = "_et"
            r18 = r13
            java.lang.String r13 = "_e"
            r19 = 1
            if (r9 >= r12) goto L_0x0754
            java.util.List<com.google.android.gms.internal.measurement.zzft> r12 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            java.lang.Object r12 = r12.get(r9)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzft r12 = (com.google.android.gms.internal.measurement.zzft) r12     // Catch:{ all -> 0x0da2 }
            r21 = r2
            com.google.android.gms.measurement.internal.zzbq r2 = r43.zzls()     // Catch:{ all -> 0x0da2 }
            r22 = r9
            com.google.android.gms.internal.measurement.zzfw r9 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = r9.zztt     // Catch:{ all -> 0x0da2 }
            r24 = r10
            java.lang.String r10 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zzo(r9, r10)     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = "_err"
            if (r2 == 0) goto L_0x0359
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = "Dropping blacklisted raw event. appId"
            com.google.android.gms.internal.measurement.zzfw r11 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r11 = r11.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.Object r11 = com.google.android.gms.measurement.internal.zzas.zzbw(r11)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r13 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzaq r13 = r13.zzgq()     // Catch:{ all -> 0x0da2 }
            r25 = r4
            java.lang.String r4 = r12.name     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = r13.zzbt(r4)     // Catch:{ all -> 0x0da2 }
            r2.zze(r10, r11, r4)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbq r2 = r43.zzls()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = r4.zztt     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zzcl(r4)     // Catch:{ all -> 0x0da2 }
            if (r2 != 0) goto L_0x0328
            com.google.android.gms.measurement.internal.zzbq r2 = r43.zzls()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = r4.zztt     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zzcm(r4)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x0326
            goto L_0x0328
        L_0x0326:
            r2 = 0
            goto L_0x0329
        L_0x0328:
            r2 = 1
        L_0x0329:
            if (r2 != 0) goto L_0x034c
            java.lang.String r2 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r2 = r9.equals(r2)     // Catch:{ all -> 0x0da2 }
            if (r2 != 0) goto L_0x034c
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzfx r26 = r2.zzgr()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r2 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r2 = r2.zztt     // Catch:{ all -> 0x0da2 }
            r28 = 11
            java.lang.String r29 = "_ev"
            java.lang.String r4 = r12.name     // Catch:{ all -> 0x0da2 }
            r31 = 0
            r27 = r2
            r30 = r4
            r26.zza(r27, r28, r29, r30, r31)     // Catch:{ all -> 0x0da2 }
        L_0x034c:
            r29 = r5
            r31 = r6
            r13 = r18
            r5 = r24
            r4 = r25
            r10 = 3
            goto L_0x0748
        L_0x0359:
            r25 = r4
            com.google.android.gms.measurement.internal.zzbq r2 = r43.zzls()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = r4.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zzp(r4, r10)     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = "_c"
            if (r2 != 0) goto L_0x03c0
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = r12.name     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)     // Catch:{ all -> 0x0da2 }
            r27 = r14
            int r14 = r10.hashCode()     // Catch:{ all -> 0x0da2 }
            r15 = 94660(0x171c4, float:1.32647E-40)
            if (r14 == r15) goto L_0x039f
            r15 = 95025(0x17331, float:1.33158E-40)
            if (r14 == r15) goto L_0x0395
            r15 = 95027(0x17333, float:1.33161E-40)
            if (r14 == r15) goto L_0x038b
            goto L_0x03a9
        L_0x038b:
            java.lang.String r14 = "_ui"
            boolean r10 = r10.equals(r14)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x03a9
            r10 = 1
            goto L_0x03aa
        L_0x0395:
            java.lang.String r14 = "_ug"
            boolean r10 = r10.equals(r14)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x03a9
            r10 = 2
            goto L_0x03aa
        L_0x039f:
            java.lang.String r14 = "_in"
            boolean r10 = r10.equals(r14)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x03a9
            r10 = 0
            goto L_0x03aa
        L_0x03a9:
            r10 = -1
        L_0x03aa:
            if (r10 == 0) goto L_0x03b4
            r14 = 1
            if (r10 == r14) goto L_0x03b4
            r14 = 2
            if (r10 == r14) goto L_0x03b4
            r10 = 0
            goto L_0x03b5
        L_0x03b4:
            r10 = 1
        L_0x03b5:
            if (r10 == 0) goto L_0x03b8
            goto L_0x03c2
        L_0x03b8:
            r29 = r5
            r31 = r6
            r30 = r11
            goto L_0x05a3
        L_0x03c0:
            r27 = r14
        L_0x03c2:
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            if (r10 != 0) goto L_0x03cb
            r10 = 0
            com.google.android.gms.internal.measurement.zzfu[] r14 = new com.google.android.gms.internal.measurement.zzfu[r10]     // Catch:{ all -> 0x0da2 }
            r12.zzaxc = r14     // Catch:{ all -> 0x0da2 }
        L_0x03cb:
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r14 = r10.length     // Catch:{ all -> 0x0da2 }
            r31 = r6
            r15 = 0
            r29 = 0
            r30 = 0
        L_0x03d5:
            java.lang.String r6 = "_r"
            if (r15 >= r14) goto L_0x0407
            r32 = r14
            r14 = r10[r15]     // Catch:{ all -> 0x0da2 }
            r33 = r10
            java.lang.String r10 = r14.name     // Catch:{ all -> 0x0da2 }
            boolean r10 = r4.equals(r10)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x03f0
            java.lang.Long r6 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0da2 }
            r14.zzaxg = r6     // Catch:{ all -> 0x0da2 }
            r29 = 1
            goto L_0x0400
        L_0x03f0:
            java.lang.String r10 = r14.name     // Catch:{ all -> 0x0da2 }
            boolean r6 = r6.equals(r10)     // Catch:{ all -> 0x0da2 }
            if (r6 == 0) goto L_0x0400
            java.lang.Long r6 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0da2 }
            r14.zzaxg = r6     // Catch:{ all -> 0x0da2 }
            r30 = 1
        L_0x0400:
            int r15 = r15 + 1
            r14 = r32
            r10 = r33
            goto L_0x03d5
        L_0x0407:
            if (r29 != 0) goto L_0x044a
            if (r2 == 0) goto L_0x044a
            com.google.android.gms.measurement.internal.zzbw r10 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r10 = r10.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r10 = r10.zzjo()     // Catch:{ all -> 0x0da2 }
            java.lang.String r14 = "Marking event as conversion"
            com.google.android.gms.measurement.internal.zzbw r15 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzaq r15 = r15.zzgq()     // Catch:{ all -> 0x0da2 }
            r29 = r5
            java.lang.String r5 = r12.name     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r15.zzbt(r5)     // Catch:{ all -> 0x0da2 }
            r10.zzg(r14, r5)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r10 = r10.length     // Catch:{ all -> 0x0da2 }
            r14 = 1
            int r10 = r10 + r14
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r10)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = (com.google.android.gms.internal.measurement.zzfu[]) r5     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu r10 = new com.google.android.gms.internal.measurement.zzfu     // Catch:{ all -> 0x0da2 }
            r10.<init>()     // Catch:{ all -> 0x0da2 }
            r10.name = r4     // Catch:{ all -> 0x0da2 }
            java.lang.Long r14 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0da2 }
            r10.zzaxg = r14     // Catch:{ all -> 0x0da2 }
            int r14 = r5.length     // Catch:{ all -> 0x0da2 }
            r15 = 1
            int r14 = r14 - r15
            r5[r14] = r10     // Catch:{ all -> 0x0da2 }
            r12.zzaxc = r5     // Catch:{ all -> 0x0da2 }
            goto L_0x044c
        L_0x044a:
            r29 = r5
        L_0x044c:
            if (r30 != 0) goto L_0x048a
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjo()     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = "Marking event as real-time"
            com.google.android.gms.measurement.internal.zzbw r14 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzaq r14 = r14.zzgq()     // Catch:{ all -> 0x0da2 }
            java.lang.String r15 = r12.name     // Catch:{ all -> 0x0da2 }
            java.lang.String r14 = r14.zzbt(r15)     // Catch:{ all -> 0x0da2 }
            r5.zzg(r10, r14)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r10 = r10.length     // Catch:{ all -> 0x0da2 }
            r14 = 1
            int r10 = r10 + r14
            java.lang.Object[] r5 = java.util.Arrays.copyOf(r5, r10)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = (com.google.android.gms.internal.measurement.zzfu[]) r5     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu r10 = new com.google.android.gms.internal.measurement.zzfu     // Catch:{ all -> 0x0da2 }
            r10.<init>()     // Catch:{ all -> 0x0da2 }
            r10.name = r6     // Catch:{ all -> 0x0da2 }
            java.lang.Long r14 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0da2 }
            r10.zzaxg = r14     // Catch:{ all -> 0x0da2 }
            int r14 = r5.length     // Catch:{ all -> 0x0da2 }
            r15 = 1
            int r14 = r14 - r15
            r5[r14] = r10     // Catch:{ all -> 0x0da2 }
            r12.zzaxc = r5     // Catch:{ all -> 0x0da2 }
        L_0x048a:
            com.google.android.gms.measurement.internal.zzt r32 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            long r33 = r43.zzly()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r5 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0da2 }
            r36 = 0
            r37 = 0
            r38 = 0
            r39 = 0
            r40 = 1
            r35 = r5
            com.google.android.gms.measurement.internal.zzu r5 = r32.zza(r33, r35, r36, r37, r38, r39, r40)     // Catch:{ all -> 0x0da2 }
            long r14 = r5.zzahl     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r5 = r5.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r10 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = r10.zztt     // Catch:{ all -> 0x0da2 }
            int r5 = r5.zzaq(r10)     // Catch:{ all -> 0x0da2 }
            r30 = r11
            long r10 = (long) r5     // Catch:{ all -> 0x0da2 }
            int r5 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r5 <= 0) goto L_0x04f0
            r5 = 0
        L_0x04be:
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r10 = r10.length     // Catch:{ all -> 0x0da2 }
            if (r5 >= r10) goto L_0x04f2
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            r10 = r10[r5]     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = r10.name     // Catch:{ all -> 0x0da2 }
            boolean r10 = r6.equals(r10)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x04ed
            com.google.android.gms.internal.measurement.zzfu[] r6 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r6 = r6.length     // Catch:{ all -> 0x0da2 }
            r10 = 1
            int r6 = r6 - r10
            com.google.android.gms.internal.measurement.zzfu[] r6 = new com.google.android.gms.internal.measurement.zzfu[r6]     // Catch:{ all -> 0x0da2 }
            if (r5 <= 0) goto L_0x04de
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            r11 = 0
            java.lang.System.arraycopy(r10, r11, r6, r11, r5)     // Catch:{ all -> 0x0da2 }
        L_0x04de:
            int r10 = r6.length     // Catch:{ all -> 0x0da2 }
            if (r5 >= r10) goto L_0x04ea
            com.google.android.gms.internal.measurement.zzfu[] r10 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r11 = r5 + 1
            int r14 = r6.length     // Catch:{ all -> 0x0da2 }
            int r14 = r14 - r5
            java.lang.System.arraycopy(r10, r11, r6, r5, r14)     // Catch:{ all -> 0x0da2 }
        L_0x04ea:
            r12.zzaxc = r6     // Catch:{ all -> 0x0da2 }
            goto L_0x04f2
        L_0x04ed:
            int r5 = r5 + 1
            goto L_0x04be
        L_0x04f0:
            r18 = 1
        L_0x04f2:
            java.lang.String r5 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r5 = com.google.android.gms.measurement.internal.zzfx.zzct(r5)     // Catch:{ all -> 0x0da2 }
            if (r5 == 0) goto L_0x05a3
            if (r2 == 0) goto L_0x05a3
            com.google.android.gms.measurement.internal.zzt r32 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            long r33 = r43.zzly()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r5 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0da2 }
            r36 = 0
            r37 = 0
            r38 = 1
            r39 = 0
            r40 = 0
            r35 = r5
            com.google.android.gms.measurement.internal.zzu r5 = r32.zza(r33, r35, r36, r37, r38, r39, r40)     // Catch:{ all -> 0x0da2 }
            long r5 = r5.zzahj     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r10 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r10 = r10.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r11 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r11 = r11.zztt     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r14 = com.google.android.gms.measurement.internal.zzai.zzajh     // Catch:{ all -> 0x0da2 }
            int r10 = r10.zzb(r11, r14)     // Catch:{ all -> 0x0da2 }
            long r10 = (long) r10     // Catch:{ all -> 0x0da2 }
            int r14 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r14 <= 0) goto L_0x05a3
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r6 = "Too many conversions. Not logging as conversion. appId"
            com.google.android.gms.internal.measurement.zzfw r10 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r10 = r10.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.Object r10 = com.google.android.gms.measurement.internal.zzas.zzbw(r10)     // Catch:{ all -> 0x0da2 }
            r5.zzg(r6, r10)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r6 = r5.length     // Catch:{ all -> 0x0da2 }
            r10 = 0
            r11 = 0
            r14 = 0
        L_0x054c:
            if (r10 >= r6) goto L_0x056a
            r15 = r5[r10]     // Catch:{ all -> 0x0da2 }
            r19 = r5
            java.lang.String r5 = r15.name     // Catch:{ all -> 0x0da2 }
            boolean r5 = r4.equals(r5)     // Catch:{ all -> 0x0da2 }
            if (r5 == 0) goto L_0x055c
            r14 = r15
            goto L_0x0565
        L_0x055c:
            java.lang.String r5 = r15.name     // Catch:{ all -> 0x0da2 }
            boolean r5 = r9.equals(r5)     // Catch:{ all -> 0x0da2 }
            if (r5 == 0) goto L_0x0565
            r11 = 1
        L_0x0565:
            int r10 = r10 + 1
            r5 = r19
            goto L_0x054c
        L_0x056a:
            if (r11 == 0) goto L_0x057f
            if (r14 == 0) goto L_0x057f
            com.google.android.gms.internal.measurement.zzfu[] r5 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            r6 = 1
            com.google.android.gms.internal.measurement.zzfu[] r9 = new com.google.android.gms.internal.measurement.zzfu[r6]     // Catch:{ all -> 0x0da2 }
            r6 = 0
            r9[r6] = r14     // Catch:{ all -> 0x0da2 }
            java.lang.Object[] r5 = com.google.android.gms.common.util.ArrayUtils.removeAll(r5, r9)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r5 = (com.google.android.gms.internal.measurement.zzfu[]) r5     // Catch:{ all -> 0x0da2 }
            r12.zzaxc = r5     // Catch:{ all -> 0x0da2 }
            goto L_0x05a3
        L_0x057f:
            if (r14 == 0) goto L_0x058c
            r14.name = r9     // Catch:{ all -> 0x0da2 }
            r5 = 10
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0da2 }
            r14.zzaxg = r5     // Catch:{ all -> 0x0da2 }
            goto L_0x05a3
        L_0x058c:
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ all -> 0x0da2 }
            java.lang.String r6 = "Did not find conversion parameter. appId"
            com.google.android.gms.internal.measurement.zzfw r9 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = r9.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.Object r9 = com.google.android.gms.measurement.internal.zzas.zzbw(r9)     // Catch:{ all -> 0x0da2 }
            r5.zzg(r6, r9)     // Catch:{ all -> 0x0da2 }
        L_0x05a3:
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r5 = r5.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r6 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r6 = r6.zztt     // Catch:{ all -> 0x0da2 }
            boolean r5 = r5.zzbd(r6)     // Catch:{ all -> 0x0da2 }
            if (r5 == 0) goto L_0x065f
            if (r2 == 0) goto L_0x065f
            com.google.android.gms.internal.measurement.zzfu[] r2 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            r5 = 0
            r6 = -1
            r9 = -1
        L_0x05ba:
            int r10 = r2.length     // Catch:{ all -> 0x0da2 }
            if (r5 >= r10) goto L_0x05db
            java.lang.String r10 = "value"
            r11 = r2[r5]     // Catch:{ all -> 0x0da2 }
            java.lang.String r11 = r11.name     // Catch:{ all -> 0x0da2 }
            boolean r10 = r10.equals(r11)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x05cb
            r6 = r5
            goto L_0x05d8
        L_0x05cb:
            java.lang.String r10 = "currency"
            r11 = r2[r5]     // Catch:{ all -> 0x0da2 }
            java.lang.String r11 = r11.name     // Catch:{ all -> 0x0da2 }
            boolean r10 = r10.equals(r11)     // Catch:{ all -> 0x0da2 }
            if (r10 == 0) goto L_0x05d8
            r9 = r5
        L_0x05d8:
            int r5 = r5 + 1
            goto L_0x05ba
        L_0x05db:
            r5 = -1
            if (r6 == r5) goto L_0x0609
            r5 = r2[r6]     // Catch:{ all -> 0x0da2 }
            java.lang.Long r5 = r5.zzaxg     // Catch:{ all -> 0x0da2 }
            if (r5 != 0) goto L_0x060b
            r5 = r2[r6]     // Catch:{ all -> 0x0da2 }
            java.lang.Double r5 = r5.zzaup     // Catch:{ all -> 0x0da2 }
            if (r5 != 0) goto L_0x060b
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjl()     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = "Value must be specified with a numeric type."
            r5.zzby(r9)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r6)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r4)     // Catch:{ all -> 0x0da2 }
            r4 = 18
            java.lang.String r5 = "value"
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r4, r5)     // Catch:{ all -> 0x0da2 }
        L_0x0609:
            r10 = 3
            goto L_0x065c
        L_0x060b:
            r5 = -1
            if (r9 != r5) goto L_0x0611
            r5 = 1
            r10 = 3
            goto L_0x063b
        L_0x0611:
            r5 = r2[r9]     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zzamn     // Catch:{ all -> 0x0da2 }
            if (r5 == 0) goto L_0x0639
            int r9 = r5.length()     // Catch:{ all -> 0x0da2 }
            r10 = 3
            if (r9 == r10) goto L_0x061f
            goto L_0x063a
        L_0x061f:
            r9 = 0
        L_0x0620:
            int r11 = r5.length()     // Catch:{ all -> 0x0da2 }
            if (r9 >= r11) goto L_0x0637
            int r11 = r5.codePointAt(r9)     // Catch:{ all -> 0x0da2 }
            boolean r14 = java.lang.Character.isLetter(r11)     // Catch:{ all -> 0x0da2 }
            if (r14 != 0) goto L_0x0631
            goto L_0x063a
        L_0x0631:
            int r11 = java.lang.Character.charCount(r11)     // Catch:{ all -> 0x0da2 }
            int r9 = r9 + r11
            goto L_0x0620
        L_0x0637:
            r5 = 0
            goto L_0x063b
        L_0x0639:
            r10 = 3
        L_0x063a:
            r5 = 1
        L_0x063b:
            if (r5 == 0) goto L_0x065c
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjl()     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = "Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter."
            r5.zzby(r9)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r6)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r4)     // Catch:{ all -> 0x0da2 }
            r4 = 19
            java.lang.String r5 = "currency"
            com.google.android.gms.internal.measurement.zzfu[] r2 = zza(r2, r4, r5)     // Catch:{ all -> 0x0da2 }
        L_0x065c:
            r12.zzaxc = r2     // Catch:{ all -> 0x0da2 }
            goto L_0x0660
        L_0x065f:
            r10 = 3
        L_0x0660:
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r2 = r2.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = r4.zztt     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r5 = com.google.android.gms.measurement.internal.zzai.zzalb     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zze(r4, r5)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x06aa
            java.lang.String r2 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r2 = r13.equals(r2)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x06ad
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            java.lang.String r2 = "_fr"
            com.google.android.gms.internal.measurement.zzfu r2 = com.google.android.gms.measurement.internal.zzft.zza(r12, r2)     // Catch:{ all -> 0x0da2 }
            if (r2 != 0) goto L_0x06aa
            if (r8 == 0) goto L_0x06a9
            java.lang.Long r2 = r8.zzaxd     // Catch:{ all -> 0x0da2 }
            long r4 = r2.longValue()     // Catch:{ all -> 0x0da2 }
            java.lang.Long r2 = r12.zzaxd     // Catch:{ all -> 0x0da2 }
            long r6 = r2.longValue()     // Catch:{ all -> 0x0da2 }
            long r4 = r4 - r6
            long r4 = java.lang.Math.abs(r4)     // Catch:{ all -> 0x0da2 }
            r6 = 1000(0x3e8, double:4.94E-321)
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 > 0) goto L_0x06a9
            boolean r2 = r1.zza(r12, r8)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x06a9
            r2 = r30
        L_0x06a6:
            r7 = 0
            r8 = 0
            goto L_0x06e3
        L_0x06a9:
            r7 = r12
        L_0x06aa:
            r2 = r30
            goto L_0x06e3
        L_0x06ad:
            java.lang.String r2 = "_vs"
            java.lang.String r4 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x06aa
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            r2 = r30
            com.google.android.gms.internal.measurement.zzfu r4 = com.google.android.gms.measurement.internal.zzft.zza(r12, r2)     // Catch:{ all -> 0x0da2 }
            if (r4 != 0) goto L_0x06e3
            if (r7 == 0) goto L_0x06e2
            java.lang.Long r4 = r7.zzaxd     // Catch:{ all -> 0x0da2 }
            long r4 = r4.longValue()     // Catch:{ all -> 0x0da2 }
            java.lang.Long r6 = r12.zzaxd     // Catch:{ all -> 0x0da2 }
            long r8 = r6.longValue()     // Catch:{ all -> 0x0da2 }
            long r4 = r4 - r8
            long r4 = java.lang.Math.abs(r4)     // Catch:{ all -> 0x0da2 }
            r8 = 1000(0x3e8, double:4.94E-321)
            int r6 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r6 > 0) goto L_0x06e2
            boolean r4 = r1.zza(r7, r12)     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x06e2
            goto L_0x06a6
        L_0x06e2:
            r8 = r12
        L_0x06e3:
            if (r29 == 0) goto L_0x073c
            if (r31 != 0) goto L_0x073c
            java.lang.String r4 = r12.name     // Catch:{ all -> 0x0da2 }
            boolean r4 = r13.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x073c
            com.google.android.gms.internal.measurement.zzfu[] r4 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x0725
            com.google.android.gms.internal.measurement.zzfu[] r4 = r12.zzaxc     // Catch:{ all -> 0x0da2 }
            int r4 = r4.length     // Catch:{ all -> 0x0da2 }
            if (r4 != 0) goto L_0x06f9
            goto L_0x0725
        L_0x06f9:
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzft.zzb(r12, r2)     // Catch:{ all -> 0x0da2 }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ all -> 0x0da2 }
            if (r2 != 0) goto L_0x071c
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = "Engagement event does not include duration. appId"
            com.google.android.gms.internal.measurement.zzfw r5 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzas.zzbw(r5)     // Catch:{ all -> 0x0da2 }
            r2.zzg(r4, r5)     // Catch:{ all -> 0x0da2 }
            goto L_0x073c
        L_0x071c:
            long r4 = r2.longValue()     // Catch:{ all -> 0x0da2 }
            long r14 = r27 + r4
            r27 = r14
            goto L_0x073c
        L_0x0725:
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r2 = r2.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r4 = "Engagement event does not contain any parameters. appId"
            com.google.android.gms.internal.measurement.zzfw r5 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzas.zzbw(r5)     // Catch:{ all -> 0x0da2 }
            r2.zzg(r4, r5)     // Catch:{ all -> 0x0da2 }
        L_0x073c:
            r4 = r25
            com.google.android.gms.internal.measurement.zzft[] r2 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            int r5 = r24 + 1
            r2[r24] = r12     // Catch:{ all -> 0x0da2 }
            r13 = r18
            r14 = r27
        L_0x0748:
            int r9 = r22 + 1
            r10 = r5
            r2 = r21
            r5 = r29
            r6 = r31
            r11 = 2
            goto L_0x02b4
        L_0x0754:
            r21 = r2
            r29 = r5
            r31 = r6
            r24 = r10
            r2 = r11
            r27 = r14
            if (r31 == 0) goto L_0x07b2
            r14 = r27
            r5 = 0
        L_0x0764:
            if (r5 >= r10) goto L_0x07b0
            com.google.android.gms.internal.measurement.zzft[] r6 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            r6 = r6[r5]     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = r6.name     // Catch:{ all -> 0x0da2 }
            boolean r7 = r13.equals(r7)     // Catch:{ all -> 0x0da2 }
            if (r7 == 0) goto L_0x078f
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = "_fr"
            com.google.android.gms.internal.measurement.zzfu r7 = com.google.android.gms.measurement.internal.zzft.zza(r6, r7)     // Catch:{ all -> 0x0da2 }
            if (r7 == 0) goto L_0x078f
            com.google.android.gms.internal.measurement.zzft[] r6 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            int r7 = r5 + 1
            com.google.android.gms.internal.measurement.zzft[] r8 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            int r9 = r10 - r5
            r11 = 1
            int r9 = r9 - r11
            java.lang.System.arraycopy(r6, r7, r8, r5, r9)     // Catch:{ all -> 0x0da2 }
            int r10 = r10 + -1
            int r5 = r5 + -1
            goto L_0x07ad
        L_0x078f:
            if (r29 == 0) goto L_0x07ad
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu r6 = com.google.android.gms.measurement.internal.zzft.zza(r6, r2)     // Catch:{ all -> 0x0da2 }
            if (r6 == 0) goto L_0x07ad
            java.lang.Long r6 = r6.zzaxg     // Catch:{ all -> 0x0da2 }
            if (r6 == 0) goto L_0x07ad
            long r7 = r6.longValue()     // Catch:{ all -> 0x0da2 }
            r11 = 0
            int r9 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r9 <= 0) goto L_0x07ad
            long r6 = r6.longValue()     // Catch:{ all -> 0x0da2 }
            long r14 = r14 + r6
        L_0x07ad:
            r6 = 1
            int r5 = r5 + r6
            goto L_0x0764
        L_0x07b0:
            r27 = r14
        L_0x07b2:
            java.util.List<com.google.android.gms.internal.measurement.zzft> r2 = r3.zzauk     // Catch:{ all -> 0x0da2 }
            int r2 = r2.size()     // Catch:{ all -> 0x0da2 }
            if (r10 >= r2) goto L_0x07c4
            com.google.android.gms.internal.measurement.zzft[] r2 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r2, r10)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzft[] r2 = (com.google.android.gms.internal.measurement.zzft[]) r2     // Catch:{ all -> 0x0da2 }
            r4.zzaxk = r2     // Catch:{ all -> 0x0da2 }
        L_0x07c4:
            if (r29 == 0) goto L_0x088f
            com.google.android.gms.measurement.internal.zzt r2 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r4.zztt     // Catch:{ all -> 0x0da2 }
            r6 = r21
            com.google.android.gms.measurement.internal.zzfw r2 = r2.zzi(r5, r6)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x07ff
            java.lang.Object r5 = r2.value     // Catch:{ all -> 0x0da2 }
            if (r5 != 0) goto L_0x07d9
            goto L_0x07ff
        L_0x07d9:
            com.google.android.gms.measurement.internal.zzfw r5 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r4.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = "auto"
            java.lang.String r10 = "_lte"
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.util.Clock r7 = r7.zzbx()     // Catch:{ all -> 0x0da2 }
            long r11 = r7.currentTimeMillis()     // Catch:{ all -> 0x0da2 }
            java.lang.Object r2 = r2.value     // Catch:{ all -> 0x0da2 }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ all -> 0x0da2 }
            long r13 = r2.longValue()     // Catch:{ all -> 0x0da2 }
            long r13 = r13 + r27
            java.lang.Long r13 = java.lang.Long.valueOf(r13)     // Catch:{ all -> 0x0da2 }
            r7 = r5
            r7.<init>(r8, r9, r10, r11, r13)     // Catch:{ all -> 0x0da2 }
            r2 = r5
            goto L_0x081c
        L_0x07ff:
            com.google.android.gms.measurement.internal.zzfw r2 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r4.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r31 = "auto"
            java.lang.String r32 = "_lte"
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.util.Clock r7 = r7.zzbx()     // Catch:{ all -> 0x0da2 }
            long r33 = r7.currentTimeMillis()     // Catch:{ all -> 0x0da2 }
            java.lang.Long r35 = java.lang.Long.valueOf(r27)     // Catch:{ all -> 0x0da2 }
            r29 = r2
            r30 = r5
            r29.<init>(r30, r31, r32, r33, r35)     // Catch:{ all -> 0x0da2 }
        L_0x081c:
            com.google.android.gms.internal.measurement.zzfz r5 = new com.google.android.gms.internal.measurement.zzfz     // Catch:{ all -> 0x0da2 }
            r5.<init>()     // Catch:{ all -> 0x0da2 }
            r5.name = r6     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.util.Clock r7 = r7.zzbx()     // Catch:{ all -> 0x0da2 }
            long r7 = r7.currentTimeMillis()     // Catch:{ all -> 0x0da2 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0da2 }
            r5.zzayw = r7     // Catch:{ all -> 0x0da2 }
            java.lang.Object r7 = r2.value     // Catch:{ all -> 0x0da2 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x0da2 }
            r5.zzaxg = r7     // Catch:{ all -> 0x0da2 }
            r7 = 0
        L_0x083a:
            com.google.android.gms.internal.measurement.zzfz[] r8 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            int r8 = r8.length     // Catch:{ all -> 0x0da2 }
            if (r7 >= r8) goto L_0x0854
            com.google.android.gms.internal.measurement.zzfz[] r8 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            r8 = r8[r7]     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r8.name     // Catch:{ all -> 0x0da2 }
            boolean r8 = r6.equals(r8)     // Catch:{ all -> 0x0da2 }
            if (r8 == 0) goto L_0x0851
            com.google.android.gms.internal.measurement.zzfz[] r6 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            r6[r7] = r5     // Catch:{ all -> 0x0da2 }
            r6 = 1
            goto L_0x0855
        L_0x0851:
            int r7 = r7 + 1
            goto L_0x083a
        L_0x0854:
            r6 = 0
        L_0x0855:
            if (r6 != 0) goto L_0x0871
            com.google.android.gms.internal.measurement.zzfz[] r6 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfz[] r7 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            int r7 = r7.length     // Catch:{ all -> 0x0da2 }
            r8 = 1
            int r7 = r7 + r8
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r6, r7)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfz[] r6 = (com.google.android.gms.internal.measurement.zzfz[]) r6     // Catch:{ all -> 0x0da2 }
            r4.zzaxl = r6     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfz[] r6 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfz[] r7 = r7.zzaxl     // Catch:{ all -> 0x0da2 }
            int r7 = r7.length     // Catch:{ all -> 0x0da2 }
            r8 = 1
            int r7 = r7 - r8
            r6[r7] = r5     // Catch:{ all -> 0x0da2 }
        L_0x0871:
            r5 = 0
            int r7 = (r27 > r5 ? 1 : (r27 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x088f
            com.google.android.gms.measurement.internal.zzt r5 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            r5.zza(r2)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r5 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r5 = r5.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjn()     // Catch:{ all -> 0x0da2 }
            java.lang.String r6 = "Updated lifetime engagement user property with value. Value"
            java.lang.Object r2 = r2.value     // Catch:{ all -> 0x0da2 }
            r5.zzg(r6, r2)     // Catch:{ all -> 0x0da2 }
        L_0x088f:
            java.lang.String r2 = r4.zztt     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfz[] r5 = r4.zzaxl     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzft[] r6 = r4.zzaxk     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r2)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzm r7 = r43.zzjs()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfr[] r2 = r7.zza(r2, r6, r5)     // Catch:{ all -> 0x0da2 }
            r4.zzayc = r2     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzbw r2 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzq r2 = r2.zzgv()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r5 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r5 = r5.zztt     // Catch:{ all -> 0x0da2 }
            boolean r2 = r2.zzat(r5)     // Catch:{ all -> 0x0da2 }
            if (r2 == 0) goto L_0x0bbc
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ all -> 0x0d81 }
            r2.<init>()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzft[] r5 = r4.zzaxk     // Catch:{ all -> 0x0d81 }
            int r5 = r5.length     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzft[] r5 = new com.google.android.gms.internal.measurement.zzft[r5]     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzfx r6 = r6.zzgr()     // Catch:{ all -> 0x0d81 }
            java.security.SecureRandom r6 = r6.zzmk()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzft[] r7 = r4.zzaxk     // Catch:{ all -> 0x0d81 }
            int r8 = r7.length     // Catch:{ all -> 0x0d81 }
            r9 = 0
            r10 = 0
        L_0x08cb:
            if (r9 >= r8) goto L_0x0b8b
            r11 = r7[r9]     // Catch:{ all -> 0x0d81 }
            java.lang.String r12 = r11.name     // Catch:{ all -> 0x0d81 }
            java.lang.String r13 = "_ep"
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x0d81 }
            if (r12 == 0) goto L_0x0952
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            java.lang.String r12 = "_en"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzft.zzb(r11, r12)     // Catch:{ all -> 0x0da2 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ all -> 0x0da2 }
            java.lang.Object r13 = r2.get(r12)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzac r13 = (com.google.android.gms.measurement.internal.zzac) r13     // Catch:{ all -> 0x0da2 }
            if (r13 != 0) goto L_0x08fb
            com.google.android.gms.measurement.internal.zzt r13 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r14 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r14 = r14.zztt     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzac r13 = r13.zzg(r14, r12)     // Catch:{ all -> 0x0da2 }
            r2.put(r12, r13)     // Catch:{ all -> 0x0da2 }
        L_0x08fb:
            java.lang.Long r12 = r13.zzaia     // Catch:{ all -> 0x0da2 }
            if (r12 != 0) goto L_0x0947
            java.lang.Long r12 = r13.zzaib     // Catch:{ all -> 0x0da2 }
            long r14 = r12.longValue()     // Catch:{ all -> 0x0da2 }
            int r12 = (r14 > r19 ? 1 : (r14 == r19 ? 0 : -1))
            if (r12 <= 0) goto L_0x0918
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r12 = r11.zzaxc     // Catch:{ all -> 0x0da2 }
            java.lang.String r14 = "_sr"
            java.lang.Long r15 = r13.zzaib     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r12 = com.google.android.gms.measurement.internal.zzft.zza(r12, r14, r15)     // Catch:{ all -> 0x0da2 }
            r11.zzaxc = r12     // Catch:{ all -> 0x0da2 }
        L_0x0918:
            java.lang.Boolean r12 = r13.zzaic     // Catch:{ all -> 0x0da2 }
            if (r12 == 0) goto L_0x0935
            java.lang.Boolean r12 = r13.zzaic     // Catch:{ all -> 0x0da2 }
            boolean r12 = r12.booleanValue()     // Catch:{ all -> 0x0da2 }
            if (r12 == 0) goto L_0x0935
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r12 = r11.zzaxc     // Catch:{ all -> 0x0da2 }
            java.lang.String r13 = "_efs"
            java.lang.Long r14 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r12 = com.google.android.gms.measurement.internal.zzft.zza(r12, r13, r14)     // Catch:{ all -> 0x0da2 }
            r11.zzaxc = r12     // Catch:{ all -> 0x0da2 }
        L_0x0935:
            int r12 = r10 + 1
            r5[r10] = r11     // Catch:{ all -> 0x0da2 }
            r25 = r4
            r24 = r6
            r21 = r7
            r22 = r8
            r23 = r9
            r10 = r12
        L_0x0944:
            r6 = r2
            goto L_0x0b7c
        L_0x0947:
            r25 = r4
            r24 = r6
            r21 = r7
            r22 = r8
            r23 = r9
            goto L_0x0944
        L_0x0952:
            com.google.android.gms.measurement.internal.zzbq r12 = r43.zzls()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfw r13 = r3.zzaui     // Catch:{ all -> 0x0d81 }
            java.lang.String r13 = r13.zztt     // Catch:{ all -> 0x0d81 }
            long r12 = r12.zzck(r13)     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzbw r14 = r1.zzada     // Catch:{ all -> 0x0d81 }
            r14.zzgr()     // Catch:{ all -> 0x0d81 }
            java.lang.Long r14 = r11.zzaxd     // Catch:{ all -> 0x0d81 }
            long r14 = r14.longValue()     // Catch:{ all -> 0x0d81 }
            long r14 = com.google.android.gms.measurement.internal.zzfx.zzc(r14, r12)     // Catch:{ all -> 0x0d81 }
            r21 = r7
            java.lang.String r7 = "_dbg"
            r22 = r8
            java.lang.Long r8 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0d81 }
            boolean r23 = android.text.TextUtils.isEmpty(r7)     // Catch:{ all -> 0x0d81 }
            if (r23 != 0) goto L_0x09c3
            if (r8 != 0) goto L_0x0980
            goto L_0x09c3
        L_0x0980:
            r25 = r4
            com.google.android.gms.internal.measurement.zzfu[] r4 = r11.zzaxc     // Catch:{ all -> 0x0da2 }
            r23 = r9
            int r9 = r4.length     // Catch:{ all -> 0x0da2 }
            r26 = r12
            r12 = 0
        L_0x098a:
            if (r12 >= r9) goto L_0x09c9
            r13 = r4[r12]     // Catch:{ all -> 0x0da2 }
            r24 = r4
            java.lang.String r4 = r13.name     // Catch:{ all -> 0x0da2 }
            boolean r4 = r7.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x09be
            boolean r4 = r8 instanceof java.lang.Long     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x09a4
            java.lang.Long r4 = r13.zzaxg     // Catch:{ all -> 0x0da2 }
            boolean r4 = r8.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r4 != 0) goto L_0x09bc
        L_0x09a4:
            boolean r4 = r8 instanceof java.lang.String     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x09b0
            java.lang.String r4 = r13.zzamn     // Catch:{ all -> 0x0da2 }
            boolean r4 = r8.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r4 != 0) goto L_0x09bc
        L_0x09b0:
            boolean r4 = r8 instanceof java.lang.Double     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x09c9
            java.lang.Double r4 = r13.zzaup     // Catch:{ all -> 0x0da2 }
            boolean r4 = r8.equals(r4)     // Catch:{ all -> 0x0da2 }
            if (r4 == 0) goto L_0x09c9
        L_0x09bc:
            r4 = 1
            goto L_0x09ca
        L_0x09be:
            int r12 = r12 + 1
            r4 = r24
            goto L_0x098a
        L_0x09c3:
            r25 = r4
            r23 = r9
            r26 = r12
        L_0x09c9:
            r4 = 0
        L_0x09ca:
            if (r4 != 0) goto L_0x09db
            com.google.android.gms.measurement.internal.zzbq r4 = r43.zzls()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r11.name     // Catch:{ all -> 0x0da2 }
            int r13 = r4.zzq(r7, r8)     // Catch:{ all -> 0x0da2 }
            goto L_0x09dc
        L_0x09db:
            r13 = 1
        L_0x09dc:
            if (r13 > 0) goto L_0x09fc
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = "Sample rate must be positive. event, rate"
            java.lang.String r8 = r11.name     // Catch:{ all -> 0x0da2 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0da2 }
            r4.zze(r7, r8, r9)     // Catch:{ all -> 0x0da2 }
            int r4 = r10 + 1
            r5[r10] = r11     // Catch:{ all -> 0x0da2 }
            r10 = r4
            r24 = r6
            goto L_0x0944
        L_0x09fc:
            java.lang.String r4 = r11.name     // Catch:{ all -> 0x0d81 }
            java.lang.Object r4 = r2.get(r4)     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzac r4 = (com.google.android.gms.measurement.internal.zzac) r4     // Catch:{ all -> 0x0d81 }
            if (r4 != 0) goto L_0x0a50
            com.google.android.gms.measurement.internal.zzt r4 = r43.zzjt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r11.name     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzac r4 = r4.zzg(r7, r8)     // Catch:{ all -> 0x0da2 }
            if (r4 != 0) goto L_0x0a50
            com.google.android.gms.measurement.internal.zzbw r4 = r1.zzada     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjj()     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = "Event being bundled has no eventAggregate. appId, eventName"
            com.google.android.gms.internal.measurement.zzfw r8 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r8.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = r11.name     // Catch:{ all -> 0x0da2 }
            r4.zze(r7, r8, r9)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzac r4 = new com.google.android.gms.measurement.internal.zzac     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da2 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r11.name     // Catch:{ all -> 0x0da2 }
            r31 = 1
            r33 = 1
            java.lang.Long r9 = r11.zzaxd     // Catch:{ all -> 0x0da2 }
            long r35 = r9.longValue()     // Catch:{ all -> 0x0da2 }
            r37 = 0
            r39 = 0
            r40 = 0
            r41 = 0
            r42 = 0
            r28 = r4
            r29 = r7
            r30 = r8
            r28.<init>(r29, r30, r31, r33, r35, r37, r39, r40, r41, r42)     // Catch:{ all -> 0x0da2 }
        L_0x0a50:
            r43.zzjr()     // Catch:{ all -> 0x0d81 }
            java.lang.String r7 = "_eid"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzft.zzb(r11, r7)     // Catch:{ all -> 0x0d81 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x0d81 }
            if (r7 == 0) goto L_0x0a5f
            r8 = 1
            goto L_0x0a60
        L_0x0a5f:
            r8 = 0
        L_0x0a60:
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)     // Catch:{ all -> 0x0d81 }
            r9 = 1
            if (r13 != r9) goto L_0x0a8c
            int r7 = r10 + 1
            r5[r10] = r11     // Catch:{ all -> 0x0da2 }
            boolean r8 = r8.booleanValue()     // Catch:{ all -> 0x0da2 }
            if (r8 == 0) goto L_0x0a87
            java.lang.Long r8 = r4.zzaia     // Catch:{ all -> 0x0da2 }
            if (r8 != 0) goto L_0x0a7d
            java.lang.Long r8 = r4.zzaib     // Catch:{ all -> 0x0da2 }
            if (r8 != 0) goto L_0x0a7d
            java.lang.Boolean r8 = r4.zzaic     // Catch:{ all -> 0x0da2 }
            if (r8 == 0) goto L_0x0a87
        L_0x0a7d:
            r8 = 0
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r8, r8, r8)     // Catch:{ all -> 0x0da2 }
            java.lang.String r8 = r11.name     // Catch:{ all -> 0x0da2 }
            r2.put(r8, r4)     // Catch:{ all -> 0x0da2 }
        L_0x0a87:
            r24 = r6
            r10 = r7
            goto L_0x0944
        L_0x0a8c:
            int r9 = r6.nextInt(r13)     // Catch:{ all -> 0x0d81 }
            if (r9 != 0) goto L_0x0acb
            r43.zzjr()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r7 = r11.zzaxc     // Catch:{ all -> 0x0da2 }
            java.lang.String r9 = "_sr"
            long r12 = (long) r13     // Catch:{ all -> 0x0da2 }
            r24 = r6
            java.lang.Long r6 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.internal.measurement.zzfu[] r6 = com.google.android.gms.measurement.internal.zzft.zza(r7, r9, r6)     // Catch:{ all -> 0x0da2 }
            r11.zzaxc = r6     // Catch:{ all -> 0x0da2 }
            int r6 = r10 + 1
            r5[r10] = r11     // Catch:{ all -> 0x0da2 }
            boolean r7 = r8.booleanValue()     // Catch:{ all -> 0x0da2 }
            if (r7 == 0) goto L_0x0ab9
            java.lang.Long r7 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0da2 }
            r8 = 0
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r8, r7, r8)     // Catch:{ all -> 0x0da2 }
        L_0x0ab9:
            java.lang.String r7 = r11.name     // Catch:{ all -> 0x0da2 }
            java.lang.Long r8 = r11.zzaxd     // Catch:{ all -> 0x0da2 }
            long r8 = r8.longValue()     // Catch:{ all -> 0x0da2 }
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r8, r14)     // Catch:{ all -> 0x0da2 }
            r2.put(r7, r4)     // Catch:{ all -> 0x0da2 }
            r10 = r6
            goto L_0x0944
        L_0x0acb:
            r24 = r6
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzq r6 = r6.zzgv()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfw r9 = r3.zzaui     // Catch:{ all -> 0x0d81 }
            java.lang.String r9 = r9.zztt     // Catch:{ all -> 0x0d81 }
            boolean r6 = r6.zzbf(r9)     // Catch:{ all -> 0x0d81 }
            if (r6 == 0) goto L_0x0b05
            java.lang.Long r6 = r4.zzahz     // Catch:{ all -> 0x0d81 }
            if (r6 == 0) goto L_0x0aea
            java.lang.Long r6 = r4.zzahz     // Catch:{ all -> 0x0da2 }
            long r26 = r6.longValue()     // Catch:{ all -> 0x0da2 }
            r12 = r2
            r9 = r7
            goto L_0x0afd
        L_0x0aea:
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0d81 }
            r6.zzgr()     // Catch:{ all -> 0x0d81 }
            java.lang.Long r6 = r11.zzaxe     // Catch:{ all -> 0x0d81 }
            r9 = r7
            long r6 = r6.longValue()     // Catch:{ all -> 0x0d81 }
            r12 = r2
            r1 = r26
            long r26 = com.google.android.gms.measurement.internal.zzfx.zzc(r6, r1)     // Catch:{ all -> 0x0d81 }
        L_0x0afd:
            int r1 = (r26 > r14 ? 1 : (r26 == r14 ? 0 : -1))
            if (r1 == 0) goto L_0x0b03
        L_0x0b01:
            r1 = 1
            goto L_0x0b1c
        L_0x0b03:
            r1 = 0
            goto L_0x0b1c
        L_0x0b05:
            r12 = r2
            r9 = r7
            long r1 = r4.zzahy     // Catch:{ all -> 0x0d81 }
            java.lang.Long r6 = r11.zzaxd     // Catch:{ all -> 0x0d81 }
            long r6 = r6.longValue()     // Catch:{ all -> 0x0d81 }
            long r6 = r6 - r1
            long r1 = java.lang.Math.abs(r6)     // Catch:{ all -> 0x0d81 }
            r6 = 86400000(0x5265c00, double:4.2687272E-316)
            int r26 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r26 < 0) goto L_0x0b03
            goto L_0x0b01
        L_0x0b1c:
            if (r1 == 0) goto L_0x0b6b
            r43.zzjr()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfu[] r1 = r11.zzaxc     // Catch:{ all -> 0x0d81 }
            java.lang.String r2 = "_efs"
            java.lang.Long r6 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfu[] r1 = com.google.android.gms.measurement.internal.zzft.zza(r1, r2, r6)     // Catch:{ all -> 0x0d81 }
            r11.zzaxc = r1     // Catch:{ all -> 0x0d81 }
            r43.zzjr()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfu[] r1 = r11.zzaxc     // Catch:{ all -> 0x0d81 }
            java.lang.String r2 = "_sr"
            long r6 = (long) r13     // Catch:{ all -> 0x0d81 }
            java.lang.Long r9 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzfu[] r1 = com.google.android.gms.measurement.internal.zzft.zza(r1, r2, r9)     // Catch:{ all -> 0x0d81 }
            r11.zzaxc = r1     // Catch:{ all -> 0x0d81 }
            int r1 = r10 + 1
            r5[r10] = r11     // Catch:{ all -> 0x0d81 }
            boolean r2 = r8.booleanValue()     // Catch:{ all -> 0x0d81 }
            if (r2 == 0) goto L_0x0b59
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0d81 }
            r6 = 1
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r6)     // Catch:{ all -> 0x0d81 }
            r6 = 0
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r6, r2, r7)     // Catch:{ all -> 0x0d81 }
        L_0x0b59:
            java.lang.String r2 = r11.name     // Catch:{ all -> 0x0d81 }
            java.lang.Long r6 = r11.zzaxd     // Catch:{ all -> 0x0d81 }
            long r6 = r6.longValue()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r6, r14)     // Catch:{ all -> 0x0d81 }
            r6 = r12
            r6.put(r2, r4)     // Catch:{ all -> 0x0d81 }
            r10 = r1
            goto L_0x0b7c
        L_0x0b6b:
            r6 = r12
            boolean r1 = r8.booleanValue()     // Catch:{ all -> 0x0d81 }
            if (r1 == 0) goto L_0x0b7c
            java.lang.String r1 = r11.name     // Catch:{ all -> 0x0d81 }
            r2 = 0
            com.google.android.gms.measurement.internal.zzac r4 = r4.zza(r9, r2, r2)     // Catch:{ all -> 0x0d81 }
            r6.put(r1, r4)     // Catch:{ all -> 0x0d81 }
        L_0x0b7c:
            int r9 = r23 + 1
            r1 = r43
            r2 = r6
            r7 = r21
            r8 = r22
            r6 = r24
            r4 = r25
            goto L_0x08cb
        L_0x0b8b:
            r6 = r2
            r1 = r4
            com.google.android.gms.internal.measurement.zzft[] r2 = r1.zzaxk     // Catch:{ all -> 0x0d81 }
            int r2 = r2.length     // Catch:{ all -> 0x0d81 }
            if (r10 >= r2) goto L_0x0b9a
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r5, r10)     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.internal.measurement.zzft[] r2 = (com.google.android.gms.internal.measurement.zzft[]) r2     // Catch:{ all -> 0x0d81 }
            r1.zzaxk = r2     // Catch:{ all -> 0x0d81 }
        L_0x0b9a:
            java.util.Set r2 = r6.entrySet()     // Catch:{ all -> 0x0d81 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x0d81 }
        L_0x0ba2:
            boolean r4 = r2.hasNext()     // Catch:{ all -> 0x0d81 }
            if (r4 == 0) goto L_0x0bbd
            java.lang.Object r4 = r2.next()     // Catch:{ all -> 0x0d81 }
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzt r5 = r43.zzjt()     // Catch:{ all -> 0x0d81 }
            java.lang.Object r4 = r4.getValue()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzac r4 = (com.google.android.gms.measurement.internal.zzac) r4     // Catch:{ all -> 0x0d81 }
            r5.zza(r4)     // Catch:{ all -> 0x0d81 }
            goto L_0x0ba2
        L_0x0bbc:
            r1 = r4
        L_0x0bbd:
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0d81 }
            r1.zzaxn = r2     // Catch:{ all -> 0x0d81 }
            r4 = -9223372036854775808
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0d81 }
            r1.zzaxo = r2     // Catch:{ all -> 0x0d81 }
            r2 = 0
        L_0x0bd1:
            com.google.android.gms.internal.measurement.zzft[] r4 = r1.zzaxk     // Catch:{ all -> 0x0d81 }
            int r4 = r4.length     // Catch:{ all -> 0x0d81 }
            if (r2 >= r4) goto L_0x0c05
            com.google.android.gms.internal.measurement.zzft[] r4 = r1.zzaxk     // Catch:{ all -> 0x0d81 }
            r4 = r4[r2]     // Catch:{ all -> 0x0d81 }
            java.lang.Long r5 = r4.zzaxd     // Catch:{ all -> 0x0d81 }
            long r5 = r5.longValue()     // Catch:{ all -> 0x0d81 }
            java.lang.Long r7 = r1.zzaxn     // Catch:{ all -> 0x0d81 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0d81 }
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x0bee
            java.lang.Long r5 = r4.zzaxd     // Catch:{ all -> 0x0d81 }
            r1.zzaxn = r5     // Catch:{ all -> 0x0d81 }
        L_0x0bee:
            java.lang.Long r5 = r4.zzaxd     // Catch:{ all -> 0x0d81 }
            long r5 = r5.longValue()     // Catch:{ all -> 0x0d81 }
            java.lang.Long r7 = r1.zzaxo     // Catch:{ all -> 0x0d81 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0d81 }
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x0c02
            java.lang.Long r4 = r4.zzaxd     // Catch:{ all -> 0x0d81 }
            r1.zzaxo = r4     // Catch:{ all -> 0x0d81 }
        L_0x0c02:
            int r2 = r2 + 1
            goto L_0x0bd1
        L_0x0c05:
            com.google.android.gms.internal.measurement.zzfw r2 = r3.zzaui     // Catch:{ all -> 0x0d81 }
            java.lang.String r2 = r2.zztt     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzt r4 = r43.zzjt()     // Catch:{ all -> 0x0d81 }
            com.google.android.gms.measurement.internal.zzg r4 = r4.zzbm(r2)     // Catch:{ all -> 0x0d81 }
            if (r4 != 0) goto L_0x0c2d
            r5 = r43
            com.google.android.gms.measurement.internal.zzbw r4 = r5.zzada     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = "Bundling raw events w/o app info. appId"
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da0 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da0 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r7)     // Catch:{ all -> 0x0da0 }
            r4.zzg(r6, r7)     // Catch:{ all -> 0x0da0 }
            goto L_0x0c8b
        L_0x0c2d:
            r5 = r43
            com.google.android.gms.internal.measurement.zzft[] r6 = r1.zzaxk     // Catch:{ all -> 0x0da0 }
            int r6 = r6.length     // Catch:{ all -> 0x0da0 }
            if (r6 <= 0) goto L_0x0c8b
            long r6 = r4.zzhe()     // Catch:{ all -> 0x0da0 }
            r8 = 0
            int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r10 == 0) goto L_0x0c43
            java.lang.Long r8 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0da0 }
            goto L_0x0c44
        L_0x0c43:
            r8 = 0
        L_0x0c44:
            r1.zzaxq = r8     // Catch:{ all -> 0x0da0 }
            long r8 = r4.zzhd()     // Catch:{ all -> 0x0da0 }
            r10 = 0
            int r12 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0c51
            goto L_0x0c52
        L_0x0c51:
            r6 = r8
        L_0x0c52:
            int r8 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x0c5b
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0da0 }
            goto L_0x0c5c
        L_0x0c5b:
            r6 = 0
        L_0x0c5c:
            r1.zzaxp = r6     // Catch:{ all -> 0x0da0 }
            r4.zzhm()     // Catch:{ all -> 0x0da0 }
            long r6 = r4.zzhj()     // Catch:{ all -> 0x0da0 }
            int r7 = (int) r6     // Catch:{ all -> 0x0da0 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0da0 }
            r1.zzaya = r6     // Catch:{ all -> 0x0da0 }
            java.lang.Long r6 = r1.zzaxn     // Catch:{ all -> 0x0da0 }
            long r6 = r6.longValue()     // Catch:{ all -> 0x0da0 }
            r4.zzo(r6)     // Catch:{ all -> 0x0da0 }
            java.lang.Long r6 = r1.zzaxo     // Catch:{ all -> 0x0da0 }
            long r6 = r6.longValue()     // Catch:{ all -> 0x0da0 }
            r4.zzp(r6)     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = r4.zzhu()     // Catch:{ all -> 0x0da0 }
            r1.zzagm = r6     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzt r6 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            r6.zza(r4)     // Catch:{ all -> 0x0da0 }
        L_0x0c8b:
            com.google.android.gms.internal.measurement.zzft[] r4 = r1.zzaxk     // Catch:{ all -> 0x0da0 }
            int r4 = r4.length     // Catch:{ all -> 0x0da0 }
            if (r4 <= 0) goto L_0x0ce0
            com.google.android.gms.measurement.internal.zzbw r4 = r5.zzada     // Catch:{ all -> 0x0da0 }
            r4.zzgw()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzbq r4 = r43.zzls()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.internal.measurement.zzfw r6 = r3.zzaui     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = r6.zztt     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.internal.measurement.zzfp r4 = r4.zzcg(r6)     // Catch:{ all -> 0x0da0 }
            if (r4 == 0) goto L_0x0cad
            java.lang.Long r6 = r4.zzawm     // Catch:{ all -> 0x0da0 }
            if (r6 != 0) goto L_0x0ca8
            goto L_0x0cad
        L_0x0ca8:
            java.lang.Long r4 = r4.zzawm     // Catch:{ all -> 0x0da0 }
            r1.zzayh = r4     // Catch:{ all -> 0x0da0 }
            goto L_0x0cd7
        L_0x0cad:
            com.google.android.gms.internal.measurement.zzfw r4 = r3.zzaui     // Catch:{ all -> 0x0da0 }
            java.lang.String r4 = r4.zzafi     // Catch:{ all -> 0x0da0 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0da0 }
            if (r4 == 0) goto L_0x0cc0
            r6 = -1
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x0da0 }
            r1.zzayh = r4     // Catch:{ all -> 0x0da0 }
            goto L_0x0cd7
        L_0x0cc0:
            com.google.android.gms.measurement.internal.zzbw r4 = r5.zzada     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzas r4 = r4.zzgt()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjj()     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = "Did not find measurement config or missing version info. appId"
            com.google.android.gms.internal.measurement.zzfw r7 = r3.zzaui     // Catch:{ all -> 0x0da0 }
            java.lang.String r7 = r7.zztt     // Catch:{ all -> 0x0da0 }
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r7)     // Catch:{ all -> 0x0da0 }
            r4.zzg(r6, r7)     // Catch:{ all -> 0x0da0 }
        L_0x0cd7:
            com.google.android.gms.measurement.internal.zzt r4 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            r12 = r18
            r4.zza(r1, r12)     // Catch:{ all -> 0x0da0 }
        L_0x0ce0:
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            java.util.List<java.lang.Long> r3 = r3.zzauj     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r3)     // Catch:{ all -> 0x0da0 }
            r1.zzaf()     // Catch:{ all -> 0x0da0 }
            r1.zzcl()     // Catch:{ all -> 0x0da0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = "rowid in ("
            r4.<init>(r6)     // Catch:{ all -> 0x0da0 }
            r6 = 0
        L_0x0cf7:
            int r7 = r3.size()     // Catch:{ all -> 0x0da0 }
            if (r6 >= r7) goto L_0x0d14
            if (r6 == 0) goto L_0x0d04
            java.lang.String r7 = ","
            r4.append(r7)     // Catch:{ all -> 0x0da0 }
        L_0x0d04:
            java.lang.Object r7 = r3.get(r6)     // Catch:{ all -> 0x0da0 }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x0da0 }
            long r7 = r7.longValue()     // Catch:{ all -> 0x0da0 }
            r4.append(r7)     // Catch:{ all -> 0x0da0 }
            int r6 = r6 + 1
            goto L_0x0cf7
        L_0x0d14:
            java.lang.String r6 = ")"
            r4.append(r6)     // Catch:{ all -> 0x0da0 }
            android.database.sqlite.SQLiteDatabase r6 = r1.getWritableDatabase()     // Catch:{ all -> 0x0da0 }
            java.lang.String r7 = "raw_events"
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0da0 }
            r8 = 0
            int r4 = r6.delete(r7, r4, r8)     // Catch:{ all -> 0x0da0 }
            int r6 = r3.size()     // Catch:{ all -> 0x0da0 }
            if (r4 == r6) goto L_0x0d47
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0da0 }
            java.lang.String r6 = "Deleted fewer rows from raw events table than expected"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0da0 }
            int r3 = r3.size()     // Catch:{ all -> 0x0da0 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0da0 }
            r1.zze(r6, r4, r3)     // Catch:{ all -> 0x0da0 }
        L_0x0d47:
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            android.database.sqlite.SQLiteDatabase r3 = r1.getWritableDatabase()     // Catch:{ all -> 0x0da0 }
            java.lang.String r4 = "delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)"
            r6 = 2
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x0d5e }
            r7 = 0
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x0d5e }
            r7 = 1
            r6[r7] = r2     // Catch:{ SQLiteException -> 0x0d5e }
            r3.execSQL(r4, r6)     // Catch:{ SQLiteException -> 0x0d5e }
            goto L_0x0d71
        L_0x0d5e:
            r0 = move-exception
            r3 = r0
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0da0 }
            java.lang.String r4 = "Failed to remove unused event metadata. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzas.zzbw(r2)     // Catch:{ all -> 0x0da0 }
            r1.zze(r4, r2, r3)     // Catch:{ all -> 0x0da0 }
        L_0x0d71:
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            r1.setTransactionSuccessful()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()
            r1.endTransaction()
            r1 = 1
            return r1
        L_0x0d81:
            r0 = move-exception
            r5 = r43
            goto L_0x0da4
        L_0x0d85:
            r5 = r1
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()     // Catch:{ all -> 0x0da0 }
            r1.setTransactionSuccessful()     // Catch:{ all -> 0x0da0 }
            com.google.android.gms.measurement.internal.zzt r1 = r43.zzjt()
            r1.endTransaction()
            r1 = 0
            return r1
        L_0x0d96:
            r0 = move-exception
            r5 = r1
            goto L_0x0256
        L_0x0d9a:
            if (r9 == 0) goto L_0x0d9f
            r9.close()     // Catch:{ all -> 0x0da0 }
        L_0x0d9f:
            throw r1     // Catch:{ all -> 0x0da0 }
        L_0x0da0:
            r0 = move-exception
            goto L_0x0da4
        L_0x0da2:
            r0 = move-exception
            r5 = r1
        L_0x0da4:
            r1 = r0
            com.google.android.gms.measurement.internal.zzt r2 = r43.zzjt()
            r2.endTransaction()
            goto L_0x0dae
        L_0x0dad:
            throw r1
        L_0x0dae:
            goto L_0x0dad
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzd(java.lang.String, long):boolean");
    }

    private final boolean zza(zzft zzft, zzft zzft2) {
        Object obj;
        Preconditions.checkArgument("_e".equals(zzft.name));
        zzjr();
        zzfu zza2 = zzft.zza(zzft, "_sc");
        String str = null;
        if (zza2 == null) {
            obj = null;
        } else {
            obj = zza2.zzamn;
        }
        zzjr();
        zzfu zza3 = zzft.zza(zzft2, "_pc");
        if (zza3 != null) {
            str = zza3.zzamn;
        }
        if (str == null || !str.equals(obj)) {
            return false;
        }
        zzjr();
        String str2 = "_et";
        zzfu zza4 = zzft.zza(zzft, str2);
        if (zza4.zzaxg != null && zza4.zzaxg.longValue() > 0) {
            long longValue = zza4.zzaxg.longValue();
            zzjr();
            zzfu zza5 = zzft.zza(zzft2, str2);
            if (!(zza5 == null || zza5.zzaxg == null || zza5.zzaxg.longValue() <= 0)) {
                longValue += zza5.zzaxg.longValue();
            }
            zzjr();
            zzft2.zzaxc = zzft.zza(zzft2.zzaxc, str2, (Object) Long.valueOf(longValue));
            zzjr();
            zzft.zzaxc = zzft.zza(zzft.zzaxc, "_fr", (Object) Long.valueOf(1));
        }
        return true;
    }

    private static zzfu[] zza(zzfu[] zzfuArr, String str) {
        int i = 0;
        while (true) {
            if (i >= zzfuArr.length) {
                i = -1;
                break;
            } else if (str.equals(zzfuArr[i].name)) {
                break;
            } else {
                i++;
            }
        }
        if (i < 0) {
            return zzfuArr;
        }
        return zza(zzfuArr, i);
    }

    private static zzfu[] zza(zzfu[] zzfuArr, int i) {
        zzfu[] zzfuArr2 = new zzfu[(zzfuArr.length - 1)];
        if (i > 0) {
            System.arraycopy(zzfuArr, 0, zzfuArr2, 0, i);
        }
        if (i < zzfuArr2.length) {
            System.arraycopy(zzfuArr, i + 1, zzfuArr2, i, zzfuArr2.length - i);
        }
        return zzfuArr2;
    }

    private static zzfu[] zza(zzfu[] zzfuArr, int i, String str) {
        int i2 = 0;
        while (true) {
            String str2 = "_err";
            if (i2 >= zzfuArr.length) {
                zzfu[] zzfuArr2 = new zzfu[(zzfuArr.length + 2)];
                System.arraycopy(zzfuArr, 0, zzfuArr2, 0, zzfuArr.length);
                zzfu zzfu = new zzfu();
                zzfu.name = str2;
                zzfu.zzaxg = Long.valueOf((long) i);
                zzfu zzfu2 = new zzfu();
                zzfu2.name = "_ev";
                zzfu2.zzamn = str;
                zzfuArr2[zzfuArr2.length - 2] = zzfu;
                zzfuArr2[zzfuArr2.length - 1] = zzfu2;
                return zzfuArr2;
            } else if (str2.equals(zzfuArr[i2].name)) {
                return zzfuArr;
            } else {
                i2++;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzt zzjt;
        zzaf();
        zzlx();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzaty = false;
                zzmc();
                throw th2;
            }
        }
        List<Long> list = this.zzauc;
        this.zzauc = null;
        boolean z = true;
        if ((i == 200 || i == 204) && th == null) {
            try {
                this.zzada.zzgu().zzanc.set(this.zzada.zzbx().currentTimeMillis());
                this.zzada.zzgu().zzand.set(0);
                zzmb();
                this.zzada.zzgt().zzjo().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzjt().beginTransaction();
                try {
                    for (Long l : list) {
                        try {
                            zzjt = zzjt();
                            long longValue = l.longValue();
                            zzjt.zzaf();
                            zzjt.zzcl();
                            if (zzjt.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                                throw new SQLiteException("Deleted fewer rows from queue than expected");
                            }
                        } catch (SQLiteException e) {
                            zzjt.zzgt().zzjg().zzg("Failed to delete a bundle in a queue table", e);
                            throw e;
                        } catch (SQLiteException e2) {
                            if (this.zzaud == null || !this.zzaud.contains(l)) {
                                throw e2;
                            }
                        }
                    }
                    zzjt().setTransactionSuccessful();
                    zzjt().endTransaction();
                    this.zzaud = null;
                    if (!zzlt().zzfb() || !zzma()) {
                        this.zzaue = -1;
                        zzmb();
                    } else {
                        zzlz();
                    }
                    this.zzatt = 0;
                } catch (Throwable th3) {
                    zzjt().endTransaction();
                    throw th3;
                }
            } catch (SQLiteException e3) {
                this.zzada.zzgt().zzjg().zzg("Database error while trying to delete uploaded bundles", e3);
                this.zzatt = this.zzada.zzbx().elapsedRealtime();
                this.zzada.zzgt().zzjo().zzg("Disable upload, time", Long.valueOf(this.zzatt));
            }
        } else {
            this.zzada.zzgt().zzjo().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzada.zzgu().zzand.set(this.zzada.zzbx().currentTimeMillis());
            if (i != 503) {
                if (i != 429) {
                    z = false;
                }
            }
            if (z) {
                this.zzada.zzgu().zzane.set(this.zzada.zzbx().currentTimeMillis());
            }
            if (this.zzada.zzgv().zzaw(str)) {
                zzjt().zzc(list);
            }
            zzmb();
        }
        this.zzaty = false;
        zzmc();
    }

    private final boolean zzma() {
        zzaf();
        zzlx();
        return zzjt().zzim() || !TextUtils.isEmpty(zzjt().zzih());
    }

    private final void zzb(zzg zzg) {
        Map map;
        zzaf();
        if (!TextUtils.isEmpty(zzg.getGmpAppId()) || (zzq.zzig() && !TextUtils.isEmpty(zzg.zzhb()))) {
            zzq zzgv = this.zzada.zzgv();
            Builder builder = new Builder();
            String gmpAppId = zzg.getGmpAppId();
            if (TextUtils.isEmpty(gmpAppId) && zzq.zzig()) {
                gmpAppId = zzg.zzhb();
            }
            Builder encodedAuthority = builder.scheme((String) zzai.zzaiy.get()).encodedAuthority((String) zzai.zzaiz.get());
            String str = "config/app/";
            String valueOf = String.valueOf(gmpAppId);
            encodedAuthority.path(valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).appendQueryParameter("app_instance_id", zzg.getAppInstanceId()).appendQueryParameter("platform", AbstractSpiCall.ANDROID_CLIENT_TYPE).appendQueryParameter("gmp_version", String.valueOf(zzgv.zzhh()));
            String uri = builder.build().toString();
            try {
                URL url = new URL(uri);
                this.zzada.zzgt().zzjo().zzg("Fetching remote configuration", zzg.zzal());
                zzfp zzcg = zzls().zzcg(zzg.zzal());
                String zzch = zzls().zzch(zzg.zzal());
                if (zzcg == null || TextUtils.isEmpty(zzch)) {
                    map = null;
                } else {
                    ArrayMap arrayMap = new ArrayMap();
                    arrayMap.put("If-Modified-Since", zzch);
                    map = arrayMap;
                }
                this.zzatx = true;
                zzaw zzlt = zzlt();
                String zzal = zzg.zzal();
                zzfq zzfq = new zzfq(this);
                zzlt.zzaf();
                zzlt.zzcl();
                Preconditions.checkNotNull(url);
                Preconditions.checkNotNull(zzfq);
                zzbr zzgs = zzlt.zzgs();
                zzba zzba = new zzba(zzlt, zzal, url, null, map, zzfq);
                zzgs.zzd((Runnable) zzba);
            } catch (MalformedURLException unused) {
                this.zzada.zzgt().zzjg().zze("Failed to parse config URL. Not fetching. appId", zzas.zzbw(zzg.zzal()), uri);
            }
        } else {
            zzb(zzg.zzal(), 204, null, null, null);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x013a A[Catch:{ all -> 0x018d, all -> 0x0196 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x014a A[Catch:{ all -> 0x018d, all -> 0x0196 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r11) {
        /*
            r6 = this;
            r6.zzaf()
            r6.zzlx()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7)
            r0 = 0
            if (r10 != 0) goto L_0x000e
            byte[] r10 = new byte[r0]     // Catch:{ all -> 0x0196 }
        L_0x000e:
            com.google.android.gms.measurement.internal.zzbw r1 = r6.zzada     // Catch:{ all -> 0x0196 }
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()     // Catch:{ all -> 0x0196 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()     // Catch:{ all -> 0x0196 }
            java.lang.String r2 = "onConfigFetched. Response size"
            int r3 = r10.length     // Catch:{ all -> 0x0196 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0196 }
            r1.zzg(r2, r3)     // Catch:{ all -> 0x0196 }
            com.google.android.gms.measurement.internal.zzt r1 = r6.zzjt()     // Catch:{ all -> 0x0196 }
            r1.beginTransaction()     // Catch:{ all -> 0x0196 }
            com.google.android.gms.measurement.internal.zzt r1 = r6.zzjt()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzg r1 = r1.zzbm(r7)     // Catch:{ all -> 0x018d }
            r2 = 200(0xc8, float:2.8E-43)
            r3 = 304(0x130, float:4.26E-43)
            r4 = 1
            if (r8 == r2) goto L_0x003e
            r2 = 204(0xcc, float:2.86E-43)
            if (r8 == r2) goto L_0x003e
            if (r8 != r3) goto L_0x0042
        L_0x003e:
            if (r9 != 0) goto L_0x0042
            r2 = 1
            goto L_0x0043
        L_0x0042:
            r2 = 0
        L_0x0043:
            if (r1 != 0) goto L_0x005a
            com.google.android.gms.measurement.internal.zzbw r8 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzas r8 = r8.zzgt()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjj()     // Catch:{ all -> 0x018d }
            java.lang.String r9 = "App does not exist in onConfigFetched. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r7)     // Catch:{ all -> 0x018d }
            r8.zzg(r9, r7)     // Catch:{ all -> 0x018d }
            goto L_0x0179
        L_0x005a:
            r5 = 404(0x194, float:5.66E-43)
            if (r2 != 0) goto L_0x00ca
            if (r8 != r5) goto L_0x0061
            goto L_0x00ca
        L_0x0061:
            com.google.android.gms.measurement.internal.zzbw r10 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.common.util.Clock r10 = r10.zzbx()     // Catch:{ all -> 0x018d }
            long r10 = r10.currentTimeMillis()     // Catch:{ all -> 0x018d }
            r1.zzv(r10)     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzt r10 = r6.zzjt()     // Catch:{ all -> 0x018d }
            r10.zza(r1)     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbw r10 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzas r10 = r10.zzgt()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzau r10 = r10.zzjo()     // Catch:{ all -> 0x018d }
            java.lang.String r11 = "Fetching config failed. code, error"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x018d }
            r10.zze(r11, r1, r9)     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbq r9 = r6.zzls()     // Catch:{ all -> 0x018d }
            r9.zzci(r7)     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbw r7 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbd r7 = r7.zzgu()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbg r7 = r7.zzand     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbw r9 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.common.util.Clock r9 = r9.zzbx()     // Catch:{ all -> 0x018d }
            long r9 = r9.currentTimeMillis()     // Catch:{ all -> 0x018d }
            r7.set(r9)     // Catch:{ all -> 0x018d }
            r7 = 503(0x1f7, float:7.05E-43)
            if (r8 == r7) goto L_0x00ae
            r7 = 429(0x1ad, float:6.01E-43)
            if (r8 != r7) goto L_0x00ad
            goto L_0x00ae
        L_0x00ad:
            r4 = 0
        L_0x00ae:
            if (r4 == 0) goto L_0x00c5
            com.google.android.gms.measurement.internal.zzbw r7 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbd r7 = r7.zzgu()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbg r7 = r7.zzane     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzbw r8 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.common.util.Clock r8 = r8.zzbx()     // Catch:{ all -> 0x018d }
            long r8 = r8.currentTimeMillis()     // Catch:{ all -> 0x018d }
            r7.set(r8)     // Catch:{ all -> 0x018d }
        L_0x00c5:
            r6.zzmb()     // Catch:{ all -> 0x018d }
            goto L_0x0179
        L_0x00ca:
            r9 = 0
            if (r11 == 0) goto L_0x00d6
            java.lang.String r2 = "Last-Modified"
            java.lang.Object r11 = r11.get(r2)     // Catch:{ all -> 0x018d }
            java.util.List r11 = (java.util.List) r11     // Catch:{ all -> 0x018d }
            goto L_0x00d7
        L_0x00d6:
            r11 = r9
        L_0x00d7:
            if (r11 == 0) goto L_0x00e6
            int r2 = r11.size()     // Catch:{ all -> 0x018d }
            if (r2 <= 0) goto L_0x00e6
            java.lang.Object r11 = r11.get(r0)     // Catch:{ all -> 0x018d }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x018d }
            goto L_0x00e7
        L_0x00e6:
            r11 = r9
        L_0x00e7:
            if (r8 == r5) goto L_0x0103
            if (r8 != r3) goto L_0x00ec
            goto L_0x0103
        L_0x00ec:
            com.google.android.gms.measurement.internal.zzbq r9 = r6.zzls()     // Catch:{ all -> 0x018d }
            boolean r9 = r9.zza(r7, r10, r11)     // Catch:{ all -> 0x018d }
            if (r9 != 0) goto L_0x0124
            com.google.android.gms.measurement.internal.zzt r7 = r6.zzjt()     // Catch:{ all -> 0x0196 }
            r7.endTransaction()     // Catch:{ all -> 0x0196 }
            r6.zzatx = r0
            r6.zzmc()
            return
        L_0x0103:
            com.google.android.gms.measurement.internal.zzbq r11 = r6.zzls()     // Catch:{ all -> 0x018d }
            com.google.android.gms.internal.measurement.zzfp r11 = r11.zzcg(r7)     // Catch:{ all -> 0x018d }
            if (r11 != 0) goto L_0x0124
            com.google.android.gms.measurement.internal.zzbq r11 = r6.zzls()     // Catch:{ all -> 0x018d }
            boolean r9 = r11.zza(r7, r9, r9)     // Catch:{ all -> 0x018d }
            if (r9 != 0) goto L_0x0124
            com.google.android.gms.measurement.internal.zzt r7 = r6.zzjt()     // Catch:{ all -> 0x0196 }
            r7.endTransaction()     // Catch:{ all -> 0x0196 }
            r6.zzatx = r0
            r6.zzmc()
            return
        L_0x0124:
            com.google.android.gms.measurement.internal.zzbw r9 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.common.util.Clock r9 = r9.zzbx()     // Catch:{ all -> 0x018d }
            long r2 = r9.currentTimeMillis()     // Catch:{ all -> 0x018d }
            r1.zzu(r2)     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzt r9 = r6.zzjt()     // Catch:{ all -> 0x018d }
            r9.zza(r1)     // Catch:{ all -> 0x018d }
            if (r8 != r5) goto L_0x014a
            com.google.android.gms.measurement.internal.zzbw r8 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzas r8 = r8.zzgt()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjl()     // Catch:{ all -> 0x018d }
            java.lang.String r9 = "Config not found. Using empty config. appId"
            r8.zzg(r9, r7)     // Catch:{ all -> 0x018d }
            goto L_0x0162
        L_0x014a:
            com.google.android.gms.measurement.internal.zzbw r7 = r6.zzada     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzas r7 = r7.zzgt()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzau r7 = r7.zzjo()     // Catch:{ all -> 0x018d }
            java.lang.String r9 = "Successfully fetched config. Got network response. code, size"
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x018d }
            int r10 = r10.length     // Catch:{ all -> 0x018d }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x018d }
            r7.zze(r9, r8, r10)     // Catch:{ all -> 0x018d }
        L_0x0162:
            com.google.android.gms.measurement.internal.zzaw r7 = r6.zzlt()     // Catch:{ all -> 0x018d }
            boolean r7 = r7.zzfb()     // Catch:{ all -> 0x018d }
            if (r7 == 0) goto L_0x0176
            boolean r7 = r6.zzma()     // Catch:{ all -> 0x018d }
            if (r7 == 0) goto L_0x0176
            r6.zzlz()     // Catch:{ all -> 0x018d }
            goto L_0x0179
        L_0x0176:
            r6.zzmb()     // Catch:{ all -> 0x018d }
        L_0x0179:
            com.google.android.gms.measurement.internal.zzt r7 = r6.zzjt()     // Catch:{ all -> 0x018d }
            r7.setTransactionSuccessful()     // Catch:{ all -> 0x018d }
            com.google.android.gms.measurement.internal.zzt r7 = r6.zzjt()     // Catch:{ all -> 0x0196 }
            r7.endTransaction()     // Catch:{ all -> 0x0196 }
            r6.zzatx = r0
            r6.zzmc()
            return
        L_0x018d:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzt r8 = r6.zzjt()     // Catch:{ all -> 0x0196 }
            r8.endTransaction()     // Catch:{ all -> 0x0196 }
            throw r7     // Catch:{ all -> 0x0196 }
        L_0x0196:
            r7 = move-exception
            r6.zzatx = r0
            r6.zzmc()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzb(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzmb() {
        /*
            r20 = this;
            r0 = r20
            r20.zzaf()
            r20.zzlx()
            boolean r1 = r20.zzmf()
            if (r1 != 0) goto L_0x001d
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzq r1 = r1.zzgv()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r2 = com.google.android.gms.measurement.internal.zzai.zzalf
            boolean r1 = r1.zza(r2)
            if (r1 != 0) goto L_0x001d
            return
        L_0x001d:
            long r1 = r0.zzatt
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0062
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.common.util.Clock r1 = r1.zzbx()
            long r1 = r1.elapsedRealtime()
            r5 = 3600000(0x36ee80, double:1.7786363E-317)
            long r7 = r0.zzatt
            long r1 = r1 - r7
            long r1 = java.lang.Math.abs(r1)
            long r5 = r5 - r1
            int r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0060
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Long r2 = java.lang.Long.valueOf(r5)
            java.lang.String r3 = "Upload has been suspended. Will update scheduling later in approximately ms"
            r1.zzg(r3, r2)
            com.google.android.gms.measurement.internal.zzbb r1 = r20.zzlu()
            r1.unregister()
            com.google.android.gms.measurement.internal.zzfj r1 = r20.zzlv()
            r1.cancel()
            return
        L_0x0060:
            r0.zzatt = r3
        L_0x0062:
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            boolean r1 = r1.zzkv()
            if (r1 == 0) goto L_0x0267
            boolean r1 = r20.zzma()
            if (r1 != 0) goto L_0x0072
            goto L_0x0267
        L_0x0072:
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.common.util.Clock r1 = r1.zzbx()
            long r1 = r1.currentTimeMillis()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r5 = com.google.android.gms.measurement.internal.zzai.zzaju
            java.lang.Object r5 = r5.get()
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            long r5 = java.lang.Math.max(r3, r5)
            com.google.android.gms.measurement.internal.zzt r7 = r20.zzjt()
            boolean r7 = r7.zzin()
            if (r7 != 0) goto L_0x00a3
            com.google.android.gms.measurement.internal.zzt r7 = r20.zzjt()
            boolean r7 = r7.zzii()
            if (r7 == 0) goto L_0x00a1
            goto L_0x00a3
        L_0x00a1:
            r7 = 0
            goto L_0x00a4
        L_0x00a3:
            r7 = 1
        L_0x00a4:
            if (r7 == 0) goto L_0x00e0
            com.google.android.gms.measurement.internal.zzbw r9 = r0.zzada
            com.google.android.gms.measurement.internal.zzq r9 = r9.zzgv()
            java.lang.String r9 = r9.zzid()
            boolean r10 = android.text.TextUtils.isEmpty(r9)
            if (r10 != 0) goto L_0x00cf
            java.lang.String r10 = ".none."
            boolean r9 = r10.equals(r9)
            if (r9 != 0) goto L_0x00cf
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r9 = com.google.android.gms.measurement.internal.zzai.zzajp
            java.lang.Object r9 = r9.get()
            java.lang.Long r9 = (java.lang.Long) r9
            long r9 = r9.longValue()
            long r9 = java.lang.Math.max(r3, r9)
            goto L_0x00f0
        L_0x00cf:
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r9 = com.google.android.gms.measurement.internal.zzai.zzajo
            java.lang.Object r9 = r9.get()
            java.lang.Long r9 = (java.lang.Long) r9
            long r9 = r9.longValue()
            long r9 = java.lang.Math.max(r3, r9)
            goto L_0x00f0
        L_0x00e0:
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r9 = com.google.android.gms.measurement.internal.zzai.zzajn
            java.lang.Object r9 = r9.get()
            java.lang.Long r9 = (java.lang.Long) r9
            long r9 = r9.longValue()
            long r9 = java.lang.Math.max(r3, r9)
        L_0x00f0:
            com.google.android.gms.measurement.internal.zzbw r11 = r0.zzada
            com.google.android.gms.measurement.internal.zzbd r11 = r11.zzgu()
            com.google.android.gms.measurement.internal.zzbg r11 = r11.zzanc
            long r11 = r11.get()
            com.google.android.gms.measurement.internal.zzbw r13 = r0.zzada
            com.google.android.gms.measurement.internal.zzbd r13 = r13.zzgu()
            com.google.android.gms.measurement.internal.zzbg r13 = r13.zzand
            long r13 = r13.get()
            com.google.android.gms.measurement.internal.zzt r15 = r20.zzjt()
            r16 = r9
            long r8 = r15.zzik()
            com.google.android.gms.measurement.internal.zzt r10 = r20.zzjt()
            r18 = r5
            long r5 = r10.zzil()
            long r5 = java.lang.Math.max(r8, r5)
            int r8 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r8 != 0) goto L_0x0127
        L_0x0124:
            r8 = r3
            goto L_0x019c
        L_0x0127:
            long r5 = r5 - r1
            long r5 = java.lang.Math.abs(r5)
            long r5 = r1 - r5
            long r11 = r11 - r1
            long r8 = java.lang.Math.abs(r11)
            long r8 = r1 - r8
            long r13 = r13 - r1
            long r10 = java.lang.Math.abs(r13)
            long r1 = r1 - r10
            long r8 = java.lang.Math.max(r8, r1)
            long r10 = r5 + r18
            if (r7 == 0) goto L_0x014d
            int r7 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r7 <= 0) goto L_0x014d
            long r10 = java.lang.Math.min(r5, r8)
            long r10 = r10 + r16
        L_0x014d:
            com.google.android.gms.measurement.internal.zzft r7 = r20.zzjr()
            r12 = r16
            boolean r7 = r7.zzb(r8, r12)
            if (r7 != 0) goto L_0x015b
            long r8 = r8 + r12
            goto L_0x015c
        L_0x015b:
            r8 = r10
        L_0x015c:
            int r7 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r7 == 0) goto L_0x019c
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r7 < 0) goto L_0x019c
            r5 = 0
        L_0x0165:
            r6 = 20
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Integer> r7 = com.google.android.gms.measurement.internal.zzai.zzajw
            java.lang.Object r7 = r7.get()
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r10 = 0
            int r7 = java.lang.Math.max(r10, r7)
            int r6 = java.lang.Math.min(r6, r7)
            if (r5 >= r6) goto L_0x0124
            r6 = 1
            long r6 = r6 << r5
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r11 = com.google.android.gms.measurement.internal.zzai.zzajv
            java.lang.Object r11 = r11.get()
            java.lang.Long r11 = (java.lang.Long) r11
            long r11 = r11.longValue()
            long r11 = java.lang.Math.max(r3, r11)
            long r11 = r11 * r6
            long r8 = r8 + r11
            int r6 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
            if (r6 <= 0) goto L_0x0199
            goto L_0x019c
        L_0x0199:
            int r5 = r5 + 1
            goto L_0x0165
        L_0x019c:
            int r1 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r1 != 0) goto L_0x01be
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.String r2 = "Next upload time is 0"
            r1.zzby(r2)
            com.google.android.gms.measurement.internal.zzbb r1 = r20.zzlu()
            r1.unregister()
            com.google.android.gms.measurement.internal.zzfj r1 = r20.zzlv()
            r1.cancel()
            return
        L_0x01be:
            com.google.android.gms.measurement.internal.zzaw r1 = r20.zzlt()
            boolean r1 = r1.zzfb()
            if (r1 != 0) goto L_0x01e6
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.String r2 = "No network"
            r1.zzby(r2)
            com.google.android.gms.measurement.internal.zzbb r1 = r20.zzlu()
            r1.zzey()
            com.google.android.gms.measurement.internal.zzfj r1 = r20.zzlv()
            r1.cancel()
            return
        L_0x01e6:
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzbd r1 = r1.zzgu()
            com.google.android.gms.measurement.internal.zzbg r1 = r1.zzane
            long r1 = r1.get()
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r5 = com.google.android.gms.measurement.internal.zzai.zzajl
            java.lang.Object r5 = r5.get()
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            long r5 = java.lang.Math.max(r3, r5)
            com.google.android.gms.measurement.internal.zzft r7 = r20.zzjr()
            boolean r7 = r7.zzb(r1, r5)
            if (r7 != 0) goto L_0x0211
            long r1 = r1 + r5
            long r8 = java.lang.Math.max(r8, r1)
        L_0x0211:
            com.google.android.gms.measurement.internal.zzbb r1 = r20.zzlu()
            r1.unregister()
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.common.util.Clock r1 = r1.zzbx()
            long r1 = r1.currentTimeMillis()
            long r8 = r8 - r1
            int r1 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x024c
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Long> r1 = com.google.android.gms.measurement.internal.zzai.zzajq
            java.lang.Object r1 = r1.get()
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            long r8 = java.lang.Math.max(r3, r1)
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzbd r1 = r1.zzgu()
            com.google.android.gms.measurement.internal.zzbg r1 = r1.zzanc
            com.google.android.gms.measurement.internal.zzbw r2 = r0.zzada
            com.google.android.gms.common.util.Clock r2 = r2.zzbx()
            long r2 = r2.currentTimeMillis()
            r1.set(r2)
        L_0x024c:
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.Long r2 = java.lang.Long.valueOf(r8)
            java.lang.String r3 = "Upload scheduled in approximately ms"
            r1.zzg(r3, r2)
            com.google.android.gms.measurement.internal.zzfj r1 = r20.zzlv()
            r1.zzh(r8)
            return
        L_0x0267:
            com.google.android.gms.measurement.internal.zzbw r1 = r0.zzada
            com.google.android.gms.measurement.internal.zzas r1 = r1.zzgt()
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjo()
            java.lang.String r2 = "Nothing to upload or uploading impossible"
            r1.zzby(r2)
            com.google.android.gms.measurement.internal.zzbb r1 = r20.zzlu()
            r1.unregister()
            com.google.android.gms.measurement.internal.zzfj r1 = r20.zzlv()
            r1.cancel()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzmb():void");
    }

    /* access modifiers changed from: 0000 */
    public final void zzg(Runnable runnable) {
        zzaf();
        if (this.zzatu == null) {
            this.zzatu = new ArrayList();
        }
        this.zzatu.add(runnable);
    }

    private final void zzmc() {
        zzaf();
        if (this.zzatx || this.zzaty || this.zzatz) {
            this.zzada.zzgt().zzjo().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzatx), Boolean.valueOf(this.zzaty), Boolean.valueOf(this.zzatz));
            return;
        }
        this.zzada.zzgt().zzjo().zzby("Stopping uploading service(s)");
        List<Runnable> list = this.zzatu;
        if (list != null) {
            for (Runnable run : list) {
                run.run();
            }
            this.zzatu.clear();
        }
    }

    private final Boolean zzc(zzg zzg) {
        try {
            if (zzg.zzhf() != -2147483648L) {
                if (zzg.zzhf() == ((long) Wrappers.packageManager(this.zzada.getContext()).getPackageInfo(zzg.zzal(), 0).versionCode)) {
                    return Boolean.valueOf(true);
                }
            } else {
                String str = Wrappers.packageManager(this.zzada.getContext()).getPackageInfo(zzg.zzal(), 0).versionName;
                if (zzg.zzak() != null && zzg.zzak().equals(str)) {
                    return Boolean.valueOf(true);
                }
            }
            return Boolean.valueOf(false);
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    private final boolean zzmd() {
        zzaf();
        try {
            this.zzaub = new RandomAccessFile(new File(this.zzada.getContext().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzaua = this.zzaub.tryLock();
            if (this.zzaua != null) {
                this.zzada.zzgt().zzjo().zzby("Storage concurrent access okay");
                return true;
            }
            this.zzada.zzgt().zzjg().zzby("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            this.zzada.zzgt().zzjg().zzg("Failed to acquire storage lock", e);
        } catch (IOException e2) {
            this.zzada.zzgt().zzjg().zzg("Failed to access storage lock file", e2);
        }
    }

    private final int zza(FileChannel fileChannel) {
        zzaf();
        int i = 0;
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzada.zzgt().zzjg().zzby("Bad channel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0);
            int read = fileChannel.read(allocate);
            if (read != 4) {
                if (read != -1) {
                    this.zzada.zzgt().zzjj().zzg("Unexpected data length. Bytes read", Integer.valueOf(read));
                }
                return 0;
            }
            allocate.flip();
            i = allocate.getInt();
            return i;
        } catch (IOException e) {
            this.zzada.zzgt().zzjg().zzg("Failed to read from channel", e);
        }
    }

    private final boolean zza(int i, FileChannel fileChannel) {
        zzaf();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzada.zzgt().zzjg().zzby("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                this.zzada.zzgt().zzjg().zzg("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            this.zzada.zzgt().zzjg().zzg("Failed to write to channel", e);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzme() {
        zzaf();
        zzlx();
        if (!this.zzats) {
            this.zzats = true;
            zzaf();
            zzlx();
            if ((this.zzada.zzgv().zza(zzai.zzalf) || zzmf()) && zzmd()) {
                int zza2 = zza(this.zzaub);
                int zzjd = this.zzada.zzgk().zzjd();
                zzaf();
                if (zza2 > zzjd) {
                    this.zzada.zzgt().zzjg().zze("Panic: can't downgrade version. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzjd));
                } else if (zza2 < zzjd) {
                    if (zza(zzjd, this.zzaub)) {
                        this.zzada.zzgt().zzjo().zze("Storage version upgraded. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzjd));
                    } else {
                        this.zzada.zzgt().zzjg().zze("Storage version upgrade failed. Previous, current version", Integer.valueOf(zza2), Integer.valueOf(zzjd));
                    }
                }
            }
        }
        if (!this.zzatr && !this.zzada.zzgv().zza(zzai.zzalf)) {
            this.zzada.zzgt().zzjm().zzby("This instance being marked as an uploader");
            this.zzatr = true;
            zzmb();
        }
    }

    private final boolean zzmf() {
        zzaf();
        zzlx();
        return this.zzatr;
    }

    /* access modifiers changed from: 0000 */
    public final void zzd(zzk zzk) {
        String str = "app_id=?";
        if (this.zzauc != null) {
            this.zzaud = new ArrayList();
            this.zzaud.addAll(this.zzauc);
        }
        zzt zzjt = zzjt();
        String str2 = zzk.packageName;
        Preconditions.checkNotEmpty(str2);
        zzjt.zzaf();
        zzjt.zzcl();
        try {
            SQLiteDatabase writableDatabase = zzjt.getWritableDatabase();
            String[] strArr = {str2};
            int delete = writableDatabase.delete("apps", str, strArr) + 0 + writableDatabase.delete("events", str, strArr) + writableDatabase.delete("user_attributes", str, strArr) + writableDatabase.delete("conditional_properties", str, strArr) + writableDatabase.delete("raw_events", str, strArr) + writableDatabase.delete("raw_events_metadata", str, strArr) + writableDatabase.delete("queue", str, strArr) + writableDatabase.delete("audience_filter_values", str, strArr) + writableDatabase.delete("main_event_params", str, strArr);
            if (delete > 0) {
                zzjt.zzgt().zzjo().zze("Reset analytics data. app, records", str2, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzjt.zzgt().zzjg().zze("Error resetting analytics data. appId, error", zzas.zzbw(str2), e);
        }
        zzk zza2 = zza(this.zzada.getContext(), zzk.packageName, zzk.zzafi, zzk.zzafr, zzk.zzaft, zzk.zzafu, zzk.zzago, zzk.zzafv);
        if (!this.zzada.zzgv().zzba(zzk.packageName) || zzk.zzafr) {
            zzf(zza2);
        }
    }

    private final zzk zza(Context context, String str, String str2, boolean z, boolean z2, boolean z3, long j, String str3) {
        String str4;
        String str5;
        int i;
        String str6 = str;
        String str7 = BinData.UNKNOWN;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            this.zzada.zzgt().zzjg().zzby("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str4 = packageManager.getInstallerPackageName(str6);
        } catch (IllegalArgumentException unused) {
            this.zzada.zzgt().zzjg().zzg("Error retrieving installer package name. appId", zzas.zzbw(str));
            str4 = str7;
        }
        if (str4 == null) {
            str4 = "manual_install";
        } else if ("com.android.vending".equals(str4)) {
            str4 = "";
        }
        String str8 = str4;
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str6, 0);
            if (packageInfo != null) {
                CharSequence applicationLabel = Wrappers.packageManager(context).getApplicationLabel(str6);
                if (!TextUtils.isEmpty(applicationLabel)) {
                    String charSequence = applicationLabel.toString();
                }
                String str9 = packageInfo.versionName;
                i = packageInfo.versionCode;
                str5 = str9;
            } else {
                i = Integer.MIN_VALUE;
                str5 = str7;
            }
            this.zzada.zzgw();
            zzk zzk = new zzk(str, str2, str5, (long) i, str8, this.zzada.zzgv().zzhh(), this.zzada.zzgr().zzd(context, str6), (String) null, z, false, "", 0, this.zzada.zzgv().zzbc(str6) ? j : 0, 0, z2, z3, false, str3);
            return zzk;
        } catch (NameNotFoundException unused2) {
            this.zzada.zzgt().zzjg().zze("Error retrieving newly installed package info. appId, appName", zzas.zzbw(str), str7);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(zzfu zzfu, zzk zzk) {
        zzaf();
        zzlx();
        if (TextUtils.isEmpty(zzk.zzafi) && TextUtils.isEmpty(zzk.zzafv)) {
            return;
        }
        if (!zzk.zzafr) {
            zzg(zzk);
            return;
        }
        int zzcv = this.zzada.zzgr().zzcv(zzfu.name);
        if (zzcv != 0) {
            this.zzada.zzgr();
            this.zzada.zzgr().zza(zzk.packageName, zzcv, "_ev", zzfx.zza(zzfu.name, 24, true), zzfu.name != null ? zzfu.name.length() : 0);
            return;
        }
        int zzi = this.zzada.zzgr().zzi(zzfu.name, zzfu.getValue());
        if (zzi != 0) {
            this.zzada.zzgr();
            String zza2 = zzfx.zza(zzfu.name, 24, true);
            Object value = zzfu.getValue();
            this.zzada.zzgr().zza(zzk.packageName, zzi, "_ev", zza2, (value == null || (!(value instanceof String) && !(value instanceof CharSequence))) ? 0 : String.valueOf(value).length());
            return;
        }
        Object zzj = this.zzada.zzgr().zzj(zzfu.name, zzfu.getValue());
        if (zzj != null) {
            if (this.zzada.zzgv().zzbh(zzk.packageName)) {
                String str = "_sno";
                if (str.equals(zzfu.name)) {
                    long j = 0;
                    zzfw zzi2 = zzjt().zzi(zzk.packageName, str);
                    if (zzi2 == null || !(zzi2.value instanceof Long)) {
                        zzac zzg = zzjt().zzg(zzk.packageName, "_s");
                        if (zzg != null) {
                            j = zzg.zzahv;
                            this.zzada.zzgt().zzjo().zzg("Backfill the session number. Last used session number", Long.valueOf(j));
                        }
                    } else {
                        j = ((Long) zzi2.value).longValue();
                    }
                    zzj = Long.valueOf(j + 1);
                }
            }
            zzfw zzfw = new zzfw(zzk.packageName, zzfu.origin, zzfu.name, zzfu.zzaum, zzj);
            this.zzada.zzgt().zzjn().zze("Setting user property", this.zzada.zzgq().zzbv(zzfw.name), zzj);
            zzjt().beginTransaction();
            try {
                zzg(zzk);
                boolean zza3 = zzjt().zza(zzfw);
                zzjt().setTransactionSuccessful();
                if (zza3) {
                    this.zzada.zzgt().zzjn().zze("User property set", this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                } else {
                    this.zzada.zzgt().zzjg().zze("Too many unique user properties are set. Ignoring user property", this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                    this.zzada.zzgr().zza(zzk.packageName, 9, (String) null, (String) null, 0);
                }
            } finally {
                zzjt().endTransaction();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(zzfu zzfu, zzk zzk) {
        zzaf();
        zzlx();
        if (TextUtils.isEmpty(zzk.zzafi) && TextUtils.isEmpty(zzk.zzafv)) {
            return;
        }
        if (!zzk.zzafr) {
            zzg(zzk);
            return;
        }
        this.zzada.zzgt().zzjn().zzg("Removing user property", this.zzada.zzgq().zzbv(zzfu.name));
        zzjt().beginTransaction();
        try {
            zzg(zzk);
            zzjt().zzh(zzk.packageName, zzfu.name);
            zzjt().setTransactionSuccessful();
            this.zzada.zzgt().zzjn().zzg("User property removed", this.zzada.zzgq().zzbv(zzfu.name));
        } finally {
            zzjt().endTransaction();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(zzfm zzfm) {
        this.zzatv++;
    }

    /* access modifiers changed from: 0000 */
    public final void zzmg() {
        this.zzatw++;
    }

    /* access modifiers changed from: 0000 */
    public final zzbw zzmh() {
        return this.zzada;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0332 A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x035f A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x03eb A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x041b A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01d2 A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01df A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01f1 A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x02d4 A[Catch:{ SQLiteException -> 0x0147, all -> 0x0449 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzf(com.google.android.gms.measurement.internal.zzk r22) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            java.lang.String r3 = "_sysu"
            java.lang.String r4 = "_sys"
            java.lang.String r5 = "_pfo"
            java.lang.String r6 = "_uwa"
            java.lang.String r0 = "app_id=?"
            r21.zzaf()
            r21.zzlx()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r22)
            java.lang.String r7 = r2.packageName
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r7)
            java.lang.String r7 = r2.zzafi
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto L_0x002d
            java.lang.String r7 = r2.zzafv
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto L_0x002d
            return
        L_0x002d:
            com.google.android.gms.measurement.internal.zzt r7 = r21.zzjt()
            java.lang.String r8 = r2.packageName
            com.google.android.gms.measurement.internal.zzg r7 = r7.zzbm(r8)
            r8 = 0
            if (r7 == 0) goto L_0x0060
            java.lang.String r10 = r7.getGmpAppId()
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 == 0) goto L_0x0060
            java.lang.String r10 = r2.zzafi
            boolean r10 = android.text.TextUtils.isEmpty(r10)
            if (r10 != 0) goto L_0x0060
            r7.zzu(r8)
            com.google.android.gms.measurement.internal.zzt r10 = r21.zzjt()
            r10.zza(r7)
            com.google.android.gms.measurement.internal.zzbq r7 = r21.zzls()
            java.lang.String r10 = r2.packageName
            r7.zzcj(r10)
        L_0x0060:
            boolean r7 = r2.zzafr
            if (r7 != 0) goto L_0x0068
            r21.zzg(r22)
            return
        L_0x0068:
            long r10 = r2.zzago
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 != 0) goto L_0x0078
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada
            com.google.android.gms.common.util.Clock r7 = r7.zzbx()
            long r10 = r7.currentTimeMillis()
        L_0x0078:
            int r7 = r2.zzagp
            r14 = 1
            if (r7 == 0) goto L_0x0099
            if (r7 == r14) goto L_0x0099
            com.google.android.gms.measurement.internal.zzbw r12 = r1.zzada
            com.google.android.gms.measurement.internal.zzas r12 = r12.zzgt()
            com.google.android.gms.measurement.internal.zzau r12 = r12.zzjj()
            java.lang.String r13 = r2.packageName
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            java.lang.String r8 = "Incorrect app type, assuming installed app. appId, appType"
            r12.zze(r8, r13, r7)
            r7 = 0
        L_0x0099:
            com.google.android.gms.measurement.internal.zzt r8 = r21.zzjt()
            r8.beginTransaction()
            com.google.android.gms.measurement.internal.zzt r8 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            java.lang.String r9 = r2.packageName     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzg r8 = r8.zzbm(r9)     // Catch:{ all -> 0x0449 }
            if (r8 == 0) goto L_0x015a
            com.google.android.gms.measurement.internal.zzbw r12 = r1.zzada     // Catch:{ all -> 0x0449 }
            r12.zzgr()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = r2.zzafi     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = r8.getGmpAppId()     // Catch:{ all -> 0x0449 }
            java.lang.String r9 = r2.zzafv     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = r8.zzhb()     // Catch:{ all -> 0x0449 }
            boolean r9 = com.google.android.gms.measurement.internal.zzfx.zza(r12, r13, r9, r15)     // Catch:{ all -> 0x0449 }
            if (r9 == 0) goto L_0x015a
            com.google.android.gms.measurement.internal.zzbw r9 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzas r9 = r9.zzgt()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjj()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = "New GMP App Id passed in. Removing cached database data. appId"
            java.lang.String r13 = r8.zzal()     // Catch:{ all -> 0x0449 }
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ all -> 0x0449 }
            r9.zzg(r12, r13)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzt r9 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = r8.zzal()     // Catch:{ all -> 0x0449 }
            r9.zzcl()     // Catch:{ all -> 0x0449 }
            r9.zzaf()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r8)     // Catch:{ all -> 0x0449 }
            android.database.sqlite.SQLiteDatabase r12 = r9.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0147 }
            java.lang.String[] r13 = new java.lang.String[r14]     // Catch:{ SQLiteException -> 0x0147 }
            r15 = 0
            r13[r15] = r8     // Catch:{ SQLiteException -> 0x0147 }
            java.lang.String r14 = "events"
            int r14 = r12.delete(r14, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "user_attributes"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "conditional_properties"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "apps"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "raw_events"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "raw_events_metadata"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "event_filters"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "property_filters"
            int r15 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r15
            java.lang.String r15 = "audience_filter_values"
            int r0 = r12.delete(r15, r0, r13)     // Catch:{ SQLiteException -> 0x0147 }
            int r14 = r14 + r0
            if (r14 <= 0) goto L_0x0159
            com.google.android.gms.measurement.internal.zzas r0 = r9.zzgt()     // Catch:{ SQLiteException -> 0x0147 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjo()     // Catch:{ SQLiteException -> 0x0147 }
            java.lang.String r12 = "Deleted application data. app, records"
            java.lang.Integer r13 = java.lang.Integer.valueOf(r14)     // Catch:{ SQLiteException -> 0x0147 }
            r0.zze(r12, r8, r13)     // Catch:{ SQLiteException -> 0x0147 }
            goto L_0x0159
        L_0x0147:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzas r9 = r9.zzgt()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = "Error deleting application data. appId, error"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r8)     // Catch:{ all -> 0x0449 }
            r9.zze(r12, r8, r0)     // Catch:{ all -> 0x0449 }
        L_0x0159:
            r8 = 0
        L_0x015a:
            if (r8 == 0) goto L_0x01cc
            long r12 = r8.zzhf()     // Catch:{ all -> 0x0449 }
            r14 = -2147483648(0xffffffff80000000, double:NaN)
            java.lang.String r0 = "_pv"
            int r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r9 == 0) goto L_0x0196
            long r12 = r8.zzhf()     // Catch:{ all -> 0x0449 }
            long r14 = r2.zzafo     // Catch:{ all -> 0x0449 }
            int r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r9 == 0) goto L_0x01cc
            android.os.Bundle r9 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r9.<init>()     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = r8.zzak()     // Catch:{ all -> 0x0449 }
            r9.putString(r0, r8)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzag r0 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_au"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r9)     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = "auto"
            r12 = r0
            r9 = 1
            r8 = 0
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r0, r2)     // Catch:{ all -> 0x0449 }
            goto L_0x01cd
        L_0x0196:
            r9 = 1
            r15 = 0
            java.lang.String r12 = r8.zzak()     // Catch:{ all -> 0x0449 }
            if (r12 == 0) goto L_0x01cd
            java.lang.String r12 = r8.zzak()     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = r2.zzts     // Catch:{ all -> 0x0449 }
            boolean r12 = r12.equals(r13)     // Catch:{ all -> 0x0449 }
            if (r12 != 0) goto L_0x01cd
            android.os.Bundle r12 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r12.<init>()     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = r8.zzak()     // Catch:{ all -> 0x0449 }
            r12.putString(r0, r8)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzag r0 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_au"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r12)     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = "auto"
            r12 = r0
            r15 = r8
            r16 = r10
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r0, r2)     // Catch:{ all -> 0x0449 }
            goto L_0x01cd
        L_0x01cc:
            r9 = 1
        L_0x01cd:
            r21.zzg(r22)     // Catch:{ all -> 0x0449 }
            if (r7 != 0) goto L_0x01df
            com.google.android.gms.measurement.internal.zzt r0 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = r2.packageName     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = "_f"
            com.google.android.gms.measurement.internal.zzac r0 = r0.zzg(r8, r12)     // Catch:{ all -> 0x0449 }
            goto L_0x01ef
        L_0x01df:
            if (r7 != r9) goto L_0x01ee
            com.google.android.gms.measurement.internal.zzt r0 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            java.lang.String r8 = r2.packageName     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = "_v"
            com.google.android.gms.measurement.internal.zzac r0 = r0.zzg(r8, r12)     // Catch:{ all -> 0x0449 }
            goto L_0x01ef
        L_0x01ee:
            r0 = 0
        L_0x01ef:
            if (r0 != 0) goto L_0x041b
            r12 = 3600000(0x36ee80, double:1.7786363E-317)
            long r14 = r10 / r12
            r18 = r10
            r9 = 1
            long r14 = r14 + r9
            long r14 = r14 * r12
            java.lang.String r0 = "_r"
            java.lang.String r11 = "_c"
            java.lang.String r13 = "_et"
            if (r7 != 0) goto L_0x0377
            com.google.android.gms.measurement.internal.zzfu r7 = new com.google.android.gms.measurement.internal.zzfu     // Catch:{ all -> 0x0449 }
            java.lang.String r16 = "_fot"
            java.lang.Long r17 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x0449 }
            java.lang.String r20 = "auto"
            r12 = r7
            r14 = r13
            r13 = r16
            r8 = r14
            r14 = r18
            r16 = r17
            r17 = r20
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x0449 }
            r1.zzb(r7, r2)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r7 = r7.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = r2.zzafi     // Catch:{ all -> 0x0449 }
            boolean r7 = r7.zzbe(r12)     // Catch:{ all -> 0x0449 }
            if (r7 == 0) goto L_0x023c
            r21.zzaf()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbw r7 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbj r7 = r7.zzkk()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = r2.packageName     // Catch:{ all -> 0x0449 }
            r7.zzce(r12)     // Catch:{ all -> 0x0449 }
        L_0x023c:
            r21.zzaf()     // Catch:{ all -> 0x0449 }
            r21.zzlx()     // Catch:{ all -> 0x0449 }
            android.os.Bundle r7 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r7.<init>()     // Catch:{ all -> 0x0449 }
            r7.putLong(r11, r9)     // Catch:{ all -> 0x0449 }
            r7.putLong(r0, r9)     // Catch:{ all -> 0x0449 }
            r11 = 0
            r7.putLong(r6, r11)     // Catch:{ all -> 0x0449 }
            r7.putLong(r5, r11)     // Catch:{ all -> 0x0449 }
            r7.putLong(r4, r11)     // Catch:{ all -> 0x0449 }
            r7.putLong(r3, r11)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r11 = r2.packageName     // Catch:{ all -> 0x0449 }
            boolean r0 = r0.zzbk(r11)     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x026c
            r7.putLong(r8, r9)     // Catch:{ all -> 0x0449 }
        L_0x026c:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r11 = r2.packageName     // Catch:{ all -> 0x0449 }
            boolean r0 = r0.zzba(r11)     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x0283
            boolean r0 = r2.zzagq     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x0283
            java.lang.String r0 = "_dac"
            r7.putLong(r0, r9)     // Catch:{ all -> 0x0449 }
        L_0x0283:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            android.content.Context r0 = r0.getContext()     // Catch:{ all -> 0x0449 }
            android.content.pm.PackageManager r0 = r0.getPackageManager()     // Catch:{ all -> 0x0449 }
            if (r0 != 0) goto L_0x02a6
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzas r0 = r0.zzgt()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x0449 }
            java.lang.String r3 = "PackageManager is null, first open report might be inaccurate. appId"
            java.lang.String r4 = r2.packageName     // Catch:{ all -> 0x0449 }
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r4)     // Catch:{ all -> 0x0449 }
            r0.zzg(r3, r4)     // Catch:{ all -> 0x0449 }
            goto L_0x0344
        L_0x02a6:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ NameNotFoundException -> 0x02ba }
            android.content.Context r0 = r0.getContext()     // Catch:{ NameNotFoundException -> 0x02ba }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x02ba }
            java.lang.String r11 = r2.packageName     // Catch:{ NameNotFoundException -> 0x02ba }
            r14 = 0
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r11, r14)     // Catch:{ NameNotFoundException -> 0x02b8 }
            goto L_0x02d2
        L_0x02b8:
            r0 = move-exception
            goto L_0x02bc
        L_0x02ba:
            r0 = move-exception
            r14 = 0
        L_0x02bc:
            com.google.android.gms.measurement.internal.zzbw r11 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzas r11 = r11.zzgt()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzau r11 = r11.zzjg()     // Catch:{ all -> 0x0449 }
            java.lang.String r12 = "Package info is null, first open report might be inaccurate. appId"
            java.lang.String r13 = r2.packageName     // Catch:{ all -> 0x0449 }
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ all -> 0x0449 }
            r11.zze(r12, r13, r0)     // Catch:{ all -> 0x0449 }
            r0 = 0
        L_0x02d2:
            if (r0 == 0) goto L_0x0307
            long r11 = r0.firstInstallTime     // Catch:{ all -> 0x0449 }
            r15 = 0
            int r13 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r13 == 0) goto L_0x0307
            long r11 = r0.firstInstallTime     // Catch:{ all -> 0x0449 }
            long r14 = r0.lastUpdateTime     // Catch:{ all -> 0x0449 }
            int r0 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r0 == 0) goto L_0x02e9
            r7.putLong(r6, r9)     // Catch:{ all -> 0x0449 }
            r0 = 0
            goto L_0x02ea
        L_0x02e9:
            r0 = 1
        L_0x02ea:
            com.google.android.gms.measurement.internal.zzfu r6 = new com.google.android.gms.measurement.internal.zzfu     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_fi"
            if (r0 == 0) goto L_0x02f2
            r11 = r9
            goto L_0x02f4
        L_0x02f2:
            r11 = 0
        L_0x02f4:
            java.lang.Long r0 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0449 }
            java.lang.String r17 = "auto"
            r12 = r6
            r11 = 0
            r14 = r18
            r16 = r0
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x0449 }
            r1.zzb(r6, r2)     // Catch:{ all -> 0x0449 }
            goto L_0x0308
        L_0x0307:
            r11 = 0
        L_0x0308:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ NameNotFoundException -> 0x0319 }
            android.content.Context r0 = r0.getContext()     // Catch:{ NameNotFoundException -> 0x0319 }
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch:{ NameNotFoundException -> 0x0319 }
            java.lang.String r6 = r2.packageName     // Catch:{ NameNotFoundException -> 0x0319 }
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r6, r11)     // Catch:{ NameNotFoundException -> 0x0319 }
            goto L_0x0330
        L_0x0319:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzbw r6 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzas r6 = r6.zzgt()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzau r6 = r6.zzjg()     // Catch:{ all -> 0x0449 }
            java.lang.String r11 = "Application info is null, first open report might be inaccurate. appId"
            java.lang.String r12 = r2.packageName     // Catch:{ all -> 0x0449 }
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzas.zzbw(r12)     // Catch:{ all -> 0x0449 }
            r6.zze(r11, r12, r0)     // Catch:{ all -> 0x0449 }
            r0 = 0
        L_0x0330:
            if (r0 == 0) goto L_0x0344
            int r6 = r0.flags     // Catch:{ all -> 0x0449 }
            r11 = 1
            r6 = r6 & r11
            if (r6 == 0) goto L_0x033b
            r7.putLong(r4, r9)     // Catch:{ all -> 0x0449 }
        L_0x033b:
            int r0 = r0.flags     // Catch:{ all -> 0x0449 }
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x0344
            r7.putLong(r3, r9)     // Catch:{ all -> 0x0449 }
        L_0x0344:
            com.google.android.gms.measurement.internal.zzt r0 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            java.lang.String r3 = r2.packageName     // Catch:{ all -> 0x0449 }
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r3)     // Catch:{ all -> 0x0449 }
            r0.zzaf()     // Catch:{ all -> 0x0449 }
            r0.zzcl()     // Catch:{ all -> 0x0449 }
            java.lang.String r4 = "first_open_count"
            long r3 = r0.zzn(r3, r4)     // Catch:{ all -> 0x0449 }
            r11 = 0
            int r0 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1))
            if (r0 < 0) goto L_0x0362
            r7.putLong(r5, r3)     // Catch:{ all -> 0x0449 }
        L_0x0362:
            com.google.android.gms.measurement.internal.zzag r0 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_f"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r7)     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = "auto"
            r12 = r0
            r16 = r18
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r0, r2)     // Catch:{ all -> 0x0449 }
            goto L_0x03db
        L_0x0377:
            r8 = r13
            r3 = 1
            if (r7 != r3) goto L_0x03db
            com.google.android.gms.measurement.internal.zzfu r3 = new com.google.android.gms.measurement.internal.zzfu     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_fvt"
            java.lang.Long r16 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x0449 }
            java.lang.String r17 = "auto"
            r12 = r3
            r14 = r18
            r12.<init>(r13, r14, r16, r17)     // Catch:{ all -> 0x0449 }
            r1.zzb(r3, r2)     // Catch:{ all -> 0x0449 }
            r21.zzaf()     // Catch:{ all -> 0x0449 }
            r21.zzlx()     // Catch:{ all -> 0x0449 }
            android.os.Bundle r3 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r3.<init>()     // Catch:{ all -> 0x0449 }
            r3.putLong(r11, r9)     // Catch:{ all -> 0x0449 }
            r3.putLong(r0, r9)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r4 = r2.packageName     // Catch:{ all -> 0x0449 }
            boolean r0 = r0.zzbk(r4)     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x03b0
            r3.putLong(r8, r9)     // Catch:{ all -> 0x0449 }
        L_0x03b0:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r4 = r2.packageName     // Catch:{ all -> 0x0449 }
            boolean r0 = r0.zzba(r4)     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x03c7
            boolean r0 = r2.zzagq     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x03c7
            java.lang.String r0 = "_dac"
            r3.putLong(r0, r9)     // Catch:{ all -> 0x0449 }
        L_0x03c7:
            com.google.android.gms.measurement.internal.zzag r0 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_v"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r3)     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = "auto"
            r12 = r0
            r16 = r18
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r0, r2)     // Catch:{ all -> 0x0449 }
        L_0x03db:
            com.google.android.gms.measurement.internal.zzbw r0 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r0 = r0.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r3 = r2.packageName     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzai$zza<java.lang.Boolean> r4 = com.google.android.gms.measurement.internal.zzai.zzalc     // Catch:{ all -> 0x0449 }
            boolean r0 = r0.zze(r3, r4)     // Catch:{ all -> 0x0449 }
            if (r0 != 0) goto L_0x043a
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r0.<init>()     // Catch:{ all -> 0x0449 }
            r0.putLong(r8, r9)     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzbw r3 = r1.zzada     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzq r3 = r3.zzgv()     // Catch:{ all -> 0x0449 }
            java.lang.String r4 = r2.packageName     // Catch:{ all -> 0x0449 }
            boolean r3 = r3.zzbk(r4)     // Catch:{ all -> 0x0449 }
            if (r3 == 0) goto L_0x0406
            java.lang.String r3 = "_fr"
            r0.putLong(r3, r9)     // Catch:{ all -> 0x0449 }
        L_0x0406:
            com.google.android.gms.measurement.internal.zzag r3 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_e"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r0)     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = "auto"
            r12 = r3
            r16 = r18
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r3, r2)     // Catch:{ all -> 0x0449 }
            goto L_0x043a
        L_0x041b:
            r18 = r10
            boolean r0 = r2.zzagn     // Catch:{ all -> 0x0449 }
            if (r0 == 0) goto L_0x043a
            android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0449 }
            r0.<init>()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzag r3 = new com.google.android.gms.measurement.internal.zzag     // Catch:{ all -> 0x0449 }
            java.lang.String r13 = "_cd"
            com.google.android.gms.measurement.internal.zzad r14 = new com.google.android.gms.measurement.internal.zzad     // Catch:{ all -> 0x0449 }
            r14.<init>(r0)     // Catch:{ all -> 0x0449 }
            java.lang.String r15 = "auto"
            r12 = r3
            r16 = r18
            r12.<init>(r13, r14, r15, r16)     // Catch:{ all -> 0x0449 }
            r1.zzc(r3, r2)     // Catch:{ all -> 0x0449 }
        L_0x043a:
            com.google.android.gms.measurement.internal.zzt r0 = r21.zzjt()     // Catch:{ all -> 0x0449 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0449 }
            com.google.android.gms.measurement.internal.zzt r0 = r21.zzjt()
            r0.endTransaction()
            return
        L_0x0449:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzt r2 = r21.zzjt()
            r2.endTransaction()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzf(com.google.android.gms.measurement.internal.zzk):void");
    }

    private final zzk zzcr(String str) {
        String str2 = str;
        zzg zzbm = zzjt().zzbm(str2);
        if (zzbm == null || TextUtils.isEmpty(zzbm.zzak())) {
            this.zzada.zzgt().zzjn().zzg("No app data available; dropping", str2);
            return null;
        }
        Boolean zzc = zzc(zzbm);
        if (zzc == null || zzc.booleanValue()) {
            zzg zzg = zzbm;
            zzk zzk = new zzk(str, zzbm.getGmpAppId(), zzbm.zzak(), zzbm.zzhf(), zzbm.zzhg(), zzbm.zzhh(), zzbm.zzhi(), (String) null, zzbm.isMeasurementEnabled(), false, zzbm.getFirebaseInstanceId(), zzg.zzhv(), 0, 0, zzg.zzhw(), zzg.zzhx(), false, zzg.zzhb());
            return zzk;
        }
        this.zzada.zzgt().zzjg().zzg("App version does not match; dropping. appId", zzas.zzbw(str));
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final void zze(zzo zzo) {
        zzk zzcr = zzcr(zzo.packageName);
        if (zzcr != null) {
            zzb(zzo, zzcr);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(zzo zzo, zzk zzk) {
        Preconditions.checkNotNull(zzo);
        Preconditions.checkNotEmpty(zzo.packageName);
        Preconditions.checkNotNull(zzo.origin);
        Preconditions.checkNotNull(zzo.zzags);
        Preconditions.checkNotEmpty(zzo.zzags.name);
        zzaf();
        zzlx();
        if (TextUtils.isEmpty(zzk.zzafi) && TextUtils.isEmpty(zzk.zzafv)) {
            return;
        }
        if (!zzk.zzafr) {
            zzg(zzk);
            return;
        }
        zzo zzo2 = new zzo(zzo);
        boolean z = false;
        zzo2.active = false;
        zzjt().beginTransaction();
        try {
            zzo zzj = zzjt().zzj(zzo2.packageName, zzo2.zzags.name);
            if (zzj != null && !zzj.origin.equals(zzo2.origin)) {
                this.zzada.zzgt().zzjj().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzada.zzgq().zzbv(zzo2.zzags.name), zzo2.origin, zzj.origin);
            }
            if (zzj != null && zzj.active) {
                zzo2.origin = zzj.origin;
                zzo2.creationTimestamp = zzj.creationTimestamp;
                zzo2.triggerTimeout = zzj.triggerTimeout;
                zzo2.triggerEventName = zzj.triggerEventName;
                zzo2.zzagu = zzj.zzagu;
                zzo2.active = zzj.active;
                zzfu zzfu = new zzfu(zzo2.zzags.name, zzj.zzags.zzaum, zzo2.zzags.getValue(), zzj.zzags.origin);
                zzo2.zzags = zzfu;
            } else if (TextUtils.isEmpty(zzo2.triggerEventName)) {
                zzfu zzfu2 = new zzfu(zzo2.zzags.name, zzo2.creationTimestamp, zzo2.zzags.getValue(), zzo2.zzags.origin);
                zzo2.zzags = zzfu2;
                zzo2.active = true;
                z = true;
            }
            if (zzo2.active) {
                zzfu zzfu3 = zzo2.zzags;
                zzfw zzfw = new zzfw(zzo2.packageName, zzo2.origin, zzfu3.name, zzfu3.zzaum, zzfu3.getValue());
                if (zzjt().zza(zzfw)) {
                    this.zzada.zzgt().zzjn().zzd("User property updated immediately", zzo2.packageName, this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                } else {
                    this.zzada.zzgt().zzjg().zzd("(2)Too many active user properties, ignoring", zzas.zzbw(zzo2.packageName), this.zzada.zzgq().zzbv(zzfw.name), zzfw.value);
                }
                if (z && zzo2.zzagu != null) {
                    zzd(new zzag(zzo2.zzagu, zzo2.creationTimestamp), zzk);
                }
            }
            if (zzjt().zza(zzo2)) {
                this.zzada.zzgt().zzjn().zzd("Conditional property added", zzo2.packageName, this.zzada.zzgq().zzbv(zzo2.zzags.name), zzo2.zzags.getValue());
            } else {
                this.zzada.zzgt().zzjg().zzd("Too many conditional properties, ignoring", zzas.zzbw(zzo2.packageName), this.zzada.zzgq().zzbv(zzo2.zzags.name), zzo2.zzags.getValue());
            }
            zzjt().setTransactionSuccessful();
        } finally {
            zzjt().endTransaction();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzf(zzo zzo) {
        zzk zzcr = zzcr(zzo.packageName);
        if (zzcr != null) {
            zzc(zzo, zzcr);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(zzo zzo, zzk zzk) {
        Preconditions.checkNotNull(zzo);
        Preconditions.checkNotEmpty(zzo.packageName);
        Preconditions.checkNotNull(zzo.zzags);
        Preconditions.checkNotEmpty(zzo.zzags.name);
        zzaf();
        zzlx();
        if (TextUtils.isEmpty(zzk.zzafi) && TextUtils.isEmpty(zzk.zzafv)) {
            return;
        }
        if (!zzk.zzafr) {
            zzg(zzk);
            return;
        }
        zzjt().beginTransaction();
        try {
            zzg(zzk);
            zzo zzj = zzjt().zzj(zzo.packageName, zzo.zzags.name);
            if (zzj != null) {
                this.zzada.zzgt().zzjn().zze("Removing conditional user property", zzo.packageName, this.zzada.zzgq().zzbv(zzo.zzags.name));
                zzjt().zzk(zzo.packageName, zzo.zzags.name);
                if (zzj.active) {
                    zzjt().zzh(zzo.packageName, zzo.zzags.name);
                }
                if (zzo.zzagv != null) {
                    Bundle bundle = null;
                    if (zzo.zzagv.zzahu != null) {
                        bundle = zzo.zzagv.zzahu.zziy();
                    }
                    Bundle bundle2 = bundle;
                    zzd(this.zzada.zzgr().zza(zzo.packageName, zzo.zzagv.name, bundle2, zzj.origin, zzo.zzagv.zzaig, true, false), zzk);
                }
            } else {
                this.zzada.zzgt().zzjj().zze("Conditional user property doesn't exist", zzas.zzbw(zzo.packageName), this.zzada.zzgq().zzbv(zzo.zzags.name));
            }
            zzjt().setTransactionSuccessful();
        } finally {
            zzjt().endTransaction();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x015a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzg zzg(com.google.android.gms.measurement.internal.zzk r9) {
        /*
            r8 = this;
            r8.zzaf()
            r8.zzlx()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r9)
            java.lang.String r0 = r9.packageName
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r0)
            com.google.android.gms.measurement.internal.zzt r0 = r8.zzjt()
            java.lang.String r1 = r9.packageName
            com.google.android.gms.measurement.internal.zzg r0 = r0.zzbm(r1)
            com.google.android.gms.measurement.internal.zzbw r1 = r8.zzada
            com.google.android.gms.measurement.internal.zzbd r1 = r1.zzgu()
            java.lang.String r2 = r9.packageName
            java.lang.String r1 = r1.zzca(r2)
            r2 = 1
            if (r0 != 0) goto L_0x0042
            com.google.android.gms.measurement.internal.zzg r0 = new com.google.android.gms.measurement.internal.zzg
            com.google.android.gms.measurement.internal.zzbw r3 = r8.zzada
            java.lang.String r4 = r9.packageName
            r0.<init>(r3, r4)
            com.google.android.gms.measurement.internal.zzbw r3 = r8.zzada
            com.google.android.gms.measurement.internal.zzfx r3 = r3.zzgr()
            java.lang.String r3 = r3.zzmm()
            r0.zzaj(r3)
            r0.zzam(r1)
        L_0x0040:
            r1 = 1
            goto L_0x005e
        L_0x0042:
            java.lang.String r3 = r0.zzhc()
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x005d
            r0.zzam(r1)
            com.google.android.gms.measurement.internal.zzbw r1 = r8.zzada
            com.google.android.gms.measurement.internal.zzfx r1 = r1.zzgr()
            java.lang.String r1 = r1.zzmm()
            r0.zzaj(r1)
            goto L_0x0040
        L_0x005d:
            r1 = 0
        L_0x005e:
            java.lang.String r3 = r9.zzafi
            java.lang.String r4 = r0.getGmpAppId()
            boolean r3 = android.text.TextUtils.equals(r3, r4)
            if (r3 != 0) goto L_0x0070
            java.lang.String r1 = r9.zzafi
            r0.zzak(r1)
            r1 = 1
        L_0x0070:
            java.lang.String r3 = r9.zzafv
            java.lang.String r4 = r0.zzhb()
            boolean r3 = android.text.TextUtils.equals(r3, r4)
            if (r3 != 0) goto L_0x0082
            java.lang.String r1 = r9.zzafv
            r0.zzal(r1)
            r1 = 1
        L_0x0082:
            java.lang.String r3 = r9.zzafk
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x009c
            java.lang.String r3 = r9.zzafk
            java.lang.String r4 = r0.getFirebaseInstanceId()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x009c
            java.lang.String r1 = r9.zzafk
            r0.zzan(r1)
            r1 = 1
        L_0x009c:
            long r3 = r9.zzade
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x00b4
            long r3 = r9.zzade
            long r5 = r0.zzhh()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x00b4
            long r3 = r9.zzade
            r0.zzr(r3)
            r1 = 1
        L_0x00b4:
            java.lang.String r3 = r9.zzts
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00ce
            java.lang.String r3 = r9.zzts
            java.lang.String r4 = r0.zzak()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x00ce
            java.lang.String r1 = r9.zzts
            r0.setAppVersion(r1)
            r1 = 1
        L_0x00ce:
            long r3 = r9.zzafo
            long r5 = r0.zzhf()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x00de
            long r3 = r9.zzafo
            r0.zzq(r3)
            r1 = 1
        L_0x00de:
            java.lang.String r3 = r9.zzafp
            if (r3 == 0) goto L_0x00f4
            java.lang.String r3 = r9.zzafp
            java.lang.String r4 = r0.zzhg()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x00f4
            java.lang.String r1 = r9.zzafp
            r0.zzao(r1)
            r1 = 1
        L_0x00f4:
            long r3 = r9.zzafq
            long r5 = r0.zzhi()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0104
            long r3 = r9.zzafq
            r0.zzs(r3)
            r1 = 1
        L_0x0104:
            boolean r3 = r9.zzafr
            boolean r4 = r0.isMeasurementEnabled()
            if (r3 == r4) goto L_0x0112
            boolean r1 = r9.zzafr
            r0.setMeasurementEnabled(r1)
            r1 = 1
        L_0x0112:
            java.lang.String r3 = r9.zzagm
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x012c
            java.lang.String r3 = r9.zzagm
            java.lang.String r4 = r0.zzht()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x012c
            java.lang.String r1 = r9.zzagm
            r0.zzap(r1)
            r1 = 1
        L_0x012c:
            long r3 = r9.zzafs
            long r5 = r0.zzhv()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x013c
            long r3 = r9.zzafs
            r0.zzac(r3)
            r1 = 1
        L_0x013c:
            boolean r3 = r9.zzaft
            boolean r4 = r0.zzhw()
            if (r3 == r4) goto L_0x014a
            boolean r1 = r9.zzaft
            r0.zze(r1)
            r1 = 1
        L_0x014a:
            boolean r3 = r9.zzafu
            boolean r4 = r0.zzhx()
            if (r3 == r4) goto L_0x0158
            boolean r9 = r9.zzafu
            r0.zzf(r9)
            r1 = 1
        L_0x0158:
            if (r1 == 0) goto L_0x0161
            com.google.android.gms.measurement.internal.zzt r9 = r8.zzjt()
            r9.zza(r0)
        L_0x0161:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfn.zzg(com.google.android.gms.measurement.internal.zzk):com.google.android.gms.measurement.internal.zzg");
    }

    /* access modifiers changed from: 0000 */
    public final String zzh(zzk zzk) {
        try {
            return (String) this.zzada.zzgs().zzb((Callable<V>) new zzfr<V>(this, zzk)).get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.zzada.zzgt().zzjg().zze("Failed to get app instance id. appId", zzas.zzbw(zzk.packageName), e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzm(boolean z) {
        zzmb();
    }
}
