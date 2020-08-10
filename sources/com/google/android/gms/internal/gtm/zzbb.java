package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import com.mobiroller.constants.ChatConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzbb extends zzan {
    private boolean started;
    private final zzay zzxp;
    private final zzck zzxq;
    private final zzcj zzxr;
    private final zzat zzxs;
    private long zzxt = Long.MIN_VALUE;
    private final zzbs zzxu;
    private final zzbs zzxv;
    private final zzcv zzxw;
    private long zzxx;
    private boolean zzxy;

    protected zzbb(zzap zzap, zzar zzar) {
        super(zzap);
        Preconditions.checkNotNull(zzar);
        this.zzxr = new zzcj(zzap);
        this.zzxp = new zzay(zzap);
        this.zzxq = new zzck(zzap);
        this.zzxs = new zzat(zzap);
        this.zzxw = new zzcv(zzcn());
        this.zzxu = new zzbc(this, zzap);
        this.zzxv = new zzbd(this, zzap);
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
        this.zzxp.zzag();
        this.zzxq.zzag();
        this.zzxs.zzag();
    }

    /* access modifiers changed from: 0000 */
    public final void start() {
        zzdb();
        Preconditions.checkState(!this.started, "Analytics backend already started");
        this.started = true;
        zzcq().zza((Runnable) new zzbe(this));
    }

    private final boolean zzx(String str) {
        return Wrappers.packageManager(getContext()).checkCallingOrSelfPermission(str) == 0;
    }

    /* access modifiers changed from: protected */
    public final void zzdw() {
        zzdb();
        zzk.zzav();
        Context context = zzcm().getContext();
        if (!zzcp.zza(context)) {
            zzt("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzcq.zze(context)) {
            zzu("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zza(context)) {
            zzt("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzcv().zzfv();
        if (!zzx("android.permission.ACCESS_NETWORK_STATE")) {
            zzu("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzeg();
        }
        if (!zzx("android.permission.INTERNET")) {
            zzu("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzeg();
        }
        if (zzcq.zze(getContext())) {
            zzq("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzt("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzxy && !this.zzxp.isEmpty()) {
            zzdz();
        }
        zzec();
    }

    /* access modifiers changed from: private */
    public final void zzdx() {
        zzb((zzbw) new zzbf(this));
    }

    /* access modifiers changed from: 0000 */
    public final void zzcl() {
        zzk.zzav();
        this.zzxx = zzcn().currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0044 A[LOOP:1: B:15:0x0044->B:23:?, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0040 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onServiceConnected() {
        /*
            r5 = this;
            com.google.android.gms.analytics.zzk.zzav()
            com.google.android.gms.analytics.zzk.zzav()
            r5.zzdb()
            boolean r0 = com.google.android.gms.internal.gtm.zzbq.zzen()
            if (r0 != 0) goto L_0x0014
            java.lang.String r0 = "Service client disabled. Can't dispatch local hits to device AnalyticsService"
            r5.zzt(r0)
        L_0x0014:
            com.google.android.gms.internal.gtm.zzat r0 = r5.zzxs
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Service not connected"
            r5.zzq(r0)
            return
        L_0x0022:
            com.google.android.gms.internal.gtm.zzay r0 = r5.zzxp
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x007d
            java.lang.String r0 = "Dispatching local hits to device AnalyticsService"
            r5.zzq(r0)
        L_0x002f:
            com.google.android.gms.internal.gtm.zzay r0 = r5.zzxp     // Catch:{ SQLiteException -> 0x0074 }
            int r1 = com.google.android.gms.internal.gtm.zzbq.zzer()     // Catch:{ SQLiteException -> 0x0074 }
            long r1 = (long) r1     // Catch:{ SQLiteException -> 0x0074 }
            java.util.List r0 = r0.zzd(r1)     // Catch:{ SQLiteException -> 0x0074 }
            boolean r1 = r0.isEmpty()     // Catch:{ SQLiteException -> 0x0074 }
            if (r1 == 0) goto L_0x0044
            r5.zzec()     // Catch:{ SQLiteException -> 0x0074 }
            return
        L_0x0044:
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x002f
            r1 = 0
            java.lang.Object r1 = r0.get(r1)
            com.google.android.gms.internal.gtm.zzcd r1 = (com.google.android.gms.internal.gtm.zzcd) r1
            com.google.android.gms.internal.gtm.zzat r2 = r5.zzxs
            boolean r2 = r2.zzb(r1)
            if (r2 != 0) goto L_0x005d
            r5.zzec()
            return
        L_0x005d:
            r0.remove(r1)
            com.google.android.gms.internal.gtm.zzay r2 = r5.zzxp     // Catch:{ SQLiteException -> 0x006a }
            long r3 = r1.zzfg()     // Catch:{ SQLiteException -> 0x006a }
            r2.zze(r3)     // Catch:{ SQLiteException -> 0x006a }
            goto L_0x0044
        L_0x006a:
            r0 = move-exception
            java.lang.String r1 = "Failed to remove hit that was send for delivery"
            r5.zze(r1, r0)
            r5.zzee()
            return
        L_0x0074:
            r0 = move-exception
            java.lang.String r1 = "Failed to read hits from store"
            r5.zze(r1, r0)
            r5.zzee()
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzbb.onServiceConnected():void");
    }

    /* access modifiers changed from: private */
    public final void zzdy() {
        try {
            this.zzxp.zzdr();
            zzec();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzxv.zzh(86400000);
    }

    /* access modifiers changed from: protected */
    public final void zzb(zzas zzas) {
        zzk.zzav();
        zzb("Sending first hit to property", zzas.zzdj());
        if (!zzcv().zzfw().zzj(zzbq.zzex())) {
            String zzfz = zzcv().zzfz();
            if (!TextUtils.isEmpty(zzfz)) {
                zzr zza = zzcz.zza(zzco(), zzfz);
                zzb("Found relevant installation campaign", zza);
                zza(zzas, zza);
            }
        }
    }

    public final void zzg(long j) {
        zzk.zzav();
        zzdb();
        if (j < 0) {
            j = 0;
        }
        this.zzxt = j;
        zzec();
    }

    private final void zzdz() {
        if (!this.zzxy && zzbq.zzen() && !this.zzxs.isConnected()) {
            if (this.zzxw.zzj(((Long) zzby.zzaan.get()).longValue())) {
                this.zzxw.start();
                zzq("Connecting to service");
                if (this.zzxs.connect()) {
                    zzq("Connected to service");
                    this.zzxw.clear();
                    onServiceConnected();
                }
            }
        }
    }

    public final long zza(zzas zzas, boolean z) {
        String str = "properties";
        String str2 = "Failed to end transaction";
        Preconditions.checkNotNull(zzas);
        zzdb();
        zzk.zzav();
        try {
            this.zzxp.beginTransaction();
            zzay zzay = this.zzxp;
            long zzdi = zzas.zzdi();
            String zzbt = zzas.zzbt();
            Preconditions.checkNotEmpty(zzbt);
            zzay.zzdb();
            zzk.zzav();
            int i = 1;
            int delete = zzay.getWritableDatabase().delete(str, "app_uid=? AND cid<>?", new String[]{String.valueOf(zzdi), zzbt});
            if (delete > 0) {
                zzay.zza("Deleted property records", Integer.valueOf(delete));
            }
            long zza = this.zzxp.zza(zzas.zzdi(), zzas.zzbt(), zzas.zzdj());
            zzas.zzb(1 + zza);
            zzay zzay2 = this.zzxp;
            Preconditions.checkNotNull(zzas);
            zzay2.zzdb();
            zzk.zzav();
            SQLiteDatabase writableDatabase = zzay2.getWritableDatabase();
            Map zzdm = zzas.zzdm();
            Preconditions.checkNotNull(zzdm);
            Builder builder = new Builder();
            for (Entry entry : zzdm.entrySet()) {
                builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            String encodedQuery = builder.build().getEncodedQuery();
            if (encodedQuery == null) {
                encodedQuery = "";
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_uid", Long.valueOf(zzas.zzdi()));
            contentValues.put("cid", zzas.zzbt());
            contentValues.put("tid", zzas.zzdj());
            String str3 = "adid";
            if (!zzas.zzdk()) {
                i = 0;
            }
            contentValues.put(str3, Integer.valueOf(i));
            contentValues.put("hits_count", Long.valueOf(zzas.zzdl()));
            contentValues.put("params", encodedQuery);
            try {
                if (writableDatabase.insertWithOnConflict(str, null, contentValues, 5) == -1) {
                    zzay2.zzu("Failed to insert/update a property (got -1)");
                }
            } catch (SQLiteException e) {
                zzay2.zze("Error storing a property", e);
            }
            this.zzxp.setTransactionSuccessful();
            try {
                this.zzxp.endTransaction();
            } catch (SQLiteException e2) {
                zze(str2, e2);
            }
            return zza;
        } catch (SQLiteException e3) {
            zze("Failed to update Analytics property", e3);
            try {
                this.zzxp.endTransaction();
            } catch (SQLiteException e4) {
                zze(str2, e4);
            }
            return -1;
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                this.zzxp.endTransaction();
            } catch (SQLiteException e5) {
                zze(str2, e5);
            }
            throw th2;
        }
    }

    public final void zza(zzcd zzcd) {
        Preconditions.checkNotNull(zzcd);
        zzk.zzav();
        zzdb();
        if (this.zzxy) {
            zzr("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzcd);
        }
        if (TextUtils.isEmpty(zzcd.zzfl())) {
            Pair zzgc = zzcv().zzga().zzgc();
            if (zzgc != null) {
                Long l = (Long) zzgc.second;
                String str = (String) zzgc.first;
                String valueOf = String.valueOf(l);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length());
                sb.append(valueOf);
                sb.append(":");
                sb.append(str);
                String sb2 = sb.toString();
                HashMap hashMap = new HashMap(zzcd.zzdm());
                hashMap.put("_m", sb2);
                zzcd zzcd2 = new zzcd(this, hashMap, zzcd.zzfh(), zzcd.zzfj(), zzcd.zzfg(), zzcd.zzff(), zzcd.zzfi());
                zzcd = zzcd2;
            }
        }
        zzdz();
        if (this.zzxs.zzb(zzcd)) {
            zzr("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzxp.zzc(zzcd);
            zzec();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzco().zza(zzcd, "deliver: failed to insert hit to database");
        }
    }

    public final void zzch() {
        zzk.zzav();
        zzdb();
        zzq("Delete all hits from local store");
        try {
            zzay zzay = this.zzxp;
            zzk.zzav();
            zzay.zzdb();
            zzay.getWritableDatabase().delete("hits2", null, null);
            zzay zzay2 = this.zzxp;
            zzk.zzav();
            zzay2.zzdb();
            zzay2.getWritableDatabase().delete("properties", null, null);
            zzec();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzdz();
        if (this.zzxs.zzdn()) {
            zzq("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    private final boolean zzea() {
        String str = "Failed to commit local dispatch transaction";
        zzk.zzav();
        zzdb();
        zzq("Dispatching a batch of local hits");
        boolean z = !this.zzxq.zzfr();
        if (!(!this.zzxs.isConnected()) || !z) {
            long max = (long) Math.max(zzbq.zzer(), zzbq.zzes());
            ArrayList arrayList = new ArrayList();
            long j = 0;
            while (true) {
                this.zzxp.beginTransaction();
                arrayList.clear();
                try {
                    List<zzcd> zzd = this.zzxp.zzd(max);
                    if (zzd.isEmpty()) {
                        zzq("Store is empty, nothing to dispatch");
                        zzee();
                        try {
                            this.zzxp.setTransactionSuccessful();
                            this.zzxp.endTransaction();
                            return false;
                        } catch (SQLiteException e) {
                            zze(str, e);
                            zzee();
                            return false;
                        }
                    } else {
                        zza("Hits loaded from store. count", Integer.valueOf(zzd.size()));
                        for (zzcd zzfg : zzd) {
                            if (zzfg.zzfg() == j) {
                                zzd("Database contains successfully uploaded hit", Long.valueOf(j), Integer.valueOf(zzd.size()));
                                zzee();
                                try {
                                    this.zzxp.setTransactionSuccessful();
                                    this.zzxp.endTransaction();
                                    return false;
                                } catch (SQLiteException e2) {
                                    zze(str, e2);
                                    zzee();
                                    return false;
                                }
                            }
                        }
                        if (this.zzxs.isConnected()) {
                            zzq("Service connected, sending hits to the service");
                            while (!zzd.isEmpty()) {
                                zzcd zzcd = (zzcd) zzd.get(0);
                                if (this.zzxs.zzb(zzcd)) {
                                    j = Math.max(j, zzcd.zzfg());
                                    zzd.remove(zzcd);
                                    zzb("Hit sent do device AnalyticsService for delivery", zzcd);
                                    try {
                                        this.zzxp.zze(zzcd.zzfg());
                                        arrayList.add(Long.valueOf(zzcd.zzfg()));
                                    } catch (SQLiteException e3) {
                                        zze("Failed to remove hit that was send for delivery", e3);
                                        zzee();
                                        try {
                                            return false;
                                        } catch (SQLiteException e4) {
                                            zze(str, e4);
                                            zzee();
                                            return false;
                                        }
                                    } finally {
                                        try {
                                            this.zzxp.setTransactionSuccessful();
                                            this.zzxp.endTransaction();
                                        } catch (SQLiteException e5) {
                                            zze(str, e5);
                                            zzee();
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        if (this.zzxq.zzfr()) {
                            List<Long> zzb = this.zzxq.zzb(zzd);
                            for (Long longValue : zzb) {
                                j = Math.max(j, longValue.longValue());
                            }
                            try {
                                this.zzxp.zza(zzb);
                                arrayList.addAll(zzb);
                            } catch (SQLiteException e6) {
                                zze("Failed to remove successfully uploaded hits", e6);
                                zzee();
                                try {
                                    this.zzxp.setTransactionSuccessful();
                                    this.zzxp.endTransaction();
                                    return false;
                                } catch (SQLiteException e7) {
                                    zze(str, e7);
                                    zzee();
                                    return false;
                                }
                            }
                        }
                        if (arrayList.isEmpty()) {
                            try {
                                return false;
                            } catch (SQLiteException e8) {
                                zze(str, e8);
                                zzee();
                                return false;
                            }
                        } else {
                            try {
                                this.zzxp.setTransactionSuccessful();
                                this.zzxp.endTransaction();
                            } catch (SQLiteException e9) {
                                zze(str, e9);
                                zzee();
                                return false;
                            }
                        }
                    }
                } catch (SQLiteException e10) {
                    zzd("Failed to read hits from persisted store", e10);
                    zzee();
                    try {
                        this.zzxp.setTransactionSuccessful();
                        this.zzxp.endTransaction();
                        return false;
                    } catch (SQLiteException e11) {
                        zze(str, e11);
                        zzee();
                        return false;
                    }
                }
            }
        } else {
            zzq("No network or service available. Will retry later");
            return false;
        }
    }

    public final void zzb(zzbw zzbw) {
        long j = this.zzxx;
        zzk.zzav();
        zzdb();
        long zzfx = zzcv().zzfx();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(zzfx != 0 ? Math.abs(zzcn().currentTimeMillis() - zzfx) : -1));
        zzdz();
        try {
            zzea();
            zzcv().zzfy();
            zzec();
            if (zzbw != null) {
                zzbw.zza(null);
            }
            if (this.zzxx != j) {
                this.zzxr.zzfq();
            }
        } catch (Exception e) {
            zze("Local dispatch failed", e);
            zzcv().zzfy();
            zzec();
            if (zzbw != null) {
                zzbw.zza(e);
            }
        }
    }

    public final void zzeb() {
        zzk.zzav();
        zzdb();
        zzr("Sync dispatching local hits");
        long j = this.zzxx;
        zzdz();
        try {
            zzea();
            zzcv().zzfy();
            zzec();
            if (this.zzxx != j) {
                this.zzxr.zzfq();
            }
        } catch (Exception e) {
            zze("Sync local dispatch failed", e);
            zzec();
        }
    }

    private final long zzds() {
        zzk.zzav();
        zzdb();
        try {
            return this.zzxp.zzds();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0;
        }
    }

    public final void zzec() {
        long j;
        zzk.zzav();
        zzdb();
        boolean z = true;
        if (!(!this.zzxy && zzef() > 0)) {
            this.zzxr.unregister();
            zzee();
        } else if (this.zzxp.isEmpty()) {
            this.zzxr.unregister();
            zzee();
        } else {
            if (!((Boolean) zzby.zzaai.get()).booleanValue()) {
                this.zzxr.zzfo();
                z = this.zzxr.isConnected();
            }
            if (z) {
                zzed();
                long zzef = zzef();
                long zzfx = zzcv().zzfx();
                if (zzfx != 0) {
                    j = zzef - Math.abs(zzcn().currentTimeMillis() - zzfx);
                    if (j <= 0) {
                        j = Math.min(zzbq.zzep(), zzef);
                    }
                } else {
                    j = Math.min(zzbq.zzep(), zzef);
                }
                zza("Dispatch scheduled (ms)", Long.valueOf(j));
                if (this.zzxu.zzez()) {
                    this.zzxu.zzi(Math.max(1, j + this.zzxu.zzey()));
                } else {
                    this.zzxu.zzh(j);
                }
            } else {
                zzee();
                zzed();
            }
        }
    }

    private final void zzed() {
        zzbv zzct = zzct();
        if (zzct.zzfc() && !zzct.zzez()) {
            long zzds = zzds();
            if (zzds != 0 && Math.abs(zzcn().currentTimeMillis() - zzds) <= ((Long) zzby.zzzm.get()).longValue()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzbq.zzeq()));
                zzct.zzfd();
            }
        }
    }

    private final void zzee() {
        if (this.zzxu.zzez()) {
            zzq("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzxu.cancel();
        zzbv zzct = zzct();
        if (zzct.zzez()) {
            zzct.cancel();
        }
    }

    private final long zzef() {
        long j = this.zzxt;
        if (j != Long.MIN_VALUE) {
            return j;
        }
        long longValue = ((Long) zzby.zzzh.get()).longValue();
        zzda zzcu = zzcu();
        zzcu.zzdb();
        if (zzcu.zzacv) {
            zzda zzcu2 = zzcu();
            zzcu2.zzdb();
            longValue = ((long) zzcu2.zzaax) * 1000;
        }
        return longValue;
    }

    public final void zzy(String str) {
        Preconditions.checkNotEmpty(str);
        zzk.zzav();
        zzr zza = zzcz.zza(zzco(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String zzfz = zzcv().zzfz();
        if (str.equals(zzfz)) {
            zzt("Ignoring duplicate install campaign");
        } else if (!TextUtils.isEmpty(zzfz)) {
            zzd("Ignoring multiple install campaigns. original, new", zzfz, str);
        } else {
            zzcv().zzad(str);
            if (zzcv().zzfw().zzj(zzbq.zzex())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzas zza2 : this.zzxp.zzf(0)) {
                zza(zza2, zza);
            }
        }
    }

    private final void zza(zzas zzas, zzr zzr) {
        Preconditions.checkNotNull(zzas);
        Preconditions.checkNotNull(zzr);
        zza zza = new zza(zzcm());
        zza.zza(zzas.zzdj());
        zza.enableAdvertisingIdCollection(zzas.zzdk());
        zzg zzac = zza.zzac();
        zzz zzz = (zzz) zzac.zzb(zzz.class);
        zzz.zzl("data");
        zzz.zzb(true);
        zzac.zza((zzi) zzr);
        zzu zzu = (zzu) zzac.zzb(zzu.class);
        zzq zzq = (zzq) zzac.zzb(zzq.class);
        for (Entry entry : zzas.zzdm().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if ("an".equals(str)) {
                zzq.setAppName(str2);
            } else if ("av".equals(str)) {
                zzq.setAppVersion(str2);
            } else if ("aid".equals(str)) {
                zzq.setAppId(str2);
            } else if ("aiid".equals(str)) {
                zzq.setAppInstallerId(str2);
            } else if (ChatConstants.ARG_USER_INFO_UID.equals(str)) {
                zzz.setUserId(str2);
            } else {
                zzu.set(str, str2);
            }
        }
        zzb("Sending installation campaign to", zzas.zzdj(), zzr);
        zzac.zza(zzcv().zzfv());
        zzac.zzam();
    }

    private final void zzeg() {
        zzdb();
        zzk.zzav();
        this.zzxy = true;
        this.zzxs.disconnect();
        zzec();
    }
}
